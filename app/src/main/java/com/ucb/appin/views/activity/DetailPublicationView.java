package com.ucb.appin.views.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.gson.Gson;
import com.ucb.appin.R;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.presenters.DetailPublicationPresenter;
import com.ucb.appin.presenters.IDetailPublicationPresenter;
import com.ucb.appin.util.ConstInVirtual;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailPublicationView extends AppCompatActivity implements OnMapReadyCallback, IDetailPublicationView {

    private Aviso avisosBean = null;

    private double latitud = ConstInVirtual.DEFAULT_LATITUDE;
    private double longitud = ConstInVirtual.DEFAULT_LONGITUDE;

    private GoogleMap googleMap;
    private int zoomInit = 0;

    @BindView(R.id.detail_form)
    View mAddFormView;

    @BindView(R.id.img_detail_foto)
    ImageView imgAddFoto;

    @BindView(R.id.map_detail_ubicacion)
    MapView mapView;

    TextView txtDescription;
    TextView txtPrice;
    TextView txtPhoneNumber;
    TextView txtAddress;
    TextView txtTypePublication;
    TextView txtTransaction;
    ImageView viewAddFoto;
    Button btnLlamar;

    private IDetailPublicationPresenter iDetailPublicationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_publication);
        ButterKnife.bind(this);

        this.mAddFormView = findViewById(R.id.detail_form);
        this.imgAddFoto = (ImageView) findViewById(R.id.img_detail_foto);
        txtDescription = (TextView) findViewById(R.id.txt_detail_descripcion);
        txtPrice = (TextView) findViewById(R.id.txt_detail_precio);
        txtPhoneNumber = (TextView) findViewById(R.id.txt_detail_telefono);
        txtAddress = (TextView) findViewById(R.id.txt_detail_direccion);
        txtTypePublication = (TextView) findViewById(R.id.txt_detail_tipo);
        txtTransaction = (TextView) findViewById(R.id.txt_detail_transaction);
        btnLlamar = (Button) findViewById(R.id.btn_detail_telefono);

        mapView.onCreate(savedInstanceState);
        loadGoogleMaps();

        viewAddFoto = (ImageView) findViewById(R.id.img_detail_foto);

        this.iDetailPublicationPresenter = new DetailPublicationPresenter(this, this);

        Gson gson = new Gson();

        try {
            String tmp = (String)getIntent().getExtras().get("aviso");
            this.avisosBean = gson.fromJson(tmp, Aviso.class);

            if(this.avisosBean != null){
                this.insertData();
            }
        }catch (Exception err){}

        mapView = (MapView) findViewById(R.id.map_detail_ubicacion);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @OnClick(R.id.btn_detail_telefono)
    public void onClickBtnCall(){
        if(this.avisosBean != null){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.confirmacion)
                    .setIcon(R.drawable.ic_dashboard_black_24dp)
                    .setMessage(R.string.confirmar_llamar)
                    .setCancelable(false)
                    .setPositiveButton(R.string.llamar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            callTel();
                        }
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(ConstInVirtual.DEFAULT_LATITUDE, ConstInVirtual.DEFAULT_LONGITUDE)).zoom(ConstInVirtual.DEFAULT_ZOOM).build();
        this.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        createMarker();
    }

    private void createMarker() {
        try {
            this.googleMap.clear();
        } catch (Exception err) {
        }
        LatLng coordinates = new LatLng(avisosBean.getLatitud(), avisosBean.getLongitud());
        googleMap.addMarker(new MarkerOptions().position(coordinates).title(avisosBean.getTitulo()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, ConstInVirtual.DEFAULT_ZOOM));
        mapView.onResume();

        if (zoomInit == 0) {
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(ConstInVirtual.DEFAULT_ZOOM);
            this.googleMap.animateCamera(zoom);
            zoomInit++;
        }
    }

    public void callTel(){
        try{
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + this.avisosBean.getTelefono()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            }
        }catch (Exception err){

        }
    }

    private void loadData(){
        this.iDetailPublicationPresenter.getTipoAvisoById(this.avisosBean.getTipoaviso());
        this.iDetailPublicationPresenter.getTransaccionAvisoById(this.avisosBean.getTipoaviso());
    }

    private void insertData(){
        TipoAviso tipoAviso = this.iDetailPublicationPresenter.getTipoAvisoById(this.avisosBean.getTipoaviso());
        TransaccionAviso transaccionAviso = this.iDetailPublicationPresenter.getTransaccionAvisoById(this.avisosBean.getTransaccionaviso());

        Log.v("this.avoso", String.valueOf(this.avisosBean));
        this.txtDescription.setText(this.avisosBean.getDescripcion());
        this.txtPrice.setText(this.avisosBean.getPrecio().toString());
        this.txtPhoneNumber.setText(this.avisosBean.getTelefono());
        this.txtAddress.setText(this.avisosBean.getDireccion());
        this.txtTypePublication.setText(tipoAviso != null?tipoAviso.getNombre():"Sin tipo" );
        this.txtTransaction.setText(transaccionAviso != null?transaccionAviso.getNombre():"Sin Transaccion");

        if(this.avisosBean.getImagen() != null){
            if(!this.avisosBean.getImagen().isEmpty()){
                byte[] imaget64 = Base64.decode(this.avisosBean.getImagen(), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imaget64, 0, imaget64.length);
                imgAddFoto.setImageBitmap(decodedImage);
            }
        }

        try {
            this.latitud = this.avisosBean.getLatitud();
            this.longitud = this.avisosBean.getLongitud();
            loadGoogleMaps();
        }catch (Exception err){

        }
    }

    private void loadGoogleMaps() {
        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(final GoogleMap googleMap) {

                MarkerOptions marker = new MarkerOptions().position(new LatLng(avisosBean.getLatitud(), avisosBean.getLongitud())).title(avisosBean.getDescripcion()).snippet("(Toque para detalles)");

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Aviso raviso = (Aviso) marker.getTag();
                        mostrarOpciones(raviso);
                    }
                });

                Marker rmarker = googleMap.addMarker(marker);
                rmarker.setTag(avisosBean);
            }
        });
    }

    public void mostrarOpciones(final Aviso aviso){
        CharSequence colors[] = new CharSequence[] {getString(R.string.llamar), getString(R.string.cancelar)};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.seleccione));
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                if(which == 0){
                    callTel();
                }
            }
        });
        builder.show();
    }

    @Override
    public void loadTipoAviso(TipoAviso tipoaviso) {
        if(tipoaviso != null){
            txtTypePublication.setText(tipoaviso.getNombre());
        }
    }

    @Override
    public void loadTransaccionAviso(TransaccionAviso transaccionaviso) {
        if(transaccionaviso != null){
            txtTransaction.setText(transaccionaviso.getNombre());
        }
    }
}
