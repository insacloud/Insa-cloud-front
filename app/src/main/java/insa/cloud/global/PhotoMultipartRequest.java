package insa.cloud.global;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class PhotoMultipartRequest extends Request<String> {

    private static final String TAG = PhotoMultipartRequest.class.getSimpleName();
    private static final boolean IS_DEBUG = true;

    private static final String FILE_PART_NAME = "file";

    private Response.Listener<String> mListener;
    private Response.ErrorListener mErrorListener;
    private Map<String, String> mRequestParams;
    private File file;
    private String tokenId;

    private final String BOUNDARY = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
    private final String CRLF = "\r\n";
    private final int MAX_BUFFER_SIZE = 1024 * 1024;

    public PhotoMultipartRequest(int method, String url,  Response.ErrorListener errorListener,Response.Listener<String> listener,
                   File file, Map<String, String> requestParams, String tokenId) {
        super(method, url, errorListener);

        mListener = listener;
        mErrorListener = errorListener;
        this.file = file;
        this.tokenId = tokenId;
        mRequestParams = requestParams;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return Response.success(new String(response.data), getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
        }
        //headers.put(Config.HEADER_AUTHORIZATION, "Bearer " + tokenId);
        headers.put("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        return headers;
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    @Override
    public void deliverError(VolleyError error) {
        mErrorListener.onErrorResponse(error);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(dos, "UTF-8"));

            Set<String> keys = mRequestParams.keySet();
            for (String key : keys) {
                pw.append("--" + BOUNDARY + CRLF);
                pw.append("Content-Disposition: form-data; ");
                pw.append("name=\"" + key + "\"" + CRLF + CRLF + mRequestParams.get(key) + CRLF);
            }

            pw.append("--" + BOUNDARY + CRLF);
            pw.append("Content-Disposition: form-data; ");
            pw.append("name=\"" + FILE_PART_NAME + "\"; filename=\"" + file.getName() + "\"" + CRLF + CRLF);

            dos.writeBytes(pw.toString());
            pw.flush();
            FileInputStream inputStream = new FileInputStream(file);
            int bytesAvailable = inputStream.available();
            int bufferSize = Math.min(bytesAvailable, MAX_BUFFER_SIZE);
            byte[] buffer = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1)
                dos.write(buffer, 0, bytesRead);

            dos.writeBytes(CRLF + "--" + BOUNDARY + "--" + CRLF);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }
}