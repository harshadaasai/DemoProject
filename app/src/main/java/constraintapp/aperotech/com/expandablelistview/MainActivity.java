package constraintapp.aperotech.com.expandablelistview;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    ArrayList<Model> groupData;
    ArrayList<ArrayList<Model>> childData;
    HashMap<String, ArrayList<Model>> expandableListDetail;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        groupData = new ArrayList<>();
        childData = new ArrayList<>();
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        getData();

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });


    }

    public  void getData()
    {
        expandableListDetail = new HashMap<>();

        final Gson gson = new Gson();
        String url = "https://api.myjson.com/bins/vt8zx";

        // prepare the Request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response

                        JSONArray res = null;
                        try {
                            res = response.getJSONArray("response");
                            for(int i = 0 ; i <  res.length(); i++)
                            {
                                Model model = gson.fromJson(res.get(i).toString(), Model.class);
                                groupData.add(model);
                                childData.add(getAnswers());
                            }

                            expandableListAdapter = new CustomExpandableListAdapter(context, groupData, childData, expandableListView);
                            expandableListView.setAdapter(expandableListAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });


        // add it to the RequestQueue
        int socketTimeout = 10000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(request, "req_questions");

    }


    public ArrayList getAnswers()
    {
        final ArrayList child = new ArrayList();
        final Gson gson = new Gson();
        String url = "https://api.myjson.com/bins/19i7wt";

        // prepare the Request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response


                        JSONArray res = null;
                        try {
                            res = response.getJSONArray("response");
                            for(int i = 0 ; i <  res.length(); i++)
                            {
                                Model model = gson.fromJson(res.get(i).toString(), Model.class);
                                child.add(model);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });


        // add it to the RequestQueue
        int socketTimeout = 10000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(request, "req_questions");


        return child;

    }
}

