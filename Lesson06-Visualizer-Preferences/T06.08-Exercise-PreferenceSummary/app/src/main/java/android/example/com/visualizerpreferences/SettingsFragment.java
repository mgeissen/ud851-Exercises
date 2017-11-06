package android.example.com.visualizerpreferences;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_visualizer);


        PreferenceScreen screen = getPreferenceScreen();
        SharedPreferences preferences = screen.getSharedPreferences();
        int count = screen.getPreferenceCount();

        for (int i = 0; i < count; i++){
            Preference preference = screen.getPreference(i);
            if(!(preference instanceof CheckBoxPreference)){
                setPreferencesSummary(preference,
                        preferences.getString(
                                getString(R.string.pref_color_key),
                                getString(R.string.pref_color_red_value)));
            }
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if(preference != null && !(preference instanceof CheckBoxPreference)){
            String value = sharedPreferences.getString(preference.getKey(), "");
            setPreferencesSummary(preference, value);
        }
    }

    // TODO (4) Override onSharedPreferenceChanged and, if it is not a checkbox preference,
    // call setPreferenceSummary on the changed preference

    private void setPreferencesSummary(Preference preference, String value){
        if(preference instanceof  ListPreference){
            ListPreference listPreference = (ListPreference) preference;
            int indexOfValue = listPreference.findIndexOfValue(value);
            String text = listPreference.getEntries()[indexOfValue].toString();
            listPreference.setSummary(text);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getPreferenceScreen()
                .getSharedPreferences();
        if(sharedPreferences != null){
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        }
    }


    // TODO (5) Register and unregister the OnSharedPreferenceChange listener (this class) in
    // onCreate and onDestroy respectively.



}