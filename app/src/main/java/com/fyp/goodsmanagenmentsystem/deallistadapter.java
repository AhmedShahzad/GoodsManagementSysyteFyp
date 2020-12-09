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

public class deallistadapter extends ArrayAdapter<dataclassmodule> {
    Context context;
    SharedPreferences pref;
//    SessionManager sessionManager;
    private static final String PREF_NAME = "login_preference";
    int PRIVATE_MODE = 0;
    ArrayList<dataclassmodule> thedata;
    ProgressDialog progressDialog;
    public deallistadapter(Context context, ArrayList<dataclassmodule> thedata)
    {
        super(context, R.layout.homeitems,thedata);
        this.context=context;
        this.thedata=thedata;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.itemsdetail, null);
        TextView tvname = view.findViewById(R.id.description);
        tvname.setText(thedata.get(position).getDescription());
        TextView enddate = view.findViewById(R.id.enddate);
        enddate.setText(thedata.get(position).getDeal_enddate());
        TextView actualprice = view.findViewById(R.id.actualprice);
        actualprice.setText(thedata.get(position).getDeal_actualPrice());
        TextView tvfinalrate = view.findViewById(R.id.tvfinalrate);
        tvfinalrate.setText(thedata.get(position).getDeal_Discount());
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
              ImageView imageView=view.findViewById(R.id.image);
        try {
          //  imageView.setImageBitmap(Utility.stringToBitmap(thedata.get(position).getImage()));
            imageView.setImageResource(thedata.get(position).getDummyimage());
        }
        catch (Exception er){
            imageView.setImageResource(R.mipmap.ic_launcher_round);
        }

        return view;
    }
}