package com.example.infiny.pickup.Helpers;

/**
 * Created by infiny on 10/9/17.
 */
public interface ConnectivityReceiverListener {
    void onNetworkConnectionChanged(boolean isConnected);
    void GPSChanged(boolean isEnabled);
}
