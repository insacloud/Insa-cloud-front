package insa.cloud.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import insa.cloud.R;
import insa.cloud.global.GetImageThumbnail;
import insa.cloud.global.PhotoMultipartRequest;
import insa.cloud.global.Url;
import insa.cloud.global.VolleyController;

public class EventSendPhotoFragment extends Fragment {
    private static String root = null;
    private static String imageFolderPath = null;
    private String imageName = null;
    private static Uri fileUri = null;
    private static final int CAMERA_IMAGE_REQUEST=1;
    private ImageView imageView;
    private Button buttonTakePhoto;
    private Button buttonUpload;
    private String pathImage = null;

    public EventSendPhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event_send_photo, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.captured);
        buttonTakePhoto = (Button)rootView.findViewById(R.id.buttonTakePhoto);
        buttonUpload = (Button)rootView.findViewById(R.id.buttonUpload);

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

        return rootView;
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case CAMERA_IMAGE_REQUEST:

                    Bitmap bitmap = null;
                    try {
                        GetImageThumbnail getImageThumbnail = new GetImageThumbnail();
                        bitmap = getImageThumbnail.getThumbnail(fileUri, getActivity());
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    // Setting image image icon on the imageview


                    imageView.setImageBitmap(bitmap);

                    break;

                default:
                    Toast.makeText(getActivity(), "Something went wrong...",
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
