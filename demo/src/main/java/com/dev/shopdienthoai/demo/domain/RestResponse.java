package com.dev.shopdienthoai.demo.domain;

public class RestResponse<T> {
    private String errorMessage;
    private int status;
    private Object message;
    private T data;
    public RestResponse() {}

    public RestResponse(String errorMessage, int status, Object message, T data) {
        this.errorMessage = errorMessage;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
