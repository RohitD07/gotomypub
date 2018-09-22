package com.gotomypub.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gotomypub.R;
import com.gotomypub.networkutilities.Datum;
import com.gotomypub.networkutilities.PubFeatureItem;

import java.util.ArrayList;

public class PubFeatureRecyclerViewAdapter extends RecyclerView.Adapter<PubFeatureRecyclerViewAdapter.FeatureViewHolder> {

   ArrayList<PubFeatureItem> pubFeatureItemArrayList;

   public PubFeatureRecyclerViewAdapter(ArrayList<PubFeatureItem> pubFeatureItems_){
       this.pubFeatureItemArrayList=pubFeatureItems_;
   }

    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_feature_item_list, parent, false);
        */
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spinner_item, parent, false);


        return new FeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureViewHolder holder, int position) {
        //  holder.mContentView.setText(pubFeatureItemArrayList.get(position).getName());
      //  holder.mIdView.setText(pubFeatureItemArrayList.get(position).getId());
        holder.spinner_text.setChecked(true);
        holder.spinner_text.setEnabled(false);
        holder.spinner_text.setText(pubFeatureItemArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return pubFeatureItemArrayList.size();
    }

    public class FeatureViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final CheckBox spinner_text;

        //public HashMap<String,String>  mItem;
        public Datum mItem;

        public FeatureViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.icon_txt);
            mContentView = (TextView) view.findViewById(R.id.txt_features);
            spinner_text=(CheckBox) view.findViewById(R.id.spinner_text);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
