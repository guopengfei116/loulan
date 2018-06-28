package com.loulan.vo;

public class HttpResult {
    private Boolean success;
    private Integer status;
    private String message;

    public static HttpResult ok(String message) {
        return new HttpResult(true, 200, message);
    }

    public static HttpResult fail(String message) {
        return new HttpResult(false, 500, message);
    }

    public HttpResult() {
    }

    public HttpResult(Boolean success, Integer status, String message) {
        this.success = success;
        this.status = status;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
