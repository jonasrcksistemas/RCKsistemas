package com.example.rcksuporte05.rcksistemas.interfaces;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rcksuporte05.rcksistemas.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RCK 03 on 26/10/2017.
 */

public class infoDialog extends Activity {

    @BindView(R.id.lyGps)
    LinearLayout lyGps;
    @BindView(R.id.lyMail)
    LinearLayout lyMail;
    @BindView(R.id.lyChamada)
    LinearLayout lyChamada;
    @BindView(R.id.lyWhats)
    LinearLayout lyWhats;
    @BindView(R.id.versaoAPP)
    TextView versaoAPP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_dialog);
        ButterKnife.bind(this);

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            versaoAPP.setText("Versão do Aplicativo: " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.lyGps)
    public void gps() {

        Uri gmmIntentUri = Uri.parse("google.navigation:q= Rua Senador Ponce, 257 Campo Grande - MS");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @OnClick(R.id.lyMail)
    public void email() {

        final Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: contato@rcksistemas.com.br"));
        startActivity(intent);
    }

    @OnClick(R.id.lyChamada)
    public void chamada() {

        final Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:06733833878"));
        startActivity(intent);
    }

    @OnClick(R.id.lyWhats)
    public void whats() {

        final Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:067993235558"));
        startActivity(intent);
    }
}

