package com.aidilude.demo.util;

public class BlockUtils {

    private static Integer blockHeight = 0;

    private static Integer exceptionCount = 0;

    private static Integer sendMsgCount = 0;

    private static Integer requestFailedCount = 0;

    public static Integer getBlockHeight() {
        return blockHeight;
    }

    public static void setBlockHeight(Integer blockHeight) {
        BlockUtils.blockHeight = blockHeight;
    }

    public static Integer getExceptionCount() {
        return exceptionCount;
    }

    public static void setExceptionCount(Integer exceptionCount) {
        BlockUtils.exceptionCount = exceptionCount;
    }

    public static Integer getSendMsgCount() {
        return sendMsgCount;
    }

    public static void setSendMsgCount(Integer sendMsgCount) {
        BlockUtils.sendMsgCount = sendMsgCount;
    }

    public static Integer getRequestFailedCount() { return requestFailedCount; }

    public static void setRequestFailedCount(Integer requestFailedCount) { BlockUtils.requestFailedCount = requestFailedCount; }

    public static void reset(){
        BlockUtils.exceptionCount = 0;
        BlockUtils.sendMsgCount = 0;
        BlockUtils.requestFailedCount = 0;
    }

}