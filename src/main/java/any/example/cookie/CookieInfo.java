package any.example.cookie;

import java.time.LocalDate;

public class CookieInfo {
    String cookie;
    LocalDate localDate;

    public CookieInfo(String cookie, LocalDate dateTime) {
        this.cookie = cookie;
        this.localDate = dateTime;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
