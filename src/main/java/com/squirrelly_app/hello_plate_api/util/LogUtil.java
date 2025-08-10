package com.squirrelly_app.hello_plate_api.util;

import org.slf4j.Logger;

@SuppressWarnings("unused")
public class LogUtil {

    private static final String TEMPLATE = "ID: {} {}: {} {}: {}";

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    private static final String REQUEST = "REQUEST";
    private static final String RESPONSE = "RESPONSE";
    private static final String ERROR = "ERROR";

    public static void getRequest(Logger logger, Long callId, String callTag, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(TEMPLATE, callId, GET, callTag, REQUEST, message);
        }
    }

    public static void getResponse(Logger logger, Long callId, String callTag, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(TEMPLATE, callId, GET, callTag, RESPONSE, message);
        }
    }

    public static void getError(Logger logger, Long callId, String callTag, String message) {
        if (logger.isErrorEnabled()) {
            logger.error(TEMPLATE, callId, GET, callTag, ERROR, message);
        }
    }

    public static void postRequest(Logger logger, Long callId, String callTag, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(TEMPLATE, callId, POST, callTag, REQUEST, message);
        }
    }

    public static void postResponse(Logger logger, Long callId, String callTag, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(TEMPLATE, callId, POST, callTag, RESPONSE, message);
        }
    }

    public static void postError(Logger logger, Long callId, String callTag, String message) {
        if (logger.isErrorEnabled()) {
            logger.error(TEMPLATE, callId, POST, callTag, ERROR, message);
        }
    }

    public static void putRequest(Logger logger, Long callId, String callTag, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(TEMPLATE, callId, PUT, callTag, REQUEST, message);
        }
    }

    public static void putResponse(Logger logger, Long callId, String callTag, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(TEMPLATE, callId, PUT, callTag, RESPONSE, message);
        }
    }

    public static void putError(Logger logger, Long callId, String callTag, String message) {
        if (logger.isErrorEnabled()) {
            logger.error(TEMPLATE, callId, PUT, callTag, ERROR, message);
        }
    }

    public static void deleteRequest(Logger logger, Long callId, String callTag, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(TEMPLATE, callId, DELETE, callTag, REQUEST, message);
        }
    }

    public static void deleteResponse(Logger logger, Long callId, String callTag, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(TEMPLATE, callId, DELETE, callTag, RESPONSE, message);
        }
    }

    public static void deleteError(Logger logger, Long callId, String callTag, String message) {
        if (logger.isErrorEnabled()) {
            logger.error(TEMPLATE, callId, DELETE, callTag, ERROR, message);
        }
    }

}
