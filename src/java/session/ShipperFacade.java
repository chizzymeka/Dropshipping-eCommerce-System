/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Shipper;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@Stateless
public class ShipperFacade extends AbstractFacade<Shipper> {

    @PersistenceContext(unitName = "PeripheralsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ShipperFacade() {
        super(Shipper.class);
    }
    
}
