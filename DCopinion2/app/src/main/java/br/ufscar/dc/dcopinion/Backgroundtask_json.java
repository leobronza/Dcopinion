package br.ufscar.dc.dcopinion;

import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
/**
 * Created by leobronza@hotmail.com on 18/09/15.
 */
public class Backgroundtask_json  extends AsyncTask<String,Void,String>{
    Context ctx;
    Backgroundtask_json(Context ctx){
        this.ctx = ctx;
    }
    private String texto_url;
    private String email;
    private String senha;
    private String categoria;
    private String id;
    private String id_questao;
    private String id_cliente;
    private String alternativa;
    private String data;
    private String jsonstring;
    @Override
    protected String doInBackground(String... params) {
        String method = params[0];

        // Verifica qual metodo de thread foi chamado
        if(method.equals("noticia_completa_json")){
            texto_url = "http://200.18.97.50/webapp/noticia_completa_json.php";
            id = params[1];
        }
        else if(method.equals("questao_completa_json")){
            texto_url = "http://200.18.97.50/webapp/questao_completa_json.php";
            id = params[1];
        }
        else if(method.equals("sugerir_questionario_json")){
            texto_url = "http://200.18.97.50/webapp/enviar_sugestao_json.php";
            jsonstring = params[1];
        }
        else if(method.equals("noticias_json")){
            texto_url = "http://200.18.97.50/webapp/noticias_json.php";
            id = params[1];
        }
        else if(method.equals("id_questoes_respondidas_json")){
            texto_url = "http://200.18.97.50/webapp/id_questoes_respondidas_json.php";
            id = params[1];
        }


        try {
            URL url = new URL(texto_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));


            if(method.equals("noticia_completa_json"))
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            else if(method.equals("questao_completa_json"))
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            else if(method.equals("noticias_json"))
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            else if(method.equals("sugerir_questionario_json"))
                data = URLEncoder.encode("jsonstring", "UTF-8") + "=" + URLEncoder.encode(jsonstring, "UTF-8");
            else if(method.equals("id_questoes_respondidas_json"))
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            InputStream IS = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));

            // Get no servidor
            String line = bufferedReader.readLine();

            bufferedReader.close();
            IS.close();
            httpURLConnection.disconnect();

            // Retorna valor respondido
            return line;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Caso conexao falhe
        return "Problema no servidor";
    }
}
