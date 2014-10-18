package org.vsu.eco.view;

import org.springframework.context.ApplicationContext;
import org.vsu.eco.dao.ProjectDao;
import org.vsu.eco.model.Project;
import org.vsu.eco.service.Util;
import org.vsu.eco.view.project.ProjectFrame;
import org.vsu.eco.view.project.ProjectPanel;
import org.vsu.eco.view.taxon.TaxonPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by arusov on 3/9/14.
 */
public class MainForm extends JFrame {

    private JMenuBar menu;

    private TaxonPanel taxonPanel;

    private ProjectPanel projectPanel;

    private ProjectFrame projectFrame;


    private ApplicationContext applicationContext;

    public MainForm(ApplicationContext applicationContext) throws HeadlessException {
        super("Эколого-биологическое качество поверхностных вод");
        this.applicationContext = applicationContext;
        init();
        add(getTaxonPanel());


    }

    private void init() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setJMenuBar(getMenu());

    }

    private JMenuBar getMenu() {
        if (menu == null) {
            menu = new JMenuBar();
            menu.add(getFileMenuItem());

        }
        return menu;
    }

    private JMenu getFileMenuItem() {
        JMenu menu = new JMenu("Файл");
        menu.add(getProjectMenuItem());
        menu.add(getTaxonMenuItem());
        menu.add(getCloseMenuItem());
        return menu;
    }

    private JMenuItem getCloseMenuItem() {
        JMenuItem menuItem = new JMenuItem("Закрыть");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return menuItem;
    }

    private JMenu getProjectMenuItem() {
        JMenu menu = new JMenu("Проекты");
        menu.add(getCreateProjectMenuItem());
        menu.add(getOpenProjectMenuItem());
        return menu;
    }

    private JMenuItem getCreateProjectMenuItem() {
        JMenuItem menuItem = new JMenuItem("Создать проект");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideAllPanels();
                Project project= new Project();
                getProjectDao().saveOrUpdate(project);
                add(getProjectPanel(project));
                repaint();
                validate();
            }
        });
        return menuItem;
    }

    private JMenuItem getOpenProjectMenuItem() {
        JMenuItem menuItem = new JMenuItem("Открыть проект");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getProjectFrame().setVisible(true);

            }
        });
        return menuItem;
    }

    private JMenuItem getTaxonMenuItem() {
        JMenuItem menuItem = new JMenuItem("Таксоны");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideAllPanels();
                add(getTaxonPanel());
                repaint();
                validate();
            }
        });
        return menuItem;
    }

    public void hideAllPanels() {
        Component[] components = getContentPane().getComponents();
        for (Component c : components) {
            if (c instanceof JPanel) {
                remove(c);
                repaint();
            }
        }
    }

    private TaxonPanel getTaxonPanel() {
        if (taxonPanel == null) {
            taxonPanel = new TaxonPanel(applicationContext);
        }
        return taxonPanel;
    }

    public ProjectPanel getProjectPanel(Project project) {
        return new ProjectPanel(applicationContext, project);
    }


    private ProjectFrame getProjectFrame() {
        if (projectFrame == null) {
            projectFrame = new ProjectFrame(applicationContext, this);
        }
        return projectFrame;
    }
    private ProjectDao getProjectDao(){
        return (ProjectDao) applicationContext.getBean("projectDao");
    }


}
