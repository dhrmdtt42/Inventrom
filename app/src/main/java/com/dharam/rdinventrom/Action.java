package com.dharam.rdinventrom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dharam on 8/11/2017.
 */
public  class Action extends Fragment{

    private CameraView mImageSurfaceView;
    private Camera camera;

    private FrameLayout cameraPreviewLayout;
    private ImageView capturedImageHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.action, container, false);


      // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cameraPreviewLayout = (FrameLayout)rootView.findViewById(R.id.camera_preview);//connecting the FrameLayout of the XML file
        capturedImageHolder = (ImageView)rootView.findViewById(R.id.captured_image); //connecting the ImageView of the XML file

        camera = checkDeviceCamera();
        mImageSurfaceView = new CameraView(getContext(), camera); //Showing the image feed received on the screen
        cameraPreviewLayout.addView(mImageSurfaceView);

        Button captureButton = (Button)rootView.findViewById(R.id.button);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, pictureCallback);
//                Intent intent = new Intent(getActivity().getApplicationContext(),UserDataa.class);
////                intent.setData(null);
//                startActivity(intent);

            }
        });
        return rootView;
    }


    private Camera checkDeviceCamera() {
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCamera;
    }

    Camera.PictureCallback pictureCallback = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (bitmap == null)
            {
                //`We tost for given a message that could not captured any image
                // or Failed to capture image ...........!!!!
                Toast.makeText(getContext(), "Image is not Captured ....eeee!!!!!", Toast.LENGTH_LONG).show();
                return;
            }
            else
            {
                Toast.makeText(getContext(), "Casting image", Toast.LENGTH_LONG).show();
                saveImage(bitmap);
                capturedImageHolder.setImageBitmap(scaleDownBitmapImage(bitmap, 300, 200));

                Intent intent = new Intent(getContext(),UserDataa.class);
//                intent.setData(null);
                startActivity(intent);
            }
            //Casting the bitmap image captured to the imageview
            // Continue only if the File was successfully created

        }

    };

    // We have used here bitmap for resizing images
    //as per the given height width of the captured image......!!!
    private Bitmap scaleDownBitmapImage(Bitmap bitmap, int newWidth, int newHeight)
    {
        //Now we are resizing in according to the given width and height
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        return resizedBitmap;
    }
    public void saveImage(Bitmap bitmap)
    {
        // The image which is captured by the camera is saved in the given file
        // directory name.
        File myDir = new File( Environment.getExternalStorageDirectory(),"/RD Inventrom");
        // Log.v  will used for show our captured image directory
        Log.v("File path",": "+myDir);
        myDir.mkdirs();

        String time=new SimpleDateFormat("yyyymmdd_HHmmss").format(new Date());
        String fname = time + "-.jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            //here we may have found an error so catch block for that...!!
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            //we have used out.flush for the flushing the captured images which is still in our cache
            //the close it .
            out.flush();
            out.close();
            String[] paths = {file.toString()};
            String[] mimeTypes = {"/image/jpeg"};

            //MediaScannerConnection provides a way for applications to pass a newly created or downloaded media file to the media scanner service.
            // The media scanner service will read metadata from the file and add the file to the media content provider.
            //scanFile() method request to media scanner to scan the capured image file.....!!
            MediaScannerConnection.scanFile(getContext(), paths, mimeTypes, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
