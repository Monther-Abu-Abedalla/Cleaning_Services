package com.example.cleaningservices.login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {
    
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText etUserName;
    TextInputLayout etLayoutUserName;
    TextInputLayout etEmailLayoutInSignUp;
    EditText etEmailInSignUp;
    EditText etPasswordInSignUp;
    TextInputLayout etPasswordLayoutInSignUp;
    Button register_btn;
    View viewBlur;
    View pbLoading;
    TextView btnGoToAccountLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        btnGoToAccountLogin = findViewById(R.id.btnGoToAccountLogin);
        btnGoToAccountLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));
                finish();
            }
        });

        viewBlur = findViewById(R.id.viewBlurnSignUp);
        pbLoading = findViewById(R.id.pbLoadingInSignUp);

        etUserName = findViewById(R.id.etUserName);
        etLayoutUserName = findViewById(R.id.etLayoutUserName);
        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etLayoutUserName.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        
        etEmailInSignUp = findViewById(R.id.etEmailInSignUp);
        etEmailLayoutInSignUp = findViewById(R.id.etEmailLayoutInSignUp);

        etEmailInSignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etEmailLayoutInSignUp.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        etPasswordInSignUp = findViewById(R.id.etPasswordInSignUp);

        etPasswordLayoutInSignUp = findViewById(R.id.etPasswordLayoutInSignUp);
        etPasswordInSignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etPasswordLayoutInSignUp.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidUsername = false;
                if (etUserName.getText().toString().trim().isEmpty()) {
                    etLayoutUserName.setError(getResources().getString(R.string.username_req));
                }
                else {
                    isValidUsername = true;
                }

                boolean isValidEmail = false;
                if(etEmailInSignUp.getText().toString().trim().isEmpty()){
                    etEmailLayoutInSignUp.setError(getResources().getString(R.string.email_req));
                }else if(!(etEmailInSignUp.getText().toString().contains("@") &&
                        etEmailInSignUp.getText().toString().contains(".com"))){
                    etEmailLayoutInSignUp.setError(getResources().getString(R.string.valid_email_req));
                }else{
                    isValidEmail = true;
                }


                boolean isValidPassword = false;
                if(etPasswordInSignUp.getText().toString().trim().isEmpty()){
                    etPasswordLayoutInSignUp.setError(getResources().getString(R.string.password_req));
                }else if(etPasswordInSignUp.getText().toString().length() < 6 ){
                    etPasswordLayoutInSignUp.setError(getResources().getString(R.string.valid_password_req));
                }else{
                    isValidPassword = true;
                }

                if(isValidUsername && isValidEmail && isValidPassword){
                    viewBlur.setVisibility(View.VISIBLE);
                    pbLoading.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(etEmailInSignUp.getText().toString(),etPasswordInSignUp.getText().toString()).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        addUserToDatabase(auth.getCurrentUser().getUid(), etUserName.getText().toString(), auth.getCurrentUser().getEmail(), etPasswordInSignUp.getText().toString());
                                    }else {
                                        showHideProgress();
                                        showErrorSnackBar(R.string.email_used);
                                    }

                                }
                            });
                }

            }
        });

    }

    private void addUserToDatabase(String id, String username, String email, String password) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", auth.getCurrentUser().getUid());
        user.put("username", etUserName.getText().toString());
        user.put("email", auth.getCurrentUser().getEmail());
        user.put("password", etPasswordInSignUp.getText().toString());
        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                showHideProgress();
                View container = findViewById(R.id.registerLayoutContainer);
                Snackbar snackbar = Snackbar.make(container, getResources().getString(R.string.account_create_success), Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundColor(getResources().getColor(R.color.success));
                snackbar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }

                }, 500);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showHideProgress();
                showErrorSnackBar(R.string.failed_to_add_account);
            }
        });
    }

    private void showErrorSnackBar(int stringResource) {
        View container = findViewById(R.id.registerLayoutContainer);
        Snackbar snackbar = Snackbar.make(container, getResources().getString(stringResource), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.red));
        snackbar.show();
    }

    private void showHideProgress() {
        if (pbLoading.getVisibility() == View.VISIBLE) {
            pbLoading.setVisibility(View.GONE);
            viewBlur.setVisibility(View.GONE);
        }
    }

}