package com.gotomypub.mapcluster;

import android.content.Context;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.gotomypub.R;
import com.gotomypub.utilitycomponents.utils.ConstantCode;

public class CustomMapClusterRenderer <T extends ClusterItem> extends DefaultClusterRenderer<T> {
    public CustomMapClusterRenderer(Context context, GoogleMap map, ClusterManager<T> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<T> cluster) {
        //start clustering if 2 or more items overlap
        return cluster.getSize() >= ConstantCode.MINIMUM_CLUSTER_SIZE;
    }

    @Override
    protected void onBeforeClusterItemRendered(T item,
                                               MarkerOptions markerOptions) {
        MapClusterItem markerItem = (MapClusterItem) item;
        BitmapDescriptor icon;
        if(markerItem.isActive()){
            icon= BitmapDescriptorFactory.fromResource(R.drawable.pointerorange);
        }
        else {
            icon= BitmapDescriptorFactory.fromResource(R.drawable.pointergray);
        }

        markerOptions.icon(icon);
    }
}