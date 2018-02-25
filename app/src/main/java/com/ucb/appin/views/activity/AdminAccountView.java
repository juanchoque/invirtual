package com.ucb.appin.views.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ucb.appin.R;
import com.ucb.appin.data.model.Cuenta;
import com.ucb.appin.data.model.TipoAviso;
import com.ucb.appin.data.model.TransaccionAviso;
import com.ucb.appin.presenters.IMyAccountPresenter;
import com.ucb.appin.presenters.MyAccountPresenter;
import com.ucb.appin.util.UtilInVirtual;
import com.ucb.appin.views.adapters.TransactionAvisosAdapter;
import com.ucb.appin.views.adapters.TypeAvisosAdapter;

import java.util.List;

public class AdminAccountView extends AppCompatActivity implements IAdminAccountView {

    private IMyAccountPresenter presenterMyAccount;

    private View mProgressView;
    private View mAccountFormView;

    private EditText txtNombre;
    private EditText txtApaterno;
    private EditText txtAmaterno;

    private Cuenta cuentasBean = null;

    private TypeAvisosAdapter typeAvisosAdapter;
    private TransactionAvisosAdapter transactionAvisosAdapter;

    RecyclerView recyclerType;
    RecyclerView recyclerTrans;

    private String []idsTipoAvisos = null;
    private String []idsTransAvisos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);

        txtNombre = (EditText) findViewById(R.id.txt_acc_nombre);
        txtApaterno = (EditText) findViewById(R.id.txt_acc_apaterno);
        txtAmaterno = (EditText) findViewById(R.id.txt_acc_amaterno);

        recyclerType = findViewById(R.id.l_type_av);
        recyclerTrans = findViewById(R.id.l_trans_av);

        mAccountFormView = findViewById(R.id.account_form);
        mProgressView = findViewById(R.id.account_progress);

        this.presenterMyAccount = new MyAccountPresenter(this, this);
        this.presenterMyAccount.getAccount();//recupera datos de la cuenta

        this.presenterMyAccount.listTipoAvisos();
        this.presenterMyAccount.listTransaccionAvisos();

    }

    private void updateAccount() {

        String nombre = txtNombre.getText().toString();
        String apaterno = txtApaterno.getText().toString();
        String amaterno = txtAmaterno.getText().toString();

        if(validateData(nombre, apaterno, amaterno)){
            UtilInVirtual utilInmo = new UtilInVirtual(this);
            String imei = utilInmo.getNumberPhone();

            Cuenta cuentasBean = new Cuenta();
            cuentasBean.setNombres(nombre);
            cuentasBean.setAmaterno(amaterno);
            cuentasBean.setAparteno(apaterno);
            cuentasBean.setTelefono(imei);
            cuentasBean.setCelular(imei);

            String idsTipoA = this.typeAvisosAdapter.getItems();
            String idsTrasA = this.transactionAvisosAdapter.getItems();

            cuentasBean.setsTipoAvisos(idsTipoA);
            cuentasBean.setsTransaccionAvisos(idsTrasA);

            this.presenterMyAccount.addAccount(cuentasBean);
        }
    }

    @Override
    public void loadData(Cuenta cuentasBean) {

        this.cuentasBean = cuentasBean;
        if(cuentasBean != null){
            txtAmaterno.setText(cuentasBean.getAmaterno());
            txtApaterno.setText(cuentasBean.getAparteno());
            txtNombre.setText(cuentasBean.getNombres());

            if(cuentasBean.getsTipoAvisos() != null){
                if(!cuentasBean.getsTipoAvisos().isEmpty()){
                    idsTipoAvisos = cuentasBean.getsTipoAvisos().split(",");
                }
            }

            if(cuentasBean.getsTransaccionAvisos() != null){
                if(!cuentasBean.getsTransaccionAvisos().isEmpty()){
                    idsTransAvisos = cuentasBean.getsTransaccionAvisos().split(",");
                }
            }

            Log.v("--->",">>TIPOS SELECCIONADOS>>" + idsTipoAvisos);
            Log.v("--->",">>TRANS SELECCIONADOS>>" + idsTransAvisos);

            loadSelectedTipoAvisos();
            loadSelectedTransAvisos();
        }
    }

    private void loadSelectedTransAvisos() {
        if(idsTransAvisos != null){
            for(int i = 0; i < transactionAvisosAdapter.getDataItems().size();i++){
                TransaccionAviso transaccionAviso = transactionAvisosAdapter.getDataItems().get(i);
                for(int j = 0;j < idsTransAvisos.length;j++){
                    String ids = idsTransAvisos[j];
                    try {
                        int id = Integer.parseInt(ids);
                        if(id == transaccionAviso.getId()){
                            transactionAvisosAdapter.selectedItem(transaccionAviso);
                        }
                    }catch (Exception ex){}
                }
            }
        }
    }

    private void loadSelectedTipoAvisos() {
        if(idsTipoAvisos != null){
            for(int i = 0; i < typeAvisosAdapter.getDataItems().size();i++){
                TipoAviso tipoAviso = typeAvisosAdapter.getDataItems().get(i);
                for(int j = 0;j < idsTipoAvisos.length;j++){
                    String ids = idsTipoAvisos[j];
                    try {
                        int id = Integer.parseInt(ids);
                        if(id == tipoAviso.getId()){
                            typeAvisosAdapter.selectedItem(tipoAviso);
                        }
                    }catch (Exception ex){}
                }
            }
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

            mAccountFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAccountFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAccountFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mAccountFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void showMessage(String messageOkOperation) {
        Toast.makeText(this, messageOkOperation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadTipoAvisos(List<TipoAviso> listTipoAvisos) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.typeAvisosAdapter = new TypeAvisosAdapter(this, listTipoAvisos);
        this.recyclerType.setLayoutManager(linearLayoutManager);
        this.recyclerType.setAdapter(this.typeAvisosAdapter);
        this.recyclerType.setItemAnimator(new DefaultItemAnimator());

        loadSelectedTipoAvisos();
    }

    @Override
    public void loadTransaccionAvisos(List<TransaccionAviso> listTransaccionAvisos) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.transactionAvisosAdapter = new TransactionAvisosAdapter(this, listTransaccionAvisos);
        this.recyclerTrans.setLayoutManager(linearLayoutManager);
        this.recyclerTrans.setAdapter(this.transactionAvisosAdapter);
        this.recyclerTrans.setItemAnimator(new DefaultItemAnimator());

        loadSelectedTransAvisos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_adda_save) {
            updateAccount();
        }

        return super.onOptionsItemSelected(item);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean validateData(String nombre, String apaterno, String amaterno) {
        boolean result = true;
        if(nombre.isEmpty() || nombre.length() > 250){
            result = false;
            txtNombre.setError("Nombre Incorrecto");
        }
        if(apaterno != null){
            if(apaterno.length() > 250){
                result = false;
                txtNombre.setError("Primer Apellido Incorrecto");
            }
        }
        if(amaterno != null){
            if(amaterno.length() > 250){
                result = false;
                txtNombre.setError("Segundo Apellido Incorrecto");
            }
        }

        return result;
    }

}

