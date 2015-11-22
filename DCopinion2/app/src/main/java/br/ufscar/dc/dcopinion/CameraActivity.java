package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Nicolas on 20/11/2015.
 */
public class CameraActivity extends Activity {

    Button button;
    ImageView imageView;
    static final int CAM_REQUEST = 1;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        button = (Button) findViewById(R.id.camB);
        imageView = (ImageView) findViewById(R.id.imV);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent, CAM_REQUEST);
            }
        });
    }

    private File getFile()
    {
        File folder = new File("sdcard/camera_app");

        if(!folder.exists()){
            folder.mkdir();
        }

        Date data = new Date(System.currentTimeMillis());
        fileName = data.toString() + ".bmp";
        File image_file = new File(folder, fileName);

        return image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        setPic();
        //galleryAddPic();

        String path = "sdcard/camera_app/" + fileName;
        Bitmap imageBitmap = BitmapFactory.decodeFile(path);
        Bitmap scaled = Bitmap.createScaledBitmap(imageBitmap, 100, 100 ,true);
        imageView.setImageBitmap(scaled);
    }


    private void setPic() {

        /* Get the size of the ImageView */
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        /* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        /* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        /* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        /* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(fileName, bmOptions);
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(
                "android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(fileName);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);


        this.sendBroadcast(mediaScanIntent);
    }
}
