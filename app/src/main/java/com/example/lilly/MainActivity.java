package com.example.lilly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lilly.fragments.HomeFragment;
import com.example.lilly.fragments.ProfileFragment;
import com.example.lilly.fragments.ResourceFragment;
import com.example.lilly.models.Info;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public BottomNavigationView bottomNavigationView;

    private DatabaseReference mDatabase;
    private DatabaseReference mPostReference;
    private List<Info> medicineInfo;
// ...

    private final HomeFragment fragmentHome = new HomeFragment();
    private final ResourceFragment fragmentResource = new ResourceFragment();
    private final ProfileFragment fragmentProfile = new ProfileFragment();
    public static final Integer RecordAudioRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_resource:
                        fragment = fragmentResource;
                        break;
                    case R.id.action_profile:
                        fragment = fragmentProfile;
                        break;
                    case R.id.action_home:
                    default:
                        fragment = fragmentHome;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.rlContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        String medicineId = "medicine1";
        mPostReference = mDatabase.child("medicineData").child(medicineId);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    // Get Post object and use the values to update the UI
//                Post post = dataSnapshot.getValue(Post.class);
//                    medicineInfo.add(
//                            new Info(postSnapShot.getKey(), postSnapShot.getValue().toString())
//                    );
                };
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }

        };
        mPostReference.addValueEventListener(postListener);

    }

}