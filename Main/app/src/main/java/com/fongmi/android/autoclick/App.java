package com.fongmi.android.autoclick;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiManager;

import androidx.annotation.NonNull;

public class App extends Application {

	private static App instance;

	public static App get() {
		return App.instance;
	}

	public App() {
		instance = this;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (Prefers.isKeepWiFi()) registerNetwork();
	}

	private void registerNetwork() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkRequest.Builder request = new NetworkRequest.Builder();
		request.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
		cm.registerNetworkCallback(request.build(), getCallback());
		if (Utils.isOffline()) resetWifi();
	}

	private ConnectivityManager.NetworkCallback getCallback() {
		return new ConnectivityManager.NetworkCallback() {
			@Override
			public void onLost(@NonNull Network network) {
				resetWifi();
			}
		};
	}

	private void resetWifi() {
		getWifi().setWifiEnabled(false);
		getWifi().setWifiEnabled(true);
	}

	private WifiManager getWifi() {
		return (WifiManager) getSystemService(Context.WIFI_SERVICE);
	}
}
