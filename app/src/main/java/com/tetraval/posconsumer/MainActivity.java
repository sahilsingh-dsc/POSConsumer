package com.tetraval.posconsumer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DatabaseReference posRef;
    EditText txtBal, txtUserID;
    Button btnAddBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBal = findViewById(R.id.txtBal);
        txtUserID = findViewById(R.id.txtUserID);
        btnAddBal = findViewById(R.id.btnAddBal);

        btnAddBal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String balance = txtBal.getText().toString();
                String userid = txtUserID.getText().toString();
                if (TextUtils.isEmpty(balance)){
                    Toast.makeText(MainActivity.this, "Please enter balance amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(userid)){
                    Toast.makeText(MainActivity.this, "Please enter userid", Toast.LENGTH_SHORT).show();
                    return;
                }
                posRef = FirebaseDatabase.getInstance().getReference("user_Data");
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("balance", balance);
                posRef.child(userid).setValue(hashMap);
                Toast.makeText(MainActivity.this, "Balance Added!", Toast.LENGTH_SHORT).show();
                txtBal.setText("");
                txtUserID.setText("");
            }
        });

    }
}
