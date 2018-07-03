package com.example.rcksuporte05.rcksistemas.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.rcksuporte05.rcksistemas.Helper.ClienteHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.CadastroAnexo;
import com.example.rcksuporte05.rcksistemas.util.FotoUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityAddCadastroAnexo extends AppCompatActivity {

    @BindView(R.id.tb_anexo)
    Toolbar tb_anexo;

    @BindView(R.id.edtAnexo)
    EditText edtAnexo;

    @BindView(R.id.imAnexo)
    ImageView imAnexo;

    private Bitmap miniatura;
    private Bitmap bitmap;
    private byte[] fotoBinario;
    private CadastroAnexo cadastroAnexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cadastro_anexo);
        ButterKnife.bind(this);

        tb_anexo.setTitle("Anexo");

        if (getIntent().getIntExtra("vizualizacao", 0) >= 1) {
            edtAnexo.setFocusable(false);
        }

        if (getIntent().getIntExtra("Alteracao", -1) <= -1) {
            cadastroAnexo = new CadastroAnexo();
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 123);
        } else {
            cadastroAnexo = ClienteHelper.getListaCadastroAnexo().get(getIntent().getIntExtra("Alteracao", -1));
            edtAnexo.setText(cadastroAnexo.getNomeAnexo());
            byte[] data = Base64.decode(cadastroAnexo.getAnexo(), Base64.NO_WRAP);
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            imAnexo.setImageBitmap(bitmap);
        }
        setSupportActionBar(tb_anexo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.imAnexo)
    public void chamaGaleria() {
        if (getIntent().getIntExtra("vizualizacao", 0) != 1) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 123);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        bitmap = FotoUtil.rotateBitmap(BitmapFactory.decodeStream(this.getContentResolver().openInputStream(data.getData())), data.getData());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    miniatura = Bitmap.createScaledBitmap(bitmap, 220, 230, false);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                    fotoBinario = outputStream.toByteArray();

                    imAnexo.setImageBitmap(bitmap);
                    imAnexo.setBackgroundColor(Color.TRANSPARENT);
                }
            } else {
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (getIntent().getIntExtra("vizualizacao", 0) != 1) {
            getMenuInflater().inflate(R.menu.menu_produto_pedido, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_salvar_produto:
                cadastroAnexo.setIdEntidade(1);
                if (fotoBinario != null)
                    cadastroAnexo.setAnexo(Base64.encodeToString(fotoBinario, Base64.NO_WRAP));
                if (miniatura != null)
                    cadastroAnexo.setMiniatura(miniatura);

                cadastroAnexo.setIdCadastro(ClienteHelper.getCliente().getId_cadastro());
                cadastroAnexo.setIdCadastroServidor(ClienteHelper.getCliente().getId_cadastro_servidor());

                if (edtAnexo.getText().toString() != null && !edtAnexo.getText().toString().trim().isEmpty()) {
                    cadastroAnexo.setNomeAnexo(edtAnexo.getText().toString());
                    if (getIntent().getIntExtra("Alteracao", -1) <= -1) {
                        if (ClienteHelper.getListaCadastroAnexo() != null) {
                            ClienteHelper.getListaCadastroAnexo().add(cadastroAnexo);
                        } else {
                            List<CadastroAnexo> listaAnexo = new ArrayList<>();
                            listaAnexo.add(cadastroAnexo);
                            ClienteHelper.setListaCadastroAnexo(listaAnexo);
                        }
                    } else {
                        ClienteHelper.getListaCadastroAnexo().set(getIntent().getIntExtra("Alteracao", -1), cadastroAnexo);
                    }
                    finish();
                } else {
                    edtAnexo.setError("É necessário informar a descrição do anexo");
                    edtAnexo.requestFocus();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
