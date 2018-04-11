package com.example.rcksuporte05.rcksistemas.activity;

import android.Manifest;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.model.Foto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class FotoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fotoTirada1)
    ImageView fotoTirada1;


    private Foto imagem = new Foto();
    private ProgressDialog progress;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.fotoTirada1)
    public void foto() {
        AlertDialog.Builder fotoAcao = new AlertDialog.Builder(this);
        fotoAcao.setTitle("Escolha a forma de capturar a imagem");
        String[] array = {"Camera", "Galeria"};
        fotoAcao.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:

                        if (ContextCompat.checkSelfPermission(FotoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(FotoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        }else if (ContextCompat.checkSelfPermission(FotoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(FotoActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                        }else {
                            File file = new File(Environment.getExternalStorageDirectory() + "/arquivo.jpg");
                            Uri outputFileUri = Uri.fromFile(file);
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                            try {
                                startActivityForResult(intent, 123);
                            } catch (FileUriExposedException e) {
                                Intent captura = new Intent("android.media.action.IMAGE_CAPTURE");
                                try {
                                    startActivityForResult(captura, 456);
                                } catch (Exception ex) {
                                    e.printStackTrace();
                                }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123){
            progress = new ProgressDialog(FotoActivity.this);
            progress.setMessage("Processando Imagen!");
            progress.setTitle("Aguarde");
            progress.show();
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                Bitmap imageBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/arquivo.jpg", options);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                boolean validaCompressao = imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                byte[] fotoBinario = outputStream.toByteArray();


                String encodedImage = Base64.encodeToString(fotoBinario, Base64.DEFAULT);

                fotoTirada1.setImageBitmap(imageBitmap); // ImageButton, seto a foto como imagem do botão

                boolean isImageTaken = true;
                progress.dismiss();
            } catch (Exception e) {
                Toast.makeText(this, "Picture Not taken",Toast.LENGTH_LONG).show();e.printStackTrace();
            }
        }

    }

    public String BitmapTOBase64(Bitmap imagem) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.PNG, 100, baos); // bm is the bitmap
        byte[] photo = baos.toByteArray();
        return Base64.encodeToString(photo, Base64.NO_WRAP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.subir_foto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.uploadFoto:
                salvarImagem(imagem);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void salvarImagem(final Foto foto) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final ProgressDialog dialog = new ProgressDialog(FotoActivity.this);
                        dialog.setTitle("Atenção");
                        dialog.setMessage("Enviando a imagem");
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                });

                Rotas apiRotas = Api.buildRetrofit();
                Call<Foto> call = apiRotas.salvarImagem(foto);
                try {
                    Response<Foto> response = call.execute();
                    switch (response.code()) {
                        case 200:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.dismiss();
                                    Toast.makeText(FotoActivity.this, "Imagem enviada com sucesso", Toast.LENGTH_SHORT).show();
                                }
                            });
                            finish();
                            break;
                        case 500:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.dismiss();
                                    Toast.makeText(FotoActivity.this, "Problemas ao enviar a imagem", Toast.LENGTH_SHORT).show();
                                }
                            });
                            finish();
                            break;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.dismiss();
                            Toast.makeText(FotoActivity.this, "Sem conexão com a rede!\n\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog.Builder alert = new AlertDialog.Builder(FotoActivity.this);
                    alert.setMessage(e.getMessage());
                    alert.setNeutralButton("OK", null);
                    alert.show();
                }
            }
        });

        thread.start();
    }
}