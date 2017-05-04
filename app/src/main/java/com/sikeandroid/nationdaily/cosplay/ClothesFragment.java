package com.sikeandroid.nationdaily.cosplay;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sikeandroid.nationdaily.R;

import static com.sikeandroid.nationdaily.cosplay.ARCosplay.clothList;


public class ClothesFragment extends Fragment {
  public View view;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    view = inflater.inflate( R.layout.activity_ar_cosplay_ce, container, false );
    RecyclerView recyclerView = (RecyclerView) view.findViewById( R.id.clothes );
    recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
    ClothAdapterCe adapter = new ClothAdapterCe( clothList );
    recyclerView.setAdapter( adapter );
    return view;
  }
}
