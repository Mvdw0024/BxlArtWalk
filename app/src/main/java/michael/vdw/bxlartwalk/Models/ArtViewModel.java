package michael.vdw.bxlartwalk.Models;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

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
    private MutableLiveData<CbArt> cbRouteArt;
    public ExecutorService threadExecutor = Executors.newFixedThreadPool(4);


    public ArtViewModel() {
        this.cbRouteArt = new MutableLiveData<>();
    }

    public MutableLiveData<CbArt> getCbRouteArt() {
        fetchArt();
        return cbRouteArt;
    }

    ;

    private void fetchArt() {
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
//                        .url("https://bruxellesdata.opendatasoft.com/explore/dataset/comic-book-route/api/")
                        .url("https://bruxellesdata.opendatasoft.com/api/records/1.0/search/?dataset=comic-book-route")
                        .get()
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String json = response.body().string();
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        String id = jsonObject.getString("id");
                        //LatLng geoco = jsonObject.getJSONArray("geocoordinates").getString();

                        String title = jsonObject.getString("value");
                        String authors = jsonObject.getString("author(s)");
                        String characters = jsonObject.getString("character(s)");
                        String coordinates = jsonObject.getString("geocoordinates");
                        Integer year = jsonObject.getInt("year");
                        Integer photo = jsonObject.getInt("photo");


                        Log.d("requestresult", title);
                        //TODO: what is wrong here ??
                        //nog steeds error wanneer ik deze wil toevoegen//
                        // CbArt cbroute = new CbArt(authors,characters,id,title, coordinates, year, photo);
                        CbArt cbroute = new CbArt();
                        cbRouteArt.postValue(cbroute);
                    }


                } catch (IOException | JSONException e) {
                    Log.d("requestresult", "Lap, we zitten in de catch...");
                }
            }
        });
    }
}
