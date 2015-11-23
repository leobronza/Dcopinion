package br.ufscar.dc.dcopinion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
/**
 * Created by leobronza@hotmail.com on 25/10/15.
 */
public class BackGroundtaskImage extends AsyncTask<String,Void,Bitmap> {
    Context ctx;
    private String imagem_url;
    BackGroundtaskImage(Context ctx){
        this.ctx = ctx;
    }
    @Override
    protected Bitmap doInBackground(String... params) {

        // Realiza conexao ao Servidor e envia o id de noticias e questoes e recebe a imagem
        String method = params[0];
        String id = params[1];
        Bitmap imagem = null;
        if (method.equals("noticia_completa_imagem")) {
            imagem_url = "http://192.168.0.104/webapp/noticia_completa_imagem.php";
        }
            else if(method.equals("questao_completa_imagem")) {
            imagem_url = "http://192.168.0.104/webapp/questao_completa_imagem.php";
        }
            try {
                URL url = new URL(imagem_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                imagem = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                httpURLConnection.disconnect();
                return imagem;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }
}
