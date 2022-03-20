package is.hi.midapp.networking;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.ContentHandler;
import java.util.List;

import is.hi.midapp.Persistance.Entities.Task;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Hlutverk ad saekja hluti fra networkinu og skila i gegnum callbac
public class NetworkManager {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://10.0.2.2:8080";

    public static Retrofit getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /*private static NetworkManager mInstance;
    private static RequestQueue mQueue;
    private static Context mContext;


    public static synchronized NetworkManager getInstance(Context context){
        if (mInstance == null){
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    private NetworkManager(Context context){
        mContext = context;
        mQueue = getRequestQueue();
    }

    public static RequestQueue getRequestQueue() {
        if(mQueue == null){
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }

    public void getTasks(final NetworkCallback<List<Task>> callback){
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "homeAPI", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Task>>(){}.getType();
                List<Task> allTasks = gson.fromJson(response, listType);
                callback.onSuccess(allTasks);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }*/

}
