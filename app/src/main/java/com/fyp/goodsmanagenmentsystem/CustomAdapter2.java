package com.fyp.goodsmanagenmentsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.MyViewHolder> {
    private ArrayList<dataclassmodule> dataSet;
    Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtitemname,txtitemrate,txtitemloaction;
        ImageView itemimg;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtitemname = (TextView) itemView.findViewById(R.id.description);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.itemimg = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (getPosition()==0)
                    {
                        Toast.makeText(v.getContext(), " On CLick one", Toast.LENGTH_SHORT).show();

                    } if (getPosition()==1)
                {
                    Toast.makeText(v.getContext(), " On CLick Two", Toast.LENGTH_SHORT).show();

                } if (getPosition()==2)
                {
                    Toast.makeText(v.getContext(), " On CLick Three", Toast.LENGTH_SHORT).show();

                } if (getPosition()==3)
                {
                    Toast.makeText(v.getContext(), " On CLick Fore", Toast.LENGTH_SHORT).show();

                }

                }
            });
        }
    }

    public CustomAdapter2(Context context, ArrayList<dataclassmodule> data) {
        this.dataSet = data;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homeitems, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView txtitemname = holder.txtitemname;
        TextView txtitemloaction = holder.txtitemloaction;

        TextView txtitemrate = holder.txtitemrate;

        // TextView textViewVersion = holder.textViewVersion;
        ImageView itemimg = holder.itemimg;

        txtitemname.setText(dataSet.get(listPosition).getQuestion());
        itemimg.setImageResource(dataSet.get(listPosition).getDummyimage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }}