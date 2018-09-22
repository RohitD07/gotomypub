package com.gotomypub.utilitycomponents.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore.Images;


import com.gotomypub.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rohitd061 on 19/06/2017.
 *To perform all Bitmap Operation
 */

public class BitmapOpeations {

    @SuppressLint("NewApi")
    public static Bitmap compressImage(Context mContext, String fromUri,String toUri,boolean fromCamera) {

        String filePath = fromUri;
        Bitmap scaledBitmap = null;
        Bitmap bmp=null;
        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
         bmp = BitmapFactory.decodeFile(filePath, options);
        /*File file=new File(fromUri);
        Uri photoUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID+".fileprovider", file);*/


        /*InputStream in = null;
        try {
             in = mContext.getContentResolver().openInputStream(
                    photoUri);
            bmp=BitmapFactory.decodeStream(in, null, options);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float maxHeight = mContext.getResources().getDimension(R.dimen.kycDocHeight);//mContext.getResources().getDisplayMetrics().heightPixels;
        float maxWidth = mContext.getResources().getDimension(R.dimen.kycDocWidth);//mContext.getResources().getDisplayMetrics().widthPixels;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
           bmp = BitmapFactory.decodeFile(filePath, options);
            /*try {
                in = mContext.getContentResolver().openInputStream(
                        photoUri);
                bmp=BitmapFactory.decodeStream(in,null,options);
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
      if(fromCamera){
          ExifInterface exif;
          try {
              exif = new ExifInterface(filePath);
/**
 * 1 = Horizontal (normal)
 2 = Mirror horizontal
 3 = Rotate 180
 4 = Mirror vertical
 5 = Mirror horizontal and rotate 270 CW
 6 = Rotate 90 CW
 7 = Mirror horizontal and rotate 90 CW
 8 = Rotate 270 CW
 */
              int orientation = exif.getAttributeInt(
                      ExifInterface.TAG_ORIENTATION, 0);

              Matrix matrix = new Matrix();
              if (orientation == 6) {
                  matrix.postRotate(90);

              } else if (orientation == 3) {
                  matrix.postRotate(180);

              } else if (orientation == 8) {
                  matrix.postRotate(270);

              }

              exif.saveAttributes();

              scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                      scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                      true);

          } catch (IOException e) {
              e.printStackTrace();
          }

      }



        FileOutputStream out = null;
        try {
           // filename = createImageFile(ConstantCode.IMAGE_NAME).getAbsolutePath();

            if(fromCamera){
                out = new FileOutputStream(fromUri,false);
            }
            else {
                out = new FileOutputStream(toUri,false);
            }

            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);



        }  catch (IOException e) {
            e.printStackTrace();
        }

        return scaledBitmap;

    }

    public static void deleteImageFromGallery(Activity mContext, long mImageTakenTime){
        String[] projection = {
                BaseColumns._ID, Images.ImageColumns.DATE_ADDED};
        String imageOrderBy = Images.Media._ID + " DESC";
        String selection = Images.Media.DATE_TAKEN+" > "+mImageTakenTime;
        //// intialize the Uri and the Cursor, and the current expected size.
        Cursor cursor = null;
        Uri uri = Images.Media.EXTERNAL_CONTENT_URI;
        cursor = mContext.getContentResolver().query(uri, projection, selection, null, imageOrderBy);
        if(null != cursor && cursor.moveToFirst()){
            ContentResolver cr = mContext.getContentResolver();
            cr.delete(Images.Media.EXTERNAL_CONTENT_URI,
                    BaseColumns._ID + "=" + cursor.getString(0), null);
            cursor.close();
        }
    }

    public static File createImageFile(Context mContext,String fileName,String dirName) {
       // File storageDir = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),ConstantCode.DIRECTORY_NAME);//new File(Environment.getExternalStorageDirectory(),"AFRASIA");
        File storageDir= getStorageDirectory(mContext,dirName);
        File imageFile=new File(storageDir.getAbsolutePath()+"/"+fileName+ConstantCode.JPGEXTENSION);
      // String mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    public static File getProductImageFile(Context mContext,String fileName,String dirName) {
        // File storageDir = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),ConstantCode.DIRECTORY_NAME);//new File(Environment.getExternalStorageDirectory(),"AFRASIA");
        File storageDir= getStorageDirectory(mContext,dirName);
        File imageFile=new File(storageDir.getAbsolutePath()+"/"+fileName);
        // String mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    public static File getStorageDirectory(Context mContext,String dirName){
        File storageDir=new File(mContext.getFilesDir(),dirName);
        if(!storageDir.exists() && !storageDir.isDirectory()){
            storageDir.mkdir();
        }
        return storageDir;
    }

    public static Bitmap setPic(Context mContext,String mCurrentPhotoPath) {
        // Get the dimensions of the View
        int targetH = (int) mContext.getResources().getDimension(R.dimen.kycDocHeight);//mContext.getResources().getDisplayMetrics().heightPixels;
        int targetW = (int) mContext.getResources().getDimension(R.dimen.kycDocWidth);//mContext.getResources().getDisplayMetrics().widthPixels;;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

     /*   InputStream in = null;
        try {
            in = getContentResolver().openInputStream(
                    Uri.parse(mCurrentPhotoPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bmp=BitmapFactory.decodeStream(in, null, bmOptions);*/


        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;


        Bitmap bitmap=null;

        try {

            bitmap= BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

//
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;



    }



    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        float totalPixels = width * height;
        float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static Bitmap convertColorIntoBlackAndWhiteImage(Bitmap orginalBitmap) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);

        Bitmap blackAndWhiteBitmap = orginalBitmap.copy(
                Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint();
        paint.setColorFilter(colorMatrixFilter);

        Canvas canvas = new Canvas(blackAndWhiteBitmap);
        canvas.drawBitmap(blackAndWhiteBitmap, 0, 0, paint);

        return blackAndWhiteBitmap;
    }

    //Copy bitmap fromone uri to another uri
    public static Bitmap copyBitmap(String fromUri,String toUri){
        Bitmap resultBitmap=null;
        try {

            File toFile=new File(toUri);
            if(toFile==null || !toFile.exists() || toFile.length()==0){
                //copy from fromUri
                resultBitmap=BitmapFactory.decodeFile(fromUri);
                FileOutputStream fileOutputStream=null;
                fileOutputStream=new FileOutputStream(toUri,false);
                        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            }
            else{
                FileInputStream fileInputStream=new FileInputStream(toUri);
                byte[] bitmapdata =readBytes(fileInputStream);
                resultBitmap=BitmapFactory.decodeByteArray(bitmapdata,0,bitmapdata.length);

            }




        }  catch (IOException e) {
            e.printStackTrace();
            resultBitmap=null;
        }
        catch (OutOfMemoryError exception) {
            exception.printStackTrace();
            resultBitmap=null;

        }
        return resultBitmap;
    }

    public static byte[] readBytes(InputStream inputStream) throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

}
