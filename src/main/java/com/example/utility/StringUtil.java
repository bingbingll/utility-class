package com.example.utility;

/**
 * @author lib
 * @version V1.0
 * @description TODO:
 * @date 2019/12/10 11:48
 */
public class StringUtil {
    /**
     * 将数组转为字符串中间,号隔开数据转换为字符串
     *
     * @param objects
     * @return 0,1,2,3,4,5,6,7,8,9
     */
    public synchronized static String arrayConvertString(Object[] objects) {
        if (objects == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("");
        for (Object s : objects) {
            stringBuilder.append(s).append(",");
        }
        return stringBuilder.deleteCharAt(stringBuilder.toString().length()-1).toString();
    }
}
