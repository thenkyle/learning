package com.school.learning.controller;

import com.school.learning.controller.dto.response.RspBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    public RspBody Hello(){
        RspBody rspBody = new RspBody("200","Success","Hello World!!");
        //Declare SLF4J 的 Logger 物件 ，用來紀錄HelloController內不同級別的Logger訊息。
        Logger logger = LoggerFactory.getLogger(HelloController.class);

        //紀錄各種等級的事件訊息
        logger.info(rspBody.getRspCode()+","+rspBody.getRspMsg()+","+rspBody.getRspData());
        logger.warn("Hello API!!");
        logger.error("Hello API!!");

        return rspBody;
    }
}
