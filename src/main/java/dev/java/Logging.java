package dev.java;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public final class Logging {
    private static Logger logger = Logger.getLogger(Logging.class.getName());

    public void runMe(HttpServletRequest request) {
        logger.info(request.getMethod() + " " + request.getRequestURI());
    }

    public void runMe(Exception e) {
        logger.error(e);
        e.printStackTrace();
    }
}
