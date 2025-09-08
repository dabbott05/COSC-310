package chap8.courses;

import java.util.ArrayList;

import chap8.users.Faculty; // Assuming Faculty exists
import chap8.users.Student;

public class Section {
    private Term term;              // Changed to Term object
    private String time;
    private String room;
    private int number;
    private Course course;
    private Faculty professor;      // Changed to Faculty object
    private ArrayList<Student> classlist;

    public Section(Term term, String time, String room, /*int number,*/ Course course, Faculty professor, ArrayList<Student> classlist) {
        this.term = term;
        this.time = time;
        this.room = room;
        this.number = 001;          // Hardcoded for simplicity; could be parameterized
        this.course = course;
        this.professor = professor;
        this.classlist = classlist != null ? new ArrayList<>(classlist) : new ArrayList<>();
    }

    public void enrollStudent(Student student) {
        if (!classlist.contains(student)) {
            classlist.add(student);
        }
    }

    public void unenrollStudent(Student student) {
        classlist.remove(student);
    }

    public Term getTerm() { return term; }
    public Course getCourse() { return course; }
    public ArrayList<Student> getClasslist() { return classlist; }

    @Override
    public String toString() {
        return course.getSubject() + " " + course.getNumber() + " Section " + number + " (" + term + ")";
    }
}