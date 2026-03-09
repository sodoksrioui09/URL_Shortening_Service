package com.example.Url_Shortening_Service.model;

import java.time.LocalDateTime;

public class UrlResponceDto {
    private String OriganalUrl;
    private String UrlShortLink;
    private LocalDateTime expirationDate;

    public UrlResponceDto(String origanalUrl, String urlShortLink, LocalDateTime expirationDate) {
        OriganalUrl = origanalUrl;
        UrlShortLink = urlShortLink;
        this.expirationDate = expirationDate;
    }
    public UrlResponceDto() {};
    public String getOriganalUrl() {
        return OriganalUrl;
    }

    public void setOriganalUrl(String origanalUrl) {
        OriganalUrl = origanalUrl;
    }

    public String getUrlShortLink() {
        return UrlShortLink;
    }

    public void setUrlShortLink(String urlShortLink) {
        UrlShortLink = urlShortLink;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "UrlResponceDto{" +
                "OriganalUrl='" + OriganalUrl + '\'' +
                ", UrlShortLink='" + UrlShortLink + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
