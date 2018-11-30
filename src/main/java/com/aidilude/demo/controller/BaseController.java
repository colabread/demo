package com.aidilude.demo.controller;

import com.aidilude.demo.util.BlockUtils;
import com.aidilude.demo.util.HttpUtils;
import com.aidilude.demo.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/base")
public class BaseController {

    @GetMapping("/resetBlockCheck")
    public String resetBlockCheck(String secret){
        if(StringUtils.isEmpty(secret))
            return "403";
        if(!secret.equals("mxb44hEQHxlkdl7aYnTMCvF5zTuoQyEZet3tGobU6VfemON4EM05RGtRdFw7TjCZ"))
            return "503";
        BlockUtils.reset();
        return "666";
    }

    @GetMapping("/queryBlockData")
    public String queryBlockData(String secret){
        if(StringUtils.isEmpty(secret))
            return "403";
        if(!secret.equals("mxb44hEQHxlkdl7aYnTMCvF5zTuoQyEZet3tGobU6VfemON4EM05RGtRdFw7TjCZ"))
            return "503";
        Map<String, Object> result = new HashMap<>();
        result.put("blockHeight", BlockUtils.getBlockHeight());
        result.put("exceptionCount", BlockUtils.getExceptionCount());
        result.put("sendMsgCount", BlockUtils.getSendMsgCount());
        return JSON.toJSONString(result);
    }

    @GetMapping("/queryCustomerProfileImageLink")
    public String queryCustomerProfileImageLink(){
        String api = "https://api.steemjs.com/get_accounts?names[]=%5B%22hll%22%5D";
        String result = null;
        try {
            result = HttpUtils.doHttpsGet(api, null);
        } catch (Exception e) {
            e.printStackTrace();
            return "401";
        }
        if(StringUtils.isEmpty(result))
            return "402";
        List<String> imageLinks = new ArrayList<>();
        JSONArray jsonArrayResult = JSONArray.parseArray(result);
        for(int i = 0;i < jsonArrayResult.size();i++){
            JSONObject jsonResult = (JSONObject)jsonArrayResult.get(i);
            JSONObject json_metadata = JSONObject.parseObject(jsonResult.getString("json_metadata"));
            JSONObject profile = json_metadata.getJSONObject("profile");
            String profile_image = profile.getString("profile_image");
            imageLinks.add(profile_image);
        }
        return JSON.toJSONString(imageLinks);
    }

    @GetMapping("/querySome")
    public String querySome(){
        String api = "https://helloacm.com/api/steemit/vote-value/?cached&id=justyy";
        String result = null;
        try {
            result = HttpUtils.doHttpsGet(api, null);
        } catch (Exception e) {
            e.printStackTrace();
            return "401";
        }
        if(StringUtils.isEmpty(result))
            return "402";
        return result;
    }

}