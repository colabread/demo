package com.aidilude.demo.task;

import com.aidilude.demo.util.BlockUtils;
import com.aidilude.demo.util.HttpUtils;
import com.aidilude.demo.util.SMUtils;
import com.aidilude.demo.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class BlockCheckTask {

    @Scheduled(cron = "0 */2 * * * ?")   //每分钟执行一次
    public void run(){
        log.info("【区块检查任务】开始执行");
        log.info("【当前区块数据】高度：【" + BlockUtils.getBlockHeight() + "】，异常次数：【" + BlockUtils.getExceptionCount() + "】，警告次数：【" + BlockUtils.getSendMsgCount() + "】");
        String result = null;
        try {
            result = HttpUtils.doGet("http://47.244.98.189:8192/api/chains/ASCHBet/blocks/height", null);
        } catch (Exception e) {
            log.error("【区块检查任务】调用区块接口异常", e);
        }
        if(StringUtils.isEmpty(result))
            log.error("【区块检查任务】调用区块接口异常");
        JSONObject jsonResult = JSONObject.parseObject(result);
        if(jsonResult.getBooleanValue("success") != true){
            log.error("【区块检查任务】success：false，发送短信警告");
            sendMsg("777777");
        }else{
            Integer nowBlockHeight = jsonResult.getInteger("height");
            if(BlockUtils.getBlockHeight() == 0){
                log.info("【区块检查任务】初始化区块高度：【" + nowBlockHeight + "】");
                BlockUtils.setBlockHeight(nowBlockHeight);
            }else{
                if(BlockUtils.getBlockHeight().equals(nowBlockHeight)){   // --> 区块高度不增加，区块异常
                    if(BlockUtils.getExceptionCount() >= 1){   // --> 异常次数达到两次
                        if(BlockUtils.getSendMsgCount() < 2){   // --> 短信通知次数小于2次，发送短信警告
                            log.error("【区块检查任务】区块异常，次数达到两次，发送短信警告，高度：【" + nowBlockHeight + "】");
                            sendMsg("666666");
                            BlockUtils.setSendMsgCount(BlockUtils.getSendMsgCount() + 1);
                        }else {
                            log.error("【区块检查任务】区块异常，短信次数超过两次，高度：【" + nowBlockHeight + "】");
                        }
                    }else{
                        log.error("【区块检查任务】区块异常，更新异常次数，高度：【" + nowBlockHeight + "】");
                        BlockUtils.setExceptionCount(BlockUtils.getExceptionCount() + 1);
                    }
                }else{
                    log.info("【区块检查任务】区块正常，更新高度，高度：【" + nowBlockHeight + "】");
                    BlockUtils.setBlockHeight(nowBlockHeight);
                }
            }
        }
        log.info("【区块检查任务】执行结束");
        log.info("**************************************************************************");
    }

    public void sendMsg(String msg){
        log.info("【区块检查任务】发送短信结果：" + SMUtils.sendMsg(msg, SMUtils.phoneTang));
        log.info("【区块检查任务】发送短信结果：" + SMUtils.sendMsg(msg, SMUtils.phoneYang));
        log.info("【区块检查任务】发送短信结果：" + SMUtils.sendMsg(msg, SMUtils.phoneDu));
    }

}