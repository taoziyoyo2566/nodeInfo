package com.taoziyoyo.net.nodeInfo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Result extends HashMap<String,Object> {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * returnFlg
     */
    public static final String RETURN_FLG = "returnFlg";
    /**
     * returnMsgID
     */
    public static final String RETURN_MSGID = "returnMsgID";

    /**
     * returnMSG
     */
    public static final String RETURN_MSG = "returnMSG";

    /**
     * return_DATA
     */
    public static final String RETURN_DATA = "data";


    public static final String NODE_LIST = "nodeList";
    public static final String MSGID_S001 = "s001";

    /**
     * RETURN_SUCCESS_CODE 值为0
     */
    public static final int RETURN_SUCCESS_CODE = 0;

    /**
     * RETURN_FAILED_CODE 值为1
     */
    public static final int RETURN_FAILED_CODE = 1;

    /**
     * RETURN_FAILED_CODE 值为""
     */
    public static final String RETURN_BLANK = "";

    /**
     * MSGID 标识
     */
    public static final String SUCCESS = "";

    /**
     * MSGID 标识
     */
    public static final String MSGID_FAILED = "failed";

    /**
     * MSGID 标识
     */
    public static final String MSGID_ERROR = "error";
    /**
     * MSG 标识
     */
    public static final String MSG_FAILED = "提交失败";

    /**
     * MSG 标识
     */
    public static final String MSG_SUCCESS = "提交成功";
    // Constructors
    public Result() {}

    /**
     * 构造器
     *
     * @param code
     * @param msg
     * @param MsgID
     */
    public Result(int code, String msg, String MsgID) {
        put(RETURN_FLG, code);
        put(RETURN_MSGID, MsgID);
        put(RETURN_MSG, msg);
    }

    /**
     * success 相关返回信息
     *
     * @param msg
     * @return
     */
    public static Result success(Object msg) {
        Result r = new Result();
        r.put(RETURN_MSG, msg);
        return r;
    }
    /**
     * success
     *
     * @param map
     * @return
     */
    public static Result success(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    /**
     * 成功
     *
     * @return
     */
    public static Result success() {
        Result r = new Result();
        r.put(RETURN_FLG, RETURN_SUCCESS_CODE);
        r.put(RETURN_MSGID, RETURN_BLANK);
        r.put(RETURN_MSG, MSG_SUCCESS);
        return r;
    }

    /**
     * 成功
     *
     * @param msg
     * @param msgId
     * @return
     */
    public static Result success(String msg, String msgId) {
        Result r = new Result();
        r.put(RETURN_MSGID, msgId);
        r.put(RETURN_MSG, msg);
        return r;
    }

    /**
     *
     * @param resultCode
     * @return
     */
    public static Result success(ResultCode resultCode) {
        Result r = new Result();
        r.put(RETURN_FLG, RETURN_SUCCESS_CODE);
        r.put(RETURN_MSGID, resultCode.getReturnMsgId());
        r.put(RETURN_MSG, resultCode.getReturnMsg());
        return r;
    }

    /**
     * failed 相关返回方法，一般对应业务逻辑相关的失败返回
     *
     * @return
     */
    public static Result failed() {
        Result r = new Result();
        r.put(RETURN_FLG, RETURN_FAILED_CODE);
        r.put(RETURN_MSGID, RETURN_BLANK);
        r.put(RETURN_MSG, MSG_FAILED);
        return r;
    }

    /**
     *
     * @param msg
     * @return
     */
    public static Result failed(String msg) {
        Result r = new Result();
        r.put(RETURN_FLG, RETURN_FAILED_CODE);
        r.put(RETURN_MSGID, RETURN_BLANK);
        r.put(RETURN_MSG, msg);
        return r;
    }

    /**
     *
     * @param msg
     * @param msgId
     * @return
     */
    public static Result failed(String msg, String msgId) {
        Result r = new Result();
        r.put(RETURN_FLG, RETURN_FAILED_CODE);
        r.put(RETURN_MSGID, msgId);
        r.put(RETURN_MSG, msg);
        return r;
    }

    /**
     *
     * @param resultCode
     * @return
     */
    public static Result failed(ResultCode resultCode) {
        Result r = new Result();
        r.put(RETURN_FLG, RETURN_FAILED_CODE);
        r.put(RETURN_MSGID, resultCode.getReturnMsgId());
        r.put(RETURN_MSG, resultCode.getReturnMsg());
        return r;
    }

    /**
     *
     * @param resultCode
     * @return
     */
    public static Result failed(ResultCode resultCode, String msg) {
        Result r = new Result();
        r.put(RETURN_FLG, RETURN_FAILED_CODE);
        r.put(RETURN_MSGID, resultCode.getReturnMsgId());
        r.put(RETURN_MSG, msg);
        return r;
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
