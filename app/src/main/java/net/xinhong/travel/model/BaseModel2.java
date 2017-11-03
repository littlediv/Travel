package net.xinhong.travel.model;

import java.io.Serializable;

/**
 * Created by mac on 2017/1/12.
 */
public class BaseModel2<T> implements Serializable {
    public String msg;
    public int code;
    public T data;
}
