package com.volkspace.bookstore.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookStoreUtil {
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public static final SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);

    public static final String SESSION_USER_ID = "userlogin";

    public static final String MESSAGE_SUCCESS = "SUCCESS";
    public static final String MESSAGE_FAIL = "FAIL";
    public static final String MESSAGE_DATA_DUPLICATE = "DATA_DUPLICATE";

    public static Date parseDate(String dateStr) {
        Date date = null;
        try {
            date = sf.parse(dateStr);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }
}
