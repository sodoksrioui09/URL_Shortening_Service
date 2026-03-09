package com.example.Url_Shortening_Service.service;

import com.example.Url_Shortening_Service.model.Url;
import com.example.Url_Shortening_Service.model.UrlDto;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {
    public Url gernerateShortLink(UrlDto urlDto);
    public Url persistShortLink(Url url);
    public Url getEncodedUrl(String url);
    public void deleteShortLink(Url url);
}
