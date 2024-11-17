/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

import java.io.File;

/**
 *
 * @author Trung
 */
public class ProductConstants {

    public static final String DEFAULT_IMAGE_URL = "/images/sanPham/default.png";
    public static final String UPLOAD_DIR_BACKUP = "D:\\Code\\Java\\ProdcutWebsite\\web\\images\\sanPham";
    public static final String UPLOAD_DIR = "images" + File.separator + "sanPham";
    public static final String CREATE_PAGE = "/product/addProduct.jsp";
    public static final String UPDATE_PAGE = "/product/updateProduct.jsp";
    public static final String PORTFOLIO_PAGE = "/product/productPortfolio.jsp";
    public static final String DETAILS_PAGE = "/product/productDetails.jsp";
    public static final String DASHBOARD_PAGE = "/product/dashboard.jsp";
    public static final String DASHBOARD_ACTION_URL = "/product?action=dashboard";
    public static final String CREATE_ACTION_URL = "/product?action=create";
    public static final String MANAGE_ACTION_URL = "/product?action=manage";
    public static final String UPDATE_ACTION_URL = "/product?action=update";
    public static final String DELETE_ACTION_URL = "/product?action=delete";
    public static final String GET_PORTFOLIO_ACTION_URL = "/product?action=get_portfolio";
    public static final String GET_DETAILS_ACTION_URL = "/product?action=get_details";
}
