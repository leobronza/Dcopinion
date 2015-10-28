package br.ufscar.dc.dcopinion;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Leonardo on 13/09/2015.
 */
public class Myadapter extends BaseExpandableListAdapter
{
    private List<String> titulo_questionario;
    private HashMap<String, List<String>> hash_titulos;
    private Context ctx;
    Myadapter(Context ctx, List<String> titulo_questionario, HashMap<String, List<String>> hash_titulos){
        this.ctx = ctx;
        this.titulo_questionario = titulo_questionario;
        this.hash_titulos = hash_titulos;
    }
    @Override
    public int getGroupCount() {
        return titulo_questionario.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return hash_titulos.get(titulo_questionario.get(groupPosition)).size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return titulo_questionario.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return hash_titulos.get(titulo_questionario.get(groupPosition)).get(childPosition);
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
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.conteudo_listview,null);
        }
        String titulo = (String)this.getChild(groupPosition, childPosition);
        TextView textView = (TextView) convertView.findViewById(R.id.conteudo_listview);
        textView.setText(titulo);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

}
