package com.example.deliveryapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryapp.models.Order;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class cartView extends AppCompatActivity {

    Button addButton;
    ListView listView;
    ArrayList<Order> orders;
    MyAdapter adapter;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String EMAIL = "EMAIL";
    private int userID = -1; // Initialize userID with an invalid value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);

        listView = findViewById(R.id.my_list);
        addButton = findViewById(R.id.add_button_ticket);

        orders = new ArrayList<>();
        adapter = new MyAdapter(cartView.this, orders);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order clickedItem = orders.get(position);

                Intent intent = new Intent(cartView.this, UpdateListView.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 1);
            }
        });

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String email = prefs.getString(EMAIL, "");

        if (!email.isEmpty()) {
            getUserID(email, new VolleyCallback() {
                @Override
                public void onSuccess(int result) {
                    userID = result; // Set userID from callback
                    getOrderNotConfermed(); // Fetch orders after userID is set
                }
            });
        }
    }

    private void getOrderNotConfermed() {
        if (userID == -1) {
            Toast.makeText(cartView.this, "UserID not set. Cannot fetch orders.", Toast.LENGTH_LONG).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String status = "inProgress";
        String url = String.format("http://10.0.2.2:5000/getOrders?userId=%d&status=%s", userID, status);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            orders.clear(); // Clear existing data to avoid duplicates
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Order order = new Order();

                                // Adjust these field names based on your JSON response
                                order.setType(jsonObject.optString("type")); // Using optString to avoid JSONException
                                order.setTime(jsonObject.optString("time"));
                                order.setFoodName(jsonObject.optString("foodName"));
                                order.setStatus(jsonObject.optString("status"));
                                // Log for debugging
                                Log.d("OrderData", "FoodName: " + jsonObject.optString("foodName"));

                                orders.add(order);
                            }
                            Log.d("OrderData", "Number of orders fetched: " + orders.size());
                            adapter.notifyDataSetChanged(); // Notify adapter about data change
                        } catch (JSONException e) {
                            Log.e("JSONError", "Error parsing orders JSON", e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("OrderFetchError", "Error fetching orders: " + error.toString());
                if (error.networkResponse != null) {
                    Log.e("OrderFetchError", "Status Code: " + error.networkResponse.statusCode);
                }
            }
        });
        queue.add(jsonArrayRequest);
        // Removed the redundant call to notifyDataSetChanged() here as it should only be called after data is fetched and updated
    }


    private void getUserID(String email, final VolleyCallback callback) {
        if(!email.isEmpty()){
            Log.e("checkUser",email.toString());
            try {
                RequestQueue queue = Volley.newRequestQueue(this);
                String url = "http://10.0.2.2:5000/user/" + email;  // Ensure 'email' is properly defined and encoded

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    Log.e("hi","0");
                                    // Extract data from the response
                                    userID = response.getInt(0); // UserID
                                    String userName = response.getString(1); // UserName
                                    String userMobile = response.getString(2); // UserMobile
                                    String role = response.getString(3); // Role



                                    String userEmail = response.getString(4); // Email
                                    String userPass = response.getString(5);
                                    String str = "userID: " + userID + " UserName: " + userName + " Mobile: " + userMobile + " Role: " + role;
                                    Log.e("hi","1");
                                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                                    Log.e("hi","2");
                                    // Save the data in SharedPreferences
                                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("UserID", userID);
                                    editor.putString("UserName", userName);
                                    editor.putString("UserMobile", userMobile);
                                    editor.putString("Role", role);
                                    editor.putString("Email", userEmail);  // Use userEmail
                                    editor.putString("UserPass",userPass);
                                    editor.apply();
                                    Log.e("hi","3");

                                    callback.onSuccess(userID);

                                } catch (JSONException e) {
                                    Log.e("JSON Error", "Error parsing JSON: " + e.getMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMsg = "Error: ";
                        Log.e("hi","4");
                        if (error.networkResponse != null) {
                            errorMsg += "Response Code: " + error.networkResponse.statusCode;
                            // Attempt to parse server response if available
                            if (error.networkResponse.data != null) {
                                try {
                                    String responseBody = new String(error.networkResponse.data, "utf-8");
                                    JSONObject data = new JSONObject(responseBody);
                                    String serverMessage = data.optString("message", "Error response from server");
                                    errorMsg += ", Message: " + serverMessage;
                                } catch (Exception e) {
                                    errorMsg += ", Error parsing error response";
                                }
                            }
                        } else {
                            errorMsg += error.toString();
                        }
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                });

                queue.add(jsonArrayRequest);
            } catch (Exception e) {
                Log.e("Request Error", "Error creating request: " + e.getMessage());
            }

        }
    }

    // Define a callback interface
    public interface VolleyCallback {
        void onSuccess(int result);
    }
}
