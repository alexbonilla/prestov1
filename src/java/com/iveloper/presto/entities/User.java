/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.presto.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByDateEntered", query = "SELECT u FROM User u WHERE u.dateEntered = :dateEntered"),
    @NamedQuery(name = "User.findByLastlogin", query = "SELECT u FROM User u WHERE u.lastlogin = :lastlogin"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByOnline", query = "SELECT u FROM User u WHERE u.online = :online"),
    @NamedQuery(name = "User.findByRoles", query = "SELECT u FROM User u WHERE u.roles = :roles"),
    @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "User.findByUserLogin", query = "SELECT u FROM User u WHERE u.userLogin = :userLogin"),
    @NamedQuery(name = "User.findByIsAdmin", query = "SELECT u FROM User u WHERE u.isAdmin = :isAdmin"),
    @NamedQuery(name = "User.findByDepartment", query = "SELECT u FROM User u WHERE u.department = :department"),
    @NamedQuery(name = "User.findByPhoneHome", query = "SELECT u FROM User u WHERE u.phoneHome = :phoneHome"),
    @NamedQuery(name = "User.findByPhoneMobile", query = "SELECT u FROM User u WHERE u.phoneMobile = :phoneMobile"),
    @NamedQuery(name = "User.findByPhoneWork", query = "SELECT u FROM User u WHERE u.phoneWork = :phoneWork"),
    @NamedQuery(name = "User.findByPhoneOther", query = "SELECT u FROM User u WHERE u.phoneOther = :phoneOther"),
    @NamedQuery(name = "User.findByPhoneFax", query = "SELECT u FROM User u WHERE u.phoneFax = :phoneFax"),
    @NamedQuery(name = "User.findByStatus", query = "SELECT u FROM User u WHERE u.status = :status"),
    @NamedQuery(name = "User.findByDateModified", query = "SELECT u FROM User u WHERE u.dateModified = :dateModified"),
    @NamedQuery(name = "User.findByReceiveNotifications", query = "SELECT u FROM User u WHERE u.receiveNotifications = :receiveNotifications")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "id")
    private String id;
    @Lob
    @Column(name = "pwd")
    private byte[] pwd;
    @Column(name = "date_entered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEntered;
    @Column(name = "lastlogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastlogin;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @Column(name = "online")
    private Boolean online;
    @Lob
    @Column(name = "salt")
    private byte[] salt;
    @Size(max = 255)
    @Column(name = "roles")
    private String roles;
    @Lob
    @Column(name = "logo")
    private byte[] logo;
    @Size(max = 100)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 100)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 100)
    @Column(name = "user_login")
    private String userLogin;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @Size(max = 255)
    @Column(name = "department")
    private String department;
    @Size(max = 50)
    @Column(name = "phone_home")
    private String phoneHome;
    @Size(max = 50)
    @Column(name = "phone_mobile")
    private String phoneMobile;
    @Size(max = 50)
    @Column(name = "phone_work")
    private String phoneWork;
    @Size(max = 50)
    @Column(name = "phone_other")
    private String phoneOther;
    @Size(max = 50)
    @Column(name = "phone_fax")
    private String phoneFax;
    @Size(max = 100)
    @Column(name = "status")
    private String status;
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    @Column(name = "receive_notifications")
    private Boolean receiveNotifications;
    @OneToMany(mappedBy = "createdBy")
    private Collection<User> userCollection;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private User createdBy;
    @OneToMany(mappedBy = "modifiedUserId")
    private Collection<User> userCollection1;
    @JoinColumn(name = "modified_user_id", referencedColumnName = "id")
    @ManyToOne
    private User modifiedUserId;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getPwd() {
        return pwd;
    }

    public void setPwd(byte[] pwd) {
        this.pwd = pwd;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
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

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Boolean getReceiveNotifications() {
        return receiveNotifications;
    }

    public void setReceiveNotifications(Boolean receiveNotifications) {
        this.receiveNotifications = receiveNotifications;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @XmlTransient
    public Collection<User> getUserCollection1() {
        return userCollection1;
    }

    public void setUserCollection1(Collection<User> userCollection1) {
        this.userCollection1 = userCollection1;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iveloper.presto.entities.User[ id=" + id + " ]";
    }
    
}
