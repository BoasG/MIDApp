package networking;

import java.util.List;

import is.hi.midapp.Persistance.Entities.Task;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NetworkCallback<T> {
    //@GET("home")
    //Call<List<Task>> alltasks();

    //@DELETE("change/{id}")
    //Call<ResponseBody> taskDelete(@Header("Authorization") String authorization, @Path("id") long id);

    void onSuccess(T result);

    void onFailure(String errorString);


}
