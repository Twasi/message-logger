package net.twasiplugin.dependency.messagelogger;

import net.twasi.core.events.NewInstanceEvent;
import net.twasi.core.events.TwasiEventHandler;
import net.twasi.core.interfaces.api.TwasiInterface;
import net.twasi.core.interfaces.events.IncomingMessageEvent;
import net.twasi.core.logger.TwasiLogger;
import net.twasi.core.models.Message.TwasiMessage;
import net.twasi.core.plugin.TwasiDependency;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DataService;
import net.twasi.core.services.providers.InstanceManagerService;
import net.twasiplugin.dependency.messagelogger.database.entities.LoggedUserMessage;
import net.twasiplugin.dependency.messagelogger.database.repositories.MessageLoggerRepository;

import java.util.Calendar;

public class MessageLoggerDependency extends TwasiDependency {

    @Override
    public void onActivate() {
        InstanceManagerService instanceManager = ServiceRegistry.get(InstanceManagerService.class);
        instanceManager.registerNewInstanceHandler(new TwasiEventHandler<NewInstanceEvent>(){
            @Override
            public void on(NewInstanceEvent newInstanceEvent) {
                startLogging(newInstanceEvent.getTwasiInterface());
                TwasiLogger.log.debug("Message logger registered for user " + newInstanceEvent.getTwasiInterface().getStreamer().getUser().getId());
            }
        });
    }

    private void startLogging(TwasiInterface twasiInterface) {
        MessageLoggerRepository repository = ServiceRegistry.get(DataService.class).get(MessageLoggerRepository.class);
        twasiInterface.getMessageReader().registerIncomingMessageHandler(new TwasiEventHandler<IncomingMessageEvent>() {
            @Override
            public void on(IncomingMessageEvent incomingMessageEvent) {
                TwasiMessage message = incomingMessageEvent.getMessage();
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
        });
    }

}
