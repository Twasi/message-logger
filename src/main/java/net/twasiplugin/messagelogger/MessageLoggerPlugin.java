package net.twasiplugin.messagelogger;

import net.twasi.core.database.Database;
import net.twasi.core.plugin.TwasiPlugin;
import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasiplugin.messagelogger.model.Message;
import net.twasiplugin.messagelogger.model.MessageCollection;

public class MessageLoggerPlugin extends TwasiPlugin {

    @Override
    public void onActivate() {
        // Register entities
        Database.getMorphia().mapPackageFromClass(Message.class);
        Database.getMorphia().mapPackageFromClass(MessageCollection.class);
    }

    public Class<? extends TwasiUserPlugin> getUserPluginClass() {
        return MessageLoggerUser.class;
    }
}
