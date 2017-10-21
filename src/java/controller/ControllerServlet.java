/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cart.ShoppingCart;
import entity.Category;
import entity.Product;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.CategoryFacade;
import session.OrderManager;
import session.ProductFacade;
import validate.Validator;

/**
 *
 * @author Chizzy Meka | 16036630 | MSc. Computing
 */
@WebServlet(name = "ControllerServlet",
        loadOnStartup = 1,
        urlPatterns = {"/category",
            "/addToCart",
            "/viewCart",
            "/updateCart",
            "/checkout",
            "/purchase"})
public class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private String surcharge;

    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private OrderManager orderManager;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // initialize servlet with configuration information
        surcharge = servletConfig.getServletContext().getInitParameter("deliverySurcharge");

        // store category list in servlet context
        getServletContext().setAttribute("categories", categoryFacade.findAll());
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        Category selectedCategory;
        Collection<Product> categoryProducts;
        // if category page is requested
        switch (userPath) {
            case "/category":
                // get categoryId from request
                String categoryId = request.getQueryString();
                if (categoryId != null) {

                    // get selected category
                    selectedCategory = categoryFacade.find(Integer.parseInt(categoryId));

                    // place selected category in session scope
                    session.setAttribute("selectedCategory", selectedCategory);

                    // get all products for selected category
                    categoryProducts = selectedCategory.getProductCollection();

                    // place category products in session scope
                    session.setAttribute("categoryProducts", categoryProducts);
                }
                break;

            // if cart page is requested
            case "/viewCart":
                String clear = request.getParameter("clear");
                if ((clear != null) && clear.equals("true")) {

                    ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                    cart.clear();
                }
                userPath = "/cart";
                break;

            // if checkout page is requested
            case "/checkout":
                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                // calculate total
                cart.calculateTotal(surcharge);

                // forward to checkout page and switch to a secure channel
                break;
            default:
                break;
        }
        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        Validator validator = new Validator();

        // if addToCart action is called
        switch (userPath) {
            case "/addToCart": {
                // if updateCart action is called
                // create cart object and attach it to user session
                if (cart == null) {

                    cart = new ShoppingCart();
                    session.setAttribute("cart", cart);
                }       // get user input from request
                String productId = request.getParameter("ProductID");
                if (!productId.isEmpty()) {

                    Product product = productFacade.find(Integer.parseInt(productId));
                    cart.addItem(product);
                }
                userPath = "/category";
                break;
            }
            case "/updateCart": {
                // get input from request
                String productId = request.getParameter("productId");
                String quantity = request.getParameter("quantity");
                Product product = productFacade.find(Integer.parseInt(productId));
                cart.update(product, quantity);
                userPath = "/cart";
                break;
            }
            // if purchase action is called
            case "/purchase":
                if (cart != null) {
                    // extract user data from request
                    String title = request.getParameter("title");
                    String firstName = request.getParameter("firstName");
                    String lastName = request.getParameter("lastName");
                    String addressLine1 = request.getParameter("addressLine1");
                    String addressLine2 = request.getParameter("addressLine2");
                    String city = request.getParameter("city");
                    String state = request.getParameter("state");
                    String postCode = request.getParameter("postCode");
                    String country = request.getParameter("country");
                    String phone = request.getParameter("phone");
                    String email = request.getParameter("email");
                    String creditCard = request.getParameter("creditCard");

//                    Test Outputs
//                    out.print(title);
//                    out.print(firstName);
//                    out.print(lastName);
//                    out.print(addressLine1);
//                    out.print(addressLine2);
//                    out.print(city);
//                    out.print(state);
//                    out.print(postCode);
//                    out.print(country);
//                    out.print(phone);
//                    out.print(email);
//                    out.print(creditCard);

                    // Validate User Data
                    boolean validationErrorFlag = false;
                    validationErrorFlag = validator.validateForm(title, firstName, lastName, addressLine1, addressLine2, city, state, postCode, country, phone, email, creditCard, request);

                    // if validation error found, return user to checkout
                    if (validationErrorFlag == true) {
                        request.setAttribute("validationErrorFlag", validationErrorFlag);
                        userPath = "/checkout";
                        // otherwise, save order to database
                    } else {

                        int orderId = orderManager.placeOrder(title, firstName, lastName, addressLine1, addressLine2, city, state, postCode, country, phone, email, creditCard, cart);

                        if (orderId != 0) {
                            // get order details
                            Map orderMap = orderManager.getOrderDetails(orderId);

                            // place order details in request scope
                            request.setAttribute("customer", orderMap.get("customer"));
                            request.setAttribute("products", orderMap.get("products"));
                            request.setAttribute("orderRecord", orderMap.get("orderRecord"));
                            request.setAttribute("orderedProducts", orderMap.get("orderedProducts"));

                            userPath = "/confirmation";
                        } else {
                            userPath = "/checkout";
                            request.setAttribute("orderFailureFlag", true);
                        }
                    }
                    break;
                }
            default:
                break;
        }
        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}