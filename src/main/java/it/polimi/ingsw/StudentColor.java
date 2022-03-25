package it.polimi.ingsw;

public enum StudentColor {
    RED(0), GREEN(1), BLUE(2), YELLOW(3), PINK(4);

    int studentColorCode;

    StudentColor(int studentColorCode) {
        this.studentColorCode = studentColorCode;
    }

    public int getStudentColorCode(){
        return this.studentColorCode;
    }
    public int getStudentColor(int studentColorCode){
        return StudentColor.this.studentColorCode;
    }
}
