package com.sikeandroid.nationdaily.main;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.sikeandroid.nationdaily.R;

public class NationHistoryActivity extends AppCompatActivity {

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nation_history);
    ExpandingList expandingList = (ExpandingList) findViewById(R.id.expanding_list_main);


    for(int i = 1;i < 9 ;i++)
    {
      if(i%2 == 0)
      {
        final ExpandingItem item = expandingList.createNewItem(R.layout.expanding_none_layout);
        View view = item.findViewById(R.id.title);
        view.setBackground(getDrawable(R.drawable.nation_history_item_none));
      }
      else
      {
        final ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
        item.createSubItems(5);

        View subItemZero = item.getSubItemView(0);
        ((TextView) subItemZero.findViewById(R.id.sub_title)).setText("Cool");
        View subItemOne = item.getSubItemView(1);
        ((TextView) subItemOne.findViewById(R.id.sub_title)).setText("Awesome");

        item.setIndicatorColorRes(R.color.nationhistory);
        item.setIndicatorIconRes(R.drawable.ic_action_float);
        item.setStateChangedListener(new ExpandingItem.OnItemStateChanged() {
          @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
          @Override
          public void itemCollapseStateChanged(boolean expanded) {
            View view = item.findViewById(R.id.title);
            if(expanded)
              view.setBackground(getDrawable(R.drawable.nation_history_year_button_open));
            else
              view.setBackground(getDrawable(R.drawable.nation_history_year_button_close1));
          }
        });
      }

    }
  }
}
