import org.junit.Test;
import xyz.prinkov.algebraic.Quaternion;

import java.io.IOException;

/**
 * Created by akaroot on 02.11.16.
 */
public class QuaternionTest {
    @Test
    public void testAdd() throws IOException {
        Quaternion q = new Quaternion(1, 2, 3, 4, 17);
        Quaternion p = new Quaternion(2, 1, 3 ,4,  17);
        System.out.println(q.pow(739).pow(124));


    }
}
