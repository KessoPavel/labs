package User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kesso on 15.05.17.
 */
public class UserLogicTest {
    @Test
    public void coding() throws Exception {

        final String expected = "21232f297a57a5a743894a0e4a801fc3";
        final String actual = UserLogic.coding("admin");
        assertEquals(expected, actual);

        final String expected1 = "c4ca4238a0b923820dcc509a6f75849b";
        final String actual1 = UserLogic.coding("1");
        assertEquals(expected1, actual1);

    }

}