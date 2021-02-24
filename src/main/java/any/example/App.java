package any.example;

import any.example.cookie.ActiveCookie;

public class App {
    public static void main(String[] args) throws Exception {

        //String filepath = "C:/Temp/cookie.csv";
        //String date = "2018-12-09";

        if (args.length < 2) {
            throw new RuntimeException("Please provide 2 arguments. First is CSV file path and Second is date for e.g. C:/Temp/cookie.csv 2018-12-09");
        }
        String filepath = args[0];
        String date = args[1];
        String mostActiveCookies = new ActiveCookie().getMostActiveCookies(filepath, date);
        System.out.print(mostActiveCookies);
    }
}
