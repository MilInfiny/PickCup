package com.example.infiny.pickup.Adapters;

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

import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by infiny on 9/8/17.
 */

public class OrderAdapter extends ExpandableRecyclerViewAdapter<OrderAdapter.orderViewHolder, OrderAdapter.ItemViewHolder> {
    public OrderAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
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
            final Drinks drinks = ((Menu) group).getItems().get(childIndex);

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
        public ItemViewHolder(View itemView) {
            super(itemView);

        }
    }
}
