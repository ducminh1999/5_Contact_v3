package com.example.a5_contact_v3.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a5_contact_v3.R;

import java.util.List;

public class CustomAdapter<C> extends ArrayAdapter<Contact> {

    private static final String TAG = null ;
    private Context context;
    private int resource;
    private List<Contact> arrayContact;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Contact> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrayContact = arrContact;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.row_list_view, parent, false);
            viewHolder.imgviewAvt = (ImageView) convertView.findViewById(R.id.imgview_avt);
            viewHolder.imgviewCall = (ImageView) convertView.findViewById(R.id.imgview_call);
            viewHolder.tvInfor = (TextView) convertView.findViewById(R.id.tv_infor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = arrayContact.get(position);
        viewHolder.tvInfor.setText(contact.getmName());
//        viewHolder.imgAvt.setBackgroundResource(contact.getmAvt());
//        viewHolder.imgAvt.setText(String.valueOf(position+1));


//        viewHolder.imgviewAvt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("debug1", "Viewholder");
//                Intent intent = new Intent(context, MainEditContactActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("contact", (Serializable) arrayContact.get(position));
//                intent.putExtra("package", bundle);
//                context.startActivity(intent);
//            }
//        });
        viewHolder.imgviewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = arrayContact.get(position).getmPhone().toString();
                Log.d("debug1", phoneNo);
                if(!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }else {
                    Toast.makeText(context, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }
    public class ViewHolder {
        ImageView imgviewAvt;
        TextView tvInfor;
        ImageView imgviewCall;

    }
}

