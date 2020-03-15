package com.heig.atmanager;

/**
 * Author : Stéphane Bottin
 * Date   : 12.03.2020
 *
 * Temp class to represent the goals
 */
public class Goal {

    String unit;
    int total;
    int current;

    public Goal(String unit, int total, int current) {
        this.unit = unit;
        this.total = total;
        this.current = current;
    }

    public int getPercentage() {
        return (int) (((float) current / total) * 100);
    }

    public String getUnit() {
        return unit;
    }

    public String getStringCurrent() {
        return (current < 10 ? "0" : "") + current;
    }

    public String getStringTotal() {
        return (total < 10 ? "0" : "") + total;
    }

}
