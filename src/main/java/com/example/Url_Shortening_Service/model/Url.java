package com.example.Url_Shortening_Service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    private String shortLink;
    @Lob
    private String originalUrl;
    private LocalDateTime created;
    private LocalDateTime expires;

    public Url() {
        // no-args constructor
    }
    public Url(LocalDateTime expires, long id, String shortLink, String originalUrl, LocalDateTime created) {
        this.expires = expires;
        this.id = id;
        this.shortLink = shortLink;
        this.originalUrl = originalUrl;
        this.created = created;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public LocalDateTime getExpires() {
        return expires;
    }
    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }

    public String getShortLink() {
        return shortLink;
    }
    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }
    public String getOriginalUrl() {
        return originalUrl;
    }
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", shortLink='" + shortLink + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", created=" + created +
                ", expires=" + expires +
                '}';
    }
}
