package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SommelierStrategy implements CharacterCardsStrategy{
    private Game game;
    private String name;
    private List<Student> students;
    private int price;
    private Scanner input;
    private String string;

    public SommelierStrategy(){
        game = GameExpert.getInstance();
        input = new Scanner(System.in);
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
    public void applyEffect(Player player) {
        string = input.next();
        if (string.equals("green")) {
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getColor() == StudentColor.GREEN){  //è lungo da fare, bisogna chiedere anche l'isola con standard input
                }
            }
        }
    }
}
