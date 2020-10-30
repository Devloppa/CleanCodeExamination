import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface DAO {

	 Optional<Integer> loginOfPlayer(String name);
	 
	 void postResult(int numberOfGuesses, int playerID);
	 
	 ArrayList<PlayerAverage> getTopTen();
}
