package com.example.rcksuporte05.rcksistemas.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.ApiGeocoder;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.Prospect;
import com.example.rcksuporte05.rcksistemas.util.MascaraUtil;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityValidaProspect extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtEndereco)
    TextView txtEndereco;
    @BindView(R.id.edtCpfCnpj)
    EditText edtCpfCnpj;
    @BindView(R.id.edtTelefone)
    EditText edtTelefone;

    ProgressDialog progress;
    private Location mLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private RespostaGeocoder respostaGeocoder;

    private DBHelper db;

    @OnClick(R.id.btnLocaliza)
    public void localiza() {
        progress = new ProgressDialog(ActivityValidaProspect.this);
        progress.setMessage("Fazendo Check-in");
        progress.setTitle("Aguarde");
        progress.show();

        EnableGPSAutoMatically();
    }

    @OnClick(R.id.btnVerificar)
    public void verificar() {
        if (!edtCpfCnpj.getText().toString().equals("") || !txtEndereco.getText().toString().equals("") || !edtTelefone.getText().toString().equals("")) {
            Prospect prospect = new Prospect();
            boolean verifica = false;
            if (!edtCpfCnpj.getText().toString().equals("")) {
                if (validaCpfCnpj()) {
                    prospect.setCpf_cnpj(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""));
                    verifica = true;
                }
            } else {
                if (!txtEndereco.getText().toString().equals("")) {
                    prospect.setEnderecoGps(txtEndereco.getText().toString());
                    verifica = true;
                }
                if (validaTelefone()) {
                    prospect.setTelefone(edtTelefone.getText().toString());
                    verifica = true;
                }
            }

            if (verifica) {
                if (verificarProspect()) {
                    if (verificaConexao()) {
                        validaProspect(prospect);
                    } else {
                        prosseguirCadastro();
                    }
                }
            }
        } else {
            Toast.makeText(ActivityValidaProspect.this, "Você precisa informar umas das opções acima!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valida_prospect);
        ButterKnife.bind(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        db = new DBHelper(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public boolean validaCpfCnpj() {
        if (edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "").length() == 11) {
            if (MascaraUtil.isValidCPF(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""))) {
                edtCpfCnpj.setText(MascaraUtil.mascaraCPF(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "")));
                return true;
            } else {
                edtCpfCnpj.setError("CPF Inválido");
                return false;
            }
        } else if (edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "").length() == 14) {
            if (MascaraUtil.isValidCNPJ(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""))) {
                edtCpfCnpj.setText(MascaraUtil.mascaraCNPJ(edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "")));
                return true;
            } else {
                edtCpfCnpj.setError("CNPJ Inválido");
                return false;
            }
        } else {
            edtCpfCnpj.setError("Tamanho do CNPJ/CPF inválido");
            return false;
        }
    }

    public boolean verificarProspect() {
        Boolean retorno = false;
        if (!edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "").equals("")) {
            if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO WHERE CPF_CNPJ = " + edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "")) <= 0) {
                retorno = true;
            } else {
                Cliente cliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE CPF_CNPJ = " + edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "")).get(0);
                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityValidaProspect.this);
                alert.setTitle("Atenção");
                String cpfCnpj;
                switch (edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "").length()) {
                    case 11:
                        cpfCnpj = "CPF";
                        break;
                    case 14:
                        cpfCnpj = "CNPJ";
                        break;
                    default:
                        cpfCnpj = "CNPJ/CPF";
                        break;
                }
                alert.setMessage(cpfCnpj + " já cadastrado para este CLIENTE.\nCodigo: " + cliente.getId_cadastro() + "\nNome: " + cliente.getNome_cadastro() + "\nNome fantasia: " + cliente.getNome_fantasia());
                alert.setNeutralButton("OK", null);
                alert.show();
                return false;
            }

            if (retorno) {
                if (db.contagem("SELECT COUNT(*) FROM TBL_PROSPECT WHERE CPF_CNPJ = " + edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "")) <= 0) {
                    retorno = true;
                } else {
                    Prospect prospect = db.buscaProspectEspecifico("SELECT * FROM TBL_PROSPECT WHERE CPF_CNPJ = " + edtCpfCnpj.getText().toString().replaceAll("[^0-9]", ""));
                    AlertDialog.Builder alert = new AlertDialog.Builder(ActivityValidaProspect.this);
                    alert.setTitle("Atenção");
                    String cpfCnpj;
                    switch (edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "").length()) {
                        case 11:
                            cpfCnpj = "CPF";
                            break;
                        case 14:
                            cpfCnpj = "CNPJ";
                            break;
                        default:
                            cpfCnpj = "CNPJ/CPF";
                            break;
                    }
                    alert.setMessage(cpfCnpj + " já cadastrado para este PROSPECT.\nCodigo: " + prospect.getId_prospect() + " (Na memoria de seu aparelho)\nNome: " + prospect.getNome_cadastro() + "\nNome fantasia: " + prospect.getNome_fantasia());
                    alert.setNeutralButton("OK", null);
                    alert.show();
                    return false;
                }
            }
        } else {
            if (!edtTelefone.getText().toString().equals("")) {
                if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO WHERE TELEFONE_PRINCIPAL LIKE '%" + edtTelefone.getText().toString() + "%' OR TELEFONE_DOIS LIKE '%" + edtTelefone.getText().toString() + "%' OR TELEFONE_TRES LIKE '%" + edtTelefone.getText().toString() + "%'") <= 0) {
                    retorno = true;
                } else {
                    Cliente cliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE TELEFONE_PRINCIPAL LIKE '%" + edtTelefone.getText().toString() + "%' OR TELEFONE_DOIS LIKE '%" + edtTelefone.getText().toString() + "%' OR TELEFONE_TRES LIKE '%" + edtTelefone.getText().toString() + "%'").get(0);
                    AlertDialog.Builder alert = new AlertDialog.Builder(ActivityValidaProspect.this);
                    alert.setTitle("Atenção");
                    alert.setMessage("Telefone já cadastrado para este CLIENTE.\nCodigo: " + cliente.getId_cadastro() + "\nNome: " + cliente.getNome_cadastro() + "\nNome fantasia: " + cliente.getNome_fantasia());
                    alert.setNeutralButton("OK", null);
                    alert.show();
                    return false;
                }

                if (retorno) {
                    if (db.contagem("SELECT COUNT(*) FROM TBL_PROSPECT WHERE TELEFONE LIKE '%" + edtTelefone.getText().toString() + "%'") <= 0) {
                        retorno = true;
                    } else {
                        Prospect prospect = db.buscaProspectEspecifico("SELECT * FROM TBL_PROSPECT WHERE TELEFONE LIKE '%" + edtTelefone.getText().toString() + "%'");
                        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityValidaProspect.this);
                        alert.setTitle("Atenção");
                        alert.setMessage("Telefone já cadastrado para este PROSPECT.\nCodigo: " + prospect.getId_prospect() + " (Na memoria de seu aparelho)\nNome: " + prospect.getNome_cadastro() + "\nNome fantasia: " + prospect.getNome_fantasia());
                        alert.setNeutralButton("OK", null);
                        alert.show();
                        return false;
                    }
                }
            }

            if (!txtEndereco.getText().toString().equals("")) {
                if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO WHERE ENDERECO_GPS = '" + txtEndereco.getText().toString() + "'") <= 0) {
                    retorno = true;
                } else {
                    Cliente cliente = db.listaCliente("SELECT * FROM TBL_CADASTRO WHERE ENDERECO_GPS = '" + txtEndereco.getText().toString() + "'").get(0);
                    AlertDialog.Builder alert = new AlertDialog.Builder(ActivityValidaProspect.this);
                    alert.setTitle("Atenção");
                    alert.setMessage("Endereço já cadastrado para este CLIENTE.\nCodigo: " + cliente.getId_cadastro() + "\nNome: " + cliente.getNome_cadastro() + "\nNome fantasia: " + cliente.getNome_fantasia());
                    alert.setNeutralButton("OK", null);
                    alert.show();
                    return false;
                }

                if (retorno) {
                    if (db.contagem("SELECT COUNT(*) FROM TBL_PROSPECT WHERE ENDERECO_GPS = ' " + txtEndereco.getText().toString() + "'") <= 0) {
                        retorno = true;
                    } else {
                        Prospect prospect = db.buscaProspectEspecifico("SELECT * FROM TBL_PROSPECT WHERE ENDERECO_GPS = ' " + txtEndereco.getText().toString() + "'");
                        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityValidaProspect.this);
                        alert.setTitle("Atenção");
                        alert.setMessage("Endereço já cadastrado para este PROSPECT.\nCodigo: " + prospect.getId_prospect() + " (Na memoria de seu aparelho)\nNome: " + prospect.getNome_cadastro() + "\nNome fantasia: " + prospect.getNome_fantasia());
                        alert.setNeutralButton("OK", null);
                        alert.show();
                        return false;
                    }
                }
            }
        }
        return retorno;
    }

    public boolean validaTelefone() {
        String telefone = edtTelefone.getText().toString();
        if (!telefone.equals("")) {
            if (telefone.length() >= 8 && telefone.length() <= 11) {
                return true;
            } else {
                edtTelefone.setError("Telefone inválido");
                return false;
            }
        } else {
            return false;
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
                                status.startResolutionForResult(ActivityValidaProspect.this, 1);
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

    @SuppressLint("NewApi")
    public void pegarUltimaLocalizacao() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(ActivityValidaProspect.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            //Tenho a última localização conhecida. Em algumas situações raras, isso pode ser nulo.
                            if (location != null) {
                                mLocation = location;
                                getGeocoder();
                            }
                        }
                    });
        } else {
            progress.dismiss();
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    public void getGeocoder() {
        Rotas rotas = ApiGeocoder.buildRetrofit();
        Call<RespostaGeocoder> call = null;

        if (mLocation != null) {
            call = rotas.getGeocoder(String.valueOf(mLocation.getLatitude()) + "," + String.valueOf(mLocation.getLongitude()), true, "pt-BR");
        }

        call.enqueue(new Callback<RespostaGeocoder>() {
            @Override
            public void onResponse(Call<RespostaGeocoder> call, Response<RespostaGeocoder> response) {
                respostaGeocoder = response.body();
                if (response.body().getResult().size() > 0) {
                    txtEndereco.setVisibility(View.VISIBLE);
                    txtEndereco.setText(respostaGeocoder.getResult().get(1).getFormattedAddress());
                } else {
                    txtEndereco.setVisibility(View.VISIBLE);
                    if (mLocation != null) {
                        txtEndereco.setText(String.valueOf(mLocation.getLatitude()) + "\n" + String.valueOf(mLocation.getLongitude()));
                    }

                    Toast.makeText(ActivityValidaProspect.this, "Endereço não encontrado! somente latitude e longitude", Toast.LENGTH_LONG).show();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<RespostaGeocoder> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ActivityValidaProspect.this, "Falha ao requisitar", Toast.LENGTH_LONG).show();
            }
        });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                progress = new ProgressDialog(ActivityValidaProspect.this);
                progress.setMessage("Fazendo Check-in");
                progress.setTitle("Aguarde");
                progress.show();
                pegarUltimaLocalizacao();
            } else {
                Toast.makeText(ActivityValidaProspect.this, "Sem a permissão, função indisponivel!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void validaProspect(Prospect prospect) {
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());

        Rotas apiRetrofit = Api.buildRetrofit();
        Call<Prospect> call = apiRetrofit.validaProspect(prospect, cabecalho);

        call.enqueue(new Callback<Prospect>() {
            @Override
            public void onResponse(Call<Prospect> call, Response<Prospect> response) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityValidaProspect.this);
                switch (response.code()) {
                    case 200:
                        alert.setTitle("Atenção");
                        String cpfCnpj;
                        switch (response.body().getCpf_cnpj().replaceAll("[^0-9]", "").length()) {
                            case 11:
                                cpfCnpj = "CPF";
                                break;
                            case 14:
                                cpfCnpj = "CNPJ";
                                break;
                            default:
                                cpfCnpj = "CNPJ/CPF";
                                break;
                        }
                        alert.setMessage("Já existe um cadastrado com essa informação.\nCodigo: " + response.body().getId_prospect_servidor() + "\n" + cpfCnpj + ": " + response.body().getCpf_cnpj() + "\nNome: " + response.body().getNome_cadastro() + "\nNome fantasia: " + response.body().getNome_fantasia());
                        alert.setNeutralButton("OK", null);
                        alert.show();
                        break;
                    case 204:
                        prosseguirCadastro();
                        break;
                    case 500:
                        alert.setTitle("Atenção!");
                        alert.setMessage("Houve um erro ao consultar o servidor, se persistir, entre em contato com o pessoal responsável.");
                        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                prosseguirCadastro();
                            }
                        });
                        alert.show();
                        break;
                    default:
                        Toast.makeText(ActivityValidaProspect.this, "Erro não catalogado: " + response.code(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Prospect> call, Throwable t) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityValidaProspect.this);
                alert.setTitle("Atenção");
                alert.setMessage("Sem conexão com o servidor.");
                alert.setNeutralButton("OK", null);
                alert.show();
                t.printStackTrace();
            }
        });
    }

    public void prosseguirCadastro() {
        Prospect prospect = new Prospect();
        prospect.setProspectSalvo("N");
        switch (edtCpfCnpj.getText().toString().replaceAll("[^0-9]", "").length()) {
            case 11:
                prospect.setPessoa_f_j("F");
                break;
            case 14:
                prospect.setPessoa_f_j("J");
                break;

        }
        if (mLocation != null) {
            prospect.setLatitude(String.valueOf(mLocation.getLatitude()));
            prospect.setLongitude(String.valueOf(mLocation.getLongitude()));
        }
        prospect.setCpf_cnpj(edtCpfCnpj.getText().toString());
        ProspectHelper.setProspect(prospect);
        Intent intent = new Intent(ActivityValidaProspect.this, ActivityCadastroProspect.class);
        intent.putExtra("novo", 1);
        startActivity(intent);
        finish();
    }

    public boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }
}
