/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@Entity
@Table(name = "supplier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Supplier.findAll", query = "SELECT s FROM Supplier s")
    , @NamedQuery(name = "Supplier.findBySupplierID", query = "SELECT s FROM Supplier s WHERE s.supplierID = :supplierID")
    , @NamedQuery(name = "Supplier.findBySupplierCompanyName", query = "SELECT s FROM Supplier s WHERE s.supplierCompanyName = :supplierCompanyName")
    , @NamedQuery(name = "Supplier.findBySupplierContactFirstName", query = "SELECT s FROM Supplier s WHERE s.supplierContactFirstName = :supplierContactFirstName")
    , @NamedQuery(name = "Supplier.findBySupplierContactLastName", query = "SELECT s FROM Supplier s WHERE s.supplierContactLastName = :supplierContactLastName")
    , @NamedQuery(name = "Supplier.findBySupplierContactTitle", query = "SELECT s FROM Supplier s WHERE s.supplierContactTitle = :supplierContactTitle")
    , @NamedQuery(name = "Supplier.findBySupplierAddressLine1", query = "SELECT s FROM Supplier s WHERE s.supplierAddressLine1 = :supplierAddressLine1")
    , @NamedQuery(name = "Supplier.findBySupplierAddressLine2", query = "SELECT s FROM Supplier s WHERE s.supplierAddressLine2 = :supplierAddressLine2")
    , @NamedQuery(name = "Supplier.findBySupplierCity", query = "SELECT s FROM Supplier s WHERE s.supplierCity = :supplierCity")
    , @NamedQuery(name = "Supplier.findBySupplierState", query = "SELECT s FROM Supplier s WHERE s.supplierState = :supplierState")
    , @NamedQuery(name = "Supplier.findBySupplierPostCode", query = "SELECT s FROM Supplier s WHERE s.supplierPostCode = :supplierPostCode")
    , @NamedQuery(name = "Supplier.findBySupplierCountry", query = "SELECT s FROM Supplier s WHERE s.supplierCountry = :supplierCountry")
    , @NamedQuery(name = "Supplier.findBySupplierPhone", query = "SELECT s FROM Supplier s WHERE s.supplierPhone = :supplierPhone")
    , @NamedQuery(name = "Supplier.findBySupplierEmail", query = "SELECT s FROM Supplier s WHERE s.supplierEmail = :supplierEmail")
    , @NamedQuery(name = "Supplier.findBySupplierWebsite", query = "SELECT s FROM Supplier s WHERE s.supplierWebsite = :supplierWebsite")})
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SupplierID")
    private Integer supplierID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SupplierCompanyName")
    private String supplierCompanyName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SupplierContactFirstName")
    private String supplierContactFirstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SupplierContactLastName")
    private String supplierContactLastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SupplierContactTitle")
    private String supplierContactTitle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "SupplierAddressLine1")
    private String supplierAddressLine1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SupplierAddressLine2")
    private String supplierAddressLine2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SupplierCity")
    private String supplierCity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "SupplierState")
    private String supplierState;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "SupplierPostCode")
    private String supplierPostCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "SupplierCountry")
    private String supplierCountry;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "SupplierPhone")
    private String supplierPhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "SupplierEmail")
    private String supplierEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SupplierWebsite")
    private String supplierWebsite;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierID")
    private Collection<Product> productCollection;

    public Supplier() {
    }

    public Supplier(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public Supplier(Integer supplierID, String supplierCompanyName, String supplierContactFirstName, String supplierContactLastName, String supplierContactTitle, String supplierAddressLine1, String supplierAddressLine2, String supplierCity, String supplierState, String supplierPostCode, String supplierCountry, String supplierPhone, String supplierEmail, String supplierWebsite) {
        this.supplierID = supplierID;
        this.supplierCompanyName = supplierCompanyName;
        this.supplierContactFirstName = supplierContactFirstName;
        this.supplierContactLastName = supplierContactLastName;
        this.supplierContactTitle = supplierContactTitle;
        this.supplierAddressLine1 = supplierAddressLine1;
        this.supplierAddressLine2 = supplierAddressLine2;
        this.supplierCity = supplierCity;
        this.supplierState = supplierState;
        this.supplierPostCode = supplierPostCode;
        this.supplierCountry = supplierCountry;
        this.supplierPhone = supplierPhone;
        this.supplierEmail = supplierEmail;
        this.supplierWebsite = supplierWebsite;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierCompanyName() {
        return supplierCompanyName;
    }

    public void setSupplierCompanyName(String supplierCompanyName) {
        this.supplierCompanyName = supplierCompanyName;
    }

    public String getSupplierContactFirstName() {
        return supplierContactFirstName;
    }

    public void setSupplierContactFirstName(String supplierContactFirstName) {
        this.supplierContactFirstName = supplierContactFirstName;
    }

    public String getSupplierContactLastName() {
        return supplierContactLastName;
    }

    public void setSupplierContactLastName(String supplierContactLastName) {
        this.supplierContactLastName = supplierContactLastName;
    }

    public String getSupplierContactTitle() {
        return supplierContactTitle;
    }

    public void setSupplierContactTitle(String supplierContactTitle) {
        this.supplierContactTitle = supplierContactTitle;
    }

    public String getSupplierAddressLine1() {
        return supplierAddressLine1;
    }

    public void setSupplierAddressLine1(String supplierAddressLine1) {
        this.supplierAddressLine1 = supplierAddressLine1;
    }

    public String getSupplierAddressLine2() {
        return supplierAddressLine2;
    }

    public void setSupplierAddressLine2(String supplierAddressLine2) {
        this.supplierAddressLine2 = supplierAddressLine2;
    }

    public String getSupplierCity() {
        return supplierCity;
    }

    public void setSupplierCity(String supplierCity) {
        this.supplierCity = supplierCity;
    }

    public String getSupplierState() {
        return supplierState;
    }

    public void setSupplierState(String supplierState) {
        this.supplierState = supplierState;
    }

    public String getSupplierPostCode() {
        return supplierPostCode;
    }

    public void setSupplierPostCode(String supplierPostCode) {
        this.supplierPostCode = supplierPostCode;
    }

    public String getSupplierCountry() {
        return supplierCountry;
    }

    public void setSupplierCountry(String supplierCountry) {
        this.supplierCountry = supplierCountry;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getSupplierWebsite() {
        return supplierWebsite;
    }

    public void setSupplierWebsite(String supplierWebsite) {
        this.supplierWebsite = supplierWebsite;
    }

    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplierID != null ? supplierID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) object;
        if ((this.supplierID == null && other.supplierID != null) || (this.supplierID != null && !this.supplierID.equals(other.supplierID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Supplier[ supplierID=" + supplierID + " ]";
    }
    
}
