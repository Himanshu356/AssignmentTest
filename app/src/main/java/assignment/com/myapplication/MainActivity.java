package assignment.com.myapplication;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import assignment.com.myapplication.adapters.RowAdapter;
import assignment.com.myapplication.app.AppController;
import assignment.com.myapplication.model.Rows;
import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SpotsDialog spotsDialog;
    RecyclerView recyclerView;
    private static ArrayList<Rows> rowData = new ArrayList<Rows>();
    RowAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        getdata();
                                    }
                                }
        );

    }

    public  void getdata() {

        try{
            spotsDialog = new SpotsDialog(MainActivity.this, "Getting Information...", R.style.CustomSpotsDialog);
            spotsDialog.setCanceledOnTouchOutside(false);
            spotsDialog.setCancelable(false);
            spotsDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (spotsDialog!=null)
                    spotsDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    Log.e("post response::", response);
                    try{
                        rowData.clear();

                        JSONObject jsonObject=new JSONObject(response);

                        String title=jsonObject.getString("title");
                        getSupportActionBar().setTitle(title);


                        JSONArray jsonArray = jsonObject.getJSONArray("rows");

                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject dataJsonObject = jsonArray.optJSONObject(i);
                            String title_1=dataJsonObject.optString("title");
                            String description=dataJsonObject.optString("description");
                            String imageHref=dataJsonObject.optString("imageHref");
                            Rows dataModel = new Rows();
                            dataModel.setTitle(title_1);
                            dataModel.setDescription(description);
                            dataModel.setImageHref(imageHref);
                            rowData.add(dataModel);
                        }
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        adapter = new RowAdapter(MainActivity.this, rowData);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){

//
            };

            stringRequest.setShouldCache(false);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        getdata();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
