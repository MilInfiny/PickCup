package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infiny.pickup.Helpers.Drinks;
import com.example.infiny.pickup.Helpers.Menu;
import com.example.infiny.pickup.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by infiny on 9/8/17.
 */

public class OrderAdapter extends ExpandableRecyclerViewAdapter<OrderAdapter.orderViewHolder, OrderAdapter.ItemViewHolder> {
    Context context;
    public OrderAdapter(Context context,List<? extends ExpandableGroup> groups) {
        super(groups);
        this.context=context;
    }

    @Override
    public OrderAdapter.orderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderlyout, parent, false);
        return new orderViewHolder(view);
    }

    @Override
    public OrderAdapter.ItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suborderlayout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(OrderAdapter.ItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Drinks drinks = ((Menu) group).getItems().get(0);
        ArrayList<String> drinkses=new ArrayList<>();
        drinkses.add(drinks.getSmall());
        Sub_Menu_Adapter subMenuAdapter=new Sub_Menu_Adapter(context,drinkses);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
        holder.recyclerView.setLayoutManager(mLayoutManager);
        holder.recyclerView.setAdapter(subMenuAdapter);
    }

    @Override
    public void onBindGroupViewHolder(OrderAdapter.orderViewHolder holder, int flatPosition, ExpandableGroup group) {


    }

    public class orderViewHolder extends GroupViewHolder {
        ImageView  arrow;
        TextView textView,price;
        View view1;
        public orderViewHolder(View itemView) {
            super(itemView);
            arrow=(ImageView)itemView.findViewById(R.id.list_item_genre_arrow);
            textView=(TextView)itemView.findViewById(R.id.tw_order);
            view1=(View)itemView.findViewById(R.id.view);
            price=(TextView)itemView.findViewById(R.id.price);
        }
        @Override
        public void expand() {
//            animateExpand();
            view1.setVisibility(View.VISIBLE);
            getArrow().setRotation(180);
        }

        @Override
        public void collapse() {
//            animateCollapse();
            view1.setVisibility(View.INVISIBLE);
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

        public ImageView getArrow(){
            return arrow;
        }
    }


    public class ItemViewHolder extends ChildViewHolder {
        RecyclerView recyclerView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.recycleView);

        }
    }
}
