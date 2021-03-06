package DoctorsNote;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.SQLException;
import java.util.Map;

/*
 * A Lambda handler for purging reminders from a user.
 * This Lambda should be called from a cron job within the AWS Console and should not have an associated API.
 *
 * Expects: Any valid input (may also be null).
 * Returns: An empty response object.
 *
 * Error Handling: Throws a RuntimeException if an unrecoverable error is encountered
 */

public class PurgeReminders implements RequestHandler<Map<String,Object>, ReminderPurger.PurgeReminderResponse> {

    @Override
    public ReminderPurger.PurgeReminderResponse handleRequest(Map<String,Object> inputMap, Context context) {
        try {
            // Establish connection with MariaDB
            ReminderPurger purger = makeReminderPurger();
            ReminderPurger.PurgeReminderResponse response = purger.purge(inputMap, context);
            if (response == null) {
                System.out.println("PurgeReminders: ReminderPurger returned null");
                throw new RuntimeException("Server experienced an error");
            }
            System.out.println("PurgeReminders: ReminderPurger returned valid response");
            return response;
        } catch (SQLException e) {
            // This should only execute if closing the connection failed
            System.out.println("PurgeReminders: Could not close the database connection");
            return null;
        }
    }

    public ReminderPurger makeReminderPurger() {
        System.out.println("PurgeReminders: Instantiating ReminderPurger");
        return new ReminderPurger(Connector.getConnection());
    }

    public static void main(String[] args) throws IllegalStateException {
        System.out.println("PurgeReminders: Executing main() (THIS SHOULD NEVER HAPPEN)");
        throw new IllegalStateException();
    }
}
