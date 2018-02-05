package net.twasiplugin.messagelogger.model;

import net.twasi.core.database.models.User;
import net.twasi.core.models.Message.TwasiMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

public class MessageCollection {
    /**
     * Unique id of the collection
     */
    @Id
    private ObjectId id;
    
    /**
     * List of messages that belong to the user
     */
    private List<TwasiMessage> messages;

    /**
     * User this collection refers to
     */
    @Reference
    private User user;

    public MessageCollection() {}
    public MessageCollection(User user) {
        this.user = user;
    }

    public ObjectId getId() {
        return id;
    }

    public List<TwasiMessage> getMessages() {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        return messages;
    }

    public User getUser() {
        return user;
    }
}
