package cn.zgx.tatistician.Util.DataUtil;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.isLetter;


public class JudgeName {


    /**
     * 判断英文名长度是否正常
     * @param s
     * @return true为正常
     */
    public static Boolean judgEngLenRight(String s) {
        return len(s) <= 20;
    }

    /**
     * 判断是否纯中文
     * @param str
     * @return
     */
    public static boolean isAllChinese(String str){
        for (int i = 0; i < str.length(); i++) {  //遍历所有字符
            char ch = str.charAt(i);
            if(ch < 0x4E00 ||ch > 0x9FA5){  //中文在unicode编码中所在的区间为0x4E00-0x9FA5
                return false;  //不在这个区间，说明不是中文字符，返回false
            }
        }
        return true;
    }

    /**
     * 判断是否含有特殊字符
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     * @param  s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int len(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;

    }
}
