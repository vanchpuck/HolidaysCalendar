package com.jonnygold.holidays.calendar;

import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class MySettings extends PreferenceActivity implements OnPreferenceClickListener, ColorPickerDialog.OnColorChangedListener {
	
	@Override
	public boolean onPreferenceClick(Preference arg0)

    {

        //new ColorPickerDialog(this, this, DROIDS_COLOR_KEY, arg0.getInt(DROIDS_COLOR_KEY, DROIDS_COLOR_DEFAULT), DROIDS_COLOR_DEFAULT).show();

 

        return true;

    }

 

    public void colorChanged(String key, int color)

    {

       // ((PreferenceScreen)this.findPreference(SETTINGS_KEY)).getEditor().putInt(key, color).commit();

    }

}
