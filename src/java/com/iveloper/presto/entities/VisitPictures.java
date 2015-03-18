/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.presto.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alexbonilla
 */
@Entity
@Table(name = "visit_pictures")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VisitPictures.findAll", query = "SELECT v FROM VisitPictures v"),
    @NamedQuery(name = "VisitPictures.findById", query = "SELECT v FROM VisitPictures v WHERE v.id = :id")})
public class VisitPictures implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "id")
    private String id;
    @Lob
    @Column(name = "picture")
    private byte[] picture;
    @JoinColumns({
        @JoinColumn(name = "visits_tours_id", referencedColumnName = "tours_id"),
        @JoinColumn(name = "visits_cases_id", referencedColumnName = "cases_id")})
    @ManyToOne(optional = false)
    private Visit visit;

    public VisitPictures() {
    }

    public VisitPictures(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitPictures)) {
            return false;
        }
        VisitPictures other = (VisitPictures) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iveloper.presto.entities.VisitPictures[ id=" + id + " ]";
    }
    
}
