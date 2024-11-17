/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import constants.AccountConstants;
import constants.ProductConstants;
import dao.CategoryDAO;
import dao.CategoryDAOImpl;
import dao.ProductDAO;
import dao.ProductDAOImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.AccountDTO;
import model.Category;
import model.Product;
import utils.Auth;
import utils.HttpUtils;
import utils.Santinization;

/**
 *
 * @author Trung
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB 
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100) // 100 MB
// tasks for tomorrow:
// create handle error function when submit form fail
// do more
// improve code
public class ProductController extends HttpServlet {

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
                case "create":
                    handleGetModify(request, response, ProductConstants.CREATE_PAGE);
                    break;
                case "manage":
                    handleGetManage(request, response);
                    break;
                case "delete":
                    handleGetDelete(request, response);
                    break;
                case "update":
                    handleGetUpdate(request, response, ProductConstants.UPDATE_PAGE);
                    break;
                case "get_portfolio":
                    handleGetPortfolio(request, response);
                    break;
                case "get_details":
                    handleGetDetails(request, response);
                    break;
                case "dashboard":
                    handleGetDashBoard(request, response);
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
    }

    private void handleGetModify(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        ServletContext sc = getServletContext();

        AccountDTO currentAccount = Auth.getCurrentAccount(request);
        Product currentProduct = (Product) request.getAttribute("product");

        if (currentProduct == null) {
            AccountDTO a = new AccountDTO();
            a.setAccount(currentAccount.getAccount());
            currentProduct = new Product(null, null, null, null, null, null, a, "pcs", 0, 0);
            request.setAttribute("product", currentProduct);
        }

        if (!currentAccount.equals(currentProduct.getAccountDTO()) && currentAccount.getRoleInSystem() != Auth.ADMIN_ROLE) {
            response.sendError(404, "Page Not Found");
            return;
        }

        HttpUtils.setAllCategories(request, sc, "categories");
        if (currentAccount.getRoleInSystem() == Auth.ADMIN_ROLE) {
            HttpUtils.setAllEmployees(request, sc, "employees");
        }

        request.getRequestDispatcher(page).forward(request, response);
    }

    private void handleGetManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        ProductDAO productDAO = new ProductDAOImpl(sc);
        CategoryDAO categoryDAO = new CategoryDAOImpl(sc);

        // get data from request
        int page = Integer.parseInt(request.getParameter("page"));
        String[] typeIds = request.getParameterValues("typeId");
        String productName = request.getParameter("productName");

        // set up
        String curURL = "/product?action=manage";
        int[] ids = null;
        if (typeIds != null) {
            ids = new int[typeIds.length];
            for (int i = 0; i < typeIds.length; i++) {
                ids[i] = Integer.parseInt(typeIds[i]);
                curURL += "&typeId=" + typeIds[i];
            }
        }
        if (productName != null) {
            curURL += "&productName=" + productName;
        }
        int pageSize = 10;

        // get products
        List<Product> products = productDAO.getByPageCategoriesName(ids, page, pageSize, productName);

        // open new connection and get total pages avaiable
        productDAO = new ProductDAOImpl(sc);
        int pages = productDAO.countPagesByCategoriesName(ids, pageSize, productName);

        // set up request attributes (in workshop 2 change it to custom tags)
        request.setAttribute("pages", pages);
        request.setAttribute("products", products);
        request.setAttribute("selectedTypeIds", ids);
        request.setAttribute("curURL", curURL);
        request.setAttribute("categories", categoryDAO.getAll());

        request.getRequestDispatcher(AccountConstants.DASHBOARD_PAGE).forward(request, response);
    }

    private void handleCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        ProductDAO productDAO = new ProductDAOImpl(sc);
        AccountDTO currentAccount = Auth.getCurrentAccount(request);
        Product p = getFullProductData(request);

        if ((int) request.getAttribute("error") > 0) {
            // set request attribute for fail situation
            HttpUtils.setAllCategories(request, sc, "categories");
            if (currentAccount.getRoleInSystem() == Auth.ADMIN_ROLE) {
                HttpUtils.setAllEmployees(request, sc, "employees");
            }

            request.setAttribute("product", p);

            request.getRequestDispatcher(ProductConstants.CREATE_PAGE).forward(request, response);
            return;
        }

        // save image
        if (p.getAccountDTO().getAccount().equals(currentAccount.getAccount()) || currentAccount.getRoleInSystem() == Auth.ADMIN_ROLE) {
            Product productSaved = productDAO.saveOne(p);
            if (productSaved != null) {
                saveProductImage(request, "productImage", p.getProductId());
            } else {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Could not send this request");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden Action");
            return;
        }

        response.sendRedirect(ProductConstants.CREATE_ACTION_URL);
    }

    private void handleGetDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductDAO productDAO = new ProductDAOImpl(getServletContext());

        String accountParam = request.getParameter("account");
        String productIdParam = request.getParameter("productId");

        if (accountParam == null || productIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters.");
            return;
        }

        String redirectPage = ProductConstants.DASHBOARD_ACTION_URL + "&page=0";
        AccountDTO account = Auth.getCurrentAccount(request);
        String uploadFilePath = request.getServletContext().getRealPath("") + ProductConstants.UPLOAD_DIR;

        boolean isDeleted = false;
        if (accountParam.equals(account.getAccount())) {
            isDeleted = productDAO.deleteById(productIdParam);
        } else if (account.getRoleInSystem() == 1) {
            isDeleted = productDAO.deleteById(productIdParam);
            redirectPage = ProductConstants.MANAGE_ACTION_URL + "&page=0";
        } else {
            response.sendRedirect(redirectPage);
            return;
        }

        if (isDeleted) {
            // delete product image in file system
            (new File(uploadFilePath + File.separator + productIdParam + ".jpg")).delete();
            (new File(ProductConstants.UPLOAD_DIR_BACKUP + File.separator + productIdParam + ".jpg")).delete();
        }

        response.sendRedirect(redirectPage);
    }

    private void handleGetUpdate(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAOImpl(getServletContext());

        Product product = productDAO.getById(request.getParameter("productId"));

        if (product == null) {
            response.sendError(404, "Page Not Found");
            return;
        }

        request.setAttribute("product", product);
        handleGetModify(request, response, page);
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext sc = getServletContext();
        ProductDAO productDAO = new ProductDAOImpl(sc);
        AccountDTO currentAccount = Auth.getCurrentAccount(request);
        Product p = getFullProductData(request);

        // error check
        if ((int) request.getAttribute("error") > 0) {
            // set request attribute for fail situation
            HttpUtils.setAllCategories(request, sc, "categories");
            if (currentAccount.getRoleInSystem() == Auth.ADMIN_ROLE) {
                HttpUtils.setAllEmployees(request, sc, "employees");
            }

            request.setAttribute("product", p);

            request.getRequestDispatcher(ProductConstants.UPDATE_PAGE).forward(request, response);
            return;
        }

        // save image and application context attribute
        if (p.getAccountDTO().getAccount().equals(currentAccount.getAccount()) || currentAccount.getRoleInSystem() == Auth.ADMIN_ROLE) {
            boolean isUpdated = productDAO.updateById(p);
            if (isUpdated) {
                saveProductImage(request, "productImage", p.getProductId());
            } else {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Could not send this request");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden Action");
            return;
        }

        response.sendRedirect(ProductConstants.UPDATE_ACTION_URL + "&productId=" + p.getProductId());
    }

    // after get params from request and create a new product object, if there is any data error, will count it and set to request attribute "error", and will set error name to request and its message
    private Product getFullProductData(HttpServletRequest request) throws IOException, ServletException, ServletException {
        int error = 0;
        // get request data
        // admin can choose who will manage the product, so we will need to check role of the current accounts
        String account = request.getParameter("account");
        String name = Santinization.capitalizeFirstLetters(request.getParameter("productName"));
        String brief = request.getParameter("brief");
        int typeId = Integer.parseInt(request.getParameter("typeId"));
        String unit = request.getParameter("unit").isEmpty() ? "pcs" : request.getParameter("unit");
        int price = Santinization.parseInt(request.getParameter("price"), 0);
        int discount = Santinization.parseInt(request.getParameter("discount"), 0);
        Part image = request.getPart("productImage");
        String id = request.getParameter("productId").isEmpty() ? Santinization.generateRandomString(10) : request.getParameter("productId");

        // santinization
        error += Santinization.checkEmptyString(request, name, "productNameError", "Product name cannot be empty!");

        boolean hasImage = image.getContentType().equals("image/jpeg") || image.getContentType().equals("image/jpg") || image.getContentType().equals("image/png");
        String imagePath = hasImage ? "/images/sanPham/" + id + ".jpg" : ProductConstants.DEFAULT_IMAGE_URL;

        // set up new product data
        Category c = new Category();
        c.setTypeId(typeId);
        AccountDTO aDTO = new AccountDTO();
        aDTO.setAccount(account);
        Product p = new Product(id, name, imagePath, brief, c, aDTO, unit, price, discount);

        request.setAttribute("error", error);

        return p;
    }

    private void saveProductImage(HttpServletRequest request, String fileInputAttribute, String fileSavedName) throws IOException, ServletException {
        Part file = request.getPart(fileInputAttribute);
        boolean hasImage = file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/jpg") || file.getContentType().equals("image/png");
        String uploadFilePath = request.getServletContext().getRealPath("") + ProductConstants.UPLOAD_DIR;
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        if (hasImage) {
            (new File(uploadFilePath + File.separator + fileSavedName + ".jpg")).delete();
            (new File(ProductConstants.UPLOAD_DIR_BACKUP + File.separator + fileSavedName + ".jpg")).delete();
            file.write(uploadFilePath + File.separator + fileSavedName + ".jpg");
            Files.copy(new File(uploadFilePath + File.separator + fileSavedName + ".jpg").toPath(), new File(ProductConstants.UPLOAD_DIR_BACKUP + File.separator + fileSavedName + ".jpg").toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void handleGetPortfolio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        ProductDAO productDAO = new ProductDAOImpl(sc);
        CategoryDAO categoryDAO = new CategoryDAOImpl(sc);

        // get data from request
        int page = Integer.parseInt(request.getParameter("page"));
        String[] typeIds = request.getParameterValues("typeId");
        String productName = request.getParameter("productName");

        // set up
        String curURL = "/product?action=get_portfolio";
        int[] ids = null;
        if (typeIds != null) {
            ids = new int[typeIds.length];
            for (int i = 0; i < typeIds.length; i++) {
                ids[i] = Integer.parseInt(typeIds[i]);
                curURL += "&typeId=" + typeIds[i];
            }
        }
        if (productName != null) {
            curURL += "&productName=" + productName;
        }
        int pageSize = 10;

        // get products
        List<Product> products = productDAO.getByPageCategoriesName(ids, page, pageSize, productName);

        // open new connection and get total pages avaiable
        productDAO = new ProductDAOImpl(sc);
        int pages = productDAO.countPagesByCategoriesName(ids, pageSize, productName);

        // set up request attributes (in workshop 2 change it to custom tags)
        request.setAttribute("pages", pages);
        request.setAttribute("products", products);
        request.setAttribute("selectedTypeIds", ids);
        request.setAttribute("curURL", curURL);
        request.setAttribute("categories", categoryDAO.getAll());

        request.getRequestDispatcher(ProductConstants.PORTFOLIO_PAGE).forward(request, response);
    }

    private void handleGetDetails(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ProductDAO productDAO = new ProductDAOImpl(getServletContext());

        String productId = request.getParameter("productId");
        Product p = productDAO.getById(productId);
        if (p == null) {
            response.sendError(404, "Product not found");
            return;
        }

        request.setAttribute("product", p);

        request.getRequestDispatcher(ProductConstants.DETAILS_PAGE).forward(request, response);
    }

    private void handleGetDashBoard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        ProductDAO productDAO = new ProductDAOImpl(sc);
        CategoryDAO categoryDAO = new CategoryDAOImpl(sc);
        AccountDTO currentAccount = Auth.getCurrentAccount(request);

        // get data from request
        int page = Integer.parseInt(request.getParameter("page"));
        String[] typeIds = request.getParameterValues("typeId");
        String productName = request.getParameter("productName");

        // set up
        String curURL = "/product?action=dashboard";
        int[] ids = null;
        if (typeIds != null) {
            ids = new int[typeIds.length];
            for (int i = 0; i < typeIds.length; i++) {
                ids[i] = Integer.parseInt(typeIds[i]);
                curURL += "&typeId=" + typeIds[i];
            }
        }
        if (productName != null) {
            curURL += "&productName=" + productName;
        }

        int pageSize = 10;

        // get products
        List<Product> products = productDAO.getByPageCategoriesAccountName(ids, page, pageSize, currentAccount.getAccount(), productName);

        // open new connection and get total pages avaiable
        productDAO = new ProductDAOImpl(sc);
        int pages = productDAO.countPagesByCategoriesAccountName(ids, pageSize, currentAccount.getAccount(), productName);

        request.setAttribute("pages", pages);
        request.setAttribute("products", products);
        request.setAttribute("selectedTypeIds", ids);
        request.setAttribute("curURL", curURL);
        request.setAttribute("categories", categoryDAO.getAll());

        request.getRequestDispatcher(AccountConstants.DASHBOARD_PAGE).forward(request, response);
    }
}
