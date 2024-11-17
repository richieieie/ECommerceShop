/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import constants.AccountConstants;
import constants.CartConstants;
import constants.ProductConstants;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AccountDTO;

/**
 *
 * @author Trung
 */
public class Auth {

    public static int ADMIN_ROLE = 1;
    public static int STAFF_ROLE = 2;
    public static int CUSTOMER_ROLE = 3;
    public static int ALL_ROLE = 0;
    public static String[] staffActions = {AccountConstants.LOGIN_ACTION_URL, ProductConstants.DASHBOARD_ACTION_URL, AccountConstants.LOGOUT_ACTION_URL, AccountConstants.UPDATE_ACTION_URL, AccountConstants.CHANGE_PASSWORD_ACTION_URL,
        ProductConstants.CREATE_ACTION_URL, ProductConstants.UPDATE_ACTION_URL, ProductConstants.GET_PORTFOLIO_ACTION_URL, ProductConstants.GET_DETAILS_ACTION_URL, ProductConstants.DELETE_ACTION_URL};
    public static String[] customerActions = {AccountConstants.LOGIN_ACTION_URL, AccountConstants.LOGOUT_ACTION_URL, AccountConstants.UPDATE_ACTION_URL, AccountConstants.CHANGE_PASSWORD_ACTION_URL, 
        ProductConstants.GET_PORTFOLIO_ACTION_URL, ProductConstants.GET_DETAILS_ACTION_URL, CartConstants.CART_ACTION_URL, CartConstants.ADD_CART_ACTION_URL, CartConstants.DELETE_CART_ACTION_URL};

    public static boolean authenticate(HttpServletRequest request, HttpServletResponse response, String LOGIN_ACTION_URL) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(LOGIN_ACTION_URL);
            return false;
        }

        return true;
    }

    public static boolean authenticate(HttpServletRequest request, HttpServletResponse response, int[] roles, String LOGIN_ACTION_URL) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(LOGIN_ACTION_URL);
            return false;
        }

        AccountDTO aDTO = (AccountDTO) session.getAttribute("account");
        boolean rightRole = false;

        for (int role : roles) {
            if (aDTO.getRoleInSystem() == role) {
                rightRole = true;
            } else if (role == 0) {
                return true;
            }
        }

        if (!rightRole) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }

    public static AccountDTO getCurrentAccount(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        return (AccountDTO) session.getAttribute("account");
    }

    public static String[] getStaffPages() {
        return staffActions;
    }

    public static String[] getCustomerPages() {
        return customerActions;
    }

}
