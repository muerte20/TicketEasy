package com.example.kassa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kassa.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        binding.backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.signUpBtn.setOnClickListener(view -> {
            String email = binding.emailEt.getText().toString();
            String password = binding.passwordEt.getText().toString();
            String username = binding.usernameEt.getText().toString();

            if (email.isEmpty() || username.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = auth.getCurrentUser();
                                if (currentUser != null) {
                                    String userId = currentUser.getUid();
                                    User userInfo = new User(email, username);
                                    db.collection("Users").document(userId)
                                            .set(userInfo)
                                            .addOnCompleteListener(userTask -> {
                                                if (userTask.isSuccessful()) {
                                                    Log.d("RegisterActivity", "User data saved successfully");
                                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                } else {
                                                    Log.e("RegisterActivity", "Failed to save user data", userTask.getException());
                                                    Toast.makeText(getApplicationContext(), "Failed to save user data. Please try again.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            } else {
                                Log.e("RegisterActivity", "User registration failed", task.getException());
                                Toast.makeText(getApplicationContext(), "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    public static class User {
        private String email;
        private String username;

        public User() {
        }

        public User(String email, String username) {
            this.email = email;
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
