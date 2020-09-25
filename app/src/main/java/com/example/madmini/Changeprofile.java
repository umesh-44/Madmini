package com.example.madmini;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Changeprofile extends AppCompatActivity {

    TextView firstName, LastName, Email, Mobile;
    Button change, can;
    FirebaseAuth FAuth;
    FirebaseFirestore FStore;
    String userId, FirstName1, lastName1, Email1, Mobile1;

    @SuppressLint({"WrongConstant", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeprofile);

        //Set the title in tool bar---------------------------------------------------------------------------------------------------
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        //----IDS-------------------------------------------------------------------------------------------------------------------
        firstName = findViewById(R.id.lblfirstname);
        LastName = findViewById(R.id.lbllastname);
        Email = findViewById(R.id.lblemail);
        Mobile = findViewById(R.id.lblmobile);

        change = findViewById(R.id.btnedit);
        can = findViewById(R.id.btncancel);

        //Firebase needs------------------------------------------------------------------------------------------------------------
        FAuth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();

        //get current user ids-----------------------------------------------------------------------------------------------------
        userId = FAuth.getCurrentUser().getUid();

        //get the firebase values-----------------------------------------------------------------------------------------------
        final DocumentReference documentReference = FStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Mobile.setText(value.getString("Mobile"));
                firstName.setText(value.getString("FirstName"));
                LastName.setText(value.getString("LastName"));
                Email.setText(value.getString("Email"));
            }
        });

        //Click the change profile button-----------------------------------------------------------------------------------------
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UpdateProfile.class);

                FirstName1 = firstName.getText().toString();
                i.putExtra("firstName", FirstName1);

                lastName1 = LastName.getText().toString();
                i.putExtra("LastName", lastName1);

                Email1 = Email.getText().toString();
                i.putExtra("email", Email1);

                Mobile1 = Mobile.getText().toString();
                i.putExtra("mobile", Mobile1);

                startActivity(i);
            }
        });
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}