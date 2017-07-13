package models;

import java.util.ArrayList;

/**
 * Created by hayden on 3/3/17.
 */
public class Game {

    /**
     * Class is used to store the current status of a game including whether or not lifelines have been used and the
     * remaining questions
     */

    /**
     * Could also use an enum for these constants, may still switch them over but they work just as well as
     * static constants.
     */
    public static final int QUESTIONS_PER_ROUND = 5;
    public static final int NUMBER_OF_QUESTIONS_NEEDED = QUESTIONS_PER_ROUND + 1;

    //Difficulties
    public static final int SET_1_DIFFICULTY = 1;
    public static final int SET_2_DIFFICULTY = 2;
    public static final int SET_3_DIFFICULTY = 3;

    // Exit reasons
    public static final String EXIT_GAME = "exit";
    public static final String WON_GAME = "won";
    public static final String LOST_GAME = "lost";

    // Winning amount for rounds that don't follow the winnings * 2 rule
    private static final int WINNINGS_ROUND_1 = 100;
    private static final int WINNINGS_ROUND_4 = 500;
    private static final int WINNINGS_ROUND_12 = 125000;

    public static final int WINNINGS_SET_0 = 0;
    public static final int WINNINGS_SET_1 = 1000;
    public static final int WINNINGS_SET_2 = 32000;
    public static final int WINNINGS_SET_3 = 1000000;

    private static final int FINAL_ROUND = 15;

    // Location of difficulties in spare questions array
    private static final int SPARE_Q_DIFF_1 = 0;
    private static final int SPARE_Q_DIFF_2 = 1;
    private static final int SPARE_Q_DIFF_3 = 2;

    private QuestionDatabase questionDatabase;
    private ArrayList<Question> questions;
    private ArrayList<Question> spareQuestions;

    private boolean hasFiftyFifty;
    private boolean hasAskTheAudience;
    private boolean hasSwitchQuestion;

    private boolean notFinished;

    private String exitReason;

    private int currentMoney;
    private int currentRoundNumber;
    private Round currentRound;
    private boolean roundNotFinished;

    private String userName;

    public Game(QuestionDatabase questionDatabase) {
        this.questionDatabase = questionDatabase;
        setUpGameQuestions();

        this.hasFiftyFifty = true;
        this.hasAskTheAudience = true;
        this.hasSwitchQuestion = true;

        this.currentMoney = 0;
        this.currentRoundNumber = 1;

        this.notFinished = true;
    }

    /**
     * Initialise and setup game questions plus spares
     */
    private void setUpGameQuestions() {
        this.questions = new ArrayList<Question>();
        this.spareQuestions = new ArrayList<Question>();

        // Grab a set of 6 questions for each difficulty
        ArrayList<Question> qDiff1 = this.questionDatabase.getQuestionsByDifficulty(NUMBER_OF_QUESTIONS_NEEDED, SET_1_DIFFICULTY);
        ArrayList<Question> qDiff2 = this.questionDatabase.getQuestionsByDifficulty(NUMBER_OF_QUESTIONS_NEEDED, SET_2_DIFFICULTY);
        ArrayList<Question> qDiff3 = this.questionDatabase.getQuestionsByDifficulty(NUMBER_OF_QUESTIONS_NEEDED, SET_3_DIFFICULTY);

        // Add the first 5 of these to the actual questions list
        this.questions.addAll(qDiff1.subList(0, QUESTIONS_PER_ROUND));
        this.questions.addAll(qDiff2.subList(0, QUESTIONS_PER_ROUND));
        this.questions.addAll(qDiff3.subList(0, QUESTIONS_PER_ROUND));

        // Add the last question of each difficulty to spare questions
        this.spareQuestions.add(0, qDiff1.get(NUMBER_OF_QUESTIONS_NEEDED - 1));
        this.spareQuestions.add(1, qDiff2.get(NUMBER_OF_QUESTIONS_NEEDED - 1));
        this.spareQuestions.add(2, qDiff3.get(NUMBER_OF_QUESTIONS_NEEDED - 1));
    }

    /**
     * Create a new round clean round
     */
    public void initRound() {
        this.roundNotFinished = true;
        this.currentRound =  new Round(getNextQuestion());;
    }

    private Question getNextQuestion() {
        return this.questions.get(this.currentRoundNumber - 1); // -1 to shift to index
    }

    /**
     * Called if a user gets the question correct
     * Changes the round to finished, increments the round number
     * and gives the contestant prize money
     */
    public void correctAnswer() {
        this.roundNotFinished = false;
        this.currentRound = null;

        if (this.currentRoundNumber == 1) {
            this.currentMoney = WINNINGS_ROUND_1;
        } else if (this.currentRoundNumber == 4) {
            this.currentMoney = WINNINGS_ROUND_4;
        } else if (this.currentRoundNumber == 12) {
            this.currentMoney = WINNINGS_ROUND_12;
        } else {
            this.currentMoney *= 2;
        }

        if (this.currentRoundNumber == FINAL_ROUND) { // finish game if user get the final question correct
            this.notFinished = false;
            this.exitReason = WON_GAME;
            this.currentMoney = WINNINGS_SET_3;
        } else {
            this.currentRoundNumber++;
        }
    }

    /**
     * Called if the user gets the question incorrect
     * Sets round and game finished sets prize money to the threshold for that set of questions
     */
    public void incorrectAnswer() {
        this.roundNotFinished = false;
        this.notFinished = false;

        this.exitReason = LOST_GAME;

        if (this.currentRoundNumber <= 5) {
            this.currentMoney = WINNINGS_SET_0;
        } else if (this.currentRoundNumber > 5 && this.currentRoundNumber <= 10) {
            this.currentMoney = WINNINGS_SET_1;
        } else if (this.currentRoundNumber > 10) {
            this.currentMoney = WINNINGS_SET_2;
        }
    }

    /**
     * Tells the round to remove two incorrect options
     */
    public void useFiftyFifty() {
        this.currentRound.removeTwoIncorrectOptions();
        this.hasFiftyFifty = false;
    }

    /**
     * Swaps out current round for one with a new question
     */
    public void useSwitchQuestion() {

        if (this.currentRoundNumber <= 5) {
            this.currentRound = new Round(this.spareQuestions.get(SPARE_Q_DIFF_1));
        } else if (this.currentRoundNumber < 10) {
            this.currentRound = new Round(this.spareQuestions.get(SPARE_Q_DIFF_2));
        } else {
            this.currentRound = new Round(this.spareQuestions.get(SPARE_Q_DIFF_3));
        }

        this.hasSwitchQuestion = false;
    }

    /**
     * Creates a new ask the audience object with random audience response data
     */
    public AskTheAudience useAskTheAudience () {
        this.hasAskTheAudience = false;
        return new AskTheAudience(this.currentRound);
    }

    public void finishGame() {
        this.roundNotFinished = false;
        this.notFinished = false;
        this.exitReason = EXIT_GAME;
    }

    public Score generateScore() {
        return new Score(this.userName, this.currentMoney);
    }

    public boolean hasFiftyFifty() {
        return hasFiftyFifty;
    }

    public boolean hasAskTheAudience() {
        return hasAskTheAudience;
    }

    public boolean hasSwitchQuestion() {
        return hasSwitchQuestion;
    }

    public int getCurrentMoney() {
        return currentMoney;
    }

    public boolean isNotFinished() {
        return notFinished;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public boolean isRoundNotFinished() {
        return roundNotFinished;
    }

    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }

    public String getExitReason() {
        return exitReason;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<Question> getSpareQuestions() {
        return spareQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (hasFiftyFifty != game.hasFiftyFifty) return false;
        if (hasAskTheAudience != game.hasAskTheAudience) return false;
        if (hasSwitchQuestion != game.hasSwitchQuestion) return false;
        if (notFinished != game.notFinished) return false;
        if (currentMoney != game.currentMoney) return false;
        if (currentRoundNumber != game.currentRoundNumber) return false;
        if (roundNotFinished != game.roundNotFinished) return false;
        if (!questionDatabase.equals(game.questionDatabase)) return false;
        if (!questions.equals(game.questions)) return false;
        if (!spareQuestions.equals(game.spareQuestions)) return false;
        if (exitReason != null ? !exitReason.equals(game.exitReason) : game.exitReason != null) return false;
        if (currentRound != null ? !currentRound.equals(game.currentRound) : game.currentRound != null) return false;
        return userName != null ? userName.equals(game.userName) : game.userName == null;

    }

    @Override
    public int hashCode() {
        int result = questionDatabase.hashCode();
        result = 31 * result + questions.hashCode();
        result = 31 * result + spareQuestions.hashCode();
        result = 31 * result + (hasFiftyFifty ? 1 : 0);
        result = 31 * result + (hasAskTheAudience ? 1 : 0);
        result = 31 * result + (hasSwitchQuestion ? 1 : 0);
        result = 31 * result + (notFinished ? 1 : 0);
        result = 31 * result + (exitReason != null ? exitReason.hashCode() : 0);
        result = 31 * result + currentMoney;
        result = 31 * result + currentRoundNumber;
        result = 31 * result + (currentRound != null ? currentRound.hashCode() : 0);
        result = 31 * result + (roundNotFinished ? 1 : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }
}
