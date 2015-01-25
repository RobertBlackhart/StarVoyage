package com.robertmcdermot.starvoyager;

import com.google.android.gms.cast.CastDevice;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.sample.castcompanionlibrary.cast.callbacks.DataCastConsumerImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class MyDataCastConsumer extends DataCastConsumerImpl
{
	Gson gson = new Gson();
	ArrayList<String> chosenRoles = new ArrayList<>(5);

	@Override
	public void onMessageReceived(CastDevice castDevice, String namespace, String message)
	{
		Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
		Map<String,String> map = gson.fromJson(message,stringStringMap);
		chosenRoles.add(map.get("roleChoice"));
	}
}
