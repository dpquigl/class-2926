/* $Id: $
   Copyright 2013, G. Blake Meike

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.marakana.android.yamba;

import android.os.Bundle;
import android.widget.TextView;


/**
 *
 * @version $Revision: $
 * @author <a href="mailto:blake.meike@gmail.com">G. Blake Meike</a>
 */
public class TimelineDetailActivity extends BaseActivity {
    public static final String TIMELINE_STATUS = "Timeline.STATUS";

    private String message;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.timeline_detail);

        if (null == state) { state = getIntent().getExtras(); }

        message = (null == state)
                ? "no status yet..."
                : state.getString(TimelineDetailActivity.TIMELINE_STATUS);

        TextView v = (TextView) findViewById(R.id.timeline_detail);
        v.setBackgroundResource(R.drawable.bg);
        v.setText(message);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString(TimelineDetailActivity.TIMELINE_STATUS, message);
    }


}
