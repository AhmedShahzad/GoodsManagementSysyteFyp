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
public class profileitemadapter extends ArrayAdapter<dataclassmodule> {
    Context context;
    SharedPreferences pref;
    private static final String PREF_NAME = "login_preference";
    int PRIVATE_MODE = 0;
    ArrayList<dataclassmodule> thedata;
    ProgressDialog progressDialog;
    public profileitemadapter(Context context, ArrayList<dataclassmodule> thedata)
    {
        super(context, R.layout.profilelayout,thedata);
        this.context=context;
        this.thedata=thedata;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.profilelayout, null);
        TextView tvprofile  = view.findViewById(R.id.tvprofile);
        tvprofile.setText(thedata.get(position).getName());
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
//            imageView.setImageResource(R.drawable.userlogoicon);
        }
        return view;
    }
}