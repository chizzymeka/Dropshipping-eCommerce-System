/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Authentication;
import entity.CustomerOrder;
import entity.OrderedProduct;
import entity.Product;
import entity.Shipper;
import entity.Supplier;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import session.AuthenticationFacade;
import session.CategoryFacade;
import session.CustomerOrderFacade;
import session.OrderedProductFacade;
import session.ProductFacade;
import session.ShipperFacade;
import session.SupplierFacade;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@WebServlet(name = "ShipperServlet",
        urlPatterns = {
            "/shipper/",
            "/shipper/shipperConsole",
            "/shipper/shipperLoginForm",
            "/shipper/shipperLogout",
            "/shipper/viewAssociatedSuppliers",
            "/shipper/viewReadyToBeShippedOrders"})

public class ShipperServlet extends HttpServlet {

    @EJB
    private AuthenticationFacade authenticationFacade;
    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private CustomerOrderFacade customerOrderFacade;
    @EJB
    private OrderedProductFacade orderedProductFacade;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private ShipperFacade shipperFacade;
    @EJB
    private SupplierFacade supplierFacade;
    private int currentlyLoggedinShipper;
    private HttpSession session;
    private String userPath;

    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);
        // store category list in servlet context for use in dynamic drop-down lists
        getServletContext().setAttribute("productCategories", categoryFacade.findAll());
        // store category list in servlet context for use in dynamic drop-down lists
        getServletContext().setAttribute("products", productFacade.findAll());
        // store supplier list in servlet context for use in dynamic drop-down lists
        getServletContext().setAttribute("productSuppliers", supplierFacade.findAll());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        session = request.getSession(true);
        userPath = request.getServletPath();

        switch (userPath) {
            // WHen shipper request the shipper login page
            case "/shipper/shipperLoginForm": {
                userPath = "/shipperLogin";
                // use RequestDispatcher to forward request internally
                String url = "/shipper" + userPath + ".jsp";
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }

            case "/shipper/viewAssociatedSuppliers": {
                //************************************************************************
                // Check that user is logged in, if not, then redirect them to login page
                if (session.getAttribute("currentylLoggedinShipper") == null) {
                    userPath = "/shipperLogin";
                    // use RequestDispatcher to forward request internally
                    String url = "/shipper" + userPath + ".jsp";
                    try {
                        request.getRequestDispatcher(url).forward(request, response);
                        return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                //************************************************************************

                List<Supplier> associatedSuppliersList = new ArrayList();

                String customerOrderID = request.getParameter("customerOrderID");
                
                // get all ordered products for a specific customer
                List<OrderedProduct> orderedProducts = orderedProductFacade.findByOrderId(Integer.parseInt(customerOrderID));

                // get product details for ordered items
                List<Product> products = new ArrayList<>();

                for (OrderedProduct orderedProductIterator : orderedProducts) {
                    // Get the product Ids
                    Product product = productFacade.find(orderedProductIterator.getOrderedProductPK().getProductID());
                    int productID = product.getProductID();

                    // Get the ids of the suppliers that supply the products
                    Supplier supplier = productFacade.find(productID).getSupplierID();
                    int supplierID = supplier.getSupplierID();

                    // Find the supplier objects in the database
                    Supplier suppliers = supplierFacade.find(supplierID);

                    // Uniquely add the objects to a list
                    if (!(associatedSuppliersList.contains(suppliers))) {
                        associatedSuppliersList.add(suppliers);
                    }
                }
                request.setAttribute("associatedSuppliersList", associatedSuppliersList);
                out.println(products);
                out.println(associatedSuppliersList);

                userPath = "/shipperIndex";
                // use RequestDispatcher to forward request internally
                String url = "/WEB-INF/view" + userPath + ".jsp";
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
            
            case "/shipper/viewReadyToBeShippedOrders": {
                //************************************************************************
                // Check that user is logged in, if not, then redirect them to login page
//                if (session.getAttribute("currentylLoggedinShipper") == null) {
//                    userPath = "/shipperLogin";
//                    // use RequestDispatcher to forward request internally
//                    String url = "/shipper" + userPath + ".jsp";
//                    try {
//                        request.getRequestDispatcher(url).forward(request, response);
//                        return;
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
                //************************************************************************
                System.out.print("viewReadyToBeShippedOrders called!");

                List<CustomerOrder> readyToBeShippedOrdersList = customerOrderFacade.findAll();
                List<Shipper> shippers = shipperFacade.findAll();
                
                for(Shipper shipper : shippers){
                    if(currentlyLoggedinShipper == shipper.getShipperID()){
                        // If readyToBeShippedOrdersList contains something and shipper is activated, then create readyToBeShippedOrdersList
                        if(readyToBeShippedOrdersList != null && shipper.getShipperActivated() == true){
                            request.setAttribute("readyToBeShippedOrdersList", readyToBeShippedOrdersList);
                        // else advise that there are no orders due for collection
                        }else{
                            request.setAttribute("shipperStatus", shipper.getShipperActivated());
                            request.setAttribute("noAvailableOrdersErrorMessage", "There are no orders available for collection.");
                        }
                    }
                }
                
                System.out.print("readyToBeShippedOrdersList: " + readyToBeShippedOrdersList);

                userPath = "/shipperIndex";
                // use RequestDispatcher to forward request internally
                String url = "/WEB-INF/view" + userPath + ".jsp";
                System.out.print(url);
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        session = request.getSession(true);
        userPath = request.getServletPath();

        switch (userPath) {
            // If shipper wants to access the console, that is login
            case "/shipper/shipperConsole": {
                HashMap<Integer, List> authenticationMap = new HashMap();

                // Obtain username and password from frontend
                String shipperUsername = request.getParameter("shipperUsername");
                String shipperPassword = request.getParameter("shipperPassword");

                System.out.print("Username from frontend: " + shipperUsername);
                System.out.print("Password from frontend: " + shipperPassword);

                // Find the username and password
                List<Authentication> authentications = authenticationFacade.findAll();

                for (Authentication authentication : authentications) {
                    // Add the credentials to a list
                    List<String> authenticationList = new ArrayList();
                    authenticationList.add(authentication.getUserName());
                    authenticationList.add(authentication.getPassword());

                    // Put the list of credentials in a map - authenticationMap, with the getAuthenticationID as the key
                    authenticationMap.put(authentication.getAuthenticationID(), authenticationList);
                    System.out.print("authenticationMap: " + authenticationMap);
                }

                // Iterate thorugh the map - authenticationMap
                for (Map.Entry<Integer, List> entry : authenticationMap.entrySet()) {
                    System.out.print(entry);

                    // If the map contains a pair of credentials that matches the entered credentials, then continue to check which shipper the corresponding Athentication ID belongs to
                    if (entry.getValue().contains(shipperUsername) && entry.getValue().contains(shipperPassword)) {
                        System.out.print("The credentials are: " + entry.getKey());
                        authenticationFacade.find(entry.getKey());
                        List<Shipper> shippers = shipperFacade.findAll();

                        for (Shipper shipper : shippers) {
                            // Set shipper as the current user
                            System.out.print("Boolean Test: " + (entry.getKey() == shipper.getAuthenticationID().getAuthenticationID()));
                            System.out.print("AuthenticationID: " + shipper.getAuthenticationID().getAuthenticationID());
                            if (entry.getKey() == shipper.getAuthenticationID().getAuthenticationID()) {
                                currentlyLoggedinShipper = shipper.getShipperID();
                                System.out.print("currentlyLoggedinShipper: " + currentlyLoggedinShipper);
                                String shipperCompanyName = shipper.getShipperCompanyName();
                                // if there's a shipper with that shipper ID then set the key, that is, AuthenticationID as the currently logged in shipper
                                if (shipperFacade.find(currentlyLoggedinShipper) != null) {
                                    session.setAttribute("currentlyLoggedinShipper", currentlyLoggedinShipper);

                                    // Set shipper's company in the session for use on the frontend
                                    session.setAttribute("shipperCompanyName", shipperCompanyName);
                                    System.out.print("currentlyLoggedinShipper upon login: " + session.getAttribute("currentlyLoggedinShipper"));
                                    System.out.print("currentlyLoggedinShipper: " + currentlyLoggedinShipper);

                                    userPath = "/shipperIndex";
                                    // use RequestDispatcher to forward request internally
                                    String url = "/WEB-INF/view" + userPath + ".jsp";
                                    try {
                                        request.getRequestDispatcher(url).forward(request, response);
                                        return;
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                } else {
                                    userPath = "/shipperError";
                                    // use RequestDispatcher to forward request internally
                                    String url = "/shipper" + userPath + ".jsp";
                                    try {
                                        request.getRequestDispatcher(url).forward(request, response);
                                        return;
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // if logout is requested
            case "/shipper/shipperLogout": {
                //************************************************************************
                // Check that user is logged in, if not, then redirect them to login page
                if (session.getAttribute("currentylLoggedinShipper") == null) {
                    userPath = "/shipperLogin";
                    // use RequestDispatcher to forward request internally
                    String url = "/shipper" + userPath + ".jsp";
                    try {
                        request.getRequestDispatcher(url).forward(request, response);
                        return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                //************************************************************************

                // Remove attribute to avoid back button exploit
                session.removeAttribute("currentlyLoggedinShipper");

                session = request.getSession();
                // terminate session
                session.invalidate();

                userPath = "/shipperLoginForm";
                // use RequestDispatcher to forward request internally
                String url = "/shipper" + userPath + ".jsp";
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }
}