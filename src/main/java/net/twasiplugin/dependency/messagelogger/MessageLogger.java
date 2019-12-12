package net.twasiplugin.dependency.messagelogger;

import net.twasi.core.logger.TwasiLogger;
import net.twasi.core.models.Message.TwasiMessage;
import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.events.TwasiMessageEvent;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DataService;
import net.twasiplugin.dependency.messagelogger.database.entities.LoggedUserMessage;
import net.twasiplugin.dependency.messagelogger.database.repositories.MessageLoggerRepository;

import java.util.Calendar;

public class MessageLogger extends TwasiUserPlugin {
    MessageLoggerRepository repository = ServiceRegistry.get(DataService.class).get(MessageLoggerRepository.class);

    @Override
    public void onMessage(TwasiMessageEvent e) {
        TwasiMessage message = e.getMessage();
        TwasiLogger.log.debug("New message from user " + message.getSender().getUserName() + " in chat " + message.getTwasiInterface().getStreamer().getUser().getTwitchAccount().getUserName() + ": " + message.getMessage());
        LoggedUserMessage messageEntity = new LoggedUserMessage(
                message.getTwasiInterface().getStreamer().getUser(),
                message.getSender().getTwitchId(),
                message.getSender().getDisplayName(),
                Calendar.getInstance().getTime(),
                message.getMessage()
        );
        repository.add(messageEntity);
        repository.commitAll();
    }
}
