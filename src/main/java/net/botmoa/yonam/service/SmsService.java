package net.botmoa.yonam.service;

import org.json.simple.JSONObject;

public interface SmsService {
    JSONObject smsSocketConn (String host,int port ,String path,String remoteId , String remotePwd,String remotePhone, String remoteCallback ,String msg) throws Exception;
}
