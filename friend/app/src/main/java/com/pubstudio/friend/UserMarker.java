package com.pubstudio.friend;

import android.graphics.drawable.Drawable;
import android.location.Location;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.pubstudio.friend.models.User;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserMarker{

    private User user;
    private MarkerOptions markerOption;

    public UserMarker(User user){
        markerOption = new MarkerOptions();
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(user.getProfilePicture()));
        markerOption.position(user.getUserLoc());
    }
    public MarkerOptions getMarkerOption() {
        return markerOption;
    }
    public void setMarkerOption(MarkerOptions markerOption) {
        this.markerOption = markerOption;
    }
}
