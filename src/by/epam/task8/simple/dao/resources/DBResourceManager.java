package by.epam.task8.simple.dao.resources;

import java.util.ResourceBundle;

/**
 * Created by Aspire on 08.04.2016.
 */
public class DBResourceManager {

    private static final String BY_EPAM_TASK8_SIMPLE_DAO_RESOURCES_DB = "by.epam.task8.simple.dao.resources.db";
    private static DBResourceManager instance = new DBResourceManager();

    private ResourceBundle bundle = ResourceBundle.getBundle(BY_EPAM_TASK8_SIMPLE_DAO_RESOURCES_DB);

    private DBResourceManager() {
    }

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
