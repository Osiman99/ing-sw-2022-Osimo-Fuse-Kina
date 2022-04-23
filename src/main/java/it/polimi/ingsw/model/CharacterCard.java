package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class CharacterCard {

    private Game game;
    private CharacterName characterName;
    private List<Student> students;
    private boolean banCards[];
    private int price;

    public CharacterCard(CharacterName characterName){
        game = GameExpert.getInstance();
        this.characterName = characterName;

        switch (characterName){
            case Sommelier:
                price = 1;
                students = new ArrayList<Student>();
                for (int i = 0; i < 4; i++) {
                    students.add(game.getBoard().getBag().getFirstStudent());
                    game.getBoard().getBag().removeStudent();
                }
                break;
            case Chef:
                price = 2;
                break;
            case Messenger:
                price = 3;
                break;
            case Postman:
                price = 1;
                break;
            case Herbalist:
                price = 2;
                banCards = new boolean[4];
                for (int i = 0; i < 4; i++) {
                    banCards[i] = true;
                }
                break;
            case Centaur:
                price = 3;
                break;
            case Joker:
                price = 1;
                students = new ArrayList<Student>();
                for (int i = 0; i < 6; i++) {
                    students.add(game.getBoard().getBag().getFirstStudent());
                    game.getBoard().getBag().removeStudent();
                }
                break;
            case Knight:
                price = 2;
                break;
            case Merchant:
                price = 3;
                break;
            case Musician:
                price = 1;
                break;
            case Lady:
                price = 2;
                students = new ArrayList<Student>();
                for (int i = 0; i < 4; i++) {
                    students.add(game.getBoard().getBag().getFirstStudent());
                    game.getBoard().getBag().removeStudent();
                }
                break;
            case Sinister:
                price = 3;
                break;


        }
    }

}
