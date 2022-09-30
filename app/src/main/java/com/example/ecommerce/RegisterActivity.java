package com.example.ecommerce;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });

        private void CreateAccount(){
            String name = InputName.getText().toString();
            String phone = InputPhoneNumber.getText().toString();
            String password = InputPassword.getText().toString();

            if (TextUtils.isEmpty(name)){
                Toast.makeText(this, "Veuillez entrer un nom...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(phone)){
                Toast.makeText(this, "Veuillez entrer un numéro de téléphone...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)){
                Toast.makeText(this, "Veuillez entrer un mot de passe...", Toast.LENGTH_SHORT).show();
            } else {
                loadingBar.setTitle("Créer un compte");
                loadingBar.setMessage("Merci de patienter");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                ValidatePhoneNumber (name, phone, password);

            }

        }


    }

    private void ValidatePhoneNumber(String name, String phone, String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Users").child(phone).exists())){


                } else {
                    Toast.makeText(RegisterActivity.this, "This " + phone + " existe déjà.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Essayez un autre numéro de téléphone ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}