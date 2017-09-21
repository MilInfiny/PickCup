/*
package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.Helpers.Drinks;
import com.example.infiny.pickup.Helpers.Menu;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.ItemData;
import com.example.infiny.pickup.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

*/
/**
 * Created by infiny on 9/5/17.
 *//*


public class MenuAdapter extends ExpandableRecyclerViewAdapter<MenuAdapter.MenuViewHolder, MenuAdapter.ItemViewHolder> {
    private final OnItemClickListener listener;
   Context context;

    public MenuAdapter(List<? extends ExpandableGroup> groups, OnItemClickListener listener, Context context) {
        super(groups);
        this.listener = listener;
        this.context=context;
    }

    @Override
    public MenuViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menulistlayout, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.submenuitem, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

        holder.bind(context,listener);
        holder.small.setText(drinks.getSmall());
        holder.medium.setText(drinks.getMedium());
        holder.large.setText(drinks.getLarge());

    }

    @Override
    public void onBindGroupViewHolder(MenuViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Drinks drinks = ((Menu) group).getItems().get(0);
   holder.setGenreTitle(group);
        holder.price.setText(drinks.getSmallPrice());

    }


    public class MenuViewHolder extends GroupViewHolder {
        View view1;
        TextView textView,price;
        ImageView arrow;

        public MenuViewHolder(View view) {
            super(view);
        textView=(TextView)view.findViewById(R.id.tw_manuname);
        arrow=(ImageView)view.findViewById(R.id.list_item_genre_arrow);
        view1=(View)view.findViewById(R.id.view);
            price=(TextView)itemView.findViewById(R.id.price);

        }
        @Override
        public void expand() {
//            animateExpand();
            view1.setVisibility(View.INVISIBLE);
            getArrow().setRotation(180);
        }

        @Override
        public void collapse() {
//            animateCollapse();
            view1.setVisibility(View.VISIBLE);
            getArrow().setRotation(360);
        }

        private void animateExpand() {

            RotateAnimation rotate =
                    new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.setAnimation(rotate);
        }

        private void animateCollapse() {
            RotateAnimation rotate =
                    new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.setAnimation(rotate);
        }


    public void setGenreTitle(ExpandableGroup genre) {
            if (genre instanceof Menu) {
                textView.setText(genre.getTitle());
            }
        }
        public ImageView getArrow(){
            return arrow;
        }
    }



    class ItemViewHolder extends ChildViewHolder {
        TextView small,medium,large;
        ImageView imageView,imageView1,imageView2;
        public ItemViewHolder(View itemView) {
            super(itemView);
            small=(TextView)itemView.findViewById(R.id.tw_small);
            medium=(TextView)itemView.findViewById(R.id.tw_medium);
            large=(TextView)itemView.findViewById(R.id.tw_large);
            imageView=(ImageView) itemView.findViewById(R.id.iv_smallsize);
            imageView1=(ImageView) itemView.findViewById(R.id.iv_mediumsize);
            imageView2=(ImageView) itemView.findViewById(R.id.iv_largesize);

        }
        public void bind(final Context item, final OnItemClickListener listener) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onimageclickLister();
                }
            });
            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onimageclickLister();
                }
            });
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onimageclickLister();
                }
            });
            small.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onimageclickLister();
                }
            });
            medium.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onimageclickLister();
                }
            });
            large.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onimageclickLister();
                }
            });

        }
    }
}
*/
