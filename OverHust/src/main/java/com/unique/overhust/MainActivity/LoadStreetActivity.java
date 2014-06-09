package com.unique.overhust.MainActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tencent.street.StreetThumbListener;
import com.tencent.street.StreetViewListener;
import com.tencent.street.StreetViewShow;
import com.tencent.street.map.basemap.GeoPoint;
import com.tencent.street.overlay.ItemizedOverlay;
import com.unique.overhust.CommonUtils.AddPois;
import com.unique.overhust.MapUtils.StreetOverlay;
import com.unique.overhust.MapUtils.StreetPoiData;
import com.unique.overhust.R;
import com.unique.overhust.UI.LoadStreetDialog;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class LoadStreetActivity extends SwipeBackActivity implements StreetViewListener {
    //控制滑动返回的边缘大小
    private static final int EDGE_SIZE = 100;
    private SwipeBackLayout mSwipeBackLayout;

    private ViewGroup streetView;
    private ImageView streetImageview, mapPreView;
    private LoadStreetDialog mDialog;
    private GeoPoint currentCenter;

    private View mStreetview;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_street);

        findViews();
        showDialog();

        //左侧滑动返回
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEdgeSize(EDGE_SIZE);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                streetImageview.setImageBitmap((Bitmap) msg.obj);
            }
        };
        String key = "4fb2821bde027e675565c75b32245ad5";
        currentCenter = new GeoPoint((int) bundle.getDouble("latitude"), (int) bundle.getDouble("longitude"));
        StreetViewShow.getInstance().showStreetView(this, currentCenter, 100, this, -170, 0, key);
    }

    public void findViews() {
        streetView = (FrameLayout) findViewById(R.id.activity_loadstreet);
        streetImageview = (ImageView) findViewById(R.id.streetview_loadstreet);
        mapPreView = (ImageView) findViewById(R.id.map_pre_loadstreet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.load_street, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //加载progressDialog
    public LoadStreetDialog showDialog() {
        mDialog = new LoadStreetDialog(this, R.style.LoadStreetDialog);
        mDialog.show();

        return mDialog;
    }

    //销毁progressDialog
    public void dismissDialog() {
        mDialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        StreetViewShow.getInstance().requestStreetThumb(
                "10041002111120153536407",// "10011505120412110900000",
                new StreetThumbListener() {

                    @Override
                    public void onGetThumbFail() {

                    }

                    @Override
                    public void onGetThumb(Bitmap bitmap) {
                        Message msg = new Message();
                        msg.obj = bitmap;
                        mHandler.sendMessage(msg);
                    }
                }
        );
    }

    StreetOverlay overlay;

    @Override
    public ItemizedOverlay getOverlay() {
        if (overlay == null) {
            ArrayList<StreetPoiData> pois = new ArrayList<StreetPoiData>();
            long a = System.currentTimeMillis();
            AddPois mAddPois = new AddPois(this);
            pois = mAddPois.getPois();
            long b = System.currentTimeMillis() - a;
            System.out.println("tim111" + b);
            overlay = new StreetOverlay(pois);
            overlay.populate();
        }
        return overlay;
    }

    @Override
    public void onAuthFail() {

    }

    @Override
    public void onViewReturn(final View view) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStreetview = view;
                streetView.addView(mStreetview);
            }
        });
    }

    @Override
    public void onNetError() {

    }

    @Override
    public void onDataError() {

    }

    @Override
    public void onLoaded() {
        dismissDialog();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStreetview.setVisibility(View.VISIBLE);
            }
        });
    }
}
