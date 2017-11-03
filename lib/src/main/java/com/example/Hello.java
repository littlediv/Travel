package com.example;

/**
 * Created by mac on 2017/4/18.
 */
public class Hello {

    public static void main(String[] args) {

//        System.out.println(getTDValue(44));
//        System.out.println(getTD(24));

        System.out.println(getTD(58.2,10.139984));
        System.out.println(getRH(10.139984, 0.09614171277173794));
    }


    public static double getTD(double tt) {
        return Math.exp((10.286 * tt - 2148.4909) / (tt - 35.85));
    }

    public static double get1(double tt) {
        return Math.exp(Math.log(611.2) + (17.62 * tt) / (243.12 + tt));
    }

    /**
     * 马格纳斯公式
     * 用于确定饱和水汽压与温度的关系
     * E0是水面和冰面的温度为0℃时的饱和水汽压，E0=6.11百帕或4.6毫米
     *
     * @param tt
     * @return
     */
    public static final float E0 = 6.11f;
    public static double getMagnus(double tt) {
        return E0 * Math.pow(10, (7.45 * tt / (235 + tt)));

    }


    /**
     * 计算露点温度
     * @param tt
     * @return
     */
    public static double getTDValue(double tt) {
        double magnus = get1(tt);
        return (243.12 * Math.log(magnus/611.12)) / (17.62-Math.log(magnus/611.12));
    }

    public static double getTD(double rh, double tt) {
        float a = 7.5f;
        float b = 237.3f;
        double temp = Math.log10(rh) - 2 + (a * tt) / (b + tt);
        return (1 - temp) / (temp * b - a);
    }

    public static double getRH(double tt, double td) {
        float a = 7.5f;
        float b = 237.3f;
        return Math.pow(10, (a+td)/(b+td)-(a*tt)/(b+tt)) * 100;
    }
 }
