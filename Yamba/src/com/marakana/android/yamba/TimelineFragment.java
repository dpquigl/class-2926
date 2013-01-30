package com.marakana.android.yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.marakana.android.yamba.svc.YambaContract;


public class TimelineFragment extends ListFragment
    implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static final String TAG = "TIME";
    private static final int LOADER_ID = 37;

    private static final String[] PROJ = new String[] {
        YambaContract.Timeline.Columns.ID,
        YambaContract.Timeline.Columns.USER,
        YambaContract.Timeline.Columns.TIMESTAMP,
        YambaContract.Timeline.Columns.STATUS
    };

    private static final String[] FROM = new String[PROJ.length - 1];
    static { System.arraycopy(PROJ, 1, FROM, 0, FROM.length); };

    private static final int[] TO = new int[] {
        R.id.textUser,
        R.id.textTime,
        R.id.textStatus
    };

    class DateBinder implements SimpleCursorAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Cursor cursor, int idx) {
            if (R.id.textTime != view.getId()) { return false; }

            long utime = cursor.getLong(idx);
            String prettyDate = "unknown";
            if (0 < utime) {
                prettyDate = DateUtils
                        .getRelativeTimeSpanString(utime, System.currentTimeMillis(), 0)
                        .toString();
            }

            ((TextView) view).setText(prettyDate);
            return true;
        }
    }

    private SimpleCursorAdapter listAdapter;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "create loader"); }
        return new CursorLoader(
            getActivity().getApplicationContext(),
            YambaContract.Timeline.URI,
            PROJ,
            null,
            null,
            YambaContract.Timeline.Columns.TIMESTAMP + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "loader finished"); }
       listAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "loader reset"); }
        listAdapter.swapCursor(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View root = super.onCreateView(inflater, container, state);

        root.setBackgroundResource(R.drawable.bg);

        getLoaderManager().initLoader(LOADER_ID, null, this);

        listAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.timeline_row,
                null,
                FROM,
                TO,
                0);
        listAdapter.setViewBinder(new DateBinder());
        setListAdapter(listAdapter);

        return root;
    }
}
