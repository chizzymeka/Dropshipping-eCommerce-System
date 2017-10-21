/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@Embeddable
public class OrderedProductPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "CustomerOrderID")
    private int customerOrderID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ProductID")
    private int productID;

    public OrderedProductPK() {
    }

    public OrderedProductPK(int customerOrderID, int productID) {
        this.customerOrderID = customerOrderID;
        this.productID = productID;
    }

    public int getCustomerOrderID() {
        return customerOrderID;
    }

    public void setCustomerOrderID(int customerOrderID) {
        this.customerOrderID = customerOrderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) customerOrderID;
        hash += (int) productID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderedProductPK)) {
            return false;
        }
        OrderedProductPK other = (OrderedProductPK) object;
        if (this.customerOrderID != other.customerOrderID) {
            return false;
        }
        if (this.productID != other.productID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderedProductPK[ customerOrderID=" + customerOrderID + ", productID=" + productID + " ]";
    }
    
}
