package com.sikeandroid.nationdaily.culture;

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
 * Created by KingChaos on 2017/5/6.
 */

public class CharTopicAdapter extends RecyclerView.Adapter<CharTopicAdapter.ViewHolder>{
    private Context mContext;
    private List<CharCulture> mCharCulList;

    public CharTopicAdapter(List<CharCulture> mCharCulList){
        this.mCharCulList=mCharCulList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();

        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.chartopic_item,parent,false);
        final  ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                CharCulture charCulture=mCharCulList.get(position);
                Intent intent=new Intent(mContext, CharCulActivity.class);
                intent.putExtra(CharCulActivity.TOPIC_ID,charCulture.getId());
                intent.putExtra(CharCulActivity.TOPIC_NAME,charCulture.getTopicName());
                intent.putExtra(CharCulActivity.TOPIC_IMAGE_ID,charCulture.getTopicImageId());
                mContext.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CharCulture charCulture=mCharCulList.get(position);
        holder.topicName.setText(charCulture.getTopicName());
        Glide.with(mContext).load(charCulture.getTopicImageId()).into(holder.topicImage);

    }

    @Override
    public int getItemCount() {
        return mCharCulList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView topicImage;
        TextView topicName;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView= (CardView) itemView;
            topicName= (TextView) itemView.findViewById(R.id.topic_name);
            topicImage= (ImageView) itemView.findViewById(R.id.topic_image);
        }
    }
}
