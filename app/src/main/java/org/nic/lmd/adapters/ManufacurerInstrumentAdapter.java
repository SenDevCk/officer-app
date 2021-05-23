package org.nic.lmd.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.officerapp.R;
import org.nic.lmd.retrofitPojo.InstrumentManufacturePojo;
import java.util.List;

public class ManufacurerInstrumentAdapter extends RecyclerView.Adapter<ManufacurerInstrumentAdapter.MyViewHolder> {
    Activity activity;
    List<InstrumentManufacturePojo> instrumentEntities;
    LayoutInflater layoutInflater;

    public ManufacurerInstrumentAdapter(Activity activity, List<InstrumentManufacturePojo> pojoArrayList) {
        this.activity = activity;
        layoutInflater = this.activity.getLayoutInflater();
        instrumentEntities = pojoArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instrument_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        if (position==0){
            viewHolder.text_tag.setText("Instruments");
            viewHolder.text_tag.setVisibility(View.VISIBLE);
        }
        InstrumentManufacturePojo instrumentEntity = instrumentEntities.get(position);
        DataBaseHelper db = new DataBaseHelper(activity);
        viewHolder.item_firm_type.setText("" + db.getINSProByID(String.valueOf(instrumentEntity.proposal)).getName());
        viewHolder.text_cat.setText("" + db.getINSCatByID(String.valueOf(instrumentEntity.category)).getName());
        viewHolder.tv_cap.setText("" + db.getINSCapByID(String.valueOf(instrumentEntity.capacity)).getCapacityDesc());
        viewHolder.tv_quan.setText("" + instrumentEntity.totalizer);
        viewHolder.tv_cl.setText("" + db.getINSClsByID(String.valueOf(instrumentEntity.insClass)).getName());
        viewHolder.tv_bra.setText("N/A");
        viewHolder.tv_val.setText("N/A");
        viewHolder.tv_min_cap.setText("" + instrumentEntity.minCapacity);
        viewHolder.tv_max_cap.setText("" + instrumentEntity.maxCapacity);
        viewHolder.tv_mod.setText("" + instrumentEntity.model);
        viewHolder.tv_ser.setText("" + instrumentEntity.serial);
        viewHolder.tv_eval.setText("" + instrumentEntity.evalue);
        viewHolder.show_Nozzles.setVisibility(View.GONE);
        viewHolder.remove.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return instrumentEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_firm_type, text_cat, tv_cap, tv_quan, tv_cl, tv_bra, tv_val, tv_min_cap, tv_max_cap;
        public TextView tv_mod, tv_ser, tv_eval,text_tag;
        public Button remove, show_Nozzles;

        public MyViewHolder(View rootview) {
            super(rootview);
            item_firm_type = rootview.findViewById(R.id.item_firm_type);
            text_cat = rootview.findViewById(R.id.text_cat);
            tv_cap = rootview.findViewById(R.id.tv_cap);
            tv_quan = rootview.findViewById(R.id.tv_quan);
            tv_cl = rootview.findViewById(R.id.tv_cl);
            tv_bra = rootview.findViewById(R.id.tv_bra);
            tv_val = rootview.findViewById(R.id.tv_val);
            tv_min_cap = rootview.findViewById(R.id.tv_min_cap);
            tv_max_cap = rootview.findViewById(R.id.tv_max_cap);
            tv_mod = rootview.findViewById(R.id.tv_mod);
            tv_ser = rootview.findViewById(R.id.tv_ser);
            tv_eval = rootview.findViewById(R.id.tv_eval);
            show_Nozzles = rootview.findViewById(R.id.show_Nozzles);
            remove = rootview.findViewById(R.id.remove_ins);
            text_tag = rootview.findViewById(R.id.text_tag);
        }
    }

}
