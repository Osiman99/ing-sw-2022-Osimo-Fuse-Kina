package it.polimi.ingsw;

public class StudentRow {
    private Student[] students;
    private StudentColor rowColor;

    /**
     * Default constructor
     *
     * @param students
     * @param rowColor
     */
    public StudentRow(Student[] students, StudentColor rowColor) {
        this.students = students;
        this.rowColor = rowColor;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }

    public StudentColor getRowColor() {
        return rowColor;
    }

    public void setRowColor(StudentColor rowColor) {
        this.rowColor = rowColor;
    }
}
