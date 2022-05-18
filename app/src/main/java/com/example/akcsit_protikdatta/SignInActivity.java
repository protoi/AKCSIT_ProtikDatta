package com.example.akcsit_protikdatta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akcsit_protikdatta.databinding.ActivitySignInBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
ActivitySignInBinding binding;
TextInputLayout nameLayout, pwdLayout, emailLayout;
TextInputEditText nameInput, pwdInput, emailInput;
TextView optionSignUp;
Button btnSignIn;
String name, pass, email; //getting an "email account is badly formatted error"
FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_in);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

/*
        nameLayout = binding.layoutUname;
        nameInput = binding.inputUname;
*/

        emailLayout = binding.layoutEmail;
        emailInput = binding.inputEmail;// THIS IS THE ISSUE

        pwdLayout = binding.layoutPassword;
        pwdInput = binding.inputPassword;

        btnSignIn = binding.btnSubmit;

        optionSignUp = binding.signOpt;
        auth = FirebaseAuth.getInstance();

        optionSignUp.setOnClickListener(view -> {
            Intent in = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(in);
            SignInActivity.this.finish();
        });

        btnSignIn.setOnClickListener(view -> {
            if(validateFields())
            {
                doLogin();
//                Toast.makeText(SignInActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public boolean validateFields()
    {
        boolean flag = false;
        pwdLayout.setError("");
        emailLayout.setError("");

//        name = String.valueOf(nameInput.getText());
        email = String.valueOf(emailInput.getText()).trim();
        pass = String.valueOf(pwdInput.getText()).trim();

        if(email.isEmpty())
        {
            emailLayout.setError("Please enter a valid Email Address");
            flag=true;
        }
        if(pass.isEmpty())
        {
            pwdLayout.setError("Please enter a valid Password");
            flag = true;
        }

        //perform some lookup in a database and check whether the password and username entries exist and match with each other


        return !flag;
    }

    private void doLogin()
    {
        auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener( authResult ->{
//                    FirebaseUser user = authResult.getUser();
                    FirebaseUser user = authResult.getUser();

                    if(user != null)
                        if(user.isEmailVerified()) {
                            //route to dashboard
                            Toast.makeText(this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        }
                    else
                        Toast.makeText(this, "Sorry email verification failed", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
//                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Sorry "+ e.getMessage() , Toast.LENGTH_SHORT).show();
                });
    }

}




/*
ORIGINAL STUFF BEFORE I CHANGED ANYTHING

package com.example.akcsit_protikdatta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }
}


 */