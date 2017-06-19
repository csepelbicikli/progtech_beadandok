package swingschooljpa.gui;

import swingschooljpa.gui.tablemodels.TeacherTableModel;
import swingschooljpa.gui.tablemodels.SubjectTableModel;
import swingschooljpa.gui.tablemodels.SubjTeachTableModel;
import swingschooljpa.gui.tablemodels.StudentTableModel;
import swingschooljpa.gui.tablemodels.MarkTableModel;
import swingschooljpa.gui.tablemodels.GrouppTableModel;
import hu.elte.DateCellEditor;
import hu.elte.inf.prt.swing.tablemodels.AbstractJpaEntityTableModel;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import swingschooljpa.gui.comboboxmodels.SchoolEntityComboBoxModel;
import swingschooljpa.logic.DataSource;

public class SwingSchoolFrame extends JFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new SwingSchoolFrame().setVisible(true);
        });
    }

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private final JTable subjectTable, studentTable, teacherTable, grouppTable, subjTeachTable, markTable;
    private final SubjectTableModel subjectTableModel;
    private final TeacherTableModel teacherTableModel;
    private final StudentTableModel studentTableModel;
    private final GrouppTableModel grouppTableModel;
    private final SubjTeachTableModel subjTeachTableModel;
    private final MarkTableModel markTableModel;
    private final JTabbedPane jTabbedPane;

    public SwingSchoolFrame() {
        try {
            DataSource.getInstance().getEntityManagerFactory().createEntityManager().close();
        } catch (Exception ex) {
            showError(ex.getMessage());
            System.exit(1);
        }

        setTitle("School");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        SchoolMenuBar jMenuBar = new SchoolMenuBar();
        setJMenuBar(jMenuBar);
        jTabbedPane = new JTabbedPane();
        getContentPane().add(jTabbedPane, BorderLayout.CENTER);
        jTabbedPane.addChangeListener(jMenuBar);

        subjectTableModel = new SubjectTableModel();
        subjectTable = new JTable(subjectTableModel);
        subjectTable.setAutoCreateRowSorter(true);
        jTabbedPane.addTab("Subjects", new JScrollPane(subjectTable));

        studentTableModel = new StudentTableModel();
        studentTable = new JTable(studentTableModel);
        studentTable.setAutoCreateRowSorter(true);
        studentTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JComboBox(new SchoolEntityComboBoxModel(DataSource.getInstance().getGrouppController()))));
        jTabbedPane.addTab("Students", new JScrollPane(studentTable));

        teacherTableModel = new TeacherTableModel();
        teacherTable = new JTable(teacherTableModel);
        teacherTable.setAutoCreateRowSorter(true);
        jTabbedPane.add("Teachers", new JScrollPane(teacherTable));

        grouppTableModel = new GrouppTableModel();
        grouppTable = new JTable(grouppTableModel);
        grouppTable.setAutoCreateRowSorter(true);
        jTabbedPane.add("Groups", new JScrollPane(grouppTable));

        subjTeachTableModel = new SubjTeachTableModel();
        subjTeachTable = new JTable(subjTeachTableModel);
        subjTeachTable.setAutoCreateRowSorter(true);
        subjTeachTable.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JComboBox(new SchoolEntityComboBoxModel(DataSource.getInstance().getTeacherController()))));
        subjTeachTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox(new SchoolEntityComboBoxModel(DataSource.getInstance().getSubjectController()))));
        subjTeachTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JComboBox(new SchoolEntityComboBoxModel(DataSource.getInstance().getGrouppController()))));
        jTabbedPane.add("Group assignments", new JScrollPane(subjTeachTable));

        markTableModel = new MarkTableModel();
        markTable = new JTable(markTableModel);
        markTable.setAutoCreateRowSorter(true);
        markTable.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JComboBox(new SchoolEntityComboBoxModel(DataSource.getInstance().getStudentController()))));
        markTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox(new SchoolEntityComboBoxModel(DataSource.getInstance().getSubjectController()))));
        markTable.getColumnModel().getColumn(2).setCellEditor(new DateCellEditor());
        jTabbedPane.add("Marks", new JScrollPane(markTable));
    }

    private final Action addGrouppAction = new AbstractAction("Add new group") {

        @Override
        public void actionPerformed(ActionEvent e) {
            grouppTableModel.addNewEntity();
        }
    };
    private final Action addMarkAction = new AbstractAction("Add new mark") {

        @Override
        public void actionPerformed(ActionEvent e) {
            markTableModel.addNewEntity();
        }
    };
    private final Action addStudentAction = new AbstractAction("Add new student") {

        @Override
        public void actionPerformed(ActionEvent e) {
            studentTableModel.addNewEntity();
        }
    };
    private final Action addSubjTeachAction = new AbstractAction("Add new group assignment") {

        @Override
        public void actionPerformed(ActionEvent e) {
            subjTeachTableModel.addNewEntity();
        }
    };
    private final Action addSubjectAction = new AbstractAction("Add new subject") {

        @Override
        public void actionPerformed(ActionEvent e) {
            subjectTableModel.addNewEntity();
        }
    };
    private final Action addTeacherAction = new AbstractAction("Add new teacher") {

        @Override
        public void actionPerformed(ActionEvent e) {
            teacherTableModel.addNewEntity();
        }
    };

    private final Action deleteGroupsAction = new AbstractAction("Delete selected groups") {

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteRowsFromTable(grouppTable, grouppTableModel);
        }
    };

    private final Action deleteMarksAction = new AbstractAction("Delete selected marks") {

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteRowsFromTable(markTable, markTableModel);
        }
    };

    private final Action deleteStudentsAction = new AbstractAction("Delete selected students") {

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteRowsFromTable(studentTable, studentTableModel);
        }
    };

    private final Action deleteSubjTeachAction = new AbstractAction("Delete selected group assignments") {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteRowsFromTable(subjTeachTable, subjTeachTableModel);
        }
    };
    
    private final Action deleteSubjectsAction = new AbstractAction("Delete selected subjects") {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteRowsFromTable(subjectTable, subjectTableModel);
        }
    };
    
    private final Action deleteTeachersAction = new AbstractAction("Delete selected teachers") {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteRowsFromTable(teacherTable, teacherTableModel);
        }
    };
    
    private void deleteRowsFromTable(JTable table, AbstractJpaEntityTableModel tableModel) {
        int[] selectedRows = table.getSelectedRows();
        ArrayList<Integer> rowIndicesList = new ArrayList<>(selectedRows.length);
        for (int i = 0; i < selectedRows.length; i++) {
            int selectedRowIdx = selectedRows[i];
            rowIndicesList.add(table.convertRowIndexToModel(selectedRowIdx));
        }
        Collections.sort(rowIndicesList);
        Collections.reverse(rowIndicesList);
        for (int i = 0; i < rowIndicesList.size(); i++) {
            Integer rowIndex = rowIndicesList.get(i);
            tableModel.deleteEntity(rowIndex);
        }
    }

    private class SchoolMenuBar extends JMenuBar implements ChangeListener {

        private final JMenu subjectsMenu, studentsMenu, teachersMenu, grouppsMenu, subjTeachsMenu, marksMenu;

        public SchoolMenuBar() {
            subjectsMenu = new JMenu("Subjects");
            studentsMenu = new JMenu("Students");
            teachersMenu = new JMenu("Teachers");
            grouppsMenu = new JMenu("Groups");
            subjTeachsMenu = new JMenu("Group assignments");
            marksMenu = new JMenu("Marks");
            add(subjectsMenu);

            grouppsMenu.add(addGrouppAction);
            grouppsMenu.add(deleteGroupsAction);
            marksMenu.add(addMarkAction);
            marksMenu.add(deleteMarksAction);
            studentsMenu.add(addStudentAction);
            studentsMenu.add(deleteStudentsAction);
            subjTeachsMenu.add(addSubjTeachAction);
            subjTeachsMenu.add(deleteSubjTeachAction);
            subjectsMenu.add(addSubjectAction);
            subjectsMenu.add(deleteSubjectsAction);
            teachersMenu.add(addTeacherAction);
            teachersMenu.add(deleteTeachersAction);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            removeAll();
            repaint();
            switch (jTabbedPane.getTitleAt(jTabbedPane.getSelectedIndex())) {
                case "Subjects":
                    add(subjectsMenu);
                    break;
                case "Students":
                    add(studentsMenu);
                    break;
                case "Teachers":
                    add(teachersMenu);
                    break;
                case "Groups":
                    add(grouppsMenu);
                    break;
                case "Group assignments":
                    add(subjTeachsMenu);
                    break;
                case "Marks":
                    add(marksMenu);
                    break;
            }
        }
    }
}
