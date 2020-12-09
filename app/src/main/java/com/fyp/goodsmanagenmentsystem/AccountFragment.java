package com.fyp.goodsmanagenmentsystem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.android.installreferrer.BuildConfig;

import java.util.ArrayList;
public class AccountFragment extends Fragment {
    SessionManager sessionManager;
    ListView listviewprofileitems;
    ArrayList<com.fyp.goodsmanagenmentsystem.dataclassmodule> thedata;
    LinearLayout linearlayout1,layoutmydeals,linearlayout3,linearlayout2,linearlayout4;
    View viewmyprofile,viewmydeal;
    TextView name1;
    ArrayAdapter<dataclassmodule> adapter;
    public static AccountFragment newInstance() {
        return new AccountFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.account_fragment, container, false);
        name1=root.findViewById(R.id.name1);
        linearlayout4=root.findViewById(R.id.linearlayout4);
        viewmyprofile=root.findViewById(R.id.viewmyprofile);
        linearlayout1=root.findViewById(R.id.linearlayout1);
        linearlayout2=root.findViewById(R.id.linearlayout2);
        linearlayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        sessionManager=new SessionManager(getActivity());
        Toolbar toolbar=(Toolbar)root.findViewById(R.id.top);
        TextView txttitle=root.findViewById(R.id.txttitle);
        TextView txtback=root.findViewById(R.id.txtback);
        TextView txtlogin=root.findViewById(R.id.txtlogin);
        txtback.setVisibility(View.GONE);
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        txttitle.setText("My Account");
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

    return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}