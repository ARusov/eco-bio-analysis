package org.vsu.eco.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vsu.eco.model.ParameterH;
import org.vsu.eco.model.Point;
import org.vsu.eco.model.Project;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arusov on 4/8/14.
 */
@Service
@Transactional
public class ProjectDao {

    @PersistenceContext
    private EntityManager em;

    public void saveOrUpdate(Project project) {
        if (project.getId() > 0) {
            em.merge(project);
        } else {
            em.persist(project);
        }
        em.flush();
    }

    public Project getProject(long id) {
        Query q = em.createQuery("from Project where id=:id").setParameter("id", id);
        List<Project> projects = q.getResultList();
        if (projects != null && projects.size() > 0) {
            return projects.get(0);
        }
        em.flush();
        return null;
    }

    public ParameterH getH(long id) {
        Query q = em.createQuery("from ParameterH where id=:id").setParameter("id", id);
        List<ParameterH> h = q.getResultList();
        if (h != null && h.size() > 0) {
            return h.get(0);
        }
        em.flush();
        return null;
    }

    public List<Project> getProjects() {
        Query q = em.createQuery("from Project");
        em.flush();
        return q.getResultList();
    }

    public void addParameter(ParameterH h) {
        if (h.getId() > 0) {
            em.merge(h);
        } else {
            em.persist(h);
        }
        em.flush();
    }

    public void addPoint(Point point) {
        if (point.getId() > 0) {
            em.merge(point);
        } else {
            em.persist(point);
        }
        System.out.println("Point was added "+point.getId());
        em.flush();
    }

    public List<ParameterH> getHforPoint(Point p) {
        List<ParameterH> result = new ArrayList<ParameterH>();
        if (p != null) {
            Query q = em.createQuery("from ParameterH where point=:p order by id").setParameter("p", p);
            return q.getResultList();
        }
        em.flush();
        return result;
    }

    public List<Point> getPoints(Project p) {
        List<Point> result = new ArrayList<Point>();
        if (p != null) {
            Query q = em.createQuery("from Point where project=:p order by id").setParameter("p", p);
            return q.getResultList();
        }
        em.flush();
        return result;
    }

    public void removeParamH(ParameterH h) {
        if (h != null) {
            Query q = em.createQuery("delete from ParameterH where id=:id").setParameter("id", h.getId());
            q.executeUpdate();
        }
        em.flush();
    }

    public void removePoint(Point point) {
        if (point != null) {
//             q = em.createQuery("delete from ParameterH where point=:point").setParameter("point", point);
//            q.executeUpdate();
            Query q = em.createQuery("delete from Point where id=:id").setParameter("id", point.getId());
            q.executeUpdate();
            System.out.println(point.getId());
        }
    }
}
