package com.ucb.appin.views.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ucb.appin.R;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.presenters.IMyPublicationPresenter;
import com.ucb.appin.presenters.MyPublicationsPresenter;
import com.ucb.appin.util.ConstInVirtual;
import com.ucb.appin.views.adapters.MyPublicationsAdapter;
import com.ucb.appin.views.activity.AddMyPublicationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyPublicationsView extends Fragment implements IMyPublicationsView {

    private OnFragmentInteractionListener mListener;
    private View view;
    private IMyPublicationPresenter presenterMyPublication;

    @BindView(R.id.l_my_publications)
    RecyclerView recycler;
    @BindView(R.id.add_progress)
    View mProgressView;

    @BindView(R.id.lin_my_publications)
    View mLinearLayout;

    @BindView(R.id.spi_l_tipo)
    Spinner spiLTipo;
    @BindView(R.id.spi_l_transaccion)
    Spinner spiLTransaccion;
    List<Aviso> listAvisosToSarch;
    MyPublicationsAdapter myPublicationsAdapter = null;

    private List<TipoAviso> listTipoAvisoBeans = new ArrayList<TipoAviso>();
    private List<TransaccionAviso> listTransaccionAvisos = new ArrayList<TransaccionAviso>();

    private String[] slistaTipoAvisos;
    private String[] slistaTransaccionAvisos;

    private TipoAviso tipoAvisoSel;
    private TransaccionAviso transaccionAvisoSel;

    private boolean swTipoAviso = false;
    private boolean swTransAviso = false;
    private boolean buscando = false;

    public MyPublicationsView() {
        // Required empty public constructor
    }

    public static MyPublicationsView newInstance(String param1, String param2) {
        MyPublicationsView fragment = new MyPublicationsView();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_my_publications, container, false);

        this.recycler = this.view.findViewById(R.id.l_my_publications);
        this.mLinearLayout = this.view.findViewById(R.id.lin_my_publications);
        this.mProgressView = this.view.findViewById(R.id.list_my_progress);

        this.presenterMyPublication = new MyPublicationsPresenter(this.getActivity().getApplicationContext(),this);

        this.spiLTipo = this.view.findViewById(R.id.spi_l_tipo);
        this.spiLTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipoAvisoSel = listTipoAvisoBeans.get(i);
                if(swTipoAviso){
                    //Toast.makeText(view.getContext(), "-TIPO AVIS-->" + tipoAvisoSel.getNombre(),Toast.LENGTH_LONG).show();
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
        this.spiLTransaccion = this.view.findViewById(R.id.spi_l_transaccion);
        this.spiLTransaccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                transaccionAvisoSel = listTransaccionAvisos.get(i);
                if(swTransAviso){
                    loadAvisos();
                    //Toast.makeText(view.getContext(), "-TRANSACCION-->" + transaccionAvisoSel.getNombre(),Toast.LENGTH_LONG).show();
                }
                else{
                    swTransAviso = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.presenterMyPublication.listTipoAvisos();
        this.presenterMyPublication.listTransaccionAvisos();

        loadAvisos();

        return this.view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
    public void loadData(List<Aviso> listAvisos) {
        if(!buscando){
            this.listAvisosToSarch = new ArrayList<Aviso>();
            this.listAvisosToSarch.addAll(listAvisos);

            //if(myPublicationsAdapter == null){
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                myPublicationsAdapter = new MyPublicationsAdapter(this.getActivity().getApplicationContext(), listAvisos, this);
                this.recycler.setLayoutManager(linearLayoutManager);
                this.recycler.setAdapter(myPublicationsAdapter);
                this.recycler.setItemAnimator(new DefaultItemAnimator());
            /*}
            else{
                List<Aviso>listAvisosRecycler = myPublicationsAdapter.getItems();
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
                        myPublicationsAdapter.setItem(aviso);
                    }
                }
                myPublicationsAdapter.notifyDataSetChanged();
            }*/
        }

    }

    @Override
    public void showDialogDelete(final Aviso avisosBean) {
        new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getString(R.string.confirmation))
                .setMessage(getActivity().getString(R.string.delete_messaje))
                .setCancelable(false)
                .setPositiveButton(getActivity().getString(R.string.delete), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenterMyPublication.deleteAviso(avisosBean);
                    }
                })
                .setNegativeButton(getActivity().getString(R.string.cancel), null)
                .show();
    }

    @Override
    public void showEditAviso(Aviso avisosBean){
        Intent intent = new Intent(getActivity(), AddMyPublicationView.class);
        Gson gson = new Gson();
        String jAvisosBean = gson.toJson(avisosBean);
        intent.putExtra("avisosBean", jAvisosBean);
        getActivity().startActivity(intent);
    }

    @Override
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLinearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mLinearLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLinearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLinearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void loadAvisos() {
        try {
            this.tipoAvisoSel = listTipoAvisoBeans.get(spiLTipo.getSelectedItemPosition());
            this.transaccionAvisoSel = listTransaccionAvisos.get(spiLTransaccion.getSelectedItemPosition());
        }catch (Exception ex){}

        this.presenterMyPublication.listAvisosBackground(this.tipoAvisoSel, this.transaccionAvisoSel);
    }

    @Override
    public void searchAvisos(String searchText) {
        if(listAvisosToSarch != null){
            List<Aviso>auxListAvisos = new ArrayList<Aviso>();
            if(!searchText.isEmpty()){
                buscando = true;
                for(int i = 0;i < listAvisosToSarch.size();i++){
                    Aviso avisosBean = listAvisosToSarch.get(i);
                    if((avisosBean.getDescripcion().toUpperCase()).contains(searchText.toUpperCase())){
                        auxListAvisos.add(avisosBean);
                    }
                }
            }
            else{
                buscando = false;
                auxListAvisos.addAll(listAvisosToSarch);
            }

            this.myPublicationsAdapter.setItemsAll(auxListAvisos);
            this.myPublicationsAdapter.notifyDataSetChanged();
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
    public void okDelete(Aviso data) {
        Toast.makeText(getActivity().getApplicationContext(), ConstInVirtual.MESSAGE_OK_OPERATION, Toast.LENGTH_LONG).show();
    }

    @Override
    public void errorDelete(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
