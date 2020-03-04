package com.olaolukiyesi.mysmartmirror;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.olaolukiyesi.mysmartmirror.Models.User;
import com.olaolukiyesi.mysmartmirror.Services.impl.FireStoreServeImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    int RC_SIGN_IN = 0;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    FireStoreServeImpl fireStoreServe = FireStoreServeImpl.getInstance();

    @BindView(R.id.sign_in_button)
    SignInButton btnGoogleSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FireStoreServeImpl.getAuthInstance();
        btnGoogleSign = findViewById(R.id.sign_in_button);
        btnGoogleSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignIn();
            }
        });
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    public void GoogleSignIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            firebaseAuthWithGoogle(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("Bridging Firebase and Google Auth", "firebaseAuthWithGoogle:" + acct.getId());
        try {
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Successful Authorization", "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                User current = new User(user.getUid(),user.getEmail());
                                fireStoreServe.validateUser(current);


                                startMirror();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Failed Sign-In", "signInWithCredential:failure", task.getException());
                                Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();

                            }


                        }
                    });
        }
        catch (Exception ex){
            Log.d("Exception"+ ex.getLocalizedMessage(), "firebaseAuthWithGoogle: ");

            //startMirrorGuest();

        }



    }

    private void startMirrorGuest() {
        boolean guestEnabled = true;
        Intent start = new Intent(this,MirrorActivity.class);
        Bundle b = new Bundle();
        b.putBoolean("Guest Mode", guestEnabled);
        start.putExtras(b);

        startActivity(start);
    }

    private void startMirror() {
        Intent start = new Intent(this,MirrorActivity.class);
        boolean guestEnabled = false;
        Bundle b = new Bundle();
        b.putBoolean("Guest Mode", guestEnabled);
        startActivity(start);
    }
}
