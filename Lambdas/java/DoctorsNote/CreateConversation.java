package DoctorsNote;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class CreateConversation implements RequestHandler<String, String> {
    private final String addMessageFormatString = "INSERT INTO Conversation (conversationName, lastMessageTime) VALUES (\'%s\', \'%s\');";

    public String handleRequest(String jsonString, Context context) {
        try {
            // Converting the passed JSON string into a POJO
            Gson gson = new Gson();
            CreateConversationRequest request = gson.fromJson(jsonString, CreateConversationRequest.class);

            // Establish connection with MariaDB
            DBCredentialsProvider dbCP;
            Connection connection = getConnection();

            // Write to database
            Statement statement = connection.createStatement();
            String writeRowString = String.format(addMessageFormatString,
                    request.getConversationName(),
                    (new java.sql.Timestamp((new Date()).getTime())).toString());
            statement.executeUpdate(writeRowString);

            // Disconnect connection with shortest lifespan possible
            connection.close();

            return gson.toJson(new CreateConversationResponse());
        } catch (Exception e) {
            return null;
        }
    }

    private Connection getConnection() {
        try {
            DBCredentialsProvider dbCP = new DBCredentialsProvider();
            Class.forName(dbCP.getDBDriver());     // Loads and registers the driver
            return DriverManager.getConnection(dbCP.getDBURL(),
                    dbCP.getDBUsername(),
                    dbCP.getDBPassword());
        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new NullPointerException("Failed to load connection in AddMessage:getConnection()");
        }
    }

    private class CreateConversationRequest {
        private String conversationName;

        public String getConversationName() {
            return conversationName;
        }

        public void setConversationName(String conversationName) {
            this.conversationName = conversationName;
        }
    }

    private class CreateConversationResponse {
        // No payload necessary
    }

    public static void main(String[] args) {
        throw new IllegalStateException();
    }
}