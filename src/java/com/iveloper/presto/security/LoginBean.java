/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.presto.security;

import com.iveloper.presto.jpa.controllers.UserJpaController;
import com.iveloper.jpa.controllers.exceptions.RollbackFailureException;
import com.iveloper.presto.entities.User;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

@ManagedBean(name = "loginBean")
@SessionScoped

/**
 *
 * @author alexbonilla
 */
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String password;
    private String message, uname;
    private User thisSessionUser;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public User getThisSessionUser() {
        return thisSessionUser;
    }

    public void setThisSessionUser(User thisSessionUser) {
        this.thisSessionUser = thisSessionUser;
    }

    public String loginProject() {
        String destinationPage = "error";
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("prestov1PU");
            Context c = new InitialContext();
            UserTransaction utx = (UserTransaction) c.lookup("java:comp/UserTransaction");

            UserJpaController userController = new UserJpaController(utx, emf);
            boolean result = userController.validateCredentials(uname, password);
            if (result) {
                // get Http Session and store username
                HttpSession session = LoginBeanUtils.getSession();
                session.setAttribute("username", uname);

                User thisUser = userController.findUserByUserLogin(uname);
                //ask if this user is user access level
                if (thisUser.getRoles().equals("admin") || thisUser.getRoles().equals("user")) {
                    thisUser.setLastlogin(new Date());
                    thisUser.setOnline(true);
                    userController.edit(thisUser);

                    //Set entityid, to be able to use it to choose the correct PU for this account                    
                    session.setAttribute("user_id", thisUser.getId());
                    this.thisSessionUser = thisUser;
                    destinationPage = "account/List";
                } else {
                    //not the correct role
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Invalid Role!",
                                    "Please Try Again!"));
                    destinationPage = "login";
                }

            } else {

                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Invalid Login!",
                                "Please Try Again!"));

                // invalidate session, and redirect to other pages
                //message = "Invalid Login. Please Try Again!";
                destinationPage = "login";
            }
        } catch (NamingException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return destinationPage;
    }

    public String logout() {
        HttpSession session = LoginBeanUtils.getSession();
        session.invalidate();
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("prestov1PU");
            Context c = new InitialContext();
            UserTransaction utx = (UserTransaction) c.lookup("java:comp/UserTransaction");

            UserJpaController userController = new UserJpaController(utx, emf);            
            thisSessionUser.setOnline(false);
            userController.edit(thisSessionUser);

        } catch (NamingException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/login";
    }
}
