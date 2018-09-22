package com.gotomypub.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gotomypub.R;
import com.gotomypub.networkutilities.Datum;

import java.util.ArrayList;
import java.util.Calendar;

public class TimingRecyclerViewAdapter extends RecyclerView.Adapter<TimingRecyclerViewAdapter.TextViewHolder> {

    ArrayList<String> timingStringArrayList;
    Context mContext;
    int dayOfWeek;
    public TimingRecyclerViewAdapter(Context context, ArrayList<String> timingStringArrayList_){
        this.timingStringArrayList=timingStringArrayList_;
        Calendar todayCalendar=Calendar.getInstance();
        dayOfWeek=todayCalendar.get(Calendar.DAY_OF_WEEK);
        this.mContext=context;

    }
    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_txtview, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        int dayPosition=position+2;
        dayPosition=dayPosition>7?1:dayPosition;
        String dayTime=timingStringArrayList.get(position);
        String day=dayTime.split(":")[0];
        String time=dayTime.substring(dayTime.indexOf(":"));
      holder.mIdView.setText(day);
      holder.mTimeTextView.setText(time);
      if(dayPosition==dayOfWeek){
          holder.mIdView.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
          holder.mTimeTextView.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
      }
      else {
          holder.mIdView.setTextColor(ContextCompat.getColor(mContext,R.color.header_text));
          holder.mTimeTextView.setTextColor(ContextCompat.getColor(mContext,R.color.header_text));
      }
    }

    @Override
    public int getItemCount() {
        return timingStringArrayList.size();
    }

    public class TextViewHolder extends RecyclerView.ViewHolder{
        public final TextView mIdView;
        public final TextView mTimeTextView;
        public TextViewHolder(View itemView) {
            super(itemView);
            mIdView = (TextView) itemView.findViewById(R.id.txt_time_val);
            mTimeTextView = (TextView) itemView.findViewById(R.id.txt_time_time);
        }
    }



}
