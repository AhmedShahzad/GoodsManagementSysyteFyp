package com.fyp.goodsmanagenmentsystem;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
public class itemsdetailadapter extends ArrayAdapter<dataclassmodule> {
    private  Context context;
    SessionManager sessionManager;
    private ArrayList<dataclassmodule> thedata;
    ConstraintLayout discussion;
    public itemsdetailadapter(Context context1,ArrayList<dataclassmodule> thedata)
    {
        super(context1, R.layout.itemsdetail,thedata);
        this.context=context1;
        this.thedata=thedata;
    }
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.itemsdetail, null);
        sessionManager = new SessionManager(context);
        TextView description = view.findViewById(R.id.description);
        TextView title = view.findViewById(R.id.description);
        title.setText(thedata.get(position).getDeal_title());
        discussion = view.findViewById(R.id.layoutstart);
        TextView price = view.findViewById(R.id.actualprice);
        TextView tvfinalrate = view.findViewById(R.id.tvfinalrate);
        tvfinalrate.setText("PKR " + Double.valueOf(thedata.get(position).getDeal_Discount()) + "");
        TextView discountpercentage = view.findViewById(R.id.discountpercentage);
        discountpercentage.setText(thedata.get(position).getDiscount() + " OFF");
        description.setText(thedata.get(position).getDescription());
        price.setText("PKR " + thedata.get(position).getDeal_actualPrice());
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Log.d("tag", thedata.get(position).getDeal_actualPrice());
        ImageView imageView = view.findViewById(R.id.image);
        try {
            //textViewVersion.setText(dataSet.get(listPosition).getVersion());
            Glide.with(context)
                    .load(thedata.get(position).getDeal_Image())
                    .error(R.drawable.nobackimage)
                    .into(imageView);
        } catch (Exception e) {
            imageView.setImageResource(R.drawable.nobackimage);
            e.printStackTrace();
        }
        return view;
    }
}