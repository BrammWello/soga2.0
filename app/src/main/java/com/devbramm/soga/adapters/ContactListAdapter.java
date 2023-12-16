package com.devbramm.soga.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devbramm.soga.ChatMessageActivity;
import com.devbramm.soga.R;
import com.devbramm.soga.models.ChatItemList;
import com.devbramm.soga.models.ContactItemList;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    Context context;
    ArrayList<ContactItemList> contactItemList;
    private OnContactSelectedListener onContactSelectedListener;

    public ContactListAdapter(Context context, ArrayList<ContactItemList> contactItemList) {
        this.context = context;
        this.contactItemList = contactItemList;
    }

    @NonNull
    @Override
    public ContactListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.new_contact_message_layout_item, parent, false);
        return new ContactListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ViewHolder holder, int position) {
        holder.contactListItemName.setText(contactItemList.get(position).getContactName());
        holder.contactListItemAbout.setText(contactItemList.get(position).getContactPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the ChatMessageActivity
                Intent intent = new Intent(context, ChatMessageActivity.class);

                // Pass the contact name to the next activity
                intent.putExtra("CONTACT_PHONE", contactItemList.get(position).getContactPhone());

                // Start the activity
                context.startActivity(intent);

                // Notify the listener if needed
                if (onContactSelectedListener != null) {
                    onContactSelectedListener.onContactSelected();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView contactListItemName, contactListItemAbout;
        public ViewHolder(View itemView) {
            super(itemView);

            contactListItemName = itemView.findViewById(R.id.contact_list_item_name); // Replace with the actual ID
            contactListItemAbout = itemView.findViewById(R.id.contact_list_item_about); // Replace with the actual ID
        }
    }


    public void setOnContactSelectedListener(OnContactSelectedListener listener) {
        this.onContactSelectedListener = listener;
    }
    public interface OnContactSelectedListener {
        void onContactSelected();
    }
}
