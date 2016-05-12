package com.escape.quickdevlibrary.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 处理字符串工具类
 * Created by john on 2016/3/25.
 */
public class StrUtil {

    public static String splicStr(String splice, String... str) {
        String s = null;
        for (String ss : str) {
            if (!TextUtils.isEmpty(ss)) {
                s = ss + splice;
            }
        }
        return s;
    }
    public static CharSequence strSetColor(Context context, String str1, String str2, int color) {
        SpannableStringBuilder sb = new SpannableStringBuilder(str1 + str2);
        sb.setSpan(new ForegroundColorSpan(context.getResources().getColor(color))
                , 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    public static boolean compareDate(String date1, String date2, String key) {
        String[] char1 = interceptStr(date1, key);
        String[] char2 = interceptStr(date2, key);

        if (char1 == null || char2 == null)
            return true;

        int y1 = Integer.parseInt(char1[0]);
        int m1 = Integer.parseInt(char2[0]);

        int y2 = Integer.parseInt(char1[1]);
        int m2 = Integer.parseInt(char2[1]);

        if (y1 == y2) {
            if (m2 <= m1) {
                return true;
            }
        } else if (y1 > y2) {
            return true;
        }
        return false;
    }
    public static boolean judgeByStr(String str, String key) {
        if (TextUtils.isEmpty(str))
            return false;
        if (str.equals(key)) {
            return true;
        } else {
            return false;
        }
    }
    public static String replaceStr(String str, String rStr) {
        if (TextUtils.isEmpty(str))
            return rStr;
        return str;
    }

    public static String interceptStr(String str, int len, boolean isOmit) {
        if (TextUtils.isEmpty(str))
            return "";
        if (str.length() < len)
            return str;
        if (isOmit) {
            return str.substring(0, len) + "...";
        } else {
            return str.substring(0, len);
        }
    }
    public static String[] interceptStr(String str, String key) {
        if (TextUtils.isEmpty(str))
            return null;
        return str.split(key);
    }
    public static String getPingYin(String str) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] input = str.trim().toCharArray();
        String output = "";
        try {
            for (char curchar : input) {
                if (java.lang.Character.toString(curchar).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(curchar, format);
                    output += temp[0];
                } else
                    output += java.lang.Character.toString(curchar);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }

    public static List<String> getPingYinList(List<String> list) {
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        return list;
    }
}
