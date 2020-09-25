package com.example.madmini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    Button btn;
    EditText oldPassword,NewPassword;
    FirebaseAuth FAuth;
    FirebaseFirestore FStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    btn=findViewById(R.id.btnchgpassword);
    oldPassword=findViewById(R.id.txtoldpassword);
    NewPassword=findViewById(R.id.txtnewpassowrd);

    FAuth=FirebaseAuth.getInstance();
    FStore=FirebaseFirestore.getInstance();

    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String oldPa=oldPassword.getText().toString();
            String newPa=NewPassword.getText().toString();
            if(TextUtils.isEmpty(oldPa)){
                Toast.makeText(ChangePassword.this, "Enter Yore Current Password", Toast.LENGTH_SHORT).show();
                return;
            }
            if(NewPassword.length()<6){
                Toast.makeText(ChangePassword.this, "Password length at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            updatePassword(oldPa,newPa);
        }
    });
    }
    private void updatePassword(String oldPa, final String newPa){
        final FirebaseUser user=FAuth.getCurrentUser();
        AuthCredential credential= EmailAuthProvider.getCredential(user.getEmail(),oldPa);
        user.reauthenticate(credential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                            user.updatePassword(newPa).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    DocumentReference dooRef=FStore.collection("Users").document(user.getUid());
                                    Map<String,Object> edited=new HashMap<>();
                                    edited.put("Password",newPa);
                                    dooRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ChangePassword.this, "password Updated...", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChangePassword.this, "Not updated"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChangePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}