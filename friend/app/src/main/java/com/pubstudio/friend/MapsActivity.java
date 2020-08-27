package com.pubstudio.friend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.PopupMenu;
import android.view.ViewGroup.LayoutParams;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pubstudio.friend.models.SliderItem;
import com.pubstudio.friend.models.User;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.skydoves.balloon.ArrowConstraints;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Bitmap myBitmap;
    RelativeLayout mRelativeLayout;
    private PopupWindow mPopupWindow;
    SliderView sliderView;
    private SliderAdapterExample adapter;
    private ShineButton shineButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout1);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        myBitmap = getRoundedBitmap(myBitmap, 15);
        myBitmap = addBorderToRoundedBitmap(myBitmap, 15, 15, Color.rgb(116, 195, 219));
//        myBitmap = addBorderToRoundedBitmap(myBitmap, 15, 3, Color.LTGRAY);
        //        myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);
        User myUser = new User("halil",new LatLng(41.004662, 29.196727),myBitmap);
        UserMarker myUserMaker = new UserMarker(myUser);
        Marker marker = mMap.addMarker(myUserMaker.getMarkerOption());
        marker.setTitle("User");

        User myUser2 = new User("halil",new LatLng(41.007662, 29.198727),myBitmap);
        UserMarker myUserMaker2 = new UserMarker(myUser2);
        Marker marker2 = mMap.addMarker(myUserMaker2.getMarkerOption());
        marker2.setTitle("User2");

        User myUser3 = new User("halil",new LatLng(41.008662, 29.196727),myBitmap);
        UserMarker myUserMaker3 = new UserMarker(myUser3);
        Marker marker3 = mMap.addMarker(myUserMaker3.getMarkerOption());
        marker3.setTitle("User3");
        marker3.showInfoWindow();
        marker3.setSnippet("asdasd");

        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map_styple));
        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(15.0f);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myUser.getUserLoc(),15) );
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


    }
    protected Bitmap getRoundedBitmap(Bitmap srcBitmap, int cornerRadius) {
        Bitmap dstBitmap = Bitmap.createBitmap(
                srcBitmap.getWidth(),
                srcBitmap.getHeight(),
                Bitmap.Config.ARGB_8888
        );

        Canvas canvas = new Canvas(dstBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight());
        RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap, 0, 0, paint);
        srcBitmap.recycle();
        return dstBitmap;
    }
    protected Bitmap addBorderToRoundedBitmap(Bitmap srcBitmap, int cornerRadius, int borderWidth, int borderColor){
        borderWidth = borderWidth*2;
        Bitmap dstBitmap = Bitmap.createBitmap(
                srcBitmap.getWidth() + borderWidth, // Width
                srcBitmap.getHeight() + borderWidth, // Height
                Bitmap.Config.ARGB_8888 // Config
        );
        Canvas canvas = new Canvas(dstBitmap);
        Paint paint = new Paint();
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        paint.setAntiAlias(true);
        Rect rect = new Rect(
                borderWidth/2,
                borderWidth/2,
                dstBitmap.getWidth() - borderWidth/2,
                dstBitmap.getHeight() - borderWidth/2
        );
        RectF rectF = new RectF(rect);
        canvas.drawRoundRect(rectF,cornerRadius,cornerRadius,paint);
        canvas.drawBitmap(srcBitmap, borderWidth / 2, borderWidth / 2, null);
        srcBitmap.recycle();
        return dstBitmap;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        Toast toast = Toast.makeText(getApplicationContext(),
//                marker.getTitle(),
//                Toast.LENGTH_SHORT);
//        toast.show();



        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popup,null);
        mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        mPopupWindow.setHeight(displayMetrics.heightPixels - (displayMetrics.heightPixels / 5) );
        mPopupWindow.setWidth(displayMetrics.widthPixels - (displayMetrics.widthPixels / 5));
        mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);


        final ImageButton message_button = customView.findViewById(R.id.message);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.button_anim);
        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message_button.startAnimation(myAnim);

            }
        });



        ImageButton closeButton = customView.findViewById(R.id.exit);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });


        sliderView = customView.findViewById(R.id.imageSlider);
        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(2);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        renewItems(sliderView);
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
        return false;
    }
    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Slider Item " + i);
            if (i % 2 == 0) {
                sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
            } else {
                sliderItem.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        SliderItem sliderItem = new SliderItem();
//        sliderItem.setDescription("Slider Item Added Manually");
        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        adapter.addItem(sliderItem);
    }
}