package com.cncounter.test.string;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneTest {

    public static void main(String[] args) throws Exception {
        //
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        //
        String json = "{\"searchText\":\"\",\"business\":null,\"timeData\":[\"2023-02-28T16:00:00.000Z\",\"2023-03-13T16:00:00.000Z\"],\"startTime\":null,\"endTime\":null,\"pageSize\":10}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray timeData = jsonObject.getJSONArray("timeData");
        String str0 = timeData.getString(0);
        String str1 = timeData.getString(1);
        //
        Date date0 = format.parse(str0);
        Date date1 = format.parse(str1);

        //
        System.out.println("date0.getTime()=" + date0.getTime());
        System.out.println("date1.getTime()=" + date1.getTime());
        //
        System.out.println("date0=" + date0);
        System.out.println("date1=" + date1);
        //
        System.out.println("format.format(date0)=" + format.format(date0));
        System.out.println("format.format(date1)=" + format.format(date1));
        //
        SimpleDateFormat f8 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        System.out.println("f8.format(date0)=" + f8.format(date0));
        System.out.println("f8.format(date1)=" + f8.format(date1));

    }
}
