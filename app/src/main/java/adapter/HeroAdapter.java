package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.triste.leagueoflegends.HeroInformation;
import com.example.triste.leagueoflegends.R;

import java.util.List;

import bean.Hero;
import de.hdodenhof.circleimageview.CircleImageView;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.ViewHolder> {

    private Context context;

    private List<Hero> heroList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View heroView;
        CircleImageView circleImageView;
        TextView heroDesignNation;
        TextView heroName;
        TextView heroAttribute;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heroView = itemView;
            circleImageView = (CircleImageView) itemView.findViewById(R.id.Hero_head_sculpture_Image);
            heroDesignNation = (TextView) itemView.findViewById(R.id.Hero_designNation);
            heroName = (TextView) itemView.findViewById(R.id.Hero_Name);
            heroAttribute = (TextView) itemView.findViewById(R.id.Hero_Attribute);
        }
    }

    public HeroAdapter(List<Hero> heroList){
        this.heroList = heroList;
    }
    @NonNull
    @Override
    public HeroAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null){
            context = viewGroup.getContext();
        }
        final View view = LayoutInflater.from(context).inflate(R.layout.hero_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.heroView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Hero hero = heroList.get(position);
                Intent intent = new Intent(context,HeroInformation.class);
                intent.putExtra("hero",hero);
                context.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HeroAdapter.ViewHolder viewHolder, int i) {
        Hero hero = heroList.get(i);
        Glide.with(context).load(hero.getHeroHeadSculptureUrl()).override(180,180).into(viewHolder.circleImageView);
        viewHolder.heroDesignNation.setText(hero.getHeroDesignNation());
        viewHolder.heroName.setText(hero.getHeroName());
        viewHolder.heroAttribute.setText(hero.getHeroAttribute());
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }
}
