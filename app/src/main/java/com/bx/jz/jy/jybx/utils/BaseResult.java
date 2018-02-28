package com.bx.jz.jy.jybx.utils;

public class BaseResult<T> {

  private boolean succ;

  private int code;

  private String msg;

  private T data;

  /**
   * 新架构异常字段
   */
  private String error;

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public boolean isSucc() {
    return succ;
  }

  public void setSucc(boolean succ) {
    this.succ = succ;
  }

  public int getCode() {
    return code;
  }

  public void setCode(Object code) {
    if (code instanceof String) {
      this.code = Integer.parseInt(((String) code));
    } else {
      this.code = (int) code;
    }
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
