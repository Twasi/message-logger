package net.twasiplugin.dependency.messagelogger.database.entities;

import net.twasi.core.database.models.User;
import net.twasiplugin.dependency.messagelogger.database.LoggedMessageType;

import java.util.Date;

public class LoggedUserMessage extends LoggedMessageBase {

    private String senderTwitchId;
    private String senderDisplayName;

    public LoggedUserMessage(User user, String senderTwitchId, String senderDisplayName, Date timestamp, String message) {
        super(user, timestamp, message, LoggedMessageType.USERMESSAGE);
        this.senderTwitchId = senderTwitchId;
        this.senderDisplayName = senderDisplayName;
    }

}
