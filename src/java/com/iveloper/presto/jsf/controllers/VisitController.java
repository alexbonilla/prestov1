package com.iveloper.presto.jsf.controllers;

import com.iveloper.presto.entities.Visit;
import com.iveloper.presto.jsf.controllers.util.JsfUtil;
import com.iveloper.presto.jsf.controllers.util.JsfUtil.PersistAction;
import com.iveloper.presto.jsf.beans.VisitFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "visitController")
@SessionScoped
public class VisitController implements Serializable {

    @EJB
    private com.iveloper.presto.jsf.beans.VisitFacade ejbFacade;
    private List<Visit> items = null;
    private Visit selected;

    public VisitController() {
    }

    public Visit getSelected() {
        return selected;
    }

    public void setSelected(Visit selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getVisitPK().setCasesId(selected.getCase1().getId());
        selected.getVisitPK().setToursId(selected.getTour().getId());
    }

    protected void initializeEmbeddableKey() {
        selected.setVisitPK(new com.iveloper.presto.entities.VisitPK());
    }

    private VisitFacade getFacade() {
        return ejbFacade;
    }

    public Visit prepareCreate() {
        selected = new Visit();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("VisitCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("VisitUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("VisitDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Visit> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Visit> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Visit> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Visit.class)
    public static class VisitControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VisitController controller = (VisitController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "visitController");
            return controller.getFacade().find(getKey(value));
        }

        com.iveloper.presto.entities.VisitPK getKey(String value) {
            com.iveloper.presto.entities.VisitPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.iveloper.presto.entities.VisitPK();
            key.setToursId(values[0]);
            key.setCasesId(values[1]);
            return key;
        }

        String getStringKey(com.iveloper.presto.entities.VisitPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getToursId());
            sb.append(SEPARATOR);
            sb.append(value.getCasesId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Visit) {
                Visit o = (Visit) object;
                return getStringKey(o.getVisitPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Visit.class.getName()});
                return null;
            }
        }

    }

}
