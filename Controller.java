import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.JOptionPane;

public class Controller {

	DAO dao;
	GUI gui;
	GameLogic game;

	public Controller(DAO dao, GUI gui, GameLogic game) {
		this.dao = dao;
		this.gui = gui;
		this.game = game;
	}

	public void run() {

		int optionsPaneState = JOptionPane.YES_OPTION;

		int playerID = getPlayerIDFromDB();

		while (optionsPaneState == JOptionPane.YES_OPTION) {
			String goal = game.makeGoal();
			gui.clear();
			gui.addString("New game with ID:" + playerID + "\n");
			// comment out or remove next line to play real games!
			gui.addString("For practice, number is: " + goal + "\n");
			String playerGuess = gui.getString();
			gui.addString(playerGuess + "\n");
			int numberOfGuesses = 1;
			String bbcc = game.checkBC(goal, playerGuess);
			gui.addString(bbcc + "\n");
			while (!bbcc.equals("BBBB,")) {
				numberOfGuesses++;
				playerGuess = gui.getString();
				gui.addString(playerGuess + "\n");
				bbcc = game.checkBC(goal, playerGuess);
				gui.addString(bbcc + "\n");
			}
			dao.postResult(numberOfGuesses, playerID);
			ArrayList<PlayerAverage> list = dao.getTopTen();
			gui.addString("\nTop ten: \n");
			list.forEach((players) -> {
				gui.addString(players.name + " " + players.average + "\n");
			});

			optionsPaneState = JOptionPane.showConfirmDialog(null, "Correct, it took " + numberOfGuesses + " guesses\nContinue?");
		}

		gui.exit();
	}

	int getPlayerIDFromDB() {

		Optional<Integer> playerID = Optional.empty();

		while (playerID.isEmpty()) {

			gui.addString("Enter your user name:\n");
			String name = gui.getString();

			playerID = dao.loginOfPlayer(name);

			if (playerID.isEmpty()) {
				gui.addString("Wrong username. Try again \n");
			}
		}
		return playerID.get();

	}

}
