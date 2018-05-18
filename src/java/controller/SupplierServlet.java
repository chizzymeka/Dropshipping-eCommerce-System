/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.CustomerOrder;
import entity.OrderedProduct;
import entity.Product;
import entity.Supplier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import session.CategoryFacade;
import session.OrderedProductFacade;
import session.ProductFacade;
import session.SupplierFacade;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@WebServlet(name = "SupplierServlet",
            urlPatterns = {// When user access the main admin console page
                            "/supplier/",
                            "/supplier/ordersDueForCollection",
                            "/supplier/logout",
                            "/supplier/updateInventoryCount",
                            "/supplier/viewMyProducts"})
public class SupplierServlet extends HttpServlet {

    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private OrderedProductFacade orderedProductFacade;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private SupplierFacade supplierFacade;

    private String userPath;
    
    // This variable is to be replaced by the actuall supplier id
    private int currentylLoggedinSupplier = 2;
    
    private List<CustomerOrder> ordersList = new ArrayList();
    private List<OrderedProduct> orderedProductsList = new ArrayList();
    private List ordersDueForCollectionList = new ArrayList();
    private List supplierProductsList = new ArrayList();
    private List<Integer> supplierProductsIDsList = new ArrayList();
    private List<Product> productsList = new ArrayList();
    private HashMap ordersDueForCollectionMap = new HashMap();

    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);
        
        // store category list in servlet context for use in dynamic drop-down lists
        getServletContext().setAttribute("productCategories", categoryFacade.findAll());
        
        // store category list in servlet context for use in dynamic drop-down lists
        getServletContext().setAttribute("products", productFacade.findAll());

        // store supplier list in servlet context for use in dynamic drop-down lists
        getServletContext().setAttribute("productSuppliers", supplierFacade.findAll());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        userPath = request.getServletPath();

        switch(userPath){
            // if logout is requested
//            case "/supplier/logout": {
//                session = request.getSession(); {
//                session.invalidate();   // terminate session
//                response.sendRedirect("/Peripherals/supplier/");}
//                
//                break;
//            }
            
            // If supplier requests his products
            case "/supplier/viewMyProducts": {
                productsList = productFacade.findAll();
                
                for(Product p : productsList){
                    Supplier s = p.getSupplierID();
                    
                    int sId = s.getSupplierID();
                    
                    if(sId == currentylLoggedinSupplier){
                        Product product = productFacade.find(sId);
                        if(!(supplierProductsList.contains(product))){
                            supplierProductsList.add(product);
                        }
                    }
                }
                
                request.setAttribute("supplierProductsList", supplierProductsList);
                break;
            }
            
            case "/supplier/ordersDueForCollection": {
                // Obtain the products for this supplier
                productsList = productFacade.findAll();
                
                for(Product p : productsList){
                    Supplier s = p.getSupplierID();
                    
                    int sId = s.getSupplierID();
                    
                    if(sId == currentylLoggedinSupplier){
                        Product product = productFacade.find(sId);
                        if(!(supplierProductsIDsList.contains(product.getProductID()))){
                            supplierProductsIDsList.add(product.getProductID());
                        }
                    }
                }
                
                // Get all ordered products
                orderedProductsList = orderedProductFacade.findAll();
                
                // Loop through all ordered products
                for(OrderedProduct op: orderedProductsList){
                    
                    // Collect all product Ids for all ordered products
                    int customerOrderID = op.getCustomerOrder().getCustomerOrderID();
                    int confirmationNumber = op.getCustomerOrder().getCustomerOrderConfirmationNumber();
                    int productID = op.getProduct().getProductID();
                    String productName = op.getProduct().getProductName();
                    short quantity = op.getOrderedProductQuantity();
                    Date date = op.getCustomerOrder().getCustomerOrderCreated();
                    
                    // If this supplier suuplies an ordered product, then make the cusstomer order details available to him
                    for(Integer spId : supplierProductsIDsList){
                        if (spId == productID){
                            System.out.println(spId + "spId");
                            System.out.println(productID + "productID");
                            
                            ordersDueForCollectionMap.put("customerOrderID", customerOrderID);
                            ordersDueForCollectionMap.put("confirmationNumber", confirmationNumber);
                            ordersDueForCollectionMap.put("productName", productName);
                            ordersDueForCollectionMap.put("quantity", quantity);
                            ordersDueForCollectionMap.put("date", date);

                            System.out.println(ordersDueForCollectionMap + "ordersDueForCollectionMap");

                            if(!(ordersDueForCollectionList.contains(ordersDueForCollectionMap))){
                                ordersDueForCollectionList.add(ordersDueForCollectionMap);
                            }

                            System.out.println(ordersDueForCollectionList + "ordersDueForCollectionList");
                        }
                    }
                }
                
                
                request.setAttribute("ordersDueForCollectionList", ordersDueForCollectionList);
                break;
            }
            
            case "/supplier/updateInventoryCount": {
                System.out.println("Chizzy");
                break;
            }
            
            default:
                break;
        }
        
        // use RequestDispatcher to forward request internally
        userPath = "/supplier/supplierIndex.jsp";
        
            try {
                request.getRequestDispatcher(userPath).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}