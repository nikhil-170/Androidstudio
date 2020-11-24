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

public class LoginFragment extends Fragment {

    EditText emailEditText,passwordEditText;
    private FirebaseAuth mAuth;

    public LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("Login Screen");
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);


        view.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(email.isEmpty()){
                    AlertUtils.showOKDialog(getActivity(),getResources().getString(R.string.error),
                            getResources().getString(R.string.email_hint));
                }else if(password.isEmpty()){
                    AlertUtils.showOKDialog(getActivity(),getResources().getString(R.string.error),
                            getResources().getString(R.string.password_hint));
                }else {

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(),"Login Success", Toast.LENGTH_SHORT).show();
                                mListener.displayListAfterLogin();

                            } else {
                                AlertUtils.showOKDialog(getActivity(),getResources().getString(R.string.error),
                                        task.getException().getMessage());
                            }


                        }
                    });
                }
            }
        });

        view.findViewById(R.id.cancelLoginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToRegisterFrag();
            }
        });


        return view;
    }

    ILoginListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ILoginListener) context;
    }

    public interface ILoginListener{
        void goToRegisterFrag();
        void displayListAfterLogin();
    }
}