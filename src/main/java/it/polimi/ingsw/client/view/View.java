package it.polimi.ingsw.client.view;

public interface View {

    /**
     * Asks the user to choose a Nickname.
     */
    void askNickname();

    /**
     * Asks the user how many players he wants to play with.
     */
    void askPlayersNumber();
}
