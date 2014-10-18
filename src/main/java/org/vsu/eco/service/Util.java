package org.vsu.eco.service;

import org.vsu.eco.model.*;
import org.vsu.eco.model.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by arusov on 4/2/14.
 */
public class Util {
    public static final int getComponentIndex(Component component) {
        if (component != null && component.getParent() != null) {
            Container c = component.getParent();
            for (int i = 0; i < c.getComponentCount(); i++) {
                if (c.getComponent(i) == component)
                    return i;
            }
        }

        return -1;
    }
    public static void getComponentTreePosition(Component c, ArrayList pos) {
        if (c.getParent() == null) {
            return;
        }

        getComponentTreePosition(c.getParent(), pos);

        pos.add(new Integer(c.getParent().getComponentCount() - getComponentIndex(c)));
    }


    public static Project getTestProject(){
        Project project= new Project();
        project.setName("Test project");
        Set<Point> points= new HashSet<Point>();
        Point point= new Point();
        point.setName("test point1");
        points.add(point);
        project.setPoints(points);
        return  project;
    }
}
