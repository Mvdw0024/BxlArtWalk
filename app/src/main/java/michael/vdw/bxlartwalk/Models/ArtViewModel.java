package michael.vdw.bxlartwalk.Models;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import michael.vdw.bxlartwalk.Room.CbArtDataBase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArtViewModel extends AndroidViewModel {
    private Context context;
    private CbArtDataBase cbArtDataBase;
    private MutableLiveData<ArrayList<CbArt>> cbRouteArt;
    private MutableLiveData<ArrayList<StreetArt>> streetArtRoute;
    public ExecutorService threadExecutor = Executors.newFixedThreadPool(4);

    public ArtViewModel(Application application) {
        super(application);
        this.cbRouteArt = new MutableLiveData<>();
        this.streetArtRoute = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<CbArt>> getCbRouteArt() {
        fetchCbArt();
        return cbRouteArt;
    }

    public MutableLiveData<ArrayList<StreetArt>> getStreetArtRoute() {
        fetchCbArt();
        return streetArtRoute;
    }

    public List<StreetArt> getAllStreetArtFromDataBase() {
        return CbArtDataBase.getSharedInstance(getApplication()).streetArtDao().getAllStreetArt();
    }

    public List<CbArt> getAllCbArtFromDataBase() {
        return CbArtDataBase.getSharedInstance(getApplication()).cbArtDao().getAllCb();
    }

    public void insertCbArtInDataBase(CbArt cbArt) {
        CbArtDataBase.getSharedInstance(getApplication()).cbArtDao().insertCbArt(cbArt);
    }

    public void insertStreetArtInDataBase(StreetArt streetArt) {
        CbArtDataBase.getSharedInstance(getApplication()).streetArtDao().insertStreetArt(streetArt);
    }

    public CbArt findCbById(String id) {
        return CbArtDataBase.getSharedInstance(getApplication()).cbArtDao().findById(id);
    }

    public StreetArt findStreetArtById(String id) {
        return CbArtDataBase.getSharedInstance(getApplication()).streetArtDao().findById(id);
    }

    private void fetchCbArt() {
//        CbArtDataBase.getSharedInstance(getApplication()).cbArtDao().nukeTable();
        threadExecutor.execute(new Runnable() {


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
                        String jsonId = jsonRecords.getJSONObject(i).getString("recordid");
                        JSONObject jsonArt = jsonRecords.getJSONObject(i).getJSONObject("fields");

                        // Prepare coordinates for Latlng

                        final CbArt currentCbArt = new CbArt(
                                jsonId,
                                jsonArt.getString("personnage_s"),
                                jsonArt.getString("auteur_s"),
                                jsonArt.getJSONObject("photo").getString("filename"),
//                                new LatLng(currentLat, currentLng),
                                jsonArt.getJSONArray("coordonnees_geographiques").getDouble(0),
                                jsonArt.getJSONArray("coordonnees_geographiques").getDouble(1),
                                Integer.parseInt(jsonArt.getString("annee"))
                        );
                        comicBookArt.add(currentCbArt);
                        //TODO: methode moet nog getest worden.
                        CbArtDataBase.dbExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                if (findCbById(currentCbArt.getId()) == null) {
                                    insertCbArtInDataBase(currentCbArt);
                                }
                            }
                        });
                        i++;
                    }

                    // Geeft resultaten van de API call in de logcat
                    for (CbArt cbArt : comicBookArt) {
                        Log.d("ReceivedData", "" + cbArt);
                    }

                    cbRouteArt.postValue(comicBookArt);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void fetchStreetArt() {
        threadExecutor.execute(new Runnable() {


            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://opendata.brussel.be/api/records/1.0/search/?dataset=street-art")
                        .get()
                        .build();


                try {
                    Response response = client.newCall(request).execute();

                    String postData = response.body().string();

                    JSONObject jsonObject = new JSONObject(postData);
                    JSONArray jsonRecords = jsonObject.getJSONArray("records");

                    int i = 0;
                    ArrayList<StreetArt> streetArtJSON = new ArrayList<>();
                    while (i < jsonRecords.length()) {
                        String jsonId = jsonRecords.getJSONObject(i).getString("recordid");
                        JSONObject jsonStreetArt = jsonRecords.getJSONObject(i).getJSONObject("fields");

                        // Prepare coordinates for Latlng

                        final StreetArt curStreetArt = new StreetArt(
                                jsonId,
                                jsonStreetArt.getString("adres"),
                                jsonStreetArt.getString("werknaam"),
                                jsonStreetArt.getString("naam_van_de_kunstenaar"),
                                jsonStreetArt.getJSONObject("photo").getString("filename"),
                                Integer.parseInt(jsonStreetArt.getString("jaar")),
                                jsonStreetArt.getJSONArray("geocoordinates").getDouble(0),
                                jsonStreetArt.getJSONArray("geocoordinates").getDouble(1)

                        );
                        streetArtJSON.add(curStreetArt);
                        //TODO: methode moet nog getest worden.
                        CbArtDataBase.dbExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                if (findStreetArtById(curStreetArt.getId()) == null) {
                                    insertStreetArtInDataBase(curStreetArt);
                                }
                            }
                        });
                        i++;
                    }

                    // Geeft resultaten van de API call in de logcat
                    for (StreetArt curStreetArt : streetArtJSON) {
                        Log.d("ReceivedStreetData", "" + curStreetArt);
                    }

                    streetArtRoute.postValue(streetArtJSON);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
