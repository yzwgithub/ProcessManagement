package com.andios.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andios.activity.R;
import com.andios.interfaces.OnItemClickListener;


/**
 * Created by ASUS on 2017/6/13.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private String[] text;
    private OnItemClickListener listener;
    public RecyclerViewAdapter(Context context,String[] text) {
        inflater=LayoutInflater.from(context);
        this.text=text;
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
    }

    @Override
    public int getItemCount() {
        return text.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textview;
        private ImageView imageview;
        public MyViewHolder(View itemView) {
            super(itemView);
            textview= (TextView) itemView.findViewById(R.id.card);
            imageview= (ImageView) itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener!=null){
                listener.onItemClick(v,getPosition());
            }
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
}
