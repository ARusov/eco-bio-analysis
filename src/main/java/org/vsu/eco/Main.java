package org.vsu.eco;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vsu.eco.view.MainForm;

/**
 * Created by arusov on 3/9/14.
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        MainForm mainFrom = new MainForm(applicationContext);
        mainFrom.setVisible(true);

    }

}
