package com.fyp.goodsmanagenmentsystem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class HotDealsAdapter extends RecyclerView.Adapter<HotDealsAdapter.MyViewHolder> {
    private ArrayList<dataclassmodule> dataSet;
    Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvdesc,tcfinalrate,tvlikes,discountpercent,tvdislikes,tvdiscount,likes,actualprice;
        ImageView imageViewIcon;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.tvdesc = (TextView) itemView.findViewById(R.id.description);
            this.actualprice = (TextView) itemView.findViewById(R.id.actualprice);
            this.tvdiscount = (TextView) itemView.findViewById(R.id.discountpercentage);
            this.discountpercent=itemView.findViewById(R.id.tvfinalrate);
            this.tvlikes=itemView.findViewById(R.id.likes);
            this.tcfinalrate=itemView.findViewById(R.id.tvfinalrate);
            this.tvdislikes=itemView.findViewById(R.id.dislikes);
            this.imageViewIcon =itemView.findViewById(R.id.image);
        }
    }
    public HotDealsAdapter(Context context, ArrayList<dataclassmodule> data) {
        this.dataSet = data;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotdealslayout, parent, false);
        //view.setOnClickListener(MainActivity.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView tvdesc = holder.tvdesc;
        TextView tcfinalrate = holder.tcfinalrate;
        TextView tvlikes = holder.tvlikes;
        TextView actualprice=holder.actualprice;
        TextView tvdislikes = holder.tvdislikes;
        TextView tvdiscount = holder.discountpercent;
        ImageView imageView = holder.imageViewIcon;
        tvdesc.setText(dataSet.get(listPosition).getDeal_title());
        tvlikes.setText(dataSet.get(listPosition).getLikes()+"");
        actualprice.setText(dataSet.get(listPosition).getDeal_actualPrice()+"");
        tvdislikes.setText(dataSet.get(listPosition).getDislikes()+"");
        tvdiscount.setText(dataSet.get(listPosition).getDiscount()+"");
        tcfinalrate.setText(dataSet.get(listPosition).getDeal_Discount()+"");
        if(!dataSet.get(listPosition).getDeal_Image().equals(""))
        {
            try {
                //textViewVersion.setText(dataSet.get(listPosition).getVersion());
                Glide.with(context)
                        .load(dataSet.get(listPosition).getDeal_Image())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(imageView);
            }
            catch (Exception e)
            {
                imageView.setImageResource(R.drawable.ic_launcher_background);
                e.printStackTrace();
            }
        }
        else
        {
            imageView.setImageResource(R.drawable.basket);
        }
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}