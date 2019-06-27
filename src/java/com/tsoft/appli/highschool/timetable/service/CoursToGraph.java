/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.appli.highschool.timetable.service;

import com.tsoft.appli.highschool.service.AnneeService;
import com.tsoft.appli.highschool.timetable.graphColoring.Constants;
import com.tsoft.appli.highschool.timetable.graphColoring.Graph;
import com.tsoft.appli.highschool.timetable.model.Cours;
import com.tsoft.appli.highschool.timetable.model.CoursOccurence;
import com.tsoft.dao.hibernate.service.HibernateServiceWrapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class CoursToGraph {

    @Autowired
    HibernateServiceWrapper service;
    @Autowired
    AnneeService as;

    private List<Cours> courss = new ArrayList();
    private List<CoursOccurence> coursoccurences = new ArrayList();

    public void CreateCoursOccurence(Cours c) {
        for (int i = 0; i < c.getDuree_heures(); i++) {
            CoursOccurence co = new CoursOccurence();
            co.setCours(c);
            co.setNum_occurence(Short.valueOf(i+""));
            coursoccurences.add(co);
        }
    }

    public Graph readGraph() throws Exception {
        String query = "select t from Cours t where t.annee.code=" + as.getAnneeCourante().getCode() + "";
        courss = service.getAll(Cours.class, query);
        //Creation des nodes
        coursoccurences.clear();
        for (Cours c : courss) {
            CreateCoursOccurence(c);
        }
        Constants.NUMBER_NODES = coursoccurences.size();

        Graph graph = new Graph();
        
        //creation des aretes
        for(CoursOccurence co:coursoccurences){
            for(CoursOccurence co1:coursoccurences){
                if(co.getCours().getProfesseur().equals(co1.getCours().getProfesseur())){
                    graph.addEdge(coursoccurences.indexOf(co), coursoccurences.indexOf(co1));
                }
                if(co.getCours().getClasse().equals(co1.getCours().getClasse())){
                    graph.addEdge(coursoccurences.indexOf(co), coursoccurences.indexOf(co1));
                }
            }
        }
//        for (Cours c : courss) {
//            //creation des arretes
//            for (Cours c1 : c.getProfesseur().getCours()) {
//                if (!c1.equals(c)) {
//                    graph.addEdge(courss.indexOf(c), courss.indexOf(c1));
//                }
//            }
//            for (Cours c2 : c.getClasse().getCours()) {
//                if (!c2.equals(c)) {
//                    graph.addEdge(courss.indexOf(c), courss.indexOf(c2));
//                }
//            }
//        }
        return graph;
    }

//    public List<Cours> getCourss() {
//        return courss;
//    }
//
//    public void setCourss(List<Cours> courss) {
//        this.courss = courss;
//    }
    public List<CoursOccurence> getCoursoccurences() {
        return coursoccurences;
    }

    public void setCoursoccurences(List<CoursOccurence> coursoccurences) {
        this.coursoccurences = coursoccurences;
    }

    
}
