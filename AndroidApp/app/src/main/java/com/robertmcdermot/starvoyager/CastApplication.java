package com.robertmcdermot.starvoyager;

import android.app.Application;

import com.google.sample.castcompanionlibrary.cast.DataCastManager;
import com.google.sample.castcompanionlibrary.utils.Utils;

public class CastApplication extends Application
{
	private static String sApplicationId;
	private static DataCastManager sCastMgr = null;
	public static final double VOLUME_INCREMENT = 0.05;

	@Override
	public void onCreate()
	{
		super.onCreate();
		sApplicationId = getString(R.string.app_id);
		initializeCastManager();
		Utils.saveFloatToPreference(getApplicationContext(),
				DataCastManager.PREFS_KEY_VOLUME_INCREMENT, (float) VOLUME_INCREMENT);
	}

	private void initializeCastManager()
	{
		sCastMgr = DataCastManager.initialize(getApplicationContext(), sApplicationId, null, null);
		sCastMgr.enableFeatures(
				DataCastManager.FEATURE_NOTIFICATION |
						DataCastManager.FEATURE_LOCKSCREEN |
						DataCastManager.FEATURE_WIFI_RECONNECT |
						DataCastManager.FEATURE_DEBUGGING);
	}

	public static DataCastManager getCastManager()
	{
		if(sCastMgr == null)
		{
			throw new IllegalStateException("Application has not been started");
		}
		return sCastMgr;
	}
}