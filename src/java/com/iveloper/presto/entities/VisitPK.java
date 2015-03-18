/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iveloper.presto.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author alexbonilla
 */
@Embeddable
public class VisitPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "tours_id")
    private String toursId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "cases_id")
    private String casesId;

    public VisitPK() {
    }

    public VisitPK(String toursId, String casesId) {
        this.toursId = toursId;
        this.casesId = casesId;
    }

    public String getToursId() {
        return toursId;
    }

    public void setToursId(String toursId) {
        this.toursId = toursId;
    }

    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (toursId != null ? toursId.hashCode() : 0);
        hash += (casesId != null ? casesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitPK)) {
            return false;
        }
        VisitPK other = (VisitPK) object;
        if ((this.toursId == null && other.toursId != null) || (this.toursId != null && !this.toursId.equals(other.toursId))) {
            return false;
        }
        if ((this.casesId == null && other.casesId != null) || (this.casesId != null && !this.casesId.equals(other.casesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iveloper.presto.entities.VisitPK[ toursId=" + toursId + ", casesId=" + casesId + " ]";
    }
    
}
