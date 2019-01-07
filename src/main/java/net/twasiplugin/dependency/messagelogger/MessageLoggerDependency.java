package net.twasiplugin.dependency.messagelogger;

import net.twasi.core.events.IncomingMessageEvent;
import net.twasi.core.events.NewInstanceEvent;
import net.twasi.core.events.TwasiEventHandler;
import net.twasi.core.interfaces.api.TwasiInterface;
import net.twasi.core.logger.TwasiLogger;
import net.twasi.core.models.Message.TwasiMessage;
import net.twasi.core.plugin.TwasiDependency;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DataService;
import net.twasi.core.services.providers.InstanceManagerService;
import net.twasiplugin.dependency.messagelogger.database.repositories.MessageLoggerRepository;

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
                TwasiMessage twasiMessage = incomingMessageEvent.getMessage();
            }
        });
        twasiInterface.getCommunicationHandler().registerOutgoingMessageHandler(null);
    }

}
