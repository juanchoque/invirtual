package com.ucb.appin.views.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.ucb.appin.R;
import com.ucb.appin.data.model.Aviso;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.presenters.IAddMyPublicationPresenter;
import com.ucb.appin.presenters.AddMyPublicationPresenter;
import com.ucb.appin.util.ConstInVirtual;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMyPublicationView extends AppCompatActivity implements OnMapReadyCallback, IAddMyPublicationView {

    private String path_foto = null;
    private GoogleMap googleMap;
    private IAddMyPublicationPresenter presenterAddMyPublication;
    private List<TipoAviso> listTipoAvisoBeans = new ArrayList<TipoAviso>();
    private List<TransaccionAviso> listTransaccionAvisos = new ArrayList<TransaccionAviso>();
    private String[] slistaTipoAvisos;
    private String[] slistaTransaccionAvisos;
    private Aviso avisosBean = null;
    private double latitud = ConstInVirtual.DEFAULT_LATITUDE;
    private double longitud = ConstInVirtual.DEFAULT_LONGITUDE;
    private int zoomInit = 0;


    // UI references.
    @BindView(R.id.add_progress)
    View mProgressView;

    @BindView(R.id.add_form)
    View mAddFormView;

    @BindView(R.id.img_add_foto)
    ImageView imgAddFoto;

    @BindView(R.id.map_add_ubicacion)
    MapView mapView;

    EditText txtDescripcion;
    EditText txtAddPrecio;
    EditText txtAddTelefono;
    EditText txtAddDireccion;
    Spinner spiAddIipo;
    Spinner spiAddTransaccion;
    ImageButton btnAddFoto;
    ImageButton btnDeleteFoto;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_publications);
        ButterKnife.bind(this);

        this.presenterAddMyPublication = new AddMyPublicationPresenter(this, this);
        this.presenterAddMyPublication.listTipoAvisos();
        this.presenterAddMyPublication.listTransaccionAvisos();

        // recuperando datos desde la vista
        this.mProgressView = findViewById(R.id.add_progress);
        this.mAddFormView = findViewById(R.id.add_form);
        this.imgAddFoto = (ImageView) findViewById(R.id.img_add_foto);

        txtDescripcion = (EditText) findViewById(R.id.txt_descripcion);
        txtAddPrecio = (EditText) findViewById(R.id.txt_add_precio);
        txtAddTelefono = (EditText) findViewById(R.id.txt_add_telefono);
        txtAddDireccion = (EditText) findViewById(R.id.txt_add_direccion);
        spiAddIipo = (Spinner) findViewById(R.id.spi_add_tipo);
        spiAddTransaccion = (Spinner) findViewById(R.id.spi_add_transaccion);

        this.btnAddFoto = (ImageButton) findViewById(R.id.btn_add_foto);
        this.btnDeleteFoto = (ImageButton) findViewById(R.id.btn_delete_foto);

        this.btnAddFoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage();
            }
        });
        this.btnDeleteFoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteImage();
            }
        });

        mapView = (MapView) findViewById(R.id.map_add_ubicacion);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        //seting data in spinner
        try {
            ArrayAdapter<String> adapterAvisos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slistaTipoAvisos);
            adapterAvisos.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spiAddIipo.setAdapter(adapterAvisos);

            ArrayAdapter<String> adapterTransaccion = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, slistaTransaccionAvisos);
            adapterTransaccion.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spiAddTransaccion.setAdapter(adapterTransaccion);
        }catch (Exception er){

        }

        try {
            Gson gson = new Gson();
            Bundle extras = getIntent().getExtras();
            String tmp = extras.getString("avisosBean");
            this.avisosBean = gson.fromJson(tmp, Aviso.class);
            Log.v("----.....-----",">ID C MOD>" + this.avisosBean.getIdc());
        } catch (Exception err) {
            this.avisosBean = null;
        }

        if (this.avisosBean != null) {
            this.insertData();
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mAddFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAddFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAddFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mAddFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_my_public, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_add_save) {

            String descripcion = this.txtDescripcion.getText().toString();
            String precio = this.txtAddPrecio.getText().toString();
            String telefono = this.txtAddTelefono.getText().toString();
            String direccion = this.txtAddDireccion.getText().toString();

            TipoAviso tipoAvisoBean = listTipoAvisoBeans.get(this.spiAddIipo.getSelectedItemPosition());
            TransaccionAviso transaccionAviso = listTransaccionAvisos.get(this.spiAddTransaccion.getSelectedItemPosition());

            boolean dvalid = this.validateData(descripcion, precio, telefono, direccion, tipoAvisoBean, transaccionAviso);
            if (dvalid) {
                Log.v("--->", ">>LLEGA PARA GUARDAR>>");

                String image64 = "";
                try {

                    Bitmap bitmap = ((BitmapDrawable) imgAddFoto.getDrawable()).getBitmap();
                    if (bitmap != null) {

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();

                        Bitmap original = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        Bitmap news = Bitmap.createScaledBitmap(original, 120, 120, false);

                        ByteArrayOutputStream rBaos = new ByteArrayOutputStream();
                        news.compress(Bitmap.CompressFormat.JPEG, 100, rBaos);
                        byte[] rImageBytes = rBaos.toByteArray();

                        image64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    }

                } catch (Exception error) {
                }

                Aviso avisosBean = new Aviso();
                int typeOperation = ConstInVirtual.ADD_OPERACION;
                if (this.avisosBean != null) {
                    avisosBean.setId(this.avisosBean.getId());
                    avisosBean.setIdc(this.avisosBean.getIdc());
                    typeOperation = ConstInVirtual.EDIT_OPERATION;
                }

                String titulo = descripcion;
                if (descripcion.length() > 35) {
                    titulo = descripcion.substring(0, 35);
                }
                avisosBean.setTitulo(titulo.toUpperCase());
                avisosBean.setDescripcion(descripcion);
                avisosBean.setPrecio(Double.parseDouble(precio));
                avisosBean.setTelefono(telefono);
                avisosBean.setDireccion(direccion);
                avisosBean.setLatitud(latitud);
                avisosBean.setLongitud(longitud);
                avisosBean.setImagen(image64);

                avisosBean.setTipoaviso(tipoAvisoBean.getId());
                avisosBean.setTransaccionAviso(transaccionAviso.getId());

                this.presenterAddMyPublication.addAviso(avisosBean, typeOperation);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case ConstInVirtual.COD_SELECCIONA:
                    Uri miPath = data.getData();
                    imgAddFoto.setImageURI(miPath);
                    break;

                case ConstInVirtual.COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path_foto}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento", "Path: " + path);
                                }
                            });

                    Bitmap bitmap = BitmapFactory.decodeFile(path_foto);
                    imgAddFoto.setImageBitmap(bitmap);

                    break;
            }
        }
    }

    @Override
    public void loadInitDataTipoAviso(List<TipoAviso> listTipoAvisoBeans) {
        this.listTipoAvisoBeans = listTipoAvisoBeans;
        if (this.listTipoAvisoBeans.size() > 0) {
            this.slistaTipoAvisos = new String[this.listTipoAvisoBeans.size()];
            for (int i = 0; i < listTipoAvisoBeans.size(); i++) {
                TipoAviso tipoAvisoBean = listTipoAvisoBeans.get(i);
                this.slistaTipoAvisos[i] = tipoAvisoBean.getNombre();
            }
        }

    }

    @Override
    public void loadInitDataTransaccionAviso(List<TransaccionAviso> listTransaccionAvisos) {
        this.listTransaccionAvisos = listTransaccionAvisos;

        if (this.listTransaccionAvisos.size() > 0) {
            this.slistaTransaccionAvisos = new String[this.listTransaccionAvisos.size()];
            for (int i = 0; i < listTransaccionAvisos.size(); i++) {
                TransaccionAviso transaccionAviso = listTransaccionAvisos.get(i);
                this.slistaTransaccionAvisos[i] = transaccionAviso.getNombre();
            }
        }
    }

    @Override
    public void okSave(Aviso data) {
        Toast.makeText(this, ConstInVirtual.MESSAGE_OK_OPERATION, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void errorSave(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();
    }


    public void loadPlacePublication(Aviso avisosBean) {
        try {
            this.googleMap.clear();
        } catch (Exception err) {
        }
        LatLng coordinates = new LatLng(avisosBean.getLatitud(), avisosBean.getLongitud());
        googleMap.addMarker(new MarkerOptions().position(coordinates).title("Ubicacion"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, ConstInVirtual.DEFAULT_ZOOM));
        mapView.onResume();

        if (zoomInit == 0) {
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(ConstInVirtual.DEFAULT_ZOOM);
            this.googleMap.animateCamera(zoom);
            zoomInit++;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (this.avisosBean != null) {
            loadPlacePublication(this.avisosBean);
        } else {
            LatLng coordinates = new LatLng(ConstInVirtual.DEFAULT_LATITUDE, ConstInVirtual.DEFAULT_LONGITUDE);
            this.googleMap.addMarker(new MarkerOptions().position(coordinates).title("Ubicacion"));
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, ConstInVirtual.DEFAULT_ZOOM));
            mapView.onResume();
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                latitud = point.latitude;
                longitud = point.longitude;
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(point));
            }
        });
    }

    //insert data if is mod
    private void insertData() {
        this.txtDescripcion.setText(this.avisosBean.getDescripcion());
        this.txtAddPrecio.setText(this.avisosBean.getPrecio().toString());
        this.txtAddTelefono.setText(this.avisosBean.getTelefono());
        this.txtAddDireccion.setText(this.avisosBean.getDireccion());

        int index = 0;
        for (int i = 0; i < listTipoAvisoBeans.size(); i++) {
            TipoAviso tipoAvisoBean = listTipoAvisoBeans.get(i);
            if (tipoAvisoBean.getId() == this.avisosBean.getTipoaviso()) {
                index = i;
                break;
            }
        }
        this.spiAddIipo.setSelection(index);

        for (int i = 0; i < listTransaccionAvisos.size(); i++) {
            TransaccionAviso transaccionAviso = listTransaccionAvisos.get(i);
            if (transaccionAviso.getId() == this.avisosBean.getTransaccionaviso()) {
                index = i;
                break;
            }
        }
        this.spiAddTransaccion.setSelection(index);

        if (this.avisosBean.getImagen() != null) {
            if (!this.avisosBean.getImagen().isEmpty()) {
                byte[] imaget64 = Base64.decode(this.avisosBean.getImagen(), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imaget64, 0, imaget64.length);
                imgAddFoto.setImageBitmap(decodedImage);
            }
        }
    }

    private void loadImage() {

        final CharSequence[] opciones = {"Tomar Foto", "Cargar Imagen", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(AddMyPublicationView.this);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")) {
                    takePhothograph();
                } else {
                    if (opciones[i].equals("Cargar Imagen")) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), ConstInVirtual.COD_SELECCIONA);
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }

    private void takePhothograph() {
        File fileImagen = new File(Environment.getExternalStorageDirectory(), ConstInVirtual.RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();
        String nombreImagen = "";
        if (!isCreada) {
            isCreada = fileImagen.mkdirs();
        }

        if (isCreada) {
            nombreImagen = (System.currentTimeMillis() / 1000) + ".jpg";
        }


        path_foto = Environment.getExternalStorageDirectory() +
                File.separator + ConstInVirtual.RUTA_IMAGEN + File.separator + nombreImagen;

        File imagen = new File(path_foto);

        Intent intent = null;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authorities = getApplicationContext().getPackageName() + ".provider";
            Uri imageUri = FileProvider.getUriForFile(this, authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent, ConstInVirtual.COD_FOTO);

    }

    private void deleteImage() {
        this.imgAddFoto.setImageURI(null);
    }

    //////////////////////////////////////////////VALIDATE///////////////////////////////////////////////////////////
    public boolean validateData(String descripcion, String precio, String telefono, String direccion, TipoAviso tipoAvisoBean, TransaccionAviso transaccionAviso) {
        boolean result = true;
        if (descripcion == null) {
            this.txtDescripcion.setError(ConstInVirtual.DEFAULT_DATA_ERROR);
            this.txtDescripcion.requestFocus();
            result = false;
        } else if (descripcion.length() == 0 || descripcion.length() > 250) {
            this.txtDescripcion.setError(ConstInVirtual.DEFAULT_DATA_ERROR);
            this.txtDescripcion.requestFocus();
            result = false;
        }
        if (telefono == null) {
            this.txtAddTelefono.setError(ConstInVirtual.DEFAULT_DATA_ERROR);
            this.txtAddTelefono.requestFocus();
            result = false;
        } else if (telefono.length() == 0 || telefono.length() > 20) {
            this.txtAddTelefono.setError(ConstInVirtual.DEFAULT_DATA_ERROR);
            this.txtAddTelefono.requestFocus();
            result = false;
        }
        if (direccion != null) {
            if (direccion.length() > 250) {
                this.txtAddDireccion.setError(ConstInVirtual.DEFAULT_DATA_ERROR);
                this.txtAddDireccion.requestFocus();
                result = false;
            }
        }
        if(tipoAvisoBean == null){
            Toast.makeText(this, ConstInVirtual.TIPO_AVISO_NOTHING, Toast.LENGTH_LONG).show();
            result = false;
        }
        if(transaccionAviso == null){
            Toast.makeText(this, ConstInVirtual.TRANSACCION_AVISO_NOTHING, Toast.LENGTH_LONG).show();
            result = false;
        }

        try {
            double aux = Double.parseDouble(precio);
            result = true;
        } catch (Exception error) {
            result = false;
            this.txtAddPrecio.setError(ConstInVirtual.DEFAULT_DATA_ERROR);
            this.txtAddPrecio.requestFocus();
        }

        return result;
    }

}

