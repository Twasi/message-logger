package net.twasiplugin.dependency.messagelogger;

import net.twasi.core.events.IncomingMessageEvent;
import net.twasi.core.events.NewInstanceEvent;
import net.twasi.core.events.OutgoingMessageEvent;
import net.twasi.core.events.TwasiEventHandler;
import net.twasi.core.interfaces.api.TwasiInterface;
import net.twasi.core.logger.TwasiLogger;
import net.twasi.core.models.Message.TwasiMessage;
import net.twasi.core.plugin.TwasiDependency;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DataService;
import net.twasi.core.services.providers.InstanceManagerService;
import net.twasiplugin.dependency.messagelogger.database.entities.LoggedBotMessage;
import net.twasiplugin.dependency.messagelogger.database.entities.LoggedUserMessage;
import net.twasiplugin.dependency.messagelogger.database.repositories.MessageLoggerRepository;

import java.util.Calendar;
import java.util.HashMap;

public class MessageLoggerDependency extends TwasiDependency {

    @Override
    public void onActivate() {
        InstanceManagerService instanceManager = ServiceRegistry.get(InstanceManagerService.class);
        instanceManager.registerNewInstanceHandler(new TwasiEventHandler<NewInstanceEvent>() {
            @Override
            public void on(NewInstanceEvent newInstanceEvent) {
                startLogging(newInstanceEvent.getTwasiInterface());
                TwasiLogger.log.debug("Message logger registered for user " + newInstanceEvent.getTwasiInterface().getStreamer().getUser().getId());
            }
        });
    }

    private HashMap<TwasiMessage, LoggedUserMessage> messageCache = new HashMap<>();

    private void startLogging(TwasiInterface twasiInterface) {
        MessageLoggerRepository repository = ServiceRegistry.get(DataService.class).get(MessageLoggerRepository.class);
        twasiInterface.getMessageReader().registerIncomingMessageHandler(new TwasiEventHandler<IncomingMessageEvent>() {
            @Override
            public void on(IncomingMessageEvent incomingMessageEvent) {
                TwasiMessage twasiMessage = incomingMessageEvent.getMessage();
                LoggedUserMessage msg = new LoggedUserMessage(
                        twasiInterface.getStreamer().getUser(),
                        twasiMessage.getSender().getTwitchId(),
                        twasiMessage.getSender().getDisplayName(),
                        Calendar.getInstance().getTime(),
                        twasiMessage.getMessage()
                );
                repository.add(msg);
                repository.commitAll();
                messageCache.put(twasiMessage, msg);
            }
        });
        twasiInterface.getCommunicationHandler().registerOutgoingMessageHandler(new TwasiEventHandler<OutgoingMessageEvent>() {
            @Override
            public void on(OutgoingMessageEvent outgoingMessageEvent) {
                LoggedBotMessage msg;
                if (messageCache.containsKey(outgoingMessageEvent.getReplyTo())){
                    msg = new LoggedBotMessage(
                            twasiInterface.getStreamer().getUser(),
                            Calendar.getInstance().getTime(),
                            outgoingMessageEvent.getMessage(),
                            messageCache.get(outgoingMessageEvent.getReplyTo())
                    );
                } else {
                    msg = new LoggedBotMessage(
                            twasiInterface.getStreamer().getUser(),
                            Calendar.getInstance().getTime(),
                            outgoingMessageEvent.getMessage()
                    );
                }
                repository.add(msg);
                repository.commitAll();
            }
        });
    }

}
