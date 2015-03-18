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
@Table(name = "projects")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findById", query = "SELECT p FROM Project p WHERE p.id = :id"),
    @NamedQuery(name = "Project.findByDateEntered", query = "SELECT p FROM Project p WHERE p.dateEntered = :dateEntered"),
    @NamedQuery(name = "Project.findByDateModiied", query = "SELECT p FROM Project p WHERE p.dateModiied = :dateModiied"),
    @NamedQuery(name = "Project.findByName", query = "SELECT p FROM Project p WHERE p.name = :name"),
    @NamedQuery(name = "Project.findByEstimatedStartDate", query = "SELECT p FROM Project p WHERE p.estimatedStartDate = :estimatedStartDate"),
    @NamedQuery(name = "Project.findByEstimatedEndDate", query = "SELECT p FROM Project p WHERE p.estimatedEndDate = :estimatedEndDate"),
    @NamedQuery(name = "Project.findByStatus", query = "SELECT p FROM Project p WHERE p.status = :status"),
    @NamedQuery(name = "Project.findByPriority", query = "SELECT p FROM Project p WHERE p.priority = :priority")})
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "id")
    private String id;
    @Column(name = "date_entered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEntered;
    @Column(name = "date_modiied")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModiied;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Column(name = "estimated_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estimatedStartDate;
    @Column(name = "estimated_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estimatedEndDate;
    @Size(max = 255)
    @Column(name = "status")
    private String status;
    @Size(max = 255)
    @Column(name = "priority")
    private String priority;
    @ManyToMany(mappedBy = "projectList")
    private List<Case> caseList;
    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User assignedUserId;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdBy;
    @JoinColumn(name = "modified_user_id", referencedColumnName = "id")
    @ManyToOne
    private User modifiedUserId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectsId")
    private List<Installation> installationList;

    public Project() {
    }

    public Project(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public Date getDateModiied() {
        return dateModiied;
    }

    public void setDateModiied(Date dateModiied) {
        this.dateModiied = dateModiied;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEstimatedStartDate() {
        return estimatedStartDate;
    }

    public void setEstimatedStartDate(Date estimatedStartDate) {
        this.estimatedStartDate = estimatedStartDate;
    }

    public Date getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate(Date estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @XmlTransient
    public List<Case> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<Case> caseList) {
        this.caseList = caseList;
    }

    public User getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(User assignedUserId) {
        this.assignedUserId = assignedUserId;
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

    @XmlTransient
    public List<Installation> getInstallationList() {
        return installationList;
    }

    public void setInstallationList(List<Installation> installationList) {
        this.installationList = installationList;
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
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iveloper.presto.entities.Project[ id=" + id + " ]";
    }
    
}
