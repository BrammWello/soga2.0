package com.devbramm.soga.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devbramm.soga.R;
import com.devbramm.soga.models.ChatItemList;
import com.devbramm.soga.models.ContactItemList;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.Viewholder> {

    Context context;
    ArrayList<ContactItemList> contactItemList;

    public ContactListAdapter(Context context, ArrayList<ContactItemList> contactItemList) {
        this.context = context;
        this.contactItemList = contactItemList;
    }

    @NonNull
    @Override
    public ContactListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.new_contact_message_layout_item, parent, false);
        return new ContactListAdapter.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return contactItemList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
