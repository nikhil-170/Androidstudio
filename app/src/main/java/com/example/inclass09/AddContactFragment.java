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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddContactFragment extends Fragment {

    EditText nameET, emailET, phoneET, phoneTypeET;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    HashMap<String,String> addContact = new HashMap<>();

    public AddContactFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        getActivity().setTitle("Add New Contact");

        nameET = view.findViewById(R.id.nameET);
        emailET = view.findViewById(R.id.emailET);
        phoneET = view.findViewById(R.id.phoneET);
        phoneTypeET = view.findViewById(R.id.phoneTypeET);

        view.findViewById(R.id.submitAddButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nameET.getText().toString().matches("[a-zA-Z\\s]*")) {
                    AlertUtils.showOKDialog(getActivity(),getResources().getString(R.string.error),
                            getResources().getString(R.string.name_hint));
                }else if(!emailET.getText().toString().matches("^[A-Za-z0-9+_.-]+@[a-zA-Z0-9]+\\.[a-zA-Z]+$")){
                    AlertUtils.showOKDialog(getActivity(),getResources().getString(R.string.error),
                            getResources().getString(R.string.email_hint));
                }else if(!phoneET.getText().toString().matches("[0-9]*")){
                    AlertUtils.showOKDialog(getActivity(),getResources().getString(R.string.error),
                            getResources().getString(R.string.phone_hint));
                }else if(!phoneTypeET.getText().toString().matches("[a-zA-Z]*")){
                    AlertUtils.showOKDialog(getActivity(),getResources().getString(R.string.error),
                            getResources().getString(R.string.phone_type_hint));
                }else{
                    addContact.clear();
                    addContact.put("name",nameET.getText().toString());
                    addContact.put("email",emailET.getText().toString());
                    addContact.put("phone",phoneET.getText().toString());
                    addContact.put("phoneType",phoneTypeET.getText().toString());

                    db.collection("contacts")
                            .add(addContact)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    AlertUtils.showOKDialog(getActivity(),getResources().getString(R.string.success),
                                            getResources().getString(R.string.contact_created));
                                    mListener.goToListFragFromAdd();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    AlertUtils.showOKDialog(getActivity(),getResources().getString(R.string.error), e.getMessage());
                                }
                            });
                }
            }
        });

        view.findViewById(R.id.cancelButtonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToListFragFromAdd();
            }
        });

        return view;
    }

    IAddContactListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (IAddContactListener) context;
    }

    public interface IAddContactListener{
        void goToListFragFromAdd();
    }
}