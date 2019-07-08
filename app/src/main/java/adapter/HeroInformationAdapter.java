package adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.triste.leagueoflegends.HeroInformation;
import com.example.triste.leagueoflegends.R;

import java.util.List;

import bean.Hero;

public class HeroInformationAdapter extends RecyclerView.Adapter<HeroInformationAdapter.ViewHolder> {

    private Context context;

    private List<String> skillImageUrls;

    private List<String> skillNames;

    private List<String> skillInfos;

    private static String[] shortcut_keys = {"(P)","(Q)","(W)","(E)","(R)"};

    static class ViewHolder extends RecyclerView.ViewHolder{

        View heroInfoView;
        ImageView imageView;
        TextView skillName;
        TextView skillInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            heroInfoView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.hero_skill_image_view);
            skillName = (TextView) itemView.findViewById(R.id.skill_name);
            skillInfo = (TextView) itemView.findViewById(R.id.skill_info);
        }
    }

    public HeroInformationAdapter(List<String> skillImageUrls,List<String> skillNames,List<String> skillInfos){
        this.skillImageUrls = skillImageUrls;
        this.skillNames = skillNames;
        this.skillInfos = skillInfos;
    }
    @NonNull
    @Override
    public HeroInformationAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        if(context == null){
            context = viewGroup.getContext();
        }

        final View view = LayoutInflater.from(context).inflate(R.layout.hero_skill_item,viewGroup,false);
        final HeroInformationAdapter.ViewHolder holder = new HeroInformationAdapter.ViewHolder(view);
        holder.heroInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                String skillName = skillNames.get(position) + shortcut_keys[position];
                String skillInfo = skillInfos.get(position);
                changeText(position,context,viewGroup,skillNames,skillInfos);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HeroInformationAdapter.ViewHolder viewHolder, int i) {
        String skillImageUrl = skillImageUrls.get(i);

        Glide.with(context).load(skillImageUrl).override(70,70).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return skillImageUrls.size();
    }

    public static void changeText(int position,Context context,ViewGroup viewGroup,List<String> skillNames,List<String> skillInfos){
        View view = LayoutInflater.from(context).inflate(R.layout.activity_hero_information,viewGroup,false);
        final HeroInformationAdapter.ViewHolder holder = new HeroInformationAdapter.ViewHolder(view);
        holder.skillName.setText(skillNames.get(position) + shortcut_keys[position]);
        holder.skillInfo.setText(skillInfos.get(position));
    }
}
