package com.unique.overhust.CommonUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.unique.overhust.MainActivity.ImageDetailsActivity;
import com.unique.overhust.R;

/**
 * Created by dsnc on 14-6-4.
 */
public class ListViewAdapter extends CursorAdapter {
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public ListViewAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

        mLayoutInflater = ((Activity) context).getLayoutInflater();
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mLayoutInflater.inflate(R.layout.fragment_search_results_item, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holder = getHolder(view);

        final SearchAccount mSearchAccount = SearchAccount.fromCursor(context, cursor);
        holder.mTextView.setText(mSearchAccount.getName());
        Picasso.with(ShareContext.getInstance()).load(mSearchAccount.getPicUrl()).into(holder.displayView);
        holder.displayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareContext.getInstance(),
                        ImageDetailsActivity.class);
                intent.putExtra("image_path", mSearchAccount.getPicUrl());
                mContext.startActivity(intent);
            }
        });

        holder.goThereView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.navigationThereView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private Holder getHolder(final View view) {
        Holder holder = (Holder) view.getTag();
        if (holder == null) {
            holder = new Holder(view);
            view.setTag(holder);
        }
        return holder;
    }

    private class Holder {
        public TextView mTextView;
        public ImageView displayView;
        public ImageView goThereView;
        public ImageView navigationThereView;

        public Holder(View view) {
            mTextView = (TextView) view.findViewById(R.id.name);
            displayView = (ImageView) view.findViewById(R.id.picture);
            goThereView = (ImageView) view.findViewById(R.id.loadstreetview);
            navigationThereView = (ImageView) view.findViewById(R.id.navigationto);
        }
    }
}
