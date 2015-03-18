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
@Table(name = "accounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
    @NamedQuery(name = "Account.findByName", query = "SELECT a FROM Account a WHERE a.name = :name"),
    @NamedQuery(name = "Account.findByAccountType", query = "SELECT a FROM Account a WHERE a.accountType = :accountType"),
    @NamedQuery(name = "Account.findByIndustry", query = "SELECT a FROM Account a WHERE a.industry = :industry"),
    @NamedQuery(name = "Account.findByPhoneFax", query = "SELECT a FROM Account a WHERE a.phoneFax = :phoneFax"),
    @NamedQuery(name = "Account.findByBillingAddressStreet", query = "SELECT a FROM Account a WHERE a.billingAddressStreet = :billingAddressStreet"),
    @NamedQuery(name = "Account.findByBillingAddressCity", query = "SELECT a FROM Account a WHERE a.billingAddressCity = :billingAddressCity"),
    @NamedQuery(name = "Account.findByBillingAddressState", query = "SELECT a FROM Account a WHERE a.billingAddressState = :billingAddressState"),
    @NamedQuery(name = "Account.findByBillingAddressCountry", query = "SELECT a FROM Account a WHERE a.billingAddressCountry = :billingAddressCountry"),
    @NamedQuery(name = "Account.findByPhoneOffice", query = "SELECT a FROM Account a WHERE a.phoneOffice = :phoneOffice"),
    @NamedQuery(name = "Account.findByPhoneAlternate", query = "SELECT a FROM Account a WHERE a.phoneAlternate = :phoneAlternate"),
    @NamedQuery(name = "Account.findByWebsite", query = "SELECT a FROM Account a WHERE a.website = :website"),
    @NamedQuery(name = "Account.findByDateEntered", query = "SELECT a FROM Account a WHERE a.dateEntered = :dateEntered"),
    @NamedQuery(name = "Account.findByDateModified", query = "SELECT a FROM Account a WHERE a.dateModified = :dateModified")})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "id")
    private String id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Size(max = 255)
    @Column(name = "account_type")
    private String accountType;
    @Size(max = 255)
    @Column(name = "industry")
    private String industry;
    @Size(max = 100)
    @Column(name = "phone_fax")
    private String phoneFax;
    @Size(max = 150)
    @Column(name = "billing_address_street")
    private String billingAddressStreet;
    @Size(max = 100)
    @Column(name = "billing_address_city")
    private String billingAddressCity;
    @Size(max = 100)
    @Column(name = "billing_address_state")
    private String billingAddressState;
    @Size(max = 100)
    @Column(name = "billing_address_country")
    private String billingAddressCountry;
    @Size(max = 100)
    @Column(name = "phone_office")
    private String phoneOffice;
    @Size(max = 100)
    @Column(name = "phone_alternate")
    private String phoneAlternate;
    @Size(max = 100)
    @Column(name = "website")
    private String website;
    @Column(name = "date_entered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEntered;
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    @JoinTable(name = "accounts_has_contacts", joinColumns = {
        @JoinColumn(name = "accounts_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "contacts_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Contact> contactList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountsId")
    private List<Case> caseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountsId")
    private List<Installation> installationList;
    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User assignedUserId;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User createdBy;
    @JoinColumn(name = "modified_user_id", referencedColumnName = "id")
    @ManyToOne
    private User modifiedUserId;

    public Account() {
    }

    public Account(String id) {
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPhoneFax() {
        return phoneFax;
    }

    public void setPhoneFax(String phoneFax) {
        this.phoneFax = phoneFax;
    }

    public String getBillingAddressStreet() {
        return billingAddressStreet;
    }

    public void setBillingAddressStreet(String billingAddressStreet) {
        this.billingAddressStreet = billingAddressStreet;
    }

    public String getBillingAddressCity() {
        return billingAddressCity;
    }

    public void setBillingAddressCity(String billingAddressCity) {
        this.billingAddressCity = billingAddressCity;
    }

    public String getBillingAddressState() {
        return billingAddressState;
    }

    public void setBillingAddressState(String billingAddressState) {
        this.billingAddressState = billingAddressState;
    }

    public String getBillingAddressCountry() {
        return billingAddressCountry;
    }

    public void setBillingAddressCountry(String billingAddressCountry) {
        this.billingAddressCountry = billingAddressCountry;
    }

    public String getPhoneOffice() {
        return phoneOffice;
    }

    public void setPhoneOffice(String phoneOffice) {
        this.phoneOffice = phoneOffice;
    }

    public String getPhoneAlternate() {
        return phoneAlternate;
    }

    public void setPhoneAlternate(String phoneAlternate) {
        this.phoneAlternate = phoneAlternate;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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
    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @XmlTransient
    public List<Case> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<Case> caseList) {
        this.caseList = caseList;
    }

    @XmlTransient
    public List<Installation> getInstallationList() {
        return installationList;
    }

    public void setInstallationList(List<Installation> installationList) {
        this.installationList = installationList;
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
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iveloper.presto.entities.Account[ id=" + id + " ]";
    }
    
}
