package net.xinhong.travel.api;

import net.xinhong.travel.model.BaseModel;
import net.xinhong.travel.model.BaseModel2;
import net.xinhong.travel.model.Data2;
import net.xinhong.travel.model.StationData;
import net.xinhong.travel.model.WeatherHourData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mac on 2017/1/4.
 */
public interface ApiService {

//    @GET("stationdata_surf/datafromcode")
//    Call<BaseModel<StationData>> addCodePara(@Query("code") String code);

    @FormUrlEncoded
    @POST("stationdata_surf/datafromcode")
    Call<BaseModel<StationData>> addCodePara(@Field("code") String code);

    @GET("stationdata_surf/seqdatafromcode")
    Call<BaseModel<Map<String, Float>>> addSeqPara(@Query("code") String code, @Query("elem") String elem);

    @GET("http://10.0.2.2:8080/index3.jsp")
    Call<BaseModel2<Data2>> getData();

    @GET("http://weather.xinhong.net/stationdata_cityfc/datafromlatlng?lat=31.45&lng=121.45&elem=TT,VIS,WS,CNL,RH,RN,WW,WD,CN,WDF")
    Call<BaseModel<WeatherHourData>> getWeatherHourData();

}

