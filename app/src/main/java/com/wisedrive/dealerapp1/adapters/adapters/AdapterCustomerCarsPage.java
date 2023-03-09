package com.wisedrive.dealerapp1.adapters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.wisedrive.dealerapp1.AllCarsPage;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.CustomerFragment;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarsPage;
import java.util.ArrayList;

public class AdapterCustomerCarsPage extends RecyclerView.Adapter<AdapterCustomerCarsPage.RecyclerViewHolder> {

    ArrayList<PojoAllCarsPage> allCarsPages;
    Context context;
    public AdapterCustomerCarsPage(ArrayList<PojoAllCarsPage> allCarsPages, Context context) {
        this.allCarsPages = allCarsPages;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterCustomerCarsPage.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_new_all_cars_list,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCustomerCarsPage.RecyclerViewHolder holder, int position)
    {

        holder.tv_insp_on_date.setText(allCarsPages.get(position).getInsp_on_date());
        String id=allCarsPages.get(position).getId();
         if(SPHelper.comingfrom.equals("customer"))
         {
            holder.iv_edit.setVisibility(View.INVISIBLE);
            holder.rl_cv.setBackground(AppCompatResources.getDrawable(context,R.drawable.cv_allcars));
            holder.tv_cust_name.setTextColor(ContextCompat.getColorStateList(context, R.color.white));
            holder.rl_sold_label.setVisibility(View.VISIBLE);
            holder.rl_sold_label.setBackground(AppCompatResources.getDrawable(context,R.drawable.cv_soldcars));
            holder.rl_sold_label.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.line2));
            holder.tv_inspect_status.setText("SOLD");
            holder.tv_inspect_status.setBackground(context.getDrawable(R.drawable.cardview_act_code));
            holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.ab_green));
            holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(context, R.color.ab_green));
        }

        else{
            holder.iv_edit.setVisibility(View.VISIBLE);
            holder.rl_cv.setBackground(context.getDrawable(R.drawable.cv_all_cars));
            holder.rl_sold_label.setVisibility(View.GONE);
           // holder.insp_status.setText(allCarsPages.get(position).getInsp_status());
            if(id.equals("1")){
                //req #1199246
                holder.tv_inspect_status.setBackground(context.getDrawable(R.drawable.cardview_act_code));
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.req_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(context,R.color.req_clr));
            }else if(id.equals("2")){
                //appr #7517479
                holder.tv_inspect_status.setBackground(context.getDrawable(R.drawable.cardview_act_code));
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.app_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(context,R.color.app_clr));
            }else if(id.equals("3")){
                //exp 2446754
                holder.tv_inspect_status.setBackground(context.getDrawable(R.drawable.cardview_act_code));
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.exp_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(context,R.color.exp_clr));
            }else if(id.equals("4")){
                //reinsp 2550118
                holder.tv_inspect_status.setBackground(context.getDrawable(R.drawable.cardview_act_code));
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.re_insp_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(context,R.color.re_insp_clr));
            }else {
                //exp=repair req
                holder.tv_inspect_status.setBackground(context.getDrawable(R.drawable.cardview_act_code));
                holder.tv_inspect_status.setBackgroundTintList(ContextCompat.getColorStateList(context,R.color.rep_req_clr));
                holder.tv_insp_on_date.setTextColor(ContextCompat.getColorStateList(context,R.color.rep_req_clr));
            }
        }


    }

    @Override
    public int getItemCount() {
        return allCarsPages.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_inspect_status,tv_insp_on_date,tv_cust_name;
        ImageView iv_edit;
        RelativeLayout rl_sold_label,rl_cv,rl_photos;
        View v3;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_inspect_status=itemView.findViewById(R.id.tv_inspect_status);
            tv_insp_on_date=itemView.findViewById(R.id.tv_insp_on_date);
            rl_sold_label=itemView.findViewById(R.id.rl_sold_label);
            rl_cv=itemView.findViewById(R.id.rl_cv);
            iv_edit=itemView.findViewById(R.id.iv_edit);
            rl_photos=itemView.findViewById(R.id.rl_photos);
            tv_cust_name=itemView.findViewById(R.id.tv_cust_name);
        }
    }
}
