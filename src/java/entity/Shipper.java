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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@Entity
@Table(name = "shipper")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shipper.findAll", query = "SELECT s FROM Shipper s")
    , @NamedQuery(name = "Shipper.findByShipperID", query = "SELECT s FROM Shipper s WHERE s.shipperID = :shipperID")
    , @NamedQuery(name = "Shipper.findByShipperCompanyName", query = "SELECT s FROM Shipper s WHERE s.shipperCompanyName = :shipperCompanyName")
    , @NamedQuery(name = "Shipper.findByShipperContactFirstName", query = "SELECT s FROM Shipper s WHERE s.shipperContactFirstName = :shipperContactFirstName")
    , @NamedQuery(name = "Shipper.findByShipperContactLastName", query = "SELECT s FROM Shipper s WHERE s.shipperContactLastName = :shipperContactLastName")
    , @NamedQuery(name = "Shipper.findByShipperContactTitle", query = "SELECT s FROM Shipper s WHERE s.shipperContactTitle = :shipperContactTitle")
    , @NamedQuery(name = "Shipper.findByShipperAddressLine1", query = "SELECT s FROM Shipper s WHERE s.shipperAddressLine1 = :shipperAddressLine1")
    , @NamedQuery(name = "Shipper.findByShipperAddressLine2", query = "SELECT s FROM Shipper s WHERE s.shipperAddressLine2 = :shipperAddressLine2")
    , @NamedQuery(name = "Shipper.findByShipperCity", query = "SELECT s FROM Shipper s WHERE s.shipperCity = :shipperCity")
    , @NamedQuery(name = "Shipper.findByShipperState", query = "SELECT s FROM Shipper s WHERE s.shipperState = :shipperState")
    , @NamedQuery(name = "Shipper.findByShipperPostCode", query = "SELECT s FROM Shipper s WHERE s.shipperPostCode = :shipperPostCode")
    , @NamedQuery(name = "Shipper.findByShipperCountry", query = "SELECT s FROM Shipper s WHERE s.shipperCountry = :shipperCountry")
    , @NamedQuery(name = "Shipper.findByShipperPhone", query = "SELECT s FROM Shipper s WHERE s.shipperPhone = :shipperPhone")
    , @NamedQuery(name = "Shipper.findByShipperEmail", query = "SELECT s FROM Shipper s WHERE s.shipperEmail = :shipperEmail")
    , @NamedQuery(name = "Shipper.findByShipperWebsite", query = "SELECT s FROM Shipper s WHERE s.shipperWebsite = :shipperWebsite")
    , @NamedQuery(name = "Shipper.findByShipperActivated", query = "SELECT s FROM Shipper s WHERE s.shipperActivated = :shipperActivated")
    , @NamedQuery(name = "Shipper.findByShipperCreated", query = "SELECT s FROM Shipper s WHERE s.shipperCreated = :shipperCreated")})
public class Shipper implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ShipperID")
    private Integer shipperID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ShipperCompanyName")
    private String shipperCompanyName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ShipperContactFirstName")
    private String shipperContactFirstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ShipperContactLastName")
    private String shipperContactLastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ShipperContactTitle")
    private String shipperContactTitle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "ShipperAddressLine1")
    private String shipperAddressLine1;
    @Size(max = 50)
    @Column(name = "ShipperAddressLine2")
    private String shipperAddressLine2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ShipperCity")
    private String shipperCity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "ShipperState")
    private String shipperState;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ShipperPostCode")
    private String shipperPostCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ShipperCountry")
    private String shipperCountry;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "ShipperPhone")
    private String shipperPhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "ShipperEmail")
    private String shipperEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ShipperWebsite")
    private String shipperWebsite;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ShipperActivated")
    private boolean shipperActivated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ShipperCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shipperCreated;
    @JoinColumn(name = "AuthenticationID", referencedColumnName = "AuthenticationID")
    @ManyToOne(optional = false)
    private Authentication authenticationID;

    public Shipper() {
    }

    public Shipper(Integer shipperID) {
        this.shipperID = shipperID;
    }

    public Shipper(Integer shipperID, String shipperCompanyName, String shipperContactFirstName, String shipperContactLastName, String shipperContactTitle, String shipperAddressLine1, String shipperCity, String shipperState, String shipperPostCode, String shipperCountry, String shipperPhone, String shipperEmail, String shipperWebsite, boolean shipperActivated, Date shipperCreated) {
        this.shipperID = shipperID;
        this.shipperCompanyName = shipperCompanyName;
        this.shipperContactFirstName = shipperContactFirstName;
        this.shipperContactLastName = shipperContactLastName;
        this.shipperContactTitle = shipperContactTitle;
        this.shipperAddressLine1 = shipperAddressLine1;
        this.shipperCity = shipperCity;
        this.shipperState = shipperState;
        this.shipperPostCode = shipperPostCode;
        this.shipperCountry = shipperCountry;
        this.shipperPhone = shipperPhone;
        this.shipperEmail = shipperEmail;
        this.shipperWebsite = shipperWebsite;
        this.shipperActivated = shipperActivated;
        this.shipperCreated = shipperCreated;
    }

    public Integer getShipperID() {
        return shipperID;
    }

    public void setShipperID(Integer shipperID) {
        this.shipperID = shipperID;
    }

    public String getShipperCompanyName() {
        return shipperCompanyName;
    }

    public void setShipperCompanyName(String shipperCompanyName) {
        this.shipperCompanyName = shipperCompanyName;
    }

    public String getShipperContactFirstName() {
        return shipperContactFirstName;
    }

    public void setShipperContactFirstName(String shipperContactFirstName) {
        this.shipperContactFirstName = shipperContactFirstName;
    }

    public String getShipperContactLastName() {
        return shipperContactLastName;
    }

    public void setShipperContactLastName(String shipperContactLastName) {
        this.shipperContactLastName = shipperContactLastName;
    }

    public String getShipperContactTitle() {
        return shipperContactTitle;
    }

    public void setShipperContactTitle(String shipperContactTitle) {
        this.shipperContactTitle = shipperContactTitle;
    }

    public String getShipperAddressLine1() {
        return shipperAddressLine1;
    }

    public void setShipperAddressLine1(String shipperAddressLine1) {
        this.shipperAddressLine1 = shipperAddressLine1;
    }

    public String getShipperAddressLine2() {
        return shipperAddressLine2;
    }

    public void setShipperAddressLine2(String shipperAddressLine2) {
        this.shipperAddressLine2 = shipperAddressLine2;
    }

    public String getShipperCity() {
        return shipperCity;
    }

    public void setShipperCity(String shipperCity) {
        this.shipperCity = shipperCity;
    }

    public String getShipperState() {
        return shipperState;
    }

    public void setShipperState(String shipperState) {
        this.shipperState = shipperState;
    }

    public String getShipperPostCode() {
        return shipperPostCode;
    }

    public void setShipperPostCode(String shipperPostCode) {
        this.shipperPostCode = shipperPostCode;
    }

    public String getShipperCountry() {
        return shipperCountry;
    }

    public void setShipperCountry(String shipperCountry) {
        this.shipperCountry = shipperCountry;
    }

    public String getShipperPhone() {
        return shipperPhone;
    }

    public void setShipperPhone(String shipperPhone) {
        this.shipperPhone = shipperPhone;
    }

    public String getShipperEmail() {
        return shipperEmail;
    }

    public void setShipperEmail(String shipperEmail) {
        this.shipperEmail = shipperEmail;
    }

    public String getShipperWebsite() {
        return shipperWebsite;
    }

    public void setShipperWebsite(String shipperWebsite) {
        this.shipperWebsite = shipperWebsite;
    }

    public boolean getShipperActivated() {
        return shipperActivated;
    }

    public void setShipperActivated(boolean shipperActivated) {
        this.shipperActivated = shipperActivated;
    }

    public Date getShipperCreated() {
        return shipperCreated;
    }

    public void setShipperCreated(Date shipperCreated) {
        this.shipperCreated = shipperCreated;
    }

    public Authentication getAuthenticationID() {
        return authenticationID;
    }

    public void setAuthenticationID(Authentication authenticationID) {
        this.authenticationID = authenticationID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shipperID != null ? shipperID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shipper)) {
            return false;
        }
        Shipper other = (Shipper) object;
        if ((this.shipperID == null && other.shipperID != null) || (this.shipperID != null && !this.shipperID.equals(other.shipperID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Shipper[ shipperID=" + shipperID + " ]";
    }
    
}
