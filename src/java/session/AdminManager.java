/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Category;
import entity.Product;
import entity.Supplier;
import java.math.BigDecimal;
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

// Treat write operations as a single transaction. This annotation is specifies that any transactions occurring in the AdminManager EJB are container-managed
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdminManager {

    @PersistenceContext(unitName = "PeripheralsPU")
    private EntityManager em;
    @Resource
    private SessionContext context;
    
    // This annotation specifies that any operations occurring in the setUpProduct method must be treated as part of the single transaction specified above transaction.
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void setUpProduct(String productCategory, String productSupplier, String productName, String price, String productDescription) {
        
        try {
            addNewProduct(productCategory, productSupplier, productName, price, productDescription);
        } catch (Exception e) {
            // Mechanism used in avoiding database corruption, by explicitly setting a trasaction to roll back if it fails.
            context.setRollbackOnly();
        }
    }

    private void addNewProduct(String productCategory, String productSupplier, String productName, String price, String productDescription) {
        Product product = new Product();
        
        // Create a category object and pass in the category number to the object
        Category category = new Category(Integer.parseInt(productCategory));
        
        // Use the category object to set the category ID
        product.setCategoryID(category);
        
        // Create a supplier object and pass in the supplier number to the object
        Supplier supplier = new Supplier(Integer.parseInt(productSupplier));
        
        // Use the supplier object to set the category ID
        product.setSupplierID(supplier);
        
        // Set product name
        product.setProductName(productName);
        
        // Firstly, convert price from string to double
        double productPrice = Double.parseDouble(price);
        
        // Then, convert double to BigDecimal
        product.setProductPrice(BigDecimal.valueOf(productPrice));
        
        // Set product description
        product.setProductDescription(productDescription);

        // Write to database
        em.persist(product);
    }
}
