package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentRow implements Serializable {

    private static final long serialVersionUID = -67899734586104788L;
    private List<Student> students;
    private StudentColor rowColor;    //ROWCOLOR INUTILE


    /**
     * class constructor
     * @param code
     */
    public StudentRow(int code) {
        students = new ArrayList<Student>();
        rowColor = StudentColor.getStudentColor(code);   //ROWCOLOR INUTILE
    }

    /**
     * getters and setters
     */

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
    }    //ROWCOLOR INUTILE

    public void addStudent(Student student){
        students.add(student);
    }
}
