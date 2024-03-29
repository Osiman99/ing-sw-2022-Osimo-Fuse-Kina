package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CharacterCard implements Serializable {

    private static final long serialVersionUID = -5098098867984135994L;
    private Game game;
    private CharacterName characterName;
    private List<Student> students;
    private boolean banCards[];
    private int price;
    private boolean enabled;
    private boolean hasStudents = false;
    private String description;

    /**
     * class constructor, takes in input the character name and initializes it
     * @param characterName
     */
    public CharacterCard(CharacterName characterName){
        game = GameExpert.getInstance();
        this.characterName = characterName;

        switch (characterName){
            case Sommelier:
                price = 1;
                students = new ArrayList<Student>();
                hasStudents = true;
                for (int i = 0; i < 4; i++) {
                    students.add(game.getBoard().getBag().getFirstStudent());
                    game.getBoard().getBag().removeStudent();
                }
                break;
            case Chef, Knight:
                price = 2;
                break;
            case Messenger, Centaur, Merchant, Sinister:
                price = 3;
                break;
            case Postman, Musician:
                price = 1;
                break;
            case Herbalist:
                price = 2;
                banCards = new boolean[4];
                for (int i = 0; i < 4; i++) {
                    banCards[i] = true;
                }
                break;
            case Joker:
                price = 1;
                students = new ArrayList<Student>();
                hasStudents = true;
                for (int i = 0; i < 6; i++) {
                    students.add(game.getBoard().getBag().getFirstStudent());
                    game.getBoard().getBag().removeStudent();
                }
                break;
            case Lady:
                price = 2;
                students = new ArrayList<Student>();
                hasStudents = true;
                for (int i = 0; i < 4; i++) {
                    students.add(game.getBoard().getBag().getFirstStudent());
                    game.getBoard().getBag().removeStudent();
                }
                break;
        }
    }

    public boolean hasStudents() {
        return hasStudents;
    }

    public CharacterName getCharacterName() {
        return characterName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Student> getStudents() {
        return students;
    }

    public boolean[] getBanCards() { return banCards;}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
