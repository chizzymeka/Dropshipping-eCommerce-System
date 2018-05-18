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
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import session.CategoryFacade;
import session.CustomerOrderFacade;
import session.OrderedProductFacade;
import session.ProductFacade;
import session.SupplierFacade;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@WebServlet(name = "ShipperServlet", 
            urlPatterns = {"/shipper/",
                           "/shipper/logout",
                           "/shipper/viewAssociatedSuppliers",
                           "/shipper/viewReadyToBeShippedOrders"})
public class ShipperServlet extends HttpServlet {
    
    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private CustomerOrderFacade customerOrderFacade;
    @EJB
    private OrderedProductFacade orderedProductFacade;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private SupplierFacade supplierFacade;

    private String userPath;
        
    private List associatedSuppliersList = new ArrayList();
    private List readyToBeShippedOrdersList = new ArrayList();
    
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
//            case "/shipper/logout": {
//                session = request.getSession();
//                session.invalidate();   // terminate session
//                response.sendRedirect("/Peripherals/shipper/");
//                break;
//            }
            
            case "/shipper/viewAssociatedSuppliers" :{
                String customerOrderID = request.getParameter("customerOrderID");
                CustomerOrder order = customerOrderFacade.find(Integer.parseInt(customerOrderID));
                
                // get all ordered products for a specific customer
                List<OrderedProduct> orderedProducts = orderedProductFacade.findByOrderId(Integer.parseInt(customerOrderID));

                // get product details for ordered items
                List<Product> products = new ArrayList<>();

                for (OrderedProduct op : orderedProducts) {
                    // Get the product Ids
                    Product p = productFacade.find(op.getOrderedProductPK().getProductID());
                    int pId = p.getProductID();
                    
                    // Get the ids of the suppliers that supply the products
                    Supplier s = productFacade.find(pId).getSupplierID();
                    int sId = s.getSupplierID();
                    
                    // Find the supplier objects in the database
                    Supplier suppliers = supplierFacade.find(sId);
                    
                    // Uniquely add the objects to a list
                    if (!(associatedSuppliersList.contains(suppliers))) {
                        associatedSuppliersList.add(suppliers);
                    }
                }
                request.setAttribute("associatedSuppliersList", associatedSuppliersList);
                out.println(products);
                out.println(associatedSuppliersList);
                
                break;
            }
            
            case "/shipper/viewReadyToBeShippedOrders" :{
                readyToBeShippedOrdersList = customerOrderFacade.findAll();
                request.setAttribute("readyToBeShippedOrdersList", readyToBeShippedOrdersList);
                
                break;
            }
            
            default:
                break;
        }
        
        // use RequestDispatcher to forward request internally
        userPath = "/shipper/shipperIndex.jsp";
        
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
