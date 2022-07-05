package ua.edu.sumdu.j2ee.chepiha.eshop.client.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerMsgService {

    private Logger logger = null;
    private String className = null;

    public LoggerMsgService(Class className) {
        logger = LoggerFactory.getLogger(className.getName());
        this.className = className.getSimpleName();
    }

    public void msgInfo(String string) {
        logger.info(className + ": " + string);
    }

    public void msgDebug(String string) {
        logger.debug(className + ": " + string);
    }

    public void msgDebugCreate(String string) {
        logger.debug("Create new " + className + ": " + string);
    }


    public void msgDebugUpdateNewValue(String string) {
        logger.debug("Update " + className + "(new value): " + string);
    }

    public void msgDebugUpdateOldValue(String string) {
        logger.debug("Update " + className + "(old value): " + string);
    }

    public void msgDebugDelete(long id) {
        logger.debug("Delete " + className + " with Id: " + id);
    }

    public void msgDebugGetAll() {
        logger.debug("Get all " + className + "s");
    }

    public void msgDebugGetActualExchange() {
        logger.debug("Get all " + className + "s on last update");
    }

    public void msgDebugGetOne(long id) {
        logger.debug("Get " + className + " by Id: " + id);
    }

    public void msgDebugGetOne(String nameField, long field) {
        logger.debug("Get " + className + " by " + nameField + ": " + field);
    }


}
