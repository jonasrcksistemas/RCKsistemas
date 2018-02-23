package com.example.rcksuporte05.rcksistemas.interfaces;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.VisitaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.ApiGeocoder;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.util.DatePickerUtil;
import com.example.rcksuporte05.rcksistemas.util.classesGeocoderUtil.RespostaGeocoder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RCK 03 on 21/02/2018.
 */

public class ActivityVisita extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    @BindView(R.id.txtLatitude)
    TextView txtLatitude;
    @BindView(R.id.txtLongitude)
    TextView txtLongitude;
    @BindView(R.id.txtChekinEndereco)
    TextView txtChekinEndereco;
    @BindView(R.id.edtDataRetornoVisita)
    EditText edtDataRetornoVisita;
    @BindView(R.id.spTipoVisita)
    Spinner spTipoVisita;

    ProgressDialog progress;

    RespostaGeocoder respostaGeocoder;
    private FusedLocationProviderClient mFusedLocationClient;
    Location mLocation;
    private String[] tipoVisita = {"Presencial","Telefone"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita);
        ButterKnife.bind(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        txtLongitude.setVisibility(View.GONE);
        txtLongitude.setVisibility(View.GONE);
        txtChekinEndereco.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_activated_1,tipoVisita);
        spTipoVisita.setAdapter(adapter);

        edtDataRetornoVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerUtil.mostraDatePickerDialog(ActivityVisita.this, edtDataRetornoVisita);
                edtDataRetornoVisita.setBackgroundResource(R.drawable.borda_edittext);
            }
        });

    }

    @Override
    protected void onResume() {
       super.onResume();
    }

    @OnClick(R.id.btnSalvarVisita)
    public void salvar(){

    }

    @OnClick(R.id.btnCheckinVisita)
    public void checkin(){
        progress = new ProgressDialog(ActivityVisita.this);
        progress.setMessage("Fazendo Check-in");
        progress.setTitle("Aguarde");
        progress.show();

        EnableGPSAutoMatically();
    }

    @Override
    protected void onDestroy() {
        VisitaHelper.limpaVisitaHelper();
        System.gc();
        super.onDestroy();
    }

    public void getGeocoder(){
        Rotas rotas = ApiGeocoder.buildRetrofit();
        Call<RespostaGeocoder> call = rotas.getGeocoder(String.valueOf(mLocation.getLatitude())+","+String.valueOf(mLocation.getLongitude()), true, "pt-BR");

        call.enqueue(new Callback<RespostaGeocoder>() {
            @Override
            public void onResponse(Call<RespostaGeocoder> call, Response<RespostaGeocoder> response) {
                respostaGeocoder = response.body();
                if(response.body().getResult().size() > 0) {
                    txtChekinEndereco.setVisibility(View.VISIBLE);
                    txtChekinEndereco.setText(respostaGeocoder.getResult().get(1).getFormattedAddress());
                }else {
                    txtLatitude.setVisibility(View.VISIBLE);
                    txtLongitude.setVisibility(View.VISIBLE);
                    txtLatitude.setText(String.valueOf(mLocation.getLatitude()));
                    txtLongitude.setText(String.valueOf(mLocation.getLongitude()));
                    Toast.makeText(ActivityVisita.this,"Endereço não encontrado! somente latitude e longitude", Toast.LENGTH_LONG).show();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<RespostaGeocoder> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ActivityVisita.this,"Falha ao requisitar", Toast.LENGTH_LONG).show();
            }
        });
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
                                getGeocoder();
                            }
                        }
                    });
        } else {
            progress.dismiss();
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pegarUltimaLocalizacao();
            } else {
                finish();
                Toast.makeText(this, "Sem a permissão função indisponivel",Toast.LENGTH_LONG).show();
            }
        }
    }


    private void EnableGPSAutoMatically() {
        GoogleApiClient googleApiClient = null;
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();

            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();

            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            pegarUltimaLocalizacao();
                            // All location settings are satisfied. The client can
                            // initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be
                            // fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(),
                                // and check the result in onActivityResult().
                                progress.dismiss();
                                status.startResolutionForResult(ActivityVisita.this, 1);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have
                            // no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
