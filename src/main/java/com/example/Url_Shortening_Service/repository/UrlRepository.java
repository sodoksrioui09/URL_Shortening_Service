package com.example.Url_Shortening_Service.repository;


import com.example.Url_Shortening_Service.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    public Url findByShortLink(String shortLink);

}
