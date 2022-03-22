package is.hi.midapp.networking;

import java.util.List;

import is.hi.midapp.Persistance.Entities.Task;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface NetworkCallback {

    @GET("homeAPI")
    Call<List<Task>> getTasks();
}


/*public interface NetworkCallback<T> {

    void onSuccess(T result);

    void onFailure(String errorString);


}*/




