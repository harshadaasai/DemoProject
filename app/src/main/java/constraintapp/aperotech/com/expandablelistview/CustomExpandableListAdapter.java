package constraintapp.aperotech.com.expandablelistview;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    ArrayList<Model> groupData;
    ArrayList<ArrayList<Model>> childData;

    public CustomExpandableListAdapter(Context context, ArrayList<Model> groupData, ArrayList<ArrayList<Model>> childData) {
        this.context = context;
        this.groupData = groupData;
        this.childData = childData;
    }

    @Override
    public Model getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,View convertView, ViewGroup parent) {

        Model model = getChild(groupPosition, childPosition);
        ViewHolder holder= null;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);

            holder=new ViewHolder();
            holder.txt_question=(TextView)convertView.findViewById(R.id.txt_question);
            holder.txt_id=(TextView)convertView.findViewById(R.id.txt_id);
            holder.txt_user_id=(TextView)convertView.findViewById(R.id.txt_user_id);
            holder.txt_messge=(TextView)convertView.findViewById(R.id.txt_message);
            holder.image = (ImageView)convertView.findViewById(R.id.image);
            holder.lin_comments = (LinearLayout)convertView.findViewById(R.id.lin_comments);
            convertView.setTag(holder);

        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }


        holder.txt_question.setText("Answer");
        holder.txt_id.setText("id: " +String.valueOf(model.getId()));
        holder.txt_user_id.setText("user_id: " +String.valueOf(model.getUser_id()));
        holder.txt_messge.setText("message: " +model.getMessage());
        Glide.with(context).
                load(Uri.parse(model.getImage())).
                centerCrop().
                into(holder.image);

        if(holder.lin_comments.getChildCount() == 0)
        {
            for(int i = 0; i < model.getComments().size(); i++)
            {
                Comments comments = model.getComments().get(i);
                TextView txt = new TextView(context);
                txt.setTextColor(Color.parseColor("#ff669900"));
                int count = i + 1;
                txt.setText("Comments "+count+": "+comments.getMessage());
                holder.lin_comments.addView(txt);
            }
        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupPosition).size();
    }

    @Override
    public Model getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Model model = (Model) getGroup(groupPosition);
        ViewHolder holder= null;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);

            holder=new ViewHolder();
            holder.txt_question=(TextView)convertView.findViewById(R.id.txt_question);
            holder.txt_id=(TextView)convertView.findViewById(R.id.txt_id);
            holder.txt_user_id=(TextView)convertView.findViewById(R.id.txt_user_id);
            holder.txt_messge=(TextView)convertView.findViewById(R.id.txt_message);
            holder.image = (ImageView)convertView.findViewById(R.id.image);
            holder.lin_comments = (LinearLayout)convertView.findViewById(R.id.lin_comments);
            convertView.setTag(holder);

        } else {
            holder=(ViewHolder)convertView.getTag();
        }

        int quesno = groupPosition + 1;
        holder.txt_question.setText("Question "+quesno);
        holder.txt_id.setText("id: " +String.valueOf(model.getId()));
        holder.txt_user_id.setText("user_id: " +String.valueOf(model.getUser_id()));
        holder.txt_messge.setText("message: " +model.getMessage());
        Glide.with(context).
                load(Uri.parse(model.getImage())).
                centerCrop().
                into(holder.image);

        if(holder.lin_comments.getChildCount() == 0)
        {
            for(int i = 0; i < model.getComments().size(); i++)
            {
                Comments comments = model.getComments().get(i);
                TextView txt = new TextView(context);
                txt.setTextColor(Color.parseColor("#000000"));
                int count = i + 1;
                txt.setText("Comments "+count+": "+comments.getMessage());
                holder.lin_comments.addView(txt);
            }
        }


        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    static class ViewHolder{
        TextView txt_question, txt_id, txt_user_id, txt_messge;
        ImageView image;
        LinearLayout lin_comments;
    }

}