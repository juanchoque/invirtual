package com.ucb.appin.views.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucb.appin.R;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.util.ConstInVirtual;
import com.ucb.appin.views.fragment.IPublicationsView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublicationsAdapter extends RecyclerView.Adapter<PublicationsAdapter.CardViewHolder> {

    private final Context mainContext;
    private final List<Aviso> items;
    private int TITULO_MAX_LENGHT = 40;
    private int ADDRESS_MAXLENGHT = 80;

    private IPublicationsView iPublicationsView;

    public PublicationsAdapter(Context mainContext, List<Aviso> items, IPublicationsView iPublicationsView) {
        this.mainContext = mainContext;
        this.items = items;
        this.iPublicationsView = iPublicationsView;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_row_publications,parent,false);
        return new CardViewHolder(v,mainContext,items);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Aviso item = items.get(position); //get item from my List<Place>
        holder.itemView.setTag(item); //save items in Tag

        String shortTitle;
        int homeColor = ContextCompat.getColor(mainContext, R.color.colorHome) ;
        int aptColor = ContextCompat.getColor(mainContext, R.color.colorDepartament);
        int shopColor = ContextCompat.getColor(mainContext, R.color.colorShopping);
        int roomColor = ContextCompat.getColor(mainContext, R.color.colorRoom);
        int shoppingColor = ContextCompat.getColor(mainContext, R.color.colorShop);
        int motoColor = ContextCompat.getColor(mainContext, R.color.colorMoto);
        int carColor = ContextCompat.getColor(mainContext, R.color.colorCar);

        if (item.getTitulo().length() > TITULO_MAX_LENGHT) {
            shortTitle = item.getTitulo().substring(0, TITULO_MAX_LENGHT) + "...";
        } else {
            shortTitle = item.getTitulo();
        }
        holder.titulo.setText(shortTitle);

        String shortAddress;
        if (item.getDireccion().length() > ADDRESS_MAXLENGHT) {
            shortAddress = item.getDireccion().substring(0, ADDRESS_MAXLENGHT) + "...";
        } else {
            shortAddress = item.getDireccion();
        }
        holder.address.setText(shortAddress);
        holder.price.setText(String.valueOf(item.getPrecio()));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = item.getFecPublicacion() != null?df.format(item.getFecPublicacion()):ConstInVirtual.WITHOUT_DATE;
        holder.datePublication.setText(fecha);
        try {
            holder.initial.setText(item.getTitulo().substring(0, 1));
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
        }catch (Exception error){}
    }

    public void setItemsAll(List<Aviso> auxListAvisos) {
        this.items.clear();
        this.items.addAll(auxListAvisos);
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if(items != null){
            size = items.size();
        }
        return size;
    }

    public List<Aviso> getItems() {
        return this.items;
    }

    public void setItem(Aviso aviso) {
        items.add(aviso);
    }

    //class static
    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.textViewInitialTitlePublication)
        TextView initial;

        @BindView(R.id.txt_title)
        TextView titulo;
        @BindView(R.id.textViewAddressPublication)
        TextView address;

        @BindView(R.id.textViewPricePublication)
        TextView price;

        @BindView(R.id.textViewDatePublication)
        TextView datePublication;
        private Context context;
        private List<Aviso> items = new ArrayList<Aviso>();
        public CardViewHolder(View v, Context context, List<Aviso> items){
            super(v);
            this.context = context;
            this.items = items;
            ButterKnife.bind(this, v); //with butterKnife
            this.initial = v.findViewById(R.id.textViewInitialTitlePublication);
            this.titulo = v.findViewById(R.id.txt_title);
            this.address = v.findViewById(R.id.textViewAddressPublication);
            this.price = v.findViewById(R.id.textViewPricePublication);
            this.datePublication = v.findViewById(R.id.textViewDatePublication);
            v.setOnClickListener(this); //click
        }

        //click within RecyclerView
        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            Aviso avisosBean = this.items.get(position);
            iPublicationsView.mostrarDetallePublication(avisosBean);
        }


    }
}
