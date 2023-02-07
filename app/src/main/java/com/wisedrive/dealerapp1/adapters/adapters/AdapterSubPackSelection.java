package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.BuyBackGuarantee;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.PackageFragment;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAddOnComboList;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterSubPackSelection extends RecyclerView.Adapter<AdapterSubPackSelection.RecyclerViewHolder> {

    ArrayList<PojoAddOnComboList> pojoAddOnComboLists;
    Context context;

    private DecimalFormat IndianCurrencyFormat;
    public AdapterSubPackSelection(ArrayList<PojoAddOnComboList> pojoAddOnComboLists, Context context) {
        this.pojoAddOnComboLists = pojoAddOnComboLists;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSubPackSelection.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.items_add_on_combo_list,parent,false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSubPackSelection.RecyclerViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        PojoAddOnComboList recyclerdata=pojoAddOnComboLists.get(position);
        holder.sub_pack_name.setText(recyclerdata.getSub_package_name());

        if(pojoAddOnComboLists.get(position).getIsSelected().equals("n")){
            holder.iv_selected.setVisibility(View.GONE);
        }else{
            holder.iv_selected.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.add_on_Main_pack_id=pojoAddOnComboLists.get(position).getMain_package_id();
                SPHelper.add_on_sub_pack_id=pojoAddOnComboLists.get(position).getSub_package_id();

                for (int i=0;i<pojoAddOnComboLists.size();i++)
                {
                    if (i == position)
                    {
                        pojoAddOnComboLists.get(i).setIsSelected("y");
                    } else {
                        pojoAddOnComboLists.get(i).setIsSelected("n");
                    }
                }
                notifyDataSetChanged();
                int x=(int)Double.parseDouble(pojoAddOnComboLists.get(position).getFinal_price());
                String s = IndianCurrencyFormat.format(x);
                BuyBackGuarantee.getInstance().price.setText(s);
                SPHelper.add_onprice=x;
                if(pojoAddOnComboLists.get(position).getPercentage_amount_saved()==null){

                }else{
                    int saved=(int)Double.parseDouble(pojoAddOnComboLists.get(position).getPercentage_amount_saved());
                    BuyBackGuarantee.getInstance().saved_amount.setText("You saved upto "+saved+"%");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pojoAddOnComboLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView sub_pack_name;
        RelativeLayout rl_select;
        ImageView iv_selected;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_selected=itemView.findViewById(R.id.iv_selected);
            rl_select=itemView.findViewById(R.id.rl_select);
            sub_pack_name=itemView.findViewById(R.id.sub_pack_name);
        }
    }
}
