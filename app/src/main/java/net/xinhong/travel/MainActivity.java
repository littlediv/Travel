package net.xinhong.travel;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.xinhong.travel.activity.BaseActivity;
import net.xinhong.travel.api.ApiService;
import net.xinhong.travel.callback.BaseCallback;
import net.xinhong.travel.callback.BaseCallback2;
import net.xinhong.travel.convert.ResultJsonDeser;
import net.xinhong.travel.model.BaseModel;
import net.xinhong.travel.model.BaseModel2;
import net.xinhong.travel.model.Data2;
import net.xinhong.travel.model.StationData;
import net.xinhong.travel.utils.Constants;
import net.xinhong.travel.utils.Haha;
import net.xinhong.travel.utils.RetrofilUtils;

import java.util.Map;
import java.util.Set;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    TextView tvGET;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tvGET = (TextView) findViewById(R.id.tvGET);
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvGET.setOnClickListener(this);


        Haha a = new Haha();
        a.ha();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static final String TAG = "MainActivity";
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvGET:
//                requestApi();
//                requestSeq();
//                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
//
                Gson gson = new GsonBuilder()
                        .registerTypeHierarchyAdapter(BaseModel2.class, new ResultJsonDeser())
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();


                retrofit.create(ApiService.class).getData()
                        .enqueue(new BaseCallback2<BaseModel2<Data2>>() {
                            @Override
                            protected void onFail(String errorNoResult) {
                                Log.e(TAG, "onFail: " + errorNoResult );
                            }

                            @Override
                            protected void onSuccess(BaseModel2<Data2> response) {

                                Log.e(TAG, "onSuccess: " + response.code );
                            }
                        });


//                retrofit.create(ApiService.class).getData()
//                        .enqueue(new Callback<BaseModel2<Data2>>() {
//                            @Override
//                            public void onResponse(Call<BaseModel2<Data2>> call, retrofit2.Response<BaseModel2<Data2>> response) {
//                                Log.e(TAG, "onResponse: " + response);
//                                tvResult.setText("status：" + response.body().data.other);
////                                tvResult.setText("status：" + response.body().data.toString());
//                            }
//
//
//                            @Override
//                            public void onFailure(Call<BaseModel2<Data2>> call, Throwable t) {
//                                Log.e(TAG, "onFailure: " + t.toString());
//                                tvResult.setText("Error");
//                            }
//
//
//                        });

                break;
        }
    }



    private void requestSeq() {
        showWaitingMessage();
        RetrofilUtils.getInstance(this).create(ApiService.class)
                .addSeqPara("545", "PR")
                .enqueue(new BaseCallback<BaseModel<Map<String, Float>>>() {
                    @Override
                    protected void onFail(String errorNoResult) {
                        tvResult.setText(errorNoResult);
                    }

                    @Override
                    protected void onSuccess(BaseModel<Map<String, Float>> response) {
                        Set<Map.Entry<String, Float>> entries = response.data.entrySet();
                        for (Map.Entry<String, Float> entry : entries) {
                            tvResult.append(entry.getKey() + "  :  " + entry.getValue() + "\n");
                        }
                    }

                    @Override
                    protected void onAfter() {
                        dismissProgressDialog();
                    }
                });


    }

    private void requestApi() {
        showWaitingMessage();

        //地面实况
        RetrofilUtils.getInstance(this).create(ApiService.class)
                .addCodePara("54511")
                .enqueue(new BaseCallback<BaseModel<StationData>>() {
                    @Override
                    protected void onFail(String errorNoResult) {

                    }

                    @Override
                    protected void onSuccess(BaseModel<StationData> response) {
                        tvResult.setText("status：" + response.status_code + "\n" + response.data.WTHC);
                    }

                    @Override
                    protected void onAfter() {
                        dismissProgressDialog();
                    }
                });



    }
}
