package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Leonardo on 16/09/2015.
 */
public class Noticias extends Activity{

    private int position_noticia;
    private TextView titulo_noticia;
    private TextView corpo_noticia;
    private TextView link_noticia;
    private ImageView imagem_noticia;
    String id_noticia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticias);
        id_noticia = getIntent().getStringExtra("id_noticia");
    }

    @Override
    protected void onStart() {
        String method = "noticia_completa";
        Backgroundtask backgroundtask = new Backgroundtask(this);
        ArrayList<String> result = new ArrayList<String>();
        try {
            result = backgroundtask.execute(method, id_noticia).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getBaseContext(), result.get(0), Toast.LENGTH_LONG).show();
        titulo_noticia = (TextView)findViewById(R.id.titulo_noticia);
        titulo_noticia.setText(result.get(1));
        corpo_noticia = (TextView)findViewById(R.id.corpo_noticia);
        corpo_noticia.setText(result.get(2));
        link_noticia = (TextView)findViewById(R.id.link_noticia);
        link_noticia.setText(result.get(3));
        imagem_noticia = (ImageView)findViewById(R.id.imagem_noticia);
        method = "noticia_completa_imagem";
        Bitmap imagem = null;
        BackGroundtaskImage backgroundtask2 = new BackGroundtaskImage(this);
        try {
            imagem = backgroundtask2.execute(method, id_noticia).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        imagem_noticia.setImageBitmap(imagem);
        super.onStart();
    }

}
