package com.range.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        final SharedPreferences pref=getSharedPreferences("myprefs",MODE_PRIVATE);
        final SharedPreferences.Editor editor=pref.edit();
        final ListView show=(ListView)findViewById(R.id.lv);
        Intent i=getIntent();
        final ArrayList<String> data = new ArrayList<>();
        final ArrayAdapter<String> myadapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, data);
        show.setAdapter(myadapter);
        FirebaseDatabase.getInstance().getReference("shares").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                share s1=dataSnapshot.getValue(share.class);
                    String addy=s1.getUsername()+" : "+s1.getMessage();
                    data.add(addy);
                    myadapter.notifyDataSetChanged();
                    editor.putString(addy,s1.getPid());
                    editor.apply();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int i1=i;

                AlertDialog.Builder builder=new AlertDialog.Builder(DeleteActivity.this,R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setCancelable(true);
                builder.setTitle("CONFIRM DELETE").setMessage("Do You Want To Delete This Entry?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final Object o = show.getItemAtPosition(i1);
                        String s;
                        s = pref.getString(o.toString(),"");
                        editor.remove(o.toString());
                        editor.apply();
                        data.remove(i1);
                        myadapter.notifyDataSetChanged();
                        FirebaseDatabase.getInstance().getReference("shares").child(s).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(DeleteActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DeleteActivity.this, "Delete Failed", Toast.LENGTH_SHORT).show();

                            }
                        });
                        dialogInterface.cancel();

                    }
                })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(DeleteActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                                dialogInterface.cancel();

                            }
                        });
                AlertDialog a=builder.create();
                a.show();


            }
        });

    }
}
