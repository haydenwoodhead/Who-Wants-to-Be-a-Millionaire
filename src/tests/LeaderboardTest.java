package tests;

import models.Leaderboard;
import models.Score;
import org.junit.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by hayden on 6/3/17.
 */
public class LeaderboardTest {

    private Leaderboard leaderboard;

    private Score topScore;
    private Score bottomScore;

    /**
     * Setup a fake (i.e not db backed) leaderboard object that we can test against
     * without modifiying our database
     * @throws Exception
     */
    @Before
    public void setupFakeLeaderboard() throws Exception {
        Constructor<Leaderboard> constructor =
                ((Constructor<Leaderboard>[]) Leaderboard.class.getDeclaredConstructors())[0];

        constructor.setAccessible(true);

        leaderboard = constructor.newInstance();

        ArrayList<Score> scores = leaderboard.getScores();

        topScore = new Score(UUID.randomUUID(), "Hayden", 1000000);
        bottomScore = new Score(UUID.randomUUID(), "Bart", 100);

        scores.add(topScore);
        scores.add(bottomScore);
        scores.add(new Score(UUID.randomUUID(), "Lisa", 1000000));
        scores.add(new Score(UUID.randomUUID(), "Maggie", 300));
        scores.add(new Score(UUID.randomUUID(), "Homer", 500));
        scores.add(new Score(UUID.randomUUID(), "Marge", 1000));
        scores.add(new Score(UUID.randomUUID(), "Smithers", 8000));
        scores.add(new Score(UUID.randomUUID(), "Apu", 125000));
        scores.add(new Score(UUID.randomUUID(), "Barney", 500000));
        scores.add(new Score(UUID.randomUUID(), "Grandpa", 32000));
    }

    @Test
    public void testFromDatabase() {
        Leaderboard fromDB = Leaderboard.fromDatabase();

        assertNotNull(fromDB);
        assertNotNull(fromDB.getScores());
        assertTrue(fromDB.getScores().size() <= 10);
    }

    @Test
    public void testAddScore() throws Exception{
        sortLeaderBoard();

        Score newScore1 = new Score(UUID.randomUUID(), "NS1", 200);
        int posNewScore1 = leaderboard.addScore(newScore1);

        assertEquals(9, posNewScore1);

        Score newScore2 = new Score(UUID.randomUUID(), "NS1", 100);
        int posNewScore2 = leaderboard.addScore(newScore2);

        assertEquals(Leaderboard.INSIGNIFICANT_SCORE_POS, posNewScore2);
    }

    @Test
    public void testSortLeaderboard() throws Exception {
        sortLeaderBoard();
        assertEquals(0, leaderboard.getScores().indexOf(topScore));
        assertEquals(9, leaderboard.getScores().indexOf(bottomScore));
    }

    /**
     * Allows us to easily access the private Leaderboard.sortLeaderboard()
     * @throws Exception
     */
    private void sortLeaderBoard() throws Exception {
        Method sort = leaderboard.getClass().getDeclaredMethod("sortLeaderboard");
        sort.setAccessible(true);
        sort.invoke(leaderboard);

    }
}
