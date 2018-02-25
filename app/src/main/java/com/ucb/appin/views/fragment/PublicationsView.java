package com.ucb.appin.views.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.views.activity.DetailPublicationView;
import com.ucb.appin.views.adapters.*;

import com.ucb.appin.R;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.presenters.IPublicationsPresenter;
import com.ucb.appin.presenters.PublicationsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PublicationsView extends Fragment implements IPublicationsView {
    private View view;
    private IPublicationsPresenter iPublicationsPresenter;

    @BindView(R.id.l_publications)
    RecyclerView recycler;

    @BindView(R.id.list_progress)
    View mProgressView;

    @BindView(R.id.spi_lall_tipo)
    Spinner spiLTipo;
    @BindView(R.id.spi_lall_transaccion)
    Spinner spiLTransaccion;

    private List<Aviso> listAvisosToSarch;
    private OnFragmentInteractionListener mListener;
    private PublicationsAdapter publicationsAdapter = null;

    private boolean buscando = false;
    private TipoAviso tipoAvisoSel;
    private TransaccionAviso transaccionAvisoSel;

    private List<TipoAviso> listTipoAvisoBeans = new ArrayList<TipoAviso>();
    private List<TransaccionAviso> listTransaccionAvisos = new ArrayList<TransaccionAviso>();

    private String[] slistaTipoAvisos;
    private String[] slistaTransaccionAvisos;
    private boolean swTipoAviso = false;
    private boolean swTransAviso = false;

    public PublicationsView() {
        // Required empty public constructor
    }

    public static PublicationsView newInstance(String param1, String param2) {
        PublicationsView fragment = new PublicationsView();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.view = inflater.inflate(R.layout.fragment_publications, container, false);
        this.recycler = this.view.findViewById(R.id.l_publications);
        this.mProgressView = this.view.findViewById(R.id.list_progress);

        this.iPublicationsPresenter = new PublicationsPresenter(this.getActivity().getApplicationContext(), this);

        this.spiLTipo = this.view.findViewById(R.id.spi_lall_tipo);
        this.spiLTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipoAvisoSel = listTipoAvisoBeans.get(i);
                if(swTipoAviso){
                    loadAvisos();
                }
                else{
                    swTipoAviso = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        this.spiLTransaccion = this.view.findViewById(R.id.spi_lall_transaccion);
        this.spiLTransaccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                transaccionAvisoSel = listTransaccionAvisos.get(i);
                if(swTransAviso){
                    loadAvisos();
                }
                else{
                    swTransAviso = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        loadAvisos();

        this.iPublicationsPresenter.listTipoAvisos();
        this.iPublicationsPresenter.listTransaccionAvisos();

        return this.view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            recycler.setVisibility(show ? View.GONE : View.VISIBLE);
            recycler.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recycler.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            recycler.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public void loadData(List<Aviso> listAvisos) {
       //Log.v("----->",">RESULT OBSERVER VIEW>" + listAvisos.size());

        if(!buscando){
            this.listAvisosToSarch = new ArrayList<Aviso>();
            this.listAvisosToSarch.addAll(listAvisos);

            //if(publicationsAdapter == null){

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                this.publicationsAdapter = new PublicationsAdapter(this.getActivity().getApplicationContext(), listAvisos, this);
                this.recycler.setLayoutManager(linearLayoutManager);
                this.recycler.setAdapter(this.publicationsAdapter);
                this.recycler.setItemAnimator(new DefaultItemAnimator());
            /*}
            else{
                List<Aviso>listAvisosRecycler = publicationsAdapter.getItems();
                for(int i = 0;i < listAvisos.size();i++){
                    Aviso aviso = listAvisos.get(i);
                    boolean exist = false;
                    for(int j = 0;j < listAvisosRecycler.size();j++){
                        Aviso aviso1 = listAvisosRecycler.get(j);
                        if(aviso.getId() == aviso1.getId()){
                            exist = true;
                            break;
                        }
                    }
                    if(!exist){
                        publicationsAdapter.setItem(aviso);
                    }
                }
                publicationsAdapter.notifyDataSetChanged();
            }*/
        }

    }

    @Override
    public void searchAvisos(String txtBuscar) {
        if(listAvisosToSarch != null){
            List<Aviso>auxListAvisos = new ArrayList<Aviso>();
            if(!txtBuscar.isEmpty()){
                buscando = true;
                for(int i = 0;i < listAvisosToSarch.size();i++){
                    Aviso equiposBean = listAvisosToSarch.get(i);
                    if((equiposBean.getDescripcion().toUpperCase()).contains(txtBuscar.toUpperCase())){
                        auxListAvisos.add(equiposBean);
                    }
                }
            }
            else{
                buscando = false;
                auxListAvisos.addAll(listAvisosToSarch);
            }

            this.publicationsAdapter.setItemsAll(auxListAvisos);
            this.publicationsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadDataTipoAviso(List<TipoAviso> listTipoAvisoBeans){
        this.listTipoAvisoBeans = listTipoAvisoBeans;
        if(this.listTipoAvisoBeans.size() > 0){
            this.slistaTipoAvisos = new String[this.listTipoAvisoBeans.size()];
            for(int i = 0;i < listTipoAvisoBeans.size();i++){
                TipoAviso tipoAvisoBean = listTipoAvisoBeans.get(i);
                this.slistaTipoAvisos[i] =  tipoAvisoBean.getNombre();
            }
            //seting data in spinner
            ArrayAdapter<String> adapterAvisos = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, slistaTipoAvisos);
            adapterAvisos.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spiLTipo.setAdapter(adapterAvisos);
        }
    }

    @Override
    public void loadDataTransaccionAviso(List<TransaccionAviso>listTransaccionAvisos){
        this.listTransaccionAvisos = listTransaccionAvisos;

        if(this.listTransaccionAvisos.size() > 0){
            this.slistaTransaccionAvisos = new String[this.listTransaccionAvisos.size()];
            for(int i = 0;i < listTransaccionAvisos.size();i++){
                TransaccionAviso transaccionAviso = listTransaccionAvisos.get(i);
                this.slistaTransaccionAvisos[i] =  transaccionAviso.getNombre();
            }

            //seting data in spinner
            ArrayAdapter<String> adapterTransaccion = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, slistaTransaccionAvisos);
            adapterTransaccion.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spiLTransaccion.setAdapter(adapterTransaccion);
        }
    }

    @Override
    public void mostrarDetallePublication(Aviso avisosBean) {
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailPublicationView.class);
        Gson gson = new Gson();
        String javiso = gson.toJson(avisosBean);
        intent.putExtra("aviso",javiso);
        startActivity(intent);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void loadAvisos() {
        try {
            this.tipoAvisoSel = listTipoAvisoBeans.get(spiLTipo.getSelectedItemPosition());
            this.transaccionAvisoSel = listTransaccionAvisos.get(spiLTransaccion.getSelectedItemPosition());
        }catch (Exception ex){}

        this.iPublicationsPresenter.listAvisosBackground(this.tipoAvisoSel, this.transaccionAvisoSel);
    }

}
