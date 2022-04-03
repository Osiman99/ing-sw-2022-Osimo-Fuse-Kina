package it.polimi.ingsw.model;

import java.util.List;

public class StudentRow {
    private List<Student> students;
    private StudentColor rowColor;


    /**
     * Default constructor
     *
     * @param students
     * @param rowColor
     */
    public StudentRow(List<Student> students, StudentColor rowColor) {
        this.students = students;
        this.rowColor = rowColor;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public StudentColor getRowColor() {
        return rowColor;
    }

    public void setRowColor(StudentColor rowColor) {
        this.rowColor = rowColor;
    }

    public void addStudent(Student student){
        students.add(student);
    }
}
