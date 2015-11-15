package com.example.dannyliu.watchewupto;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
import org.json.JSONArray;
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

    public class sendQueryAsyncTask extends AsyncTask<Void, Void, ArrayList<FoodItem> > {

        String query;
        GetQueryCallback callback;

        public sendQueryAsyncTask(String query, GetQueryCallback callback) {
            this.query = query;
            this.callback = callback;
        }

        //When AsyncTask is finished
        @Override
        protected void onPostExecute(ArrayList<FoodItem> a) {
            super.onPostExecute(a);
            progressDialog.dismiss();
            callback.done(a);
        }

        //Access the server!!
        @Override
        protected ArrayList<FoodItem> doInBackground(Void... params) {
            ArrayList<FoodItem> results = new ArrayList<FoodItem>();
            //Tell the server to post data to the database

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("search_string", query));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "select.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                //After we post the method with the query, select.php will return an array

                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONArray obj = new JSONArray(result);

                //Parse each ID
                for(int i = 0; i < obj.length(); i++) {
                    //NOTE: Beyond ghetto
                    //Query server for item given by ID
                    ArrayList<NameValuePair> dataToSend1 = new ArrayList<>();
                    dataToSend1.add(new BasicNameValuePair("id", obj.get(i).toString()));
                    Log.i("debug", obj.get(i).toString());
                    HttpParams httpRequestParams1 = new BasicHttpParams();
                    HttpConnectionParams.setConnectionTimeout(httpRequestParams1, CONNECTION_TIMEOUT);
                    HttpConnectionParams.setSoTimeout(httpRequestParams1, CONNECTION_TIMEOUT);

                    HttpClient client1 = new DefaultHttpClient(httpRequestParams1);
                    HttpPost post1 = new HttpPost(SERVER_ADDRESS + "getItem.php");

                    try {
                        post1.setEntity(new UrlEncodedFormEntity(dataToSend1));
                        HttpResponse httpResponse1 = client1.execute(post1);
                        HttpEntity entity1 = httpResponse1.getEntity();
                        String result1 = EntityUtils.toString(entity1);
                        JSONObject jObject1 = new JSONObject(result1);
                        Log.i("debug", result1 + "DERP DERP DERP");
                        if(jObject1.length() == 0) {
                            return null;
                        }
                        else {
                            //Parse into ArrayList results
                            results.add(new FoodItem(
                                    jObject1.getString("0"),
                                    jObject1.getInt("1"),
                                    jObject1.getString("3"),
                                    jObject1.getInt("4"),
                                    jObject1.getInt("5"),
                                    jObject1.getInt("6"),
                                    jObject1.getInt("7"),
                                    jObject1.getInt("8"),
                                    jObject1.getInt("9"),
                                    jObject1.getInt("10"),
                                    jObject1.getInt("11"),
                                    jObject1.getInt("12"),
                                    jObject1.getInt("13"),
                                    jObject1.getInt("14"),
                                    jObject1.getInt("15"),
                                    jObject1.getInt("16"),
                                    jObject1.getInt("17")


                            ));
//                            jObject1.getString("name"),
//                            jObject1.getInt("serving"),
//                            jObject1.getBoolean("vegetarian"),
//                            jObject1.getString("ingredients"),
//                            jObject1.getInt("calories"),
//                            jObject1.getInt("fat"),
//                            jObject1.getInt("saturated"),
//                            jObject1.getInt("cholestrol"),
//                            jObject1.getInt("sodium"),
//                            jObject1.getInt("carbohydrate"),
//                            jObject1.getInt("fibre"),
//                            jObject1.getInt("sugars"),
//                            jObject1.getInt("protein"),
//                            jObject1.getInt("vitaminA"),
//                            jObject1.getInt("vitaminC"),
//                            jObject1.getInt("calcium"),
//                            jObject1.getInt("iron"),
//                            jObject1.getInt("id")));
                            //Log.i("debug", ""+results.get(i).calories);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("array", "" + results.size());
            return results;
        }
    }


}
