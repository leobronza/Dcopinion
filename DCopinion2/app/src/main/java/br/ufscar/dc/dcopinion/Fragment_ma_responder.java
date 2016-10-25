package br.ufscar.dc.dcopinion;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by leobronza@hotmail.com on 03/11/15.
 */
public class Fragment_ma_responder extends Fragment {
    //private  RadioGroup rg;
    ArrayList<CheckBox> checkboxs = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ma_responder,container,false);

        // Recebe parametros
        Bundle bundle = this.getArguments();
        if (bundle != null) {
           String result = bundle.getString("key");


            try {
                JSONObject jsonObject = new JSONObject(result);

                CheckBox ma1 = (CheckBox)view.findViewById(R.id.ma1);
                CheckBox ma2 = (CheckBox)view.findViewById(R.id.ma2);
                CheckBox ma3 = (CheckBox)view.findViewById(R.id.ma3);
                CheckBox ma4 = (CheckBox)view.findViewById(R.id.ma4);
                CheckBox ma5 = (CheckBox)view.findViewById(R.id.ma5);


                checkboxs.add(ma1);
                checkboxs.add(ma2);
                checkboxs.add(ma3);
                checkboxs.add(ma4);
                checkboxs.add(ma5);

                int  n = 0;
                for(int i = 1 ; i < 10; i+=2 ) {
                    if (!jsonObject.getString(String.valueOf(i-1)).equals("null")) {
                        checkboxs.get(n).setText(jsonObject.getString(String.valueOf(i)));
                    }
                    else{
                        checkboxs.get(n).setVisibility(View.GONE);
                    }
                    n++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return view;
    }
    public ArrayList<String> getmopcoes(){

        ArrayList<String> retorno = new ArrayList<>();

        for(int i = 0 ; i < 5; i++ )
            if(checkboxs.get(i).isChecked())
                retorno.add(String.valueOf(i+1));
        return retorno;
    }
}
