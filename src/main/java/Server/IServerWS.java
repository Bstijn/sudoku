package Server;

import Logic.Cell;

import javax.websocket.Session;

public interface IServerWS {
    void sendFillToOthers(int Number, Cell cell);

    void sendInvalidToFiller(Session session);
}
