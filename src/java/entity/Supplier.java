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
    , @NamedQuery(name = "Supplier.findByCompanyName", query = "SELECT s FROM Supplier s WHERE s.companyName = :companyName")
    , @NamedQuery(name = "Supplier.findByContactFirstName", query = "SELECT s FROM Supplier s WHERE s.contactFirstName = :contactFirstName")
    , @NamedQuery(name = "Supplier.findByContactLastName", query = "SELECT s FROM Supplier s WHERE s.contactLastName = :contactLastName")
    , @NamedQuery(name = "Supplier.findByContactTitle", query = "SELECT s FROM Supplier s WHERE s.contactTitle = :contactTitle")
    , @NamedQuery(name = "Supplier.findByAddressLine1", query = "SELECT s FROM Supplier s WHERE s.addressLine1 = :addressLine1")
    , @NamedQuery(name = "Supplier.findByAddressLine2", query = "SELECT s FROM Supplier s WHERE s.addressLine2 = :addressLine2")
    , @NamedQuery(name = "Supplier.findByCity", query = "SELECT s FROM Supplier s WHERE s.city = :city")
    , @NamedQuery(name = "Supplier.findByState", query = "SELECT s FROM Supplier s WHERE s.state = :state")
    , @NamedQuery(name = "Supplier.findByPostCode", query = "SELECT s FROM Supplier s WHERE s.postCode = :postCode")
    , @NamedQuery(name = "Supplier.findByCountry", query = "SELECT s FROM Supplier s WHERE s.country = :country")
    , @NamedQuery(name = "Supplier.findByPhone", query = "SELECT s FROM Supplier s WHERE s.phone = :phone")
    , @NamedQuery(name = "Supplier.findByEmail", query = "SELECT s FROM Supplier s WHERE s.email = :email")
    , @NamedQuery(name = "Supplier.findByWebsite", query = "SELECT s FROM Supplier s WHERE s.website = :website")
    , @NamedQuery(name = "Supplier.findByStockLevel", query = "SELECT s FROM Supplier s WHERE s.stockLevel = :stockLevel")})
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
    @Column(name = "CompanyName")
    private String companyName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ContactFirstName")
    private String contactFirstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ContactLastName")
    private String contactLastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ContactTitle")
    private String contactTitle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "AddressLine1")
    private String addressLine1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "AddressLine2")
    private String addressLine2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "City")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "State")
    private String state;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "PostCode")
    private String postCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Country")
    private String country;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "Phone")
    private String phone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Website")
    private String website;
    @Basic(optional = false)
    @NotNull
    @Column(name = "StockLevel")
    private int stockLevel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierID")
    private Collection<Product> productCollection;

    public Supplier() {
    }

    public Supplier(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public Supplier(Integer supplierID, String companyName, String contactFirstName, String contactLastName, String contactTitle, String addressLine1, String addressLine2, String city, String state, String postCode, String country, String phone, String email, String website, int stockLevel) {
        this.supplierID = supplierID;
        this.companyName = companyName;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactTitle = contactTitle;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postCode = postCode;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.stockLevel = stockLevel;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
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
