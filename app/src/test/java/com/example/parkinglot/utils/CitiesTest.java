package com.example.parkinglot.utils;
import org.junit.Assert;
import org.junit.Test;

public class CitiesTest {

    @Test
    public void testToString() {
        Cities taipei = Cities.TAIPEI;
        Assert.assertEquals("Taipei", taipei.toString());

        Cities keelung = Cities.KEELUNG;
        Assert.assertEquals("Keelung", keelung.toString());

        Cities taoyuan = Cities.TAOYUAN;
        Assert.assertEquals("Taoyuan", taoyuan.toString());

        Cities hsinchu = Cities.HSINCHU;
        Assert.assertEquals("Hsinchu", hsinchu.toString());

        Cities hualienCounty = Cities.HUALIENCOUNTY;
        Assert.assertEquals("HualienCounty", hualienCounty.toString());

        Cities yilanCounty = Cities.YILANCOUNTY;
        Assert.assertEquals("YilanCounty", yilanCounty.toString());

        Cities miaoliCounty = Cities.MIAOLICOUNTY;
        Assert.assertEquals("MiaoliCounty", miaoliCounty.toString());

        Cities taichung = Cities.TAICHUNG;
        Assert.assertEquals("Taichung", taichung.toString());

        Cities nantouCounty = Cities.NANTOUCOUNTY;
        Assert.assertEquals("NantouCounty", nantouCounty.toString());

        Cities chiayi = Cities.CHIAYI;
        Assert.assertEquals("Chiayi", chiayi.toString());

        Cities chiayiCounty = Cities.CHIAYICOUNTY;
        Assert.assertEquals("ChiayiCounty", chiayiCounty.toString());

        Cities tainan = Cities.TAINAN;
        Assert.assertEquals("Tainan", tainan.toString());

        Cities kaohsiung = Cities.KAOHSIUNG;
        Assert.assertEquals("Kaohsiung", kaohsiung.toString());

        Cities pingtungCounty = Cities.PINGTUNGCOUNTY;
        Assert.assertEquals("PingtungCounty", pingtungCounty.toString());

        Cities taitungCounty = Cities.TAITUNGCOUNTY;
        Assert.assertEquals("TaitungCounty", taitungCounty.toString());

        Cities kinmenCounty = Cities.KINMENCOUNTY;
        Assert.assertEquals("KinmenCounty", kinmenCounty.toString());

        Cities lienchiangCounty = Cities.LIENHIANGCOUNTY;
        Assert.assertEquals("LienchiangCounty", lienchiangCounty.toString());
    }
}
