package net.botmoa.yonam.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.botmoa.yonam.service.SmsService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public JSONObject smsSocketConn(String host,int port ,String path,String remoteId , String remotePwd,String remotePhone, String remoteCallback ,String msg ) throws Exception {

        String data = URLEncoder.encode("remote_id", "UTF-8") + "=" + URLEncoder.encode(remoteId, "UTF-8");
        data += "&" + URLEncoder.encode("remote_pass", "UTF-8") + "=" + URLEncoder.encode(remotePwd, "UTF-8");
        data += "&" + URLEncoder.encode("remote_phone", "UTF-8") + "=" + URLEncoder.encode(remotePhone, "UTF-8");
        data += "&" + URLEncoder.encode("remote_callback", "UTF-8") + "=" + URLEncoder.encode(remoteCallback, "UTF-8");
        data += "&" + URLEncoder.encode("remote_msg", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8");

        Socket sk = new Socket(host,port);
        logger.info("----------- start socket request ------------");
        logger.info("socketConn: "+sk);
        logger.info("requestBody: "+data);

        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream(), "UTF-8"));
        wr.write("POST "+path+" HTTP/1.0\r\n");
        wr.write("Content-Length: "+data.length()+"\r\n");
        wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
        wr.write("\r\n");

        // Send data
        wr.write(data);
        wr.flush();

        // Get response
        BufferedReader rd = new BufferedReader(new InputStreamReader(sk.getInputStream(),"UTF-8"));
        String line;
        ArrayList<String> response = new ArrayList<>();
        logger.info("----------- start socket response ------------");
        while ((line = rd.readLine()) != null) {
            response.add(line);
        }
        Map<String,String> resultMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JSONParser parser = new JSONParser();

        String responseResult = response.get(response.size()-1);
        String[] resultArr= responseResult.split("[|]");
        resultMap.put("success",resultArr[0]);
        resultMap.put("msg",resultArr[1]);
        String resultMaptoJsonStr =  mapper.writeValueAsString(resultMap);
        JSONObject result= (JSONObject) parser.parse(resultMaptoJsonStr);
        logger.info("result"+result);

        wr.close();
        rd.close();
        return result;
    }
}
