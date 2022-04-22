package it.polimi.ingsw.model;

public class Chef extends CharacterCards{

    private String name;
    private Game game;
    private int price;

    public Chef(){
        game = GameExpert.getInstance();
        name = "Chef";
    }

    public String getName() {
        return name;
    }

    @Override
    public void applyEffect() {
        if (game.getNumPlayers() == 2) {
            for (int i = 0; i < 5; i++) {
                if (game.getPlayers().get(0).getPlank().getDiningRoom()[i].getStudents().size() >= game.getPlayers().get(1).getPlank().getDiningRoom()[i].getStudents().size()) {
                    game.getBoard().getProfessorsControlledBy()[i] = game.getPlayers().get(0).getNickname();
                } else if (game.getPlayers().get(1).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(0).getPlank().getDiningRoom()[i].getStudents().size()) {
                    game.getBoard().getProfessorsControlledBy()[i] = game.getPlayers().get(1).getNickname();
                }
            }
        } else if (game.getNumPlayers() == 3) {
            for (int i = 0; i < 5; i++) {
                if (game.getPlayers().get(0).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(1).getPlank().getDiningRoom()[i].getStudents().size() && game.getPlayers().get(0).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(2).getPlank().getDiningRoom()[i].getStudents().size()) {
                    game.getBoard().getProfessorsControlledBy()[i] = game.getPlayers().get(0).getNickname();
                } else if (game.getPlayers().get(1).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(0).getPlank().getDiningRoom()[i].getStudents().size() && game.getPlayers().get(1).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(2).getPlank().getDiningRoom()[i].getStudents().size()) {
                    game.getBoard().getProfessorsControlledBy()[i] = game.getPlayers().get(1).getNickname();
                } else if (game.getPlayers().get(2).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(0).getPlank().getDiningRoom()[i].getStudents().size() && game.getPlayers().get(2).getPlank().getDiningRoom()[i].getStudents().size() > game.getPlayers().get(1).getPlank().getDiningRoom()[i].getStudents().size()) {
                    game.getBoard().getProfessorsControlledBy()[i] = game.getPlayers().get(2).getNickname();
                }
            }
        }
    }
}
