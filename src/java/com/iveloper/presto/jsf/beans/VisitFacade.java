/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.presto.jsf.beans;

import com.iveloper.presto.entities.Visit;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alexbonilla
 */
@Stateless
public class VisitFacade extends AbstractFacade<Visit> {
    @PersistenceContext(unitName = "prestov1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VisitFacade() {
        super(Visit.class);
    }
    
}
