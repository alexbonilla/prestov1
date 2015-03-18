/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.presto.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alexbonilla
 */
@Entity
@Table(name = "tours")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tour.findAll", query = "SELECT t FROM Tour t"),
    @NamedQuery(name = "Tour.findById", query = "SELECT t FROM Tour t WHERE t.id = :id"),
    @NamedQuery(name = "Tour.findByOdometerStart", query = "SELECT t FROM Tour t WHERE t.odometerStart = :odometerStart"),
    @NamedQuery(name = "Tour.findByOdometerEnd", query = "SELECT t FROM Tour t WHERE t.odometerEnd = :odometerEnd"),
    @NamedQuery(name = "Tour.findByTourDate", query = "SELECT t FROM Tour t WHERE t.tourDate = :tourDate"),
    @NamedQuery(name = "Tour.findByDateEntered", query = "SELECT t FROM Tour t WHERE t.dateEntered = :dateEntered"),
    @NamedQuery(name = "Tour.findByDateModified", query = "SELECT t FROM Tour t WHERE t.dateModified = :dateModified")})
public class Tour implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "id")
    private String id;
    @Column(name = "odometer_start")
    private Integer odometerStart;
    @Column(name = "odometer_end")
    private Integer odometerEnd;
    @Column(name = "tour_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tourDate;
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
    @JoinTable(name = "tours_has_users", joinColumns = {
        @JoinColumn(name = "tours_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "users_id", referencedColumnName = "id")})
    @ManyToMany
    private List<User> userList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tour")
    private List<Visit> visitList;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdBy;
    @JoinColumn(name = "modified_user_id", referencedColumnName = "id")
    @ManyToOne
    private User modifiedUserId;
    @JoinColumn(name = "vehicles_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Vehicle vehiclesId;

    public Tour() {
    }

    public Tour(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOdometerStart() {
        return odometerStart;
    }

    public void setOdometerStart(Integer odometerStart) {
        this.odometerStart = odometerStart;
    }

    public Integer getOdometerEnd() {
        return odometerEnd;
    }

    public void setOdometerEnd(Integer odometerEnd) {
        this.odometerEnd = odometerEnd;
    }

    public Date getTourDate() {
        return tourDate;
    }

    public void setTourDate(Date tourDate) {
        this.tourDate = tourDate;
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

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @XmlTransient
    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
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

    public Vehicle getVehiclesId() {
        return vehiclesId;
    }

    public void setVehiclesId(Vehicle vehiclesId) {
        this.vehiclesId = vehiclesId;
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
        if (!(object instanceof Tour)) {
            return false;
        }
        Tour other = (Tour) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iveloper.presto.entities.Tour[ id=" + id + " ]";
    }
    
}
