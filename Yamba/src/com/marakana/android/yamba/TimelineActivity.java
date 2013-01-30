package com.marakana.android.yamba;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.marakana.android.yamba.svc.YambaContract;
import com.marakana.android.yamba.svc.YambaServiceHelper;


public class TimelineActivity extends ListActivity
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

    static class TimelineBinder implements SimpleCursorAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Cursor cursor, int colIndex) {
            if (view.getId() != R.id.textTime) { return false; }

            String tStr = "ages ago";
            long t = cursor.getLong(colIndex);
            if (0 < t) {
                tStr = DateUtils.getRelativeTimeSpanString(t, System.currentTimeMillis(), 0)
                    .toString();
            }
            ((TextView) view).setText(tStr);
            return true;
        }
    }


    private SimpleCursorAdapter listAdapter;
    private YambaServiceHelper yamba;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "create loader"); }
        return new CursorLoader(
            getApplicationContext(),
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
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        getListView().setBackgroundResource(R.drawable.bg);

        yamba = YambaServiceHelper.getInstance();

        getLoaderManager().initLoader(LOADER_ID, null, this);

        listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.timeline_row,
                null,
                FROM,
                TO,
                0);
        listAdapter.setViewBinder(new TimelineBinder());
        setListAdapter(listAdapter);
    }

    @Override
    protected void onPause() {
        yamba.stopPolling(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        yamba.startPolling(this);
    }
}
