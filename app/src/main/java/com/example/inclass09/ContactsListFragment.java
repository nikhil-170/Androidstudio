package com.example.inclass09;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactsListFragment extends Fragment {

    ListView listView;
    ContactAdapter adapter;
    ArrayList<Contacts> contactList = new ArrayList<>();

    public ContactsListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);
        getActivity().setTitle("Contacts List Screen");

        listView = view.findViewById(R.id.listView);
        adapter = new ContactAdapter(getActivity(),R.layout.contact_row_item,contactList);
        listView.setAdapter(adapter);

        getData();


        view.findViewById(R.id.addContactButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToAddContactFragment();
            }
        });

        view.findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                mListener.logOutSession();
            }
        });


        return view;
    }

    public static void deleteData(Contacts contacts){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("contacts").document(contacts.id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
    }


    public static IContactListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (IContactListListener) context;
    }

    public interface IContactListListener{
        void logOutSession();
        void goToAddContactFragment();
        void SendDataToDisplay(Contacts contacts);
    }

    private void getData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("contacts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        contactList.clear();
                        for(QueryDocumentSnapshot document: value){
                            contactList.add(new Contacts(document.getId(), document.getString("name"), document.getString("email"),
                                    document.getString("phone"), document.getString("phoneType")));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }


}