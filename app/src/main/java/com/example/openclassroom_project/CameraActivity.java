package com.example.openclassroom_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static android.widget.ImageView.*;



public class CameraActivity extends AppCompatActivity {

    Camera camera;
    FrameLayout framelayout;
    Button captureButton;
    ShowCamera showCamera;
    ImageView imageView;
    Module module;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        framelayout = (FrameLayout) findViewById(R.id.frame_layout_cam);
        captureButton = (Button) findViewById(R.id.button1);
        captureButton = (Button) findViewById(R.id.button2);
        imageView = (ImageView) findViewById(R.id.imageview_cam);


        //open camera
        camera = Camera.open();
        showCamera = new ShowCamera(this, camera);
        framelayout.addView(showCamera);

        module = null;
        try {
            //Loading the model file.
            module = Module.load( fetchModelFile(CameraActivity.this, "resnet18_traced.pt"));

        } catch (IOException e) {

        }


        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



                Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
                    public void onShutter() {
                        //Log.d(TAG, "onShutter: ");
                        Snackbar mySnackbar2 = Snackbar.make(v, "on shutter ", 5000);
                        mySnackbar2.show();
                        mySnackbar2 = null;

                    }
                };

                Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
                    public void onPictureTaken(final byte[] data, final Camera camera) {
                        camera.startPreview();
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }



                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 224, 224, true);

                        //Input Tensor
                        final Tensor input = TensorImageUtils.bitmapToFloat32Tensor(
                                bMapScaled,
                                TensorImageUtils.TORCHVISION_NORM_MEAN_RGB,
                                TensorImageUtils.TORCHVISION_NORM_STD_RGB
                        );

//                        Snackbar mySnackbar4 = Snackbar.make(v, "yes boss", 5000);
//                        mySnackbar4.show();
                                    //Calling the forward of the model to run our input
                                    final Tensor output = module.forward(IValue.from(input)).toTensor();
//
//
                                    final float[] score_arr = output.getDataAsFloatArray();

                                    // Fetch the index of the value with maximum score
                                    float max_score = -Float.MAX_VALUE;
                                    int ms_ix = -1;
                                    for (int i = 0; i < score_arr.length; i++) {
                                        if (score_arr[i] > max_score) {
                                            max_score = score_arr[i];
                                            ms_ix = i;
                                        }
                                    }

                                    //Fetching the name from the list based on the index
                                    String detected_class = ModelClasses.MODEL_CLASSES[ms_ix];

                                    Snackbar mySnackbar301 = Snackbar.make(v, detected_class, 5000);
                                    mySnackbar301.show();

//                                    //Writing the detected class in to the text view of the layout
//                                    TextView textView = findViewById(R.id.result_text);
//                                    textView.setText(detected_class);


                    }
                };
                camera.takePicture(shutterCallback, null, jpegCallback);

            }
        });




        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Well done!")
                        .setMessage("Your score is 0 nullos")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .create()
                        .show();
            }
        }
    };








    public static String fetchModelFile(Context context, String modelName) throws IOException {
        File file = new File(context.getFilesDir(), modelName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(modelName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }
}







































                                    //imageView.setImageBitmap(bMapScaled);

                                    //pictureTakenCallBack.saveAsBitmap(data);


//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                                camera.enableShutterSound(false);
//                            }

//                            /camera.takePicture(shutterCallback, null, jpegCallback);





                        //framelayout.addView(showCamera);

                        //Camera1Manager.this.onPictureTaken(bytes, camera, callback);

//                    }
//                });
//
//    }

//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
//            if(requestCode == 100){
//                Bitmap captureImage = (Bitmap) data.getExtras().get("data");
//                imageView.setImageBitmap(captureImage);
//            }
//        }

//    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
//        @Override
//        public void onPictureTaken(byte[] data, Camera camera) {
//            File picture_file = getOutputMediaFile();
//
//            if(picture_file == null){
//                return;
//            }
//            else{
//                //FileOutputStream fos = new fileOutputStream(picture_file);
//            }
//        }
//    };
//
//    private File getOutputMediaFile(){
//        String state = Environment.getExternalStorageState();
//        if(!state.equals(Environment.MEDIA_MOUNTED)){
//            return null;
//        }
//        else
//        {
//            File folder_gui = new File(Environment.getExternalStorageDirectory() + File.separator + "GUI");
//
//            if(!folder_gui.exists()){
//                folder_gui.mkdirs();
//        }
//            File outputfile = new File(folder_gui,"temp.jpg");
//            return outputfile;
//        }
//    }
//
//
//    public void captureImage(View v){
//        if(camera!=null){
//            camera.takePicture(null,null,mPictureCallback);
//        }
//    }
