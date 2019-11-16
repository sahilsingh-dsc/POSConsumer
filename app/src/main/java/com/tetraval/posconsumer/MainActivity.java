package com.tetraval.posconsumer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DatabaseReference posRef;
    EditText txtBal, txtUserID;
    Button btnAddBal;
    String amount = "0";
    TextView txtCurrentBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBal = findViewById(R.id.txtBal);
        txtUserID = findViewById(R.id.txtUserID);
        btnAddBal = findViewById(R.id.btnAddBal);
        txtCurrentBal = findViewById(R.id.txtCurrentBal);

        posRef = FirebaseDatabase.getInstance().getReference("request");
        posRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                amount = dataSnapshot.child("amount").getValue().toString();
                txtCurrentBal.setText("₹"+amount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAddBal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String balance_to_add = txtBal.getText().toString();
                if (TextUtils.isEmpty(balance_to_add)){
                    Toast.makeText(MainActivity.this, "Please enter balance amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                addBalance(balance_to_add);
            }
        });
    }

    private void addBalance(String balance_to_add){
        int current_balance = Integer.parseInt(amount);
        int add_balance = Integer.parseInt(balance_to_add);
        int updated_balance = current_balance+add_balance;
        posRef = FirebaseDatabase.getInstance().getReference("request");
        posRef.child("amount").setValue(updated_balance);
        posRef = FirebaseDatabase.getInstance().getReference("user_transactions");
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("amount", balance_to_add);
        posRef.push().setValue(hashMap);
        Toast.makeText(MainActivity.this, "Balance Added!", Toast.LENGTH_SHORT).show();
        txtBal.setText("");
    }

}
