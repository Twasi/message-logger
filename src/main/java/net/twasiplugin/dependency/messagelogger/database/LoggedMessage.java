package net.twasiplugin.dependency.messagelogger.database;

import net.twasi.core.database.models.BaseEntity;
import net.twasi.core.database.models.User;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;

@Entity(value = "messagelogger", noClassnameStored = true)
public class LoggedMessage extends BaseEntity {

    @Reference
    private User user;

    private String messageWriterTwitchId;

    private Date timestamp;

    private String message;

    public LoggedMessage() {
    }

    public LoggedMessage(User user, String messageWriterTwitchId, String message, Date timestamp){
        this.user = user;
        this.messageWriterTwitchId = messageWriterTwitchId;
        this.message = message;
        this.timestamp = timestamp;
    }

}
