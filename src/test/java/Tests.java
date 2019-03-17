import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.ArrayList;


public class Tests {

        static void plusTest (String expected, String lef, String rig){
        BigInteger obj1 = new BigInteger(lef);
        BigInteger obj2 = new BigInteger(rig);
        String result = obj1.plus(obj2);
        assertEquals(expected, result);
    }

        @Test
        public void plus () {
        plusTest("1008", "999", "9");
        plusTest("4", "2", "2");
        plusTest("60719476900", "60719476895", "5");
        plusTest("68719476736", "1", "68719476735");

        plusTest("4294967296", "4294967295", "1");
        plusTest("4", "2", "2");
        plusTest("48", "24", "24");
        plusTest("91288568294", "91217443908", "71124386");
    }

    static void minusTest(String expect, String lef, String rit) {
        BigInteger obj1 = new BigInteger(lef);
        BigInteger obj2 = new BigInteger(rit);
        String result = obj1.minus(obj2);
        assertEquals(expect, result);
    }

    @Test
    public void minus() {
        minusTest("91217443908", "91288568294", "71124386");
        minusTest("3", "7", "4");
        minusTest("2", "4", "2");
        minusTest("0", "117", "117");

    }

    static void  multiplyTest(String expect, String lef, String rit) {
        BigInteger obj1 = new BigInteger(lef);
        BigInteger obj2 = new BigInteger(rit);
        String result = obj1.multiply(obj2);
        assertEquals(expect, result);
    }

    @Test
    public void  multiply() {
        multiplyTest("81", "9", "9");
        multiplyTest("28", "7", "4");
        multiplyTest("4", "2", "2");
        multiplyTest("500", "100", "5");
        multiplyTest("0", "1", "0");
    }

    static void  divTest(String expect, String lef, String rit) {
        BigInteger obj1 = new BigInteger(lef);
        BigInteger obj2 = new BigInteger(rit);
        String result = obj1.div(obj2);
        assertEquals(expect, result);
    }

    @Test
    public void  div() {
        divTest("0", "0", "1");
        divTest("100", "500", "5");
        divTest("14", "9999", "87");
    }
}
