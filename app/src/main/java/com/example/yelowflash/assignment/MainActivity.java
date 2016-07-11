package com.example.yelowflash.assignment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    public static final String BASEURL = "https://api.github.com/repos/crashlytics/secureudid/issues?sort=updated";
    @Bind(R.id.rv_issues)
    RecyclerView rvIssue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (Factory.isConnectingToInternet(MainActivity.this)) {
            new FetchIssues().execute();
        }
    }

    private ArrayList<Issue> getListFromJson(JSONArray issueArray) throws JSONException {
        ArrayList<Issue> list = new ArrayList<>();
        for (int i = 0; i < issueArray.length(); i++) {
            JSONObject issueObject = issueArray.getJSONObject(i);
            String state = issueObject.getString(Constant.STATE);
            if (state.equals(Constant.STATEOPEN)) {
                String title = issueObject.getString(Constant.TITLE);
                JSONObject userObject = issueObject.getJSONObject(Constant.USER);
                String userName = userObject.getString(Constant.LOGIN);
                String desc = issueObject.getString(Constant.BODY);
                Issue issue = new Issue(title, userName, desc);
                list.add(issue);
            }
        }
        return list;
    }

    private void configureRecyclerView(ArrayList<Issue> issueArrayList) {
        rvIssue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvIssue.setHasFixedSize(true);
        rvIssue.setItemAnimator(new DefaultItemAnimator());
        IssueAdapter adapter = new IssueAdapter(MainActivity.this, issueArrayList);
        rvIssue.setAdapter(adapter);
    }

    private class FetchIssues extends AsyncTask<Void, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("please Wait");
            dialog.setMessage("Getting Issues");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String issues) {
            super.onPostExecute(issues);
            dialog.dismiss();
            try {
                Log.i("response", issues);
                JSONArray issueArray = new JSONArray(issues);
                if (issueArray.length() > 0) {
                    ArrayList<Issue> issueArrayList = getListFromJson(issueArray);
                    configureRecyclerView(issueArrayList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                Toast.makeText(MainActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(BASEURL)
                    .addHeader("User-Agent", "Awesome-Octocat-App")
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();
                return responseBody;

            } catch (SocketTimeoutException e) {
                return null;
            } catch (IOException e) {
                Log.i("Message", e.getMessage());
            }
            return null;
        }
    }
}
