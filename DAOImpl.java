import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import javax.management.RuntimeErrorException;

public class DAOImpl implements DAO {

	Connection connection;
	Statement stmt;
	ResultSet rs;

	@Override
	public Optional<Integer> loginOfPlayer(String name) {
		Optional<Integer> id = Optional.empty();
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/moo", "root", "root");
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select id,name from players where name = '" + name + "'");
			if (rs.next()) {
				id = Optional.of(rs.getInt("id"));
			}

		} catch (SQLException | ClassNotFoundException e) {
			throw new RuntimeException("Error in top loginOfPlayer()" + e);
		}

		return id;

	}

	@Override
	public void postResult(int numberOfGuesses, int playerID) {

		try {
			int ok = stmt.executeUpdate(
					"INSERT INTO results " + "(result, player) VALUES (" + numberOfGuesses + ", " + playerID + ")");
		} catch (SQLException e) {
			throw new RuntimeException("Error in top postResult()" + e);
		}
	}

	public ArrayList<PlayerAverage> getTopTen() {
		try {
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
		} catch (SQLException e) {
			throw new RuntimeException("Error in top getTopTen()" + e);
		}
	}

}
