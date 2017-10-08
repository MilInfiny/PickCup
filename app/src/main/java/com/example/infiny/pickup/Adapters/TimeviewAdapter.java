package com.example.infiny.pickup.Adapters;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.infiny.pickup.Activity.OrderActivity;
import com.example.infiny.pickup.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by infiny on 9/8/17.
 */

public class TimeviewAdapter extends ExpandableRecyclerViewAdapter<TimeviewAdapter.TimeViewHolder, TimeviewAdapter.ItemViewHolder> {
   Context context;
    public TimeviewAdapter(List<? extends ExpandableGroup> groups,Context context) {
        super(groups);
        this.context=context;
    }

    @Override
    public TimeViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timelayout, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subtimelayout, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindChildViewHolder(ItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

    }

    @Override
    public void onBindGroupViewHolder(TimeViewHolder holder, int flatPosition, ExpandableGroup group) {

    }

    public class TimeViewHolder extends GroupViewHolder {
        ImageView  arrow;
        TextView textView;
        View view1;

        public TimeViewHolder(View itemView) {
            super(itemView);
            arrow=(ImageView)itemView.findViewById(R.id.list_item_genre_arrow);
            textView=(TextView)itemView.findViewById(R.id.tw_order);
            view1=(View)itemView.findViewById(R.id.view);
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

        public ImageView getArrow(){
            return arrow;
        }
    }

    public class ItemViewHolder extends ChildViewHolder {
        RelativeLayout rl;
        TextView timebutton;
        AnalogClock clk;
        public ItemViewHolder(View itemView) {
            super(itemView);
            rl = (RelativeLayout)itemView.findViewById(R.id.rl);
            timebutton=(TextView)itemView.findViewById(R.id.bt_time);
            rl.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(context,new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String timeSet = "";
                            if (selectedHour > 12) {
                                selectedHour -= 12;
                                timeSet = "PM";
                            } else if (selectedHour == 0) {
                                selectedHour += 12;
                                timeSet = "AM";
                            } else if (selectedHour == 12){
                                timeSet = "PM";
                            }else{
                                timeSet = "AM";
                            }

                            if(selectedMinute<10)
                            {
                                timebutton.setText(selectedHour + ":0" + selectedMinute+" "+timeSet);
                            }
                            else {
                                timebutton.setText(selectedHour + ":" + selectedMinute+" "+timeSet);

                            }
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     OrderActivity.dateString=sdf.format(mcurrentTime.getTime());
                    mTimePicker.show();

                }
            });
        }
        }
    }

