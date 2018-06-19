package Server;

import Logic.Cell;
import com.google.gson.JsonObject;

import javax.websocket.Session;

public interface IServerWS {
    void sendFillToOthers(Session session,int Number, JsonObject cellJson);

    void sendInvalidToFiller(Session session);
}
