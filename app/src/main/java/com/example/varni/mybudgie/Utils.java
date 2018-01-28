package com.example.varni.mybudgie;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by akash on 1/28/2018.
 */

public class Utils {
    private static final String expenseURL = "http://129.21.83.18:3000/expenses=";
    private static final String earnURL = "http://129.21.83.18:3000/earnings=";

    public static void getExpenses(String date, Context context, final expenseListener listener){
        //Sending request - on new Thread
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, expenseURL +date, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<Expense> expenses = new ArrayList<>();
                            for(int i =0;i<response.length();i++){
                                JSONObject obj = (JSONObject)response.get(i);
                                String source = obj.getString("source");
                                double value = obj.getDouble("value");
                                String desp = obj.getString("description");
                                String name = obj.getString("name");
                                expenses.add(new Expense(source,value,desp,name));
                            }
                            listener.onResult(expenses);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        queue.add(jsObjRequest);
    }

    public static void getEarnings(String date, Context context, final earningListener listener){
        //Sending request - on new Thread
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, earnURL +date, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<Earning> earnings = new ArrayList<>();
                    for(int i =0;i<response.length();i++){
                        JSONObject obj = (JSONObject)response.get(i);
                        String source = obj.getString("source");
                        double value = obj.getDouble("value");
                        String desp = obj.getString("description");
                        String name = obj.getString("name");
                        earnings.add(new Earning(source,value,desp,name));
                    }
                    listener.onResult(earnings);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsObjRequest);
    }

    public interface expenseListener{
        void onResult(ArrayList<Expense> expenses);
    }

    public interface earningListener{
        void onResult(ArrayList<Earning> earnings);
    }
}
