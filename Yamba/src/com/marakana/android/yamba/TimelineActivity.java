package com.marakana.android.yamba;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


public class TimelineActivity extends BaseActivity {
    private static final String DETAIL_FRAGMENT = "Timeline.DETAIL";

    private boolean usingFragments;

    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int req) {
        if (!usingFragments) { startActivity(intent); }
        else { launchDetailFragment(intent.getExtras()); }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ((id == R.id.menu_timeline) || (id == android.R.id.home)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        usingFragments = null != findViewById(R.id.timeline_details);

        if (usingFragments) { addDetailFragment(); }
    }

    private void addDetailFragment() {
        FragmentManager mgr = getFragmentManager();

        if (null != mgr.findFragmentByTag(DETAIL_FRAGMENT)) { return; }

        FragmentTransaction xact = mgr.beginTransaction();
        xact.add(
                R.id.timeline_details,
                TimelineDetailFragment.newInstance(null),
                DETAIL_FRAGMENT);
        xact.commit();
    }

    private void launchDetailFragment(Bundle args) {
        FragmentManager mgr = getFragmentManager();
        FragmentTransaction xact = mgr.beginTransaction();
        xact.replace(
                R.id.timeline_details,
                TimelineDetailFragment.newInstance(args),
                DETAIL_FRAGMENT);
        xact.addToBackStack(null);
        xact.commit();
    }
}
