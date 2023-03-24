package com.example.smartxportadmin;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableTextviewAdapter extends BaseExpandableListAdapter {

    Context context;


    String[]faqs={"1.How to Login ?",
            "2.How to change the Password?",
            "3.How to change the App Language?",
            "4.How to Allow all Permissions?",
            "5.Which permissions should be given while using the app? ",
            "6.How to scan the face using the phone camera?",
            "7.Why my Face is not showing in the camera recording section?",
            "8.How to Use Drowsy Alert Alarm?",
            "9.Use of Drowsy Alert Alarm?",

    };

    String[][] answer={{
            "Firstly you have to register yourself As a driver on the admin app and" +
                    " then use that driver login Credentials Here for accessing the app "},
            {"You can't change your password from Here You have to contact" +
                    " to your Owner(Admin) which have the admin app"},
            {"There is a Button Situated on the Login panel/Window You have " +
                    "to click on that button then a pop up comes select your preferred Location and" +
                    " please Restart the application"},
            {"At the time of first use of application please allow all permission which asked by the application." +
                    "If there any issue please check app permissions in your phone settings"},
            {"There are several permission needed like \n Location \n SMS \n Camera \n Internet"},
            {"There is no need to do anything by your side " +
                    "You just need to settle your mobile front in front of you while driving " +
                    "We developed a system which can automatically detect and track your face"},
            {"Don't Worry About That Because, In The Background the Camera is running. " +
                    "In order to reduce the power consumption by the app we kept the display of face off "},
            {"Just click on that ON button to START the Alarm and OFF button to STOP the Alarm"},
            {"We Develope Drowsy Alert alarm to kept driver alert While driving " +
                    "If driver is alone while driving and feels drowsy then just start the alarm" +
                    "Drowsy alert alarm will alert the driver Repeatedly after Some interval of time " +
                    "The time between two alert given by alarm is 5 minutes that already inbuilt in the application"}
    };

    public ExpandableTextviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return faqs.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return answer[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return faqs[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return answer[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String questionFaqs = (String)getGroup(groupPosition);
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(R.layout.faqs_title,null);

        }
        TextView questionFaq2=convertView.findViewById(R.id.faqstitleView);
        questionFaq2.setTypeface(null, Typeface.BOLD);
        questionFaq2.setText(questionFaqs);
        return convertView;


    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String answerFaqs = (String)getChild(groupPosition,childPosition);
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(R.layout.faq_answer,null);

        }
        TextView answerFaq2=convertView.findViewById(R.id.descriptionFaqView);
        answerFaq2.setText(answerFaqs);
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
