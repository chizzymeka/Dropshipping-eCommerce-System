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
import entity.Shipper;
import static java.lang.System.out;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    private CustomerFacade customerFacade;
    @EJB
    private OrderedProductFacade orderedProductFacade;
    @EJB
    private SupplierFacade supplierFacade;
    @EJB
    private ShipperFacade shipperFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @SuppressWarnings({"unchecked", "unchecked"})
    public int placeOrder(String title, String firstName, String lastName, String phone, String email, String addressLine1, String addressLine2, String city, String state, String postCode, String country, String creditCard, ShoppingCart cart) {

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
            Customer customer = addCustomer(title, firstName, lastName, phone, email, addressLine1, addressLine2, city, state, postCode, country);
            CustomerOrder order = addOrder(customer, cart);
            addOrderedItems(order, cart);

            int customerID = order.getCustomerID().getCustomerID();
            int orderId = order.getCustomerOrderID();
            int confirmationNumber = order.getCustomerOrderConfirmationNumber();
            Collection<OrderedProduct> orderedProducts = order.getOrderedProductCollection();
            BigDecimal orderAmount = order.getCustomerOrderAmount();
            Date orderDate = order.getCustomerOrderCreated();

            // Send email notifications
            sendAdminOrderNotificationEmail(customerID, orderId, confirmationNumber, orderedProducts, orderAmount, orderDate);
            sendCustomerOrderNotificationEmail(customerID, orderId, confirmationNumber, orderedProducts, orderAmount, orderDate);
            sendShipperOrderNotificationEmail(customerID, orderId, confirmationNumber, orderedProducts, orderAmount, orderDate);
            sendSupplierOrderNotificationEmail(customerID, orderId, confirmationNumber, orderedProducts, orderDate);

            return orderId;
        } catch (Exception e) {
            context.setRollbackOnly();
            return 0;
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private Customer addCustomer(String title, String firstName, String lastName, String phone, String email, String addressLine1, String addressLine2, String city, String state, String postCode, String country) {
        out.print(title);
        out.print(firstName);
        out.print(lastName);
        out.print(phone);
        out.print(email);
        out.print(addressLine1);
        out.print(addressLine2);
        out.print(city);
        out.print(state);
        out.print(postCode);
        out.print(country);

        Customer customer = new Customer();
        customer.setCustomerTitle(title);
        customer.setCustomerFirstName(firstName);
        customer.setCustomerLastName(lastName);
        customer.setCustomerPhone(phone);
        customer.setCustomerEmail(email);
        customer.setCustomerAddressLine1(addressLine1);
        customer.setCustomerAddressLine2(addressLine2);
        customer.setCustomerCity(city);
        customer.setCustomerState(state);
        customer.setCustomerPostCode(postCode);
        customer.setCustomerCountry(country);

        em.persist(customer);
        return customer;
    }

    private CustomerOrder addOrder(Customer customer, ShoppingCart cart) {
        // Set up customer order
        CustomerOrder order = new CustomerOrder();
        order.setCustomerID(customer);
        order.setCustomerOrderAmount(BigDecimal.valueOf(cart.getTotal()));

        // Get current timestamp
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        order.setCustomerOrderCreated(currentTimestamp);

        // Create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setCustomerOrderConfirmationNumber(i);
        out.print(i);

        // Save order
        em.persist(order);

        return order;
    }

    private void addOrderedItems(CustomerOrder order, ShoppingCart cart) {

        em.flush();

        List<ShoppingCartItem> items = cart.getItems();

        // Iterate through the shopping cart and create OrderedProducts
        for (ShoppingCartItem scItem : items) {
            int productId = scItem.getProduct().getProductID();
            int productInventoryCount = scItem.getProduct().getProductInventoryCount();

            // set up primary key object
            OrderedProductPK orderedProductPK = new OrderedProductPK();
            orderedProductPK.setCustomerOrderID(order.getCustomerOrderID());
            orderedProductPK.setProductID(productId);

            // create ordered item using PK object
            OrderedProduct orderedItem = new OrderedProduct(orderedProductPK);

            // set quantity
            orderedItem.setOrderedProductQuantity(scItem.getQuantity());

            int orderedQuantity = orderedItem.getOrderedProductQuantity();

            if (productInventoryCount >= orderedQuantity) {
                // save ordered item
                em.persist(orderedItem);

                // deduct ordered quantity from inventory count
                int newproductInventoryCount = productInventoryCount - orderedQuantity;
                Product product = productFacade.find(productId);

                // set the inveorty count as the updated value
                product.setProductInventoryCount(newproductInventoryCount);

            } else if (productInventoryCount < orderedQuantity) {
                System.out.println("The Ordered Quantity for Product: " + productFacade.find(productId).getProductName() + "is higher than what we currently hold in stock.");
            }
        }
    }

    @SuppressWarnings("unchecked")
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

    // Customer Order Email Notification
    public int sendCustomerOrderNotificationEmail(int customerID, int orderId, int confirmationNumber, Collection<OrderedProduct> orderedProducts, BigDecimal orderAmount, Date orderDate) {
        // SMTP Setting
        String smtpServer = "smtp.gmail.com";

        //Email Addresses
        String from = "chizzymeka@gmail.com";
        String customerEmail = customerFacade.find(customerID).getCustomerEmail().trim();
        String to = customerEmail;
        String bcc = "chizzymeka@yahoo.co.uk";

        //Message
        String subject = "Order Confirmation: " + orderId + "|" + "Confirmation Number: " + confirmationNumber;
        String message = "Thanks for your order. We’ll let you know once your item(s) have dispatched.";
        String productNameAndQuantitySubheader = "<tr><td>Product</td><td>Quantity</td></tr>";
        String productNameAndQuantity = null;

        for (OrderedProduct op : orderedProducts) {
            String productName = op.getProduct().getProductName();
            int productQuantity = op.getOrderedProductQuantity();
            productNameAndQuantity += "<tr><td>" + productName + "</td><td>" + productQuantity + "</td></tr>";
        }

        String messageBody
                = "<table>"
                + "<tr><td colspan=2>" + message + "</td></tr>"
                + "<tr><td colspan=2>Order Details</td></tr>"
                + "<tr><td>Order Number:</td><td>" + orderId + "</td></tr>"
                + "<tr><td>Confirmation Number:</td><td>" + confirmationNumber + "</td></tr>"
                + "<tr><td>Order Amount:</td><td>" + orderAmount + "</td></tr>"
                + "<tr><td>Order Date:</td><td>" + orderDate + "</td></tr>"
                + productNameAndQuantitySubheader
                + productNameAndQuantity
                + "</table>";

        try {
            Properties properties = System.getProperties();
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", smtpServer);
            properties.put("mail.smtp.auth", "true");
            Authenticator authenticator = new SMTPAuthenticator();
            Session session = Session.getInstance(properties, authenticator);

            // Create a new messageBody
            Message mimeMessage = new MimeMessage(session);

            // Set the FROM and TO fields
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, false)); // Change this to be hard-coded Peripherals email
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(messageBody, "text/html; charset=utf-8");

            // Set some other header information
            mimeMessage.setHeader("Order Confirmation", "Peripherals");
            mimeMessage.setSentDate(new Date());

            // Send the messageBody
            Transport.send(mimeMessage);
            System.out.println("Message sent successfully!");
            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception " + ex);
            return -1;
        }
    }

    // Supplier Order Email Notification
    public int sendSupplierOrderNotificationEmail(int customerID, int orderId, int confirmationNumber, Collection<OrderedProduct> orderedProducts, Date orderDate) {
        // SMTP Setting
        String smtpServer = "smtp.gmail.com";

        //Email Addresses
        String from = "chizzymeka@gmail.com";
        for (OrderedProduct orderedProduct_outer : orderedProducts) {
            int supplierId = orderedProduct_outer.getProduct().getSupplierID().getSupplierID();
            String supplierEmail = supplierFacade.find(supplierId).getSupplierEmail().trim();

            String to = supplierEmail;
            String bcc = "chizzymeka@yahoo.co.uk";

            //Message
            String subject = "New Order for Collection: " + orderId + "|" + "Confirmation Number: " + confirmationNumber;
            String message = "Please liaise with our shipper as soon as possible to get the products listed below shipped to the customer.";
            String productNameAndQuantitySubheader = "<tr><td>Product</td><td>Quantity</td></tr>";
            String productNameAndQuantity = null;

            for (OrderedProduct orderedProduct_inner : orderedProducts) {
                
                int productSupplier = orderedProduct_inner.getProduct().getSupplierID().getSupplierID();
                
                // if productSupplier matches the supplier's id whom email has been retrived above, then send them the product and quantity details below
                if(supplierId == productSupplier){
                    String productName = orderedProduct_inner.getProduct().getProductName();
                    int productQuantity = orderedProduct_inner.getOrderedProductQuantity();
                    productNameAndQuantity += "<tr><td>" + productName + "</td><td>" + productQuantity + "</td></tr>";
                }
            }

            String messageBody
                    = "<table>"
                    + "<tr><td colspan=2>" + message + "</td></tr>"
                    + "<tr><td colspan=2>Order Details</td></tr>"
                    + "<tr><td>Order Number:</td><td>" + orderId + "</td></tr>"
                    + "<tr><td>Confirmation Number:</td><td>" + confirmationNumber + "</td></tr>"
                    + "<tr><td>Order Date:</td><td>" + orderDate + "</td></tr>"
                    + productNameAndQuantitySubheader
                    + productNameAndQuantity
                    + "</table>";

            try {
                Properties properties = System.getProperties();
                properties.put("mail.transport.protocol", "smtp");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", smtpServer);
                properties.put("mail.smtp.auth", "true");
                Authenticator authenticator = new SMTPAuthenticator();
                Session session = Session.getInstance(properties, authenticator);

                // Create a new messageBody
                Message mimeMessage = new MimeMessage(session);

                // Set the FROM and TO fields
                mimeMessage.setFrom(new InternetAddress(from));
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
                mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, false)); // Change this to be hard-coded Peripherals email
                mimeMessage.setSubject(subject);
                mimeMessage.setContent(messageBody, "text/html; charset=utf-8");

                // Set some other header information
                mimeMessage.setHeader("Order Confirmation", "Peripherals");
                mimeMessage.setSentDate(new Date());

                // Send the messageBody
                Transport.send(mimeMessage);
                System.out.println("Message sent successfully!");
                return 0;
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Exception " + ex);
                return -1;
            }
        }
        return 1;
    }

    // Shipper Order Email Notification
    public int sendShipperOrderNotificationEmail(int customerID, int orderId, int confirmationNumber, Collection<OrderedProduct> orderedProducts, BigDecimal orderAmount, Date orderDate) {
        // SMTP Setting
        String smtpServer = "smtp.gmail.com";

        //Email Addresses
        String from = "chizzymeka@gmail.com";
        String shipperEmail = null;
        
        // Look for the activated shipper and obtain thier email aaddress
        List<Shipper> shipperList = shipperFacade.findAll();
        for(Shipper shipper : shipperList){
            boolean shipperActivated = shipper.getShipperActivated();
            
            if(shipperActivated){
                shipperEmail = shipper.getShipperEmail().trim();
            }
        }
        
        String to = shipperEmail;
        String bcc = "chizzymeka@yahoo.co.uk";

        //Message
        String subject = "New Order for Collection: " + orderId + "|" + "Confirmation Number: " + confirmationNumber;
        String message = "Please liaise with the listed suppliers below to collect the orders for the subject customers.";
        String productDetailsAndAssociatedSupplierDetails = null;

        for (OrderedProduct op : orderedProducts) {
            String productName = op.getProduct().getProductName();
            int productQuantity = op.getOrderedProductQuantity();
            String supplierName = op.getProduct().getSupplierID().getSupplierCompanyName();
            String supplierPhone = op.getProduct().getSupplierID().getSupplierPhone();
            String supplierEmail = op.getProduct().getSupplierID().getSupplierEmail();
            productDetailsAndAssociatedSupplierDetails += "<tr><td>" + productName + "</td><td>" + productQuantity + "</td><td>" + supplierName + "</td><td>" + supplierPhone + "</td><td>" + supplierEmail + "</td></tr>";
        }

        String messageBody
                = "<table>"
                + "<tr><td colspan=5>" + message + "</td></tr>"
                + "<tr><td colspan=5>Order Number:</td><td>" + orderId + "</td></tr>"
                + "<tr><td colspan=5>Confirmation Number:</td><td>" + confirmationNumber + "</td></tr>"
                + "<tr><td colspan=5>Amount:</td><td>" + orderAmount + "</td></tr>"
                + "<tr><td colspan=5>Order Date:</td><td>" + orderDate + "</td></tr>"
                + "<tr><td>Product Name</td><td>Quantity</td><td>Suplier Name</td><td>Supplier Phone</td><td>Supplier Email</td></tr>"
                + productDetailsAndAssociatedSupplierDetails
                + "</table>";

        try {
            Properties properties = System.getProperties();
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", smtpServer);
            properties.put("mail.smtp.auth", "true");
            Authenticator authenticator = new SMTPAuthenticator();
            Session session = Session.getInstance(properties, authenticator);

            // Create a new messageBody
            Message mimeMessage = new MimeMessage(session);

            // Set the FROM and TO fields
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, false)); // Change this to be hard-coded Peripherals email
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(messageBody, "text/html; charset=utf-8");

            // Set some other header information
            mimeMessage.setHeader("Order Confirmation", "Peripherals");
            mimeMessage.setSentDate(new Date());

            // Send the messageBody
            Transport.send(mimeMessage);
            System.out.println("Message sent successfully!");
            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception " + ex);
            return -1;
        }
    }

    public int sendAdminOrderNotificationEmail(int customerID, int orderId, int confirmationNumber, Collection<OrderedProduct> orderedProducts, BigDecimal orderAmount, Date orderDate) {
        // SMTP Setting
        String smtpServer = "smtp.gmail.com";

        //Email Addresses
        String from = "chizzymeka@gmail.com";
        String customerEmail = customerFacade.find(customerID).getCustomerEmail().trim();
        String to = customerEmail;
        String bcc = "chizzymeka@yahoo.co.uk";

        //Message
        String subject = "Order Confirmation: " + orderId + "|" + "Confirmation Number: " + confirmationNumber;
        String message = "Thanks for your order. We’ll let you know once your item(s) have dispatched.";
        String productNameAndQuantitySubheader = "<tr><td>Product</td><td>Quantity</td></tr>";
        String productNameAndQuantity = null;

        for (OrderedProduct op : orderedProducts) {
            String productName = op.getProduct().getProductName();
            int productQuantity = op.getOrderedProductQuantity();
            productNameAndQuantity += "<tr><td>" + productName + "</td><td>" + productQuantity + "</td></tr>";
        }

        String messageBody
                = "<table>"
                + "<tr><td colspan=2>" + message + "</td></tr>"
                + "<tr><td colspan=2>Order Details</td></tr>"
                + "<tr><td>Order Number:</td><td>" + orderId + "</td></tr>"
                + "<tr><td>Confirmation Number:</td><td>" + confirmationNumber + "</td></tr>"
                + "<tr><td>Order Amount:</td><td>" + orderAmount + "</td></tr>"
                + "<tr><td>Order Date:</td><td>" + orderDate + "</td></tr>"
                + productNameAndQuantitySubheader
                + productNameAndQuantity
                + "</table>";

        try {
            Properties properties = System.getProperties();
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", smtpServer);
            properties.put("mail.smtp.auth", "true");
            Authenticator authenticator = new SMTPAuthenticator();
            Session session = Session.getInstance(properties, authenticator);

            // Create a new messageBody
            Message mimeMessage = new MimeMessage(session);

            // Set the FROM and TO fields
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, false)); // Change this to be hard-coded Peripherals email
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(messageBody, "text/html; charset=utf-8");

            // Set some other header information
            mimeMessage.setHeader("Order Confirmation", "Peripherals");
            mimeMessage.setSentDate(new Date());

            // Send the messageBody
            Transport.send(mimeMessage);
            System.out.println("Message sent successfully!");
            return 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception " + ex);
            return -1;
        }
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "chizzymeka@gmail.com"; // specify your email id here (sender's email id)
            String password = "kalakuta5114"; // specify your password here
            return new PasswordAuthentication(username, password);
        }
    }
}
