package uz.nt.firstspring.utils;

import java.util.Date;

public class DateUtil {

    public static Date oneDay(){
        return new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
    }
}
