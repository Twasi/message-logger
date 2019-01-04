package net.twasiplugin.dependency.messagelogger.database.entities;

import net.twasi.core.database.models.BaseEntity;
import net.twasi.core.database.models.User;
import net.twasiplugin.dependency.messagelogger.database.LoggedMessageType;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;

@Entity(value = "messagelogger", noClassnameStored = true)
public abstract class LoggedMessageBase extends BaseEntity {

    @Reference
    private User user;

    private Date timestamp;

    private String message;

    private LoggedMessageType messageType;

    public LoggedMessageBase(User user, Date timestamp, String message, LoggedMessageType type) {
        this.user = user;
        this.timestamp = timestamp;
        this.message = message;
        this.messageType = type;
    }

}

