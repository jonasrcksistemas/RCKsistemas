package com.example.rcksuporte05.rcksistemas.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.VisitaHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by RCK 03 on 21/02/2018.
 */

public class LocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLocation;
    private String mLatitude;
    private String mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this) //Be aware of state of the connection
                .addOnConnectionFailedListener(this) //Be aware of failures
                .addApi(LocationServices.API)
                .build();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                mLocation = location;
                                VisitaHelper.setmLocation(mLocation);
                                finish();
                            }
                        }
                    });


        } else {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        pararConexaoComGoogleApi();
    }

    public void pararConexaoComGoogleApi() {
        //Verificando se está conectado para então cancelar a conexão!
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    /**
     * Depois que o método connect() for chamado, esse método será chamado de forma assíncrona caso a conexão seja bem sucedida.
     *
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {
        //Conexão com o serviços do Google Service API foi estabelecida!
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
           mFusedLocationClient.getLastLocation();

        } else {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

    }

    /**
     * Esse método é chamado quando o client está temporariamente desconectado. Isso pode acontecer quando houve uma falha ou problema com o serviço que faça com que o client seja desligado.
     * Nesse estado, todas as requisições e listeners são cancelados.
     * Não se preocupe em tentar reestabelecer a conexão, pois isso acontecerá automaticamente.
     * As aplicações devem desabilitar recursos visuais que estejam relacionados com o uso dos serviços e habilitá-los novamente quando o método onConnected() for chamado, indicando reestabelecimento da conexão.
     */
    @Override
    public void onConnectionSuspended(int i) {
        // Aguardando o GoogleApiClient reestabelecer a conexão.
    }

    /**
     * Método chamado quando um erro de conexão acontece e não é possível acessar os serviços da Google Service.
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //A conexão com o Google API falhou!
        pararConexaoComGoogleApi();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pegarUltimaLocalizacao();
            } else {
                Toast.makeText(this, "Sem a permissão função indisponivel",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void pegarUltimaLocalizacao(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                mLocation = location;
                                VisitaHelper.setmLocation(mLocation);
                                finish();
                            }
                        }
                    });


        } else {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }




}

