package it.polimi.ingsw;

public class StudentRow {
    private StudentRow[] students = new StudentRow[10];
    private StudentColor rowColor;

    /**
     * Default constructor
     *
     * @param students
     * @param rowColor
     */
    public StudentRow(StudentRow[] students, StudentColor rowColor) {
        this.students = students;
        this.rowColor = rowColor;
    }

    public StudentRow[] getStudents() {
        return students;
    }

    public void setStudents(StudentRow[] students) {
        this.students = students;
    }

    public StudentColor getRowColor() {
        return rowColor;
    }

    public void setRowColor(StudentColor rowColor) {
        this.rowColor = rowColor;
    }
}
