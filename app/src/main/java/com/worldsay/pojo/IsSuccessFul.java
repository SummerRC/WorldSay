package com.worldsay.pojo;

/**
 * Created by wenlong on 2015/9/4.
 */
public class IsSuccessFul {
    private boolean is_successful;
    private int error;
    private String message;
    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean is_successful() {
        return is_successful;
    }

    public void setIs_successful(boolean is_successful) {
        this.is_successful = is_successful;
    }
}
