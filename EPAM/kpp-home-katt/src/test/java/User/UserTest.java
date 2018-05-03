package User;



import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by kesso on 20.3.17.
 */
public class UserTest extends Assert{
    private static final Map<Boolean, String> checkLoad = new HashMap<Boolean, String>();

    @Before
    public void setUpCheckLoad(){
        checkLoad.put(true,"/home/kesso/Tests/1,3MB.mp3");
        checkLoad.put(false,"/home/kesso/Tests/16,2MB.mp3");
    }

    @After
    public void tearDownCheckLoad(){
        checkLoad.clear();
    }
    @org.junit.Test
    public void testcheckLoad() throws Exception {
        User user = new User("","","",1);
        for(Map.Entry<Boolean, String> entry : checkLoad.entrySet()){
            final String testData = entry.getValue();
            final Boolean expected = entry.getKey();
            final Boolean actual = user.checkLoad(new File(testData));
            assertEquals(expected,actual);
        }
    }

}