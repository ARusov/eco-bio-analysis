package org.vsu.eco.view.project;

import org.jdesktop.swingx.VerticalLayout;
import org.springframework.context.ApplicationContext;
import org.vsu.eco.dao.ProjectDao;
import org.vsu.eco.model.*;
import org.vsu.eco.model.Point;
import org.vsu.eco.service.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import java.util.List;

/**
 * Created by arusov on 3/31/14.
 */
public class ProjectPanel extends JPanel {

    private ApplicationContext applicationContext;

    private JTextField projectNameField;

    private JButton saveButton;

    private JButton closeButton;

    private JButton addPointButton;

    private JScrollPane scrollPane = new JScrollPane();

    private JPanel pointsPanel;

    private Project project;

    public ProjectPanel(ApplicationContext applicationContext, Project project) {
        super(new GridBagLayout());
        this.applicationContext = applicationContext;
        this.project=project;
        init();
    }

    private void init() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 0.1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(getProjectNameField(), c);
        c.gridy = 1;
        c.gridheight = 3;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.BOTH;
        scrollPane.setViewportView(getPointsPanel());
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, c);
        c.weighty = 0;
        c.weightx = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 4;
        c.gridx = 3;
        c.gridwidth = 1;
        add(getSaveButton(), c);
        c.gridy = 5;
        c.gridx = 4;
        add(getCloseButton(), c);

        if (project!=null){
            List<Point> points=getProjectDao().getPoints(project);
            for (Point point: points){
                    addPointPane(point);
            }
            getProjectNameField().setText(project.getName());
        }

    }

    private JButton getAddPointButton() {
        if (addPointButton == null) {
            addPointButton = new JButton("Добавить точку");
            addPointButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   Point point= new Point();
                   point.setProject(project);
                   getProjectDao().addPoint(point);
                   addPointPane(point);

                }
            });
        }
        return addPointButton;
    }

    private void addPointPane(Point point){
        PointPane pointPane = new PointPane(applicationContext,point);
        getPointsPanel().add(pointPane, Util.getComponentIndex(getAddPointButton()));
        scrollPane.repaint();
        scrollPane.validate();
        getPointsPanel().repaint();
        getPointsPanel().validate();
    }
    private JTextField getProjectNameField() {
        if (projectNameField == null) {
            projectNameField = new JTextField("Имя проекта");
            projectNameField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {

                }

                @Override
                public void focusLost(FocusEvent e) {
                    project.setName(projectNameField.getText());
                    getProjectDao().saveOrUpdate(project);
                }
            });
        }
        return projectNameField;
    }

    private JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new JButton("Сохранить");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    saveProject();
                }
            });
        }

        return saveButton;
    }


    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton("Закрыть");
        }
        return closeButton;
    }

    private JPanel getPointsPanel() {
        if (pointsPanel == null) {
            pointsPanel = new JPanel();
            pointsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            pointsPanel.setLayout(new VerticalLayout());
            pointsPanel.add(getAddPointButton());
        }
        return pointsPanel;
    }


    private ProjectDao getProjectDao(){
        return (ProjectDao) applicationContext.getBean("projectDao");
    }

    private void  saveProject(){
        project.setName(projectNameField.getText());
        getProjectDao().saveOrUpdate(project);
    }


}

