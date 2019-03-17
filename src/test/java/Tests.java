import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.ArrayList;


public class Tests {

    static void plusTest(String expected, String lhv, String rhv) {
        BigInteger controlObj = new BigInteger(expected);
        BigInteger obj1 = new BigInteger(lhv);
        BigInteger obj2 = new BigInteger(rhv);
        String result = obj1.plus(obj2);
        assertEquals(controlObj, result);
    }

    @Test
    public void plus() {
        plusTest("2469135780", "1234567890", "1234567890");
        plusTest("4", "2", "2");
        plusTest("48", "24", "24");
        plusTest("4294967296", "4294967295", "1");
        plusTest("68719476736", "68719476735", "1");
        plusTest("68719476736", "1", "68719476735");

    }
}
