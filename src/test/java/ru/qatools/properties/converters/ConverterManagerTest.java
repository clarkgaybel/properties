package ru.qatools.properties.converters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.qatools.properties.testdata.PropEnum;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
@RunWith(Parameterized.class)
public class ConverterManagerTest {

    private StringSplitter splitter = new RegexStringSplitter(",");

    private ConverterManager manager = new ConverterManager(splitter);

    @Parameterized.Parameter(0)
    public String origin;

    @Parameterized.Parameter(1)
    public Object expect;

    @Parameterized.Parameter(2)
    public Class type;

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws MalformedURLException {
        return Arrays.asList(new Object[][]{
                {"hello world", "hello world", String.class},
                {"hello,world", new String[]{"hello", "world"}, String[].class},

                {"1.23456789", 1.23456789, Double.class},
                {"1.23456789, 32156.7", new Double[]{1.23456789, 32156.7}, Double[].class},
                {"1.23456789", 1.23456789, Double.TYPE},
                {"1.23456789, 32156.7", new double[]{1.23456789, 32156.7}, double[].class},

                {"1.2345", 1.2345F, Float.class},
                {"1.2345, 32156.7", new Float[]{1.2345F, 32156.7F}, Float[].class},
                {"1.2345", 1.2345F, Float.TYPE},
                {"1.2345, 32156.7", new float[]{1.2345F, 32156.7F}, float[].class},

                {"123", 123L, Long.class},
                {"1, 2 , 3", new Long[]{1L, 2L, 3L}, Long[].class},
                {"0", 0L, Long.TYPE},
                {"1,2,3", new long[]{1, 2, 3}, long[].class},

                {"123", 123, Integer.class},
                {"1, 2 , 3", new Integer[]{1, 2, 3}, Integer[].class},
                {"0", 0, Integer.TYPE},
                {"1,2,3", new int[]{1, 2, 3}, int[].class},

                {"123", (short) 123, Short.class},
                {"123, 24", new Short[]{123, 24}, Short[].class},
                {"-123", (short) -123, Short.TYPE},
                {"123, 24", new short[]{123, 24}, short[].class},

                {"1", (byte) 1, Byte.class},
                {"1,0,1", new Byte[]{1, 0, 1}, Byte[].class},
                {"0", (byte) 0, Byte.TYPE},
                {"0,0,0", new byte[]{0, 0, 0}, byte[].class},

                {"c", 'c', Character.class},
                {"a,c,n", new Character[]{'a', 'c', 'n'}, Character[].class},
                {"a", 'a', Character.TYPE},
                {"a,c,n", new char[]{'a', 'c', 'n'}, char[].class},

                {"true", true, Boolean.class},
                {"false,true,false", new Boolean[]{false, true, false}, Boolean[].class},
                {"false", false, Boolean.TYPE},
                {"false,true,true", new boolean[]{false, true, true}, boolean[].class},

                {"http://www.yandex.ru", new URL("http://www.yandex.ru"), URL.class},
                {"http://www.yandex.ru, http://www.ya.ru", new URL[]{ new URL("http://www.yandex.ru"), new URL("http://www.ya.ru")}, URL[].class},

                {"file:///foo", URI.create("file:///foo"), URI.class},
                {"file:///foo, file:///bar", new URI[]{ URI.create("file:///foo"), URI.create("file:///bar")}, URI[].class},

                {"UTF-8", StandardCharsets.UTF_8, Charset.class},
                {"UTF-8, UTF-16", new Charset[]{ StandardCharsets.UTF_8, StandardCharsets.UTF_16}, Charset[].class},

                {"mordor", PropEnum.MORDOR, PropEnum.class}
        });
    }

    @Test
    public void shouldConvert() throws Exception {
        Object converted = manager.convert(type, origin);
        assertThat(converted, is(expect));
    }
}
