package com.example.dannyliu.watchewupto;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dannyliu on 11/14/15.
 */
public class ServerRequest {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://159.203.20.113/EngHacks/";

    public ServerRequest(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("processing");
        progressDialog.setMessage("Please wait and listen");

    }


    //omg look, in order to pass a function as a parameter, we must use an interface
    public void storeUserDataInBackground(User user, GetUserCallBack userCallback) {
        progressDialog.show();
        //Execute method must be found in the AsyncTask class declaration
        new StoreUserDataAsyncTask(user, userCallback).execute();
    }

    public void fetchUserDataInBackground(User user, GetUserCallBack callback) {
        progressDialog.show();
        new fetchUserDataAsyncTask(user, callback).execute();
    }

    public void sendQueryInBackground(String query, GetQueryCallback callback){
        progressDialog.show();
        new sendQueryAsyncTask(query, callback).execute();
    }

    //To do a background task in Android, we use an AsyncTask
    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

        User user;
        GetUserCallBack userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallBack userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        //When AsyncTask is finished
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            userCallback.done(null);
        }

        //Access the server!!
        @Override
        protected Void doInBackground(Void... params) {
            //Tell the server to post data to the database
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", user.name));
            dataToSend.add(new BasicNameValuePair("email", user.email));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        //Tell the server to get data from the database
        User user;
        GetUserCallBack userCallback;

        public fetchUserDataAsyncTask(User user, GetUserCallBack userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            super.onPostExecute(returnedUser);
            progressDialog.dismiss();
            userCallback.done(returnedUser);

        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("email", user.email));
            dataToSend.add(new BasicNameValuePair("password", user.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");

            User returnedUser = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                //executing this post method will actually return a response
                HttpResponse httpResponse = client.execute(post);
                //Entity is the data from the http response
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                if(jObject.length() == 0){
                    returnedUser = null;
                } else {
                    String name = jObject.getString("name");

                    returnedUser = new User(name, user.email, user.password);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedUser;
        }
    }

    public class sendQueryAsyncTask extends AsyncTask<Void, Void, Void> {

        String query;
        GetQueryCallback callback;

        public sendQueryAsyncTask(String query, GetQueryCallback callback) {
            this.query = query;
            this.callback = callback;
        }

        //When AsyncTask is finished
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            callback.done(null);
        }

        //Access the server!!
        @Override
        protected Void doInBackground(Void... params) {
            //Tell the server to post data to the database

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "select.php");

            try {
                post.setEntity(new StringEntity(query));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
