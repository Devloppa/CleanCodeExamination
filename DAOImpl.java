import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOImpl implements DAO {

	Connection connection;
	Statement stmt;
	ResultSet rs;

	@Override
	public int loginOfPlayer(String name) {
		int id = 0;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/moo", "root", "root");
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select id,name from players where name = '" + name + "'");
			if (rs.next()) {
				id = rs.getInt("id");
			}

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("ErrorMessage:" + e);
		}

		return id;

	}

	@Override
	public void postResult(int numberOfGuesses, int playerID) {

		try {
			int ok = stmt.executeUpdate(
					"INSERT INTO results " + "(result, player) VALUES (" + numberOfGuesses + ", " + playerID + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<PlayerAverage> getTopTen() throws SQLException {
		ArrayList<PlayerAverage> topList = new ArrayList<>();
		Statement stmt2 = connection.createStatement();
		ResultSet rs2;
		rs = stmt.executeQuery("select * from players");
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			rs2 = stmt2.executeQuery("select * from results where player = " + id);
			int nGames = 0;
			int totalGuesses = 0;
			while (rs2.next()) {
				nGames++;
				totalGuesses += rs2.getInt("result");
			}
			if (nGames > 0) {
				topList.add(new PlayerAverage(name, (double) totalGuesses / nGames));
			}

		}
		return topList;
	}

}
