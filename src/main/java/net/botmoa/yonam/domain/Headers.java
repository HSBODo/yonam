package net.botmoa.yonam.domain;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class Headers {
    @Value("${headers.remote_token}")
    private String remote_token;
    @Value("${headers.Cookie}")
    private String Cookie;
    @Value("${headers.timestamp}")
    private String timestamp;
    @Value("${headers.client_id}")
    private String client_id;
    @Value("${headers.swap_key}")
    private String swap_key;
    private Map<String,String> headers;

    public Headers() {
        this.headers = new HashMap<>();
    }

    public Map<String, String> getHerders() {

        System.out.println(this.remote_token);
        System.out.println(this.Cookie);
        System.out.println(this.timestamp);
        System.out.println(this.client_id);
        System.out.println(this.swap_key);
        this.headers.put("remote_token",this.remote_token);
        this.headers.put("Cookie",this.Cookie);
        this.headers.put("timestamp",this.timestamp);
        this.headers.put("client_id",this.client_id);
        this.headers.put("swap_key",this.swap_key);
        return this.headers;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
