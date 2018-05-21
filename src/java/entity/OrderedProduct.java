/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@Entity
@Table(name = "ordered_product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderedProduct.findAll", query = "SELECT o FROM OrderedProduct o")
    , @NamedQuery(name = "OrderedProduct.findByCustomerOrderID", query = "SELECT o FROM OrderedProduct o WHERE o.orderedProductPK.customerOrderID = :customerOrderID")
    , @NamedQuery(name = "OrderedProduct.findByProductID", query = "SELECT o FROM OrderedProduct o WHERE o.orderedProductPK.productID = :productID")
    , @NamedQuery(name = "OrderedProduct.findByOrderedProductQuantity", query = "SELECT o FROM OrderedProduct o WHERE o.orderedProductQuantity = :orderedProductQuantity")
    , @NamedQuery(name = "OrderedProduct.findByOrderedProductCreated", query = "SELECT o FROM OrderedProduct o WHERE o.orderedProductCreated = :orderedProductCreated")})
public class OrderedProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderedProductPK orderedProductPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrderedProductQuantity")
    private short orderedProductQuantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrderedProductCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderedProductCreated;
    @JoinColumn(name = "CustomerOrderID", referencedColumnName = "CustomerOrderID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CustomerOrder customerOrder;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;

    public OrderedProduct() {
    }

    public OrderedProduct(OrderedProductPK orderedProductPK) {
        this.orderedProductPK = orderedProductPK;
    }

    public OrderedProduct(OrderedProductPK orderedProductPK, short orderedProductQuantity, Date orderedProductCreated) {
        this.orderedProductPK = orderedProductPK;
        this.orderedProductQuantity = orderedProductQuantity;
        this.orderedProductCreated = orderedProductCreated;
    }

    public OrderedProduct(int customerOrderID, int productID) {
        this.orderedProductPK = new OrderedProductPK(customerOrderID, productID);
    }

    public OrderedProductPK getOrderedProductPK() {
        return orderedProductPK;
    }

    public void setOrderedProductPK(OrderedProductPK orderedProductPK) {
        this.orderedProductPK = orderedProductPK;
    }

    public short getOrderedProductQuantity() {
        return orderedProductQuantity;
    }

    public void setOrderedProductQuantity(short orderedProductQuantity) {
        this.orderedProductQuantity = orderedProductQuantity;
    }

    public Date getOrderedProductCreated() {
        return orderedProductCreated;
    }

    public void setOrderedProductCreated(Date orderedProductCreated) {
        this.orderedProductCreated = orderedProductCreated;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderedProductPK != null ? orderedProductPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderedProduct)) {
            return false;
        }
        OrderedProduct other = (OrderedProduct) object;
        if ((this.orderedProductPK == null && other.orderedProductPK != null) || (this.orderedProductPK != null && !this.orderedProductPK.equals(other.orderedProductPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderedProduct[ orderedProductPK=" + orderedProductPK + " ]";
    }
    
}
