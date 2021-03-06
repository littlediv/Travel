package net.xinhong.travel.model;

import java.util.TreeMap;

/**
 * Created by mac on 2017/2/8.
 */
public class WeatherHourData {


    /**
     * status_code : 0
     * data : {"TT":{"3":3.9,"6":3.6,"9":3.3,"12":4.1,"15":3,"18":2.3,"21":0.5,"24":0.8,"27":2.1,"30":2.5,"33":2.1,"36":0,"39":-0.9,"42":-1.4,"45":-2.6,"48":-2.1,"54":2.9,"60":0.6,"66":-0.3,"72":-1.4,"84":1.4,"96":0.5,"108":1.5,"120":2.7,"132":3.7,"144":3.1,"156":5,"168":6.3},"CNL":{"3":68.9,"6":18.3,"9":32.4,"12":17.8,"15":49.6,"18":61.1,"21":71,"24":75,"27":53.5,"30":7.9,"33":1.6,"36":1.5,"39":0,"42":0,"45":0,"48":0,"54":0,"60":0,"66":0,"72":4.6,"84":1.1,"96":0,"108":0,"120":0,"132":5.6,"144":0.1,"156":0,"168":0.3},"WW":{"3":"小雨","6":"小雨","9":"小雨","12":"小雨","15":"阴","18":"阴","21":"阴","24":"阴","27":"多云","30":"晴","33":"晴","36":"晴","39":"晴","42":"晴","45":"晴","48":"晴","54":"晴","60":"晴","66":"晴","72":"晴","84":"晴","96":"晴","108":"晴","120":"晴","132":"晴","144":"晴","156":"晴","168":"晴"},"VIS":{},"RH":{"3":93.4,"6":93.3,"9":92.8,"12":89.8,"15":90.5,"18":91.2,"21":91.4,"24":91.2,"27":57.8,"30":54,"33":52.3,"36":53.4,"39":54.6,"42":54.6,"45":54.5,"48":54,"54":51,"60":56,"66":59.2,"72":58.3,"84":65.6,"96":69.1,"108":80,"120":78.9,"132":80.4,"144":89.9,"156":67.5,"168":80.7},"CN":{"3":100,"6":100,"9":100,"12":100,"15":98.3,"18":78.7,"21":89,"24":79.6,"27":52.8,"30":8.6,"33":0.9,"36":1.3,"39":0,"42":0,"45":0,"48":0,"54":0,"60":0,"66":0,"72":2.6,"84":0.9,"96":0,"108":0,"120":0,"132":3.7,"144":9.1,"156":12.1,"168":0},"WS":{"3":"3.0","6":"3.6","9":"4.4","12":"4.7","15":"5.0","18":"5.3","21":"5.5","24":"5.3","27":"6.2","30":"6.5","33":"5.8","36":"4.8","39":"5.0","42":"5.1","45":"5.0","48":"5.1","54":"6.6","60":"4.8","66":"4.6","72":"4.6","84":"3.7","96":"2.7","108":"3.1","120":"2.7","132":"2.6","144":"1.8","156":"2.9","168":"2.6"},"RN":{"3":0.3,"6":0.2,"9":0.2,"12":0.1,"15":0,"18":0,"21":0,"24":0,"27":0,"30":0,"33":0,"36":0,"39":0,"42":0,"45":0,"48":0,"54":0,"60":0,"66":0,"72":0,"84":0,"96":0,"108":0,"120":0,"132":0,"144":0,"156":0,"168":0},"WD":{"3":"8.5","6":"4.9","9":"350.0","12":"335.7","15":"326.4","18":"315.8","21":"309.4","24":"306.9","27":"315.9","30":"314.6","33":"319.5","36":"315.9","39":"307.7","42":"307.9","45":"308.7","48":"307.5","54":"309.9","60":"310.4","66":"310.6","72":"303.7","84":"307.7","96":"313.4","108":"353.7","120":"310.3","132":"27.4","144":"355.2","156":"121.3","168":"179.9"}}
     * lng : 121.45
     * status_msg : 查询成功
     * station_code : 58362
     * time : 20170208080000
     * station_cname : 上海
     * lat : 31.42
     */

//    public int status_code;
//    public double lng;
//    public String status_msg;
//    public String station_code;
//    public String time;
//    public String station_cname;
//    public double lat;
//
//    public DetailData data;
//
//    class DetailData
//    {
        public TreeMap<Integer, Double> TT;
        public TreeMap<Integer, Double> CML;
        public TreeMap<Integer, Double> VIS;
        public TreeMap<Integer, String> WW;
        public TreeMap<Integer, Double> RH;
        public TreeMap<Integer, Double> WS;
        public TreeMap<Integer, Double> WD;
        public TreeMap<Integer, Double> CN;
        public TreeMap<Integer, String> WDF;


//    }


}
