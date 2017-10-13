package com.example.dell.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText txtName,txtAge,txtEmail,txtdescription;
    Button btnSave,btnClear,btnShowList;
    private static final int REQUEST_EXTERNAL_STORAGE=1;
    private static String[] PERMISSION_STORAGE=
            {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    boolean result=false;
    int L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Verify Storage Permission
        verifyStoragePermission(this);
        //Prepare Database
        DBService.createDBAndTable();
        //Matching XML with activity java
        txtName=(EditText)findViewById(R.id.txtName);
        txtAge=(EditText)findViewById(R.id.txtAge);
        txtEmail=(EditText)findViewById(R.id.txtEmail);
        txtdescription=(EditText)findViewById(R.id.description);
        btnSave=(Button) findViewById(R.id.btnSave);
        btnClear=(Button)findViewById(R.id.btnClear);
        btnShowList=(Button)findViewById(R.id.btnShowList);

        //Add Listeners to buttons
        btnSave.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnShowList.setOnClickListener(this);
        //checking maxline
        checkMaxLines();

        }

    private void checkMaxLines() {
        txtdescription.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence q, int s, int c, int a) {
            }

            public void onTextChanged(CharSequence q, int s, int b, int c) {
                L = txtdescription.getLineCount();
                if(L > 3){
                    txtdescription.getText().delete(txtdescription.getSelectionEnd() - 1,txtdescription.getSelectionStart());
                    txtdescription.setImeOptions(EditorInfo.IME_ACTION_NEXT);

                }
            }
        });
    }


    public static void verifyStoragePermission(Activity act)
    {
        int permission= ActivityCompat.checkSelfPermission(act, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(act,PERMISSION_STORAGE,REQUEST_EXTERNAL_STORAGE);
        }
    }



    @Override
    public void onClick(View view) {
        if(view.getId()==btnSave.getId()){
            if(isValid()){
                saveUserData();
            }
        }
        if(view.getId()==btnClear.getId()){
            clearfield();
        }
        if(view.getId()==btnShowList.getId()){
            Intent intent=new Intent(MainActivity.this,ShowListActivity.class);
            startActivity(intent);
        }
    }

    private void saveUserData() {
        UserData data=new UserData();
        data.setName(txtName.getText().toString().trim());
        data.setAge(Integer.parseInt(txtAge.getText().toString().trim()));
        data.setEmail(txtEmail.getText().toString().trim());
        data.setDescription(txtdescription.getText().toString().trim());
        result=UserService.saveUser(data);
        if(result){
            Toast.makeText(getApplicationContext(),"User was inserted !!!!",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid() {
        String name=txtName.getText().toString().trim();
        String age=txtAge.getText().toString().trim();
        String email=txtEmail.getText().toString().trim();
        String description=txtdescription.getText().toString().trim();
        if(name.equals("")){
            txtName.setError("Enter name.");
            return false;
        }
        if(!name.equals("")){
            txtName.setError(null);
        }
        if(age.equals("")){
            txtAge.setError("Enter age.");
            return false;
        }

        String agepattern = "((?!00)[0-9]{2})";
        if(!age.matches(agepattern)){
            txtAge.setError("Invalid Age.\n Enter 2 digits!!");
        }
        if(age.matches(agepattern)){
            txtAge.setError(null);
        }

        if(email.equals("") ){
            txtEmail.setError("Enter email.");
            return false;
        }
        if (!isValidEmail(email)) {
            txtEmail.setError("Invalid Email.");
            return false;
        }
        if(isValidEmail(email)){
            txtEmail.setError(null);
        }
        if(description.equals("")){
            txtdescription.setError("Enter description.");
            return false;
        }
        if(!description.equals("")){
            txtdescription.setError(null);
        }
        return true;
    }

    /*public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }*/

    // validating email
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void clearfield() {
        txtName.setText("");
        txtAge.setText("");
        txtEmail.setText("");
        txtdescription.setText("");
    }
}

