package id.sch.smktelkom_mlg.tugas02.xirpl338.steamapigames;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sakata Yoga on 11/02/2018.
 */

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.Holder> {
    List<Game> gameList;
    Context context;

    public GameAdapter(List<Game> gameList, Context context) {
        this.gameList = gameList;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Game g = gameList.get(position);
        holder.tvTitle.setText(g.getTitle());
        holder.tvDeveloper.setText("Developer : " + g.getDeveloper());
        holder.tvPublisher.setText("Publisher : " + g.getPublisher());
        Picasso.with(context).load(g.getUrlImage()).fit().into(holder.ivHeader);
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPublisher, tvDeveloper;
        ImageView ivHeader;

        public Holder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
            tvPublisher = itemView.findViewById(R.id.publisher);
            tvDeveloper = itemView.findViewById(R.id.developer);
            ivHeader = itemView.findViewById(R.id.header_image);
        }
    }
}
