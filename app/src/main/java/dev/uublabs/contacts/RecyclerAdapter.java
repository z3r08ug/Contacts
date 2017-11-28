package dev.uublabs.contacts;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Admin on 11/27/2017.
 */

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    List<Contact> contacts;

    public RecyclerAdapter(List<Contact> contacts)
    {
        this.contacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position)
    {
        Contact contact = contacts.get(position);
        if(contact != null)
        {
            holder.tvContactFirstName.setText(contact.getFirstname());
            holder.tvContactLastName.setText(contact.getLastName());
        }
    }

    @Override
    public int getItemCount()
    {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView tvContactFirstName;
        private final TextView tvContactLastName;
        public ViewHolder(View itemView)
        {
            super(itemView);
            tvContactFirstName = itemView.findViewById(R.id.tvContactFirstName);
            tvContactLastName = itemView.findViewById(R.id.tvContactLastName);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String first = contacts.get(getAdapterPosition()).getFirstname();
                    String last = contacts.get(getAdapterPosition()).getLastName();
                    String phone = contacts.get(getAdapterPosition()).getPhoneNumber();
                    String email = contacts.get(getAdapterPosition()).getEmail();
                    Intent intent = new Intent(v.getContext(), ContactInfoActivity.class);
                    intent.putExtra("first", first);
                    intent.putExtra("last" , last);
                    intent.putExtra("phone", phone);
                    intent.putExtra("email", email);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
