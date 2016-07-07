import by.epam.eshop.dao.UserDAO;
import by.epam.eshop.dao.exception.ConnectionPoolException;
import by.epam.eshop.dao.exception.DAOException;
import by.epam.eshop.dao.helper.ConnectionPool;
import by.epam.eshop.dao.impl.UserDAOImpl;
import by.epam.eshop.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDAOTest {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static ConnectionPool connectionPool;

    @BeforeClass
    public static void initConnectionPool() throws ConnectionPoolException {
        try {
            connectionPool = ConnectionPool.getInstance();
            connectionPool.initPoolData();
        } catch (ConnectionPoolException e) {
            LOGGER.error(e);
        }
    }

    @AfterClass
    public static void closeConnectionPool() {
        connectionPool.dispose();
    }

    @Test
    public void testFindByLoginPassword() {
        UserDAO userDAO = UserDAOImpl.getInstance();
        try {
            User user = userDAO.findByLoginPassword("admin", "admin");
            Assert.assertEquals("Admin", user.getFirstName());
            Assert.assertEquals("Admin", user.getLastName());
        } catch (DAOException e) {
            LOGGER.error(e);
        }
    }
}

