package fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import upv.dadm.quotesapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_settings, rootKey);
    }
}
