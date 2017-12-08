package com.andios.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andios.activity.R;
import com.andios.dao.HistoryHelper;
import com.andios.interfaces.OnItemClickListener;
import com.andios.interfaces.OnLongClickListener;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by YangZheWen on 2017/6/13.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private String[] text,string_time,string_details;
    private OnItemClickListener listener;
    private OnLongClickListener longClickListener;
    public RecyclerViewAdapter(Context context,String[] text,String[]string_time,String []string_details) {
        inflater=LayoutInflater.from(context);
        this.text=text;
        this.string_time=string_time;
        this.string_details=string_details;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=inflater.inflate(R.layout.cardview,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //holder.imageview.setImageBitmap();
        holder.textview.setText(text[position]);
        holder.time.setText(string_time[position]);
        holder.details.setText(string_details[position]);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return text.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private TextView textview,time,details;
        private ImageView imageview;
        public MyViewHolder(View itemView) {
            super(itemView);
            textview= (TextView) itemView.findViewById(R.id.project_name);
            time= (TextView) itemView.findViewById(R.id.time);
            details= (TextView) itemView.findViewById(R.id.details);
            imageview= (ImageView) itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener!=null){
                listener.onItemClick(v, (Integer) v.getTag());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (longClickListener!=null){
                longClickListener.onLongClick(v,(Integer) v.getTag());
            }
            return true;
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
    public void setOnLongClickListener(OnLongClickListener longClickListener){
        this.longClickListener=longClickListener;
    }
}
