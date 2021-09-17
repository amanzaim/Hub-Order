package com.example.huborder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    //Initialize variable
    ImageView ivImage;
    TextView tvName;
    Button btLogout;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private CardView StartOrderCard, OrderHistoryCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Assign var
        ivImage = findViewById(R.id.iv_image);
        tvName = findViewById(R.id.tv_name);
        btLogout = findViewById(R.id.bt_logout);

        //initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        //Intialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //check condition

        if (firebaseUser != null) {
            //When firebase user is not equal to null
            //Set image on view
            Glide.with(ProfileActivity.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(ivImage);
            //Set name on text View
            tvName.setText(firebaseUser.getDisplayName());
        }

        //Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(ProfileActivity.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sign out from google
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //check condition
                        if (task.isSuccessful()) {
                            //When task is successful
                            //Sign out from firebase
                            firebaseAuth.signOut();
                            //Display toast
                            Toast.makeText(getApplicationContext()
                                    , "Logout successfull", Toast.LENGTH_SHORT).show();
                            //finish activity
                            finish();
                        }

                    }
                });
            }
        });

        //defining card
        StartOrderCard = (CardView) findViewById(R.id.orderpick);
        OrderHistoryCard = (CardView) findViewById(R.id.pastorder);

        //add click listener to card
        StartOrderCard.setOnClickListener(this);
        OrderHistoryCard.setOnClickListener(this);


        //hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.mytoolbar);

        //Tool Bar
        setSupportActionBar(toolbar);

        //Navigation Drawer Menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){

        switch (menuItem.getItemId()){
            case R.id.nav_pastOrder:
                Intent intent = new Intent(ProfileActivity.this,OrderHistory.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                Toast.makeText(getApplicationContext()
                        , "Logout successfull", Toast.LENGTH_SHORT).show();
                //logout
                firebaseAuth.signOut();
                finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.orderpick:
                i = new Intent(this, StartOrder.class);
                startActivity(i);
                break;
            case R.id.pastorder:
                i = new Intent(this, OrderHistory.class);
                startActivity(i);
                break;
            default:
                break;
        }

    }


}


