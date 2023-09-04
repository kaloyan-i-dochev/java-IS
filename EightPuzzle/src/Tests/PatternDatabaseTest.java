package Tests;

import org.junit.Before;
import org.junit.Test;

import Main.PuzzleState;
import PuzzleSolvers.Patterns.PatternDatabase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PatternDatabaseTest {
    PatternDatabase database;
    PuzzleState goalState;

    @Before
    public void setUp() {
    	database = new PatternDatabase(3, 3); // create a PatternDatabase with a 3x3 default goal state
        goalState = new PuzzleState(new int[][] { // goal state for a 3x3 puzzle
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 0 }
        });
        database.generateDatabase();
    }

    @Test
    public void testGoalStateInDatabase() {
        PuzzleState goalState = new PuzzleState(new int[][] {
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 0 }
        });

        int goalStateCost = database.queryDatabase(goalState);
        assertEquals(goalStateCost, 0);
    }

    @Test
    public void testDatabaseSize() {
        // Assuming only solvable states are stored in the database
        assertEquals(database.getDatabaseSize(), 181440); // This number is an example, you would need to calculate the correct value
    }

    // Add more tests here...

}
