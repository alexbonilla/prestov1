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
@Table(name = "contacts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c"),
    @NamedQuery(name = "Contact.findById", query = "SELECT c FROM Contact c WHERE c.id = :id"),
    @NamedQuery(name = "Contact.findByFirstName", query = "SELECT c FROM Contact c WHERE c.firstName = :firstName"),
    @NamedQuery(name = "Contact.findByLastName", query = "SELECT c FROM Contact c WHERE c.lastName = :lastName"),
    @NamedQuery(name = "Contact.findByTitle", query = "SELECT c FROM Contact c WHERE c.title = :title"),
    @NamedQuery(name = "Contact.findByDepartment", query = "SELECT c FROM Contact c WHERE c.department = :department"),
    @NamedQuery(name = "Contact.findByDoNotCall", query = "SELECT c FROM Contact c WHERE c.doNotCall = :doNotCall"),
    @NamedQuery(name = "Contact.findByPhoneHome", query = "SELECT c FROM Contact c WHERE c.phoneHome = :phoneHome"),
    @NamedQuery(name = "Contact.findByPhoneMobile", query = "SELECT c FROM Contact c WHERE c.phoneMobile = :phoneMobile"),
    @NamedQuery(name = "Contact.findByPhoneWork", query = "SELECT c FROM Contact c WHERE c.phoneWork = :phoneWork"),
    @NamedQuery(name = "Contact.findByPhoneOther", query = "SELECT c FROM Contact c WHERE c.phoneOther = :phoneOther"),
    @NamedQuery(name = "Contact.findByPhoneFax", query = "SELECT c FROM Contact c WHERE c.phoneFax = :phoneFax"),
    @NamedQuery(name = "Contact.findByPrimaryAddressStreet", query = "SELECT c FROM Contact c WHERE c.primaryAddressStreet = :primaryAddressStreet"),
    @NamedQuery(name = "Contact.findByPrimaryAddressCity", query = "SELECT c FROM Contact c WHERE c.primaryAddressCity = :primaryAddressCity"),
    @NamedQuery(name = "Contact.findByPrimaryAddressState", query = "SELECT c FROM Contact c WHERE c.primaryAddressState = :primaryAddressState"),
    @NamedQuery(name = "Contact.findByPrimaryAddressCountry", query = "SELECT c FROM Contact c WHERE c.primaryAddressCountry = :primaryAddressCountry"),
    @NamedQuery(name = "Contact.findByDateEntered", query = "SELECT c FROM Contact c WHERE c.dateEntered = :dateEntered"),
    @NamedQuery(name = "Contact.findByDateModified", query = "SELECT c FROM Contact c WHERE c.dateModified = :dateModified"),
    @NamedQuery(name = "Contact.findByEmail", query = "SELECT c FROM Contact c WHERE c.email = :email")})
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "id")
    private String id;
    @Size(max = 100)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 100)
    @Column(name = "last_name")
    private String lastName;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Size(max = 100)
    @Column(name = "title")
    private String title;
    @Size(max = 255)
    @Column(name = "department")
    private String department;
    @Column(name = "do_not_call")
    private Boolean doNotCall;
    @Size(max = 100)
    @Column(name = "phone_home")
    private String phoneHome;
    @Size(max = 100)
    @Column(name = "phone_mobile")
    private String phoneMobile;
    @Size(max = 100)
    @Column(name = "phone_work")
    private String phoneWork;
    @Size(max = 100)
    @Column(name = "phone_other")
    private String phoneOther;
    @Size(max = 100)
    @Column(name = "phone_fax")
    private String phoneFax;
    @Size(max = 150)
    @Column(name = "primary_address_street")
    private String primaryAddressStreet;
    @Size(max = 100)
    @Column(name = "primary_address_city")
    private String primaryAddressCity;
    @Size(max = 100)
    @Column(name = "primary_address_state")
    private String primaryAddressState;
    @Size(max = 100)
    @Column(name = "primary_address_country")
    private String primaryAddressCountry;
    @Column(name = "date_entered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEntered;
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    @ManyToMany(mappedBy = "contactList")
    private List<Account> accountList;
    @JoinTable(name = "installations_has_contacts", joinColumns = {
        @JoinColumn(name = "contacts_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "installations_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Installation> installationList;
    @ManyToMany(mappedBy = "contactList")
    private List<Case> caseList;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdBy;
    @JoinColumn(name = "modified_user_id", referencedColumnName = "id")
    @ManyToOne
    private User modifiedUserId;
    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User assignedUserId;

    public Contact() {
    }

    public Contact(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Boolean getDoNotCall() {
        return doNotCall;
    }

    public void setDoNotCall(Boolean doNotCall) {
        this.doNotCall = doNotCall;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    public String getPhoneMobile() {
        return phoneMobile;
    }

    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public void setPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
    }

    public String getPhoneOther() {
        return phoneOther;
    }

    public void setPhoneOther(String phoneOther) {
        this.phoneOther = phoneOther;
    }

    public String getPhoneFax() {
        return phoneFax;
    }

    public void setPhoneFax(String phoneFax) {
        this.phoneFax = phoneFax;
    }

    public String getPrimaryAddressStreet() {
        return primaryAddressStreet;
    }

    public void setPrimaryAddressStreet(String primaryAddressStreet) {
        this.primaryAddressStreet = primaryAddressStreet;
    }

    public String getPrimaryAddressCity() {
        return primaryAddressCity;
    }

    public void setPrimaryAddressCity(String primaryAddressCity) {
        this.primaryAddressCity = primaryAddressCity;
    }

    public String getPrimaryAddressState() {
        return primaryAddressState;
    }

    public void setPrimaryAddressState(String primaryAddressState) {
        this.primaryAddressState = primaryAddressState;
    }

    public String getPrimaryAddressCountry() {
        return primaryAddressCountry;
    }

    public void setPrimaryAddressCountry(String primaryAddressCountry) {
        this.primaryAddressCountry = primaryAddressCountry;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @XmlTransient
    public List<Installation> getInstallationList() {
        return installationList;
    }

    public void setInstallationList(List<Installation> installationList) {
        this.installationList = installationList;
    }

    @XmlTransient
    public List<Case> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<Case> caseList) {
        this.caseList = caseList;
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

    public User getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(User assignedUserId) {
        this.assignedUserId = assignedUserId;
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
        if (!(object instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iveloper.presto.entities.Contact[ id=" + id + " ]";
    }
    
}
