package com.ucb.appin.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ucb.appin.R;
import com.ucb.appin.data.model.TransaccionAviso;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionAvisosAdapter extends RecyclerView.Adapter<TransactionAvisosAdapter.CardViewHolder> {
    private final Context mainContext;
    private final List<TransaccionAviso> items;

    public TransactionAvisosAdapter(Context mainContext, List<TransaccionAviso> items) {
        this.mainContext = mainContext;
        this.items = items;
    }

    @Override
    public TransactionAvisosAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_row_trans_aviso,parent,false);
        return new TransactionAvisosAdapter.CardViewHolder(v,mainContext,items);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        TransaccionAviso item = items.get(position); //get item from my List<Place>
        holder.itemView.setTag(item); //save items in Tag

        holder.name.setText(item.getNombre());
        holder.select.setChecked(item.isSeleccionado());
        holder.select.setTag(item);

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransaccionAviso tipoAviso = (TransaccionAviso) v.getTag();
                selectedItem(tipoAviso);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransaccionAviso tipoAviso = (TransaccionAviso) v.getTag();
                holder.select.setChecked(!tipoAviso.isSeleccionado());
                selectedItem(tipoAviso);
            }
        });
    }

    public void selectedItem(TransaccionAviso tipoAviso) {
        for(int i = 0;i < this.items.size();i++){
            if(this.items.get(i).getId() == tipoAviso.getId()){
                this.items.get(i).setSeleccionado(!tipoAviso.isSeleccionado());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if(items != null){
            size = items.size();
        }
        return size;
    }

    public String getItems() {
        String result = "";
        for(int i = 0;i < items.size();i++){
            if(items.get(i).isSeleccionado()){
                result += items.get(i).getId() + ",";
            }
        }

        if(result.length() > 0){
            result = result.substring(0, result.lastIndexOf(","));
        }
        return result;
    }

    public List<TransaccionAviso> getDataItems() {
        return items;
    }

    //class static
    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.row_nom_trans)
        TextView name;

        @BindView(R.id.row_sel_trans)
        CheckBox select;

        private Context context;
        private List<TransaccionAviso> items = new ArrayList<TransaccionAviso>();

        public CardViewHolder(View v, Context context, List<TransaccionAviso> items){
            super(v);
            this.context = context;
            this.items = items;
            ButterKnife.bind(this, v); //with butterKnife

            this.name = v.findViewById(R.id.row_nom_trans);
            this.select = v.findViewById(R.id.row_sel_trans);

            v.setOnClickListener(this); //click
        }

        //click within RecyclerView
        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            TransaccionAviso tipoAviso = this.items.get(position);
        }

    }
}
