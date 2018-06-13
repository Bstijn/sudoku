package Server;

import Logic.Cell;

import javax.websocket.Session;

public interface IServerWS {
    void sendFillToOthers(Session session,int Number, Cell cell);

    void sendInvalidToFiller(Session session);
}
