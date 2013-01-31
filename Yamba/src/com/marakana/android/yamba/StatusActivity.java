package com.marakana.android.yamba;

import android.os.Bundle;
import android.view.MenuItem;


public class StatusActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_status) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
