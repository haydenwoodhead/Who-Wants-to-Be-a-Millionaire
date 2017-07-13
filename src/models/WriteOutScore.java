package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by hayden on 5/11/17.
 */
public class WriteOutScore extends Thread {

    /**
     * Class to concurrently write out score to database
     */

    private Score scoreToWrite;

    public WriteOutScore(Score scoreToWrite) {
        this.scoreToWrite = scoreToWrite;
    }

    @Override
    public void run() {
        try {
            Connection conn = DriverManager.getConnection(DatabaseConn.CONNECTION_URL);

            String insertStatement = "insert into app.SCORES values ('"+ scoreToWrite.getId() + "', '"
                    + scoreToWrite.getUserName() +"', " + scoreToWrite.getMoneyWon()+ ")";

            Statement statement = conn.createStatement();

            statement.execute(insertStatement);

            statement.close();
            conn.close();

        } catch (SQLException e) {
            //DO nothing!
        }
    }
}
