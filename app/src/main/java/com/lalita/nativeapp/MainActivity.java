package com.lalita.nativeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "";
//    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent);
        Toast.makeText(this, "Send Message Click", Toast.LENGTH_SHORT).show();
    }

    public void testFireStore(){

        final String TAG = "testFireStore";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("restaurants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

//    private void initFirestore() {
//        mFirestore = FirebaseFirestore.getInstance();
//        int LIMIT = 0;
//        Query mQuery = mFirestore.collection( "restaurants" )
//                .orderBy( "Rating", Query.Direction.DESCENDING )
//                .limit(LIMIT);
//    }
//
    public void signInClick(View view){
        final String TAG = "signInClick";
        Context context = getApplicationContext();
//        CharSequence text = "Login Successfully!!";
//        int duration = Toast.LENGTH_SHORT;
        String email = ((TextView)findViewById(R.id.editText)).getText().toString();
        String password = ((TextView)findViewById(R.id.editText2)).getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            OpenUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            OpenUI(null);
                        }
                    }
                });
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();

    }

//    public void sigoutClick(View view){
//        final String TAG = "sigoutClick";
//        Log.w(TAG, "sign out");
//        mAuth.signOut();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            updateUI(currentUser);
//        }
//    }
//
    private void OpenUI(FirebaseUser currentUser) {
        final String TAG = "openUI";
        TextView loginName;
        if(currentUser != null){
            Log.d(TAG, currentUser.getEmail());
            Intent intent = new Intent(this,ListActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
        }
    }




}
