package com.gotomypub.adapters;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gotomypub.R;
import com.gotomypub.interfaces.ListItemClick;
import com.gotomypub.networkutilities.Datum;
import com.gotomypub.utilitycomponents.utils.UtilityMethods;

import java.util.HashMap;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RestListRecyclerViewAdapter extends RecyclerView.Adapter<RestListRecyclerViewAdapter.ViewHolder> {

    public List<HashMap<String,String>> getmValues() {
        return restaurantList;
    }

    public void setmValues(List<HashMap<String,String>> restaurantList_) {
        this.restaurantList = restaurantList_;
    }

    private List<HashMap<String,String>> restaurantList;
    private List<Datum> pubList;
    Location currentLocation;

    public ListItemClick getListItemClick() {
        return listItemClick;
    }

    public void setListItemClick(ListItemClick listItemClick) {
        this.listItemClick = listItemClick;
    }

    ListItemClick listItemClick;
   // private final OnListFragmentInteractionListener mListener;

   /* public RestListRecyclerViewAdapter(List<HashMap<String,String>>   items) {
        restaurantList = items;

    }*/

    public RestListRecyclerViewAdapter(List<Datum>   items, Location currentLocatopn_) {
        this.pubList = items;
        this.currentLocation=currentLocatopn_;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recylce_item_rest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
      /*  holder.mItem = restaurantList.get(position);
        String name = holder.mItem .get("place_name");

        // Getting vicinity
        String vicinity = holder.mItem .get("vicinity");

        holder.mIdView.setText(name);
        holder.mContentView.setText(vicinity);
*/

        holder.mItem = pubList.get(position);
        String name = holder.mItem .getName();

        // Getting vicinity
        String vicinity = UtilityMethods.textNullReturn(holder.mItem .getAddress1());

        holder.mIdView.setText(name);
        holder.mContentView.setText(vicinity);
        float distance=UtilityMethods.getLocationDistanceInMeter(holder.mItem.getLat(),holder.mItem.getLng(),currentLocation);
        holder.mDistanceTextView.setText(String.format("%.02f", distance)+" Km away");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItemClick.onItemClick(holder.mItem,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return pubList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDistanceTextView;
        //public HashMap<String,String>  mItem;
        public Datum  mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDistanceTextView= (TextView) view.findViewById(R.id.txt_distance);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
