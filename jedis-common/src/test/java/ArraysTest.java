import com.lsm1998.jedis.common.utils.ArraysUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * 作者：刘时明
 * 时间：2021/1/26
 */

public class ArraysTest
{
    @Test
    public void arraysTest()
    {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(ArraysUtils.append(5, 100, array, Integer.class)));
    }
}
