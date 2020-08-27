package com.pubstudio.friend.models;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.pubstudio.friend.R;

public class User{
    private String name;
    private LatLng userLoc;
    private Bitmap profilePicture;

    public User(String name,LatLng userLoc,Bitmap profilePicture){
        this.name = name;
        this.userLoc = userLoc;
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getUserLoc() {
        return this.userLoc;
    }

    public void setUserLoc(LatLng userLoc) {
        this.userLoc = userLoc;
    }

    public Bitmap getProfilePicture() {
//        return profilePicture;
        return this.profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }
}
