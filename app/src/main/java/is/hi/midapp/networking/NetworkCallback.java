package is.hi.midapp.networking;

import java.util.List;

import is.hi.midapp.Persistance.Entities.PostTask;
import is.hi.midapp.Persistance.Entities.Task;
import is.hi.midapp.Persistance.Entities.TaskCategory;
import is.hi.midapp.Persistance.Entities.TaskStatus;
import is.hi.midapp.Persistance.Entities.User;
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
    //TODO PUT/POST task f. change
    //TODO kannski GET task by ID

    @DELETE("deleteTaskAPI/{id}")
    Call<Task> deleteTask(@Path("id") long id);

    @POST("addATaskAPI")
    Call<Task> addATask(@Body PostTask postTask);

    @GET("homeAPI")
    Call<List<Task>> getTasks();

    @GET("homefAPI")
    Call<List<Task>> findTasks(
            @Query("priority1") Boolean priority1,
            @Query("priority2") Boolean priority2,
            @Query("category1") String category1,
            @Query("category2") String category2,
            @Query("category3") String category3,
            @Query("category4") String category4,
            @Query("category5") String category5,
            @Query("category6") String category6,
            @Query("category7") String category7,
            @Query("category8") String category8,
            @Query("status1") String status1,
            @Query("status2") String status2,
            @Query("status3") String status3);

    @GET("homesAPI")
    Call<List<Task>> getTaskByName(
            @Query("name") String name);

    @POST("signupAPI")
    Call<User> addATask(@Body User user);

}





