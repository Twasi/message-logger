package net.twasiplugin.messagelogger;

import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.events.TwasiInstallEvent;
import net.twasi.core.plugin.api.events.TwasiMessageEvent;

public class MessageLoggerUser extends TwasiUserPlugin {

    @Override
    public void onInstall(TwasiInstallEvent e) {
        MessageStore.verifyUserExist(e.getUserPlugin().getTwasiInterface().getStreamer().getUser());
    }

    @Override
    public void onMessage(TwasiMessageEvent e) {
        MessageStore.logNew(e.getTwasiInterface().getTwasiInterface().getStreamer().getUser(), e.getMessage().getSender().getTwitchId(), e.getMessage().getMessage());
    }
}
