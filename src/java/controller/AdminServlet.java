/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Customer;
import entity.CustomerOrder;
import entity.Product;
import entity.Shipper;
import entity.Supplier;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import session.AdminManager;
import session.CategoryFacade;
import session.CustomerFacade;
import session.CustomerOrderFacade;
import session.OrderManager;
import session.ProductFacade;
import session.ShipperFacade;
import session.ShipperManager;
import session.SupplierFacade;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@WebServlet(name = "AdminServlet",
            urlPatterns = {// When user access the main admin console page
                            "/admin/",
                
                           // When user clicks on a customer to view customer details
                           "/admin/customerRecord",
                           
                           // When user logs into the console
                           "/admin/login",
                           
                           // When user logs out of the console
                           "/admin/logout",
                           
                           // When user clicks on 'Add New Product' button
                           "/addNewProduct",
                           
                           //When user clicks on 'Add New Product' Link
                           "/admin/addNewProductForm",
                           
                           //When user clicks on 'Add New Shipper' button
                           "/admin/addNewShipper",
                           
                           //When user clicks on 'Add New Shipper' link
                           "/admin/addNewShipperForm",
                           
                           //When user clicks on 'Add New Supplier' button
                           "/admin/addNewSupplier",
                           
                           //When user clicks on 'Add New Supplier' link
                           "/admin/addNewSupplierForm",
                           
                           // When user clicks on an order to view order details
                           "/admin/orderRecord",
                           
                           // When user clicks on a shipper to view shipper details
                           "/admin/shipperRecord",
                           
                           // When user clicks on a supplier to view supplier details
                           "/admin/supplierRecord",
                           
                           // When user clicks to view all customers
                           "/admin/viewCustomers",
                           
                           // When user clicks to view all orders
                           "/admin/viewOrders",
                           
                           // When user clicks to view all Products
                           "/admin/viewProducts",
                           
                           // When user clicks to view all shippers
                           "/admin/viewShippers",
                           
                           // When user clicks to view all suppliers
                           "/admin/viewSuppliers"})
public class AdminServlet extends HttpServlet {

    @EJB
    private OrderManager orderManager;
    @EJB
    private CustomerFacade customerFacade;
    @EJB
    private CustomerOrderFacade customerOrderFacade;
    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private SupplierFacade supplierFacade;
    @EJB
    private ShipperFacade shipperFacade;
    @EJB
    private AdminManager adminManager;
    @EJB
    private ShipperManager shipperManager;

    private String userPath;
    private Customer customer;
    private CustomerOrder order;
    private Product product;
    private Supplier supplier;
    private Shipper shipper;
    
    private List customerList = new ArrayList();
    private List orderList = new ArrayList();
    private List supplierList = new ArrayList();
    private List shipperList = new ArrayList();
    private List productList = new ArrayList();

    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);
        
        // store category list in servlet context for use in dynamic drop-down lists
        getServletContext().setAttribute("productCategories", categoryFacade.findAll());
        
        // store supplier list in servlet context for use in dynamic drop-down lists
        getServletContext().setAttribute("productSuppliers", supplierFacade.findAll());
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        userPath = request.getServletPath();
        
        switch(userPath){
            case "/admin/addNewProduct": 
                // extract user data from request
                String productCategory = request.getParameter("productCategory");
                String productSupplier = request.getParameter("productSupplier");
                String productName = request.getParameter("productName");
                String price = request.getParameter("price");
                String productDescription = request.getParameter("productDescription");

                // Test Outputs
                out.print(productCategory);
                out.print(productSupplier);
                out.print(productName);
                out.print(price);
                out.print(productDescription);

                adminManager.setUpProduct(productCategory, productSupplier, productName, price, productDescription);
                break;
            
            // if add new product form is requested    
            case "/admin/addNewProductForm":
                response.sendRedirect("/Peripherals/admin/addNewProduct.jsp");
                break;
                
            case "/admin/addNewShipper":
                // extract user data from request
                String shipperCompanyName = request.getParameter("shipperCompanyName");
                String shipperContactFirstName = request.getParameter("shipperContactFirstName");
                String shipperContactLastName = request.getParameter("shipperContactLastName");
                String shipperContactTitle = request.getParameter("shipperContactTitle");
                String shipperAddressLine1 = request.getParameter("shipperAddressLine1");
                String shipperAddressLine2 = request.getParameter("shipperAddressLine2");
                String shipperCity = request.getParameter("shipperCity");
                String shipperState = request.getParameter("shipperState");
                String shipperPostCode = request.getParameter("shipperPostCode");
                String shipperCountry = request.getParameter("shipperCountry");
                String shipperPhone = request.getParameter("shipperPhone");
                String shipperEmail = request.getParameter("shipperEmail");
                String shipperWebsite = request.getParameter("shipperWebsite");

                // Test Outputs
                out.print(shipperCompanyName);
                out.print(shipperContactFirstName);
                out.print(shipperContactLastName);
                out.print(shipperContactTitle);
                out.print(shipperAddressLine1);
                out.print(shipperAddressLine2);
                out.print(shipperCity);
                out.print(shipperState);
                out.print(shipperPostCode);
                out.print(shipperCountry);
                out.print(shipperPhone);
                out.print(shipperEmail);
                out.print(shipperWebsite);

                shipperManager.setUpShipper(shipperCompanyName, shipperContactFirstName, shipperContactLastName, shipperContactTitle, shipperAddressLine1, shipperAddressLine2, shipperCity, shipperState, shipperPostCode, shipperCountry, shipperPhone, shipperEmail, shipperWebsite);
                break;
                
            // if add new shipper form is requested
            case "/admin/addNewShipperForm": {
                response.sendRedirect("/Peripherals/admin/addNewShipper.jsp");
                break;
            }
            
            // if add new supplier form is requested
            case "/admin/addNewSupplierForm": {
                response.sendRedirect("/Peripherals/admin/addNewSupplier.jsp");
                break;
            }
                
            // if customerRecord is requested
            case "/admin/customerRecord": {
                // get customer id from request
                String customerId = request.getQueryString();

                // get customer details
                customer = customerFacade.find(Integer.parseInt(customerId));

                //Test Output
                out.print(customer);

                request.setAttribute("customerRecord", customer);

                // get customer order details
                order = customerOrderFacade.findByCustomerID(customer);
                request.setAttribute("order", order);
                break;
            }
            
            // if logout is requested
            case "/admin/logout": {
                session = request.getSession(); {
                session.invalidate();   // terminate session
                response.sendRedirect("/Peripherals/admin/");}
                break;
            }
                
            // if orderRecord is requested
            case "/admin/orderRecord": {
                // get customer id from request
                String orderId = request.getQueryString();

                // get order details
                Map orderMap = orderManager.getOrderDetails(Integer.parseInt(orderId));

                // place order details in request scope
                request.setAttribute("customer", orderMap.get("customer"));
                request.setAttribute("products", orderMap.get("products"));
                request.setAttribute("orderRecord", orderMap.get("orderRecord"));
                request.setAttribute("orderedProducts", orderMap.get("orderedProducts"));
                break;
            }
            
            // if productRecord is requested
            case "/admin/productRecord": {
                // get customer id from request
                String productId = request.getQueryString();

                // get customer details
                product = productFacade.find(Integer.parseInt(productId));

                //Test Output
                out.print(product);

                request.setAttribute("productRecord", product);
                break;
            }
                
            // if shipperRecord is requested
            case "/admin/shipperRecord": {
                // get shipper id from request
                String shipperId = request.getQueryString();

                // get shipper details
                shipper = shipperFacade.find(Integer.parseInt(shipperId));

                //Test Output
                out.print(shipper);

                request.setAttribute("shipperRecord", shipper);
                break;
            }
                
            // if supplierRecord is requested
            case "/admin/supplierRecord": {
                // get supplier id from request
                String supplierId = request.getQueryString();

                // get supplier details
                supplier = supplierFacade.find(Integer.parseInt(supplierId));

                //Test Output
                out.print(supplier);

                request.setAttribute("supplierRecord", supplier);
                break;
            }
        
            // if viewCustomers is requested
            case "/admin/viewCustomers":{
                customerList = customerFacade.findAll();
                request.setAttribute("customerList", customerList);
                break;
            }

            // if viewOrders is requested
            case "/admin/viewOrders": {
                orderList = customerOrderFacade.findAll();
                request.setAttribute("orderList", orderList);
                break;
            }
        
            // if viewProducts is requested
            case"/admin/viewProducts": {
                productList = productFacade.findAll();
                request.setAttribute("productList", productList);
                break;
            }
        
            // if viewShippers is requested
            case "/admin/viewShippers": {
                shipperList = shipperFacade.findAll();
                request.setAttribute("shipperList", shipperList);
                break;
            }
        
            // if viewSuppliers is requested
            case "/admin/viewSuppliers": {
                supplierList = supplierFacade.findAll();
                request.setAttribute("supplierList", supplierList);
                break;
            }
            
            default:
                break;
            
        }
        
        // use RequestDispatcher to forward request internally
        userPath = "/admin/adminIndex.jsp";
        
        try {
            request.getRequestDispatcher(userPath).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}