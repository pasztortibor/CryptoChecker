package com.example.cryptochecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.cryptochecker.model.CryptoItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CryptoListActivity extends AppCompatActivity {
    private NotificationHelper mNotificationHelper;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private RecyclerView mRecyclerView;
    private ArrayList<CryptoItem> mItemList;
    private CryptoItemAdapter mAdapter;
    private int gridNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_list);

        mNotificationHelper = new NotificationHelper(this);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemList =  new ArrayList<>();

        mAdapter = new CryptoItemAdapter(this, mItemList);

        mRecyclerView.setAdapter(mAdapter);

        initializeData();
    }

    private void initializeData() {
        String[] itemsName = getResources().getStringArray(R.array.crypto_names);
        String[] itemsPrice = getResources().getStringArray(R.array.crypto_prices);
        String[] itemsChange = getResources().getStringArray(R.array.crypto_changes);
        TypedArray itemsImageResource = getResources().obtainTypedArray(R.array.crypto_images);
        mItemList.clear();
        for (int i = 0; i < itemsName.length; i++){
            mItemList.add(new CryptoItem(itemsName[i], itemsPrice[i], itemsChange[i], itemsImageResource.getResourceId(i, 0)));
        }
        itemsImageResource.recycle();
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void figyeles(View view){
        mNotificationHelper.send("A kriptovaluták figyelése elindult!");
    }

    @Override
    protected void onPause() {
        mNotificationHelper.send("Vigyázz, bármikor változhat az árfolyam!");
        super.onPause();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}