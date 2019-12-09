package com.xd.drivesafe.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.squareup.picasso.Picasso;
import com.xd.drivesafe.Models.UserModel;
import com.xd.drivesafe.R;
import com.xd.drivesafe.User.DriverProfileUserActivity;

import java.util.ArrayList;
import java.util.List;

public class UserDriversAdapter extends RecyclerView.Adapter<UserDriversAdapter.MyViewHolder> {

    Context context;

    List<UserModel> userModelList;

    public UserDriversAdapter(Context context, List<UserModel> userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending_driver_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        UserModel userModel = userModelList.get(position);
        holder.lince.setText(userModel.getLicense());
        holder.username.setText(userModel.getName());
        holder.createat.setText(TimeAgo.from(userModel.getCreate_at()));
        Picasso.get().load(userModel.getImage()).into(holder.pro);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DriverProfileUserActivity.class);
                intent.putExtra("key",userModel.getUserId());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }


    public void setfilter(List<UserModel> itemData) {
        userModelList = new ArrayList<>();
        userModelList.addAll(itemData);
        notifyDataSetChanged();

    }



    public  class  MyViewHolder  extends RecyclerView.ViewHolder {


        ImageView pro;
        TextView createat;
        TextView username,lince;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pro = itemView.findViewById(R.id.itemmain_imgm);

            createat = itemView.findViewById(R.id.item_createAt_ID);

            username = itemView.findViewById(R.id.item_Driver_Namae_Id);
            lince = itemView.findViewById(R.id.item_Driver_license_Id);


        }
    }

}
