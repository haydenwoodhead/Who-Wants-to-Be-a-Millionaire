package models;

import java.util.ArrayList;

/**
 * Created by hayden on 3/26/17.
 */
public class AskTheAudience {

    /**
     * Class generates and stores random percentages to simulate an audience answering the question
     *
     * The percentages are biased towards the correct answer but it is still possible for the audience to be
     * incorrect.
     *
     * I say again this class can and will give users the wrong answer by design!
     */

    // Statics for ask the audience lifeline
    private static final int ASK_FULL_PERCENTAGE = 100;
    private static final int ASK_CORRECT_MIN = 40;
    private static final int ASK_CORRECT_MAX = 65;
    private static final int ASK_INCORRECT_MIN = 0;

    private static final int NUMBER_OF_RANDOM_VALUES_NEEDED = 2;
    private static final int NUMBER_OF_INCORRECT_PERCENTAGES = 3;

    private int correctChoice;

    private ArrayList<Integer> incorrectPercentages;
    private ArrayList<String> percentages;


    public AskTheAudience(Round round) {
        int percentageRemaining = ASK_FULL_PERCENTAGE;

        this.correctChoice = Helper.randomNumberBetweenInterval(ASK_CORRECT_MIN, ASK_CORRECT_MAX);

        this.incorrectPercentages = new ArrayList<Integer>();

        if (round.getChoices().size() == 4) {

            percentageRemaining = percentageRemaining - this.correctChoice;

            for (int i = 0; i < NUMBER_OF_RANDOM_VALUES_NEEDED; i++) {
                this.incorrectPercentages.add(Helper.randomNumberBetweenInterval(ASK_INCORRECT_MIN, percentageRemaining));
                percentageRemaining = percentageRemaining - this.incorrectPercentages.get(i);
            }

            this.incorrectPercentages.add(percentageRemaining);

        } else {
            this.incorrectPercentages.add(ASK_FULL_PERCENTAGE-this.correctChoice);
        }

        this.percentages = new ArrayList<String>();

        for (int i = 0; i < this.incorrectPercentages.size(); i++) {
            this.percentages.add(Integer.toString(this.incorrectPercentages.get(i)));
        }

        int correctChoiceLocation = round.findCorrectAnswer();

        this.percentages.add(correctChoiceLocation, Integer.toString(this.correctChoice));
    }

    public ArrayList<String> getPercentages() {
        return percentages;
    }
}
