package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.wisedrive.dealerapp1.adapters.adapters.Adapter_features;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_leads_page;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_features;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_leads_page;

import java.util.ArrayList;

public class Leads_page extends AppCompatActivity {
    RecyclerView rv_leads_list;
    ArrayList<Pojo_leads_page>pojo_leads_pageArrayList;
    Adapter_leads_page adapter_leads_page;
    RelativeLayout rl_back;

    private static Leads_page instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads_page);
        rv_leads_list=findViewById(R.id.rv_leads_list);
        rl_back=findViewById(R.id.rl_back);
        instance=this;


        pojo_leads_pageArrayList= new ArrayList<>();
        pojo_leads_pageArrayList.add(new Pojo_leads_page("Maheen","6361131647","23 Apr 23, ","11:45","AM"));
        pojo_leads_pageArrayList.add(new Pojo_leads_page("Maheen","6361131647","23 Apr 23, ","11:45","AM"));
        pojo_leads_pageArrayList.add(new Pojo_leads_page("Maheen","6361131647","23 Apr 23, ","11:45","AM"));
        pojo_leads_pageArrayList.add(new Pojo_leads_page("Maheen","6361131647","23 Apr 23, ","11:45","AM"));
        pojo_leads_pageArrayList.add(new Pojo_leads_page("Maheen","6361131647","23 Apr 23, ","11:45","AM"));
        pojo_leads_pageArrayList.add(new Pojo_leads_page("Maheen","6361131647","23 Apr 23, ","11:45","AM"));
        pojo_leads_pageArrayList.add(new Pojo_leads_page("Maheen","6361131647","23 Apr 23, ","11:45","AM"));
        pojo_leads_pageArrayList.add(new Pojo_leads_page("Maheen","6361131647","23 Apr 23, ","11:45","AM"));
        adapter_leads_page = new Adapter_leads_page(this,pojo_leads_pageArrayList);
        GridLayoutManager layoutManager1 = new GridLayoutManager(Leads_page.this, 1);
        rv_leads_list.setLayoutManager(layoutManager1);
        rv_leads_list.setAdapter(adapter_leads_page);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static Leads_page getInstance () {
        return instance;
    }
}
