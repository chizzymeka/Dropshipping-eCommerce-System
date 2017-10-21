/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderedProduct;
import entity.OrderedProductPK;
import entity.Product;
import static java.lang.System.out;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.EJB;
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
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrderManager {

    @PersistenceContext(unitName = "PeripheralsPU")
    private EntityManager em;
    @Resource
    private SessionContext context;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private CustomerOrderFacade customerOrderFacade;
    @EJB
    private OrderedProductFacade orderedProductFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int placeOrder(String title, String firstName, String lastName, String addressLine1, String addressLine2, String city, String state, String postCode, String country, String phone, String email, String creditCard, ShoppingCart cart) {
        out.print(title);
        out.print(firstName);
        out.print(lastName);
        out.print(addressLine1);
        out.print(addressLine2);
        out.print(city);
        out.print(state);
        out.print(postCode);
        out.print(country);
        out.print(phone);
        out.print(email);
        out.print(creditCard);
        
        try {
            Customer customer = addCustomer(title, firstName, lastName, addressLine1, addressLine2, city, state, postCode, country, phone, email, creditCard);
            CustomerOrder order = addOrder(customer, cart);
            addOrderedItems(order, cart);
            return order.getCustomerOrderID();
        } catch (Exception e) {
            context.setRollbackOnly();
            return 0;
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private Customer addCustomer(String title, String firstName, String lastName, String addressLine1, String addressLine2, String city, String state, String postCode, String country, String phone, String email, String creditCard) {
        out.print(title);
        out.print(firstName);
        out.print(lastName);
        out.print(addressLine1);
        out.print(addressLine2);
        out.print(city);
        out.print(state);
        out.print(postCode);
        out.print(country);
        out.print(phone);
        out.print(email);
        out.print(creditCard);

        Customer customer = new Customer();
        customer.setTitle(title);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddressLine1(addressLine1);
        customer.setAddressLine2(addressLine2);
        customer.setCity(city);
        customer.setState(state);
        customer.setPostCode(postCode);
        customer.setCountry(country);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setCreditCard(creditCard);

        em.persist(customer);
        return customer;
    }

    private CustomerOrder addOrder(Customer customer, ShoppingCart cart) {
        // Set up customer order
        CustomerOrder order = new CustomerOrder();
        order.setCustomerID(customer);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));
        order.setShipperID(null);
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        order.setCreated(currentTimestamp);

        // Create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmationNumber(i);
        out.print(i);
        
        em.persist(order);
        return order;
    }

    private void addOrderedItems(CustomerOrder order, ShoppingCart cart) {

        em.flush();

        List<ShoppingCartItem> items = cart.getItems();

        // Iterate through the shopping cart and create OrderedProducts
        for (ShoppingCartItem scItem : items) {
            int productId = scItem.getProduct().getProductID();

            // set up primary key object
            OrderedProductPK orderedProductPK = new OrderedProductPK();
            orderedProductPK.setCustomerOrderID(order.getCustomerOrderID());
            orderedProductPK.setProductID(productId);

            // create ordered item using PK object
            OrderedProduct orderedItem = new OrderedProduct(orderedProductPK);

            // set quantity
            orderedItem.setQuantity(scItem.getQuantity());

            em.persist(orderedItem);
        }
    }

    public Map getOrderDetails(int orderId) {

        Map orderMap = new HashMap();

        // get order
        CustomerOrder order = customerOrderFacade.find(orderId);

        // get customer
        Customer customer = order.getCustomerID();

        // get all ordered products
        List<OrderedProduct> orderedProducts = orderedProductFacade.findByOrderId(orderId);

        // get product details for ordered items
        List<Product> products = new ArrayList<Product>();

        for (OrderedProduct op : orderedProducts) {
            Product p = (Product) productFacade.find(op.getOrderedProductPK().getProductID());
            products.add(p);
        }

        // add each item to orderMap
        orderMap.put("orderRecord", order);
        orderMap.put("customer", customer);
        orderMap.put("orderedProducts", orderedProducts);
        orderMap.put("products", products);

        return orderMap;
    }
}
