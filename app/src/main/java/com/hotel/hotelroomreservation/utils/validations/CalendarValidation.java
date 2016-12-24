package com.hotel.hotelroomreservation.utils.validations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public final class CalendarValidation {

    public static Calendar[] getSelectableDates(final Calendar arrivalCalendar, final List<Date> arrivalDates) {
        final List<Calendar> selectableDates = new ArrayList<>();
        final Date dateArrival = arrivalCalendar.getTime();

        Collections.sort(arrivalDates);

        for (final Date date : arrivalDates) {
            if (dateArrival.before(date)) {
                final Calendar cal = Calendar.getInstance();
                cal.setTime(dateArrival);

                while (cal.getTime().getTime() <= date.getTime()) {
                    final Calendar c = Calendar.getInstance();
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

        final Calendar[] calendarDates = new Calendar[selectableDates.size()];
        return selectableDates.toArray(calendarDates);
    }
}
