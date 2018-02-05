package net.twasiplugin.messagelogger.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

public class Message {
    /**
     * Unique ID of the message
     */
    @Id
    private ObjectId id;

    private String twitchId;
    private String message;
    private Date date;

    public Message() {};
    public Message(String twitchId, String message, Date date) {
        this.twitchId = twitchId;
        this.message = message;
        this.date = date;
    }

    public ObjectId getId() {
        return id;
    }

    public String getTwitchId() {
        return twitchId;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
