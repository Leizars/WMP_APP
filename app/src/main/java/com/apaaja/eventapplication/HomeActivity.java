package com.apaaja.eventapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private ClassAdapter classAdapter;
    private List<ClassItem> classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        classList = new ArrayList<>();

        classAdapter = new ClassAdapter(this, classList);
        recyclerView.setAdapter(classAdapter);

        fetchClassesFromFirestore();
    }

    private void fetchClassesFromFirestore() {
        db.collection("Classes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot querySnapshot = task.getResult();
                        classList.clear();
                        for (DocumentSnapshot document : querySnapshot) {
                            String className = document.getString("className");
                            String location = document.getString("location");
                            String time = document.getString("time");
                            boolean available = document.getBoolean("available");

                            if (available) {
                                classList.add(new ClassItem(className, location, time));
                            }
                        }
                        classAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("FirestoreError", "Error fetching classes", task.getException());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, StudentDetailActivity.class);
            startActivity(intent);
        }
    }
}
