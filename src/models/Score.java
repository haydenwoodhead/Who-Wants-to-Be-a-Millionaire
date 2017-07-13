package models;

import java.util.Comparator;
import java.util.UUID;

/**
 * Created by hayden on 3/30/17.
 */
public class Score {

    /**
     * Class stores an instance of score as well as a ScoreComparator class which can be used to sort an arraylist
     * of scores into descending order
     */

    private UUID id;
    private String userName;
    private int moneyWon;

    public static class ScoreComparator implements Comparator<Score> {

        @Override
        public int compare(Score o1, Score o2) {

            if (o1.getMoneyWon() > o2.getMoneyWon()) { // return -1 if lhs should be before rhs
                return -1;
            } else if (o1.getMoneyWon() < o2.getMoneyWon()) { // return 1 if rhs should be before lhs
                return 1;
            } else {
                return 0; // otherwise return 0
            }
        }
    }

    public Score(String userName, int moneyWon) {
        this.id = UUID.randomUUID();
        this.userName = userName;
        this.moneyWon = moneyWon;
    }

    public Score(String id, String userName, int moneyWon) {
        this.id = UUID.fromString(id);
        this.userName = userName;
        this.moneyWon = moneyWon;
    }

    public Score(String id, String userName, String moneyWon) {
        this.id = UUID.fromString(id);
        this.userName = userName;
        this.moneyWon = Integer.parseInt(moneyWon);
    }

    public Score(UUID id, String userName, int moneyWon) {
        this.id = id;
        this.userName = userName;
        this.moneyWon = moneyWon;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMoneyWon() {
        return moneyWon;
    }

    public void setMoneyWon(int moneyWon) {
        this.moneyWon = moneyWon;
    }

    @Override
    public String toString() {
        return this.id+"//"+this.userName+"//"+this.moneyWon;
    }

    @Override
    public boolean equals(Object obj) {
        boolean toReturn;

        if (obj instanceof Score) {

            Score q = (Score) obj;

            if (q.getId().equals(this.getId())) {
                toReturn = true;
            } else {
                toReturn = false;
            }

        } else {
            toReturn = false;
        }

        return toReturn;
    }
}



