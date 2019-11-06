package cn.zgx.tatistician.Util.DataUtil;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    public static Map<String, Integer> frequencyOfListElements(List<String> items) {
        if (items == null || items.size() == 0) return null;
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String temp : items) {
            Integer count = map.get(temp);
            map.put(temp, (count == null) ? 1 : count + 1);
        }
        return map;
    }

    /*public static Map<String, Integer> frequencyOfList(List<String> list) {
        if (list == null || list.size() == 0) return null;
        Map<String, Integer> map = new HashMap<String, Integer>();
        Set<String> uniqueSet = new HashSet(list);
        for (String temp : uniqueSet) {
            map.put(temp, Collections.frequency(list, temp));
        }
        return map;
    }*/

    /**
     * 统计List集合中每个元素出现的次数
     *
     * @return Map<String, Integer>
     */
    public static HashMap<String, Integer> countList(List<String> list) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        int count = 1;// 默认出现的次数为1
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) == list.get(j)) {
                    count++;// 次数+1
                    list.remove(j);
                    j--;// 这里是重点，因为集合remove（）后，长度改变了，对应的下标也不再是原来的下标，//仔细体会
                }
            }
            hashMap.put(list.get(i), count);
        }
        return hashMap;
    }

    /**
     * 判断一个字符串是否含有连续四个以上数字
     *
     * @param content
     * @return
     */
    public static boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile("[0-9]{4,}");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断是否为null
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if (str == null || str.trim().equals("") || str.trim().equalsIgnoreCase("null")) {
            return true;
        } else {
            return false;
        }
    }

}
