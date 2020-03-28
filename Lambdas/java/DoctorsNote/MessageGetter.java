package DoctorsNote;

import com.amazonaws.services.lambda.runtime.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

public class MessageGetter {
    private final String getMessagesFormatString = "SELECT content, messageID, timeCreated, sender, contentType FROM Message" +
            " WHERE conversationID = ? ORDER BY timeCreated DESC LIMIT ?;";
    Connection dbConnection;

    public MessageGetter(Connection dbConnection) { this.dbConnection = dbConnection; }

    public GetMessagesResponse get(Map<String,Object> inputMap, Context context) {
        try {
            System.out.println("Getting messages");

            // Establish connection with MariaDB
            DBCredentialsProvider dbCP;

            // Reading from database
            PreparedStatement statement = dbConnection.prepareStatement(getMessagesFormatString);
            statement.setLong(1, Long.parseLong(((Map<String,Object>) inputMap.get("body-json")).get("conversationID").toString()));
            statement.setLong(2, Long.parseLong(((Map<String,Object>) inputMap.get("body-json")).get("numberToRetrieve").toString()));
            ResultSet messageResult = statement.executeQuery();

            // Processing results
            ArrayList<Message> messages = new ArrayList<>();
            while (messageResult.next()) {
                String content = messageResult.getString(1);
                long messageId = messageResult.getLong(2);
                long timeSent = messageResult.getTimestamp(3).toInstant().toEpochMilli();
                String sender = messageResult.getString(4);
                long contentType = messageResult.getLong(5);

                if (timeSent >= 0) {
                    messages.add(new Message(content, contentType, messageId, timeSent, sender));
                }
            }

            // Disconnect connection with shortest lifespan possible
            dbConnection.close();

            Message[] tempArray = new Message[messages.size()];
            GetMessagesResponse response = new GetMessagesResponse(messages.toArray(tempArray));

            return response;
        } catch (Exception e) {
            return null;
        }
    }

    private class GetMessagesRequest {
        private Long conversationId;
        private int nMessages;
        private int startIndex;
        private long sinceWhen;

        public Long getConversationId() {
            return conversationId;
        }

        public void setConversationId(Long conversationId) {
            this.conversationId = conversationId;
        }

        public int getnMessages() {
            return nMessages;
        }

        public void setnMessages(int nMessages) {
            this.nMessages = nMessages;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        public long getSinceWhen() {
            return sinceWhen;
        }

        public void setSinceWhen(long sinceWhen) {
            this.sinceWhen = sinceWhen;
        }
    }

    private class Message {
        private String content;
        private long contentType;
        private long messageID;
        private long timeSent;
        private String sender;

        public Message(String content, long contentType, long messageId, long timeSent, String sender) {
            this.content = content;
            this.contentType = contentType;
            this.messageID = messageId;
            this.timeSent = timeSent;
            this.sender = sender;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Long getMessageId() {
            return messageID;
        }

        public void setMessageId(Long messageId) {
            this.messageID = messageId;
        }

        public long getTimeSent() {
            return timeSent;
        }

        public void setTimeSent(long timeSent) {
            this.timeSent = timeSent;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public long getContentType() {
            return contentType;
        }

        public void setContentType(long contentType) {
            this.contentType = contentType;
        }
    }

    public class GetMessagesResponse {
        private Message[] messageList;

        public GetMessagesResponse(Message[] messageList) {
            this.messageList = messageList;
        }

        public Message[] getMessages() {
            return messageList;
        }

        public void setMessages(Message[] messageList) {
            this.messageList = messageList;
        }
    }
}
