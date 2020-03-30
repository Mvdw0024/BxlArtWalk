package michael.vdw.bxlartwalk.Models;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import michael.vdw.bxlartwalk.Room.CbArtDataBase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArtViewModel extends ViewModel {
    private Context context;
    private MutableLiveData<ArrayList<CbArt>> cbRouteArt;
    public ExecutorService threadExecutor = Executors.newFixedThreadPool(4);

    public ArtViewModel() {
        this.cbRouteArt = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<CbArt>> getCbRouteArt() {
        if (cbRouteArt == null) {
            fetchArt();
        }
        return cbRouteArt;
    }

    private void fetchArt() {
        threadExecutor.execute(new Runnable() {

            private CbArt currentCbArt;

            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://bruxellesdata.opendatasoft.com/api/records/1.0/search/?dataset=comic-book-route")
                        .get()
                        .build();


                try {
                    Response response = client.newCall(request).execute();

                    String postData = response.body().string();

                    JSONObject jsonObject = new JSONObject(postData);
                    JSONArray jsonRecords = jsonObject.getJSONArray("records");

                    int i = 0;
                    ArrayList<CbArt> comicBookArt = new ArrayList<>();
                    while (i < jsonRecords.length()) {
                        JSONObject jsonArt = jsonRecords.getJSONObject(i).getJSONObject("fields");

                        // Prepare coordinates for Latlng
                        Double currentLat = jsonArt.getJSONArray("coordonnees_geographiques").getDouble(0);
                        Double currentLng = jsonArt.getJSONArray("coordonnees_geographiques").getDouble(1);

                        currentCbArt = new CbArt(
                                jsonArt.getString("personnage_s"),
                                jsonArt.getString("auteur_s"),
                                jsonArt.getJSONObject("photo").getString("filename"),
                                new LatLng(currentLat, currentLng),
                                Integer.parseInt(jsonArt.getString("annee"))
                        );
                        comicBookArt.add(currentCbArt);
                        i++;
                    }

                    cbRouteArt.postValue(comicBookArt);
                    //TODO: methode moet nog getest worden.
                    CbArtDataBase.getSharedInstance(context).cbArtDao().insertCbArt(currentCbArt);

                } catch (IOException | JSONException e) {
                    Log.d("requestresult", "Lap, we zitten in de catch...");
                    e.printStackTrace();
                }


            }
        });
    }
}
