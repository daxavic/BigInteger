import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class Tests {
   static void equalityTest(int expected, String lef, String rig){
       BigInteger obj1 = new BigInteger(lef);
       BigInteger obj2 = new BigInteger(rig);
       int result = obj1.equality(obj2);
        assertEquals(expected, result);
    }
    @Test
    public void equality () {
       equalityTest(0, "123456789987654321", "123456789987654321");
       equalityTest(1, "9978767567564543453423445678989896756543", "99787675675645434534234456789898");
       equalityTest(2, "111111111187654321", "123456789987654321");

    }

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
            plusTest("121932631112635269", "98765432100000000", "23167199012635269");
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
        minusTest("909", "1008", "99");
        minusTest("786456341510091", "786545342498978", "89000988887");

    }

    static void  multiplyTest(String expect, String lef, String rit) {
        BigInteger obj1 = new BigInteger(lef);
        BigInteger obj2 = new BigInteger(rit);
        String result = obj1.multiply(obj2);
        assertEquals(expect, result);
    }

    @Test
    public void  multiply() {
        multiplyTest("51948", "999", "52");
        multiplyTest("4", "2", "2");
        multiplyTest("500", "100", "5");
        multiplyTest("0", "1", "0");
        multiplyTest("81", "9", "9");
        multiplyTest("121932631112635269", "987654321", "123456789");
        multiplyTest("75892268083671675044", "7689509798", "9869584678");
        multiplyTest("839416315003641515507739245048705929950", "8504830679486059757485", "98698768574939670");
    }

    static void  divTest(String expect, String lef, String rit) {
        BigInteger obj1 = new BigInteger(lef);
        BigInteger obj2 = new BigInteger(rit);
        String result = obj1.div(obj2);
        assertEquals(expect, result);
    }

    @Test
    public void  div() {
        divTest("102", "510", "5");
        divTest("0", "9999", "99999999");
        divTest("1", "9999", "9999");
        divTest("114", "9999", "87");
        divTest("102", "510", "5");
        divTest("0", "0", "1");
        divTest("100", "500", "5");
        divTest("845850160668657", "73247965406850780697065", "86596857");

    }

    static void  modTest(String expect, String lef, String rit) {
        BigInteger obj1 = new BigInteger(lef);
        BigInteger obj2 = new BigInteger(rit);
        String result = obj1.mod(obj2);
        assertEquals(expect, result);
    }

    @Test
    public void  mod() {
       modTest("3767", "495839", "23432");
       modTest("0" , "510" , "5");
       modTest("34" , "1000934" , "100");
       modTest("34" , "1000934" , "100");
       modTest("0" , "999" , "999");
       modTest( "8", "495839", "21");

    }
}

