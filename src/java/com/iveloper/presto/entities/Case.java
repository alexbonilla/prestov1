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
@Table(name = "cases")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Case.findAll", query = "SELECT c FROM Case c"),
    @NamedQuery(name = "Case.findById", query = "SELECT c FROM Case c WHERE c.id = :id"),
    @NamedQuery(name = "Case.findByName", query = "SELECT c FROM Case c WHERE c.name = :name"),
    @NamedQuery(name = "Case.findByType", query = "SELECT c FROM Case c WHERE c.type = :type"),
    @NamedQuery(name = "Case.findByCasenumber", query = "SELECT c FROM Case c WHERE c.casenumber = :casenumber"),
    @NamedQuery(name = "Case.findByStatus", query = "SELECT c FROM Case c WHERE c.status = :status"),
    @NamedQuery(name = "Case.findByPriority", query = "SELECT c FROM Case c WHERE c.priority = :priority"),
    @NamedQuery(name = "Case.findByDateEntered", query = "SELECT c FROM Case c WHERE c.dateEntered = :dateEntered"),
    @NamedQuery(name = "Case.findByDateModified", query = "SELECT c FROM Case c WHERE c.dateModified = :dateModified")})
public class Case implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "id")
    private String id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Size(max = 255)
    @Column(name = "type")
    private String type;
    @Column(name = "casenumber")
    private Integer casenumber;
    @Size(max = 100)
    @Column(name = "status")
    private String status;
    @Size(max = 100)
    @Column(name = "priority")
    private String priority;
    @Lob
    @Size(max = 65535)
    @Column(name = "resolution")
    private String resolution;
    @Lob
    @Size(max = 65535)
    @Column(name = "work_log")
    private String workLog;
    @Column(name = "date_entered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEntered;
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    @JoinTable(name = "installations_has_cases", joinColumns = {
        @JoinColumn(name = "cases_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "installations_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Installation> installationList;
    @JoinTable(name = "cases_has_contacts", joinColumns = {
        @JoinColumn(name = "cases_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "contacts_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Contact> contactList;
    @JoinTable(name = "projects_has_cases", joinColumns = {
        @JoinColumn(name = "cases_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "projects_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Project> projectList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "case1")
    private List<Visit> visitList;
    @JoinColumn(name = "accounts_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account accountsId;
    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User assignedUserId;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdBy;
    @JoinColumn(name = "modified_user_id", referencedColumnName = "id")
    @ManyToOne
    private User modifiedUserId;

    public Case() {
    }

    public Case(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCasenumber() {
        return casenumber;
    }

    public void setCasenumber(Integer casenumber) {
        this.casenumber = casenumber;
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

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getWorkLog() {
        return workLog;
    }

    public void setWorkLog(String workLog) {
        this.workLog = workLog;
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
    public List<Installation> getInstallationList() {
        return installationList;
    }

    public void setInstallationList(List<Installation> installationList) {
        this.installationList = installationList;
    }

    @XmlTransient
    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @XmlTransient
    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    @XmlTransient
    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
    }

    public Account getAccountsId() {
        return accountsId;
    }

    public void setAccountsId(Account accountsId) {
        this.accountsId = accountsId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Case)) {
            return false;
        }
        Case other = (Case) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iveloper.presto.entities.Case[ id=" + id + " ]";
    }
    
}
