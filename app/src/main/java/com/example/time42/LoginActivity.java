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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    Button btnlogin;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText nameText;
    EditText passText;

    TextView failedText;

    private FirebaseAuth mAuth;

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
        /*btnlogin.setOnClickListener(view -> signIn(nameText.getText().toString(), passText.getText().toString()));
        mAuth = FirebaseAuth.getInstance();*/

        //Username: admin
        //password: admin
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            reload();
        }
    }*/

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
                                        editor.putString("full name", document.get("First").toString() + " " + document.get("Last").toString());
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

    /*private void signIn(String email, String password) {
        if(!validate()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    DocumentReference docRef = db.collection("User").get().

                    editor.putString("name", document.getId());
                    editor.putLong("permission", (Long) document.get("Permission"));
                    editor.putString("email", document.get("Email").toString());
                    editor.apply();

                    launchActivity();

                } else {
                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    //update UI(null)
                }

            }
        });

    }

    private void reload() {
        mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    //update UI(mAuth.getCurrentUser())
                    Toast.makeText(LoginActivity.this, "Reload successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to reload User", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validate() {
        boolean valid = false;
        if(nameText.getText().toString().isEmpty()) {
            failedText.setText("E-Mail or Password is missing");
            valid = false;
        } else {
            valid = true;
        }

        if(passText.getText().toString().isEmpty()) {
            failedText.setText("E-Mail or Password is missing");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }*/

}