package net.twasiplugin.dependency.messagelogger;

import net.twasi.core.plugin.TwasiDependency;
import net.twasi.core.plugin.api.TwasiUserPlugin;

public class MessageLoggerDependency extends TwasiDependency {
    @Override
    public Class<? extends TwasiUserPlugin> getUserPluginClass() {
        return MessageLogger.class;
    }
}
