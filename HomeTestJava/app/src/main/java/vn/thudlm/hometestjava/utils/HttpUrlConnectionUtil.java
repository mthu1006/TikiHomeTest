package vn.thudlm.hometestjava.utils;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUrlConnectionUtil extends AsyncTask<String, Void, String> {

    private static Context mContext;
    private AsyncResponse mDelegate;
    private final String GET_METHOD = "GET";
    private final String POST_METHOD = "POST";

    public HttpUrlConnectionUtil(Context context) {
        mContext = context;
    }

    public static HttpUrlConnectionUtil with(Context context){
        return new HttpUrlConnectionUtil(context);
    }

    public HttpUrlConnectionUtil onFinish(AsyncResponse delegate){
        mDelegate = delegate;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        URL url;
        String response = "";
        try {
            url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            if(params.length>1){
                conn.setRequestMethod(POST_METHOD);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(params[1]);
                writer.flush();
                writer.close();
                os.close();

            } else {
                conn.setRequestMethod(GET_METHOD);
                conn.setDoInput(true);
            }
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mDelegate.processFinish(result);
    }

    public interface AsyncResponse {
        void processFinish(String output);
    }
}
