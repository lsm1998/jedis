import com.lsm1998.jedis.common.struct.set.IntSet;
import org.junit.Test;

/**
 * 作者：刘时明
 * 时间：2021/1/29
 */

public class IntSetTest
{
    @Test
    public void test1()
    {
        IntSet set = new IntSet();
        set.add(1);
        set.add(2);
        set.add(5);
        set.add(4);
        set.add(7);
        set.add(8);

        set.add(Short.MAX_VALUE + 10);
        set.add(Short.MAX_VALUE + 1);
        System.out.println(set.toList());
        set.remove(8);
        System.out.println(set.toList());
    }
}
