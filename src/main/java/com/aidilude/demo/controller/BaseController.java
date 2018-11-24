package com.aidilude.demo.controller;

import com.aidilude.demo.util.BlockUtils;
import com.aidilude.demo.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}