package it.polimi.ingsw.network.messages;

import java.awt.*;

public class CreateMatchRequest extends Message{

    CreateMatchRequest(String nickname) {
        super(nickname, MessageType.CREATE_MATCH_REQUEST);
    }
}
