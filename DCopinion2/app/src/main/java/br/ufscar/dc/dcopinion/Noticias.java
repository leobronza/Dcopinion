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
    private ArrayList<String> id_noticia = new ArrayList<String>();
    private int position_noticia;
    private TextView titulo_noticia;
    private TextView corpo_noticia;
    private TextView link_noticia;
    private ImageView imageView; //deletar
    private ImageView imagem_noticia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticias);
        position_noticia = getIntent().getIntExtra("position_noticia", -1);
        id_noticia = getIntent().getStringArrayListExtra("id_noticia");
        imageView = (ImageView)findViewById(R.id.imagem_noticia); //deletar
    }

    @Override
    protected void onStart() {
        String method = "noticia_completa";
        Backgroundtask backgroundtask = new Backgroundtask(this);
        ArrayList<String> result = new ArrayList<String>();
        try {
            result = backgroundtask.execute(method, id_noticia.get(position_noticia)).get();
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
        //imagem_noticia.setImageBitmap(StringToBitMap(result.get(4)));

        super.onStart();
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }


 //deletar abaixo
    private void getImage() {
        //String id = editTextId.getText().toString().trim();
        class GetImage extends AsyncTask<String,Void,Bitmap> {
            //ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(ViewImage.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                //loading.dismiss();

                //int w = 20, h = 100;
                //Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
                //Bitmap bmp = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
                //imageView.setImageBitmap(bmp);

                imageView.setImageBitmap(b);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                //String id = params[0];
                //String add = "http://simplifiedcoding.16mb.com/ImageUpload/getImage.php?id="+id;
                String add = "http://192.168.0.31/webapp/noticia_completa2.php";
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }

        GetImage gi = new GetImage();
        gi.execute();
    }

    public void onclick_imagem(View v) {
        getImage();
    }

}
