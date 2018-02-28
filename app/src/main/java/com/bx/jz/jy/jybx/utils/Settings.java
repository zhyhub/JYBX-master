package com.bx.jz.jy.jybx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.bx.jz.jy.jybx.R;

public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String PREF_NAME = "setting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(PREF_NAME);
        addPreferencesFromResource(R.xml.settings);

        // Load the preferences from an XML resource
        SharedPreferences sp = getPrefs(this);
        findPreference("packet_interval").setSummary(sp.getString("packet_interval", getString(R.string.default_packet_interval))+"ms");
    }

    protected void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    protected void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            EditTextPreference etp = (EditTextPreference) pref;
            pref.setSummary(etp.getText()+"ms");
        }
    }

    public static SharedPreferences getPrefs(Context context) {
        PreferenceManager.setDefaultValues(context, PREF_NAME, MODE_PRIVATE,
                R.xml.settings, false);
        return context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
    }
}
