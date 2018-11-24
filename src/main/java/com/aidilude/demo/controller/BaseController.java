package com.aidilude.demo.controller;

import com.aidilude.demo.util.BlockUtils;
import com.aidilude.demo.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

}