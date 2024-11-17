/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import constants.CategoryConstants;
import dao.CategoryDAO;
import dao.CategoryDAOImpl;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import utils.Santinization;

/**
 *
 * @author Trung
 */
public class CategoryController extends HttpServlet {

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
                case "manage":
                    handleGetManage(request, response);
                    break;
                case "create":
                    handleGetCreate(request, response);
                    break;
                case "update":
                    handleGetUpdate(request, response);
                    break;
                case "delete":
                    handleGetDelete(request, response);
                    break;
                default:
                    response.sendError(404, "action failed");
            }
        } else if (method.equals("POST")) {
            switch (action) {
                case "create":
                    handleCreate(request, response);
                    break;
                case "update":
                    handleUpdate(request, response);
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

    private void handleGetManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        CategoryDAO categoryDAO = new CategoryDAOImpl(sc);

        request.setAttribute("categories", categoryDAO.getAll());
        request.getRequestDispatcher(CategoryConstants.MANAGE_PAGE).forward(request, response);
    }

    private void handleGetDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        CategoryDAO categoryDAO = new CategoryDAOImpl(sc);

        int typeId;
        try {
            typeId = Integer.parseInt(request.getParameter("typeId"));
        } catch (Exception e) {
            request.getRequestDispatcher(CategoryConstants.MANAGE_PAGE).forward(request, response);
            return;
        }

        categoryDAO.deleteById(typeId);

        response.sendRedirect(CategoryConstants.MANAGE_ACTION_URL);
    }

    private void handleGetUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        CategoryDAO categoryDAO = new CategoryDAOImpl(sc);

        int typeId = Integer.parseInt(request.getParameter("typeId"));
        Category c = categoryDAO.getById(typeId);

        request.setAttribute("category", c);

        request.getRequestDispatcher(CategoryConstants.UPDATE_PAGE).forward(request, response);
    }

    private void handleGetCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(CategoryConstants.CREATE_PAGE).forward(request, response);
    }

    private void handleCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        CategoryDAO categoryDAO = new CategoryDAOImpl(sc);

        int error = 0;
        String name = Santinization.capitalizeFirstLetters(request.getParameter("categoryName"));
        String memo = Santinization.capitalizeFirstLetters(request.getParameter("memo"));

        if (name.isEmpty()) {
            request.setAttribute("categoryNameError", "Name cannot be empty!");
            error++;
        }

        if (error > 0) {
            request.getRequestDispatcher(CategoryConstants.CREATE_PAGE).forward(request, response);
            return;
        }

        Category c = categoryDAO.saveOne(new Category(0, name, memo));

        if (c == null) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "fail to send this request");
            return;
        }

        response.sendRedirect(CategoryConstants.CREATE_ACTION_URL);
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        CategoryDAO categoryDAO = new CategoryDAOImpl(sc);

        int error = 0;
        int typeId = Integer.parseInt(request.getParameter("typeId"));
        String name = request.getParameter("categoryName");
        String memo = request.getParameter("memo");

        Category c = new Category(typeId, name, memo);
        request.setAttribute("category", c);

        error += Santinization.checkEmptyString(request, name, "categoryNameError", "Name cannot be empty!");

        // error check
        if (error > 0) {
            request.getRequestDispatcher(CategoryConstants.UPDATE_PAGE).forward(request, response);
            return;
        }
        if (!categoryDAO.updateOne(c)) {
            request.getRequestDispatcher(CategoryConstants.UPDATE_PAGE).forward(request, response);
            return;
        }
        
        response.sendRedirect(CategoryConstants.UPDATE_ACTION_URL + "&" + "typeId=" + c.getTypeId());
    }

}
