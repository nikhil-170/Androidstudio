package com.example.inclass09;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ContactDetailFragment extends Fragment {

    Contacts contacts;
    TextView nameTV, emailTV, phoneTV, phoneTypeTV;

    public ContactDetailFragment(Contacts contacts) {
        this.contacts = contacts;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);
        getActivity().setTitle("Contact Details");

        nameTV = view.findViewById(R.id.nameTV);
        emailTV = view.findViewById(R.id.emailTV);
        phoneTV = view.findViewById(R.id.phoneTV);
        phoneTypeTV = view.findViewById(R.id.phoneTypeTV);

        nameTV.setText(contacts.name);
        emailTV.setText(contacts.email);
        phoneTV.setText(contacts.phone);
        phoneTypeTV.setText(contacts.phoneType);

        view.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.closeDetailsFragment();
            }
        });



        return view;
    }

    public void deleteData(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("contacts").document(contacts.id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mListener.closeDetailsFragment();
                    }
                });
    }

    IDetailsFragListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (IDetailsFragListener) context;
    }

    public interface IDetailsFragListener{
        void closeDetailsFragment();
    }

}