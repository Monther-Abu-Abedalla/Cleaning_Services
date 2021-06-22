package com.example.cleaningservices.login_signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cleaningservices.R;
import com.example.cleaningservices.user_screens.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class RestartPassword extends AppCompatActivity {
    TextInputLayout etEmailLayoutInSignIn;
    EditText etEmailInSignIn;
    Button restart_paswword_btn;
    TextView btnGoToAccountCreate;
    View pbLoadingInSignIn;
    View viewBlurInSignIn;
    View restartPasswordContainer;

    final private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart_password);

        restartPasswordContainer = findViewById(R.id.restartPasswordContainer);
        pbLoadingInSignIn = findViewById(R.id.pbLoadingInSignIn);
        viewBlurInSignIn = findViewById(R.id.viewBlurInSignIn);

        etEmailInSignIn = findViewById(R.id.etEmailInRestartPassword);
        etEmailLayoutInSignIn = findViewById(R.id.etEmailLayoutInRestartPassword);

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



        restart_paswword_btn = findViewById(R.id.restart_paswword_btn);
        restart_paswword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(restartPasswordContainer);
                boolean isValidEmail = false;
                if(etEmailInSignIn.getText().toString().trim().isEmpty()){
                    etEmailLayoutInSignIn.setError(getResources().getString(R.string.email_req));
                }else if(!(etEmailInSignIn.getText().toString().contains("@") &&
                        etEmailInSignIn.getText().toString().contains(".com"))){
                    etEmailLayoutInSignIn.setError(getResources().getString(R.string.valid_email_req));
                }else{
                     isValidEmail = true;
                }
                if(isValidEmail){
                    auth.sendPasswordResetEmail(etEmailInSignIn.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            showSnackBar(R.string.success_to_send_email,R.color.green);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            showSnackBar(R.string.failed_to_send_email,R.color.red);
                        }
                    });
                }
            }
        });

    }

    private void showSnackBar(int stringResource,int colorResource) {
        View container = findViewById(R.id.restartPasswordContainer);
        Snackbar snackbar = Snackbar.make(container, getResources().getString(stringResource), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(getResources().getColor(colorResource));
        snackbar.show();
    }
    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}