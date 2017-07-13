package models;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static models.Game.*;

/**
 * Created by hayden on 3/3/17.
 */
public class QuestionDatabase {

    /**
     * Class acts as a in memory database for questions allowing selection and other methods
     */

    private static final int NUMBER_OF_COLUMNS = 7;

    private static final int COLUMN_ID = 1;
    private static final int COLUMN_QUESTION = 2;
    private static final int COLUMN_ANSWER = 3;
    private static final int COLUMN_INCORRECT_1 = 4;
    private static final int COLUMN_INCORRECT_2 = 5;
    private static final int COLUMN_INCORRECT_3 = 6;
    private static final int COLUMN_DIFFICULTY = 7;

    private static final String SELECT_ALL_QUESTIONS = "select * from app.QUESTIONS";

    private ArrayList<Question> difficulty1;
    private ArrayList<Question> difficulty2;
    private ArrayList<Question> difficulty3;

    private QuestionDatabase() {
        difficulty1 = new ArrayList<Question>();
        difficulty2 = new ArrayList<Question>();
        difficulty3 = new ArrayList<Question>();
    }

    public static QuestionDatabase fromDatabase() {
        QuestionDatabase toReturn = new QuestionDatabase();

        //Read in from database

        try {
            Connection conn = DriverManager.getConnection(DatabaseConn.CONNECTION_URL);

            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery(SELECT_ALL_QUESTIONS);

            while (rs.next()) {
                Question.Builder qBuilder = new Question.Builder();

                Question question = qBuilder.id(rs.getString(COLUMN_ID))
                        .questionTitle(rs.getString(COLUMN_QUESTION))
                        .questionAnswer(rs.getString(COLUMN_ANSWER))
                        .incorrect(rs.getString(COLUMN_INCORRECT_1))
                        .incorrect(rs.getString(COLUMN_INCORRECT_2))
                        .incorrect(rs.getString(COLUMN_INCORRECT_3))
                        .difficulty(rs.getString(COLUMN_DIFFICULTY))
                        .build();

                if (question.getDifficulty() == SET_1_DIFFICULTY) {
                    toReturn.difficulty1.add(question);
                } else if (question.getDifficulty() == SET_2_DIFFICULTY) {
                    toReturn.difficulty2.add(question);
                } else if (question.getDifficulty() == SET_3_DIFFICULTY) {
                    toReturn.difficulty3.add(question);
                } else {
                    throw new IllegalArgumentException("Database file contains an incorrectly formatted question");
                }
            }

            rs.close();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println("SQL Exception");
            e.printStackTrace();
        }

        if (toReturn.difficulty1.size() < NUMBER_OF_QUESTIONS_NEEDED ||
                toReturn.difficulty2.size() <  NUMBER_OF_QUESTIONS_NEEDED ||
                toReturn.difficulty2.size() < NUMBER_OF_QUESTIONS_NEEDED ) {
            // find something to do here
            System.err.println("Insufficient number of questions");
            System.exit(1);
        }

        return toReturn;
    }

    @Deprecated
    public static QuestionDatabase fromFile() {
        /**
         * Method replaced by fromDatabase()
         */

        QuestionDatabase toReturn = new QuestionDatabase();

        //Read in from file

        try {

            File file = new File("database/questions.txt");

            Scanner s = new Scanner(file);

            while (s.hasNextLine()) {

                String row = s.nextLine();

                String[] columns = row.split("//");

                if (columns.length != NUMBER_OF_COLUMNS) {
                    throw new IllegalArgumentException("Database file contains an incorrectly formatted question: "
                            + row);
                }

                Question.Builder qBuilder = new Question.Builder();

                // For some reason ResultSet.getX(int) doesn't use index's I changed the static's to the
                // correct number for ResultSet.getX and simply -1 from each of these to get the correct column
                Question question = qBuilder.id(columns[COLUMN_ID-1])
                                            .questionTitle(columns[COLUMN_QUESTION-1])
                                            .questionAnswer(columns[COLUMN_ANSWER-1])
                                            .incorrect(columns[COLUMN_INCORRECT_1-1])
                                            .incorrect(columns[COLUMN_INCORRECT_2-1])
                                            .incorrect(columns[COLUMN_INCORRECT_3-1])
                                            .difficulty(columns[COLUMN_DIFFICULTY-1])
                                            .build();

                if (question.getDifficulty() == SET_1_DIFFICULTY) {
                    toReturn.difficulty1.add(question);
                } else if (question.getDifficulty() == SET_2_DIFFICULTY) {
                    toReturn.difficulty2.add(question);
                } else if (question.getDifficulty() == SET_3_DIFFICULTY) {
                    toReturn.difficulty3.add(question);
                } else {
                    throw new IllegalArgumentException("Database file contains an incorrectly formatted question: "
                            + row);
                }
            }

        } catch (IOException e) {
            System.err.println("Questions database file not found!");
            e.printStackTrace();
        }

        if (toReturn.difficulty1.size() < NUMBER_OF_QUESTIONS_NEEDED ||
                toReturn.difficulty2.size() <  NUMBER_OF_QUESTIONS_NEEDED ||
                toReturn.difficulty2.size() < NUMBER_OF_QUESTIONS_NEEDED ) {
            // find something to do here
            System.err.println("Insufficient number of questions");
            System.exit(1);
        }

        return toReturn;
    }

    /**
     * Returns a  arraylist of a given number of randomly selected questions of a given difficulty
     *
     * @param numberOfQuestions
     * @param difficulty
     * @return
     */
    public ArrayList<Question> getQuestionsByDifficulty(int numberOfQuestions, int difficulty) {
        ArrayList<Question> toReturn = new ArrayList<Question>();

        if (difficulty == SET_1_DIFFICULTY) {
            toReturn = this.getRandomQuestionsFromList(this.difficulty1, numberOfQuestions);
        } else if (difficulty == SET_2_DIFFICULTY) {
            toReturn = this.getRandomQuestionsFromList(this.difficulty2, numberOfQuestions);
        } else if (difficulty == SET_3_DIFFICULTY) {
            toReturn = this.getRandomQuestionsFromList(this.difficulty3, numberOfQuestions);
        } else {
            throw new IllegalArgumentException("Difficulty must be 1, 2 or 3");
        }

        return toReturn;
    }

    /**
     * Method returns a number of questions from a given arraylist
     *
     * @param arrayList
     * @param numberOfQuestions
     * @return
     */
    private ArrayList<Question>  getRandomQuestionsFromList(ArrayList<Question> arrayList, int numberOfQuestions) {
        ArrayList<Question> toReturn = new ArrayList<Question>();

        // If the size of of our question database is the same size as the number of questions we want then
        // just add them all!
        if (arrayList.size() == numberOfQuestions) {
            toReturn.addAll(arrayList);
        } else {
            for (int i = 0; i < numberOfQuestions; i++) {
                boolean questionNotAdded = true;

                while (questionNotAdded) {
                    int questionIndex = Helper.randomNumberBetweenInterval(1, arrayList.size()) - 1;

                    Question question = arrayList.get(questionIndex);

                    if (!toReturn.contains(question)) { // check to see if questions has already been added
                        toReturn.add(question);
                        questionNotAdded = false;
                    }
                }
            }

            // Second option for selecting questions
            // Collections.shuffle(arrayList, new Random());
            // toReturn = new ArrayList<QuestionView>(arrayList.subList(0, numberOfQuestions));
        }

        return toReturn;
    }
}
