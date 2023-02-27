package net.botmoa.yonam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.botmoa.yonam.domain.Headers;
import net.botmoa.yonam.domain.SmsDto;
import net.botmoa.yonam.service.HttpURLConnService;
import net.botmoa.yonam.service.SmsService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/*")
public class Api {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${hostUrl}")
    private  String hostUrl ;
    @Value("${headers.remote_token}")
    private  String remote_token ;
    @Value("${headers.Cookie}")
    private  String Cookie ;
    @Value("${headers.timestamp}")
    private  String timestamp ;
    @Value("${headers.client_id}")
    private  String client_id ;
    @Value("${headers.swap_key}")
    private  String swap_key ;
    private static String timeStamp(){
        Date date = new Date();
        long time = date.getTime();
        String nowTime = String.valueOf(time);
        return nowTime;
    }

    @Autowired
    HttpURLConnService httpURLConnService;
    @Autowired
    SmsService smsService;
    private static ObjectMapper mapper = new ObjectMapper();
    @ResponseBody
    @RequestMapping(value = "login" , method= {RequestMethod.POST},headers = {"Accept=application/json; UTF-8"})
    public String login(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
        String responseBody ="" ;
        try {
            Map<String,String> headers = new HashMap<>();
            headers.put("remote_token",remote_token);
            headers.put("Cookie",Cookie);
            headers.put("timestamp",timeStamp());
            headers.put("client_id",client_id);
            headers.put("swap_key",swap_key);

            JSONObject responseJson = httpURLConnService.httpUrlConn(hostUrl+"/chatbotLogin.do",params,headers);
            responseBody = mapper.writeValueAsString(responseJson);
        }catch (Exception e){
            e.printStackTrace();
        }

        return responseBody;
    }
    @ResponseBody
    @RequestMapping(value = "validationCheck" , method= {RequestMethod.POST},headers = {"Accept=application/json; UTF-8"})
    public String validationCheck(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
        String responseBody ="" ;


        try {
            Map<String,String> headers = new HashMap<>();
            headers.put("remote_token",remote_token);
            headers.put("cookie",Cookie);
            headers.put("timestamp",timestamp);
            headers.put("client_id",client_id);
            headers.put("swap_key",swap_key);

            JSONObject responseJson = httpURLConnService.httpUrlConn(hostUrl+"/chatbotGetMealMenu.do",params,headers);
            responseBody = mapper.writeValueAsString(responseJson);
        }catch (Exception e){
            e.printStackTrace();
        }

        return responseBody;
    }

    @ResponseBody
    @RequestMapping(value = "smsSend" , method= {RequestMethod.POST},headers = {"Accept=application/json; UTF-8"})
    public String smsSend(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
        String responseBody ="" ;
        try {
            String phone = (String)params.get("hphone");
            String msg ="SMS발송 성공 \n인증코드[123456]";
            SmsDto smsProp = new SmsDto(phone,msg);
           JSONObject result = smsService.smsSocketConn(
                    smsProp.getHost(),
                    smsProp.getPort(),
                    smsProp.getPath(),
                    smsProp.getRemoteId(),
                    smsProp.getRemotePwd(),
                    smsProp.getRemotePhone(),
                    smsProp.getRemoteCallback(),
                    smsProp.getMsg()
            );
            responseBody = mapper.writeValueAsString(result);
            logger.info("final"+result.get("success"));
            logger.info("final"+result.get("msg"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return responseBody;
    }
}




