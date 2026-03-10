package com.example.Url_Shortening_Service.service;

import ch.qos.logback.core.util.StringUtil;
import com.example.Url_Shortening_Service.model.Url;
import com.example.Url_Shortening_Service.model.UrlDto;
import com.example.Url_Shortening_Service.repository.UrlRepository;
import com.google.common.hash.Hashing;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Url gernerateShortLink(UrlDto urlDto) {
        if (StringUtils.isNotEmpty(urlDto.getUrl())){
            String encodedUrl = encodeUrl(urlDto.getUrl());
               Url urlTopersist = new Url();
               urlTopersist.setOriginalUrl(urlDto.getUrl());
               urlTopersist.setShortLink(encodedUrl);
               if (StringUtils.isNotBlank(urlDto.getExpirationDate())){
               urlTopersist.setExpires(getEpirationDate(urlDto.getExpirationDate(),urlTopersist.getCreated()));
               }
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
        try{
        Url urlToret = urlRepository.save(url);
        return urlToret;
        }
        catch (DataIntegrityViolationException e){
            //short_link already exists → regenerate
            url.setShortLink(encodeUrl(url.getShortLink()+LocalDateTime.now()));
            return urlRepository.save(url);
        }
    }

    @Override
    public Url getEncodedUrl(String shortUrl) {

        String cachekey= "shortLink:"+ shortUrl;
        // erst check redis
        String cachedUrl = redisTemplate.opsForValue().get(cachekey);
        if (cachedUrl != null){
            Url urlToret= new Url();
            urlToret.setOriginalUrl(cachedUrl);
            urlToret.setShortLink(shortUrl);
            return urlToret;
        }
        //2 not found in redis then check postgre
        Url urlToret = urlRepository.findByShortLink(shortUrl);
        if (urlToret != null){
            //3 store in redis
            redisTemplate.opsForValue().set(
                    cachekey,
                    urlToret.getOriginalUrl(),
                    Duration.ofHours(1)
            );
        }
        return urlToret;
    }

    @Override
    public void deleteShortLink(Url url) {
        urlRepository.delete(url);
    }
}
