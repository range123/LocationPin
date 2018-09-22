package com.range.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        final Intent intent=getIntent();
        final EditText et=(EditText)findViewById(R.id.mess);
        Button share=(Button)findViewById(R.id.butt);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=et.getEditableText().toString();
                if (message.isEmpty())
                    Toast.makeText(ShareActivity.this, "ENTER VALID MESSAGE!", Toast.LENGTH_SHORT).show();
                else
                {
                    String uname= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    String pid= FirebaseDatabase.getInstance().getReference("shares").push().getKey();
                    double lat=intent.getDoubleExtra("latitude",0);
                    double lon=intent.getDoubleExtra("longitude",0);
                    share s=new share(lat,lon,uname,message,pid);
                    FirebaseDatabase.getInstance().getReference("shares").child(pid).setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ShareActivity.this, "Shared Successfully", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ShareActivity.this, "Share Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                    finish();


                }
            }
        });
    }
}
