package com.sikeandroid.nationdaily.cosplay;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.sikeandroid.nationdaily.R;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class ClothAdapter extends RecyclerView.Adapter<ClothAdapter.ViewHolder> {
  private Context mContext;
  private List<Cloth> mClothList;

  static class ViewHolder extends RecyclerView.ViewHolder {
    CardView cardView;
    ImageView nationCloth;
    TextView nationName;

    public ViewHolder(View itemView) {
      super( itemView );
      cardView = (CardView) itemView;
      nationCloth = (ImageView) itemView.findViewById( R.id.nation_cloth );
      nationName = (TextView) itemView.findViewById( R.id.nation_name );
    }
  }

  public ClothAdapter(List<Cloth> cloth) {
    mClothList = cloth;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (mContext == null) {
      mContext = parent.getContext();
    }
    View view = LayoutInflater.from( mContext ).inflate( R.layout.cloth_item, parent, false );
    final ViewHolder holder = new ViewHolder( view );
    holder.cardView.setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View v) {
        //Log.d( "ClothAdapter",
        //    "position:" + holder.getAdapterPosition() + "," + mClothList.size() );

        int position = holder.getAdapterPosition();
        Cloth cloth = mClothList.get( position );
        Intent intent = new Intent( mContext, ARCamera.class );
        intent.putExtra( Cloth.NATION_NAME, cloth.getName() );
        intent.putExtra( Cloth.NATION_CLOTH_ID, cloth.getClothId() );
        CameraPreview.cameraFlag = CameraPreview.FRONT_CAMERA;
        mContext.startActivity( intent );
        //intent.putExtra(  )
      }
    } );
    return holder;
  }

  @Override public void onBindViewHolder(ClothAdapter.ViewHolder holder, int position) {
    Cloth cloth = mClothList.get( position );
    holder.nationName.setText( cloth.getName() );
    Glide.with( mContext ).load( cloth.getClothId() ).into( holder.nationCloth );
  }

  @Override public int getItemCount() {
    return mClothList.size();
  }
}
