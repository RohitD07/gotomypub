package com.gotomypub.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gotomypub.R;
import com.gotomypub.networkutilities.BeerItem;
import com.gotomypub.networkutilities.Datum;
import com.gotomypub.utilitycomponents.utils.ConstantCode;

import java.util.ArrayList;

public class RecycleerImageAdapter extends RecyclerView.Adapter<RecycleerImageAdapter.ViewHolder> {
    String[] imageArrayList;
    Context mContext;

    public RecycleerImageAdapter(String[] imgStrings,Context context){
        this.imageArrayList=imgStrings;
        this.mContext=context;
    }
    @Override
    public RecycleerImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_imageview, parent, false);
        return new RecycleerImageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//       holder.mView.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_pin_beer));

        try {
            String imgpath=(position+1)+".jpg";
          String apiUrl=imageArrayList[position];
          apiUrl=apiUrl.substring(2,apiUrl.length()-1);
          String  imgUrl = ConstantCode.BASE_BEERIMGURL+apiUrl;//.get(position).getUrl();
            Log.d("Img URL", imgUrl);

            /*    Glide.with(context)
                     .load(Uri.parse(imgUrl))
                     .place
                     .into(imageView)*/


            GlideApp.with(mContext)
                    .load(imgUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_blank_placeholder)
                    .error(ContextCompat.getDrawable(mContext,R.drawable.ic_blank_placeholder))

                    .into(holder.mView);


        } catch (Exception e) {
            //imgUrl = "http://";
            e.printStackTrace();
        }

    }



    @Override
    public int getItemCount() {
        return imageArrayList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mView;
        //public HashMap<String,String>  mItem;
        public Datum mItem;

        public ViewHolder(View view) {
            super(view);
            mView = (ImageView) view;

        }

        @Override
        public String toString() {
            return super.toString() + " '"  + "'";
        }
    }
}
