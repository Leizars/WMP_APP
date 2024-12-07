package com.apaaja.eventapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class StudentDetailActivity extends AppCompatActivity {

    private EditText edtStudentName, edtStudentId, edtClassNumber;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        edtStudentName = findViewById(R.id.edtStudentName);
        edtStudentId = findViewById(R.id.edtStudentId);
        edtClassNumber = findViewById(R.id.edtClassNumber);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> submitStudentDetails());
    }

    private void submitStudentDetails() {
        String studentName = edtStudentName.getText().toString();
        String studentId = edtStudentId.getText().toString();
        String classNumber = edtClassNumber.getText().toString();

        if (TextUtils.isEmpty(studentName) || TextUtils.isEmpty(studentId) || TextUtils.isEmpty(classNumber)) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("StudentDetails")
                .orderBy("studentId", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        String lastStudentId = documentSnapshot.getId();

                        int lastStudentNumber = Integer.parseInt(lastStudentId.replaceAll("[^0-9]", ""));
                        int newStudentNumber = lastStudentNumber + 1;

                        String newStudentId = "student" + newStudentNumber;

                        Map<String, Object> student = new HashMap<>();
                        student.put("name", studentName);
                        student.put("studentId", studentId);
                        student.put("classNumber", classNumber);
                        //student.put("timestamp", System.currentTimeMillis());

                        db.collection("StudentDetails")
                                .document(newStudentId)
                                .set(student)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(StudentDetailActivity.this, "Student added successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e -> Toast.makeText(StudentDetailActivity.this, "Error adding student", Toast.LENGTH_SHORT).show());
                    } else {

                        String newStudentId = "student1";

                        Map<String, Object> student = new HashMap<>();
                        student.put("name", studentName);
                        student.put("studentId", studentId);
                        student.put("classNumber", classNumber);
                        student.put("timestamp", System.currentTimeMillis());

                        db.collection("StudentDetails")
                                .document(newStudentId)
                                .set(student)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(StudentDetailActivity.this, "Student added successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e -> Toast.makeText(StudentDetailActivity.this, "Error adding student", Toast.LENGTH_SHORT).show());
                    }
                });
    }
}
