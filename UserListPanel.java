
package chap8.Banner;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import chap8.users.User;

public class UserListPanel extends JPanel {
    private JList<String> userList;
    private DefaultListModel<String> listModel;
    private ArrayList<User> allusers;
    public UserPanel userPanel;

    public UserListPanel(ArrayList<User> allusers) {
        this.allusers = allusers;
        setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        userList = new JList<>(listModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userList);
        add(scrollPane, BorderLayout.CENTER);
        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // if we clear out the list we don't want to do anything
                // selectedIndex is -1 when the list is empty
                if(!e.getValueIsAdjusting() && userList.getSelectedIndex() != -1) {
                    loadUser();
                }
            }
        });
    }

    public void loadUser() {
        User u = allusers.get(userList.getSelectedIndex());
        userPanel.loadUser(u);
        System.out.println(userList.getSelectedValue());
    }

    public void updateUserList(ArrayList<User> users) {
        userList.clearSelection();
        listModel.clear();
        for (User user : users) {
            listModel.addElement(user.toString());
        }
    }
}
    