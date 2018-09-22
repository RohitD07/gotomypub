package com.gotomypub.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.gotomypub.R;
import com.gotomypub.utilitycomponents.utils.ConstantCode;

public class BannerPagerAdapter extends PagerAdapter {



        FragmentManager fragmentManager;
        private Context context;
       // private ArrayList<String> imageIdList;
        private  String pubID;
        private int size;
        ViewPager banner_viewpage;
    BaseTarget target;
    ImageView imageView;

    public BannerPagerAdapter(Context context,
                FragmentManager fragmentManager,String pubID_, ViewPager banner_viewpager) {
            this.context = context;
            this.pubID=pubID_;
            this.size = 8;//imageIdList.size();
            this.banner_viewpage=banner_viewpager;
            this.fragmentManager = fragmentManager;



        }

        @Override
        public int getCount() {
            return size;
        }



        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public View instantiateItem(final ViewGroup container,  int position) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.details_image_layout_view, container, false);

              imageView= (ImageView) view.findViewById(R.id.session_photo);

            String imgUrl;

            try {
                String imgpath=(position+1)+".jpg";

                imgUrl = String.format(ConstantCode.BASE_IMGURL,pubID,imgpath);
                Log.d("Img URL", imgUrl);

            /*    Glide.with(context)
                     .load(Uri.parse(imgUrl))
                     .place
                     .into(imageView)*/


                GlideApp.with(context)
                        .load(imgUrl)
                        .centerCrop()
                        .placeholder(R.drawable.ic_blank_placeholder)
                        .error(ContextCompat.getDrawable(context,R.drawable.ic_blank_placeholder))
                        //.transition(DrawableTransitionOptions.withCrossFade()) //Optional
                        /*.skipMemoryCache(true)  //No memory cache
                        .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
                        .placeholder(R.drawable.ic_blank_placeholder)
                        .listener(new RequestListener<Drawable>() {sss
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                               imageView.setImageResource(R.drawable.ic_blank_placeholder);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                imageView.setImageDrawable(resource);
                                return false;
                            }
                        })*/
                        .into(imageView);


            } catch (Exception e) {
                imgUrl = "http://";
            }

           /* try {


                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                int drawableID=R.drawable.s1;
                switch (position){
                    case 0:
                        drawableID=R.drawable.s1;
                        break;
                    case 1:
                        drawableID=R.drawable.s2;
                        break;
                    case 2:
                        drawableID=R.drawable.s3;
                        break;
                    case 3:
                        drawableID=R.drawable.s4;
                        break;
                    case 4:
                        drawableID=R.drawable.s5;
                        break;
                    default:
                        drawableID=R.drawable.s1;
                        break;
                }
                img.setImageDrawable(ContextCompat.getDrawable(context,drawableID));

               // Glide.with(context).load(Uri.parse(s)).into(img);

            } catch (Exception e) {
                e.printStackTrace();
            }*/








            container.addView(view);
            return view;
        }




        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        private int getPosition(int position) {
            return position;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

