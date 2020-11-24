package com.example.inclass09;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    EditText emailEditTextRegister,passwordEditTextRegister;
    private FirebaseAuth mAuth;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle("Register Screen");

        emailEditTextRegister = view.findViewById(R.id.emailEditTextRegister);
        passwordEditTextRegister = view.findViewById(R.id.passwordEditTextRegister);

        view.findViewById(R.id.submitButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditTextRegister.getText().toString();
                String password = passwordEditTextRegister.getText().toString();
                if(email.isEmpty()){
                    AlertUtils.showOKDialog(getActivity(),getResources().getString(R.string.error),
                            getResources().getString(R.string.email_hint));
                }else if(password.isEmpty()){
                    AlertUtils.showOKDialog(getActivity(),getResources().getString(R.string.error),
                            getResources().getString(R.string.password_hint));
                }else {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Registration Success", Toast.LENGTH_SHORT).show();
                                mListener.loginAfterRegistering();
                            }else{
                                AlertUtils.showOKDialog(getActivity(),getResources().getString(R.string.error),
                                        task.getException().getMessage());
                            }
                        }
                    });

                }
            }
        });

        view.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goBackToLoginFrag();
            }
        });

        return view;
    }

    IRegisterListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (IRegisterListener) context;
    }

    public interface IRegisterListener{
        void goBackToLoginFrag();
        void loginAfterRegistering();
    }
}