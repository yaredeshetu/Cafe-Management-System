package org.o7planning.agelegecafe.ui.Working_Date;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Set_Working_Date extends Fragment {
    private static View root=null;
    private static DBManager dbManager;
    private static View.OnClickListener txClick2=null;
    private static TextView b1=null,b2=null,tx1=null,tx2=null;
    private static TextInputLayout txIn=null;
    private static EditText e1=null;
    private AlertDialog alert=null;
    private AlertDialog alert1=null;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static String date1[]={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    public Set_Working_Date()
    {

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.set_working_date, container, false);
        try {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        } catch (Exception ex) {
            Toast.makeText(root.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        txIn=root.findViewById(R.id.work_tx1);
        tx1=root.findViewById(R.id.work_t2);
        tx2=root.findViewById(R.id.work_t3);
        b1=root.findViewById(R.id.work_b1);
        b2=root.findViewById(R.id.work_b2);
        e1=root.findViewById(R.id.work_e1);
        txIn.setEndIconOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try
                {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    try
                                    {
                                        int mon=monthOfYear+1;
                                        e1.setText(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
                                        String s16=year + "-" +mon + "-" +dayOfMonth;
                                        SimpleDateFormat inform=new SimpleDateFormat("dd-MM-yyyy");
                                        Date myDat=inform.parse(dayOfMonth+"-"+mon+"-"+year);
                                        SimpleDateFormat simpleDateFo=new SimpleDateFormat("EEEE");
                                        String dayName=simpleDateFo.format(myDat);
                                        //Toast.makeText(root.getContext(),yr+"-"+mn+"-"+dt,Toast.LENGTH_LONG).show();
                                        tx1.setText(dayName);
                                        String t2Date=year+"/"+ (monthOfYear + 1) +"/"+dayOfMonth;
                                        ArrayList ConEdate= Date_converter.ToEth(t2Date);

                                        String EEtyear=ConEdate.get(0).toString();
                                        int yr2=Integer.parseInt(EEtyear);
                                        String EEtmonth=ConEdate.get(1).toString();
                                        int mn2=Integer.parseInt(EEtmonth);
                                        String EEtdate=ConEdate.get(2).toString();
                                        int dy2=Integer.parseInt(EEtdate);

                                        String mn21="";
                                        if(mn2<10)
                                            mn21="0"+mn2;
                                        else
                                            mn21=mn21+mn2;
                                        String sc21="";
                                        if(dy2<10)
                                            sc21="0"+dy2;
                                        else
                                            sc21=sc21+dy2;
                                        tx2.setText(yr2+"/"+mn21+"/"+sc21);
                                    }
                                    catch (Exception ex)
                                    {
                                        Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.mipmap.glance_logo);
                    builder.setMessage("Are You Sure To Save This Date-"+s16+"?");
                    String finalS1 = s16;
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                            try
                            {
                                Statement st=MainActivity.connection.createStatement();
                                Statement st1=MainActivity.connection.createStatement();
                                String empN="",userN="";
                                String s1=e1.getText().toString();
                                String s2=tx1.getText().toString();
                                String s3=tx2.getText().toString();
                                st.execute("delete from workingDate");
                                st1.execute("insert into workingDate(date,dateName,localDate) values('"+s1+"','"+s2+"','"+s3+"')");
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
        Date d = new Date();
        int yr = d.getYear() + 1900;
        int mn = d.getMonth() + 1;
        int dt = d.getDate();
        int ds=d.getDay();
        String s16 = yr + "-" + mn + "-" + dt;
        e1.setText(s16);
        tx1.setText(date1[ds]);
        String t2Date=yr+"/"+mn+"/"+dt;
        ArrayList ConEdate= Date_converter.ToEth(t2Date);

        String EEtyear=ConEdate.get(0).toString();
        int yr2=Integer.parseInt(EEtyear);
        String EEtmonth=ConEdate.get(1).toString();
        int mn2=Integer.parseInt(EEtmonth);
        String EEtdate=ConEdate.get(2).toString();
        int dy2=Integer.parseInt(EEtdate);

        String mn21="";
        if(mn2<10)
            mn21="0"+mn2;
        else
            mn21=mn21+mn2;
        String sc21="";
        if(dy2<10)
            sc21="0"+dy2;
        else
            sc21=sc21+dy2;
        tx2.setText(yr2+"/"+mn21+"/"+sc21);
        return root;
    }
}
