package com.fyp.goodsmanagenmentsystem;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
public class nitificationadapter extends ArrayAdapter<dataclassmodule> {
    Context context;
    ArrayList<dataclassmodule> thedata;
    public nitificationadapter(Context context, ArrayList<dataclassmodule> thedata)
    {
        super(context, R.layout.notificationlayout,thedata);
        this.context=context;
        this.thedata=thedata;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.notificationlayout, null);
        TextView tvdescription = view.findViewById(R.id.description);
        TextView tvname  = view.findViewById(R.id.name);
        tvname.setText(thedata.get(position).getDescription());
        tvdescription.setText(thedata.get(position).getDeal_startdate());
              ImageView imageView=view.findViewById(R.id.image);
//        if(thedata.get(position).getDeal_Image().equals("")) {
//            try {
//                imageView.setImageResource(R.drawable.notification);
//              //  imageView.setImageBitmap(Utility.stringToBitmap(new DBconfig().baseuurlimage + thedata.get(position).getImage()));
//            } catch (Exception er) {
//                imageView.setImageResource(R.drawable.notification);
//            }
//        }
//        else
//        {
//            imageView.setImageResource(R.drawable.notification);
//        }

        return view;
    }
}