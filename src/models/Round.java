package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Hayden on 11/03/2017.
 */
public class Round {

    /**
     * Stores a randomly shuffled set of answers to a question
     */

    public static final int POS_CHOICE_A = 0;
    public static final int POS_CHOICE_B = 1;
    public static final int POS_CHOICE_C = 2;
    public static final int POS_CHOICE_D = 3;

    public static final String CHOICE_A = "a";
    public static final String CHOICE_B = "b";
    public static final String CHOICE_C = "c";
    public static final String CHOICE_D = "d";

    private Question question;
    private int correctAnswer;
    private ArrayList<String> choices;

    public Round(Question question) {
        this.question = question;
        this.choices = new ArrayList<String>();

        // Add all question text to an array list
        this.choices.add(question.getQuestionAnswer());
        this.choices.addAll(question.getIncorrect());

        shuffleChoices();
    }

    public boolean checkAnswer(String choice) {
        boolean answerCorrect = false;

        choice = choice.toLowerCase();

        if (choice.equals(CHOICE_A)) {
            answerCorrect = this.correctAnswer == POS_CHOICE_A;
        } else if (choice.equals(CHOICE_B)) {
            answerCorrect = this.correctAnswer == POS_CHOICE_B;
        } else if (choice.equals(CHOICE_C)) {
            answerCorrect = this.correctAnswer == POS_CHOICE_C;
        } else if (choice.equals(CHOICE_D)) {
            answerCorrect = this.correctAnswer == POS_CHOICE_D;
        }

        return answerCorrect;
    }

    public Question getQuestion() {
        return this.question;
    }

    public ArrayList<String> getChoices() {
        return this.choices;
    }

    /**
     * Shuffle this array list to randomize where the correct answer appears
     */
    private void shuffleChoices() {
        Random r = new Random(System.currentTimeMillis());
        Collections.shuffle(this.choices, r);
        this.correctAnswer = findCorrectAnswer();
    }

    /**
     * Find the position of the correct answer in the arraylist
     * @return the position of the correct answer
     */
    public int findCorrectAnswer() {
        return this.choices.indexOf(question.getQuestionAnswer());
    }
    /**
     * Remove two incorrect options from the list of choices
     */
    public void removeTwoIncorrectOptions() {

        int tempAnswerLocation = this.correctAnswer;

        for (int i = 0; this.choices.size() > 2; i++) {
            if (i != tempAnswerLocation) {
                this.choices.remove(i);
            }

            // regrab location of the correct answer so we know where it is on each loop
            tempAnswerLocation = this.choices.indexOf(question.getQuestionAnswer());
        }

        shuffleChoices();

        this.correctAnswer = findCorrectAnswer();
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Round round = (Round) o;

        if (correctAnswer != round.correctAnswer) return false;
        if (!question.equals(round.question)) return false;
        return choices.equals(round.choices);

    }

    @Override
    public int hashCode() {
        int result = question.hashCode();
        result = 31 * result + correctAnswer;
        result = 31 * result + choices.hashCode();
        return result;
    }
}
