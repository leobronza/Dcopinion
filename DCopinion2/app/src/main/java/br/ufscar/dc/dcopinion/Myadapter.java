package br.ufscar.dc.dcopinion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;
/**
 * Created by leobronza@hotmail.com on 13/09/2015.
 */
public class Myadapter extends BaseExpandableListAdapter
{
    private List<String> titulo_questionario;
    private HashMap<String, List<String>> hash_titulos;
    private Context ctx;
    private LayoutInflater layoutInflater;

    Myadapter(Context ctx, List<String> titulo_questionario, HashMap<String, List<String>> hash_titulos){
        this.ctx = ctx;
        this.titulo_questionario = titulo_questionario;
        this.hash_titulos = hash_titulos;
        this.layoutInflater = (LayoutInflater)this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return titulo_questionario.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return titulo_questionario.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return hash_titulos.get(titulo_questionario.get(groupPosition)).get(childPosition * 2 + 1);
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
        if(convertView == null){
            if(getChildType(groupPosition,childPosition) == 0) {
                convertView = layoutInflater.inflate(R.layout.conteudo_respostas, null);
            }else{
                convertView = layoutInflater.inflate(R.layout.conteudo_star,null);
            }
        }

        // Verifica o tipo de questionario
        if(getChildType(groupPosition,childPosition) == 0) {

            // Seleciona a opção com maior voto
            int maior = -1;
            int ii = 0;
            for(int i = 0 ; i < hash_titulos.get(titulo_questionario.get(groupPosition)).size()/2 ; i++) {
                if (Integer.parseInt(hash_titulos.get(titulo_questionario.get(groupPosition)).get(i * 2).toString()) > maior) {
                    maior = Integer.parseInt(hash_titulos.get(titulo_questionario.get(groupPosition)).get(i * 2).toString());
                    ii = i;
                }
            }

            // Arrumar quando 2 opcoes com msm quantidade de votos
            // Set conteudo_titulo e conteudo_quantidade da opcao com maior voto
            TextView conteudo_titulo = (TextView) convertView.findViewById(R.id.conteudo_titulo);
            conteudo_titulo.setText(hash_titulos.get(titulo_questionario.get(groupPosition)).get(ii * 2 + 1));
            TextView conteudo_quantidade = (TextView) convertView.findViewById(R.id.conteudo_quantidade);
            conteudo_quantidade.setText(String.valueOf(maior));
        }else{
            // Calcula quantidade de votos
            float estrelas = (Integer.parseInt(hash_titulos.get(titulo_questionario.get(groupPosition)).get(0).toString()) +
                    Integer.parseInt(hash_titulos.get(titulo_questionario.get(groupPosition)).get(2).toString())*2 +
                    Integer.parseInt(hash_titulos.get(titulo_questionario.get(groupPosition)).get(4).toString())*3 +
                    Integer.parseInt(hash_titulos.get(titulo_questionario.get(groupPosition)).get(6).toString())*4 +
                    Integer.parseInt(hash_titulos.get(titulo_questionario.get(groupPosition)).get(8).toString())*5);
            float respostas = (Integer.parseInt(hash_titulos.get(titulo_questionario.get(groupPosition)).get(0).toString()) +
                    Integer.parseInt(hash_titulos.get(titulo_questionario.get(groupPosition)).get(2).toString()) +
                    Integer.parseInt(hash_titulos.get(titulo_questionario.get(groupPosition)).get(4).toString()) +
                    Integer.parseInt(hash_titulos.get(titulo_questionario.get(groupPosition)).get(6).toString()) +
                    Integer.parseInt(hash_titulos.get(titulo_questionario.get(groupPosition)).get(8).toString()));

            // Set media e quantidade de votos
            RatingBar conteudo_star = (RatingBar) convertView.findViewById(R.id.conteudo_star);
            conteudo_star.setRating(estrelas / respostas);
            TextView media_star = (TextView) convertView.findViewById(R.id.media_star);
            media_star.setText(String.valueOf(String.format("%.2f", estrelas / respostas)));
            TextView quantidade_votos = (TextView) convertView.findViewById(R.id.quantidade_votos);
            quantidade_votos.setText(String.valueOf((int)respostas));
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
        return 2;
    }

    @Override
    public int getChildType (int groupPosition, int childPosition) {

        // Set tipos de respostas
        String titulo = this.getChild(groupPosition, childPosition);
        if(!titulo.contains("<E>"))
        return 0;
        else return 1;
    }

}
