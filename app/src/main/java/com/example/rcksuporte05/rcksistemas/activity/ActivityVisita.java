package com.example.rcksuporte05.rcksistemas.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.DAO.CadastroAnexoDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.Helper.VisitaHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.ApiGeocoder;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.model.VisitaProspect;
import com.example.rcksuporte05.rcksistemas.util.DatePickerUtil;
import com.example.rcksuporte05.rcksistemas.util.FotoUtil;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RCK 03 on 21/02/2018.
 */

public class ActivityVisita extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    static int REQUEST_CODE_IMAGEM_1 = 798;
    static int REQUEST_CODE_IMAGEM_2 = 987;

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
    @BindView(R.id.edtTitulo)
    EditText edtTitulo;
    @BindView(R.id.edtDescricaoAcao)
    EditText edtDescricaoVisita;
    @BindView(R.id.tb_visita)
    Toolbar tb_visita;
    @BindView(R.id.btnCheckinVisita)
    Button btnCheckinVisita;
    @BindView(R.id.btnSalvarVisita)
    Button btnSalvarVisita;
    @BindView(R.id.imagem1)
    ImageButton imagem1;
    @BindView(R.id.imagem2)
    ImageButton imagem2;
    boolean verificaObrigatorios;
    Bitmap mImagem1;
    Bitmap mImagem2;
    ProgressDialog progress;
    RespostaGeocoder respostaGeocoder;
    Location mLocation;
    DBHelper db;
    private Uri uri;
    private FusedLocationProviderClient mFusedLocationClient;
    private String[] tipoVisita = {"Presencial", "Telefone"};

    @OnClick(R.id.imagem1)
    public void chamarGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), REQUEST_CODE_IMAGEM_1);
    }

    @OnClick(R.id.imagem2)
    public void chamarGaleria2() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), REQUEST_CODE_IMAGEM_2);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita);
        ButterKnife.bind(this);

        db = new DBHelper(this);

        tb_visita.setTitle("Registro de Visita");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        imagem1.setImageResource(R.mipmap.ic_add_imagem);
        imagem2.setImageResource(R.mipmap.ic_add_imagem);
        txtLongitude.setVisibility(View.GONE);
        txtLongitude.setVisibility(View.GONE);
        txtChekinEndereco.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, tipoVisita);
        spTipoVisita.setAdapter(adapter);

        edtDataRetornoVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerUtil.mostraDatePickerDialog(ActivityVisita.this, edtDataRetornoVisita);
                edtDataRetornoVisita.setBackgroundResource(R.drawable.borda_edittext);
            }
        });

        edtDataRetornoVisita.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityVisita.this);
                alert.setMessage("Deseja limpar o campo da data de retorno?");
                alert.setNegativeButton("NÃO", null);
                alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edtDataRetornoVisita.setText("  /  /    ");
                    }
                });
                alert.show();
                return false;
            }
        });

        try {
            if (db.contagem("SELECT COUNT(*) FROM TBL_CADASTRO_ANEXOS WHERE ID_CADASTRO = " + VisitaHelper.getVisitaProspect().getIdVisita() + " AND ID_ENTIDADE = 11;") > 0) {
                CadastroAnexoDAO cadastroAnexoDAO = new CadastroAnexoDAO(db);
                List<CadastroAnexo> listaCadastroAnexo = cadastroAnexoDAO.listaCadastroAnexoProspectAcao(Integer.parseInt(VisitaHelper.getVisitaProspect().getIdVisita()));

                for (CadastroAnexo cadastroAnexo : listaCadastroAnexo) {
                    if (cadastroAnexo.getPrincipal().equals("S"))
                        VisitaHelper.getVisitaProspect().setFotoPrincipalBase64(cadastroAnexo);
                    else
                        VisitaHelper.getVisitaProspect().setFotoSecundariaBase64(cadastroAnexo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setSupportActionBar(tb_visita);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int acao = getIntent().getIntExtra("acao", 0);
        switch (acao) {
            case 1:
                injetaDadosNaTela();
                edtTitulo.setFocusable(false);
                edtDescricaoVisita.setFocusable(false);
                edtDataRetornoVisita.setEnabled(false);
                imagem1.setEnabled(false);
                imagem2.setEnabled(false);
                btnCheckinVisita.setEnabled(false);
                btnSalvarVisita.setVisibility(View.GONE);
                spTipoVisita.setEnabled(false);
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
    public void salvar() {
        VisitaProspect visita;
        if (VisitaHelper.getVisitaProspect() != null)
            visita = VisitaHelper.getVisitaProspect();
        else
            visita = new VisitaProspect();
        verificaObrigatorios = true;

        visita.setProspect(VisitaHelper.getProspect());
        visita.setDataVisita(db.pegaDataAtual());
        visita.setUsuario_id(UsuarioHelper.getUsuario().getId_usuario());
        visita.setTipoContato(tipoVisita[spTipoVisita.getSelectedItemPosition()]);

        if (spTipoVisita.getSelectedItemPosition() == 0) {
            if (edtTitulo.getText() != null && !edtTitulo.getText().toString().trim().equals("")) {
                visita.setTitulo(edtTitulo.getText().toString());
            } else {
                edtTitulo.setError("Campo Obrigatorio");
                edtTitulo.requestFocus();
                verificaObrigatorios = false;
            }

            if (edtDescricaoVisita.getText() != null && !edtDescricaoVisita.getText().toString().trim().equals("")) {
                visita.setDescricaoVisita(edtDescricaoVisita.getText().toString());
            } else {
                edtDescricaoVisita.setError("Campo Obrigatorio");
                edtDescricaoVisita.requestFocus();
                verificaObrigatorios = false;
            }
            String dataRetorno = dataRetorno();
            if (dataRetorno != null) {
                visita.setDataRetorno(dataRetorno);
            } else {
                edtDataRetornoVisita.setBackgroundResource(R.drawable.borda_edittext_erro);
                Toast.makeText(this, "A Data é Obrigatoria", Toast.LENGTH_SHORT).show();
            }

            if (mLocation != null) {
                visita.setLongitude(String.valueOf(mLocation.getLongitude()));
                visita.setLatitude(String.valueOf(mLocation.getLatitude()));
            }

        } else {

            String dataRetorno = dataRetorno();
            if (dataRetorno != null) {
                visita.setDataRetorno(dataRetorno);
            }

            if (edtTitulo.getText() != null && !edtTitulo.getText().toString().trim().equals("")) {
                visita.setDescricaoVisita(edtTitulo.getText().toString());
            } else {
                edtTitulo.setError("Campo Obrigatorio");
                edtTitulo.requestFocus();
                verificaObrigatorios = false;
            }

            if (edtDescricaoVisita.getText() != null && !edtDescricaoVisita.getText().toString().trim().equals("")) {
                visita.setDescricaoVisita(edtDescricaoVisita.getText().toString());
            } else {
                edtDescricaoVisita.setError("Campo Obrigatorio");
                edtDescricaoVisita.requestFocus();
                verificaObrigatorios = false;
            }
        }


        if (verificaObrigatorios) {
            if (db.atualizaTBL_VISITA_PROSPECT(visita) != null) {
                Toast.makeText(this, "Visita salva", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Falha ao salvar", Toast.LENGTH_LONG).show();
            }
        }

    }

    private String dataRetorno() {
        if (edtDataRetornoVisita.getText() != null && !edtDataRetornoVisita.getText().toString().trim().isEmpty()) {
            String dataCapturada = "";
            Calendar dataAtual = new GregorianCalendar();
            Calendar dataRetorno = new GregorianCalendar();
            Date date = new Date();
            dataAtual.setTime(date);

            //pegar data da tela
            try {
                dataCapturada = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy")
                        .parse(edtDataRetornoVisita.getText().toString().trim()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //converte no mesmo padrão da captura da atual
            try {
                dataRetorno.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dataCapturada));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (dataAtual.after(dataRetorno)) {
                edtDataRetornoVisita.setBackgroundResource(R.drawable.borda_edittext_erro);
                Toast.makeText(this, "Data deve ser posterior a atual", Toast.LENGTH_SHORT).show();
                verificaObrigatorios = false;
            } else {
                return dataCapturada;
            }
        }
        return null;
    }

    @OnClick(R.id.btnCheckinVisita)
    public void checkin() {
        progress = new ProgressDialog(ActivityVisita.this);
        progress.setMessage("Fazendo Check-in");
        progress.setTitle("Aguarde");
        progress.show();

        EnableGPSAutoMatically();
    }

    public void injetaDadosNaTela() {
        try {
            edtTitulo.setText(VisitaHelper.getVisitaProspect().getTitulo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            edtDescricaoVisita.setText(VisitaHelper.getVisitaProspect().getDescricaoVisita());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (VisitaHelper.getImagem1() != null) {
            mImagem1 = VisitaHelper.getImagem1();
            imagem1.setImageBitmap(VisitaHelper.getImagem1());
        } else {
            try {
                byte[] data = Base64.decode(VisitaHelper.getVisitaProspect().getFotoPrincipalBase64().getAnexo(), Base64.NO_WRAP);
                mImagem1 = BitmapFactory.decodeByteArray(data, 0, data.length);
                VisitaHelper.setImagem1(mImagem1);
                imagem1.setImageBitmap(Bitmap.createScaledBitmap(mImagem1, 220, 230, false));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (VisitaHelper.getImagem2() != null) {
            mImagem2 = VisitaHelper.getImagem2();
            imagem2.setImageBitmap(VisitaHelper.getImagem2());
        } else {
            try {
                byte[] data2 = Base64.decode(VisitaHelper.getVisitaProspect().getFotoSecundariaBase64().getAnexo(), Base64.NO_WRAP);
                mImagem2 = BitmapFactory.decodeByteArray(data2, 0, data2.length);
                VisitaHelper.setImagem2(mImagem2);
                imagem2.setImageBitmap(Bitmap.createScaledBitmap(mImagem2, 220, 230, false));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            edtDataRetornoVisita.setText(new SimpleDateFormat("dd/MM/yyyy")
                    .format(new SimpleDateFormat("yyyy-MM-dd")
                            .parse(VisitaHelper.getVisitaProspect().getDataRetorno())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (VisitaHelper.getVisitaProspect().getLatitude() != null && VisitaHelper.getVisitaProspect().getLongitude() != null) {
            progress = new ProgressDialog(ActivityVisita.this);
            progress.setMessage("Requisitando Endereço");
            progress.setTitle("Aguarde");
            progress.show();
            getGeocoder();
        }

        if (VisitaHelper.getVisitaProspect().getTipoContato() != null && VisitaHelper.getVisitaProspect().getTipoContato().equals(tipoVisita[0])) {
            spTipoVisita.setSelection(0);
        } else {
            spTipoVisita.setSelection(1);
        }

    }

    public void getGeocoder() {
        Rotas rotas = ApiGeocoder.buildRetrofit();
        Call<RespostaGeocoder> call;
        if (mLocation != null) {
            call = rotas.getGeocoder(String.valueOf(mLocation.getLatitude()) + "," + String.valueOf(mLocation.getLongitude()), true, "pt-BR");
        } else {
            call = rotas.getGeocoder(VisitaHelper.getVisitaProspect().getLatitude() + "," + VisitaHelper.getVisitaProspect().getLongitude(), true, "pt-BR");
        }


        call.enqueue(new Callback<RespostaGeocoder>() {
            @Override
            public void onResponse(Call<RespostaGeocoder> call, Response<RespostaGeocoder> response) {
                respostaGeocoder = response.body();
                if (response.body().getResult().size() > 0) {
                    txtChekinEndereco.setVisibility(View.VISIBLE);
                    txtChekinEndereco.setText(respostaGeocoder.getResult().get(1).getFormattedAddress());
                } else {
                    txtLatitude.setVisibility(View.VISIBLE);
                    txtLongitude.setVisibility(View.VISIBLE);
                    txtLatitude.setText(String.valueOf(mLocation.getLatitude()));
                    txtLongitude.setText(String.valueOf(mLocation.getLongitude()));
                    Toast.makeText(ActivityVisita.this, "Endereço não encontrado! somente latitude e longitude", Toast.LENGTH_LONG).show();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<RespostaGeocoder> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ActivityVisita.this, "Falha ao requisitar", Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void pegarUltimaLocalizacao() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            //Tenho a última localização conhecida. Em algumas situações raras, isso pode ser nulo.
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                progress = new ProgressDialog(ActivityVisita.this);
                progress.setMessage("Fazendo Check-in");
                progress.setTitle("Aguarde");
                progress.show();
                pegarUltimaLocalizacao();
            } else {
                Toast.makeText(this, "Sem a permissão função indisponivel", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode != 0) {
                Toast.makeText(ActivityVisita.this, "Tente Novamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ActivityVisita.this, "Sem Localização ativa, recurso indisponível", Toast.LENGTH_LONG).show();
            }

        }

        Bitmap bitmap = null;
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {

                Intent novaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                ActivityVisita.this.sendBroadcast(novaIntent);

                Intent intent = new Intent(ActivityVisita.this, FotoActivity.class);

                try {
                    imagem1.setImageBitmap(FotoUtil.rotateBitmap(BitmapFactory.decodeStream(ActivityVisita.this.getContentResolver().openInputStream(uri)), uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        } else if (requestCode == 456) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                imagem1.setImageBitmap(bitmap);
            }
        } else if (requestCode == REQUEST_CODE_IMAGEM_1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        bitmap = FotoUtil.rotateBitmap(BitmapFactory.decodeStream(ActivityVisita.this.getContentResolver().openInputStream(data.getData())), data.getData());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    Bitmap testeDeDimuir = Bitmap.createScaledBitmap(bitmap, 220, 230, false);
                    boolean validaCompressao = bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                    byte[] fotoBinario = outputStream.toByteArray();

                    mImagem1 = bitmap;
                    if (VisitaHelper.getVisitaProspect().getFotoPrincipalBase64() == null)
                        VisitaHelper.getVisitaProspect().setFotoPrincipalBase64(new CadastroAnexo());
                    VisitaHelper.getVisitaProspect().getFotoPrincipalBase64().setAnexo(Base64.encodeToString(fotoBinario, Base64.NO_WRAP));
                    VisitaHelper.setImagem1(testeDeDimuir);
                    imagem1.setImageBitmap(testeDeDimuir);
                }
            }
        } else if (requestCode == REQUEST_CODE_IMAGEM_2) {
            if (data != null) {
                try {
                    bitmap = FotoUtil.rotateBitmap(BitmapFactory.decodeStream(ActivityVisita.this.getContentResolver().openInputStream(data.getData())), data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Bitmap testeDeDimuir = Bitmap.createScaledBitmap(bitmap, 220, 230, false);
                boolean validaCompressao = bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                byte[] fotoBinario = outputStream.toByteArray();


                if (VisitaHelper.getVisitaProspect().getFotoSecundariaBase64() == null)
                    VisitaHelper.getVisitaProspect().setFotoSecundariaBase64(new CadastroAnexo());
                VisitaHelper.getVisitaProspect().getFotoSecundariaBase64().setAnexo(Base64.encodeToString(fotoBinario, Base64.NO_WRAP));
                VisitaHelper.setImagem2(testeDeDimuir);
                mImagem2 = bitmap;
                imagem2.setImageBitmap(testeDeDimuir);
            }
        } else if (requestCode == 1) {
            if (resultCode != 0) {
                Toast.makeText(ActivityVisita.this, "Tente Novamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ActivityVisita.this, "Sem Localização ativa, recurso indisponível", Toast.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
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
                @RequiresApi(api = Build.VERSION_CODES.M)
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
    protected void onDestroy() {
        VisitaHelper.limpaVisitaHelper();
        super.onDestroy();
    }
}
