package apprentit.myapplication;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class RecyclerView_Adapter extends
        RecyclerView.Adapter<DemoViewHolder> {

        private ArrayList<Model> list;

        public RecyclerView_Adapter(ArrayList<Model> Data) {
            list = Data;
        }

        @Override
        public DemoViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType) {
            // create a new view
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_row,viewGroup,false);
            DemoViewHolder holder = new DemoViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final DemoViewHolder holder, int position) {

            holder.titleTextView.setText(list.get(position).getCardName());
            holder.coverImageView.setImageResource(list.get(position).getImageResourceId());
            holder.coverImageView.setTag(list.get(position).getImageResourceId());
            holder.likeImageView.setTag(R.drawable.image);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
