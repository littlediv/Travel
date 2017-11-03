package net.xinhong.travel.callback;

import android.util.Log;

import net.xinhong.travel.model.BaseModel2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 2017/1/4.
 */
public abstract class BaseCallback2<T extends BaseModel2> implements Callback {
    @Override
    public void onResponse(Call call, Response response) {
        Log.e("----", "onResponse: " + response);
        int code = response.raw().code();


        if (code == 200) {
            /**
             * 这里我只实现了成功和失败的回调，还可以根据接口返回的状态信息实现相应的回调
             * */
            T t = (T) response.body();
            if (t.code == 200) {
                onSuccess(t);
            } else {
                /**
                 * 如果接口返回了msg，就不要使用自定义的msg
                 * */
//                onFail(ERR_MSG.ERROR_NO_RESULT);
                onFail("404");
            }
        } else if (code == 204) {
            onNoData(ERR_MSG.NO_DATA);
        } else if (code == 400) {
            onFail(ERR_MSG.ERR0E_400);
        }else if (code == 404) {
            onFail(ERR_MSG.ERR0E_400);
        } else if (code == 500) {
            onFail(ERR_MSG.ERROR_500);
        } else {
            onFail(ERR_MSG.ERROR_NO_RESULT);
        }

        onAfter();
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.e("----", "onFailure: ");
        onFail(t.getMessage());
        onAfter();


    }

    /**
     * 请求完的回调，可以在里面停止刷新控件，可以不实现
     */
    protected void onAfter() {
    }


    /**
     * 没有数据的回调，可以不实现
     */
    protected void onNoData(String noData) {
    }

    /**
     * 请求失败的回调
     */
    protected abstract void onFail(String errorNoResult);

    /**
     * 请求成功的回调
     */
    protected abstract void onSuccess(T response);





    /**
     * 自定义的错误信息
     */
    class ERR_MSG {
        private static final String NO_DATA = "暂无数据";
        private static final String ERR0E_400 = "请求失败";
        private static final String ERROR_500 = "服务器错误";
        private static final String ERROR_NO_RESULT = "未知错误";
    }
}
