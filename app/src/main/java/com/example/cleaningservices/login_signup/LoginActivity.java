package com.example.cleaningservices.login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cleaningservices.R;
import com.example.cleaningservices.user_screens.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout etEmailLayoutInSignIn;
    EditText etEmailInSignIn;
    EditText etPasswordInSignIn;
    TextInputLayout etPasswordLayoutInSignIn;
    TextView btnForgetPassword;
    Button login_btn;
    ImageButton btnLoginByFacebook;
    ImageButton btnLoginByGooglePlus;
    ImageButton btnLoginByTwitter;
    TextView btnGoToAccountCreate;
    View pbLoadingInSignIn;
    View viewBlurInSignIn;
    View signInContainer;


    final private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInContainer = findViewById(R.id.signInContainer);
        pbLoadingInSignIn = findViewById(R.id.pbLoadingInSignIn);
        viewBlurInSignIn = findViewById(R.id.viewBlurInSignIn);

        etEmailInSignIn = findViewById(R.id.etEmailInSignIn);
        etEmailLayoutInSignIn = findViewById(R.id.etEmailLayoutInSignIn);

        etEmailInSignIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               etEmailLayoutInSignIn.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etPasswordInSignIn = findViewById(R.id.etPasswordInSignIn);

        etPasswordLayoutInSignIn = findViewById(R.id.etPasswordLayoutInSignIn);
        etPasswordInSignIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etPasswordLayoutInSignIn.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidEmail = false;
                if(etEmailInSignIn.getText().toString().trim().isEmpty()){
                    etEmailLayoutInSignIn.setError(getResources().getString(R.string.email_req));
                }else if(!(etEmailInSignIn.getText().toString().contains("@") &&
                        etEmailInSignIn.getText().toString().contains(".com"))){
                    etEmailLayoutInSignIn.setError(getResources().getString(R.string.valid_email_req));
                }else{
                     isValidEmail = true;
                }


                boolean isValidPassword = false;
                if(etPasswordInSignIn.getText().toString().trim().isEmpty()){
                    etPasswordLayoutInSignIn.setError(getResources().getString(R.string.password_req));
                }else if(etPasswordInSignIn.getText().toString().length() < 6 ){
                    etPasswordLayoutInSignIn.setError(getResources().getString(R.string.valid_password_req));
                }else{
                    isValidPassword = true;
                }
                if(isValidEmail && isValidPassword){
                    pbLoadingInSignIn.setVisibility(View.VISIBLE);
                    viewBlurInSignIn.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(etEmailInSignIn.getText().toString(),etPasswordInSignIn.getText().toString()).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
                           }else {
                               hideProgress();
                               showErrorSnackBar(R.string.error_sign_in);
                           }

                        }
                    });


                }

            }
        });


        btnGoToAccountCreate = findViewById(R.id.btnGoToAccountCreate);
        btnGoToAccountCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext() , CreateAccountActivity.class);
                startActivity(i);

            }
        });




    }

    private void showErrorSnackBar(int stringResource) {
        View container = findViewById(R.id.registerLayoutContainer);
        Snackbar snackbar = Snackbar.make(signInContainer, getResources().getString(stringResource), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.red));
        snackbar.show();
    }

    private void hideProgress() {
        if (pbLoadingInSignIn.getVisibility() == View.VISIBLE) {
            pbLoadingInSignIn.setVisibility(View.GONE);
            viewBlurInSignIn.setVisibility(View.GONE);
        }
    }


}