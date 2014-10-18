package org.vsu.eco.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by arusov on 4/2/14.
 */
@Entity
@Table(name = "point")
public class Point {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "point", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<ParameterH> parameterH = new HashSet<ParameterH>();

    public static String[] getColumnNames() {
        return new String[]{"ID", "Таксон", "h", "S", "x", "o", "b", "a", "p", "x", "o", "b", "a", "p", "s", "sh"};
    }

    public Set<ParameterH> getParameterH() {
        return parameterH;
    }

    public void setParameterH(Set<ParameterH> parameterH) {
        this.parameterH = parameterH;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
