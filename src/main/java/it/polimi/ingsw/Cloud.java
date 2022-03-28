package it.polimi.ingsw;

public class Cloud {

    private Student[] students;

    // if(Player1, Player2) {students = new Student[3]}
    // if(Player1, Player2, Player 3) {students = new Student[4]}

    /**
     * Default constructor
     *
     * @param students
     */
    public Cloud(Student[] students) {
        this.students = students;
    }

    /**
     * Setter
     *
     * @param students
     */
    public void setStudents(Student[] students) {
        this.students = students;
    }

    //public void moveStudentsToEntrance(Player player){}
}
