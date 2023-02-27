package net.botmoa.yonam.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.botmoa.yonam.service.HttpURLConnService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

@Service
public class HttpURLConnServiceImpl implements HttpURLConnService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public JSONObject httpUrlConn(String hostURL, Map<String, Object> params,Map<String,String>headers) {
        JSONObject resoponseJobj= new JSONObject();

        try {

            URL url = new URL(hostURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //연결
            conn.setRequestMethod("POST"); // 전송 방식
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "application/json"); //응답 형식 유형 설정
            headers.forEach((s, s2) -> {
                conn.setRequestProperty(s,s2);
            });



            conn.setConnectTimeout(5000); // 연결 타임아웃 설정(5초)
            conn.setReadTimeout(5000); // 읽기 타임아웃 설정(5초)

            conn.setDoOutput(true);	// URL 연결을 출력용으로 사용(true)

            ObjectMapper mapper = new ObjectMapper();
            String requestBody =  mapper.writeValueAsString(params);
            logger.info("--------------- start request ----------------");
            logger.info("requestHeaders: "+ conn.getRequestProperties());
            logger.info("requestBody: " + requestBody);
            logger.info("--------------- start response ----------------");



            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            bw.write(requestBody);
            bw.flush();
            bw.close();


            Charset charset = Charset.forName("UTF-8");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();

            String response = sb.toString();
            JSONParser parser = new JSONParser();
            resoponseJobj= (JSONObject) parser.parse(response);

            logger.info("responseContentType: " + conn.getContentType()); // 응답 콘텐츠 유형 구하기
            logger.info("responseCode: "    + conn.getResponseCode()); // 응답 코드 구하기
            logger.info("responseMessage: " + conn.getResponseMessage()); // 응답 메시지 구하기
            logger.info("responseBody: " + response); // 응답 메시지 구하기

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resoponseJobj;
    }
}
