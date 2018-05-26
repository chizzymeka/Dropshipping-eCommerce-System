/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Authentication;
import entity.OrderedProduct;
import entity.Product;
import entity.Supplier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import session.OrderedProductFacade;
import session.ProductFacade;
import session.SupplierFacade;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@WebServlet(name = "SupplierServlet",
        urlPatterns = {
            "/supplier/",
            "/supplier/ordersDueForCollection",
            "/supplier/supplierConsole",
            "/supplier/supplierLoginForm",
            "/supplier/supplierLogout",
            "/supplier/updateInventoryCount",
            "/supplier/viewMyProducts"})

public class SupplierServlet extends HttpServlet {

    @EJB
    private AuthenticationFacade authenticationFacade;
    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private OrderedProductFacade orderedProductFacade;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private SupplierFacade supplierFacade;
    private int currentlyLoggedinSupplier;
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
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        session = request.getSession(true);
        userPath = request.getServletPath();

        switch (userPath) {

            case "/supplier/ordersDueForCollection": {
                //************************************************************************
                // Check that user is logged in, if not, then redirect them to login page
                if (currentlyLoggedinSupplier == 0) {
                    userPath = "/supplierLogin";
                    // use RequestDispatcher to forward request internally
                    String url = "/supplier" + userPath + ".jsp";
                    try {
                        request.getRequestDispatcher(url).forward(request, response);
                        return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                //************************************************************************
                List ordersDueForCollectionList = new ArrayList();
                HashMap ordersDueForCollectionMap = new HashMap();
                List<Integer> supplierProductsIDsList = new ArrayList();
                List<Product> productsList = new ArrayList();
                List<OrderedProduct> orderedProductsList = new ArrayList();
                
                // Empty the list and map before adding anything in it to avoid having it display orders that are supposed to be viewed by other suppliers
                ordersDueForCollectionList.clear();
                ordersDueForCollectionMap.clear();

                // Obtain the products for this supplier
                productsList = productFacade.findAll();

                for (Product productIterator : productsList) {
                    int supplierID = productIterator.getSupplierID().getSupplierID();

                    if (supplierID == currentlyLoggedinSupplier) {
                        if (!(supplierProductsIDsList.contains(productIterator.getProductID()))) {
                            supplierProductsIDsList.add(productIterator.getProductID());
                        }
                    }
                }
                System.out.print("supplierProductsIDsList" + supplierProductsIDsList);
                // Get all ordered products
                orderedProductsList = orderedProductFacade.findAll();

                // Loop through all ordered products
                for (OrderedProduct orderedProductIterator : orderedProductsList) {
                    // Collect all product Ids for all ordered products
                    int customerOrderID = orderedProductIterator.getCustomerOrder().getCustomerOrderID();
                    int confirmationNumber = orderedProductIterator.getCustomerOrder().getCustomerOrderConfirmationNumber();
                    int productID = orderedProductIterator.getProduct().getProductID();
                    String productName = orderedProductIterator.getProduct().getProductName();
                    short quantity = orderedProductIterator.getOrderedProductQuantity();
                    Date date = orderedProductIterator.getCustomerOrder().getCustomerOrderCreated();

                    // If this supplier suuplies an ordered product, then make the customer order details available to them
                    for (Integer supplierProductIDIterator : supplierProductsIDsList) {
                        if (supplierProductIDIterator == productID) {
                            ordersDueForCollectionMap.put("customerOrderID", customerOrderID);
                            ordersDueForCollectionMap.put("confirmationNumber", confirmationNumber);
                            ordersDueForCollectionMap.put("productName", productName);
                            ordersDueForCollectionMap.put("quantity", quantity);
                            ordersDueForCollectionMap.put("date", date);

                            System.out.println(ordersDueForCollectionMap + "ordersDueForCollectionMap");

                            if (!(ordersDueForCollectionList.contains(ordersDueForCollectionMap))) {
                                ordersDueForCollectionList.add(ordersDueForCollectionMap);
                            }
                        }
                    }
                }

                request.setAttribute("ordersDueForCollectionList", ordersDueForCollectionList);
                System.out.println(ordersDueForCollectionList + "ordersDueForCollectionList");
                if(ordersDueForCollectionList.isEmpty()){
                    request.setAttribute("noOrdersErrorMessage", "You have no orders due for collection.");
                }
                
                userPath = "/supplierIndex";
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

            // WHen supplier request the supplier login page
            case "/supplier/supplierLoginForm": {
                userPath = "/supplierLogin";
                // use RequestDispatcher to forward request internally
                String url = "/supplier" + userPath + ".jsp";
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }

            // If supplier requests his products
            case "/supplier/viewMyProducts": {
                //************************************************************************
                // Check that user is logged in, if not, then redirect them to login page
                if (currentlyLoggedinSupplier == 0) {
                    userPath = "/supplierLogin";
                    // use RequestDispatcher to forward request internally
                    String url = "/supplier" + userPath + ".jsp";
                    try {
                        request.getRequestDispatcher(url).forward(request, response);
                        return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                //************************************************************************
                List<Product> productsList = new ArrayList();
                List<Product> supplierProductsList = new ArrayList();

                System.out.print("currentylLoggedinSupplier Chizzy: " + session.getAttribute("currentylLoggedinSupplier"));

                // Empty the list before adding anything in it to avoid having it display products that belong to other suppliers
                supplierProductsList.clear();
                
                productsList = productFacade.findAll();

                for (Product productIterator : productsList) {
                    int supplierID = productIterator.getSupplierID().getSupplierID();

                    if (supplierID == currentlyLoggedinSupplier) {
                        Product product = productFacade.find(supplierID);
                        if (!(supplierProductsList.contains(product))) {
                            supplierProductsList.add(product);
                        }
                    }
                }
                
                request.setAttribute("supplierProductsList", supplierProductsList);
                
                if(supplierProductsList.isEmpty()){
                    request.setAttribute("noProductsErrorMessage", "You currently have no products on offer. Please contact Peripherals administrators to resolve this.");
                }
                
                userPath = "/supplierIndex";
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
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        session = request.getSession(true);
        userPath = request.getServletPath();

        switch (userPath) {
            // If supplier wants to access the console, that is login
            case "/supplier/supplierConsole": {
                HashMap<Integer, List> authenticationMap = new HashMap();

                // Obtain username and password from frontend
                String supplierUsername = request.getParameter("supplierUsername");
                String supplierPassword = request.getParameter("supplierPassword");

                System.out.print("Username from frontend: " + supplierUsername);
                System.out.print("Password from frontend: " + supplierPassword);

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

                    // If the map contains a pair of credentials that matches the entered credentials, then continue to check which supplier the corresponding Athentication ID belongs to
                    if (entry.getValue().contains(supplierUsername) && entry.getValue().contains(supplierPassword)) {
                        System.out.print("The credentials are: " + entry.getKey());
                        authenticationFacade.find(entry.getKey());
                        List<Supplier> suppliers = supplierFacade.findAll();
                        
                        for(Supplier supplier : suppliers){
                            // Set supplier as the current user
                            if(entry.getKey() == supplier.getAuthenticationID().getAuthenticationID()){
                                currentlyLoggedinSupplier = supplier.getSupplierID();
                                String supplierCompanyName = supplier.getSupplierCompanyName();
                                // if there's a supplier with that Supplier ID then set the key, that is, AuthenticationID as the currently logged in supplier
                                if (supplierFacade.find(currentlyLoggedinSupplier) != null) {
                                    session.setAttribute("currentylLoggedinSupplier", currentlyLoggedinSupplier);
                                    
                                    // Set Supplier's company in the session for use on the frontend
                                    session.setAttribute("supplierCompanyName", supplierCompanyName);
                                    System.out.print("currentlyLoggedinSupplier upon login: " + session.getAttribute("currentylLoggedinSupplier"));

                                    userPath = "/supplierIndex";
                                    // use RequestDispatcher to forward request internally
                                    String url = "/WEB-INF/view" + userPath + ".jsp";
                                    try {
                                        request.getRequestDispatcher(url).forward(request, response);
                                        return;
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                } else {
                                    userPath = "/supplierError";
                                    // use RequestDispatcher to forward request internally
                                    String url = "/supplier" + userPath + ".jsp";
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
            case "/supplier/supplierLogout": {
                //************************************************************************
                // Check that user is logged in, if not, then redirect them to login page
                if (session.getAttribute("currentylLoggedinSupplier") == null) {
                    userPath = "/supplierLogin";
                    // use RequestDispatcher to forward request internally
                    String url = "/supplier" + userPath + ".jsp";
                    try {
                        request.getRequestDispatcher(url).forward(request, response);
                        return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                //************************************************************************

                // Remove attribute to avoid back button exploit
                session.removeAttribute("currentlyLoggedinSupplier");

                session = request.getSession();
                // terminate session
                session.invalidate();

                userPath = "/supplierLoginForm";
                // use RequestDispatcher to forward request internally
                String url = "/supplier" + userPath + ".jsp";
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }

            case "/supplier/updateInventoryCount": {
                //************************************************************************
                // Check that user is logged in, if not, then redirect them to login page
                if (currentlyLoggedinSupplier == 0) {
                    userPath = "/supplierLogin";
                    // use RequestDispatcher to forward request internally
                    String url = "/supplier" + userPath + ".jsp";
                    try {
                        request.getRequestDispatcher(url).forward(request, response);
                        return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                //************************************************************************

                System.out.println("Chizzy");
                break;
            }
        }
    }
}
