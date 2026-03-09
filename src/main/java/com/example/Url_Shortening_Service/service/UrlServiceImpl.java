package com.example.Url_Shortening_Service.service;

import ch.qos.logback.core.util.StringUtil;
import com.example.Url_Shortening_Service.model.Url;
import com.example.Url_Shortening_Service.model.UrlDto;
import com.example.Url_Shortening_Service.repository.UrlRepository;
import com.google.common.hash.Hashing;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class UrlServiceImpl implements UrlService {
    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url gernerateShortLink(UrlDto urlDto) {
        if (StringUtils.isNotEmpty(urlDto.getUrl())){
            String encodedUrl = encodeUrl(urlDto.getUrl());
               Url urlTopersist = new Url();
               urlTopersist.setOriginalUrl(urlDto.getUrl());
               urlTopersist.setShortLink(encodedUrl);
               urlTopersist.setExpires(getEpirationDate(urlDto.getExpirationDate(),urlTopersist.getCreated()));
               Url urlToRet =persistShortLink(urlTopersist);
               if (urlToRet != null) return urlToRet;
               return null;
        }
        return null;
    }

    private LocalDateTime getEpirationDate(String expirationDate, LocalDateTime created) {
    if (StringUtils.isBlank(expirationDate)){
        return created.plusSeconds(60);
    }
    LocalDateTime expiresToret = LocalDateTime.parse(expirationDate);
    return expiresToret;
    }

    private String encodeUrl(String url) {
    String encodedUrl = "";
        LocalDateTime time = LocalDateTime.now();
        encodedUrl= String.valueOf(Hashing.murmur3_32()
                .hashString(url.concat(time.toString()), StandardCharsets.UTF_8));
        return encodedUrl;
    }


    @Override
    public Url persistShortLink(Url url) {
        Url urlToret = urlRepository.save(url);
        return url;
    }

    @Override
    public Url getEncodedUrl(String url) {
        Url urlToret = urlRepository.findByShortLink(url);
        return urlToret;
    }

    @Override
    public void deleteShortLink(Url url) {
        urlRepository.delete(url);
    }
}
