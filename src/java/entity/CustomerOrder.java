/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@Entity
@Table(name = "customer_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerOrder.findAll", query = "SELECT c FROM CustomerOrder c")
    , @NamedQuery(name = "CustomerOrder.findByCustomerOrderID", query = "SELECT c FROM CustomerOrder c WHERE c.customerOrderID = :customerOrderID")
    , @NamedQuery(name = "CustomerOrder.findByCustomerID", query = "SELECT c FROM CustomerOrder c WHERE c.customerID = :customerID") // manually created to work with findByCustomerID method in CustomerOrderFacade
    , @NamedQuery(name = "CustomerOrder.findByCustomerOrderAmount", query = "SELECT c FROM CustomerOrder c WHERE c.customerOrderAmount = :customerOrderAmount")
    , @NamedQuery(name = "CustomerOrder.findByCustomerOrderConfirmationNumber", query = "SELECT c FROM CustomerOrder c WHERE c.customerOrderConfirmationNumber = :customerOrderConfirmationNumber")
    , @NamedQuery(name = "CustomerOrder.findByCustomerOrderCreated", query = "SELECT c FROM CustomerOrder c WHERE c.customerOrderCreated = :customerOrderCreated")})
public class CustomerOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CustomerOrderID")
    private Integer customerOrderID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "CustomerOrderAmount")
    private BigDecimal customerOrderAmount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CustomerOrderConfirmationNumber")
    private int customerOrderConfirmationNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CustomerOrderCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date customerOrderCreated;
    @JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID")
    @ManyToOne(optional = false)
    private Customer customerID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerOrder")
    private Collection<OrderedProduct> orderedProductCollection;

    public CustomerOrder() {
    }

    public CustomerOrder(Integer customerOrderID) {
        this.customerOrderID = customerOrderID;
    }

    public CustomerOrder(Integer customerOrderID, BigDecimal customerOrderAmount, int customerOrderConfirmationNumber, Date customerOrderCreated) {
        this.customerOrderID = customerOrderID;
        this.customerOrderAmount = customerOrderAmount;
        this.customerOrderConfirmationNumber = customerOrderConfirmationNumber;
        this.customerOrderCreated = customerOrderCreated;
    }

    public Integer getCustomerOrderID() {
        return customerOrderID;
    }

    public void setCustomerOrderID(Integer customerOrderID) {
        this.customerOrderID = customerOrderID;
    }

    public BigDecimal getCustomerOrderAmount() {
        return customerOrderAmount;
    }

    public void setCustomerOrderAmount(BigDecimal customerOrderAmount) {
        this.customerOrderAmount = customerOrderAmount;
    }

    public int getCustomerOrderConfirmationNumber() {
        return customerOrderConfirmationNumber;
    }

    public void setCustomerOrderConfirmationNumber(int customerOrderConfirmationNumber) {
        this.customerOrderConfirmationNumber = customerOrderConfirmationNumber;
    }

    public Date getCustomerOrderCreated() {
        return customerOrderCreated;
    }

    public void setCustomerOrderCreated(Date customerOrderCreated) {
        this.customerOrderCreated = customerOrderCreated;
    }

    public Customer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Customer customerID) {
        this.customerID = customerID;
    }

    @XmlTransient
    public Collection<OrderedProduct> getOrderedProductCollection() {
        return orderedProductCollection;
    }

    public void setOrderedProductCollection(Collection<OrderedProduct> orderedProductCollection) {
        this.orderedProductCollection = orderedProductCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerOrderID != null ? customerOrderID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerOrder)) {
            return false;
        }
        CustomerOrder other = (CustomerOrder) object;
        if ((this.customerOrderID == null && other.customerOrderID != null) || (this.customerOrderID != null && !this.customerOrderID.equals(other.customerOrderID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CustomerOrder[ customerOrderID=" + customerOrderID + " ]";
    }
    
}
