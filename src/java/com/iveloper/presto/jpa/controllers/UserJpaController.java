/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.presto.jpa.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iveloper.presto.entities.User;
import com.iveloper.presto.jpa.controllers.exceptions.NonexistentEntityException;
import com.iveloper.presto.jpa.controllers.exceptions.PreexistingEntityException;
import com.iveloper.presto.jpa.controllers.exceptions.RollbackFailureException;
import com.iveloper.presto.security.PasswordEncryptionService;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author alexbonilla
 */
public class UserJpaController implements Serializable {

    public UserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (user.getUserCollection() == null) {
            user.setUserCollection(new ArrayList<User>());
        }
        if (user.getUserCollection1() == null) {
            user.setUserCollection1(new ArrayList<User>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User createdBy = user.getCreatedBy();
            if (createdBy != null) {
                createdBy = em.getReference(createdBy.getClass(), createdBy.getId());
                user.setCreatedBy(createdBy);
            }
            User modifiedUserId = user.getModifiedUserId();
            if (modifiedUserId != null) {
                modifiedUserId = em.getReference(modifiedUserId.getClass(), modifiedUserId.getId());
                user.setModifiedUserId(modifiedUserId);
            }
            Collection<User> attachedUserCollection = new ArrayList<User>();
            for (User userCollectionUserToAttach : user.getUserCollection()) {
                userCollectionUserToAttach = em.getReference(userCollectionUserToAttach.getClass(), userCollectionUserToAttach.getId());
                attachedUserCollection.add(userCollectionUserToAttach);
            }
            user.setUserCollection(attachedUserCollection);
            Collection<User> attachedUserCollection1 = new ArrayList<User>();
            for (User userCollection1UserToAttach : user.getUserCollection1()) {
                userCollection1UserToAttach = em.getReference(userCollection1UserToAttach.getClass(), userCollection1UserToAttach.getId());
                attachedUserCollection1.add(userCollection1UserToAttach);
            }
            user.setUserCollection1(attachedUserCollection1);
            em.persist(user);
            if (createdBy != null) {
                createdBy.getUserCollection().add(user);
                createdBy = em.merge(createdBy);
            }
            if (modifiedUserId != null) {
                modifiedUserId.getUserCollection().add(user);
                modifiedUserId = em.merge(modifiedUserId);
            }
            for (User userCollectionUser : user.getUserCollection()) {
                User oldCreatedByOfUserCollectionUser = userCollectionUser.getCreatedBy();
                userCollectionUser.setCreatedBy(user);
                userCollectionUser = em.merge(userCollectionUser);
                if (oldCreatedByOfUserCollectionUser != null) {
                    oldCreatedByOfUserCollectionUser.getUserCollection().remove(userCollectionUser);
                    oldCreatedByOfUserCollectionUser = em.merge(oldCreatedByOfUserCollectionUser);
                }
            }
            for (User userCollection1User : user.getUserCollection1()) {
                User oldModifiedUserIdOfUserCollection1User = userCollection1User.getModifiedUserId();
                userCollection1User.setModifiedUserId(user);
                userCollection1User = em.merge(userCollection1User);
                if (oldModifiedUserIdOfUserCollection1User != null) {
                    oldModifiedUserIdOfUserCollection1User.getUserCollection1().remove(userCollection1User);
                    oldModifiedUserIdOfUserCollection1User = em.merge(oldModifiedUserIdOfUserCollection1User);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUser(user.getId()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User persistentUser = em.find(User.class, user.getId());
            User createdByOld = persistentUser.getCreatedBy();
            User createdByNew = user.getCreatedBy();
            User modifiedUserIdOld = persistentUser.getModifiedUserId();
            User modifiedUserIdNew = user.getModifiedUserId();
            Collection<User> userCollectionOld = persistentUser.getUserCollection();
            Collection<User> userCollectionNew = user.getUserCollection();
            Collection<User> userCollection1Old = persistentUser.getUserCollection1();
            Collection<User> userCollection1New = user.getUserCollection1();
            if (createdByNew != null) {
                createdByNew = em.getReference(createdByNew.getClass(), createdByNew.getId());
                user.setCreatedBy(createdByNew);
            }
            if (modifiedUserIdNew != null) {
                modifiedUserIdNew = em.getReference(modifiedUserIdNew.getClass(), modifiedUserIdNew.getId());
                user.setModifiedUserId(modifiedUserIdNew);
            }
            Collection<User> attachedUserCollectionNew = new ArrayList<User>();
            for (User userCollectionNewUserToAttach : userCollectionNew) {
                userCollectionNewUserToAttach = em.getReference(userCollectionNewUserToAttach.getClass(), userCollectionNewUserToAttach.getId());
                attachedUserCollectionNew.add(userCollectionNewUserToAttach);
            }
            userCollectionNew = attachedUserCollectionNew;
            user.setUserCollection(userCollectionNew);
            Collection<User> attachedUserCollection1New = new ArrayList<User>();
            for (User userCollection1NewUserToAttach : userCollection1New) {
                userCollection1NewUserToAttach = em.getReference(userCollection1NewUserToAttach.getClass(), userCollection1NewUserToAttach.getId());
                attachedUserCollection1New.add(userCollection1NewUserToAttach);
            }
            userCollection1New = attachedUserCollection1New;
            user.setUserCollection1(userCollection1New);
            user = em.merge(user);
            if (createdByOld != null && !createdByOld.equals(createdByNew)) {
                createdByOld.getUserCollection().remove(user);
                createdByOld = em.merge(createdByOld);
            }
            if (createdByNew != null && !createdByNew.equals(createdByOld)) {
                createdByNew.getUserCollection().add(user);
                createdByNew = em.merge(createdByNew);
            }
            if (modifiedUserIdOld != null && !modifiedUserIdOld.equals(modifiedUserIdNew)) {
                modifiedUserIdOld.getUserCollection().remove(user);
                modifiedUserIdOld = em.merge(modifiedUserIdOld);
            }
            if (modifiedUserIdNew != null && !modifiedUserIdNew.equals(modifiedUserIdOld)) {
                modifiedUserIdNew.getUserCollection().add(user);
                modifiedUserIdNew = em.merge(modifiedUserIdNew);
            }
            for (User userCollectionOldUser : userCollectionOld) {
                if (!userCollectionNew.contains(userCollectionOldUser)) {
                    userCollectionOldUser.setCreatedBy(null);
                    userCollectionOldUser = em.merge(userCollectionOldUser);
                }
            }
            for (User userCollectionNewUser : userCollectionNew) {
                if (!userCollectionOld.contains(userCollectionNewUser)) {
                    User oldCreatedByOfUserCollectionNewUser = userCollectionNewUser.getCreatedBy();
                    userCollectionNewUser.setCreatedBy(user);
                    userCollectionNewUser = em.merge(userCollectionNewUser);
                    if (oldCreatedByOfUserCollectionNewUser != null && !oldCreatedByOfUserCollectionNewUser.equals(user)) {
                        oldCreatedByOfUserCollectionNewUser.getUserCollection().remove(userCollectionNewUser);
                        oldCreatedByOfUserCollectionNewUser = em.merge(oldCreatedByOfUserCollectionNewUser);
                    }
                }
            }
            for (User userCollection1OldUser : userCollection1Old) {
                if (!userCollection1New.contains(userCollection1OldUser)) {
                    userCollection1OldUser.setModifiedUserId(null);
                    userCollection1OldUser = em.merge(userCollection1OldUser);
                }
            }
            for (User userCollection1NewUser : userCollection1New) {
                if (!userCollection1Old.contains(userCollection1NewUser)) {
                    User oldModifiedUserIdOfUserCollection1NewUser = userCollection1NewUser.getModifiedUserId();
                    userCollection1NewUser.setModifiedUserId(user);
                    userCollection1NewUser = em.merge(userCollection1NewUser);
                    if (oldModifiedUserIdOfUserCollection1NewUser != null && !oldModifiedUserIdOfUserCollection1NewUser.equals(user)) {
                        oldModifiedUserIdOfUserCollection1NewUser.getUserCollection1().remove(userCollection1NewUser);
                        oldModifiedUserIdOfUserCollection1NewUser = em.merge(oldModifiedUserIdOfUserCollection1NewUser);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            User createdBy = user.getCreatedBy();
            if (createdBy != null) {
                createdBy.getUserCollection().remove(user);
                createdBy = em.merge(createdBy);
            }
            User modifiedUserId = user.getModifiedUserId();
            if (modifiedUserId != null) {
                modifiedUserId.getUserCollection().remove(user);
                modifiedUserId = em.merge(modifiedUserId);
            }
            Collection<User> userCollection = user.getUserCollection();
            for (User userCollectionUser : userCollection) {
                userCollectionUser.setCreatedBy(null);
                userCollectionUser = em.merge(userCollectionUser);
            }
            Collection<User> userCollection1 = user.getUserCollection1();
            for (User userCollection1User : userCollection1) {
                userCollection1User.setModifiedUserId(null);
                userCollection1User = em.merge(userCollection1User);
            }
            em.remove(user);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public User findUser(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public User findUserByUserLogin(String userLogin) {

        Query query = getEntityManager().createNamedQuery("User.findByUserLogin", com.iveloper.presto.entities.User.class);
        query.setParameter("userLogin", userLogin);

        User user = (User) query.getSingleResult();

        return user;
    }

    public boolean validateCredentials(String user, String pwd) {
        PasswordEncryptionService pes = new PasswordEncryptionService();
        User validatingAccount = findUserByUserLogin(user);
        boolean validation = false;
        if (validatingAccount != null) {
            try {
                validation = pes.authenticate(pwd, validatingAccount.getPwd(), validatingAccount.getSalt());
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(UserJpaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return validation;
    }
}
