package com.sam.backend01;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class service {

    private Map<String, url> db = new HashMap<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Autowired
    private RestTemplate restTemplate;

    private final String LOG_URL = "http://20.244.56.144/evaluation-service/logs";

    public ResponseEntity<?> createShortUrl(urlreq req) {
        try {
            String code = req.shortcode != null ? req.shortcode : UUID.randomUUID().toString().substring(0, 6);
            if (db.containsKey(code)) return ResponseEntity.status(400).body(Map.of("error", "Shortcode already exists"));

            int validity = req.validity != null ? req.validity : 30;
            LocalDateTime expiry = LocalDateTime.now().plusMinutes(validity);
            url mapping = new url(req.url, code, expiry);

            db.put(code, mapping);

            log("backend", "info", "service", "Short URL created: " + code);

            urlres res = new urlres();
            res.shortLink = "http://localhost:8080/shorturls/" + code;
            res.expiry = expiry.format(formatter);
            return ResponseEntity.status(201).body(res);

        } catch (Exception e) {
            log("backend", "error", "service", e.getMessage());
            return ResponseEntity.status(500).body(Map.of("error", "Internal Server Error"));
        }
    }

    public ResponseEntity<?> redirect(String shortcode) {
        url mapping = db.get(shortcode);
        if (mapping == null) return ResponseEntity.status(404).body(Map.of("error", "Shortcode not found"));
        if (LocalDateTime.now().isAfter(mapping.getExpiry()))
            return ResponseEntity.status(410).body(Map.of("error", "Link expired"));

        mapping.getClicks().add(new info(LocalDateTime.now(), "browser", "IN"));
        log("backend", "info", "controller", "Redirected: " + shortcode);
        return ResponseEntity.status(302).header("Location", mapping.getOriginalUrl()).build();
    }

    public ResponseEntity<?> getStats(String shortcode) {
        url mapping = db.get(shortcode);
        if (mapping == null) return ResponseEntity.status(404).body(Map.of("error", "Shortcode not found"));

        StatsResponse res = new StatsResponse();
        res.clicks = mapping.getClicks().size();
        res.originalURL = mapping.getOriginalUrl();
        res.createdAt = mapping.getCreatedAt().format(formatter);
        res.expiry = mapping.getExpiry().format(formatter);
        res.clickDetails = new ArrayList<>();
        for (info click : mapping.getClicks()) {
            res.clickDetails.add(Map.of(
                    "timestamp", click.getTimestamp().format(formatter),
                    "source", click.getSource(),
                    "location", click.getLocation()
            ));
        }
        return ResponseEntity.ok(res);
    }

    private void log(String stack, String level, String pkg, String msg) {
        Map<String, String> log = new HashMap<>();
        log.put("stack", stack);
        log.put("level", level);
        log.put("package", pkg);
        log.put("message", msg);
        try {
            restTemplate.postForEntity(LOG_URL, log, String.class);
        } catch (Exception ignored) {}
    }
}
