package com.example.lib;

public class LunarCalendarAlgorithm {

    public Double jdFromDate(int dd, int mm, int yy){
        Double a, y, m, jd;
        a = Math.floor((14 - mm) / 12);
        y = yy+4800-a;
        m = mm+12*a-3;
        jd = dd + Math.floor((153*m+2)/5) + 365*y + Math.floor(y/4) - Math.floor(y/100) + Math.floor(y/400) - 32045;
        if (jd < 2299161) {
            jd = dd + Math.floor((153*m+2)/5) + 365*y + Math.floor(y/4) - 32083;
        }
        return jd;
    }
}
