package com.example.forexamplejohn.rmhumphreslab3;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by for example john on 1/24/2016.
 */
public class MyListAdapter extends BaseExpandableListAdapter{

    private Context _context;
    List<String> manufacturers;
     HashMap<String,List<String>> models;


    public MyListAdapter(Context act, List<String> makes, HashMap<String, List<String>> childData){
        this._context = act;
        this.manufacturers = (makes);
        this.models = (childData);
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        return this.models.get(this.manufacturers.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.manufacturers.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.manufacturers.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater thelay = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = thelay.inflate(R.layout.listings, null);
        }

        TextView theHead = (TextView) convertView
                .findViewById(R.id.listHead);
        theHead.setTypeface(null, Typeface.BOLD);
        theHead.setText(headerTitle);

        return convertView;
    }

   /* public void remove (int groupPosition,int childPosition){
        String s = this.models.get(manufacturers.get(groupPosition)).get(childPosition);
        this.models.remove(manufacturers.get(groupPosition)).get(childPosition);

    }*/

    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.models.get(this.manufacturers.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View view, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.childlayout, null);
        }
        final ImageView image = (ImageView) view.findViewById(R.id.deleteButton);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                models.get(manufacturers.get(groupPosition)).remove(childPosition);
                notifyDataSetChanged();
                //Toast.makeText(RMHumphreslab3.this, "made to on click", Toast.LENGTH_SHORT).show();
            }
        });//image

        TextView txtList = (TextView) view
                .findViewById(R.id.listItem);

        txtList.setText(childText);
        return view;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }




}
