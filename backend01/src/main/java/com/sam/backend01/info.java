package com.sam.backend01;

import java.time.LocalDateTime;

public class info {
    private LocalDateTime timestamp;
    private String source;
    private String location;

    public info(LocalDateTime timestamp, String source, String location) {
        this.timestamp = timestamp;
        this.source = source;
        this.location = location;
    }


    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getSource() {
        return source;
    }

    public String getLocation() {
        return location;
    }

    

 
    
}

