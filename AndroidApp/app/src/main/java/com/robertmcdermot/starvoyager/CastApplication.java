package com.robertmcdermot.starvoyager;

import android.app.Application;

import com.google.sample.castcompanionlibrary.cast.DataCastManager;

public class CastApplication extends Application
{
	static String namespace = "urn:x-cast:com.robertmcdermot.starvoyager";
	private static String sApplicationId;
	private static DataCastManager castManager = null;

	@Override
	public void onCreate()
	{
		super.onCreate();

		sApplicationId = getString(R.string.app_id);
		initializeCastManager();
	}

	private void initializeCastManager()
	{
		castManager = DataCastManager.initialize(getApplicationContext(), sApplicationId, namespace);
		castManager.enableFeatures(
				DataCastManager.FEATURE_NOTIFICATION |
						DataCastManager.FEATURE_LOCKSCREEN |
						DataCastManager.FEATURE_WIFI_RECONNECT |
						DataCastManager.FEATURE_DEBUGGING);
	}

	public static DataCastManager getCastManager()
	{
		if(castManager == null)
		{
			throw new IllegalStateException("Application has not been started");
		}
		return castManager;
	}
}