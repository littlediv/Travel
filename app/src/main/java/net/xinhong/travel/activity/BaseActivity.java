package net.xinhong.travel.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.xinhong.travel.utils.Constants;

/**
 * Created by mac on 2016/11/8.
 */
public class BaseActivity extends AppCompatActivity {


    public ProgressDialog progressDialog;

    public void showProgressDialoag(String title, String msg) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        progressDialog = ProgressDialog.show(this, title, msg, true, false);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void showWaitingMessage() {
        showProgressDialoag("", "加载中...");
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
//                if (shouldShowRequestPermissionRationale(permission))
//                {
//
//                }
                boolean b = shouldShowRequestPermissionRationale(permission);
                Log.e("------- ", "hasPermission: " + b);
                return false;
            }
        }
        return true;

    }

    public void requestPermission(int code, String... permissions) {
//        ActivityCompat.requestPermission(code, permissions);
        ActivityCompat.requestPermissions(this, permissions, code);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constants.WRITE_EXTERNAL_STORAGE:
//                phoneGranted();
                break;
            case Constants.CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    phoneGranted();
                }

                break;
        }
    }

    public void sdGranted(){

    }


    public void phoneGranted() {

    }
}
