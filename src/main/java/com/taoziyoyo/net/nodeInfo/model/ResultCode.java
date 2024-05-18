package com.taoziyoyo.net.nodeInfo.model;

public enum ResultCode implements IErrorCode{

    SUCCESS("","SUCCESS"),
    FAILED("","FAILED"),
    ERROR("E999", "未知错误，请联系管理员"),
    E003("E003", "通过认证API获取userCd发生错误"),
    E004("E004", "获取失败,系列长度不符合要求"),
    E056("E056", "优惠券发放API异常");


    private String returnMsgId;

    private String returnMsg;

    private ResultCode(String returnMsgID, String returnMSG) {
        this.returnMsgId = returnMsgID;
        this.returnMsg = returnMSG;
    }
    /**
     * @return
     */
    @Override
    public String getReturnMsgId() {
        return returnMsgId;
    }

    /**
     * @return
     */
    @Override
    public String getReturnMsg() {
        return returnMsg;
    }

 
}
