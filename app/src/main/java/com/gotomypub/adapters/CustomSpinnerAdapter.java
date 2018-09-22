package com.gotomypub.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;

import com.gotomypub.R;
import com.gotomypub.networkutilities.BeerItem;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by rohitd061 on 13/07/2017.
 * {@link ArrayAdapter} that can display a {@link Object}
 * This adadter is used to set data to various spinner using in app
 *
 */

public class CustomSpinnerAdapter extends ArrayAdapter {

    private static final int TYPE_NONE=0;
    private static final int TYPE_APOINTEE_RELATIONSHIP=1;
    public static final int TYPE_NOMINEE_RELATIONSHIP=2;
    public static final int TYPE_INSUREDPERSON_RELATIONSHIP=3;
    public static final int TYPE_OCCUPATION=4;

    private ArrayList<BeerItem> objectList;
    private ArrayList<BeerItem>  resultList;
    private ArrayList<BeerItem>  filterList;
    private LayoutInflater layoutInflater;
    private int type= TYPE_NONE;
    AutoCompleteFilterable autoCompleteFilterable;

    public ArrayList<BeerItem>  getObjectList() {
        return objectList;
    }

    public void setObjectList(ArrayList<BeerItem>  objectList) {
        this.objectList = objectList;
    }

    public CustomSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<BeerItem> objects) {
        //super(context, resource, objects);
        super(context,resource);

        objectList =new ArrayList<BeerItem>();
        resultList=new ArrayList<BeerItem>();

        for(BeerItem beerItem:objects){
            objectList.add(beerItem);
            resultList.add(beerItem);
        }

        getFilter();
    }

    public CustomSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects, String spinnerPrompt, int type_) {
        super(context, resource, objects);
        type =type_;
       // objectList =new ArrayList(objects.size());

        objectList =new ArrayList(objects);
        resultList=new ArrayList(objectList);




        //resultList=new ArrayList(objectList);
        getFilter();

    }





        @NonNull
    @Override
    public Filter getFilter() {
        //return super.getFilter();
        if(autoCompleteFilterable==null){
            autoCompleteFilterable=new AutoCompleteFilterable();
        }


        return autoCompleteFilterable;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        return getCustomView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getDropDownView(position, convertView, parent);
        return getCustomView(position,convertView,parent);
    }

    private View getCustomView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        final CheckBox mTextView;
        //if(convertView==null)
        layoutInflater = LayoutInflater.from(parent.getContext());
        convertView= layoutInflater.inflate(R.layout.spinner_item,null);
        mTextView= (CheckBox) convertView;
       mTextView.setText(((BeerItem)getItem(position)).getName());
       mTextView.setChecked(((BeerItem)getItem(position)).isSelected());
       mTextView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ((BeerItem)getItem(position)).setSelected(mTextView.isChecked());
           }
       });
        return convertView;

    }



    @Nullable
    @Override
    public Object getItem(int position) {
        //return super.getItem(position);
        return resultList.get(position);
    }

    @Override
    public int getCount() {
        //return super.getCount();
        return resultList.size();
    }

    class AutoCompleteFilterable extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            filterList=new ArrayList<BeerItem>();
            if(constraint.length()>1){
              for(BeerItem object : objectList){
                  String searchVal="";
                 searchVal=object.getName().toLowerCase();
                  if(searchVal.contains(constraint.toString().toLowerCase())){
                      filterList.add(object);
                  }


              }
                results.values=filterList;
                results.count=filterList.size();
            }
            else {
                filterList=new ArrayList(objectList);
                results.values=filterList;
                results.count=filterList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
              if(results!=null && results.count>0){
                  resultList=new ArrayList(filterList);
                  notifyDataSetChanged();
              }
        }
    }


}
