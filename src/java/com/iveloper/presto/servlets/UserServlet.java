/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.presto.servlets;

import com.iveloper.jpa.controllers.exceptions.RollbackFailureException;
import com.iveloper.presto.entities.User;
import com.iveloper.presto.jpa.controllers.UserJpaController;
import com.iveloper.presto.security.PasswordEncryptionService;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author Alex
 */
@WebServlet(name = "userServlet", urlPatterns = {"/userServlet"})
public class UserServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        PrintWriter out = response.getWriter();

        try {
            out.println(createUser(request, response));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        out.close();
    }

    public String createUser(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String message = "Failed";
        
        try {

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("prestov1PU");
            Context c = new InitialContext();
            UserTransaction utx = (UserTransaction) c.lookup("java:comp/UserTransaction");

            PasswordEncryptionService pes = new PasswordEncryptionService();
            byte[] salt = pes.generateSalt();
            
            UUID uid = UUID.randomUUID();
            String uname = request.getParameter("user");
            String role = request.getParameter("role");
            String email = request.getParameter("email");

            byte[] pwd = pes.getEncryptedPassword(request.getParameter("password"), salt);

            UserJpaController userController = new UserJpaController(utx, emf);

            User user = new User(uid.toString());
            user.setUserLogin(uname);
            user.setPwd(pwd);
            user.setEmail(email);
            user.setSalt(salt);
            user.setRoles(role);
            user.setDateEntered(new Date());
            userController.create(user);
            message = "Success!";

        } catch (NamingException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
