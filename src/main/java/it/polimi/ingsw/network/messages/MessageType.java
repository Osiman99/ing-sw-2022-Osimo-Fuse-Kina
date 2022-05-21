package it.polimi.ingsw.network.messages;

/**
 * This enum contains all the message type available and used by the server and clients.
 */
public enum MessageType {
    LOGIN_REQUEST, LOGIN_REPLY,
    PLAYERNUMBER_REQUEST,
    PLAYERNUMBER_REPLY,
    LOBBY,
    ASSISTANTCARD_REQUEST,
    ASSISTANTCARD_RESULT,
    CHOOSE_FIRST_PLAYER,
    BOARD,
    INIT_COLORS,
    MOVE,
    MOVE_FX,
    WIN,
    WIN_FX,
    LOSE,

    //utility:
    GAME_LOAD,
    MATCH_INFO,
    DISCONNECTION,
    GENERIC_MESSAGE,
    PING,
    ERROR,
    ENABLE_EFFECT,
    APPLY_EFFECT,
    PERSISTENCE
}
