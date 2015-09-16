package com.worldsay.widget;

/**
 * Created by yanchangsen on 15/9/4.
 */
public class UpdateTopBarTitleEvent {

    private String title;

    public UpdateTopBarTitleEvent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
