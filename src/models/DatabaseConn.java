package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by hayden on 5/11/17.
 */
public class DatabaseConn {

    /**
     * We reference this in lots of places lets store it somewhere once
     */
    public static final String CONNECTION_URL = "jdbc:derby:MillionaireDB;create=true";

    private DatabaseConn(){};
}
