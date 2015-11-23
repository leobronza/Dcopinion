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
import java.util.ArrayList;
/**
 * Created by leobronza@hotmail.com on 18/09/15.
 */
public class Backgroundtask  extends AsyncTask<String,Void,ArrayList<String>>{
    Context ctx;
    Backgroundtask(Context ctx){
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
    @Override
    protected ArrayList<String> doInBackground(String... params) {
        String method = params[0];

        // Verifica qual metodo de thread foi chamado
        if(method.equals("cadastrar")) {
            texto_url = "http://192.168.0.104/webapp/cadastrar.php";
            email = params[1];
            senha = params[2];
            categoria = params[3];
        }
        else if(method.equals("login")){
            texto_url = "http://192.168.0.104/webapp/login.php";
            email = params[1];
            senha = params[2];
        }
        else if(method.equals("noticias")){
            texto_url = "http://192.168.0.104/webapp/noticias.php";
        }
        else if(method.equals("noticia_completa")){
            texto_url = "http://192.168.0.104/webapp/noticia_completa.php";
            id = params[1];
        }
        else if(method.equals("questoes_responder")){
            texto_url = "http://192.168.0.104/webapp/questoes_responder.php";
            id = params[1];
        }
        else if(method.equals("questao_completa")){
            texto_url = "http://192.168.0.104/webapp/questao_completa.php";
            id = params[1];
        }
        else if(method.equals("envia_resposta")){
            texto_url = "http://192.168.0.104/webapp/envia_resposta.php";
            id_questao = params[1];
            id_cliente = params[2];
            alternativa = params[3];
        }
        else if(method.equals("id_questoes_respondidas")){
            texto_url = "http://192.168.0.104/webapp/id_questoes_respondidas.php";
            id = params[1];
        }

        // Faz conexao ao servidor e realiza operação
        try {
            URL url = new URL(texto_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

            if(method.equals("cadastrar"))
                data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("senha", "UTF-8") + "=" + URLEncoder.encode(senha, "UTF-8") + "&" +
                        URLEncoder.encode("categoria", "UTF-8") + "=" + URLEncoder.encode(categoria, "UTF-8");
            else if(method.equals("login"))
                data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("senha", "UTF-8") + "=" + URLEncoder.encode(senha, "UTF-8");
            else if(method.equals("noticias"))
                data = "";
            else if(method.equals("noticia_completa"))
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            else if(method.equals("questoes_responder"))
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            else if(method.equals("questao_completa"))
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            else if(method.equals("envia_resposta"))
                data = URLEncoder.encode("id_questao", "UTF-8") + "=" + URLEncoder.encode(id_questao, "UTF-8") + "&" +
                        URLEncoder.encode("id_cliente", "UTF-8") + "=" + URLEncoder.encode(id_cliente, "UTF-8")+ "&" +
                        URLEncoder.encode("alternativa", "UTF-8") + "=" + URLEncoder.encode(alternativa, "UTF-8");
            else if(method.equals("id_questoes_respondidas"))
                data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            InputStream IS = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
            ArrayList<String> response = new ArrayList<>();

            if( method.equals("questao_completa"))
                response.add("x"); // arrumar

            String line;
            while ((line = bufferedReader.readLine()) != null)
                response.add(line);
            bufferedReader.close();
            IS.close();
            httpURLConnection.disconnect();

            // Retorna valor respondido
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Caso conexao falhe
        ArrayList<String> response = new ArrayList<String>();
        response.add("Problema no servidor");
        return response;
    }
}
