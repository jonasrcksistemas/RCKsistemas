package com.example.rcksuporte05.rcksistemas.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.classes.Prospect;
import com.example.rcksuporte05.rcksistemas.interfaces.FotoActivity;
import com.example.rcksuporte05.rcksistemas.util.DatePickerUtil;
import com.example.rcksuporte05.rcksistemas.util.FotoUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RCK 03 on 29/01/2018.
 */

public class CadastroProspectFotoSalvar extends Fragment {

    private Uri uri;

    @BindView(R.id.imagemProspect)
    ImageView imagemProspect;
    @BindView(R.id.edtDataRetorno)
    EditText edtDataRetorno;

    ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_prospect_foto_salvar, container, false);
        ButterKnife.bind(this, view);

        edtDataRetorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerUtil.mostraDatePickerDialog(getContext(), edtDataRetorno);
            }
        });


        return view;
    }

    public void insereDadosDaFrame(){
        if(edtDataRetorno.getText() != null && !edtDataRetorno.getText().toString().equals("")){
            ProspectHelper.getProspect().setDataRetorno(edtDataRetorno.getText().toString().trim());
        }
    }


    @OnClick(R.id.btnSalvarParcial)
    public void salvarParcial(){
        progress = new ProgressDialog(getContext());
        progress.setMessage("Carregando historico financeiro!");
        progress.setCancelable(false);
        progress.show();

        ProspectHelper.getProspect().setIdEmpresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
        ProspectHelper.getProspect().setUsuario_id(UsuarioHelper.getUsuario().getId_usuario());

        Rotas apiRotas = Api.buildRetrofit();
        Map<String, String> cabecalho = new HashMap<>();
        cabecalho.put("AUTHORIZATION", UsuarioHelper.getUsuario().getToken());
        Call<Prospect> call = apiRotas.salvarProspect(cabecalho, ProspectHelper.getProspect());

        call.enqueue(new Callback<Prospect>() {
            @Override
            public void onResponse(Call<Prospect> call, Response<Prospect> response) {
                if(response.code() == 200){
                    Toast.makeText(getContext(), "Foiiiii lek!", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(getContext(), "NÃO FOIIIIII!", Toast.LENGTH_LONG).show();

                progress.dismiss();
            }

            @Override
            public void onFailure(Call<Prospect> call, Throwable t) {
                Toast.makeText(getContext(), "OPS!", Toast.LENGTH_LONG).show();
                progress.dismiss();

            }
        });

    }

    @OnClick(R.id.btnSalvarProspect)
    public void salvarProspect(){
        ProspectHelper.salvarProspect();
    }

    @OnClick(R.id.btnAddFoto)
    public void foto() {
        AlertDialog.Builder fotoAcao = new AlertDialog.Builder(getActivity());
        fotoAcao.setTitle("Escolha a forma de capturar a imagem");
        String[] array = {"Camera", "Galeria"};
        fotoAcao.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        File diretorio = Environment.getExternalStoragePublicDirectory(getString(R.string.app_name) + " visitas");
                        File imagem = new File(diretorio.getPath() + "/" + System.currentTimeMillis() + ".jpg");
                        uri = Uri.fromFile(imagem);

                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        }

                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
                        }

                        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        try {
                            startActivityForResult(intentCamera, 123);
                        } catch (FileUriExposedException e) {
                            Intent captura = new Intent("android.media.action.IMAGE_CAPTURE");
                            try {
                                startActivityForResult(captura, 456);
                            } catch (Exception ex) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 1:
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 789);
                        break;
                }
            }
        });
        fotoAcao.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {

                Intent novaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                getActivity().sendBroadcast(novaIntent);

                Intent intent = new Intent(getActivity(), FotoActivity.class);

                try {
                    imagemProspect.setImageBitmap(FotoUtil.rotateBitmap(BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri)), uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        } else if (requestCode == 456) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                imagemProspect.setImageBitmap(bitmap);
            }
        } else if (requestCode == 789) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        bitmap = FotoUtil.rotateBitmap(BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(data.getData())), data.getData());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imagemProspect.setImageBitmap(bitmap);
                }
            }
        }
    }
}
