/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@Entity
@Table(name = "authentication")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Authentication.findAll", query = "SELECT a FROM Authentication a")
    , @NamedQuery(name = "Authentication.findByAuthenticationID", query = "SELECT a FROM Authentication a WHERE a.authenticationID = :authenticationID")
    , @NamedQuery(name = "Authentication.findByUserName", query = "SELECT a FROM Authentication a WHERE a.userName = :userName")
    , @NamedQuery(name = "Authentication.findByPassword", query = "SELECT a FROM Authentication a WHERE a.password = :password")
    , @NamedQuery(name = "Authentication.findByRole", query = "SELECT a FROM Authentication a WHERE a.role = :role")
    , @NamedQuery(name = "Authentication.findByAuthenticationCreated", query = "SELECT a FROM Authentication a WHERE a.authenticationCreated = :authenticationCreated")})
public class Authentication implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AuthenticationID")
    private Integer authenticationID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "UserName")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Role")
    private String role;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AuthenticationCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date authenticationCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authenticationID")
    private Collection<Shipper> shipperCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authenticationID")
    private Collection<Supplier> supplierCollection;

    public Authentication() {
    }

    public Authentication(Integer authenticationID) {
        this.authenticationID = authenticationID;
    }

    public Authentication(Integer authenticationID, String userName, String password, String role, Date authenticationCreated) {
        this.authenticationID = authenticationID;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.authenticationCreated = authenticationCreated;
    }

    public Integer getAuthenticationID() {
        return authenticationID;
    }

    public void setAuthenticationID(Integer authenticationID) {
        this.authenticationID = authenticationID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getAuthenticationCreated() {
        return authenticationCreated;
    }

    public void setAuthenticationCreated(Date authenticationCreated) {
        this.authenticationCreated = authenticationCreated;
    }

    @XmlTransient
    public Collection<Shipper> getShipperCollection() {
        return shipperCollection;
    }

    public void setShipperCollection(Collection<Shipper> shipperCollection) {
        this.shipperCollection = shipperCollection;
    }

    @XmlTransient
    public Collection<Supplier> getSupplierCollection() {
        return supplierCollection;
    }

    public void setSupplierCollection(Collection<Supplier> supplierCollection) {
        this.supplierCollection = supplierCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authenticationID != null ? authenticationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authentication)) {
            return false;
        }
        Authentication other = (Authentication) object;
        if ((this.authenticationID == null && other.authenticationID != null) || (this.authenticationID != null && !this.authenticationID.equals(other.authenticationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Authentication[ authenticationID=" + authenticationID + " ]";
    }
    
}
