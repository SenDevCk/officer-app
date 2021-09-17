package org.nic.lmd.officerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.nic.lmd.utilities.Utiilties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScannedBarcodeActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    String intentData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_barcode);
        initViews();
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);
        btnAction.setVisibility(View.GONE);
       /* btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intentData.length() > 0) {
                    if (intentData.contains("VendorId"))
                        startActivity(new Intent(ScannedBarcodeActivity.this, VerificationFeeCalculationActivity.class).putExtra("vid", Utiilties.extractInt(intentData).trim()).putExtra("from","Scan"));
                    else {
                        startActivity(new Intent(ScannedBarcodeActivity.this, ManufactureFeeCalculationActivity.class).putExtra("mid", (Utiilties.extractInt(intentData)).trim()).putExtra("from","Scan"));
                    }
                    //Toast.makeText(ScannedBarcodeActivity.this, ""+intentData, Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    private void initialiseDetectorsAndSources() {
        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("MissingPermission")
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannedBarcodeActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannedBarcodeActivity.this, new
                                String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(() -> {
                        if (barcodes.valueAt(0).rawValue != null){
                            Log.d("rawValue",barcodes.valueAt(0).rawValue);
                            intentData = barcodes.valueAt(0).rawValue;
                        }
                        else  if (barcodes.valueAt(0).displayValue != null){
                            Log.d("rawValue",barcodes.valueAt(0).displayValue);
                            intentData = barcodes.valueAt(0).displayValue;
                        }
                        txtBarcodeValue.setText(""+intentData);
                        txtBarcodeValue.append("\nScanned ID: "+Utiilties.extractInt(intentData.trim()));
                        Intent intent=null;
                        if (intentData.contains("VendorId") && intentData.trim().length()>=10){
                            intent=new Intent(ScannedBarcodeActivity.this, VerificationFeeCalculationActivity.class);
                            intent.putExtra("vid", Utiilties.extractInt(intentData.trim()));
                            intent.putExtra("from","scan");
                          }
                        else if (intentData.contains("ManufactureId") && intentData.trim().length()>=10){
                            intent=new Intent(ScannedBarcodeActivity.this, ManufactureFeeCalculationActivity.class);
                            intent.putExtra("man_id", Utiilties.extractInt(intentData.trim()));
                            intent.putExtra("from","scan");
                        }
                        startActivity(intent);
                        btnAction.setVisibility(View.GONE);
                        //finish();
                    });
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

}
