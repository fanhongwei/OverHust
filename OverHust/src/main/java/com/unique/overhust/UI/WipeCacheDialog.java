package com.unique.overhust.UI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.unique.overhust.R;

/**
 * Created by fhw on 2/17/14.
 */
public class WipeCacheDialog extends Dialog {

    public WipeCacheDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.wipecache_dialog);

        findViewById(R.id.wipecache_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.wipecache_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wipeCache();
            }
        });
    }

    @Override
    public void show() {
        Window window = this.getWindow();
        window.setWindowAnimations(R.style.dialoganim);
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    public void wipeCache(){

    }
}
