/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import constants.CartConstants;
import constants.ProductConstants;
import dao.ProductDAO;
import dao.ProductDAOImpl;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.Product;

/**
 *
 * @author Trung
 */
public class CartController extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        String method = request.getMethod();

        if (method.equals("GET")) {
            switch (action) {
                case "get":
                    handleGetCart(request, response);
                    break;
                case "delete":
                    handleGetDelete(request, response);
                    break;
                default:
                    response.sendError(404, "action failed");
            }
        } else if (method.equals("POST")) {
            switch (action) {
                case "add":
                    handleAddCart(request, response);
                    break;
                default:
                    response.sendError(404, "action failed");
            }
        } else {
            response.sendError(500, "action failed");
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
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void handleGetCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart currentCart = (Cart) session.getAttribute("cart");

        request.setAttribute("cart", currentCart.getCart());

        request.getRequestDispatcher(CartConstants.CART_PAGE).forward(request, response);
    }

    private void handleAddCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext sc = getServletContext();
        ProductDAO productDAO = new ProductDAOImpl(sc);
        HttpSession session = request.getSession();
        Cart currentCart = (Cart) session.getAttribute("cart");

        String productId = request.getParameter("productId");

        Product p = productDAO.getById(productId);
        if (p == null) {
            response.sendError(404);
            return;
        }

        currentCart.add(p);

        response.sendRedirect(ProductConstants.GET_DETAILS_ACTION_URL + "&productId=" + productId);
    }

    private void handleGetDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Cart currentCart = (Cart) session.getAttribute("cart");
        
        String productId = request.getParameter("productId");
        Product p = new Product();
        p.setProductId(productId);
        
        currentCart.remove(p);
        
        response.sendRedirect(CartConstants.CART_ACTION_URL);
    }
    
}
