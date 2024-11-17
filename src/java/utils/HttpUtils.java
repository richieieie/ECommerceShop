/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dao.AccountDAO;
import dao.AccountDAOImpl;
import dao.CategoryDAO;
import dao.CategoryDAOImpl;
import dao.ProductDAO;
import dao.ProductDAOImpl;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Trung
 */
public class HttpUtils {

    public static HttpSession createSession(HttpServletRequest request, int timeInSec) {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(timeInSec);

        return session;
    }

    public static void setAllEmployees(HttpServletRequest req, ServletContext sc, String name) {
        AccountDAO accountDAO = new AccountDAOImpl(sc);
        req.setAttribute(name, accountDAO.getAllEmployees());
    }

    public static void setAllCategories(HttpServletRequest req, ServletContext sc, String name) {
        CategoryDAO categoryDAO = new CategoryDAOImpl(sc);
        req.setAttribute(name, categoryDAO.getAll());
    }

    public static void setAllProduct(HttpServletRequest req, ServletContext sc, String name) {
        ProductDAO productDAO = new ProductDAOImpl(sc);
        req.setAttribute(name, productDAO.getAll());
    }
}
