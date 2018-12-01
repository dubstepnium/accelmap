package com.herokuapp.accelomap.opengltest;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity {
  private GLSurfaceView mGLView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Create a GLSurfaceView instance and set it
    // as the ContentView for this Activity.
    mGLView = findViewById(R.id.surfaceView);
    mGLView.setRenderer(new MyGLRenderer());

  }
}
