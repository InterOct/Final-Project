package by.epam.eshop.dao.resources;

import java.util.ResourceBundle;

/**
 * Created by Aspire on 08.04.2016.
 */
public class DBResourceManager {

    private static final String PATH = "by.epam.eshop.db";
    private static DBResourceManager instance = new DBResourceManager();

    private ResourceBundle bundle = ResourceBundle.getBundle(PATH);

    private DBResourceManager() {
    }

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
