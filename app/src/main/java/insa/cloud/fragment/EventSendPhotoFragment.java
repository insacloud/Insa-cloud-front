package insa.cloud.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import insa.cloud.R;
import insa.cloud.global.GetImageThumbnail;
import insa.cloud.global.MultipartRequest;
import insa.cloud.global.Url;
import insa.cloud.global.VolleyController;

public class EventSendPhotoFragment extends Fragment {
    private static final int CAMERA_IMAGE_REQUEST = 1;
    private static String root = null;
    private static String imageFolderPath = null;
    private static Uri fileUri = null;
    private ImageView imageView;
    private Button buttonUpload;
    private String pathImage = null;
    private boolean captured = false;
    private ProgressDialog pDialog;


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
        Button buttonTakePhoto = (Button) rootView.findViewById(R.id.buttonTakePhoto);
        buttonUpload = (Button) rootView.findViewById(R.id.buttonUpload);

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
        buttonUpload.setEnabled(false);
        captured = false;
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
        String imageName = "image.png";

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
                    captured = true;
                    buttonUpload.setEnabled(true);
                    break;

                default:
                    Toast.makeText(getActivity(), "Something went wrong...",
                            Toast.LENGTH_SHORT).show();
                    captured = false;
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

    public void uploadPhoto() {
        if (captured) {
            try {
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Uploading...");
                pDialog.show();
                File image = new File((String) imageView.getTag());

                Map<String, String> requestParams = new HashMap<String, String>();
                requestParams.put("event", getActivity().getIntent().getStringExtra("eventID"));

                MultipartRequest imageUploadReq = new MultipartRequest(Url.UploadImage, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        pDialog.dismiss();
                        VolleyLog.d("TAG", "Error had occured " + volleyError.getCause());
                        Log.d("TAG", "inside Error");
                        Toast.makeText(getActivity().getApplicationContext(), "Error had occured",
                                Toast.LENGTH_SHORT).show();

                    }
                }, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        pDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), "Success",
                                Toast.LENGTH_SHORT).show();
                    }
                }, image, requestParams);
                imageUploadReq.setRetryPolicy(new DefaultRetryPolicy(
                        60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleyController.getInstance().addToRequestQueue(imageUploadReq);
            } catch (Exception e) {

            }
        }
    }
}
