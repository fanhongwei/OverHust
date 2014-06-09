package com.unique.overhust.MainActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devspark.appmsg.AppMsg;
import com.tencent.street.StreetViewListener;
import com.tencent.street.StreetViewShow;
import com.tencent.street.a;
import com.tencent.street.map.basemap.GeoPoint;
import com.tencent.street.overlay.ItemizedOverlay;
import com.unique.overhust.CommonUtils.ShareContext;
import com.unique.overhust.MapUtils.OverHustLocation;
import com.unique.overhust.MapUtils.StreetPoiData;
import com.unique.overhust.NavigationUtils.NaviInfo;
import com.unique.overhust.NavigationUtils.NavigationPoint;
import com.unique.overhust.NavigationUtils.NavigationTools;
import com.unique.overhust.NavigationUtils.StreetNavigationOverlay;
import com.unique.overhust.R;
import com.unique.overhust.UI.LoadStreetDialog;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class NavigationActivity extends SwipeBackActivity {
    private ViewGroup mView;
    private TextView mTextView;
    private ImageView mImage;


    private NavigationTools navigationTools = null;
    private NaviInfo naviInfo = null;
    private StreetViewListener mListener;
    private View mStreetView = null;
    private GeoPoint center;
    private String key = "50ed4b26a236ee30947caf2b52a2a8f9";
    private StreetNavigationOverlay overlay;
    private NavigationPoint mStartPoint;
    private NavigationPoint mEndPoint;

    private LoadStreetDialog mDialog;

    //控制滑动返回的边缘大小
    private static final int EDGE_SIZE = 100;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();

        //左侧滑动返回
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.setEdgeSize(EDGE_SIZE);

        mView = (LinearLayout) findViewById(R.id.activity_streetlayout);
        mTextView = (TextView) findViewById(R.id.activity_textView1);
        mImage = (ImageView) findViewById(R.id.activity_image);

        OverHustLocation mLocation = new OverHustLocation(ShareContext.getInstance());
        mLocation.getLocation();
        //System.out.println("location"+mLocation.getiLatitu()+mLocation.getiLongti());
        mStartPoint = new NavigationPoint(mLocation.getiLatitu(), mLocation.getiLongti());
        mEndPoint = new NavigationPoint(bundle.getDouble("latitude"), bundle.getDouble("longitude"));

        initStreatView(mStartPoint, mEndPoint);
    }

    private void initStreatView(NavigationPoint startPoint, NavigationPoint endPoint) {
        // TODO Auto-generated method stub
        navigationTools = new NavigationTools(startPoint, endPoint);
        naviInfo = new NaviInfo(this, navigationTools);
        addPoins(navigationTools.getPoints());
        mListener = new StreetViewListener() {
            @Override
            public void onViewReturn(final View arg0) {
                // TODO Auto-generated method stub
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStreetView = arg0;
                        mView.addView(mStreetView);
                    }
                });
            }

            @Override
            public void onNetError() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onLoaded() {
                // TODO Auto-generated method stub
                dismissDialog();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStreetView.setVisibility(View.VISIBLE);
                        //mRelativeLayout.setVisibility(View.GONE);
                        final a status = StreetViewShow.getInstance().getStreetStatus();
                        NavigationPoint b = new NavigationPoint((double) status.c / 1E6, (double) status.d / 1E6);
                        naviInfo.show(b);
                    }
                });
            }

            @Override
            public void onDataError() {
                // TODO Auto-generated method stub
                //Toast.makeText(mContext, "errorData", Toast.LENGTH_SHORT).show();
                System.out.println("errorData");
            }

            @Override
            public void onAuthFail() {
                // TODO Auto-generated method stub

            }

            @Override
            public ItemizedOverlay getOverlay() {
                // TODO Auto-generated method stub
                return overlay;
            }
        };
        center = new GeoPoint((int) (navigationTools.getPoints().get(0).getiLatitu() * 1E6), (int) (navigationTools.getPoints().get(0).getiLongti() * 1E6));
        //center=new GeoPoint((int)(30.508809 * 1E6),(int)(114.43087 * 1E6));
        StreetViewShow.getInstance().showStreetView(ShareContext.getInstance(), center, 100,
                mListener, 0, 0, key);
        showDialog();
    }

    private void addPoins(ArrayList<NavigationPoint> points) {
        // TODO Auto-generated method stub
        overlay = null;
        ArrayList<StreetPoiData> pois = new ArrayList<StreetPoiData>();

        pois.add(new StreetPoiData((int) (points.get(0).getiLatitu() * 1E6),
                (int) (points.get(0).getiLongti() * 1E6),
                getBmhaha(R.drawable.navi_start),
                getBmhaha(R.drawable.navi_start), 0));

        for (int i = 1; i < points.size() - 1; i++) {
            NavigationPoint point = points.get(i);
            switch (point.getAngle()) {
                case -1:
                    pois.add(new StreetPoiData((int) (point.getiLatitu() * 1E6),
                            (int) (point.getiLongti() * 1E6),
                            getBm(R.drawable.left),
                            getBm(R.drawable.left), 0));
                    break;
                case 1:
                    pois.add(new StreetPoiData((int) (point.getiLatitu() * 1E6),
                            (int) (point.getiLongti() * 1E6),
                            getBm(R.drawable.right),
                            getBm(R.drawable.right), 0));
                    break;
                default:
                    System.out.println("angle" + point.getAngle());
                    pois.add(new StreetPoiData((int) (point.getiLatitu() * 1E6),
                            (int) (point.getiLongti() * 1E6),
                            getBm(R.drawable.up),
                            getBm(R.drawable.up), 0));
                    break;
            }
        }
        int i = points.size() - 1;
        pois.add(new StreetPoiData((int) (points.get(i).getiLatitu() * 1E6),
                (int) (points.get(i).getiLongti() * 1E6),
                getBmhaha(R.drawable.nava_end),
                getBmhaha(R.drawable.nava_end), 0));
        overlay = new StreetNavigationOverlay(pois);
        overlay.populate();
    }

    private Bitmap getBm(int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inScaled = false;
        options.inSampleSize = 4;

        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

    private Bitmap getBmhaha(int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inScaled = false;

        return BitmapFactory.decodeResource(getResources(), resId, options);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
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
}
