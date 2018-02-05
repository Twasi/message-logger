package net.twasiplugin.messagelogger;

import net.twasi.core.database.Database;
import net.twasi.core.database.models.User;
import net.twasi.core.logger.TwasiLogger;
import net.twasiplugin.messagelogger.model.Message;
import net.twasiplugin.messagelogger.model.MessageCollection;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.Date;

class MessageStore {

    static void logNew(User user, String twitchidFrom, String message) {
        Message messageObj = new Message(twitchidFrom, message, new Date());
        UpdateOperations<MessageCollection> ops = Database
                .getStore()
                .createUpdateOperations(MessageCollection.class)
                .push("messages", messageObj);
        Query<MessageCollection> query = Database
                .getStore()
                .createQuery(MessageCollection.class)
                .field("user").equal(user);

        Database.getStore().update(query, ops);
    }

    static void verifyUserExist(User user) {
        Query<MessageCollection> query = Database
                .getStore()
                .createQuery(MessageCollection.class)
                .field("user").equal(user);

        if (Database.getStore().getCount(query) == 0) {
            MessageCollection collection = new MessageCollection(user);
            Database.getStore().save(collection);
            TwasiLogger.log.debug("Empty MessageCollection created for user " + user);
        }
    }

}
