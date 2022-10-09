package org.o7planning.agelegecafe.ui.Working_Date;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.Date_converter;
import org.o7planning.agelegecafe.MainActivity;
import org.o7planning.agelegecafe.R;
import org.w3c.dom.Document;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Closing_Time  extends Fragment {
    private static View root=null;
    private static DBManager dbManager;
    private static View.OnClickListener txClick2=null;
    private static TextView b1=null,b2=null,tx1=null,tx2=null;
    private static TextInputLayout txIn=null;
    private static EditText e1=null;
    private AlertDialog alert=null;
    private AlertDialog alert1=null;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static String tabID="",UsName="",Work_Date="",PREFIX="";
    private static String date1[]={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    public Closing_Time()
    {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.closing_date, container, false);
        try {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        } catch (Exception ex) {
            Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        txIn=root.findViewById(R.id.cl_tx2);
        tx1=root.findViewById(R.id.cl_tx1);
        tx2=root.findViewById(R.id.cl_tx3);
        b1=root.findViewById(R.id.cl_b1);
        b2=root.findViewById(R.id.cl_b2);
        e1=root.findViewById(R.id.cl_e1);
        MainActivity.TimeTx=tx2;
        txIn.setEndIconOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try
                {
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {
                                    try
                                    {
                                        String AM_PM="";
                                        if(hourOfDay<12)
                                            AM_PM="AM";
                                        else
                                            AM_PM="PM";
                                        e1.setText(hourOfDay + ":" + minute+" "+AM_PM);
                                    }
                                    catch (Exception ex)
                                    {
                                        Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    String s16 = e1.getText().toString();
                    String s81 = s16;
                    int indx=0;
                    for(int i=0;i<s81.length();i++)
                    {
                        if(s81.charAt(i)=='P'||s81.charAt(i)=='A')
                        {
                            indx=i;
                            break;
                        }
                    }
                    s16=s81.substring(0,(indx-1));
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.mipmap.glance_logo);
                    builder.setMessage("Are You Sure To Save This ?");
                    String finalS1 = s16;
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                            try
                            {
                                Statement st= MainActivity.connection.createStatement();
                                Statement st1=MainActivity.connection.createStatement();
                                String empN="",userN="";
                                String s2=Work_Date+" "+ finalS1 +":00";
                                Toast.makeText(root.getContext(),"Time="+s2,Toast.LENGTH_LONG).show();
                                st.execute("delete from workEnd where date='"+Work_Date+"'");
                                st1.execute("insert into workEnd(date,stopTime,status) values('"+Work_Date+"','"+s2+"','true')");
                                Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();
                            }
                            catch (Exception ex)
                            {
                                Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    alert = builder.create();
                    alert.show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    String s16 = e1.getText().toString();
                    Statement st = MainActivity.connection.createStatement();
                    Statement st2 = MainActivity.connection.createStatement();
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.mipmap.glance_logo);
                    builder.setMessage("Are You Sure To Delete This Date- ?");
                    String finalS1 = s16;
                    String finalS11 = s16;
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                            try
                            {
                                Statement st=MainActivity.connection.createStatement();
                                st.execute("delete from workingDate");
                                Toast.makeText(root.getContext(),"Success",Toast.LENGTH_LONG).show();
                            }
                            catch (Exception ex)
                            {
                                Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    alert = builder.create();
                    alert.show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        try {
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2=MainActivity.connection.createStatement();
            String s16 = "";
            Work_Date="";
            ResultSet r1=st2.executeQuery("select date from workingDate");
            int ckk=0;
            while(r1.next())
            {
                ckk=1;
                s16=r1.getString("date");
            }
            if(ckk==0)
            {
                tx1.setText("No Working Date Found");
                Toast.makeText(root.getContext(),"You Have To Set Working Date First.",Toast.LENGTH_LONG).show();
            }
            else
            {
                LocalDate cur1= LocalDate.parse(s16);
                DayOfWeek yu=cur1.getDayOfWeek();
                tx1.setText(s16+" ("+yu+")");
                Work_Date=s16;
                String tim="";
                Timestamp d1=null;
                ResultSet r2=st1.executeQuery("select stopTime from workEnd where date='"+Work_Date+"'");
                while (r2.next())
                {
                    d1=r2.getTimestamp("stopTime");;
                }
                int yr1=d1.getYear()+1900;
                int mn1=d1.getMonth()+1;
                int dt1=d1.getDate();
                int hr1=d1.getHours();
                int min1=d1.getMinutes();
                int sec1=d1.getSeconds();
                String s1661 = yr1 + "-" + mn1 + "-" + dt1 + " " + hr1 + ":" + min1 + ":" + sec1;
                Date currentT= Calendar.getInstance().getTime();
                int hr=currentT.getHours();
                int min=currentT.getMinutes();
                Date d = new Date();
                int yr = d.getYear() + 1900;
                int mn = d.getMonth() + 1;
                int dt = d.getDate();
                int sec = d.getSeconds();
                String s166 = yr + "-" + mn + "-" + dt + " " + hr + ":" + min + ":" + sec;
                String AM_PM="";
                if(hr1<12)
                    AM_PM="AM";
                else
                    AM_PM="PM";
                e1.setText(hr1 + ":" +min1+" "+AM_PM);
                Instant ins=d1.toInstant();
                ZoneId zone=ZoneId.systemDefault();
                ZonedDateTime zdt=ins.atZone(zone);
                LocalTime lt1=zdt.toLocalTime();
                long nod= ChronoUnit.MINUTES.between(LocalTime.now(),lt1);
                long v1=nod/60;
                long v2=nod%60;
                tx2.setText(v1+":"+v2);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return root;
    }
}
