package chap8.Degreeworks;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import chap8.courses.Course;
import chap8.courses.Section;
import chap8.courses.Term;
import chap8.users.Faculty;
import chap8.users.Student;
import chap8.users.User;

public class Main {
    private static ArrayList<Course> allcourses = new ArrayList<>();
    private static ArrayList<Term> allterms = new ArrayList<>();
    private static ArrayList<Section> allsections = new ArrayList<>();
    private static ArrayList<User> allusers = new ArrayList<>();
    private static ArrayList<Student> students = new ArrayList<>();
    private static Random random = new Random();

    private static JList<Course> courseList;
    private static JList<Term> termList;
    private static JList<Section> sectionList;
    private static JList<Student> studentList;
    private static DefaultListModel<Course> courseModel;
    private static DefaultListModel<Term> termModel;
    private static DefaultListModel<Section> sectionModel;
    private static DefaultListModel<Student> studentModel;

    private static String[] schedules = {"MWF 9:00-10:00", "TTh 13:00-14:30", "MWF 14:00-15:00"};
    private static String[] rooms = {"Sci 101", "Math 202", "Engr 305"};

    private static void loadUsers() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream fi = new FileInputStream(chooser.getSelectedFile());
                ObjectInputStream si = new ObjectInputStream(fi);
                ArrayList<User> tmpusers = (ArrayList<User>) si.readObject();
                allusers.clear();
                for (User u : tmpusers) {
                    allusers.add(u);
                }
                si.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected static void populateData() {
        ArrayList<Faculty> facultyMembers = User.getFaculty(allusers);
        ArrayList<Student> allStudents = User.getStudents(allusers);
        students.addAll(allStudents);

        ArrayList<Term> terms = new ArrayList<>();
        String[] termNames = {"Spring", "Summer", "Fall", "Winter"};
        for (int year = 2024; year <= 2026; year++) {
            for (String termName : termNames) {
                terms.add(new Term(termName, String.valueOf(year)));
            }
        }

        ArrayList<Course> courses = new ArrayList<>(Arrays.asList(
                new Course("COSC", "101", "Introduction to Computer Science", 3),
                new Course("COSC", "210", "Data Structures", 4),
                new Course("COSC", "310", "Advanced Programming Concepts", 4),
                new Course("MATH", "120", "Calculus I", 4),
                new Course("MATH", "220", "Discrete Mathematics", 3),
                new Course("MATH", "320", "Linear Algebra", 3),
                new Course("PHYS", "101", "General Physics I", 4),
                new Course("PHYS", "102", "General Physics II", 4),
                new Course("ENGR", "150", "Engineering Design", 3),
                new Course("ENGR", "250", "Thermodynamics", 3),
                new Course("CHEM", "101", "General Chemistry", 4),
                new Course("BIO", "110", "Cell Biology", 3),
                new Course("ECON", "200", "Principles of Economics", 3),
                new Course("HIST", "101", "World History", 3),
                new Course("PSYC", "101", "Introduction to Psychology", 3),
                new Course("SOC", "205", "Sociology of Organizations", 3),
                new Course("PHIL", "210", "Ethics in Technology", 3),
                new Course("STAT", "350", "Probability and Statistics", 3),
                new Course("ART", "101", "Art History", 3),
                new Course("MUS", "105", "Music Theory", 3)));

        allterms.addAll(terms);
        allcourses.addAll(courses);

        for (int i = 0; i < 50; i++) {
            Term term = terms.get(random.nextInt(terms.size()));
            Course course = courses.get(random.nextInt(courses.size()));
            Faculty instructor = facultyMembers.get(random.nextInt(facultyMembers.size()));
            ArrayList<Student> enrolledStudents = getSeveralRandomStudents(allStudents, 10 + random.nextInt(6));
            Section section = new Section(term, schedules[random.nextInt(schedules.length)],
                    rooms[random.nextInt(rooms.length)], course, instructor, enrolledStudents);
            allsections.add(section);
        }
    }

    private static ArrayList<Student> getSeveralRandomStudents(ArrayList<Student> allStudents, int count) {
        ArrayList<Student> result = new ArrayList<>();
        ArrayList<Student> copy = new ArrayList<>(allStudents);
        for (int i = 0; i < count && !copy.isEmpty(); i++) {
            result.add(copy.remove(random.nextInt(copy.size())));
        }
        return result;
    }

    private static void setupGUI() {
        JFrame frame = new JFrame("DegreeWorks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Reduced width since layout is more vertical
        frame.setLayout(new BorderLayout());

        // Top Panel - Using GridBagLayout for vertical organization
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Enroll/Unenroll Section
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(new JLabel("Student:"), gbc);
        gbc.gridx = 1;
        JComboBox<Student> studentCombo = new JComboBox<>(students.toArray(new Student[0]));
        topPanel.add(studentCombo, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        JButton enrollButton = new JButton("Enroll");
        topPanel.add(enrollButton, gbc);
        gbc.gridx = 1;
        JButton unenrollButton = new JButton("Unenroll");
        topPanel.add(unenrollButton, gbc);

        // Add Course Section
        gbc.gridx = 0;
        gbc.gridy = 2;
        topPanel.add(new JLabel("Subject:"), gbc);
        gbc.gridx = 1;
        JTextField subjectField = new JTextField(10);
        topPanel.add(subjectField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        topPanel.add(new JLabel("Number:"), gbc);
        gbc.gridx = 1;
        JTextField numberField = new JTextField(10);
        topPanel.add(numberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        topPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        JTextField titleField = new JTextField(10);
        topPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        topPanel.add(new JLabel("Credits:"), gbc);
        gbc.gridx = 1;
        JTextField creditsField = new JTextField(10);
        topPanel.add(creditsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Span both columns
        JButton addCourseButton = new JButton("Add Course");
        topPanel.add(addCourseButton, gbc);
        gbc.gridwidth = 1; // Reset

        // Add Section Section
        gbc.gridx = 0;
        gbc.gridy = 7;
        topPanel.add(new JLabel("Faculty:"), gbc);
        gbc.gridx = 1;
        JComboBox<Faculty> facultyCombo = new JComboBox<>(User.getFaculty(allusers).toArray(new Faculty[0]));
        topPanel.add(facultyCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        JButton addSectionButton = new JButton("Add Section");
        topPanel.add(addSectionButton, gbc);

        // Bottom Panel with 4 Lists
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4));

        // Course List
        courseModel = new DefaultListModel<>();
        for (Course c : allcourses) courseModel.addElement(c);
        courseList = new JList<>(courseModel);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bottomPanel.add(new JScrollPane(courseList));

        // Term List
        termModel = new DefaultListModel<>();
        for (Term t : allterms) termModel.addElement(t);
        termList = new JList<>(termModel);
        termList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bottomPanel.add(new JScrollPane(termList));

        // Section List
        sectionModel = new DefaultListModel<>();
        for (Section s : allsections) sectionModel.addElement(s);
        sectionList = new JList<>(sectionModel);
        sectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bottomPanel.add(new JScrollPane(sectionList));

        // Student List
        studentModel = new DefaultListModel<>();
        for (Student s : students) studentModel.addElement(s);
        studentList = new JList<>(studentModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bottomPanel.add(new JScrollPane(studentList));

        // Add panels to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.CENTER);

        // List Selection Listeners
        courseList.addListSelectionListener(e -> updateSections());
        termList.addListSelectionListener(e -> updateSections());
        sectionList.addListSelectionListener(e -> updateStudents());

        // Button Actions
        enrollButton.addActionListener(e -> {
            Section selectedSection = sectionList.getSelectedValue();
            Student selectedStudent = (Student) studentCombo.getSelectedItem();
            if (selectedSection != null && selectedStudent != null) {
                selectedSection.enrollStudent(selectedStudent);
                updateStudents();
                JOptionPane.showMessageDialog(frame, selectedStudent.getName() + " enrolled in " + selectedSection);
            }
        });

        unenrollButton.addActionListener(e -> {
            Section selectedSection = sectionList.getSelectedValue();
            Student selectedStudent = (Student) studentCombo.getSelectedItem();
            if (selectedSection != null && selectedStudent != null) {
                selectedSection.unenrollStudent(selectedStudent);
                updateStudents();
                JOptionPane.showMessageDialog(frame, selectedStudent.getName() + " unenrolled from " + selectedSection);
            }
        });

        addCourseButton.addActionListener(e -> {
            try {
                String subject = subjectField.getText().trim();
                String number = numberField.getText().trim();
                String title = titleField.getText().trim();
                int credits = Integer.parseInt(creditsField.getText().trim());
                if (!subject.isEmpty() && !number.isEmpty() && !title.isEmpty()) {
                    Course newCourse = new Course(subject, number, title, credits);
                    allcourses.add(newCourse);
                    courseModel.addElement(newCourse);
                    subjectField.setText("");
                    numberField.setText("");
                    titleField.setText("");
                    creditsField.setText("");
                    JOptionPane.showMessageDialog(frame, "Course added: " + newCourse);
                } else {
                    JOptionPane.showMessageDialog(frame, "All fields are required!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Credits must be a number!");
            }
        });

        addSectionButton.addActionListener(e -> {
            Course selectedCourse = courseList.getSelectedValue();
            Term selectedTerm = termList.getSelectedValue();
            Faculty selectedFaculty = (Faculty) facultyCombo.getSelectedItem();
            if (selectedCourse != null && selectedTerm != null && selectedFaculty != null) {
                Section newSection = new Section(selectedTerm, schedules[random.nextInt(schedules.length)],
                        rooms[random.nextInt(rooms.length)], selectedCourse, selectedFaculty, new ArrayList<>());
                allsections.add(newSection);
                sectionModel.addElement(newSection);
                updateSections();
                JOptionPane.showMessageDialog(frame, "Section added: " + newSection);
            } else {
                JOptionPane.showMessageDialog(frame, "Select a course, term, and faculty!");
            }
        });

        frame.setVisible(true);
    }

    private static void updateSections() {
        Course selectedCourse = courseList.getSelectedValue();
        Term selectedTerm = termList.getSelectedValue();
        sectionModel.clear();
        for (Section s : allsections) {
            boolean matchesCourse = selectedCourse == null || s.getCourse().equals(selectedCourse);
            boolean matchesTerm = selectedTerm == null || s.getTerm().equals(selectedTerm);
            if (matchesCourse && matchesTerm) {
                sectionModel.addElement(s);
            }
        }
    }

    private static void updateStudents() {
        Section selectedSection = sectionList.getSelectedValue();
        studentModel.clear();
        if (selectedSection != null) {
            for (Student s : selectedSection.getClasslist()) {
                studentModel.addElement(s);
            }
        } else {
            for (Student s : students) {
                studentModel.addElement(s);
            }
        }
    }

    public static void main(String[] args) {
        loadUsers();
        if (allusers.isEmpty()) {
            System.out.println("No users loaded. Exiting.");
            return;
        }
        populateData();
        SwingUtilities.invokeLater(Main::setupGUI);
    }
}