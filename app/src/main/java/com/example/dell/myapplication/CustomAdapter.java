package com.example.dell.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<UserData> items;

    public CustomAdapter(Context context, ArrayList<UserData> items){
        super(context,R.layout.row_icon_label,items);
        this.context=context;
        this.items=items;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<UserData> getItems() {
        return items;
    }

    public void setItems(ArrayList<UserData> items) {
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.row_icon_label,null);
        UserData data=items.get(position);
        int serNo=position+1;
        TextView lblSrNo=(TextView)view.findViewById(R.id.lblSrNo);
        lblSrNo.setText(serNo+".");
        TextView show=(TextView)view.findViewById(R.id.txtshow);
        show.setText("Name::"+data.getName()+"\n"+"Age::"+data.getAge()+"\n"+"Email::"+data.getEmail()+"\n"+"Description::\n"+data.getDescription());
        return view;
    }
}
