package insa.cloud.global;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

public class MultipartRequest extends Request<String> {

// private MultipartEntity entity = new MultipartEntity();

    private static final String FILE_PART_NAME = "image";
    private final Response.Listener<String> mListener;
    private final File mFilePart;
    private final Map<String, String> mStringPart;
    MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    HttpEntity httpentity;

    public MultipartRequest(String url, Response.ErrorListener errorListener,
                            Response.Listener<String> listener, File file,
                            Map<String, String> mStringPart) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePart = file;
        this.mStringPart = mStringPart;
        entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        buildMultipartEntity();
    }

    public void addStringBody(String param, String value) {
        mStringPart.put(param, value);
    }

    private void buildMultipartEntity() {
        entity.addPart(FILE_PART_NAME, new FileBody(mFilePart));
        for (Map.Entry<String, String> entry : mStringPart.entrySet()) {
            entity.addTextBody(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
        }
        headers.put("Authorization", String.format("Basic %s", Base64.encodeToString(
                String.format("%s:%s", "admin", "insacloud").getBytes(), Base64.DEFAULT)));

        return headers;
    }

    @Override
    public String getBodyContentType() {
        return httpentity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            httpentity = entity.build();
            httpentity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return Response.success("Uploaded", getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        //return super.parseNetworkError(volleyError);

        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
            volleyError = error;
        }

        return volleyError;
    }
}