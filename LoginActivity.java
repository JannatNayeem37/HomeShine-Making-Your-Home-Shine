package com.example.homeshine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private View loginForm, signupForm;
    private TextView tabLogin, tabSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabLogin = findViewById(R.id.tabLogin);
        tabSignup = findViewById(R.id.tabSignup);
        loginForm = findViewById(R.id.loginForm);
        signupForm = findViewById(R.id.signupForm);

        tabLogin.setOnClickListener(v -> showLoginTab());
        tabSignup.setOnClickListener(v -> showSignupTab());

        findViewById(R.id.btnLogin).setOnClickListener(v -> attemptLogin());
        findViewById(R.id.btnSignup).setOnClickListener(v -> attemptSignup());

        findViewById(R.id.forgotPassword).setOnClickListener(v ->
                Toast.makeText(this, "Password reset link sent (demo)", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnGoogle).setOnClickListener(v ->
                Toast.makeText(this, "Google sign-in coming soon", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnFacebook).setOnClickListener(v ->
                Toast.makeText(this, "Facebook sign-in coming soon", Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnSkip).setOnClickListener(v -> goHome());
    }

    private void showLoginTab() {
        loginForm.setVisibility(View.VISIBLE);
        signupForm.setVisibility(View.GONE);
        tabLogin.setBackgroundResource(R.drawable.bg_tab_active);
        tabSignup.setBackground(null);
        tabLogin.setTextColor(getColor(R.color.teal_dark));
        tabSignup.setTextColor(getColor(R.color.ink_light));
    }

    private void showSignupTab() {
        loginForm.setVisibility(View.GONE);
        signupForm.setVisibility(View.VISIBLE);
        tabSignup.setBackgroundResource(R.drawable.bg_tab_active);
        tabLogin.setBackground(null);
        tabSignup.setTextColor(getColor(R.color.teal_dark));
        tabLogin.setTextColor(getColor(R.color.ink_light));
    }

    private void attemptLogin() {
        EditText email = findViewById(R.id.loginEmail);
        EditText password = findViewById(R.id.loginPassword);

        if (TextUtils.isEmpty(email.getText())) {
            email.setError("Enter your email");
            return;
        }
        if (TextUtils.isEmpty(password.getText())) {
            password.setError("Enter your password");
            return;
        }
        Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
        goHome();
    }

    private void attemptSignup() {
        EditText name = findViewById(R.id.signupName);
        EditText email = findViewById(R.id.signupEmail);
        EditText password = findViewById(R.id.signupPassword);
        EditText confirm = findViewById(R.id.signupConfirm);

        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Enter your name");
            return;
        }
        if (TextUtils.isEmpty(email.getText())) {
            email.setError("Enter your email");
            return;
        }
        if (TextUtils.isEmpty(password.getText())) {
            password.setError("Create a password");
            return;
        }
        if (!password.getText().toString().equals(confirm.getText().toString())) {
            confirm.setError("Passwords don't match");
            return;
        }
        Toast.makeText(this, "Account created — welcome, " + name.getText() + "!", Toast.LENGTH_SHORT).show();
        goHome();
    }

    private void goHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
