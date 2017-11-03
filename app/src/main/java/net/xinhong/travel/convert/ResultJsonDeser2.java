package net.xinhong.travel.convert;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.xinhong.travel.model.BaseModel2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by mac on 2017/1/12.
 */
public class ResultJsonDeser2 implements JsonDeserializer<BaseModel2<?>> {

    @Override
    public BaseModel2<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        BaseModel2 response = new BaseModel2();

        Log.e("---", "deserialize: " + json.toString() );
        if (json.isJsonObject()){
            JsonObject jsonObject = json.getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            response.code= code;
            response.msg = jsonObject.get("msg").getAsString();
            if (code != 200){
                Log.e("++++++++", "deserialize: " + response.data );
                return response;
            }
            Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
            response.data=context.deserialize(jsonObject.get("data"), itemType);
            Log.e("===========", "deserialize: " + response.data );
            return response;
        }
        return response;
    }
}