package com.technercia.supercallrecorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technercia.supercallrecorder.R;
import com.technercia.supercallrecorder.contacts.ContactProvider;
import com.technercia.supercallrecorder.pojo_classes.Contacts;
import com.technercia.supercallrecorder.utils.StringUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class IncommingAdapter extends RecyclerView.Adapter{
    private static ArrayList<Object> contacts=new ArrayList<>();
    private final int VIEW1 = 0, VIEW2 = 1,VIEW3=3;
    static IncommingAdapter.itemClickListener listener;
    Context ctx;
    public IncommingAdapter(){

    }

    public void setListener(itemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder1;
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case VIEW1:
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.people_contact,parent,false);
                viewHolder1 = new IncommingAdapter.MyViewHolder(view);
                ctx=view.getContext();
                break;
            case VIEW2:
                View v2 = inflater.inflate(R.layout.no_contact_list,parent, false);
                viewHolder1= new IncommingAdapter.MyViewHolder(v2);
                ctx=v2.getContext();
                break;
            case VIEW3:
                View v3=inflater.inflate(R.layout.time_row,parent,false);
                viewHolder1=new MytimeViewHolder2(v3);
                ctx=v3.getContext();
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder1 = new IncommingAdapter.MyViewHolder(v);
                ctx=v.getContext();
                break;
        }
        return  viewHolder1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW1:
                Contacts contact= (Contacts) contacts.get(position);
                String Phonnumber= StringUtils.prepareContacts(ctx,contact.getNumber());
                if(ContactProvider.checkFav(ctx,Phonnumber)){
                    //not favourite
                    ((MyViewHolder)holder).favorite.setImageResource(R.drawable.ic_star_border_black_24dp);
                }else{
                    //favourite
                    ((MyViewHolder)holder).favorite.setImageResource(R.drawable.ic_star_black_24dp);
                }
                if(ContactProvider.checkContactToRecord(ctx,Phonnumber)){
                    //record
                    ((MyViewHolder)holder).state.setImageResource(R.drawable.ic_microphone);
                }else{
                    //dont wanna record
                    ((MyViewHolder)holder).state.setImageResource(R.drawable.ic_muted);
                }
                ((MyViewHolder)holder).name.setText(contact.getName());
                ((MyViewHolder)holder).number.setText(contact.getNumber());
                if(contact.getPhotoUri()!=null){
                    Picasso.with(ctx)
                            .load(contact.getPhotoUri()).placeholder(R.drawable.profile)
                            .into(((MyViewHolder) holder).profileimage);
                }else {
                    ((MyViewHolder)holder).profileimage.setImageResource(R.drawable.profile);
                }
                ((MyViewHolder)holder).time.setText(contact.getTime());
                break;
            case VIEW2:
                Contacts contact3= (Contacts) contacts.get(position);
                String phonenumber=StringUtils.prepareContacts(ctx,contact3.getNumber());
                if(ContactProvider.checkFav(ctx,phonenumber)){
                    //not favourite
                    ((MyViewHolder)holder).favorite.setImageResource(R.drawable.ic_star_border_black_24dp);
                }else{
                    //favourite
                    ((MyViewHolder)holder).favorite.setImageResource(R.drawable.ic_star_black_24dp);
                }
                if(ContactProvider.checkContactToRecord(ctx,phonenumber)){
                    //record
                    ((MyViewHolder)holder).state.setImageResource(R.drawable.ic_microphone);
                }else{
                    //dont wanna record
                    ((MyViewHolder)holder).state.setImageResource(R.drawable.ic_muted);
                }
                ((MyViewHolder)holder).name.setText(contact3.getNumber());
                ((MyViewHolder)holder).time.setText(contact3.getTime());
                break;
            case VIEW3:
                String time=contacts.get(position).toString();
                if(time=="1"){
                    ((MytimeViewHolder2)holder).time.setText("Today");
                }else if(time=="2"){
                    ((MytimeViewHolder2)holder).time.setText("Yesterday");
                }else{
                    ((MytimeViewHolder2)holder).time.setText(time);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return IncommingAdapter.contacts.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profileimage;
        TextView name;
        TextView number;
        TextView time;
        ImageView state,favorite;
        public MyViewHolder(final View itemView) {
            super(itemView);
            profileimage=(CircleImageView)itemView.findViewById(R.id.profile_image);
            name=(TextView)itemView.findViewById(R.id.textView2);
            number=(TextView)itemView.findViewById(R.id.textView3);
            time=(TextView)itemView.findViewById(R.id.textView4);
            state=(ImageView)itemView.findViewById(R.id.imageView5);
            favorite=(ImageView)itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view,getAdapterPosition());
                }
            });
        }

    }
    public static class MytimeViewHolder2 extends RecyclerView.ViewHolder{
        TextView time;
        public MytimeViewHolder2(View itemView) {
            super(itemView);
            time=(TextView)itemView.findViewById(R.id.time_view);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if(contacts.get(position) instanceof String){
            return VIEW3;
        }else{
            if(contacts.get(position) instanceof Contacts){
                Contacts contxt= (Contacts) contacts.get(position);
                if(contxt.getName()!=null){
                    return VIEW1;
                }else{
                    return VIEW2;
                }
            }else {
                return VIEW1;
            }
        }
    }
    public void setContacts(ArrayList<Object> contacts){
        IncommingAdapter.contacts=contacts;
    }

    public interface itemClickListener{
        public void onClick(View v,int position);
    }
}