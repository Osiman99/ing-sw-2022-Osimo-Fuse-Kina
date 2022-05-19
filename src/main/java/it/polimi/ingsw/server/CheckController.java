package it.polimi.ingsw.server;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.model.Game;

import java.util.Map;

public class CheckController {

    private static final long serialVersionUID = 7413156215358698632L;

    private final Game game;
    private transient Map<String, VirtualView> virtualViewMap;
    private final GameController gameController;

    /**
     * Constructor of the Input Controller Class.
     *
     * @param virtualViewMap Virtual View Map.
     * @param gameController Game Controller.
     */
    public CheckController(Map<String, VirtualView> virtualViewMap, GameController gameController) {
        game = Game.getInstance();
        this.virtualViewMap = virtualViewMap;
        this.gameController = gameController;
    }


    public boolean checkLoginNickname(String nickname, View view) {
        if (nickname.isEmpty() || nickname.equalsIgnoreCase(Game.SERVER_NICKNAME)) {
            view.showGenericMessage("Forbidden name.");
            view.showLoginResult(false, true, null);
            return false;
        } else if (game.isNicknameTaken(nickname)) {
            view.showGenericMessage("Nickname already taken.");
            view.showLoginResult(false, true, null);
            return false;
        }
        return true;
    }






}
