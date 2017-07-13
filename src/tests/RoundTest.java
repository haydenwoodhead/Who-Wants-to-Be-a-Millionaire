package tests;

import models.Question;
import models.Round;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by hayden on 5/25/17.
 */
public class RoundTest {

    Round round;
    String answer;

    @Before
    public void setupRound() {
        Question.Builder qBuilder = new Question.Builder();

        answer = "Question Answer";

        Question question = qBuilder.id(UUID.randomUUID())
                .questionTitle("Question")
                .questionAnswer(answer)
                .incorrect("Wrong 1")
                .incorrect("Wrong 2")
                .incorrect("Wrong 3")
                .difficulty(1)
                .build();

        this.round = new Round(question);
    }

    @Test
    public void testRemoveTwoIncorrectOptions() {
        round.removeTwoIncorrectOptions();

        // if the correct answer doesn't exist inside the choices
        // then fail the test
        if (round.getChoices().indexOf(answer) == -1) {
            fail();
        }
    }

    @Test
    public void testCheckAnswer() throws Exception {
        // Loop over this test a few times to make sure that we still get the correct answer after
        // random shuffling
        for (int i = 0; i < 10; i++) {
            String correctAnswerChar = Character.toString((char) (round.findCorrectAnswer() + 97));

            assertTrue(round.checkAnswer(correctAnswerChar));

            // Get the private shuffleChoices method and run it, ready for the next iteration
            Method shuffleMethod = Round.class.getDeclaredMethod("shuffleChoices");
            shuffleMethod.setAccessible(true);
            shuffleMethod.invoke(round);
        }
    }
}