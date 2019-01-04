package net.twasiplugin.dependency.messagelogger;

import net.twasi.core.events.TwasiEventHandler;
import net.twasi.core.interfaces.api.TwasiInterface;
import net.twasi.core.interfaces.events.IncomingMessageEvent;
import net.twasi.core.models.Message.TwasiMessage;
import net.twasi.core.plugin.TwasiDependency;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DataService;
import net.twasi.core.services.providers.InstanceManagerService;
import net.twasiplugin.dependency.messagelogger.database.LoggedMessage;
import net.twasiplugin.dependency.messagelogger.database.MessageLoggerRepository;

import java.util.Calendar;

public class MessageLoggerDependency extends TwasiDependency {

    @Override
    public void onReady() {
        InstanceManagerService instanceManager = ServiceRegistry.get(InstanceManagerService.class);
        for (TwasiInterface twasiInterface : instanceManager.getInterfaces()) {
            startLogging(twasiInterface);
        }
    }

    private void startLogging(TwasiInterface twasiInterface) {
        MessageLoggerRepository repository = ServiceRegistry.get(DataService.class).get(MessageLoggerRepository.class);
        twasiInterface.getMessageReader().registerIncomingMessageHandler(new TwasiEventHandler<IncomingMessageEvent>() {
            @Override
            public void on(IncomingMessageEvent incomingMessageEvent) {
                TwasiMessage message = incomingMessageEvent.getMessage();
                LoggedMessage messageEntity = new LoggedMessage(
                        message.getTwasiInterface().getStreamer().getUser(),
                        message.getSender().getTwitchId(),
                        message.getMessage(),
                        Calendar.getInstance().getTime()
                );
                repository.commit(messageEntity);
            }
        });
    }

}
