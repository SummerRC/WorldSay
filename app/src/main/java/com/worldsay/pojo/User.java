package com.worldsay.pojo;

/**
 * Created by gekson on 2015/8/26.
 */
public class User {

    /**
     * duration : 6000000
     * token : eyJhbGciOiJIUzI1NiIsImV4cCI6MTQ0NzI5MTQ4MywiaWF0IjoxNDQxMjkxNDgzfQ.eyJpZCI6MjV9.oznWjKCNk0gi9mACztllkQCTRIGJg3yrtYamAN0eDlo
     * uid : 25
     * username : zo
     */

    private int duration;
    private String token;
    private int uid;
    private String username;

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getDuration() {
        return duration;
    }

    public String getToken() {
        return token;
    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }
}
