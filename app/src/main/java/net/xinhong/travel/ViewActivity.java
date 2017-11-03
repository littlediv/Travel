package net.xinhong.travel;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.xinhong.travel.adapter.WeatherHourAdapter;
import net.xinhong.travel.api.ApiService;
import net.xinhong.travel.callback.BaseCallback;
import net.xinhong.travel.convert.ResultJsonDeser2;
import net.xinhong.travel.model.BaseModel;
import net.xinhong.travel.model.BaseModel2;
import net.xinhong.travel.model.WeatherHourData;
import net.xinhong.travel.utils.Constants;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac on 2017/2/6.
 */
public class ViewActivity extends Activity {

    private static final String TAG = "ViewActivity";
    @BindView(R.id.tv_all_time)
    TextView tvAllTime;
    @BindView(R.id.rv_weather_hour)
    RecyclerView rvWeatherHour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        setContentView(R.layout.activity_sunview);
//
//        SunView sunView = (SunView) findViewById(R.id.sunview);
//
//        sunView.setTime("7:24", "17:39");

        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewActivity.this, LinearLayoutManager.VERTICAL, false);
        rvWeatherHour.setLayoutManager(linearLayoutManager);


        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(BaseModel2.class, new ResultJsonDeser2())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofit.create(ApiService.class).getWeatherHourData()
                .enqueue(new BaseCallback<BaseModel<WeatherHourData>>() {
                    @Override
                    protected void onFail(String errorNoResult) {

                    }

                    @Override
                    protected void onSuccess(BaseModel<WeatherHourData> response) {
                        WeatherHourData data = response.data;
                        Log.e(TAG, "onSuccess: ");

                        DateTime dateTime = DateTime.now();

                        WeatherHourAdapter adapter = new WeatherHourAdapter(ViewActivity.this, data, dateTime.getHourOfDay());


                        rvWeatherHour.setAdapter(adapter);
                    }


                });


    }
}
