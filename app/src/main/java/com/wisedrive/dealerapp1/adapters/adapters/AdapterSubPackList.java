package com.wisedrive.dealerapp1.adapters.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.PackageFragment;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSubPackList;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterSubPackList extends RecyclerView.Adapter<AdapterSubPackList.RecyclerViewHolder> {

    Context context;
    ArrayList<PojoSubPackList> subPackLists;
    private DecimalFormat IndianCurrencyFormat;
    AdapterMainPackLists adapterMainPackLists;

    public AdapterSubPackList(Context context, ArrayList<PojoSubPackList> subPackLists) {
        this.context = context;
        this.subPackLists = subPackLists;
    }

    @NonNull
    @Override
    public AdapterSubPackList.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_sub_pack_lists, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSubPackList.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {

        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        holder.tv_sub_pack_name.setText(subPackLists.get(position).getSub_package_name());

        String quant=subPackLists.get(position).getQuantity();
        String subpack=subPackLists.get(position).getSub_package_name();

        if(quant.equals("1")){
            holder.tv_sub_pack_name.setText(subpack);

        }else if(!quant.equals("1") && subpack.equalsIgnoreCase("any car")){
            holder.tv_sub_pack_name.setText(quant+"\t Bundle"+"("+subpack+")");

        }else{
            holder.tv_sub_pack_name.setText(quant+subpack);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SPHelper.selected_sub_packid=subPackLists.get(position).getSub_package_id();
                SPHelper.selected_main_pack_id=subPackLists.get(position).getMain_package_id();
                SPHelper.finalamount=Double.parseDouble(subPackLists.get(position).getFinal_price());
                SPHelper.product_id=subPackLists.get(position).getPackage_id();
                System.out.println("id"+SPHelper.product_id+SPHelper.selected_main_pack_id+
                        SPHelper.selected_sub_packid+SPHelper.finalamount+"d_id"+
                        SPHelper.getSPData(context, SPHelper.dealerid, ""));
               // make for loop of main pack list and inisde sub pack list and make it unselcted

//                for (int i=0;i<PackageFragment.getInstance().pojoMainPackLists.size();i++)
//                {
//                    for(int j=0;j<PackageFragment.getInstance().pojoMainPackLists.get(i).getPackCombList().size();j++)
//                    {
//                        if(PackageFragment.getInstance().pojoMainPackLists.get(i).getPackCombList().get(j).getIsSelected().equals("y"))
//                        {
//                            PackageFragment.getInstance().pojoMainPackLists.get(i).getPackCombList().get(j).setIsSelected("y");
//                        }
////                        else
////                        {
////                            if(j==position)
////                            {
////                                if (PackageFragment.getInstance().pojoMainPackLists.get(i).getPackCombList().get(j).getIsSelected().equals("n"))
////                                {
////                                    PackageFragment.getInstance().pojoMainPackLists.get(i).getPackCombList().get(j).setIsSelected("n");
////                                 }
////                            }
////                        }
//                    }
//                   PackageFragment.getInstance().adapterMainPackLists.notifyDataSetChanged();
//                   // notifyDataSetChanged();
//                }
               // notifyDataSetChanged();

              //  PackageFragment.getInstance().adapterMainPackLists.notifyDataSetChanged();
                System.out.println("position"+subPackLists.get(position).getIsSelected());
               if(subPackLists.get(position).getIsSelected().equals("y"))
                {
                    //subPackLists.get(position).setIsSelected("n");
                    for (int i=0;i<subPackLists.size();i++)
                    {
                        if (i == position)
                        {
                            subPackLists.get(i).setIsSelected("n");
                        }
                    }
                    PackageFragment.getInstance().price.setText("0");
                    PackageFragment.getInstance().percentage_amount_saved.setText("You saved upto "+"0"+"%");
                }
                else {
                   // subPackLists.get(position).setIsSelected("y");
                    for (int i=0;i<subPackLists.size();i++)
                    {
                        if (i == position)
                        {
                            subPackLists.get(i).setIsSelected("y");
                        }
                        else {
                            subPackLists.get(i).setIsSelected("n");
                        }
                    }
                    int x=(int)Double.parseDouble(subPackLists.get(position).getFinal_price());
                    String s = IndianCurrencyFormat.format(x);
                    PackageFragment.getInstance().price.setText(s);
                    if(subPackLists.get(position).getPercentage_amount_saved()==null){
                        PackageFragment.getInstance().percentage_amount_saved.setText("You saved upto "+"0%");
                    }else{
                        int saved=(int)Double.parseDouble(subPackLists.get(position).getPercentage_amount_saved());
                        PackageFragment.getInstance().percentage_amount_saved.setText("You saved upto "+saved+"%");
                    }
                }
                notifyDataSetChanged();
               // notifyDataSetChanged();
            }
        });

        if(subPackLists.get(position).getIsSelected().equals("n")){
            holder.tv_select.setVisibility(View.VISIBLE);
            holder.iv_buy_warranty.setVisibility(View.GONE);
            holder.tv_sub_pack_name.setTextColor(Color.parseColor("#FF000000"));
            holder.any_brand.setTextColor(Color.parseColor("#FF000000"));
            holder.rl_whole.setBackground(AppCompatResources.getDrawable(context,R.drawable.cv_black_border));

        }else{
            holder.tv_select.setVisibility(View.GONE);
            holder.iv_buy_warranty.setVisibility(View.VISIBLE);
            holder.tv_sub_pack_name.setTextColor(Color.parseColor("#FFFFFFFF"));
             holder.any_brand.setTextColor(Color.parseColor("#FFFFFFFF"));
             holder.rl_whole.setBackground(AppCompatResources.getDrawable(context,R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return subPackLists.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sub_pack_name,tv_select,any_brand;
        ImageView iv_buy_warranty;
        RelativeLayout rl_whole;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sub_pack_name=itemView.findViewById(R.id.tv_sub_pack_name);
            tv_select=itemView.findViewById(R.id.tv_select);
            iv_buy_warranty=itemView.findViewById(R.id.iv_buy_warranty);
            rl_whole=itemView.findViewById(R.id.rl_whole);
            any_brand=itemView.findViewById(R.id.any_brand);
        }
    }
}
