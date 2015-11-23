package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticias);

        //Função responsavel pela Noticia
        Noticia();
    }
    public void Noticia(){
        // Recebe parametros
        id_noticia = getIntent().getStringExtra("id_noticia");

        // Consulta textos da noticia
        Backgroundtask thread_textos_noticia_completa = new Backgroundtask(this);
        ArrayList<String> consulta_texto_noticia = new ArrayList<>();
        try {
            consulta_texto_noticia = thread_textos_noticia_completa.execute("noticia_completa", id_noticia).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Set textos na noticia
        titulo_noticia = (TextView)findViewById(R.id.titulo_noticia);
        titulo_noticia.setText(consulta_texto_noticia.get(0));
        corpo_noticia = (TextView)findViewById(R.id.corpo_noticia);
        corpo_noticia.setText(consulta_texto_noticia.get(1));
        link_noticia = (TextView)findViewById(R.id.link_noticia);
        link_noticia.setText(consulta_texto_noticia.get(2));


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
}
