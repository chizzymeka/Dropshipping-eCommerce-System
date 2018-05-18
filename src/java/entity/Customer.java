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
@Table(name = "customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
    , @NamedQuery(name = "Customer.findByCustomerID", query = "SELECT c FROM Customer c WHERE c.customerID = :customerID")
    , @NamedQuery(name = "Customer.findByCustomerTitle", query = "SELECT c FROM Customer c WHERE c.customerTitle = :customerTitle")
    , @NamedQuery(name = "Customer.findByCustomerFirstName", query = "SELECT c FROM Customer c WHERE c.customerFirstName = :customerFirstName")
    , @NamedQuery(name = "Customer.findByCustomerLastName", query = "SELECT c FROM Customer c WHERE c.customerLastName = :customerLastName")
    , @NamedQuery(name = "Customer.findByCustomerAddressLine1", query = "SELECT c FROM Customer c WHERE c.customerAddressLine1 = :customerAddressLine1")
    , @NamedQuery(name = "Customer.findByCustomerAddressLine2", query = "SELECT c FROM Customer c WHERE c.customerAddressLine2 = :customerAddressLine2")
    , @NamedQuery(name = "Customer.findByCustomerCity", query = "SELECT c FROM Customer c WHERE c.customerCity = :customerCity")
    , @NamedQuery(name = "Customer.findByCustomerState", query = "SELECT c FROM Customer c WHERE c.customerState = :customerState")
    , @NamedQuery(name = "Customer.findByCustomerPostCode", query = "SELECT c FROM Customer c WHERE c.customerPostCode = :customerPostCode")
    , @NamedQuery(name = "Customer.findByCustomerCountry", query = "SELECT c FROM Customer c WHERE c.customerCountry = :customerCountry")
    , @NamedQuery(name = "Customer.findByCustomerPhone", query = "SELECT c FROM Customer c WHERE c.customerPhone = :customerPhone")
    , @NamedQuery(name = "Customer.findByCustomerEmail", query = "SELECT c FROM Customer c WHERE c.customerEmail = :customerEmail")
    , @NamedQuery(name = "Customer.findByCustomerCreditCard", query = "SELECT c FROM Customer c WHERE c.customerCreditCard = :customerCreditCard")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CustomerID")
    private Integer customerID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CustomerTitle")
    private String customerTitle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CustomerFirstName")
    private String customerFirstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CustomerLastName")
    private String customerLastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "CustomerAddressLine1")
    private String customerAddressLine1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CustomerAddressLine2")
    private String customerAddressLine2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CustomerCity")
    private String customerCity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "CustomerState")
    private String customerState;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "CustomerPostCode")
    private String customerPostCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CustomerCountry")
    private String customerCountry;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "CustomerPhone")
    private String customerPhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "CustomerEmail")
    private String customerEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 19)
    @Column(name = "CustomerCreditCard")
    private String customerCreditCard;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerID")
    private Collection<CustomerOrder> customerOrderCollection;

    public Customer() {
    }

    public Customer(Integer customerID) {
        this.customerID = customerID;
    }

    public Customer(Integer customerID, String customerTitle, String customerFirstName, String customerLastName, String customerAddressLine1, String customerAddressLine2, String customerCity, String customerState, String customerPostCode, String customerCountry, String customerPhone, String customerEmail, String customerCreditCard) {
        this.customerID = customerID;
        this.customerTitle = customerTitle;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerAddressLine1 = customerAddressLine1;
        this.customerAddressLine2 = customerAddressLine2;
        this.customerCity = customerCity;
        this.customerState = customerState;
        this.customerPostCode = customerPostCode;
        this.customerCountry = customerCountry;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.customerCreditCard = customerCreditCard;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerTitle() {
        return customerTitle;
    }

    public void setCustomerTitle(String customerTitle) {
        this.customerTitle = customerTitle;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerAddressLine1() {
        return customerAddressLine1;
    }

    public void setCustomerAddressLine1(String customerAddressLine1) {
        this.customerAddressLine1 = customerAddressLine1;
    }

    public String getCustomerAddressLine2() {
        return customerAddressLine2;
    }

    public void setCustomerAddressLine2(String customerAddressLine2) {
        this.customerAddressLine2 = customerAddressLine2;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getCustomerPostCode() {
        return customerPostCode;
    }

    public void setCustomerPostCode(String customerPostCode) {
        this.customerPostCode = customerPostCode;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerCreditCard() {
        return customerCreditCard;
    }

    public void setCustomerCreditCard(String customerCreditCard) {
        this.customerCreditCard = customerCreditCard;
    }

    @XmlTransient
    public Collection<CustomerOrder> getCustomerOrderCollection() {
        return customerOrderCollection;
    }

    public void setCustomerOrderCollection(Collection<CustomerOrder> customerOrderCollection) {
        this.customerOrderCollection = customerOrderCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerID != null ? customerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerID == null && other.customerID != null) || (this.customerID != null && !this.customerID.equals(other.customerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ customerID=" + customerID + " ]";
    }
    
}
