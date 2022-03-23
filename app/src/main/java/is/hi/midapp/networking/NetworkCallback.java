package is.hi.midapp.networking;

import java.util.List;

import is.hi.midapp.Persistance.Entities.Task;
import is.hi.midapp.Persistance.Entities.TaskCategory;
import is.hi.midapp.Persistance.Entities.TaskStatus;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkCallback {

    @GET("homeAPI")
    Call<List<Task>> getTasks();

    @POST("addTaskAPI")
    Call<Task> addTask(@Body Task task);

    @GET("homefAPI")
    Call<List<Task>> getTasksWFilters(
            @Query("priority") Boolean priority,
            @Query("category") String category,
            @Query("status") String status);

    @GET("homesAPI")
    Call<List<Task>> getTaskByName(
            @Query("name") String name);

}


/*public interface NetworkCallback<T> {

    void onSuccess(T result);

    void onFailure(String errorString);


}*/




