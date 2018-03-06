package com.example.rcksuporte05.rcksistemas.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.rcksuporte05.rcksistemas.Helper.ProspectHelper;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.extras.DBHelper;
import com.example.rcksuporte05.rcksistemas.interfaces.FotoActivity;
import com.example.rcksuporte05.rcksistemas.util.DatePickerUtil;
import com.example.rcksuporte05.rcksistemas.util.FotoUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RCK 03 on 29/01/2018.
 */

public class CadastroProspectFotoSalvar extends Fragment {

    @BindView(R.id.edtDataRetorno)
    public EditText edtDataRetorno;

    @BindView(R.id.imagemProspect1)
    ImageButton imagemProspect1;

    @BindView(R.id.imagemProspect2)
    ImageButton imagemProspect2;

    @BindView(R.id.btnSalvarParcial)
    Button btnSalvarParcial;

    @BindView(R.id.btnSalvarProspect)
    Button btnSalvarProspect;

    private Uri uri;
    private DBHelper db;
    String encodedImage;

    static int REQUEST_CODE_IMAGEM_1 = 798;
    static int REQUEST_CODE_IMAGEM_2 = 987;
    Bitmap mImagem1;
    Bitmap mImagem2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_prospect_foto_salvar, container, false);
        ButterKnife.bind(this, view);
        db = new DBHelper(getContext());

        imagemProspect1.setImageResource(R.mipmap.ic_add_imagem);
        imagemProspect2.setImageResource(R.mipmap.ic_add_imagem);

        insereDadosNaTela();

        if (ProspectHelper.getProspect().getProspectSalvo() != null && ProspectHelper.getProspect().getProspectSalvo().equals("S")) {
            edtDataRetorno.setFocusable(false);
            btnSalvarParcial.setVisibility(View.INVISIBLE);
            btnSalvarProspect.setVisibility(View.INVISIBLE);

        } else {
            edtDataRetorno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerUtil.mostraDatePickerDialog(getContext(), edtDataRetorno);
                    edtDataRetorno.setBackgroundResource(R.drawable.borda_edittext);
                }
            });
        }


        ProspectHelper.setCadastroProspectFotoSalvar(this);
        return view;
    }

    public void insereDadosDaFrame() {
        try {
            ProspectHelper.getProspect().setDataRetorno(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(edtDataRetorno.getText().toString().trim())));
        } catch (ParseException e) {
            System.out.println(edtDataRetorno.getText().toString().trim());
            e.printStackTrace();
        }

        try {
            ProspectHelper.setImagem1(mImagem1);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            ProspectHelper.setImagem2(mImagem2);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void insereDadosNaTela() {
        try {
            if (ProspectHelper.getProspect().getDataRetorno() != null && !ProspectHelper.getProspect().getDataRetorno().trim().isEmpty())
                edtDataRetorno.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(ProspectHelper.getProspect().getDataRetorno())));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (ProspectHelper.getImagem1() != null) {
            mImagem1 = ProspectHelper.getImagem1();
            imagemProspect1.setImageBitmap(ProspectHelper.getImagem1());
        } else {
            try {
                byte[] data = Base64.decode(ProspectHelper.getProspect().getFotoPrincipalBase64(), Base64.NO_WRAP);
                mImagem1 = BitmapFactory.decodeByteArray(data, 0, data.length);
                ProspectHelper.setImagem1(mImagem1);
                imagemProspect1.setImageBitmap(mImagem1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (ProspectHelper.getImagem2() != null) {
            mImagem2 = ProspectHelper.getImagem2();
            imagemProspect2.setImageBitmap(ProspectHelper.getImagem2());
        } else {
            try {
                byte[] data2 = Base64.decode(ProspectHelper.getProspect().getFotoSecundariaBase64(), Base64.NO_WRAP);
                mImagem2 = BitmapFactory.decodeByteArray(data2, 0, data2.length);
                ProspectHelper.setImagem2(mImagem2);
                imagemProspect2.setImageBitmap(mImagem2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @OnClick(R.id.btnSalvarParcial)
    public void salvarParcial() {
        insereDadosDaFrame();
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Atenção");
        alert.setMessage("Tem certeza que deseja salvar esse prospect para continuar mais tarde?");
        alert.setNegativeButton("NÃO", null);
        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProspectHelper.getProspect().setProspectSalvo("N");
                db.atualizarTBL_PROSPECT(ProspectHelper.getProspect());
                getActivity().finish();
            }
        });
        alert.show();
    }

    @OnClick(R.id.btnSalvarProspect)
    public void salvarProspect() {
        insereDadosDaFrame();
        if (ProspectHelper.salvarProspect()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Atenção");
            alert.setMessage("Tem certeza que deseja salvar esse prospect PERMANENTEMENTE?");
            alert.setNegativeButton("NÃO", null);
            alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ProspectHelper.getProspect().setProspectSalvo("S");
                    ProspectHelper.getProspect().setIdEmpresa(UsuarioHelper.getUsuario().getIdEmpresaMultiDevice());
                    ProspectHelper.getProspect().setUsuario_id(UsuarioHelper.getUsuario().getId_usuario());
                    ProspectHelper.getProspect().setUsuario_nome(UsuarioHelper.getUsuario().getNome_usuario());
                    db.atualizarTBL_PROSPECT(ProspectHelper.getProspect());
                    getActivity().finish();
                }
            });
            alert.show();
        }
    }

    @OnClick(R.id.imagemProspect1)
    public void chamarGaleria(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), REQUEST_CODE_IMAGEM_1);
    }

    @OnClick(R.id.imagemProspect2)
    public void chamarGaleria2(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), REQUEST_CODE_IMAGEM_2);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {

                Intent novaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                getActivity().sendBroadcast(novaIntent);

                Intent intent = new Intent(getActivity(), FotoActivity.class);

                try {
                    imagemProspect1.setImageBitmap(FotoUtil.rotateBitmap(BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri)), uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        } else if (requestCode == 456) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                bitmap = (Bitmap) bundle.get("data");
                imagemProspect1.setImageBitmap(bitmap);
            }
        } else if (requestCode == REQUEST_CODE_IMAGEM_1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        bitmap = FotoUtil.rotateBitmap(BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(data.getData())), data.getData());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    boolean validaCompressao = bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                    byte[] fotoBinario = outputStream.toByteArray();

                    mImagem1 = bitmap;
                    ProspectHelper.getProspect().setFotoPrincipalBase64(Base64.encodeToString(fotoBinario, Base64.NO_WRAP));
                    ProspectHelper.setImagem1(bitmap);
                    imagemProspect1.setImageBitmap(bitmap);
                }
            }
        }else if(requestCode == REQUEST_CODE_IMAGEM_2){
            if (data != null) {
                try {
                  bitmap = FotoUtil.rotateBitmap(BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(data.getData())), data.getData());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                boolean validaCompressao = bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
                byte[] fotoBinario = outputStream.toByteArray();


                ProspectHelper.getProspect().setFotoSecundariaBase64(Base64.encodeToString(fotoBinario, Base64.NO_WRAP));
                ProspectHelper.setImagem2(bitmap);
                mImagem2 = bitmap;
                imagemProspect2.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
