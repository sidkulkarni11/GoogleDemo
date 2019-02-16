package com.sid.googledemo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    SignInButton signInButton;
    TextView personEmail, personFamilyName,Emailid;
    ImageView personImage;

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = findViewById(R.id.sign_in_button);
        personImage = findViewById(R.id.img);
        personEmail = findViewById(R.id.Name);
        personFamilyName = findViewById(R.id.Familyname);
        Emailid = findViewById(R.id.Emailid);

        ;

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);

        if (account != null) {
            personEmail.setText(account.getDisplayName());


            Emailid.setText(account.getEmail());
            if(account.getPhotoUrl()!=null)
                Picasso.get()
                        .load(account.getPhotoUrl().toString())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(personImage);
//            personImage.setImageURI(account.getPhotoUrl());
            personFamilyName.setText(account.getFamilyName());

//            Log.d("**", account.getPhotoUrl().toString());
        } else {

        }


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        Button signOut = findViewById(R.id.SignOut);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                Picasso.get().load(R.mipmap.ic_launcher).into(personImage);
                Toast.makeText(MainActivity.this, "Successfully Signed out", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            if (account != null) {
                personEmail.setText(account.getDisplayName());

                Emailid.setText(account.getEmail());
//                Toast.makeText(this, account.getPhotoUrl().toString(), Toast.LENGTH_SHORT).show();
//                if(!account.getPhotoUrl().toString().trim().isEmpty())
                if(account.getPhotoUrl()!=null)
                Picasso.get()
                        .load(account.getPhotoUrl().toString())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(personImage);

//            personImage.setImageURI(account.getPhotoUrl());
                personFamilyName.setText(account.getFamilyName());

//                Log.d("**", account.getPhotoUrl().toString());
            } else {

            }
//            Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
            Log.w("", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }
}
