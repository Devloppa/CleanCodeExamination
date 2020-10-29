package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameLogicTest {
	
	GameLogicDAO game;
	
	@Test
	void testMakeGoal() {
		game = new GameLogicMock();
		
		String expectedResult = "1111";
		String testResult = game.makeGoal();
		assertEquals(expectedResult, testResult);
	}

	@Test
	void testNotMakeGoal() {
		game = new GameLogicMock();
		
		String notExpectedResult = "1112";
		String testResult = game.makeGoal();
		assertNotEquals(notExpectedResult, testResult);
	}
	
	
	@Test
	void testCheckBBBB() {
		game = new GameLogicMock();
		
		String goal = "0825";
		String playerGuess = "0825";
		
		String expectedResult = "BBBB,";
		String result = game.checkBC(goal, playerGuess);
		
		assertEquals(expectedResult, result);
	}

	@Test
	void testCheckBBBC() {
		game = new GameLogicMock();
		
		String goal = "0825";
		String playerGuess = "0822";
		
		String expectedResult = "BBB,C";
		String result = game.checkBC(goal, playerGuess);
		assertEquals(expectedResult, result);
	}

	
	@Test
	void testCheckBBCC() {
		game = new GameLogicMock();
		
		String goal = "0825";
		String playerGuess = "0808";
		
		String expectedResult = "BB,CC";
		String result = game.checkBC(goal, playerGuess);
		
		assertEquals(expectedResult, result);
		
	}
	
	@Test
	void testCheckBCCC() {
		
		game = new GameLogicMock();
		
		String goal = "0825";
		String playerGuess = "0582";
		
		String expectedResult = "B,CCC";
		String result = game.checkBC(goal, playerGuess);
		
		assertEquals(expectedResult, result);
	}
	
	@Test
	void testCheckCCCC() {
		game = new GameLogicMock();
		
		String goal = "0825";
		String playerGuess = "5280";
		
		String expectedResult = ",CCCC";
		String result = game.checkBC(goal, playerGuess);
		
		assertEquals(expectedResult, result);
	}
	
	@Test
	void testCheckAllWrong() {
		game = new GameLogicMock();
		
		String goal = "0825";
		String playerGuess = "1111";
		
		String expectedResult = ",";
		String result = game.checkBC(goal, playerGuess);
		
		assertEquals(expectedResult, result);
	}
}

