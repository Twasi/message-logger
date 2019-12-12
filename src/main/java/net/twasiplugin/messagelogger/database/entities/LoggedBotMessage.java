package net.twasiplugin.messagelogger.database.entities;

import jdk.nashorn.internal.ir.annotations.Reference;
import net.twasi.core.database.models.User;
import net.twasiplugin.messagelogger.database.LoggedMessageType;

import java.util.Date;

public class LoggedBotMessage extends LoggedMessageBase {

    @Reference
    private LoggedUserMessage inReplyTo;

    public LoggedBotMessage(User user, Date timestamp, String message) {
        super(user, timestamp, message, LoggedMessageType.BOTMESSAGE);
        this.inReplyTo = null;
    }

    public LoggedBotMessage(User user, Date timestamp, String message, LoggedUserMessage inReplyTo) {
        super(user, timestamp, message, LoggedMessageType.BOTMESSAGE);
        this.inReplyTo = inReplyTo;
    }
}
