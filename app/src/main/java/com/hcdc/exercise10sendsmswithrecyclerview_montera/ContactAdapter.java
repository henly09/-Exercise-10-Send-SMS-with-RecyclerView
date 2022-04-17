package com.hcdc.exercise10sendsmswithrecyclerview_montera;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactView> {

    String namesadapter[];
    String emailadapter[];
    String phonenumberadapter[];
    Context ContactContext;

    public ContactAdapter(Context context, String[] names, String[] email, String[] phonenumber){
        ContactContext = context;
        phonenumberadapter = phonenumber;
        emailadapter = email;
        namesadapter = names;
    }

    @NonNull
    @Override
    public ContactView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ContactContext);
        View view = inflater.inflate(R.layout.contact_adapt, parent, false);
        return new ContactView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactView holder, int position) {
        holder.nameview.setText(namesadapter[position]);
        holder.phonenumberview.setText(phonenumberadapter[position]);
        holder.emailview.setText(emailadapter[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactContext, sendpage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("phonenumber", holder.phonenumberview.getText().toString());
                ContactContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return namesadapter.length;
    }

    public class ContactView extends RecyclerView.ViewHolder {

        TextView nameview,phonenumberview,emailview;

        public ContactView(@NonNull View itemView) {
            super(itemView);
            nameview = itemView.findViewById(R.id.namerview);
            phonenumberview = itemView.findViewById(R.id.phonerview);
            emailview = itemView.findViewById(R.id.emailrview);

        }


    }
}
