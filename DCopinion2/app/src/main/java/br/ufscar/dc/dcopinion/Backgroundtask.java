package br.ufscar.dc.dcopinion;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

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
 * Created by leo on 18/09/15.
 */
public class Backgroundtask  extends AsyncTask<String,Void,ArrayList<String>>{
    Context ctx;
    AlertDialog alertDialog;
    Backgroundtask(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Atenção !");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {

        if (result.get(0).equals("0")){
            Toast.makeText(ctx,result.get(1), Toast.LENGTH_LONG).show();
        }
        else if (result.get(0).equals("1")) {
                alertDialog.setMessage(result.get(1));
                alertDialog.show();
            }
        }


    @Override
    protected ArrayList<String> doInBackground(String... params) {
        String reg_url = "http://192.168.0.40/webapp/register.php";
        String login_url = "http://192.168.0.40/webapp/login.php";
        String noticias_url = "http://192.168.0.40/webapp/noticias.php";
        String noticia_completa_url = "http://192.168.0.40/webapp/noticia_completa.php";
        String questoes_responder_url = "http://192.168.0.40/webapp/questoes_responder.php";
        String questao_completa_url = "http://192.168.0.40/webapp/questao_completa.php";
        String envia_resposta_url =  "http://192.168.0.40/webapp/envia_resposta.php";
        String method = params[0];
        if (method.equals("register")) {
            String email = params[1];
            String senha = params[2];
            String categoria = params[3];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("senha", "UTF-8") + "=" + URLEncoder.encode(senha, "UTF-8") + "&" +
                        URLEncoder.encode("categoria", "UTF-8") + "=" + URLEncoder.encode(categoria, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();//ver
                IS.close();
                httpURLConnection.disconnect();
                ArrayList<String> response = new ArrayList<String>();
                response.add("0");
                response.add("Cadastro Concluido...");
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (method.equals("login")){
            String email = params[1];
            String senha = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("senha", "UTF-8") + "=" + URLEncoder.encode(senha, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                ArrayList<String> response = new ArrayList<String>();
                String line = "";
                response.add("1");
                while ((line = bufferedReader.readLine()) != null){
                    response.add(line);
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (method.equals("noticias")){
            try {
                URL url = new URL(noticias_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoInput(true);
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                String line = "";
                ArrayList<String> response = new ArrayList<String>();
                while ((line = bufferedReader.readLine()) != null){
                    response.add(line);
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(method.equals("noticia_completa")){
            String id = params[1];
            try {
                URL url = new URL(noticia_completa_url);
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
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                ArrayList<String> response = new ArrayList<String>();
                String line = "";
                response.add("2");
                while ((line = bufferedReader.readLine()) != null){
                    response.add(line);
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (method.equals("questoes_responder")){
            String id = params[1];
            try{
            URL url = new URL(questoes_responder_url);
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
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                ArrayList<String> response = new ArrayList<String>();
                String line = "";
                response.add("3");
                while ((line = bufferedReader.readLine()) != null){
                    response.add(line);
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(method.equals("questao_completa")){
            String id = params[1];
            String tipo_questao = params[2];
            try {
                URL url = new URL(questao_completa_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                        URLEncoder.encode("tipo_questao", "UTF-8") + "=" + URLEncoder.encode(tipo_questao, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                ArrayList<String> response = new ArrayList<String>();
                String line = "";
                response.add("4");
                while ((line = bufferedReader.readLine()) != null){
                    response.add(line);
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(method.equals("envia_resposta")){
            String id_questao = params[1];
            String id_cliente = params[2];
            String alternativa = params[3];
            try {
                URL url = new URL(envia_resposta_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("id_questao", "UTF-8") + "=" + URLEncoder.encode(id_questao, "UTF-8") + "&" +
                        URLEncoder.encode("id_cliente", "UTF-8") + "=" + URLEncoder.encode(id_cliente, "UTF-8")+ "&" +
                        URLEncoder.encode("alternativa", "UTF-8") + "=" + URLEncoder.encode(alternativa, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                ArrayList<String> response = new ArrayList<String>();
                String line = "";
                response.add("5");

                while ((line = bufferedReader.readLine()) != null){
                    response.add(line);
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> response = new ArrayList<String>();
        response.add("0");
        response.add("saiu");
        return response;
    }
}
