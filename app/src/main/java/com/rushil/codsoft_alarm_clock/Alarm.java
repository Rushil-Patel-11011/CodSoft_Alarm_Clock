package com.rushil.codsoft_alarm_clock;

public class Alarm {
    private int hour;
    private int minute;
    private boolean enabled;

    public Alarm(int hour, int minute, boolean enabled) {
        this.hour = hour;
        this.minute = minute;
        this.enabled = enabled;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
