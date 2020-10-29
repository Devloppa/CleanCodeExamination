import java.sql.SQLException;
import java.util.ArrayList;

public interface DAO {

	 int loginOfPlayer(String name);
	 
	 void postResult(int numberOfGuesses, int playerID);
	 
	 ArrayList<PlayerAverage> getTopTen() throws SQLException;
}
