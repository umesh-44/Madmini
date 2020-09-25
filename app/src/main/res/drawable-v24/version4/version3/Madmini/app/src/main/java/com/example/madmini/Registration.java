package com.example.madmini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madmini.ui.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    EditText firstName,lastName,password,retypePassword,Email,mobile;
    Button createAccount,signIn;
    boolean valid=true;

    FirebaseAuth fAuth;
    FirebaseFirestore fStory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fAuth=FirebaseAuth.getInstance();
        fStory=FirebaseFirestore.getInstance();

        firstName=findViewById(R.id.registerfirstname);
        lastName=findViewById(R.id.registerlastname);
        Email=findViewById(R.id.registeremail);
        mobile=findViewById(R.id.registermobile);
        password=findViewById(R.id.registerpassword);
        retypePassword=findViewById(R.id.registerretypepassowrd);

        createAccount=findViewById(R.id.btnregcreateaccount);
        signIn=findViewById(R.id.btnsignup);

       createAccount.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               checkField(firstName);
               checkField(lastName);
               checkField(Email);
               checkField(mobile);
               checkField(password);
               checkField(retypePassword);

               if(valid){
                   fAuth.createUserWithEmailAndPassword(Email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
                           FirebaseUser user=fAuth.getCurrentUser();
                           Toast.makeText(Registration.this, "Account Created", Toast.LENGTH_SHORT).show();
                           DocumentReference df=fStory.collection("Users").document(user.getUid());
                           Map<String,Object> userInfo=new HashMap<>();
                           userInfo.put("FirstName",firstName.getText().toString());
                           userInfo.put("LastName",lastName.getText().toString());
                           userInfo.put("Email",Email.getText().toString());
                           userInfo.put("Mobile",mobile.getText().toString());
                           userInfo.put("Password",password.getText().toString());
                           //specify if the user is admin//

                           userInfo.put("isUser","1");
                           df.set(userInfo);

                           startActivity(new Intent(getApplicationContext(),MainActivity.class));
                           finish();
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(Registration.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                       }
                   });
               }
           }
       });
       signIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(), Login.class));
           }
       });
    }
    public boolean checkField(EditText textFiled){
        if(textFiled.getText().toString().isEmpty()){
            textFiled.setText("Error");
            valid=false;
        }
        else {
            valid=true;
        }
        return valid;
    }

}