package com.example.inclass09;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactsListFragment.IContactListListener, AddContactFragment.IAddContactListener,
        ContactDetailFragment.IDetailsFragListener, LoginFragment.ILoginListener, RegisterFragment.IRegisterListener {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth= FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentView, new LoginFragment())
                    .commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentView, new ContactsListFragment())
                    .commit();
        }



    }

    @Override
    public void logOutSession() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new LoginFragment())
                .commit();
    }

    @Override
    public void goToAddContactFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new AddContactFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void SendDataToDisplay(Contacts contacts) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new ContactDetailFragment(contacts))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToListFragFromAdd() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void closeDetailsFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goToRegisterFrag() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new RegisterFragment())
                .commit();
    }

    @Override
    public void goBackToLoginFrag() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new LoginFragment())
                .commit();
    }

    @Override
    public void loginAfterRegistering() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new LoginFragment())
                .commit();
    }

    @Override
    public void displayListAfterLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new ContactsListFragment())
                .commit();
    }
}