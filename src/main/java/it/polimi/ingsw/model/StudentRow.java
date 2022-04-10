package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class StudentRow {
    private List<Student> students;
    private StudentColor rowColor;    //ROWCOLOR INUTILE



    public StudentRow(int code) {
        students = new ArrayList<Student>();
        rowColor = StudentColor.getStudentColor(code);   //ROWCOLOR INUTILE
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
