package DoctorsNote;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    public static Connection getConnection() {
        try {
            DBCredentialsProvider dbCP = new DBCredentialsProvider();
            Class.forName(dbCP.getDBDriver());     // Loads and registers the driver
            Properties properties = new Properties();
            properties.setProperty("user", dbCP.getDBUsername());
            properties.setProperty("password", dbCP.getDBPassword());
            properties.setProperty("useFractionalSeconds", "true");
            //return DriverManager.getConnection(dbCP.getDBURL(),
            //        dbCP.getDBUsername(),
            //        dbCP.getDBPassword());

            return DriverManager.getConnection(dbCP.getDBURL(), properties);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new NullPointerException("Failed to load connection");
        }
    }
}