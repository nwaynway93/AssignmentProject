package com.example.dell.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {
    ListView showList;
    ArrayList<UserData> arrayList;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("UserRegisterList");
        actionBar.show();

        showList=(ListView)findViewById(R.id.showList);

        //Get all user record
        retrieveUser();
    }

    private void retrieveUser() {

        arrayList=UserService.retrieveUser();

        showList.setAdapter(new CustomAdapter(this,arrayList));
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return  super.onOptionsItemSelected(item);
    }

}
