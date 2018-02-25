package com.ucb.appin.views.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trello.rxlifecycle2.components.RxFragment;
import com.ucb.appin.R;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.presenters.IPublicationsMapPresenter;
import com.ucb.appin.presenters.PublicationsMapPresenter;
import com.ucb.appin.util.ConstInVirtual;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PublicationsMapView extends RxFragment implements OnMapReadyCallback, IPublicationsMapView {
    private OnFragmentInteractionListener mListener;
    private View view;

    private IPublicationsMapPresenter iPublicationsMapPresenter;

    private GoogleMap googleMap;
    private MapView mapView;
    private int zoomInit = 0;

    @BindView(R.id.spi_lallm_tipo)
    Spinner spiLTipo;
    @BindView(R.id.spi_lallm_transaccion)
    Spinner spiLTransaccion;

    private boolean buscando = false;
    private TipoAviso tipoAvisoSel;
    private TransaccionAviso transaccionAvisoSel;

    private String[] slistaTipoAvisos;
    private String[] slistaTransaccionAvisos;
    private boolean swTipoAviso = false;
    private boolean swTransAviso = false;

    private List<TipoAviso> listTipoAvisoBeans = new ArrayList<TipoAviso>();
    private List<TransaccionAviso> listTransaccionAvisos = new ArrayList<TransaccionAviso>();

    private List<Aviso> listAvisosToSarch;

    private ArrayList<Marker> mMarkerArray = new ArrayList<Marker>();

    public PublicationsMapView() {
        // Required empty public constructor
    }

    public static PublicationsMapView newInstance(String param1, String param2) {
        PublicationsMapView fragment = new PublicationsMapView();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_publications_map, container, false);

        this.iPublicationsMapPresenter = new PublicationsMapPresenter(this.getActivity().getApplicationContext(), this);

        this.spiLTipo = this.view.findViewById(R.id.spi_lallm_tipo);
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
        this.spiLTransaccion = this.view.findViewById(R.id.spi_lallm_transaccion);
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

        this.iPublicationsMapPresenter.listTipoAvisos();
        this.iPublicationsMapPresenter.listTransaccionAvisos();

        mapView = (MapView) this.view.findViewById(R.id.content_map);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

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
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getActivity().getApplicationContext());
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(ConstInVirtual.DEFAULT_LATITUDE, ConstInVirtual.DEFAULT_LONGITUDE)).zoom(ConstInVirtual.DEFAULT_ZOOM).build();
        this.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        loadAvisos();
    }

    private void loadAvisos() {
        try{
            googleMap.clear();
            mMarkerArray = new ArrayList<>();
        }catch (Exception ex){}

        try {
            this.tipoAvisoSel = listTipoAvisoBeans.get(spiLTipo.getSelectedItemPosition());
            this.transaccionAvisoSel = listTransaccionAvisos.get(spiLTransaccion.getSelectedItemPosition());
        }catch (Exception ex){}

        this.iPublicationsMapPresenter.listAvisosBackground(this.tipoAvisoSel, this.transaccionAvisoSel);
    }

    @Override
    public void loadData(List<Aviso> listPublications) {
        //Log.v("------------>",".........>" + listPublications.size());
        if(buscando){
            try{
                googleMap.clear();
                mMarkerArray = new ArrayList<>();
            }catch (Exception ex){}
        }

        if(listPublications != null){
            this.listAvisosToSarch = new ArrayList<Aviso>();
            listPublications.addAll(listPublications);

            if(listPublications.size() > 0){
                Aviso ubicacionesBean  = listPublications.get(0);
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(ubicacionesBean.getLatitud(),ubicacionesBean.getLongitud()));
                this.googleMap.moveCamera(center);
                if(zoomInit == 0){
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(ConstInVirtual.DEFAULT_ZOOM);
                    this.googleMap.animateCamera(zoom);
                    zoomInit++;
                }
            }

            for (int i = 0;i < listPublications.size();i++){
                Aviso aviso = listPublications.get(i);
                boolean exist = false;
                for (Marker marker : mMarkerArray) {
                    Aviso aviso1 = (Aviso) marker.getTag();
                    if(aviso.getId() == aviso1.getId()){
                        exist = true;
                        break;
                    }
                }
                if(!exist){
                    drawMarker(aviso);
                }

            }
        }
    }

    @Override
    public void loadDataTipoAviso(List<TipoAviso> listTipoAvisos) {
        this.listTipoAvisoBeans = listTipoAvisos;
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
    public void loadDataTransaccionAviso(List<TransaccionAviso> listTransaccionAvisos) {
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

            loadData(auxListAvisos);
        }
    }

    public void drawMarker(Aviso aviso) {
        if (googleMap != null) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(aviso.getLatitud(), aviso.getLongitud())).title(aviso.getDescripcion()).snippet("(Toque para detalles)");

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Aviso raviso = (Aviso) marker.getTag();
                    showDetailsMarker(raviso);
                }
            });

            Marker rmarker = googleMap.addMarker(marker);
            rmarker.setTag(aviso);

            mMarkerArray.add(rmarker);
        }
    }

    public void callTel(String ntelefono){
        try{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + ntelefono));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            }
        }catch (Exception err){

        }
    }


    //method for show popup
    private void showDetailsMarker(Aviso aviso){
        String scontent = "DIRECCION:" + aviso.getDireccion()
                + "\n TITULO:"+ aviso.getTitulo() + "," + aviso.getLongitud()
                + "\n PRECIO:" + aviso.getPrecio()
                + "\n DESC:" + aviso.getDescripcion()
                + "\n TELF:" + aviso.getTelefono()
                + "\n FECHA:" + aviso.getFecPublicacion();

        View mView = this.getActivity().getLayoutInflater().inflate(R.layout.fragment_detail_map_avisos, null);

        TextView txtContent = (TextView) mView.findViewById(R.id.txt_content_det);
        txtContent.setText(scontent);

        Button btnSDetailMon = (Button) mView.findViewById(R.id.btn_s_detail_det);
        Button buttonLlamar = mView.findViewById(R.id.btn_s_detail_llamar);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnSDetailMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        buttonLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callTel(aviso.getTelefono());
            }
        });

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
