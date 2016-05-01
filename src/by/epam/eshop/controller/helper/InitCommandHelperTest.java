package by.epam.eshop.controller.helper;

import org.junit.Assume;
import org.junit.Test;

/**
 * Created by Aspire on 01.05.2016.
 */
public class InitCommandHelperTest  {
    @Test
    public void initTest() {
        Assume.assumeNotNull(InitCommandHelper.init());
    }
}
