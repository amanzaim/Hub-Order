package com.example.huborder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    //initialize variables
    SignInButton btSignIn;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign var
        btSignIn = findViewById(R.id.bt_sign_in);

        //initialize sign in options
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken("150221136362-5d9q6tmm3s8c9g97s7do0v5erpkh91jr.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //Intialize sign in client
        googleSignInClient = GoogleSignIn.getClient(MainActivity.this
                ,googleSignInOptions);

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize signin intent
                Intent intent = googleSignInClient.getSignInIntent();

                //Start activit for result
                startActivityForResult(intent, 100);

            }
        });

        //Intialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        //Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //check condition
        if (firebaseUser != null){
            //When user already sign in
            //Redirect to profile activity
            startActivity(new Intent(MainActivity.this
                    ,ProfileActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Check condition

        if (requestCode == 100){
            //When request code os equal to 100
            //initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                    .getSignedInAccountFromIntent(data);
            //When google sign in successful
            //initialize string
            String s = "Google Sign In Successful";
            //Display toast
            displayToast(s);

            try {
                //Initialize sign in account
                GoogleSignInAccount googleSignInAccount = signInAccountTask
                        .getResult(ApiException.class);
                //check condition
                AuthCredential authCredential = GoogleAuthProvider
                        .getCredential(googleSignInAccount.getIdToken()
                                ,null);
                //Check credential
                firebaseAuth.signInWithCredential(authCredential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Check condition
                                if (task.isSuccessful()){
                                    //When task is successful
                                    //Redirect to profile activity
                                    startActivity(new Intent(MainActivity.this
                                            ,ProfileActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    //Display toast
                                    displayToast("Firebase authentication successful");
                                } else{
                                    //When task is unsuccessful
                                    //Display toast
                                    displayToast("Authentication Failed : " + task.getException()
                                            .getMessage());
                                }
                            }
                        });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

    }
}