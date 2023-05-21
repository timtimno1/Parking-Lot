package com.example.parkinglot.utils;

public enum Cities {

    TAIPEI("Taipei"),

    KEELUNG("Keelung"),

    TAOYUAN("Taoyuan"),

    HSINCHU("Hsinchu"),

    HUALIENCOUNTY("HualienCounty"),

    YILANCOUNTY("YilanCounty"),

    MIAOLICOUNTY("MiaoliCounty"),

    TAICHUNG("Taichung"),

    NANTOUCOUNTY("NantouCounty"),

    CHIAYI("Chiayi"),

    CHIAYICOUNTY("ChiayiCounty"),

    TAINAN("Tainan"),

    KAOHSIUNG("KaohsIung"),

    PINGTUNGCOUNTY("PingtungCounty"),

    TAITUNGCOUNTY("TaitungCounty"),

    KINMENCOUNTY("KinmenCounty"),

    LIENHIANGCOUNTY("LienchiangCounty");

    private final String cityString;

    Cities(String cityString) {
        this.cityString = cityString;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public String toString() {
        return cityString;
    }
}