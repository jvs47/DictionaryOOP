import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Week4Test {
    @Test
    public void testMax2Int1(){
        assertEquals(Week4.max2Int(1,4),4);
    }

    @Test
    public void testMax2Int2(){
        assertEquals(Week4.max2Int(190,342),342);
    }

    @Test
    public void testMax2Int3(){
        assertEquals(Week4.max2Int(10,40),40);
    }

    @Test
    public void testMax2Int4(){
        assertEquals(Week4.max2Int(13424,4432143),4432143);
    }

    @Test
    public void testMax2Int5(){
        assertEquals(Week4.max2Int(4539458,489157843),489157843);
    }

    @Test
    public void testMinArray1(){
        assertEquals(Week4.minArray(new int[]{12, 34, 54, 32, 12}),12);
    }

    @Test
    public void testMinArray2(){
        assertEquals(Week4.minArray(new int[]{34, 93, 12,34, -342, 1341}),-342);
    }

    @Test
    public void testMinArray3(){
        assertEquals(Week4.minArray(new int[]{314,54, 42, 0, 32, 12}),0);
    }

    @Test
    public void testMinArray4(){
        assertEquals(Week4.minArray(new int[]{12, 34, 54, 32, 12}),12);
    }

    @Test
    public void testMinArray5(){
        assertEquals(Week4.minArray(new int[]{12, 34, 54, 32, 12}),12);
    }

    @Test
    public void testCalculateBMI1(){
        assertEquals(Week4.calculateBMI(58, 1.68), "Bình thường");
    }

    @Test
    public void testCalculateBMI2(){}

    @Test
    public void testCalculateBMI3(){}

    @Test
    public void testCalculateBMI4(){}

    @Test
    public void testCalculateBMI5(){}
}