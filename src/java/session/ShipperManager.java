/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Shipper;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */

// Specfies that this bean  will be used for operations that can occur in a single method call.
@Stateless

// Treat write operations as a single transaction. This annotation is specifies that any transactions occurring in the ShipperManager EJB are container-managed
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ShipperManager {

    @PersistenceContext(unitName = "PeripheralsPU")
    private EntityManager em;
    @Resource
    private SessionContext context;
    
    // This annotation specifies that any operations occurring in the setUpShipper method must be treated as part of the single transaction specified above transaction.
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void setUpShipper(String shipperCompanyName, String shipperContactFirstName, String shipperContactLastName, String shipperContactTitle, String shipperAddressLine1, String shipperAddressLine2, String shipperCity, String shipperState, String shipperPostCode, String shipperCountry, String shipperPhone, String shipperEmail, String shipperWebsite) {
        
        try {
            addNewShipper(shipperCompanyName, shipperContactFirstName, shipperContactLastName, shipperContactTitle, shipperAddressLine1, shipperAddressLine2, shipperCity, shipperState, shipperPostCode, shipperCountry, shipperPhone, shipperEmail, shipperWebsite);
        } catch (Exception e) {
            // Mechanism used in avoiding database corruption, by explicitly setting a trasaction to roll back if it fails.
            context.setRollbackOnly();
        }
    }

    private void addNewShipper(String shipperCompanyName, String shipperContactFirstName, String shipperContactLastName, String shipperContactTitle, String shipperAddressLine1, String shipperAddressLine2, String shipperCity, String shipperState, String shipperPostCode, String shipperCountry, String shipperPhone, String shipperEmail, String shipperWebsite) {
        Shipper shipper = new Shipper();
        
        // Set values
        shipper.setShipperCompanyName(shipperCompanyName);
        shipper.setShipperCompanyName(shipperContactFirstName);
        shipper.setShipperCompanyName(shipperContactLastName);
        shipper.setShipperCompanyName(shipperContactTitle);
        shipper.setShipperCompanyName(shipperAddressLine1);
        shipper.setShipperCompanyName(shipperAddressLine2);
        shipper.setShipperCompanyName(shipperCity);
        shipper.setShipperCompanyName(shipperState);
        shipper.setShipperCompanyName(shipperPostCode);
        shipper.setShipperCompanyName(shipperCountry);
        shipper.setShipperCompanyName(shipperPhone);
        shipper.setShipperCompanyName(shipperEmail);
        shipper.setShipperCompanyName(shipperWebsite);
        
        // Write to database
        em.persist(shipper);
    }
}