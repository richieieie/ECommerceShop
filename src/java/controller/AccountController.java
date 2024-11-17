/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import constants.AccountConstants;
import constants.ProductConstants;
import dao.AccountDAO;
import dao.AccountDAOImpl;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.AccountDTO;
import utils.Auth;
import utils.HttpUtils;
import utils.Santinization;

/**
 *
 * @author Trung
 */
public class AccountController extends HttpServlet {

    private final int sessionTimeOut = 30 * 60;

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
                case "login":
                    handleGetLogin(request, response);
                    break;
                case "logout":
                    handleGetLogout(request, response);
                    break;
                case "manage":
                    handleGetManage(request, response);
                    break;
                case "register":
                    handleGetRegister(request, response);
                    break;
                case "update":
                    handleGetUpdate(request, response);
                    break;
                case "delete":
                case "active":
                    handleGetChangeStatus(request, response);
                    break;
                case "change_password":
                    handleGetChangePassword(request, response);
                    break;
                default:
                    response.sendError(404, "action failed");
                    break;
            }
        } else if (method.equals("POST")) {
            switch (action) {
                case "login":
                    handleLogin(request, response);
                    break;
                case "register":
                    handleRegister(request, response);
                    break;
                case "update":
                    handleUpdate(request, response);
                    break;
                case "change_password":
                    handleChangePassword(request, response);
                    break;
                default:
                    response.sendError(404, "action failed");
                    break;
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
    }// </editor-fold>a

    private void handleGetLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountDTO currentAccount = Auth.getCurrentAccount(request);

        String url = null;
        if (currentAccount != null) {
            int role = currentAccount.getRoleInSystem();
            if (role == Auth.ADMIN_ROLE || role == Auth.STAFF_ROLE) {
                url = ProductConstants.DASHBOARD_ACTION_URL + "&page=0";
            } else {
                url = ProductConstants.GET_PORTFOLIO_ACTION_URL + "&page=0";
            }
        }

        if (url != null) {
            response.sendRedirect(url);
            return;
        }

        request.getRequestDispatcher(AccountConstants.LOGIN_PAGE).forward(request, response);
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String account = request.getParameter("account");
        String pass = request.getParameter("pass");

        AccountDAO accountDAO = new AccountDAOImpl(getServletContext());
        AccountDTO a = accountDAO.login(account, pass);

        if (a == null || !a.isIsUse()) {
            request.setAttribute("unauthorized", "Please check your password and account name and try again.");
            request.getRequestDispatcher(AccountConstants.LOGIN_PAGE).forward(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            session = HttpUtils.createSession(request, sessionTimeOut);
        }

        String url = (String) session.getAttribute("originalUrl");
        session.removeAttribute("originalUrl");

        if (url == null) {
            int role = a.getRoleInSystem();
            url = role != Auth.ADMIN_ROLE && role != Auth.STAFF_ROLE ? ProductConstants.GET_PORTFOLIO_ACTION_URL + "&page=0" : ProductConstants.DASHBOARD_ACTION_URL + "&page=0";
        }
        session.setAttribute("account", a);
        response.sendRedirect(url);

    }

    private void handleGetLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(AccountConstants.LOGIN_ACTION_URL);
    }

    private void handleGetManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        AccountDAO accountDAO = new AccountDAOImpl(sc);

        List<AccountDTO> employees = accountDAO.getAll();

        request.setAttribute("employees", employees);

        request.getRequestDispatcher(AccountConstants.MANAGE_PAGE).forward(request, response);
    }

    private void handleGetUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        AccountDAO accountDAO = new AccountDAOImpl(sc);

        String account = request.getParameter("account");

        AccountDTO aDTO = accountDAO.getByAccount(account).mapToDTO();
        AccountDTO currentAccount = Auth.getCurrentAccount(request);
        if (!currentAccount.equals(aDTO) && currentAccount.getRoleInSystem() != 1) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorzied action");
            return;
        }

        request.setAttribute("account", aDTO);

        request.getRequestDispatcher(AccountConstants.UPDATE_PAGE).forward(request, response);
    }

    private void handleGetChangeStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext sc = getServletContext();
        AccountDAO accountDAO = new AccountDAOImpl(sc);

        int error = 0;
        String account = request.getParameter("account");
        error += Santinization.checkEmptyString(request, account, "accountError", "");

        if (error > 0) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String action = request.getParameter("action");
        if (action.equals("delete") && !accountDAO.deleteByAccount(account)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } else if (action.equals("active") && !accountDAO.activeByAccount(account)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.sendRedirect(AccountConstants.MANAGE_ACTION_URL);
    }

    private void handleGetRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountDTO currentAdminAccount = Auth.getCurrentAccount(request);
        Account account = new Account();

        account.setIsUse(true);
        account.setGender(true);
        if (currentAdminAccount != null && currentAdminAccount.getRoleInSystem() == Auth.ADMIN_ROLE) {
            account.setRoleInSystem(Auth.ADMIN_ROLE);
        } else {
            account.setRoleInSystem(Auth.CUSTOMER_ROLE);
        }
        request.setAttribute("account", account);

        request.getRequestDispatcher(AccountConstants.REGISTER_PAGE).forward(request, response);
    }

    private void handleGetChangePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher(AccountConstants.CHANGE_PASSWORD_PAGE).forward(request, response);
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int error = 0;
        ServletContext sc = getServletContext();
        AccountDAO accountDAO = new AccountDAOImpl(sc);

        // get data
        String pass = request.getParameter("pass");
        String confirmPass = request.getParameter("confirmPass");
        Account a = parseAccountFromRequest(request);

        // validate
        error += validateNewPassword(request, pass, confirmPass);
        error += validateAccountData(request, a);

        a.setPass(pass);
        request.setAttribute("account", a);

        if (error > 0) {
            request.getRequestDispatcher(AccountConstants.REGISTER_PAGE).forward(request, response);
            return;
        }

        if (!accountDAO.saveOne(a)) {
            request.setAttribute("accountError", "Account name exists!!!");
            request.getRequestDispatcher(AccountConstants.REGISTER_PAGE).forward(request, response);
            return;
        };

        AccountDTO currentAccount = Auth.getCurrentAccount(request);
        String url = AccountConstants.REGISTER_ACTION_URL;
        if (currentAccount == null) {
            HttpSession session = HttpUtils.createSession(request, sessionTimeOut);
            session.setAttribute("account", a.mapToDTO());
            url = ProductConstants.GET_PORTFOLIO_ACTION_URL + "&page=0";
        }

        response.sendRedirect(url);
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int error = 0;
        ServletContext sc = getServletContext();
        AccountDAO accountDAO = new AccountDAOImpl(sc);

        // get data
        Account a = parseAccountFromRequest(request);

        // validate
        error += validateAccountData(request, a);
        request.setAttribute("account", a);

        if (error > 0) {
            request.getRequestDispatcher(AccountConstants.UPDATE_PAGE).forward(request, response);
            return;
        }

        AccountDTO currentAccount = Auth.getCurrentAccount(request);
        if (!currentAccount.equals(a.mapToDTO()) && currentAccount.getRoleInSystem() != 1) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorzied action");
            return;
        }

        if (!accountDAO.updateByAccount(a)) {
            request.getRequestDispatcher(AccountConstants.UPDATE_PAGE).forward(request, response);
            return;
        };

        response.sendRedirect(AccountConstants.UPDATE_ACTION_URL + "&account=" + a.getAccount());
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int error = 0;
        ServletContext sc = getServletContext();
        AccountDAO accountDAO = new AccountDAOImpl(sc);

        String curPass = request.getParameter("currentPass");
        String newPass = request.getParameter("newPass");
        String confirmNewPass = request.getParameter("confirmNewPass");

        error += Santinization.checkEmptyString(request, curPass, "currentPassError", "Please enter current password!");
        error += Santinization.checkEmptyString(request, newPass, "newPassError", "Please enter new password!");
        error += Santinization.checkEmptyString(request, confirmNewPass, "confirmNewPassError", "Please confirm new password!");
        error += Santinization.matchesRegex(request, newPass, confirmNewPass, "confirmNewPassError", "Please confirm new password!");

        if (error > 0) {
            request.getRequestDispatcher(AccountConstants.CHANGE_PASSWORD_PAGE).forward(request, response);
            return;
        }

        AccountDTO account = Auth.getCurrentAccount(request);
        if (!accountDAO.updatePassword(account.getAccount(), curPass, newPass)) {
            request.setAttribute("currentPassError", "Wrong password");
            request.getRequestDispatcher(AccountConstants.CHANGE_PASSWORD_PAGE).forward(request, response);
            return;
        }

        handleGetLogout(request, response);
    }

    private Account parseAccountFromRequest(HttpServletRequest request) {
        String account = request.getParameter("account");
        String lastName = Santinization.capitalizeFirstLetters(request.getParameter("lastName")).trim();
        String firstName = Santinization.capitalizeFirstLetters(request.getParameter("firstName")).trim();
        String birthday = request.getParameter("birthday");
        boolean gender = Integer.parseInt(request.getParameter("gender")) == 1 ? true : false;
        String phone = request.getParameter("phone");
        boolean status = Integer.parseInt(request.getParameter("isUse")) == 1 ? true : false;
        int role = Integer.parseInt(request.getParameter("roleInSystem"));

        return new Account(account, "", lastName, firstName, birthday == null || birthday.isEmpty() ? null : Date.valueOf(birthday), gender, phone, status, role);
    }

    private int validateAccountData(HttpServletRequest request, Account a) {
        int error = 0;
        error += Santinization.checkEmptyString(request, a.getAccount(), "accountError", "Account name cannot be empty!");
        if (request.getAttribute("accountError") == null) {
            error += Santinization.matchesRegex(request, a.getAccount(), "^[a-zA-Z0-9]+$", "accountError", "Please enter an account name without any special characters!");
        }
        error += Santinization.checkEmptyString(request, a.getLastName(), "lastNameError", "Last name cannot be empty!");
        error += Santinization.checkEmptyString(request, a.getFirstName(), "firstNameError", "First name cannot be empty!");
        error += Santinization.checkEmptyString(request, a.getBirthday() == null ? "" : a.getBirthday().toString(), "birthdayError", "Birthday name cannot be empty!");
        error += Santinization.checkEmptyString(request, a.getPhone(), "phoneError", "Phone cannot be empty!");
        if (request.getAttribute("phoneError") == null) {
            error += Santinization.matchesRegex(request, a.getPhone(), "\\d{10}", "phoneError", "Please enter a 10-digit phone number!");
        }

        return error;
    }

    private int validateNewPassword(HttpServletRequest request, String newPassword, String confirmNewPassword) {
        int error = 0;

        error += Santinization.checkEmptyString(request, newPassword, "passError", "Password cannot be empty!");
        error += Santinization.checkEmptyString(request, confirmNewPassword, "confirmPassError", "Please confirm your password!");
        if (!newPassword.equals(confirmNewPassword)) {
            request.setAttribute("confirmPassError", "Please confirm new password!");
            error++;
        }

        return error;
    }
}
