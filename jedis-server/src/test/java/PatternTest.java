import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：刘时明
 * 时间：2021/1/28
 */

public class PatternTest
{
    @Test
    public void test1()
    {
        System.out.println(Pattern.matches("he.*.", "hello"));
        boolean ty=Pattern.compile("^\\w*$").matcher("hello").matches();
        System.out.println(ty);
        Matcher m = Pattern.compile("^h\\w*$").matcher("hello");
        System.out.println(m.find());
    }
}
