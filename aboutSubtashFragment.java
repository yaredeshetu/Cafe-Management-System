package org.o7planning.agelegecafe.ui.aboutSebtash;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.o7planning.agelegecafe.R;

public class aboutSubtashFragment extends Fragment {

    private static LinearLayout lay=null,lay2=null,sc=null;
    private org.o7planning.agelegecafe.ui.aboutSebtash.aboutSubtashViewModel galleryViewModel;
    View root=null;
    private static TextView tx=null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(org.o7planning.agelegecafe.ui.aboutSebtash.aboutSubtashViewModel.class);
        root = inflater.inflate(R.layout.about_sebtash, container, false);

        sc=root.findViewById(R.id.abtsebscroll);
        tx=root.findViewById(R.id.sebtx);
        sc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View view = v;
                view.bringToFront();
                viewTransformation(view, event);
                return true;
            }
        });
        tx.setText("1.\tAutomated Camera based Utility Meter Reader (አገልግል አንባቢ)\n" +
                "2.\tAutomated Bill Preparing and selling Software (አገልግል ቢል)\n" +
                "3.\tAutomated  Financial Software (አገልግል ሂሳብ)\n" +
                "4.\t Online Customer Service System (አገልግል ደንበኛ)\n" +
                "5.\tAutomated Inventory Controlling and Management system (አገልግል ንብረት)\n" +
                "6.\tHuman Resource Management System (አገልግል ሰዉ ሀብት)\n" +
                "7.\tGPS based Employee activity tracking software (አገልግል ስራ)\n" +
                "8.\tGPS based Car tracking software (አገልግል ተሽከርካሪ)\n" +
                "9.\tSmart Waste Management System (አገልግል ፅዳት)\n" +
                "10.\tSale Management System (አገልግል ሽያጭ)\n" +
                "11.\tAutomated Payment Bank Integration System (አገልግል ክፍያ)\n" +
                "12.\tAutomated Paper Free Secretary Office (አገልግል ወረቀት አልባ ቢሮ)\n" +
                "13.\tIntegrated Dashboard/web-based enterprise management system (አገልግል ተቆጣጣሪ)\n" +
                "14.\tIntegrated Android-based enterprise management App (አገልግል አለቃ)\n");
        return root;
    }

    float[] lastEvent = null;
    float d = 0f;
    float newRot = 0f;
    private boolean isZoomAndRotate;
    private boolean isOutSide;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private static TableLayout table=null;
    private PointF start = new PointF();
    private PointF mid = new PointF();
    float oldDist = 1f;
    private float xCoOrdinate, yCoOrdinate;
    private void viewTransformation(View view, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                xCoOrdinate = view.getX() - event.getRawX();
                yCoOrdinate = view.getY() - event.getRawY();

                start.set(event.getX(), event.getY());
                isOutSide = false;
                mode = DRAG;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    midPoint(mid, event);
                    mode = ZOOM;
                }

                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                //d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
                isZoomAndRotate = false;
                if (mode == DRAG) {
                    float x = event.getX();
                    float y = event.getY();
                }
            case MotionEvent.ACTION_OUTSIDE:
                isOutSide = true;
                mode = NONE;
                lastEvent = null;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isOutSide) {
                    if (mode == DRAG) {
                        isZoomAndRotate = false;
                        //view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                    }
                    if (mode == ZOOM && event.getPointerCount() == 2) {
                        float newDist1 = spacing(event);
                        if (newDist1 > 10f) {
                            float scale = newDist1 / oldDist * view.getScaleX();
                            view.setScaleX(scale);
                            view.setScaleY(scale);
                        }
                        if (lastEvent != null) {
                            //newRot = rotation(event);
                            //view.setRotation((float) (view.getRotation() + (newRot - d)));
                        }
                    }
                }
                break;
        }
    }
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (int) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}