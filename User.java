package chap8.users;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable, Comparable<User> {

    protected String name;
    protected String id; // nine #
    protected String username;
    protected String password;

    public User() {
        // nothing to do here
    }

    public User(String name, String id, String username, String password) {
        this.name = name;
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String name) {
        this(name, "901010103", "", "");
    }

    @Override
    public String toString() {
        return String.format("%s - %s: 9xxxx%s", getType(), name, id.length()>6?id.substring(5):id);
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password; 
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int compareTo(User other) {
        return this.getName().compareTo(other.getName());
    }

    public static ArrayList<Faculty> getFaculty(ArrayList<User> allusers) {
        ArrayList<Faculty> facultymembers = new ArrayList<>();
        for (User u : allusers) {
            if (u.getType().equals("FACULTY")) {
                facultymembers.add((Faculty)u);
            }
        }
        return facultymembers;
    }

    public static ArrayList<Student> getStudents(ArrayList<User> allusers) {
        ArrayList<Student> students = new ArrayList<>();
        for (User u : allusers) {
            if (u.getType().equals("STUDENT")) {
                students.add((Student)u);
            }
        }
        return students;
    }

    abstract public String getType();
}