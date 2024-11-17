/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tags;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import utils.Santinization;

/**
 *
 * @author Trung
 */
public class ShowPage extends SimpleTagSupport {

    /**
     * Called by the container to invoke this tag. The implementation of this
     * method is provided by the tag library developer, and handles all tag
     * processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        JspWriter out = getJspContext().getOut();

        int curPage = Santinization.parseInt(request.getParameter("page"), 0);
        int totalPages = (int) request.getAttribute("pages");
        String curURL = (String) request.getAttribute("curURL");
        
        out.println("<nav class=\"pages mt-5\">");
        out.println("<ul class=\"pagination justify-content-center\">");
        out.println(getPrevButton(curPage, curURL));
        for (int i = 1; i <= totalPages; i++) {
            String isChosen = curPage + 1 == i ? "bg-black text-white" : "";
            String buttonUrl = curURL + "&page=" + (i - 1);
            out.println("<li class=\"page-item\"><a class=\"page-link " + isChosen + "\" href=\"" + buttonUrl + "\">" + i + "</a></li>");
        }
        out.println(getNextButton(curPage, totalPages, curURL));
        out.println("</ul>");
        out.println("</nav>");
    }

    private String getPrevButton(int curPage, String curURL) {
        String disabled = curPage < 1 ? "disabled" : "";
        String url = curURL + "&page=" + (curPage - 1);
        return "<li class=\"page-item\"><a class=\"page-link " + disabled + "\" href=\"" + url + "\"" + ">Previous</a></li>";
    }

    private String getNextButton(int curPage, int totalPages, String curURL) {
        String disabled = curPage + 1 >= totalPages ? "disabled" : "";
        String url = curURL + "&page=" + (curPage + 1);
        return "<li class=\"page-item\"><a class=\"page-link " + disabled + "\" href=\"" + url + "\"" + ">Next</a></li>";
    }
}
