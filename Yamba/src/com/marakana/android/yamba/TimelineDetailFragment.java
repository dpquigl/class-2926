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

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 *
 * @version $Revision: $
 * @author <a href="mailto:blake.meike@gmail.com">G. Blake Meike</a>
 */
public class TimelineDetailFragment extends Fragment {

    public static TimelineDetailFragment newInstance(Bundle args) {
         TimelineDetailFragment frag = new TimelineDetailFragment();
         if (null != args) { frag.setArguments(args); }
         return frag;
    }


    private TextView details;
    private String status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        super.onCreateView(inflater, container, state);

        details = (TextView) inflater.inflate(R.layout.timeline_detail, container, false);

        if (null == state) { state = getActivity().getIntent().getExtras(); }
        if (null == state) { state = getArguments(); }

        setStatus(state);

        return details;
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString(TimelineDetailActivity.TIMELINE_STATUS, status);
    }

    public void setStatus(Bundle state) {
        status = (null == state)
            ? "no status yet..."
            : state.getString(TimelineDetailActivity.TIMELINE_STATUS);
        if (null != details) { details.setText(status); }
    }
}
