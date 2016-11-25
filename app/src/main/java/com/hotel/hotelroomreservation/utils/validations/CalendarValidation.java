package com.hotel.hotelroomreservation.utils.validations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CalendarValidation {

    public static Calendar[] getSelectableDates(Calendar arrivalCalendar, List<Date> arrivalDates) {
        List<Calendar> selectableDates = new ArrayList<>();
        Date dateArrival = arrivalCalendar.getTime();

        Collections.sort(arrivalDates);

        for (Date date : arrivalDates) {
            if (dateArrival.before(date)) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateArrival);

                while (cal.getTime().getTime() <= date.getTime()) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(cal.getTime());
                    selectableDates.add(c);

                    cal.add(Calendar.DATE, 1);
                    cal.set(Calendar.MILLISECOND, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.HOUR, 0);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                }
                break;
            }
        }

        Calendar[] calendarDates = new Calendar[selectableDates.size()];
        return selectableDates.toArray(calendarDates);
    }
}
