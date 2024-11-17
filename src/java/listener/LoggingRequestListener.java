/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Web application lifecycle listener.
 *
 * @author Trung
 */
public class LoggingRequestListener implements ServletRequestListener {

    private static final Logger LOGGER = Logger.getLogger(LoggingRequestListener.class.getName());

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();

        logRequestInfo(req, "destroyed");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();

        logRequestInfo(req, "initialized");
    }

    private void logRequestInfo(HttpServletRequest request, String status) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String logMessage = String.format("[%s] [%s] [%s] [%s] [%s]",
                now.format(formatter),
                request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI(),
                request.getHeader("User-Agent"));

        LOGGER.info(String.format("Request %s: %s", status, logMessage));
    }
}
