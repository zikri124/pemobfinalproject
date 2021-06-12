package com.example.pemobfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class signIn extends AppCompatActivity {
    ImageButton SignBack;
    EditText SignEmail,SignPassword;
    TextView SignRegister,ForgotPassword;
    Button SignIns;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SignBack = findViewById(R.id.backBtn);
        SignEmail = findViewById(R.id.editTextEmail);
        SignPassword =findViewById(R.id.editTextPassword);
        SignRegister = findViewById(R.id.RegisterTXT);
        SignIns = findViewById(R.id.signinBtn);
        ForgotPassword = findViewById(R.id.textForgotPassword);
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),mainMenu.class));
            finish();
        }

        SignBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signIn.this, landingPage.class));
            }
        });

        SignRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signIn.this, register.class));
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Did You Want To Reset Your Password?");
                passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    // Close the dialog
                    }
                });

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(signIn.this,"Please Check Your Email For Reset Link", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(signIn.this,"Error Has Occured Please Try Again Later", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.create().show();
            }
        });

        SignIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = SignEmail.getText().toString().trim();
                String Password = SignPassword.getText().toString().trim();

                if (TextUtils.isEmpty(Email)){
                    SignEmail.setError("Username cannot be empty!");
                    return;
                }
                if (TextUtils.isEmpty(Password)){
                    SignPassword.setError("Password cannot be empty!");
                }

                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(signIn.this,"User Logged In",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),mainMenu.class));
                        }
                        else
                            Toast.makeText(signIn.this, "Error " + task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
    }
}