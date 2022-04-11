package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        List<String> nicknames = new ArrayList<String>();
        Game game;

        nicknames.add("davide");
        nicknames.add("riise");
        nicknames.add("elis");
        game = new Game();
        game.initGame(game, nicknames, 3);
        for(int i = 0; i < 3; i++) {
            System.out.println(game.getPlayers().get(i).getNickname());
        }
    }
}
