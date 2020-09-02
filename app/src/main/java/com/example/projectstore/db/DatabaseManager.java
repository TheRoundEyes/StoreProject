package com.example.projectstore.db;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.projectstore.activity.HomePageActivity;
import com.example.projectstore.obj.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseManager {
    DatabaseReference dbRef;
    FirebaseAuth mFirebaseAuth;
    Context con;
    User user;
    boolean isUsernameExisting = false, isContactExisting = false;

    public DatabaseManager(Context con) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        user = new User();
        this.con = con;
    }

    public void writeNewUser(String fullname, String location, String contactnumber, String storename,
                             String email, String un, String password, String userType) {
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        Long cn = Long.parseLong(contactnumber);
        if(userType.equals("Customer")) user = new User(fullname, location, contactnumber, email, un, password, userType);
        else user = new User(fullname, location, contactnumber, storename, email, un, password, userType);
        dbRef.child(un).setValue(user);
    }

    public void emailAndPasswordAuth(final String fn, final String loc, final String cn, final String store,
                                     final String email_, final String un, final String pw, final String userType, final TextView textView, final Context context) {
        mFirebaseAuth.createUserWithEmailAndPassword(email_, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    writeNewUser(fn, loc, cn, store, email_, un, pw, userType);
                    context.startActivity(new Intent(context, HomePageActivity.class));
                }
                else textView.setText("Email is Existing.");
            }
        });
    }

    public void getData(final String fn, final String loc, final String cn, final String store,
                        final String email_, final String un, final String pw, final String userType, final Context ctx, final TextView textView) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()) {
                    String cn_ = data.child("contactNumber").getValue().toString();
                    String un_ = data.child("username").getValue().toString();
                    if(cn_.equals(cn) && un_.equals(un)) {
                        isUsernameExisting = true;
                        isContactExisting = true;
                        break;
                    }
                    else if(cn_.equals(cn)) {
                        isUsernameExisting = false;
                        isContactExisting = true;
                        break;
                    }
                    else if(un_.equals(un)) {
                        isUsernameExisting = true;
                        isContactExisting = false;
                        break;
                    }
                    else {
                        isUsernameExisting = false;
                        isContactExisting = false;
                    }
                }
                if(isUsernameExisting && isContactExisting) {
                    textView.setText("Username and Contact Number is existing.");
                }
                else if(isUsernameExisting) textView.setText("Username is existing.");
                else if(isContactExisting) textView.setText("Contact Number is existing.");
                else {
                    emailAndPasswordAuth(fn, loc, cn, store, email_, un, pw, userType, textView, ctx);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
