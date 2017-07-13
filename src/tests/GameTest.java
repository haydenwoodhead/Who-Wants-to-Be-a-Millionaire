package tests;

import models.*;
import org.junit.*;

import org.hamcrest.CoreMatchers;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


/**
 * Created by hayden on 5/25/17.
 */
public class GameTest {

    private QuestionDatabase questionDatabase;

    @Before
    public void setupDB() {
        this.questionDatabase = QuestionDatabase.fromDatabase();
    }

    /**
     * Make sure when we generate two games they are different each time
     */
    @Test
    public void testGameUnique() {
        Game g1 = new Game(questionDatabase);
        Game g2 = new Game(questionDatabase);

        assertNotEquals(g1, g2);
    }

    @Test
    public void testGameQuestions() {
        Game game = new Game(questionDatabase);

        ArrayList<Question> questions = game.getQuestions();

        assertNotNull(questions);
        assertTrue(questions.size() == 15);

        ArrayList<Question> spare = game.getSpareQuestions();

        assertNotNull(spare);
        assertTrue(spare.size() == 3);
    }

    @Test
    public void testInitRound() {
        Game game = new Game(questionDatabase);

        game.initRound();

        assertNotNull(game.getCurrentRound());
    }

    @Test
    public void testCorrectAnswer() {
        Game game = new Game(questionDatabase);

        game.initRound();

        int roundNoBefore = game.getCurrentRoundNumber();
        int moneyBefore = game.getCurrentMoney();

        game.correctAnswer();

        int roundNoAfter = game.getCurrentRoundNumber();
        int moneyAfter = game.getCurrentMoney();

        assertNotEquals(roundNoBefore, roundNoAfter);
        assertNotEquals(moneyBefore, moneyAfter);
        assertFalse(game.isRoundNotFinished());
    }

    /**
     * Test simulates a random number of correct answers followed by a inorrect one
     * Then make sures the game finished and we get exit money
     */
    @Test
    public void testIncorrectAnswer() {
        Game game = new Game(questionDatabase);

        for (int i = 0; i < Helper.randomNumberBetweenInterval(0, 15); i++) {
            game.initRound();
            game.correctAnswer();
        }

        game.incorrectAnswer();

        assertEquals(game.getExitReason(), Game.LOST_GAME);
        assertFalse(game.isNotFinished());
        assertThat(game.getCurrentMoney(), isOneOf(Game.WINNINGS_SET_0,
                Game.WINNINGS_SET_1, Game.WINNINGS_SET_2, Game.WINNINGS_SET_3));
    }

    @Test
    public void testSwitchQuestion() {
        Game game = new Game(questionDatabase);
        game.initRound();

        Round before = game.getCurrentRound();

        game.useSwitchQuestion();

        Round after = game.getCurrentRound();

        assertNotEquals(before, after);
        assertFalse(game.hasSwitchQuestion());
    }

    @Test
    public void testUseAskTheAudience() {
        Game game = new Game(questionDatabase);
        game.initRound();

        assertThat(game.useAskTheAudience(), instanceOf(AskTheAudience.class));
        assertFalse(game.hasAskTheAudience());
    }

    @Test
    public void testUseFiftyFifty() {
        Game game = new Game(questionDatabase);
        game.initRound();
        game.useFiftyFifty();

        game.getCurrentRound();

        assertEquals(game.getCurrentRound().getChoices().size(), 2);
        assertFalse(game.hasFiftyFifty());
    }

    @Test
    public void testFinishGame() {
        Game game = new Game(questionDatabase);
        game.initRound();

        game.finishGame();

        assertFalse(game.isRoundNotFinished());
        assertFalse(game.isNotFinished());
        assertEquals(game.getExitReason(), Game.EXIT_GAME);
    }

    @After
    public void teardownDB() {
        this.questionDatabase = null;
    }

}