package net.xinhong.travel.model;

import java.io.Serializable;

/**
 * Created by mac on 2017/1/4.
 */
public class BaseModel<T> implements Serializable {

    public int status_code;
    public int delay;
    public String status_msg;
    public String time;

    public T data;


}
