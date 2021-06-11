package com.example.cleaningservices.login_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cleaningservices.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

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
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                    etEmailLayoutInSignIn.setError("يجب ادخال بريد الكتروني");
                }else if(!(etEmailInSignIn.getText().toString().contains("@") &&
                        etEmailInSignIn.getText().toString().contains(".com"))){
                    etEmailLayoutInSignIn.setError("يجب ادخال بريد الكتروني صالح");
                }else{
                     isValidEmail = true;
                }


                boolean isValidPassword = false;
                if(etPasswordInSignIn.getText().toString().trim().isEmpty()){
                    etPasswordLayoutInSignIn.setError("يجب ادخال كلمة المرور");
                }else if(etPasswordInSignIn.getText().toString().length() < 6 ){
                    etPasswordLayoutInSignIn.setError("يجب ادخال كلمة مرور اكبر من 6 احرف");
                }else{
                    isValidPassword = true;
                }

                if(isValidEmail && isValidPassword){
                    mAuth.signInWithEmailAndPassword(etEmailInSignIn.getText().toString(),etPasswordInSignIn.getText().toString()).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                           if (task.isSuccessful()){

                           }else {

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

}