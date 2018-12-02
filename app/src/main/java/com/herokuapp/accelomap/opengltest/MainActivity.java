package com.herokuapp.accelomap.opengltest;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LoaderManager.LoaderCallbacks<Integer> {
  private SensorManager sensorManager;
  private Sensor accel;

  private int LOADER_ID = 1;
  private LoaderManager loaderManager;

  private TextView viewId;

  private static final String URL_ID = "https://accelomap.herokuapp.com/api/getId";
  private static final String URL_SEND = "https://accelomap.herokuapp.com/api/datapoint";

  private int userId;
  private String[] values;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    userId = -1;
    viewId = findViewById(R.id.text_id);

    sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    accel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

    loaderManager = getSupportLoaderManager();
    loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (sensorManager != null)
      sensorManager.registerListener(this, accel, 1000000);
  }

  @Override
  protected void onStop() {
    super.onStop();
    sensorManager.unregisterListener(this);
  }

  long lastTime = -1;
  long secondLastTime = -1;

  double lastV0 = 0;
  double lastV1 = 0;
  double lastV2 = 0;

  double lastX0 = 0;
  double lastX1 = 0;
  double lastX2 = 0;

  @Override
  public void onSensorChanged(SensorEvent event) {
    if(lastTime == -1) {
      lastTime = event.timestamp;
      return;
    }
    double velocity0 = (event.timestamp - lastTime) / 1e9 * event.values[0];
    double velocity1 = (event.timestamp - lastTime) / 1e9 * event.values[1];
    double velocity2 = (event.timestamp - lastTime) / 1e9 * event.values[2];

    velocity0 += lastV0;
    velocity1 += lastV1;
    velocity2 += lastV2;

    double position0 = (event.timestamp - lastTime) / 1e9 * velocity0;
    double position1 = (event.timestamp - lastTime) / 1e9 * velocity1;
    double position2 = (event.timestamp - lastTime) / 1e9 * velocity2;

    position0 += lastX0;
    position1 += lastX1;
    position2 += lastX2;

    lastTime = event.timestamp;

    String[] values = new String[]{ String.valueOf(position0), String.valueOf(position1), String.valueOf(position2) };

    Bundle bundle = new Bundle();
    bundle.putStringArray("values", values);
    loaderManager.restartLoader(LOADER_ID, bundle, this).forceLoad();
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {

  }

  @NonNull
  @Override
  public Loader onCreateLoader(int id, @Nullable Bundle bundle) {
      String urlSend;
      String[] values;

    if(bundle != null && (values = bundle.getStringArray("values")) != null ) {
        urlSend = URL_SEND.concat("?id=" + userId
                + "&position=" + values[0]
                + "," + values[1]
                + "," + values[2]);
        return new DataLoader(this, URL_ID, urlSend, userId);
    }
    return new DataLoader(this, URL_ID, URL_SEND, userId);
  }

  @Override
  public void onLoadFinished(@NonNull Loader<Integer> loader, Integer id) {
    this.userId = id;
    viewId.setText(String.valueOf(id));
  }

  @Override
  public void onLoaderReset(@NonNull Loader<Integer> loader) {

  }
}
