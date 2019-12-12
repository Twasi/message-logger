package net.twasiplugin.messagelogger;

import net.twasi.core.plugin.TwasiPlugin;
import net.twasi.core.plugin.api.TwasiUserPlugin;

public class MessageLoggerPlugin extends TwasiPlugin {
    @Override
    public Class<? extends TwasiUserPlugin> getUserPluginClass() {
        return MessageLogger.class;
    }
}
