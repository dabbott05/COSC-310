package chap8.Banner;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import chap8.users.Admin;
import chap8.users.Faculty;
import chap8.users.Staff;
import chap8.users.Student;
import chap8.users.User;

public class UserPanel extends JPanel {
    private JTextField nameBox;
    private JTextField idBox;
    private JTextField usernameBox;
    private JTextField passwordBox;
    private JComboBox<String> typeDropdown;
    private JButton addButton;
    private JButton updateButton;
    // DECLARE A DELETE BUTTON HERE
    private JButton deleteButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton sortButton;
    private UserListPanel userListPanel;
    private ArrayList<User> allusers;
    private User selectedUser; // the current user being edited if the user has selected a user from the users list
    private boolean reverseSort = false;

    public UserPanel(UserListPanel userListPanel, ArrayList<User> allusers) {
        this.userListPanel = userListPanel;
        this.allusers = allusers;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Name: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nameBox = new JTextField(15);
        add(nameBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("ID (9#): "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        idBox = new JTextField(15);
        add(idBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Username: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        usernameBox = new JTextField(15);
        add(usernameBox, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Password: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        passwordBox = new JTextField(15);
        add(passwordBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Type: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        typeDropdown = new JComboBox<>(Main.userTypes);
        add(typeDropdown, gbc);


        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        sortButton = new JButton("Sort");
        add(sortButton, gbc);
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Collections.sort(allusers);
                if (reverseSort) {
                    java.util.Collections.reverse(allusers);
                }
                reverseSort = !reverseSort;
                userListPanel.updateUserList(allusers);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        addButton = new JButton("Add User");
        updateButton = new JButton("Update User");
        updateButton.setVisible(false);
        // INITIALIZE AND HIDE THE DELETE BUTTON HERE
        deleteButton = new JButton("Delete User");
        deleteButton.setVisible(false);
        saveButton = new JButton("Save Users");
        loadButton = new JButton("Load Users");
        buttonPanel.add(addButton, gbc);
        buttonPanel.add(updateButton, gbc);
        // ADD THE HIDDEN DELETE BUTTON TO THE PANEL
        buttonPanel.add(deleteButton, gbc);
        buttonPanel.add(saveButton, gbc);
        buttonPanel.add(loadButton, gbc);
        add(buttonPanel, gbc);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUser();
            }
        });
        // REGISTER A HANDLER FOR THE DELETE BUTTON HERE
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUsers();
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadUsers();
                userListPanel.updateUserList(allusers);
            }
        });
        
    }

    private void saveUsers() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                FileOutputStream fo = new FileOutputStream(chooser.getSelectedFile());
                ObjectOutputStream so = new ObjectOutputStream(fo);
                so.writeObject(allusers);
                so.flush();
                so.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void loadUsers() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
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

    private void addUser() {
        String name = nameBox.getText();
        String type = (String) typeDropdown.getSelectedItem();
        String username = usernameBox.getText();
        String password = passwordBox.getText();
        if (!name.isEmpty() && type != null) {
            if (type.equals("Student")) {
                allusers.add(new Student(name, type, username, password));
            } else if (type.equals("Faculty")) {
                allusers.add(new Faculty(name, type, username, password));
            } else if (type.equals("Staff")) {
                allusers.add(new Staff(name, type, username, password));
            } else if (type.equals("Admin")) {
                allusers.add(new Admin(name, type, username, password));
            }
            nameBox.setText("");
            idBox.setText("");
            usernameBox.setText("");
            passwordBox.setText("");
            userListPanel.updateUserList(allusers);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a name and select a type.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // b/c we have a reference to the User stored in our global allusers array, we can just update the User object directly instead of having to mess with some kind of index into the list
    // BUT that update doesn't happen until the user clicks the update button ... so we need to save a reference to the selected user so we can update it later in our updateUser method
    public void loadUser(User u) {
        this.selectedUser = u;
        sortButton.setVisible(false);
        deleteButton.setVisible(true);
        addButton.setVisible(false);
        updateButton.setVisible(true);
        loadButton.setVisible(false);
        // MAKE THE DELETE BUTTON VISIBLE
        nameBox.setText(u.getName());
        idBox.setText(u.getId());
        usernameBox.setText(u.getUsername());
        passwordBox.setText(u.getPassword());
        typeDropdown.setSelectedItem(u.getType());

    }

    // b/c we have a reference to the User currently being edited, we can just update the User object directly instead of having to mess with some kind of index into the list
    public void updateUser() {
        this.selectedUser.setName(nameBox.getText());
        this.selectedUser.setId(idBox.getText());

        // finish updating the user below!
        // add your code here


        this.selectedUser.setUsername(usernameBox.getText());
        this.selectedUser.setPassword(passwordBox.getText());



        // now that we have updated the user, let's tell the userListPanel to update the list
        userListPanel.updateUserList(allusers);
        // restore the UI to prepare it for adding a new user
        sortButton.setVisible(true);
        addButton.setVisible(true);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);
        // HIDE THE DELETE BUTTON TOO!

        nameBox.setText("");
        idBox.setText("");
        usernameBox.setText("");
        passwordBox.setText("");
        selectedUser = null;
    }

     // finish this deleteUser method AND call it from the delete button's action listener which you also need to add alongside the other action listeners
    public void deleteUser() {
        allusers.remove(selectedUser);
        userListPanel.updateUserList(allusers);
    
            // Reset UI
        sortButton.setVisible(true);
        addButton.setVisible(true);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);
    
        nameBox.setText("");
        idBox.setText("");
        usernameBox.setText("");
        passwordBox.setText("");
    
        selectedUser = null;
        }
    }
    
