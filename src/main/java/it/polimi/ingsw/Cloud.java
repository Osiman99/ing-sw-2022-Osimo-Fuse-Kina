package it.polimi.ingsw;

public class Cloud {

    private Student[] students;

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
