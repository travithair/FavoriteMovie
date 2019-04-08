package com.tugasmobile.searchmovieapplication.notification;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.widget.Toast;

import com.tugasmobile.searchmovieapplication.BuildConfig;
import com.tugasmobile.searchmovieapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationSetting extends SettingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        SwitchPreference switchReminderUpdate;
        SwitchPreference switchReminder;

        NotificationReminder notificationReminder = new NotificationReminder();
        NotificationUpdateReminder notificationUpdateReminder = new NotificationUpdateReminder();

        List<MovieModel> list;
        List<MovieModel> modelList;
        MovieInterface filmInterface;
        Call<MovieResponse> call;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);

            list = new ArrayList<>();
            modelList = new ArrayList<>();

            switchReminder = (SwitchPreference) findPreference("reminder");
            switchReminder.setOnPreferenceChangeListener(this);
            switchReminderUpdate = (SwitchPreference) findPreference("update");
            switchReminderUpdate.setOnPreferenceChangeListener(this);

            Preference myPref = findPreference("bahasa");
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    return true;
                }
            });
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean b = (boolean) newValue;

            if (key.equals("reminder")) {
                if (b) {
                    notificationReminder.setAlarm(getActivity());
                } else {
                    notificationReminder.cancelAlarm(getActivity());
                }
            } else {
                if (b) {
                    setReleaseAlarm();
                } else {
                    notificationUpdateReminder.cancelAlarm(getActivity());
                }
            }
            return true;
        }

        private void setReleaseAlarm() {
            filmInterface = MovieClient.getClient().create(MovieInterface.class);
            call = filmInterface.getUpcomingMovie(BuildConfig.TMDB_API_KEY);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            final String now = dateFormat.format(date);

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    assert response.body() != null;
                    list = response.body().getResults();
                    for (MovieModel movieModel : list) {
                        if (movieModel.getTanggalRilis().equals(now)) {
                            modelList.add(movieModel);
                        }
                    }
                    notificationUpdateReminder.setAlarm(getActivity(), modelList);
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Something when wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
