package br.ufscar.dc.dcopinion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leobronza@hotmail.com on 13/09/2015.
 */
public class MyadapterJson extends BaseExpandableListAdapter
{
    private List<String> consulta_questao_completa_json;
    private HashMap<String, List<String>> hash_titulos;
    private Context ctx;

    private LayoutInflater layoutInflater;

    MyadapterJson(Context ctx, List<String> consulta_questao_completa_json){
        this.ctx = ctx;
        this.consulta_questao_completa_json = consulta_questao_completa_json;

        this.layoutInflater = (LayoutInflater)this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return consulta_questao_completa_json.size();
        //return titulo_questionario.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        try {
            JSONObject jsonObject = new JSONObject(consulta_questao_completa_json.get(groupPosition));
            return jsonObject.getString("titulo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
        //return consulta_questao_completa_json.get(groupPosition;
        //return titulo_questionario.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {


        //return consulta_questao_completa_json.get(groupPosition)
        //return hash_titulos.get(titulo_questionario.get(groupPosition)).get(childPosition * 2 + 1);
        return null;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.titulos_listview,null);
        }
        String titulo = (String)this.getGroup(groupPosition);
        TextView textView = (TextView)convertView.findViewById(R.id.titulos_listview);
        textView.setText(titulo);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // Verifica se ja criado view para reutilizar
        //if(convertView == null){
            if(getChildType(groupPosition) == 0) {
                convertView = layoutInflater.inflate(R.layout.conteudo_respostas, null);
            }else if(getChildType(groupPosition) == 1) {
            convertView = layoutInflater.inflate(R.layout.conteudo_star, null);
            }if(getChildType(groupPosition) == 2){
            convertView = layoutInflater.inflate(R.layout.conteudo_respostas_img, null);
            }

        //}

        // Verifica o tipo de questionario
        if(getChildType(groupPosition) == 0) {
            int maior = -1;
            TextView conteudo_quantidade = (TextView) convertView.findViewById(R.id.conteudo_quantidade);
            TextView conteudo_titulo = (TextView) convertView.findViewById(R.id.conteudo_titulo);
            try {
                JSONObject jsonObject = new JSONObject(consulta_questao_completa_json.get(groupPosition));
                for(int i = 0; i < jsonObject.length()-4 ; i+=2)
                if( !jsonObject.getString(String.valueOf(i)).equals("null") && Integer.parseInt(jsonObject.getString(String.valueOf(i))) > maior ) {
                     maior = Integer.parseInt(jsonObject.getString(String.valueOf(i)));
                     conteudo_titulo.setText(jsonObject.getString(String.valueOf(i+1)));
                     conteudo_quantidade.setText(String.valueOf(maior));
                    //Toast.makeText(ctx, jsonObject.getString(String.valueOf(i)), Toast.LENGTH_LONG).show();
                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else if (getChildType(groupPosition) == 1){
            // Calcula quantidade de votos
            // Set media e quantidade de votos
            RatingBar conteudo_star = (RatingBar) convertView.findViewById(R.id.conteudo_star);
            TextView media_star = (TextView) convertView.findViewById(R.id.media_star);
            TextView quantidade_votos = (TextView) convertView.findViewById(R.id.quantidade_votos);

            try {
                JSONObject jsonObject = new JSONObject(consulta_questao_completa_json.get(groupPosition));
            float estrelas = (Integer.parseInt(jsonObject.getString("E1")) +
                    Integer.parseInt(jsonObject.getString("E2"))*2 +
                    Integer.parseInt(jsonObject.getString("E3"))*3 +
                    Integer.parseInt(jsonObject.getString("E4"))*4 +
                    Integer.parseInt(jsonObject.getString("E5"))*5);
            float respostas = (Integer.parseInt(jsonObject.getString("E1")) +
                    Integer.parseInt(jsonObject.getString("E2")) +
                    Integer.parseInt(jsonObject.getString("E3")) +
                    Integer.parseInt(jsonObject.getString("E4")) +
                    Integer.parseInt(jsonObject.getString("E5")));

                conteudo_star.setRating(estrelas / respostas);
                media_star.setText(String.valueOf(String.format("%.2f", estrelas / respostas)));
                quantidade_votos.setText(String.valueOf((int)respostas));

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else if(getChildType(groupPosition) == 2){
            //Toast.makeText(ctx, "tipo 2", Toast.LENGTH_LONG).show();

            int maior = -1;
            TextView conteudo_quantidade = (TextView) convertView.findViewById(R.id.conteudo_quantidade);
            //TextView conteudo_titulo = (TextView) convertView.findViewById(R.id.conteudo_titulo);
            //setar imagem
            ImageView imageView = (ImageView) convertView.findViewById(R.id.conteudo_imgviewer);
            try {
                JSONObject jsonObject = new JSONObject(consulta_questao_completa_json.get(groupPosition));
                for(int i = 0; i < jsonObject.length()-5 ; i+=2)
                    if( !jsonObject.getString(String.valueOf(i)).equals("null") && Integer.parseInt(jsonObject.getString(String.valueOf(i))) > maior ) {
                        maior = Integer.parseInt(jsonObject.getString(String.valueOf(i)));
                        //conteudo_titulo.setText(jsonObject.getString(String.valueOf(i+1)));
                        //adicionar imagem
                        byte[] decodedString = Base64.decode(jsonObject.getString(String.valueOf(i+1)), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView.setImageBitmap(decodedByte);

                        conteudo_quantidade.setText(String.valueOf(maior));

                        //Toast.makeText(ctx, jsonObject.getString(String.valueOf(i)), Toast.LENGTH_LONG).show();
                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public int getChildTypeCount () {

        // Set quantidade de tipos de respostas
        return 3;
    }

    //melhorar codigo
    public int getChildType (int groupPosition) {

        // Set tipos de respostas
        //String titulo = this.getChild(groupPosition, childPosition);
        try {
            JSONObject jsonObject = new JSONObject(consulta_questao_completa_json.get(groupPosition));
            jsonObject.getString("E1");
            return 1;
        } catch (JSONException e) {
            try {
                JSONObject jsonObject = new JSONObject(consulta_questao_completa_json.get(groupPosition));
                jsonObject.getString("tipo");
                return 2;
            } catch (JSONException e1) {
                return 0;
            }

        }

        //if(!titulo.contains("<E>"))
           // return 0;
        //else return 1;
    }

}
