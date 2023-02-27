package net.botmoa.yonam.service;

import org.json.simple.JSONObject;

import java.util.Map;
import java.util.Objects;

public interface HttpURLConnService {

    JSONObject httpUrlConn(String hostURL, Map<String,Object> params,Map<String,String>headers);
}
