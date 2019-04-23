package com.cncounter.common.vo;

import java.util.*;

/**
 * MVC层与前端的AJAX交互, 如果有不同的消息类型需求,可以继承此类,也可以重新编写,但应尽可能与此类在同一个包内.
 */
public class JSONMessage<T> {
    /**
     * 成功状态，值为1
     */
    public static final int STATUS_SUCCESS = 1;
    /**
     * 失败状态, 代表有错误发生, 值为0
     */
    public static final int STATUS_FAILURE = 0;
    /**
     * 是否成功,默认0为不成功,1为成功
     */
    private int status = STATUS_FAILURE;
    /**
     * 返回数据、类型可以是 Model, List<Model>, 以及其他类型
     */
    private T data = null;
    /**
     * 元数据、额外信息、可以存储任意类型的映射 <br/>
     */
    private Map<String, Object> meta = null;

    /**
     * 为了兼容表格的记录总数, 需要用户手动设置
     */
    private int total = 0;
    /**
     * 提示信息,如果需要则提供
     */
    private String message = "";

    /**
     * 新创建一个空消息对象
     */
    public static <T> JSONMessage<T> success() {
        JSONMessage<T> jsonMessage = new JSONMessage<T>();
        jsonMessage.asSuccess();
        return jsonMessage;
    }

    public static <T> JSONMessage<T> failure() {
        JSONMessage<T> jsonMessage = new JSONMessage<T>();
        jsonMessage.asFailure();
        return jsonMessage;
    }

    public int getTotal() {
        return total;
    }

    public JSONMessage<T> setTotal(int total) {
        this.total = total;
        return this;
    }

    public T getData() {
        return data;
    }

    public JSONMessage<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public JSONMessage<T> setMeta(Map<String, Object> meta) {
        this.meta = meta;
        return this;
    }

    /**
     * 添加 meta信息,
     */
    public JSONMessage<T> addMeta(String key, Object value) {
        if (null == this.meta) {
            // 非线程安全, 不考虑使用多线程操作
            this.meta = new HashMap<String, Object>();
        }
        //
        this.meta.put(key, value);
        return this;
    }

    public int getStatus() {
        return status;
    }

    /**
     * 设置执行成功
     */
    public JSONMessage<T> asSuccess() {
        return this.setStatus(STATUS_SUCCESS);
    }

    public JSONMessage<T> asFailure() {
        return this.setStatus(STATUS_FAILURE);
    }

    public JSONMessage<T> setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
