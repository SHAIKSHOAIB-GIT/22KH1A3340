package com.sam.backend01;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class url{
    private String originalUrl;
    private String shortcode;
    private LocalDateTime expiry;
     private LocalDateTime createdAt;
    private List<info> clicks = new ArrayList<>();
   
   

    public url(String originalUrl, String shortcode, LocalDateTime expiry) {
        this.originalUrl = originalUrl;
        this.shortcode = shortcode;
        this.expiry = expiry;
        this.createdAt = LocalDateTime.now();
    }

     public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setClicks(List<info> clicks) {
        this.clicks = clicks;
    }   
    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortcode() {
        return shortcode;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<info> getClicks() {
        return clicks;
    }

    
}

