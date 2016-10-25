package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
/**
 * Created by leobronza@hotmail.com on 16/09/2015.
 */
public class Noticias extends Activity{
    private TextView titulo_noticia;
    private TextView corpo_noticia;
    private TextView link_noticia;
    private ImageView imagem_noticia;
    private String id_noticia;
    private TextView publicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.noticias);

        //Função responsavel pela Noticia
        Noticia();
    }
    public void Noticia(){
            // Recebe parametros
            id_noticia = getIntent().getStringExtra("id_noticia");

        // Consulta textos da noticia json
        Backgroundtask_json thread_textos_noticia_completa_json = new Backgroundtask_json(this);
        String consulta_texto_noticia_json = new String();
        try {
            consulta_texto_noticia_json = thread_textos_noticia_completa_json.execute("noticia_completa_json", id_noticia).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(consulta_texto_noticia_json);

            // Set textos na noticia
            titulo_noticia = (TextView)findViewById(R.id.titulo_noticia);
            titulo_noticia.setText(jsonObject.getString("titulo"));
            corpo_noticia = (TextView)findViewById(R.id.corpo_noticia);
            corpo_noticia.setText(jsonObject.getString("corpo"));
            link_noticia = (TextView)findViewById(R.id.link_noticia);
            link_noticia.setText(jsonObject.getString("link"));
            publicacao = (TextView)findViewById(R.id.publicacao);
            publicacao.setText(jsonObject.getString("publicacao"));
        }catch (JSONException e) {
            e.printStackTrace();
        }
            // Consulta imagem da noticia
            Bitmap imagem = null;
            BackGroundtaskImage thread_imagem_noticia_completa = new BackGroundtaskImage(this);
            try {
                imagem = thread_imagem_noticia_completa.execute("noticia_completa_imagem", id_noticia).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            //Set Imagem na noticia
            imagem_noticia = (ImageView)findViewById(R.id.imagem_noticia);
            imagem_noticia.setImageBitmap(imagem);

    }

    public void onclick_voltar(View view){
        finish();
    }
}
