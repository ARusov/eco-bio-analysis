package org.vsu.eco.view.project;

import org.springframework.context.ApplicationContext;
import org.vsu.eco.dao.ProjectDao;
import org.vsu.eco.model.Project;
import org.vsu.eco.view.MainForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by arusov on 4/9/14.
 */
public class ProjectFrame extends JFrame {

    private ApplicationContext applicationContext;

    private JTable projectTable;

    private JScrollPane scrollPane;

    private MainForm parent;

    public ProjectFrame(ApplicationContext applicationContext, MainForm parent) throws HeadlessException {
        super("Выбор проекта");
        this.applicationContext = applicationContext;
        this.parent=parent;
        setSize(400, 400);
        setLayout(new GridLayout(1, 1));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        add(getScrollPane());

    }

    private JTable getProjectTable() {
        if(projectTable==null){
            projectTable= new JTable();


            final NonEditableModel  tableModel= new NonEditableModel();
            projectTable.setModel(tableModel);
            tableModel.setColumnIdentifiers(new String[]{"ID","Название проекта"});
            projectTable.getColumnModel().getColumn(0).setMaxWidth(50);
            projectTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JTable table =(JTable) e.getSource();
                    Point p = e.getPoint();
                    int row = table.rowAtPoint(p);
                    if (e.getClickCount() == 2) {
                        long id= (Long) tableModel.getValueAt(projectTable.getSelectedRow(),0);
                        Project project=getProjectDao().getProject(id);
                        System.out.println(project.getId());
                        if(p!=null){
                            parent.hideAllPanels();
                            parent.add(parent.getProjectPanel(project));
                            parent.repaint();
                            parent.validate();
                        }
                       setVisible(false);
                    }
                }
            });
            List<Project> projects=getProjectDao().getProjects();
            if(projectTable!=null){
                for(Project p:projects){
                    tableModel.addRow(new Object[]{p.getId(),p.getName()});
                }
            }
        }
        return projectTable;
    }

    private JScrollPane getScrollPane(){
        if(scrollPane==null){
            scrollPane= new JScrollPane(getProjectTable());
        }
        return scrollPane;
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            //TODO: update table;
        }
        super.setVisible(b);
    }

    private ProjectDao getProjectDao() {
        return (ProjectDao) applicationContext.getBean("projectDao");
    }

    class NonEditableModel extends DefaultTableModel{
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

}
