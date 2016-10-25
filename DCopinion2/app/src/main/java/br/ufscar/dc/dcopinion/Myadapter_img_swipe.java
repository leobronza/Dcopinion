package br.ufscar.dc.dcopinion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created by leo on 21/06/16.
 */
public class Myadapter_img_swipe extends PagerAdapter {
    private ArrayList<String> img_array = new ArrayList<>();
    private int[] radiobutton_array_img = new int[] {0,0,0,0,0};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public Myadapter_img_swipe(Context ctx, String result){
        this.ctx = ctx;
        try {
            JSONObject obj = new JSONObject(result);
            for( int i = 1 ; i< 10 ; i+=2){
                if (!obj.getString(String.valueOf(i-1)).equals("null"))
                    img_array.add(obj.getString(String.valueOf(i)));
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
        View item_view = layoutInflater.inflate(R.layout.fragment_img_swipe_responder,container,false);

        ImageView imageView = (ImageView)item_view.findViewById(R.id.view_img_swipe);
        RadioButton radioButton = (RadioButton)item_view.findViewById(R.id.img_radiobutton);

        if(radiobutton_array_img[position] == 1) {
            radioButton.setChecked(true);
        }
        else {
            radioButton.setChecked(false);
        }

       radioButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               for (int i = 0 ; i < getCount() ; i++){
                   if(i != position )
                       radiobutton_array_img[i] = 0;
                   else{
                       radiobutton_array_img[i] = 1;
                   }
               }

               notifyDataSetChanged();
               //Toast.makeText(ctx, String.valueOf(position) + String.valueOf(radiobutton_array_img[0]) , Toast.LENGTH_LONG).show();
           }
       });

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

    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
    public int getopcaoimg_myadapter() {
        for (int i = 0; i < getCount(); i++)
            if (radiobutton_array_img[i] == 1)
                return i+1;
        return -1;
    }
}
