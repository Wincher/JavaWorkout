package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarTest {
    public static void main(String[] args) {

        getTimeOfMillionsTime(1563879779-1563850979);
        Calendar now = Calendar.getInstance();
        clearTime(now);
        System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(now.getTime()));
        Calendar expire = Calendar.getInstance();
        clearTime(expire);
        expire.add(Calendar.MONTH, -24);
        now.add(Calendar.MONTH, -24);
        System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(expire.getTime()));
        System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(now.getTime()));
        System.out.println(now.after(expire));
    }

    private static void clearTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void getTimeOfMillionsTime(long timestamp) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(timestamp);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);//24小时制
//		int hour = calendar.get(Calendar.HOUR);//12小时制
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        System.out.println(year + "年" + (month + 1) + "月" + day + "日" + hour + "时" + minute + "分" + second + "秒");
    }
}
