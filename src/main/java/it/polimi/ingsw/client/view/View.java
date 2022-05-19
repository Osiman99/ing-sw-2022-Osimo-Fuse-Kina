package it.polimi.ingsw.client.view;

import it.polimi.ingsw.network.messages.Message;

public interface View {

    /**
     * The player has to choose a nickname.
     */
    void askNickname();

    /**
     * choose the players number.
     */
    void askPlayersNumber();

    void askAssistantCard();

    /**
     * Shows to the user if the Login succeeded.
     *
     * @param nicknameAccepted     indicates if the chosen nickname has been accepted.
     * @param connectionResult indicates if the connection has been successful.
     * @param nickname             the nickname of the player to be greeted.
     */
    void showLoginResult(boolean nicknameAccepted, boolean connectionResult, String nickname);

    void showGenericMessage(String message);
}
