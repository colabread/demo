package com.aidilude.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class StringUtils {

    //###########################################正则式###########################################

    private static final String numberRegex = "^[-\\+]?[\\d]*$";

    private static final String phoneNumberRegex = "^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[89]))\\d{8})$";

    private static final String emailRegex = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    private static final String chineseRegex = "^[\u4e00-\u9fa5]+";

    private static final String IDNumberRegex = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";

    private static final String letterNumberRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";   //英文字母和数字组合，8到16位

    //###########################################验证函数###########################################

    public static boolean isEmpty(String str){
        if(str == null)
            return true;
        if(str.replace(" ", "").length() == 0)
            return true;
        return false;
    }

    public static boolean isNumber(String number) {
        if(isEmpty(number))
            return false;
        return number.matches(numberRegex);
    }

    public static boolean isPhoneNumber(String phoneNumber){
        if(isEmpty(phoneNumber))
            return false;
        return phoneNumber.matches(phoneNumberRegex);
    }

    public static boolean isEmail(String email){
        if(isEmpty(email))
            return false;
        return email.matches(emailRegex);
    }

    public static boolean isChinese(String chinese){
        if(isEmpty(chinese))
            return false;
        return chinese.matches(chineseRegex);
    }

    public static boolean isIDNumber(String IDNumber) {
        if(isEmpty(IDNumber))
            return false;
        boolean matches = IDNumber.matches(IDNumberRegex);
        //判断第18位校验值
        if (matches) {
            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + IDNumber);
                    return false;
                }
            }
        }
        return matches;
    }

    public static boolean isTimestamp(String timestamp){
        if(!isNumber(timestamp))
            return false;
        if(timestamp.length() != 13)
            return false;
        return true;
    }

    public static boolean isPrice(String price){
        if(isEmpty(price))
            return false;
        if(price.contains(".")){
            String intPart = price.substring(0, price.indexOf("."));
            String floatPart = price.substring(price.indexOf(".") + 1);
            if(!isNumber(intPart))
                return false;
            if(intPart.charAt(0) == '0')
                return false;
            if(!isNumber(floatPart))
                return false;
            if(floatPart.length() > 2)
                return false;
        }else{
            if(!isNumber(price))
                return false;
            if(price.charAt(0) == '0')
                return false;
        }
        return true;
    }

    public static boolean isRound(String round){
        if(isEmpty(round))
            return false;
        if(round.length() != 10)
            return false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(round);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    //###########################################生成函数###########################################

    /**
     * 将手机号中间四位变成*
     * @param phone
     * @return
     */
    public static String shieldPhone(String phone){
        String prefix = phone.substring(0, 3);
        String suffix = phone.substring(7, 11);
        return prefix + "****" + suffix;
    }

    /**
     * 创建uuid（32位）
     * @return
     */
    public static String createUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 生成字母（全大写）和数字的随机码
     * 不能同时包含0和O 或 1和I
     * @param length 长度
     * @return
     */
    public static String createLetterNumRandomCode(int length){
        String val = "";
        Random random = new Random();
        for ( int i = 0; i < length; i++ )
        {
            String str = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
            if ( "char".equalsIgnoreCase( str ) )
            { // 产生字母
                int nextInt = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
                // System.out.println(nextInt + "!!!!"); 1,0,1,1,1,0,0
                val += (char) ( nextInt + random.nextInt( 26 ) );
            }
            else if ( "num".equalsIgnoreCase( str ) )
            { // 产生数字
                val += String.valueOf( random.nextInt( 10 ) );
            }
        }
        val = val.toUpperCase();
        if((val.contains("O") && val.contains("0")) || (val.contains("I") && val.contains("1")))
            return createLetterNumRandomCode(length);
        return val;
    }

    /**
     * 生成数字随机码（第一位不能为0）
     * @param length 长度
     * @return
     */
    public static String createNumberRandomCode(int length)
    {
        StringBuilder sb = new StringBuilder();
        String base = "0123456789";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(base.charAt(random.nextInt(9)));
        }
        String result = sb.toString();
        if(result.charAt(0) == '0'){
            int max=9;
            int min=1;
            String s = String.valueOf(random.nextInt(max)%(max-min+1) + min);
            result = s + result.substring(1);
        }
        return result;
    }

    /**
     * 获取上一轮轮次字符串
     * @return   yyyy-MM-dd
     */
    public static String gainLastRound(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取本轮轮次字符串
     * @return   yyyy-MM-dd
     */
    public static String gainCurrentRound(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return sdf.format(cal.getTime());
    }

    //测试
    public static void main(String[] args) throws ParseException {

    }

}