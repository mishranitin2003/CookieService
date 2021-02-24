package any.example.cookie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ActiveCookie {

    public String getMostActiveCookies(String filepath, String forDate) {
        List<String> lines = null;

        Path path = getFilePath(filepath);

        try {
            lines = Files.readAllLines(path);
        } catch (IOException ioException) {
            throw new RuntimeException("Problem reading file " + filepath);
        }

        // Assuming its a CSV file with first line as header and therefore not required to be parsed
        if (lines.get(0).contains(",timestamp")) {
            lines.remove(0);
        }

        LocalDate inputDate = LocalDate.parse(forDate);
        Map<String, Long> cookieCountMap = lines.stream()
                .map(line -> readCookieInfoFromLine(line))
                .filter(cookieInfo -> isValidCookieInfo(cookieInfo, inputDate))
                .collect(Collectors.groupingBy(CookieInfo::getCookie, Collectors.counting()));

        Long maxActiveCount = Collections.max(cookieCountMap.values());

        StringBuffer mostActiveCookies = new StringBuffer();
        cookieCountMap.forEach((k, v) -> {
            if (v == maxActiveCount) {
                mostActiveCookies.append(k).append(System.getProperty("line.separator"));
            }
        });

        return mostActiveCookies.toString();
    }

    private boolean isValidCookieInfo(CookieInfo cookieInfo, LocalDate inputDate) {
        return cookieInfo != null && cookieInfo.getLocalDate() != null
                && inputDate.compareTo(cookieInfo.getLocalDate()) == 0;
    }

    /*
    Ignore line if
        - line is empty
        - cookie is not present on a line
        - line doesn't contain comma
        - incorrect date format
     */
    private CookieInfo readCookieInfoFromLine(String line) {
        if (line == null || line.length() == 0 || !line.contains(",")) {
            return null;
        }
        String[] elements = Pattern.compile(",").split(line);
        String cookie = elements[0];

        if (cookie == null || cookie.trim().length() == 0) {
            return null;
        }
        String timestamp = elements[1];
        try {
            OffsetDateTime zonedDateTime = OffsetDateTime.parse(timestamp);
            LocalDate localDate = zonedDateTime.toLocalDate();
            return new CookieInfo(cookie, localDate);
        } catch (DateTimeParseException parseException) {
            return new CookieInfo(null, null);
        }
    }

    /*
    First search for file in classpath.
    If not found in classpath, search by absolute path
    If still not found, then Runtime Exception
     */
    private Path getFilePath(String filepath) {
        Path path = null;

        try {
            if (filepath != null) {
                path = Paths.get(ClassLoader.getSystemResource(filepath).toURI());
            }
        } catch (Exception anyException) {
            path = Paths.get(filepath);
        }

        if (path == null) {
            throw new RuntimeException("File " + filepath + " does not exist. Please use correct file path.");
        }
        return path;
    }
}
