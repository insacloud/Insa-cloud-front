package insa.cloud.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import insa.cloud.R;
import insa.cloud.global.GetImageThumbnail;
import insa.cloud.global.PhotoMultipartRequest;
import insa.cloud.global.Url;
import insa.cloud.global.VolleyController;

public class SendPhotoActivity extends AppCompatActivity {

    private static String root = null;
    private static String imageFolderPath = null;
    private String imageName = null;
    private static Uri fileUri = null;
    private static final int CAMERA_IMAGE_REQUEST=1;
    private ImageView imageView;
    private Button buttonTakePhoto;
    private Button buttonUpload;
    private String pathImage = null;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_photo);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Send Photo");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.captured);
        buttonTakePhoto = (Button)findViewById(R.id.buttonTakePhoto);
        buttonUpload = (Button)findViewById(R.id.buttonUpload);

        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullImage(v);
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void captureImage() {
        // fetching the root directory
        root = Environment.getExternalStorageDirectory().toString()
                + "/INSACloud";

        // Creating folders for Image
        imageFolderPath = root + "/captured";
        File imagesFolder = new File(imageFolderPath);
        imagesFolder.mkdirs();

        // Generating file name
        imageName = "image.png";

        // Creating image here

        File image = new File(imageFolderPath, imageName);

        fileUri = Uri.fromFile(image);

        pathImage = imageFolderPath + File.separator + imageName;

        imageView.setTag(pathImage);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(takePictureIntent,
                CAMERA_IMAGE_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case CAMERA_IMAGE_REQUEST:

                    Bitmap bitmap = null;
                    try {
                        GetImageThumbnail getImageThumbnail = new GetImageThumbnail();
                        bitmap = getImageThumbnail.getThumbnail(fileUri, this);
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    // Setting image image icon on the imageview

                    ImageView imageView = (ImageView) this
                            .findViewById(R.id.captured);

                    imageView.setImageBitmap(bitmap);

                    break;

                default:
                    Toast.makeText(this, "Something went wrong...",
                            Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    public void showFullImage(View view) {
        String path = (String) view.getTag();

        if (path != null) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri imgUri = Uri.parse("file://" + path);
            intent.setDataAndType(imgUri, "image/*");
            startActivity(intent);

        }
    }

    public void uploadPhoto(){
        try {
            File image = new File((String)imageView.getTag());
            String token = "";

            PhotoMultipartRequest imageUploadReq = new PhotoMultipartRequest(0, Url.UploadImage, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            }, new Response.Listener<String>() {

                @Override
                public void onResponse(String s) {

                }
            }, image, null, token);

            VolleyController.getInstance().addToRequestQueue(imageUploadReq);
        }
        catch(Exception e){

        }

        // Adding request to request queue
    }
}
