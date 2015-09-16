package com.worldsay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.worldsay.R;


/**
 * Created by LXG on 2015/9/4.
 */
public class ItemMessage extends RelativeLayout  {

    private View view;
    private TextView nameKey;
    private TextView nameValue;

    public ItemMessage(Context context) {
        super(context);
        init(context);
    }

    public ItemMessage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemMessage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        view=View.inflate(context,R.layout.item_add_work_message,this);
        initDate();
    }

    private void initDate() {
        nameKey = (TextView) view.findViewById(R.id.nameKey);
        nameValue = (TextView) view.findViewById(R.id.nameValue);
    }

    public TextView getNameKey() {
        return nameKey;
    }

    public TextView getNameValue() {
        return nameValue;
    }
}
