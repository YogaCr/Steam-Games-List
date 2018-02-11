package id.sch.smktelkom_mlg.tugas02.xirpl338.steamapigames;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Game> gameList = new ArrayList<>();
    List<Integer> appid = new ArrayList<>();
    RecyclerView recyclerView;
    GameAdapter gameAdapter;
    boolean store = false;
    int index = 0;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = findViewById(R.id.dim);

        recyclerView = findViewById(R.id.rv_data);
        volleyProcess("http://api.steampowered.com/ISteamApps/GetAppList/v0002/?format=json");
    }

    public void volleyProcess(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getJSON(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (!store) {
                JSONObject applist = jsonObject.getJSONObject("applist");
                JSONArray apps = applist.getJSONArray("apps");

                for (int x = 0; x < 300; x++) {
                    JSONObject obj = apps.getJSONObject(x);
                    appid.add(obj.getInt("appid"));

                }
                store = true;

                volleyProcess("http://store.steampowered.com/api/appdetails?appids=" + String.valueOf(appid.get(index)));
            } else {
                JSONObject id = jsonObject.getJSONObject(String.valueOf(appid.get(index)));

                if (id.getBoolean("success")) {
                    JSONObject data = id.getJSONObject("data");
                    if (data.get("type").equals("game")) {
                        String developer = "";
                        String publisher = "";
                        if (data.has("developers")) {
                            JSONArray developers = data.getJSONArray("developers");

                            for (int x = 0; x < developers.length(); x++) {
                                developer += developers.getString(x);
                                if (x < developers.length() - 1) {
                                    developer += ",";
                                }
                            }
                        }
                        if (data.has("publishers")) {
                            JSONArray publishers = data.getJSONArray("publishers");

                            for (int x = 0; x < publishers.length(); x++) {
                                publisher += publishers.getString(x);
                                if (x < publishers.length() - 1) {
                                    publisher += ",";
                                }
                            }
                        }
                        Game game = new Game(data.getString("name"), data.getString("header_image"), developer, publisher);
                        gameList.add(game);
                        gameAdapter = new GameAdapter(gameList, MainActivity.this);
                        LinearLayoutManager linearLayout = new LinearLayoutManager(MainActivity.this);
                        recyclerView.setLayoutManager(linearLayout);
                        recyclerView.setAdapter(gameAdapter);
                    }
                }

                index++;
                if (index < appid.size()) {
                    volleyProcess("http://store.steampowered.com/api/appdetails?appids=" + String.valueOf(appid.get(index)));
                } else {
                    relativeLayout.setVisibility(View.INVISIBLE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
