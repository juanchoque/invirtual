package com.ucb.appin.views;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ucb.appin.R;
import com.ucb.appin.data.managerLocalDb.CuentaManager;
import com.ucb.appin.data.model.Cuenta;
import com.ucb.appin.services.SynBackgroundService;
import com.ucb.appin.services.SynForegroundService;
import com.ucb.appin.util.ConstInVirtual;
import com.ucb.appin.util.UtilInVirtual;
import com.ucb.appin.views.activity.AddMyPublicationView;
import com.ucb.appin.views.activity.AdminAccountView;
import com.ucb.appin.views.fragment.PublicationsMapView;
import com.ucb.appin.views.fragment.MyPublicationsView;
import com.ucb.appin.views.fragment.PublicationsView;

public class MainView extends AppCompatActivity implements PublicationsMapView.OnFragmentInteractionListener,
        PublicationsView.OnFragmentInteractionListener,
        MyPublicationsView.OnFragmentInteractionListener {

    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    private int indexFragment = 0;

    private BottomNavigationView navigation;
    private Menu menu;
    private boolean isMultiPanel;
    private SearchView searchView;

    @Override
    @SuppressLint("RestrictedApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //**********************TEST ONLY***************************
        SynForegroundService synForegroundService = new SynForegroundService();
        synForegroundService.syncronizedCloudLocalTipoAvisos();
        synForegroundService.syncronizedCloudLocalTransaccionAvisos();
        //**********************FIN TEST****************************

        //init test only
        Fragment rFragment = new PublicationsView();
        this.loadFragment(rFragment);
        registeredFragments.append(indexFragment, rFragment);
        //////////////////////////////////////////////////////////////////////////////////////////////////

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));

        //esta es otra de las
        Intent intent = new Intent(this, SynBackgroundService.class);
        startService(intent);

        //start jarcode account
        //CuentaManager cuentaManager = new CuentaManager();
        //boolean result = cuentaManager.saveOrUpadateJarcode(this);
        //start insert data

    }

    public void loadFragment(Fragment fragment) {

        try {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_fragment, fragment);
            fragmentTransaction.commit();
        } catch (Exception ex) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //for search
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String searchText) {
                searchAvisos(searchText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        //end for search

        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_add) {
            //verify if exist account
            CuentaManager cuentaManager = new CuentaManager();
            Cuenta cuenta = cuentaManager.getCuenta(this);
            if(cuenta != null){
                Intent intent = new Intent(MainView.this, AddMyPublicationView.class);
                int requestCode = 1; // Or some number you choose
                startActivityForResult(intent, requestCode);
            }
            else{
                Toast.makeText(this, ConstInVirtual.MESSAGE_NOTHING_ACCOUNT, Toast.LENGTH_LONG).show();
            }
        }
        if (id == R.id.menu_cuenta) {
            //verify if exist conextion
            UtilInVirtual utilInVirtual = new UtilInVirtual(this);
            if(utilInVirtual.isOnline()){
                Toast.makeText(this, ConstInVirtual.MESSAGE_OFFLINE, Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent(MainView.this, AdminAccountView.class);
            int requestCode = 2; // Or some number you choose
            startActivityForResult(intent, requestCode);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = new PublicationsView();
            switch (item.getItemId()) {
                case R.id.nav_publications:
                    indexFragment = ConstInVirtual.INDEX_FRAGMENT_AVISOS;
                    hideShowMenu(false);
                    fragment = new PublicationsView();
                    break;
                case R.id.nav_maps:
                    indexFragment = ConstInVirtual.INDEX_FRAGMENT_MAPA;
                    hideShowMenu(false);
                    fragment = new PublicationsMapView();
                    break;
                case R.id.nav_my_publications:
                    indexFragment = ConstInVirtual.INDEX_FRAGMENT_MY_AVISOS;
                    hideShowMenu(true);
                    fragment = new MyPublicationsView();
                    break;
            }
            Log.v("----<>", "......" + indexFragment);
            registeredFragments.append(indexFragment, fragment);

            loadFragment(fragment);

            return false;
        }

    };

    ////////////////////////////////////////////////////////////////////////////////////////////
    public void hideShowMenu(boolean visible) {
        MenuItem menuItem = menu.findItem(R.id.menu_add);
        menuItem.setVisible(visible);
    }

    private void searchAvisos(String searchText) {
        try {
            if(indexFragment == ConstInVirtual.INDEX_FRAGMENT_MY_AVISOS){
                MyPublicationsView fragmentMyPublicationImpl = (MyPublicationsView)this.registeredFragments.get(ConstInVirtual.INDEX_FRAGMENT_MY_AVISOS);
                if(fragmentMyPublicationImpl != null){
                    fragmentMyPublicationImpl.searchAvisos(searchText);
                }
            }
            else if(indexFragment == ConstInVirtual.INDEX_FRAGMENT_MAPA){
                PublicationsMapView publicationsMapView = (PublicationsMapView)this.registeredFragments.get(ConstInVirtual.INDEX_FRAGMENT_MAPA);
                if(publicationsMapView != null){
                    publicationsMapView.searchAvisos(searchText);
                }
            }
            else if(indexFragment == ConstInVirtual.INDEX_FRAGMENT_AVISOS){
                PublicationsView fragmentPublicationImpl = (PublicationsView)this.registeredFragments.get(ConstInVirtual.INDEX_FRAGMENT_AVISOS);
                if(fragmentPublicationImpl != null){
                    fragmentPublicationImpl.searchAvisos(searchText);
                }
            }
        }catch (Exception err){
        }
    }

}
