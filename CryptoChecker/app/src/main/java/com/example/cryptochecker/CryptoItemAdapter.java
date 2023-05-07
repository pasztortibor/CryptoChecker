package com.example.cryptochecker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cryptochecker.model.CryptoItem;

import java.util.ArrayList;

public class CryptoItemAdapter extends RecyclerView.Adapter<CryptoItemAdapter.ViewHolder> implements Filterable {

    private ArrayList<CryptoItem> mCryptoItemsData;
    private ArrayList<CryptoItem> mCryptoItemsDataAll;
    private Context mContext;
    private int lastPosition = -1;
    CryptoItemAdapter(Context context, ArrayList<CryptoItem> itemsData){
        this.mCryptoItemsData = itemsData;
        this.mCryptoItemsDataAll = itemsData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CryptoItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoItemAdapter.ViewHolder holder, int position) {
        CryptoItem currentItem = mCryptoItemsData.get(position);
        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount() {
        return mCryptoItemsData.size();
    }

    @Override
    public Filter getFilter() {
        return cryptoFilter;
    }

    private Filter cryptoFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<CryptoItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0){
                results.count = mCryptoItemsDataAll.size();
                results.values = mCryptoItemsDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(CryptoItem item : mCryptoItemsDataAll){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;

            }
            return null;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mCryptoItemsData = (ArrayList) filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mNameText;
        private TextView mPriceText;
        private TextView mChangeText;
        private ImageView mItemImage;
        public ViewHolder(View itemView){
            super(itemView);
            mNameText = itemView.findViewById(R.id.cryptoName);
            mPriceText = itemView.findViewById(R.id.cryptoPrice);
            mChangeText = itemView.findViewById(R.id.cryptoChange);
            mItemImage = itemView.findViewById(R.id.cryptoImage);
        }

        public void bindTo(CryptoItem currentItem) {
            mNameText.setText(currentItem.getName());
            mPriceText.setText(currentItem.getPrice());
            mChangeText.setText(currentItem.getChange());
            Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);
        }
    }

}


