package models;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by hayden on 3/30/17.
 */
public class Leaderboard {

    /**
     * Stores scores of previous players
     */

    private static final String SCORES_FILE = "database/scores.txt";

    private static final int LEADERBOARD_START = 0;
    private static final int LEADERBAORD_FINISH = 10;

    private static final int COLUMN_ID = 1;
    private static final int COLUMN_USERNAME = 2;
    private static final int COLUMN_MONEY = 3;

    public static final int INSIGNIFICANT_SCORE_POS = -1;

    private ArrayList<Score> scores;

    private Leaderboard() {
        this.scores = new ArrayList<Score>();
    }


    public static Leaderboard fromDatabase() {
        Leaderboard newLeaderboard = new Leaderboard();

        try {
            Connection conn = DriverManager.getConnection(DatabaseConn.CONNECTION_URL);

            Statement statement = conn.createStatement();
            statement.setMaxRows(LEADERBAORD_FINISH);

            String selectTop10 = "select * from app.SCORES order by MONEYWON desc";

            ResultSet rs = statement.executeQuery(selectTop10);

            while (rs.next()) {
                Score score = new Score(rs.getString(COLUMN_ID),
                                        rs.getString(COLUMN_USERNAME),
                                        rs.getInt(COLUMN_MONEY));

                newLeaderboard.scores.add(score);
            }

            rs.close();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newLeaderboard;
    }

    /**
     * Read in leaderboard from file
     * @return a leaderboard object
     */
    @Deprecated
    public static Leaderboard fromFile() {
        Leaderboard newLeaderboard = new Leaderboard();

        try {
            File file = new File(SCORES_FILE);

            Scanner s = new Scanner(file);

            while (s.hasNextLine()) {

                String row = s.nextLine();

                String[] columns = row.split("//");

                Score score = new Score(columns[COLUMN_ID-1], columns[COLUMN_USERNAME-1], columns[COLUMN_MONEY-1]);

                newLeaderboard.scores.add(score);
            }

            s.close();
        } catch (IOException e) {
            System.err.println("Scores database file not found!");
            e.printStackTrace();
        }

        newLeaderboard.sortLeaderboard();

        return newLeaderboard;
    }

    /**
     * Write leaderboard out to file
     */
    @Deprecated
    public void toFile() {

        this.scores.trimToSize();

        try {
            File file = new File(SCORES_FILE);

            PrintWriter pw = new PrintWriter(file);

            for (Score score: this.scores) {
                pw.println(score.toString());
            }

            pw.close();

        } catch (IOException e) {
            System.err.println("Writing failed!");
            e.printStackTrace();
        }

    }

    /**
     * Add a score to the leaderboard and return its position within the arraylist
     * @param score
     * @return -1 if score not added otherwise return index
     */
    public int addScore(Score score) {
        this.scores.add(score); // add our new score to the arraylist
        this.sortLeaderboard(); // sort the arraylist in desc order
        this.trimLeaderBoard(); // trim the arraylist to the top 10 scores

        return this.scores.indexOf(score); // try find our score
    }

    /**
     * Sort the leaderboard by money in desc order.
     * Note: only sorts the leaderboard in memory
     */
    private void sortLeaderboard() {
        Collections.sort(this.scores, new Score.ScoreComparator());
    }

    /**
     * Trim leaderboard to a max of 10 scores
     *  MUST USE sortLeaderboard() BEFORE RUNNING
     *  Note only trims the leaderboard in memory
     */
    private void trimLeaderBoard() {
        int endOfLeaderBoard = 0;

        if (this.scores.size() < LEADERBAORD_FINISH) {
            endOfLeaderBoard = scores.size();
        } else {
            endOfLeaderBoard = LEADERBAORD_FINISH;
        }

        this.scores = new ArrayList<Score>(this.scores.subList(LEADERBOARD_START, endOfLeaderBoard));
    }

    /**
     * Used by LeaderboardView to determine wether or not to add the label for leaderboard position
     * @param i the number of the score
     * @return true if score number exists in leaderboard
     */
    public boolean hasPosition(int i) {
        try {
            this.scores.get(i-1);

            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public ArrayList<Score> getScores() {
        return scores;
    }
}
