package com.example.Url_Shortening_Service.Controller;

import com.example.Url_Shortening_Service.model.Url;
import com.example.Url_Shortening_Service.model.UrlDto;
import com.example.Url_Shortening_Service.model.UrlErrorResponseDto;
import com.example.Url_Shortening_Service.model.UrlResponceDto;
import com.example.Url_Shortening_Service.service.UrlService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class UrlShorteningController {

@Autowired
    private UrlService urlService;


@PostMapping("/generate")
    public ResponseEntity<?> generateShortLink(@RequestBody UrlDto urlDto) {
    Url urlToret = urlService.gernerateShortLink(urlDto);
    if (urlToret != null) {

        UrlResponceDto urlResponceDto = new UrlResponceDto();
        urlResponceDto.setOriganalUrl(urlToret.getOriginalUrl());
        urlResponceDto.setExpirationDate(urlToret.getExpires());
        urlResponceDto.setUrlShortLink(urlToret.getShortLink());
        return new ResponseEntity<UrlResponceDto>(urlResponceDto, HttpStatus.OK);
    }
    UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
    urlErrorResponseDto.setStatus("404");
    urlErrorResponseDto.setError("there was an error generating the link please try again.");
    return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
}
    @GetMapping("/{shortLink}")
    public ResponseEntity<?> redirectToOriganalUrl(
            @PathVariable String shortLink,
            HttpServletResponse response) throws IOException {

    if (StringUtils.isEmpty(shortLink)) {
        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        urlErrorResponseDto.setStatus("400");
        urlErrorResponseDto.setError("invalid Url");
        return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
    }
    Url urlToret =urlService.getEncodedUrl(shortLink);
    if (urlToret == null) {
        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        urlErrorResponseDto.setStatus("400");
        urlErrorResponseDto.setError("Url doc does not exist or it just expired");
        return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
    }
    if (urlToret.getExpires() != null && urlToret.getExpires().isBefore(LocalDateTime.now())){
        urlService.deleteShortLink(urlToret);
        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        urlErrorResponseDto.setStatus("200");
        urlErrorResponseDto.setError("Url expired please trx generating a frech one ");
        return new ResponseEntity<>(urlErrorResponseDto, HttpStatus.OK);
    }
    response.sendRedirect(urlToret.getOriginalUrl());
    return null;
}

}
