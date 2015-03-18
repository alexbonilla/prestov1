/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.presto.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alexbonilla
 */
@Entity
@Table(name = "visits")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visit.findAll", query = "SELECT v FROM Visit v"),
    @NamedQuery(name = "Visit.findByToursId", query = "SELECT v FROM Visit v WHERE v.visitPK.toursId = :toursId"),
    @NamedQuery(name = "Visit.findByCasesId", query = "SELECT v FROM Visit v WHERE v.visitPK.casesId = :casesId"),
    @NamedQuery(name = "Visit.findByVisitStart", query = "SELECT v FROM Visit v WHERE v.visitStart = :visitStart"),
    @NamedQuery(name = "Visit.findByVisitEnd", query = "SELECT v FROM Visit v WHERE v.visitEnd = :visitEnd"),
    @NamedQuery(name = "Visit.findByDateEntered", query = "SELECT v FROM Visit v WHERE v.dateEntered = :dateEntered"),
    @NamedQuery(name = "Visit.findByDateModified", query = "SELECT v FROM Visit v WHERE v.dateModified = :dateModified")})
public class Visit implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VisitPK visitPK;
    @Column(name = "visit_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date visitStart;
    @Column(name = "visit_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date visitEnd;
    @Lob
    @Size(max = 65535)
    @Column(name = "observations")
    private String observations;
    @Column(name = "date_entered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEntered;
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    @JoinColumn(name = "cases_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Case case1;
    @JoinColumn(name = "tours_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tour tour;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdBy;
    @JoinColumn(name = "modified_user_id", referencedColumnName = "id")
    @ManyToOne
    private User modifiedUserId;
    @JoinColumn(name = "filled_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User filledBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "visit")
    private List<VisitPictures> visitPicturesList;

    public Visit() {
    }

    public Visit(VisitPK visitPK) {
        this.visitPK = visitPK;
    }

    public Visit(String toursId, String casesId) {
        this.visitPK = new VisitPK(toursId, casesId);
    }

    public VisitPK getVisitPK() {
        return visitPK;
    }

    public void setVisitPK(VisitPK visitPK) {
        this.visitPK = visitPK;
    }

    public Date getVisitStart() {
        return visitStart;
    }

    public void setVisitStart(Date visitStart) {
        this.visitStart = visitStart;
    }

    public Date getVisitEnd() {
        return visitEnd;
    }

    public void setVisitEnd(Date visitEnd) {
        this.visitEnd = visitEnd;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Case getCase1() {
        return case1;
    }

    public void setCase1(Case case1) {
        this.case1 = case1;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(User modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public User getFilledBy() {
        return filledBy;
    }

    public void setFilledBy(User filledBy) {
        this.filledBy = filledBy;
    }

    @XmlTransient
    public List<VisitPictures> getVisitPicturesList() {
        return visitPicturesList;
    }

    public void setVisitPicturesList(List<VisitPictures> visitPicturesList) {
        this.visitPicturesList = visitPicturesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visitPK != null ? visitPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visit)) {
            return false;
        }
        Visit other = (Visit) object;
        if ((this.visitPK == null && other.visitPK != null) || (this.visitPK != null && !this.visitPK.equals(other.visitPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iveloper.presto.entities.Visit[ visitPK=" + visitPK + " ]";
    }
    
}
