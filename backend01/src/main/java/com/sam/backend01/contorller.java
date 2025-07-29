package com.sam.backend01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/shorturls")
public class contorller {

    @Autowired
    private service service;

    @PostMapping
    public ResponseEntity<?> createShortUrl(@RequestBody urlreq request) {
        return service.createShortUrl(request);
    }

    @GetMapping("/{shortcode}")
    public ResponseEntity<?> redirectToOriginal(@PathVariable String shortcode) {
        return service.redirect(shortcode);
    }

    @GetMapping("/{shortcode}/stats")
    public ResponseEntity<?> getStats(@PathVariable String shortcode) {
        return service.getStats(shortcode);
    }
}