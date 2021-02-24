package any.example.cookie;

import org.junit.Assert;
import org.junit.Test;

public class ActiveCookieTest {

    private ActiveCookie toTest = new ActiveCookie();

    @Test
    public void testGetActiveCookiesFromFile() {
        String filepath = "cookie.csv";
        String date = "2018-12-09";
        String mostActiveCookies = toTest.getMostActiveCookies(filepath, date);
        Assert.assertEquals("AtY0laUfhglK3lC7"+ System.getProperty("line.separator")
                            + "SAZuXPGUrfbcn5UA" + System.getProperty("line.separator")
                            , mostActiveCookies);
    }

    @Test(expected = RuntimeException.class)
    public void testGetActiveCookiesThrowsExceptionOnIncorrectFilePath() {
        String filepath = "wrongfile.csv";
        String date = "2018-12-09";
        toTest.getMostActiveCookies(filepath, date);
    }

    @Test
    public void testGetActiveCookiesIgnoreCookieWithIncorrectDateFormat() {
        String filepath = "cookieWithIncorrectDateFormat.csv";
        String date = "2018-12-09";
        String mostActiveCookies = toTest.getMostActiveCookies(filepath, date);
        Assert.assertEquals("AtY0laUfhglK3lC7" + System.getProperty("line.separator")
                            + "SAZuXPGUrfbcn5UA" + System.getProperty("line.separator")
                            + "5UAVanZf6UtGyKVS" + System.getProperty("line.separator")
                            , mostActiveCookies);
    }

    @Test
    public void testGetActiveCookiesIgnoreInvalidLines() {
        String filepath = "cookieWithIncorrectLine.csv";
        String date = "2018-12-09";
        String mostActiveCookies = toTest.getMostActiveCookies(filepath, date);
        Assert.assertEquals("AtY0laUfhglK3lC7"+ System.getProperty("line.separator")
                , mostActiveCookies);
    }

    @Test
    public void testGetActiveCookiesWithoutHeader() {
        String filepath = "cookieWithoutHeader.csv";
        String date = "2018-12-09";
        String mostActiveCookies = toTest.getMostActiveCookies(filepath, date);
        Assert.assertEquals("AtY0laUfhglK3lC7"+ System.getProperty("line.separator")
                        + "SAZuXPGUrfbcn5UA" + System.getProperty("line.separator")
                , mostActiveCookies);
    }
}
