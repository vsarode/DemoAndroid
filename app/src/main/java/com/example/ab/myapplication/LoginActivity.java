package com.example.ab.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.ab.myapplication.asyncTask.UserLoginTask;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myContext = this;
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button mSignUpButton = (Button) findViewById(R.id.SignUp);

        mSignUpButton.setOnClickListener(this);

        mEmailSignInButton.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();


        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
        }

        try {
            new UserLoginTask(email, password, myContext).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.SignUp) {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.email_sign_in_button) {
            attemptLogin();
            Intent intent = new Intent(this, Prakingground.class);
            startActivity(intent);
        }
    }
}