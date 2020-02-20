package DoctorsNote;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * A generic, non-instantiable class for getting authentication
 * values from DBCredentials.tsv in a scalable, maintainable way.
 *
 * This class should not be instantiated more than once per
 * execution path. (It is permitted to do so, but is redundant
 * and adds unnecessary overhead)
 */
public class DBCredentialsProvider {
    private final String tsvFilePath = "Lambdas/res/DBCredentials.tsv";

    private final String DBProvider;
    private final String DBEndpoint;
    private final String DBPort;
    private final String DBUrl;
    private final String DBUsername;
    private final String DBPassword;
    private final String DBDriver;

    public DBCredentialsProvider() throws IOException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(tsvFilePath));

            DBProvider = br.readLine().split(";;;;")[1];
            DBEndpoint = br.readLine().split(";;;;")[1];
            DBPort = br.readLine().split(";;;;")[1];
            DBUsername = br.readLine().split(";;;;")[1];
            DBPassword = br.readLine().split(";;;;")[1];
            DBDriver = br.readLine().split(";;;;")[1];

            DBUrl = DBProvider + DBEndpoint + ":" + DBPort;
        }
        catch(IOException e){
            throw new IOException("Unable to read DBCredentials.tsv");
        }
    }

    public String getDBProvider() {
        return this.DBProvider;
    }

    public String getDBEndpoint() {
        return this.DBEndpoint;
    }

    public String getDBPort() {
        return this.DBPort;
    }

    public String getDBURL() {
        return this.DBUrl;
    }

    public String getDBUsername() {
        return this.DBUsername;
    }

    public String getDBPassword() {
        return this.DBPassword;
    }

    public String getDBDriver() {
        return this.DBDriver;
    }
}
