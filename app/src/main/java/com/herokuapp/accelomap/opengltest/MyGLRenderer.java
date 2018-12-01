package com.herokuapp.accelomap.opengltest;

import android.opengl.EGLConfig;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.SurfaceView;

import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
  @Override
  public void onSurfaceCreated(GL10 gl10, javax.microedition.khronos.egl.EGLConfig eglConfig) {
    // Set the background frame color
    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
  }

  @Override
  public void onDrawFrame(GL10 unused) {
    // Redraw background color
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
  }

  @Override
  public void onSurfaceChanged(GL10 unused, int width, int height) {
    GLES20.glViewport(0, 0, width, height);
  }
}