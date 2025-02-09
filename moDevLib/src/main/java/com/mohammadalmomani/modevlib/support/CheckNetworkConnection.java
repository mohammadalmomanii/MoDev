package com.mohammadalmomani.modevlib.support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class CheckNetworkConnection extends LiveData<Boolean> {
    /**
     * {@code CheckNetworkConnection} is a LiveData class that observes real-time network connectivity status.
     * It automatically updates observers when the internet connection is available or lost.
     *
     * <p>
     * Usage Example:
     * <pre>
     * {@code
     * CheckNetworkConnection networkLiveData = new CheckNetworkConnection(context);
     * networkLiveData.observe(this, isConnected -> {
     *     if (isConnected) {
     *         Toast.makeText(this, "Internet Connected", Toast.LENGTH_SHORT).show();
     *     } else {
     *         Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
     *     }
     * });
     * }
     * </pre>
     * </p>
     * <p>
     * Note: This class should be initialized in an Activity or ViewModel to ensure proper lifecycle handling.
     */

    private final ConnectivityManager connectivityManager;
    private final ConnectivityManager.NetworkCallback networkCallback;

    public CheckNetworkConnection(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                postValue(true); // Internet is available
            }

            @Override
            public void onLost(@NonNull Network network) {
                postValue(false); // Internet is lost
            }
        };
    }

    @Override
    protected void onActive() {
        super.onActive();
        NetworkRequest request = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        connectivityManager.registerNetworkCallback(request, networkCallback);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
}
