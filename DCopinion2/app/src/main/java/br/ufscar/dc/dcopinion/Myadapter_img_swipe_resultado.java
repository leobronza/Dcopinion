package br.ufscar.dc.dcopinion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by leo on 21/06/16.
 */
public class Myadapter_img_swipe_resultado extends PagerAdapter {
    private ArrayList<String> img_array = new ArrayList<>();
    private ArrayList<String> votos_array = new ArrayList<>();
    private Context ctx;
    private LayoutInflater layoutInflater;

    public Myadapter_img_swipe_resultado(Context ctx, String result){
        this.ctx = ctx;
        try {
            JSONObject obj = new JSONObject(result);
            for( int i = 1 ; i< 10 ; i+=2){
                if (!obj.getString(String.valueOf(i-1)).equals("null"))
                    img_array.add(obj.getString(String.valueOf(i)));
            }
            for( int i = 0 ; i< 9 ; i+=2){
                if (!obj.getString(String.valueOf(i)).equals("null"))
                    votos_array.add(obj.getString(String.valueOf(i)));
            }
            //Toast.makeText(ctx, String.valueOf(arr.size()) + String.valueOf(obj.getString("8")) , Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ctx, "catch" , Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public int getCount() {
        return img_array.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.fragment_img_swipe_resultado,container,false);

        ImageView imageView = (ImageView)item_view.findViewById(R.id.view_img_swipe);
        TextView textview = (TextView)item_view.findViewById(R.id.quant_votos);
        textview.setText(votos_array.get(position));

        byte[] decodedString = Base64.decode(img_array.get(position), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
        //Toast.makeText(ctx, "aqui" , Toast.LENGTH_LONG).show();
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
