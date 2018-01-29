package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rcksuporte05.rcksistemas.Helper.FotoHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.api.Api;
import com.example.rcksuporte05.rcksistemas.api.Rotas;
import com.example.rcksuporte05.rcksistemas.classes.Foto;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FotoActivity extends AppCompatActivity {

    @BindView(R.id.foto)
    ImageView foto;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Foto imagem = new Foto();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        ButterKnife.bind(this);

        foto.setImageBitmap(FotoHelper.getFoto());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog = new ProgressDialog(FotoActivity.this);
                        dialog.setTitle("Aguarde");
                        dialog.setMessage("Carregando imagem");
                        dialog.show();
                    }
                });
                imagem.setBase64(BitmapTOBase64(FotoHelper.getFoto()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }
        });
        thread.start();
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

    public void salvarImagem(Foto foto) {
        final ProgressDialog dialog = new ProgressDialog(FotoActivity.this);
        dialog.setTitle("Atenção");
        dialog.setMessage("Enviando a imagem");
        dialog.show();
        Rotas apiRotas = Api.buildRetrofit();
        Call<Foto> call = apiRotas.salvarImagem(foto);
        call.enqueue(new Callback<Foto>() {
            @Override
            public void onResponse(Call<Foto> call, Response<Foto> response) {
                switch (response.code()) {
                    case 200:
                        dialog.dismiss();
                        Toast.makeText(FotoActivity.this, "Imagem enviada com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 500:
                        dialog.dismiss();
                        Toast.makeText(FotoActivity.this, "Problemas ao enviar a imagem", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Foto> call, Throwable t) {
                dialog.dismiss();
                AlertDialog.Builder alert = new AlertDialog.Builder(FotoActivity.this);
                alert.setMessage(t.getMessage());
                alert.setNeutralButton("OK", null);
                alert.show();
                Toast.makeText(FotoActivity.this, "Sem conexão com a rede!\n\n" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}