package com.example.robertalmar.enblanco;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rober_000 on 26/12/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity context;
    private List<String> pareAjuda;
    private HashMap<String,List<String>> child;


    public ExpandableListAdapter(Activity context, List<String> p, HashMap<String,List<String>> child){
        this.context = context;
        this.pareAjuda = p;
        this.child = child;
    }

    @Override
    public int getGroupCount() {
        return this.pareAjuda.size();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return this.child.get(this.pareAjuda.get(groupPosition)).size();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return this.pareAjuda.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.child.get(this.pareAjuda.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child, null);
        }
        TextView ch = (TextView) convertView.findViewById(R.id.textView2);
        ch.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.padre, null);
        }
        TextView padre = (TextView) convertView.findViewById(R.id.padreee);
        padre.setText(headerTitle);

        return convertView;
    }
}
