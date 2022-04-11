package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class SommelierStrategy implements CharacterCardsStrategy{
    public Game game;
    public String name;
    public List<Student> students;
    public int price;

    public SommelierStrategy(){
        game = GameExpert.getInstance();
        name = "Sommelier";
        students = new ArrayList<Student>();
        for (int i = 0; i < 4; i++) {
            students.add(game.getBoard().getBag().getFirstStudent());
            game.getBoard().getBag().removeStudent();
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {

    }
}
