package com.example.rcksuporte05.rcksistemas.interfaces;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.Helper.VisitaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.ApiGeocoder;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.classes.VisitaProspect;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    @BindView(R.id.edtDescricaoVisita)
    EditText edtDescricaoVisita;
    @BindView(R.id.tb_visita)
    Toolbar tb_visita;

    @BindView(R.id.btnCheckinVisita)
    Button btnCheckinVisita;
    @BindView(R.id.btnSalvarVisita)
    Button btnSalvarVisita;


    ProgressDialog progress;

    RespostaGeocoder respostaGeocoder;
    private FusedLocationProviderClient mFusedLocationClient;
    Location mLocation;
    private String[] tipoVisita = {"Presencial","Telefone"};
    DBHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita);
        ButterKnife.bind(this);

        db = new DBHelper(this);

        tb_visita.setTitle("Registro de Visita");

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

        setSupportActionBar(tb_visita);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switch (getIntent().getIntExtra("edicao", 0)){
            case 1:
                edtDescricaoVisita.setFocusable(false);
                edtDataRetornoVisita.setFocusable(false);
                btnCheckinVisita.setEnabled(false);
                btnSalvarVisita.setEnabled(false);
                break;
            case 2:
                injetaDadosNaTela();
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
       super.onResume();
    }

    @OnClick(R.id.btnSalvarVisita)
    public void salvar(){
        VisitaProspect visita = new VisitaProspect();
        boolean verificaObrigatorios = true;

        visita.setProspect(VisitaHelper.getProspect());
        visita.setDataVisita(db.pegaDataAtual());
        visita.setUsuario_id(UsuarioHelper.getUsuario().getId_usuario());
        visita.setTipoContato(tipoVisita[spTipoVisita.getSelectedItemPosition()]);

       if(spTipoVisita.getSelectedItemPosition() == 0) {
        if(edtDescricaoVisita.getText() != null && !edtDescricaoVisita.getText().toString().trim().equals("")){
            visita.setDescricaoVisita(edtDescricaoVisita.getText().toString());
        }else{
            edtDescricaoVisita.setError("Campo Obrigatorio");
            edtDescricaoVisita.requestFocus();
            verificaObrigatorios = false;
        }

        if (edtDataRetornoVisita.getText() != null && !edtDataRetornoVisita.getText().toString().trim().isEmpty()) {
            String dataCapturada = "";
            Calendar dataAtual = new GregorianCalendar();
            Calendar dataRetorno = new GregorianCalendar();
            Date date = new Date();
            dataAtual.setTime(date);

            //pegar data da tela
            try{
                 dataCapturada = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy")
                          .parse(edtDataRetornoVisita.getText().toString().trim()));
            }catch (ParseException e){
                e.printStackTrace();
            }

            //converte no mesmo padrão da capitura da atual
            try {
                dataRetorno.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dataCapturada));
            } catch (ParseException e) {
                e.printStackTrace();
            }
             
            if (dataAtual.after(dataRetorno)){
                edtDataRetornoVisita.setBackgroundResource(R.drawable.borda_edittext_erro);
                Toast.makeText(this, "Data deve ser posterior a atual",Toast.LENGTH_SHORT).show();
                verificaObrigatorios = false;
            }else {
                visita.setDataRetorno(dataCapturada);
            }

        }else {
            edtDataRetornoVisita.setBackgroundResource(R.drawable.borda_edittext_erro);
            Toast.makeText(this, "A Data é Obrigatoria",Toast.LENGTH_SHORT).show();
        }

            if (mLocation == null) {
                if(txtLatitude.getText() != null && txtLatitude.getText().toString().trim().equals("")
                   && txtLongitude.getText() != null && txtLongitude.getText().toString().trim().equals("") ){
                    Toast.makeText(this, "Fazer Check-in é obrigatorio", Toast.LENGTH_SHORT).show();
                    verificaObrigatorios = false;
                }
            }else {
                visita.setLongitude(String.valueOf(mLocation.getLongitude()));
                visita.setLatitude(String.valueOf(mLocation.getLatitude()));
            }

        } else {

            if (edtDescricaoVisita.getText() != null && !edtDescricaoVisita.getText().toString().trim().equals("")) {
                visita.setDescricaoVisita(edtDescricaoVisita.getText().toString());
            } else {
                edtDescricaoVisita.setError("Campo Obrigatorio");
                edtDescricaoVisita.requestFocus();
                verificaObrigatorios = false;
            }
        }


        if (verificaObrigatorios){
            if(db.atualizaTBL_VISITA_PROSPECT(visita)){
                Toast.makeText(this, "Visita salva", Toast.LENGTH_LONG).show();
                finish();
            }else {
                Toast.makeText(this, "Falha ao salvar", Toast.LENGTH_LONG).show();
            }
        }

    }

    @OnClick(R.id.btnCheckinVisita)
    public void checkin(){
        progress = new ProgressDialog(ActivityVisita.this);
        progress.setMessage("Fazendo Check-in");
        progress.setTitle("Aguarde");
        progress.show();

        EnableGPSAutoMatically();
    }

    public void injetaDadosNaTela(){
        try{
            edtDescricaoVisita.setText(VisitaHelper.getVisitaProspect().getDescricaoVisita());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            edtDataRetornoVisita.setText(new SimpleDateFormat("dd/MM/yyyy")
                    .format(new SimpleDateFormat("yyyy-MM-dd")
                            .parse(VisitaHelper.getVisitaProspect().getDataRetorno())));
        }catch (Exception e){
            e.printStackTrace();
        }

        if(VisitaHelper.getVisitaProspect().getLatitude() != null && VisitaHelper.getVisitaProspect().getLongitude() != null){
            txtLongitude.setText(VisitaHelper.getVisitaProspect().getLongitude());
            txtLatitude.setText(VisitaHelper.getVisitaProspect().getLatitude());
            txtLatitude.setVisibility(View.VISIBLE);
            txtLongitude.setVisibility(View.VISIBLE);
        }

        if(VisitaHelper.getVisitaProspect().getTipoContato() != null && VisitaHelper.getVisitaProspect().getTipoContato().equals(tipoVisita[0])){
            spTipoVisita.setSelection(0);
        }else {
            spTipoVisita.setSelection(1);
        }

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
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                progress = new ProgressDialog(ActivityVisita.this);
                progress.setMessage("Fazendo Check-in");
                progress.setTitle("Aguarde");
                progress.show();
                pegarUltimaLocalizacao();
            } else {
                Toast.makeText(this, "Sem a permissão função indisponivel",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode != 0){
                Toast.makeText(ActivityVisita.this, "Tente Novamente", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(ActivityVisita.this, "Sem Localização ativa, recurso indisponível", Toast.LENGTH_LONG).show();
            }

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

}
