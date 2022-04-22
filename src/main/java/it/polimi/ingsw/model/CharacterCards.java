package it.polimi.ingsw.model;

public abstract class CharacterCards {
    private Sommelier sommelier;
    private Game game;

    public CharacterCards(){
        game = GameExpert.getInstance();
        sommelier = Sommelier.getInstance();
    }

    public abstract String getName();

    public abstract void applyEffect(); //da non implementare qui, le altre apply invece si

    public void applyEffect(Student student, Island island){
        for(int i = 0; i < 4; i++){
            if(sommelier.getStudents().get(i).getColor() == student.getColor()){
                island.addStudent(sommelier.getStudents().get(i));
                sommelier.getStudents().remove(i);
                sommelier.getStudents().add(game.getBoard().getBag().getFirstStudent());
                game.getBoard().getBag().removeStudent();
            }
        }
    }

    public void applyEffect(Island island) {
        game.getBoard().calculateSupremacy(island);
    }

    public void applyEffect(Player player, int numMoves){
        for (int i = 0; i < player.getDeck().getDeck().size(); i++) {
            if (player.getDeck().getDeck().get(i).getValue() == player.getChosenCardValue()) {
                if (numMoves <= (player.getDeck().getDeck().get(i).getMaxMoves() + 2) && numMoves > 0){
                    game.getBoard().moveMotherNature(numMoves);
                }//else throw exception;
                //remove AssistantCard
                break;
            }
        }
    }


}
