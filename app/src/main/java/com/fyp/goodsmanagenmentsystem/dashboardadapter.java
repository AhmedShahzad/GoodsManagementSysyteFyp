package com.fyp.goodsmanagenmentsystem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
public class dashboardadapter extends ArrayAdapter<dataclassmodule> {
    ArrayAdapter<dataclassmodule> gridadapter;
    ArrayAdapter<dataclassmodule> listadapter;
    Context context;
    public dashboardadapter(@NonNull Context context,ArrayList<dataclassmodule> thedata) {
        super(context, R.layout.dashboard_layout,thedata);
    }
    public void setdata(ArrayAdapter<dataclassmodule> gridadapter,ArrayAdapter<dataclassmodule> listadapter)
    {
        this.gridadapter=gridadapter;
        this.listadapter=listadapter;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.dashboard_layout,null);
        GridView gridView=view.findViewById(R.id.gridview);
        ListView listView=view.findViewById(R.id.listview);

        return view;
    }
}
