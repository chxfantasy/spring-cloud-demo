package spring.cloud.client.uitils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Harry on 2017/4/14.
 */
public class DateUtils {

    public static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };
    public static final int[] arr = new int[]{20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};
    public static final String s = "魔羯水瓶双鱼白羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";

    /**
     * 根据日期获取生肖
     * @return
     */
    public static String getZodica(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return zodiacArr[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 根据日期获取星座
     * @return
     */
    public static String getConstellation(int month, int day) {
    	try {
    		int index = month * 2 - (day < arr[month - 1] ? 2 : 0);
        	return s.substring(index, index+2) + "座";			
		} catch (Exception e) {
			return "";
		}
    }
    
    public static String getTimeStr(long timeMilles) {
        if ( timeMilles <= 0 ) {
            timeMilles = System.currentTimeMillis();
        }

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sf.format(new Date(timeMilles));
    }
    
}
