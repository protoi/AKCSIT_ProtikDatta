package com.example.akcsit_protikdatta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akcsit_protikdatta.databinding.ActivitySignUpBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    TextInputLayout nameLayout, emailLayout, pwdLayout, phoneLayout;
    TextInputEditText nameInput, emailInput, pwdInput, phoneInput;
    TextView optionSignIn;
    Button btnSignUp;
    RadioGroup radioGroup;
    String name, gender, contact, email, pass;
    FirebaseAuth auth;
    FirebaseFirestore fs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
//      setContentView(R.layout.activity_sign_up);

        setContentView(binding.getRoot());
        nameLayout = binding.layoutUname;
        nameInput = binding.inputUname;

        emailLayout = binding.layoutEmail;
        emailInput = binding.inputEmail;

        pwdLayout = binding.layoutPassword;
        pwdInput = binding.inputPassword;

        phoneLayout = binding.layoutPhone;
        phoneInput = binding.inputPhone;

        radioGroup = binding.gender;

        btnSignUp = binding.btnSubmit;

        optionSignIn = binding.signOpt;

        //firebase authentication instance creation
        auth = FirebaseAuth.getInstance();

        //firestore instance

        fs = FirebaseFirestore.getInstance();

        //to get data from radio button under radio group

        radioGroup.setOnCheckedChangeListener((radioGroup1, i) ->{
            if(radioGroup1.getCheckedRadioButtonId() == binding.genderMale.getId())
                gender = getString(R.string.male);
            else if(radioGroup1.getCheckedRadioButtonId() == binding.genderFemale.getId())
                gender = getString(R.string.female);
            else
                gender = getString(R.string.others);
        });

        optionSignIn.setOnClickListener(view -> {
            //route to the login or sign in screen that you have added to the project

            Intent in = new Intent(SignUpActivity.this, SignInActivity.class); //message passing object
            startActivity(in);
            SignUpActivity.this.finish(); //destroy the lifecycle of the activity

        });
        btnSignUp.setOnClickListener(view -> {
            if (validateFields()) {
                doRegister();
//                Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

        private void storeInfo(String usrId) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("name", name);
            userMap.put("email", email);
            userMap.put("contact", contact);
            userMap.put("gender", gender);

            fs.collection("user_info")
                    .document(usrId)
                    .set(userMap)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    })
                    .addOnFailureListener( e-> {
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }


        public void doRegister()
        {
            auth.createUserWithEmailAndPassword(email, pass)
                    .addOnSuccessListener(task -> {
                        if(auth.getCurrentUser()!= null)
                        {
                            String usrID = auth.getUid();
                            //to verify email
                            auth.getCurrentUser().sendEmailVerification();
                            storeInfo(usrID);
                        }
                        else
                            Toast.makeText(this, "Try Later", Toast.LENGTH_SHORT).show();
                    }) //to detect the process completion status
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Sorry "+e.getMessage(), Toast.LENGTH_SHORT ).show();
                    });
        }

        public boolean validateFields()
        {
            boolean flag = false;

            pwdLayout.setError("");
            phoneLayout.setError("");
            nameLayout.setError("");
            emailLayout.setError("");

            name = String.valueOf(nameInput.getText()).trim();
            email = String.valueOf(emailInput.getText()).trim();
            contact = String.valueOf(phoneInput.getText()).trim();
            pass = String.valueOf(pwdInput.getText()).trim();
//            performing validations

            if (gender == null) {
                Toast.makeText(SignUpActivity.this, "choose gender", Toast.LENGTH_SHORT).show();
                flag= true;
            }
             if (name.isEmpty()) {
                nameLayout.setError("Specify the username");
                flag = true;
            }
             if (email.isEmpty())
            {
                emailLayout.setError("Email can't be empty");
                flag = true;
            }
             if (contact.length() != 10 )
            {
                phoneLayout.setError("Contact must be of 10 or 8 character length");
                flag = true;
            }
            if(pass.isEmpty()) {
                pwdLayout.setError("Password can't be empty");
                flag=true;
            }

            return !flag;
        }


}

