package org.nic.lmd.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nic.lmd.filehandling.PdfOpenHelper;
import org.nic.lmd.officerapp.LoadUrlActivity;
import org.nic.lmd.officerapp.R;
import org.nic.lmd.retrofitPojo.DocPoso;

import java.util.ArrayList;

 /**
 * Created by chandan on 27.01.2021
 */

public class ManufacurerDocRecyclerAdapter extends RecyclerView.Adapter<ManufacurerDocRecyclerAdapter.MyViewHolder> {

    private ArrayList<DocPoso> jsonArray;
    Activity activity;
    private int lastPosition = -1;
    int count_for_ins;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_head_doc,text_doc_name;
        public MyViewHolder(View view) {
            super(view);
            text_head_doc =  view.findViewById(R.id.text_head_doc);
            text_doc_name =  view.findViewById(R.id.text_doc_name);
        }
    }


    public ManufacurerDocRecyclerAdapter(ArrayList<DocPoso> jsonArray, Activity activity) {
        this.jsonArray = jsonArray;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doc_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            DocPoso obj=jsonArray.get(position);
            if (position==0) {
                holder.text_head_doc.setVisibility(View.VISIBLE);
                if (jsonArray.size()>0) {
                    holder.text_head_doc.setText("Uploaded Documents :-");
                }else{
                    holder.text_head_doc.setText("Documents not available");
                }
            }else{
                holder.text_head_doc.setVisibility(View.GONE);
            }
            SpannableString content = new SpannableString(String.valueOf(obj.documentId));
            content.setSpan(new UnderlineSpan(), 0,content.length(), 0);
            String[] tokens=obj.docUrl.split("/");
            holder.text_doc_name.setText(content+" "+tokens[tokens.length-1]);
            holder.text_doc_name.setOnClickListener(v -> {
                    //activity.startActivity(new Intent(activity, LoadUrlActivity.class).putExtra("url","http://192.168.1.20:65001/app"+obj.docUrl));
                try {
                    PdfOpenHelper.openPdfFromUrl("http://192.168.0.151:65000/api" + obj.docUrl, activity);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });

            setAnimation(holder.itemView, position);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return jsonArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
                     Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
                     viewToAnimate.startAnimation(animation);
                     lastPosition = position;
        }
    }
}