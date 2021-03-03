package com.example.time42;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    Button btnlogin;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText nameText;
    EditText passText;

    TextView failedText;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = findViewById(R.id.button);

        nameText = findViewById(R.id.nameTextView);
        passText = findViewById(R.id.passwordTextView);
        failedText = findViewById(R.id.failedText);

        sharedpreferences = getSharedPreferences("logPref", Context.MODE_PRIVATE);

        if(sharedpreferences.getString("name",null) != null)
        {
            launchActivity();
        }

        btnlogin.setOnClickListener(view -> getTest());

        //Username: admin
        //password: admin
    }

    private void launchActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getTest()
    {

        DocumentReference docRef = db.collection("User").document("admin");
        docRef
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        try {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                try {
                                    if (document.get("Password").equals(passText.getText().toString())) {
                                        failedText.setText("Logging in");
                                        failedText.setVisibility(View.VISIBLE);
                                        failedText.setTextColor(Color.GREEN);

                                        SharedPreferences.Editor editor = sharedpreferences.edit();

                                        editor.putString("name", document.getId());
                                        editor.putLong("permission", (Long) document.get("Permission"));
                                        editor.putString("email", document.get("Email").toString());
                                        editor.apply();

                                        launchActivity();
                                    } else {
                                        failedText.setText("Username or Password is wrong");
                                        failedText.setVisibility(View.VISIBLE);
                                        failedText.setTextColor(getColor(R.color.Error));
                                    }
                                } catch (NullPointerException e) {
                                    Log.i("LoginActivity", e + "");
                                }
                            } else {
                                failedText.setText("No User found");
                                failedText.setVisibility(View.VISIBLE);
                                failedText.setTextColor(getColor(R.color.Error));
                            }
                        }catch(NullPointerException e)
                        {
                            Log.i("LoginActivity", e + "");
                        }
                    } else {
                        failedText.setText("Database Error: " + task.getException());
                        failedText.setVisibility(View.VISIBLE);
                        failedText.setTextColor(getColor(R.color.Error));
                    }
        });
    }


}