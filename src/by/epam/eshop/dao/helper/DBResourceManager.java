package by.epam.eshop.dao.helper;

import java.util.ResourceBundle;

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
