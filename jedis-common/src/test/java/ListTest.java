import com.lsm1998.jedis.common.struct.list.LinkedDList;
import org.junit.Test;

public class ListTest
{
    @Test
    public void listTest()
    {
        LinkedDList<Integer> list = new LinkedDList<>();
        list.addFirst(1);
        list.addLast(10);
        list.addLast(100);
        list.addLast(102);
        list.addLast(106);


        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
        System.out.println(list.get(4));

        list.delNode(list.get(4));

        list.foreach(System.out::println);
    }
}
