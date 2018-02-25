package com.ucb.appin.views.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ucb.appin.R;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.views.fragment.IMyPublicationsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPublicationsAdapter extends RecyclerView.Adapter<MyPublicationsAdapter.CardViewHolder> {

    private final Context mainContext;
    private final List<Aviso> items;

    private IMyPublicationsView iMyPublicationsView;

    public MyPublicationsAdapter(Context mainContext, List<Aviso> items, IMyPublicationsView iMyPublicationsView) {
        this.mainContext = mainContext;
        this.items = items;
        this.iMyPublicationsView = iMyPublicationsView;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_row_my_publications,parent,false);
        return new CardViewHolder(v,mainContext,items);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Aviso item = items.get(position); //get item from my List<Place>
        holder.itemView.setTag(item); //save items in Tag
        int homeColor = ContextCompat.getColor(mainContext, R.color.colorHome) ;
        int aptColor = ContextCompat.getColor(mainContext, R.color.colorDepartament);
        int shopColor = ContextCompat.getColor(mainContext, R.color.colorShopping);
        int roomColor = ContextCompat.getColor(mainContext, R.color.colorRoom);
        int shoppingColor = ContextCompat.getColor(mainContext, R.color.colorShop);
        int motoColor = ContextCompat.getColor(mainContext, R.color.colorMoto);
        int carColor = ContextCompat.getColor(mainContext, R.color.colorCar);

        holder.titulo.setText(item.getTitulo());
        holder.descripcion.setText(item.getDescripcion());
        holder.btnEdit.setTag(item);
        holder.btnDelete.setTag(item);

        holder.initial.setText(item.getTitulo().substring(0, 1));

        try {
            if (item.getTipoaviso()== 1) {
                holder.initial.getBackground().setColorFilter(homeColor, PorterDuff.Mode.SRC);
            }
            else if (item.getTipoaviso()== 2) {
                holder.initial.getBackground().setColorFilter(aptColor, PorterDuff.Mode.SRC);
            }else if (item.getTipoaviso()== 3) {
                holder.initial.getBackground().setColorFilter(shopColor, PorterDuff.Mode.SRC);
            }else if (item.getTipoaviso()== 4) {
                holder.initial.getBackground().setColorFilter(roomColor, PorterDuff.Mode.SRC);
            }else if (item.getTipoaviso()== 5) {
                holder.initial.getBackground().setColorFilter(shoppingColor, PorterDuff.Mode.SRC);
            }else if (item.getTipoaviso()== 6) {
                holder.initial.getBackground().setColorFilter(motoColor, PorterDuff.Mode.SRC);
            }else if (item.getTipoaviso()== 6) {
                holder.initial.getBackground().setColorFilter(carColor, PorterDuff.Mode.SRC);
            }
        }catch (Exception err){

        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iMyPublicationsView.showEditAviso((Aviso) view.getTag());
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iMyPublicationsView.showDialogDelete((Aviso) view.getTag());
            }
        });
    }


    @Override
    public int getItemCount() {
        int size = 0;
        if(items != null){
            size = items.size();
        }
        return size;
    }

    public void setItemsAll(List<Aviso> auxListAvisos) {
        this.items.clear();
        this.items.addAll(auxListAvisos);
    }

    public List<Aviso> getItems() {
        return items;
    }

    public void setItem(Aviso aviso) {
        items.add(aviso);
    }

    //class static
    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.txt_title)
        TextView titulo;
        @BindView(R.id.txt_description)
        TextView descripcion;

        @BindView(R.id.textLetterMyPubTitle)
        TextView initial;

        ImageButton btnEdit;
        ImageButton btnDelete;

        private Context context;
        private List<Aviso> items = new ArrayList<Aviso>();

        public CardViewHolder(View v, Context context, List<Aviso> items){
            super(v);
            this.context = context;
            this.items = items;
            ButterKnife.bind(this, v); //with butterKnife

            this.titulo = v.findViewById(R.id.txt_title);
            this.descripcion = v.findViewById(R.id.txt_description);

            this.btnDelete = v.findViewById(R.id.btn_delete);
            this.btnEdit = v.findViewById(R.id.btn_edit);
            this.initial = v.findViewById(R.id.textLetterMyPubTitle);

            v.setOnClickListener(this); //click
        }

        //click within RecyclerView
        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            //Aviso avisosBean = this.items.get(position);
            //compraPresenter.modificarCompra(compra);
            //Snackbar.make(v.getRootView(), "Compra Realizada>>" + compra.getDescripcion(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }
}
