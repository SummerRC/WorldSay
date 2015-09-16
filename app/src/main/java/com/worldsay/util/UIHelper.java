package com.worldsay.util;

import android.content.Context;
import android.content.Intent;

public class UIHelper {
    public static void openActivity(Context ctx,Class<?> cls) {
        Intent intent = new Intent(ctx, cls); //
        ctx.startActivity(intent);

    }
}
