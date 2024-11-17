/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tags;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import model.Category;

/**
 *
 * @author Trung
 */
public class FilterProductBox extends SimpleTagSupport {

    /**
     * Called by the container to invoke this tag.The implementation of this
 method is provided by the tag library developer, and handles all tag
 processing, body iteration, etc.
     * @throws javax.servlet.jsp.JspException
     * @throws java.io.IOException
     */
    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        JspWriter out = getJspContext().getOut();
        
        String action = request.getParameter("action");
        List<Category> cats = (List<Category>) request.getAttribute("categories");
        int[] selectedTypeIds = (int[]) request.getAttribute("selectedTypeIds");
        
        out.println("<form action='/product' method='get' class='filter col-md-2 col mt-3 bg-dark text-white'>");
        out.println("<input type='hidden' name='action' value='" + action + "'/>");
        out.println("<input type='hidden' name='page' value='0'/>");
        for (Category c : cats) {
            String checked = "";
            if (selectedTypeIds != null) {
                for (int id : selectedTypeIds) {
                    if (id == c.getTypeId()) {
                       checked = "checked";
                    }
                }
            }
            out.println("<div class='form-check mb-3'><input class='form-check-input' type='checkbox' value='" + c.getTypeId() + "' id='" + c.getTypeId() 
                    + "' name='typeId'" + " " + checked + " /><label class='form-check-label' for='" + c.getTypeId() + "'>" + c.getCategoryName() + "</label></div>");
        }
        out.println("<input type='text' class='search-name-box' id='productName' name='productName' placeholder='Product name'/>");
        out.println("<div class='text-center'><button type='submit' class='btn btn-dark btn-outline-light'>Filter</button></div>");
        out.println("</form>");
    }

}
