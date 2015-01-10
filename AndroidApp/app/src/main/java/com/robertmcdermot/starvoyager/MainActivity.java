package com.robertmcdermot.starvoyager;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.sample.castcompanionlibrary.cast.BaseCastManager;
import com.google.sample.castcompanionlibrary.cast.DataCastManager;
import com.google.sample.castcompanionlibrary.cast.exceptions.NoConnectionException;
import com.google.sample.castcompanionlibrary.cast.exceptions.TransientNetworkDisconnectionException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ActionBarActivity
{
	DataCastManager castManager;
	Gson gson = new Gson();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		BaseCastManager.checkGooglePlayServices(this);

		castManager = CastApplication.getCastManager();

		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if(toolbar != null)
		{
			setSupportActionBar(toolbar);
		}
	}

	public void sendRole(View v)
	{
		String role = ((TextView)v).getText().toString();
		Map<String,String> map = new HashMap<>();
		map.put("roleChoice",role);
		try
		{
			castManager.sendDataMessage(gson.toJson(map), CastApplication.namespace);
		}
		catch(IOException | TransientNetworkDisconnectionException | NoConnectionException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		castManager = CastApplication.getCastManager();
		castManager.incrementUiCounter();
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		castManager.decrementUiCounter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		castManager.addMediaRouterButton(menu, R.id.castButton);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		return super.onOptionsItemSelected(item);
	}
}
