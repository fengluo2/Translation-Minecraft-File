package org.fengluo;

public class Utils {

    public static String JSON_STATEMENT = "//该文件由程序自动生成，难免有翻译不通处，但可以在此基础上进行进一步加工。\r\n//如果不嫌弃的话也可以凑合看看\r\n";
    public static String LANG_STATEMENT = "#该文件由程序自动生成，难免有翻译不通处，但可以在此基础上进行进一步加工。\r\n#如果不嫌弃的话也可以凑合看看\r\n";

    /**
     * 在某字符前后添加字段
     *
     * @param stringBuilder：原字符串
     * @param keyword：字符
     * @param before：在字符前需要插入的字段
     * @param rear：在字符后需要插入的字段
     * @return
     */
    public static String replacementInfo(StringBuilder stringBuilder, String keyword, String before, String rear) {
        //字符第一次出现的位置
        int index = stringBuilder.indexOf(keyword);
        while (index != -1) {
            stringBuilder.insert(index, before);
            stringBuilder.insert(index + before.length() + keyword.length(), rear);
            //下一次出现的位置，
            index = stringBuilder.indexOf(keyword, index + before.length() + keyword.length() + rear.length() - 1);
        }
        return stringBuilder.toString();
    }

    public static String replacementInfo(StringBuilder stringBuilder, String keyword) {
        //字符第一次出现的位置
        int index = stringBuilder.indexOf(keyword);
        while (index != -1) {
            if (index < stringBuilder.length() - 3 && stringBuilder.charAt(index + 2) != ' ' && stringBuilder.charAt(index + 2) != keyword.charAt(0)) {
                stringBuilder.insert(index + keyword.length() + 1, " ");
            }
            //下一次出现的位置，
            index = stringBuilder.indexOf(keyword, index + keyword.length());
        }
        return stringBuilder.toString();
    }
}
