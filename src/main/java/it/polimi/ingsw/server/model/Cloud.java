package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cloud implements Serializable {

    private static final long serialVersionUID = 6633940502260451770L;

    private List<Student> students;

    /**
     * cloud constructor
     */
    public Cloud(){
        students = new ArrayList<Student>();
    }

    public int getStudentsSize(){
        return students.size();
    }

    public Student getFirstStudent(){
        return students.get(0);
    }

    public void removeStudent(){
        students.remove(0);
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    /**
     * @return true if the cloud is empty, false if it's not.
     */
    public boolean isEmpty(){
        if (students.size() == 0){
            return true;
        }else{
            return false;
        }
    }
}
