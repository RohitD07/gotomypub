package com.gotomypub.customviews;

	import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

	import android.support.v7.widget.RecyclerView;
	import android.util.SparseBooleanArray;
	import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
	import android.view.ViewGroup;
	import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
	import android.widget.ImageView;
	import android.widget.PopupWindow;

import android.content.Context;

		public class PopupWindows {
		protected Context mContext;
		public PopupWindow mWindow;
		protected View mRootView;
		protected Drawable mBackground = null;
		protected WindowManager mWindowManager;
		RecyclerView filterRecyclerView;
		ImageView doneClick;
		SparseBooleanArray filterSparseArray;
		int type;
		private final int FILTER_FEATURES=1;
			private final int FILTER_BEER=2;
		
		/**
		 * Constructor.
		 * 
		 * @param context Context
		 */
		public PopupWindows(Context context,int filterType) {
			mContext	= context;
			mWindow 	= new PopupWindow(context);
			mWindow.setBackgroundDrawable(new BitmapDrawable());
			mWindow.setTouchInterceptor(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						mWindow.dismiss();
						
						return true;
					}
					
					return false;
				}
			});

			mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			type=filterType;
			//spreShow();
		}
		
		/**
		 * On dismiss
		 */
		protected void onDismiss() {		
		}
		
		/**
		 * On show
		 */
		public void onShow(View anchorView) {
			mWindow.showAsDropDown(anchorView);
		}


		/**
		 * On pre show
		 */
		protected void preShow() {
			if (mRootView == null) 
				throw new IllegalStateException("setContentView was not called with a view to display.");
		


		//	if (mBackground == null) 
		//		mWindow.setBackgroundDrawable(new BitmapDrawable());
		//else 
		//		mWindow.setBackgroundDrawable(new BitmapDrawable());

			//mWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
			//mWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
			
			mWindow.setWidth(LayoutParams.WRAP_CONTENT);
			mWindow.setHeight(LayoutParams.WRAP_CONTENT);
			
			mWindow.setTouchable(true);
			mWindow.setFocusable(true);
			mWindow.setOutsideTouchable(true);

			mWindow.setContentView(mRootView);
		}

		/**
		 * Set background drawable.
		 * 
		 * @param background Background drawable
		 */
		public void setBackgroundDrawable(Drawable background) {
			mWindow.setBackgroundDrawable(background);
		}

		/**
		 * Set content view.
		 * 
		 * @param root Root view
		 */
		public void setContentView(View root) {
			mRootView = root;
			
			mWindow.setContentView(root);
			preShow();


		}

		/**
		 * Set content view.
		 * 
		 * @param layoutResID Resource id
		 */
		public void setContentView(int layoutResID) {
			LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			setContentView(inflator.inflate(layoutResID, null));
		}

		/**
		 * Set listener on window dismissed.
		 * 
		 * @param listener
		 */
		public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
			mWindow.setOnDismissListener(listener);  
		}

		/**
		 * Dismiss the popup window.
		 */
		public void dismiss() {
			mWindow.dismiss();
		}


			public class FilterRecyclerViewAdapter extends RecyclerView.Adapter{
				@Override
				public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
					return null;
				}

				@Override
				public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

				}

				@Override
				public int getItemCount() {
					return 0;
				}
			}
	}

	

