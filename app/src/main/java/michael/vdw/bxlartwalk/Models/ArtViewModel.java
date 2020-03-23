package michael.vdw.bxlartwalk.Models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArtViewModel extends ViewModel {
    public ExecutorService threadExecutor = Executors.newFixedThreadPool(4);


    public ArtViewModel() {
    }

    ;

    private void fetchArt() {
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://bruxellesdata.opendatasoft.com/explore/dataset/comic-book-route/api/")
                        .get()
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String json = response.body().string();
                    JSONObject jsonObject = new JSONObject(json);
                    //JSONArray nodig?
                   // JSONArray jsonArray = new JSONArray(json);
//                    TODO: aanpassen naar de Art route

                    String id = jsonObject.getString("id");
                    String title = jsonObject.getString("value");
                    String authors = jsonObject.getString("author(s)");
                    String characters = jsonObject.getString("character(s)");
                    String coordinates = jsonObject.getString("geocoordinates");
                    String year = jsonObject.getString("year");
                    String photo = jsonObject.getString("photo");


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
