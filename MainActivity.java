package org.o7planning.agelegecafe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.o7planning.agelegecafe.databinding.ActivityMainBinding;
import org.o7planning.agelegecafe.ui.Manage_Table.Daily_Table_Transaction;
import org.o7planning.agelegecafe.ui.home.Collected_Table;
import org.o7planning.agelegecafe.ui.home.HomeFragment;
import org.o7planning.agelegecafe.ui.home.Uncollected_Table;
import org.o7planning.agelegecafe.ui.sales_invoice.Sales_InvoiceFragment;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static AppBarConfiguration mAppBarConfiguration;
    private static ActivityMainBinding binding;
    private static String ip = "192.168.8.104";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Agelegel_Cafe_Hisab_Glance";
    private static String username = "yared";
    private static String password = "ST@321";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
    public static Context context=null;
    public static Connection connection = null;
    private static DBManager dbManager;
    public static DrawerLayout drawer =null;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private static String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    public static NavController navController =null;
    public static AppCompatActivity AppCom=null;
    public static int NEW_OPEN=0;
    public static String Emp_USERNAME="",Emp_NAME="",Emp_PRIVILAGE="",Emp_ID="",Emp_MOBILE="",Connection_type="",Main_WORK_DATE="";
    public static NavigationView navigationView =null;
    public static TextView userTx=null,prevl=null,logout=null;
    private static final int REQU_CODE=101;
    private static View root=null;
    public static String AutoDate="false",AutoAssign="false",DateFrom="";
    private static View.OnClickListener clickListener=null;
    public static ArrayList selectedWaiter=new ArrayList();
    public static ArrayList selectedWaiter1=new ArrayList();
    public static ArrayList selectedWaiter2=new ArrayList();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        root=binding.getRoot();
        setContentView(root);

        setSupportActionBar(binding.appBarMain.toolbar);
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        QrCho=0;
        AutoDate="false";AutoAssign="false";DateFrom="";
        try
        {
            userTx=root.findViewById(R.id.heduser);
            prevl=root.findViewById(R.id.heduser1);
            logout=root.findViewById(R.id.heduser2);
            dbManager = new DBManager(getApplicationContext());
            dbManager.open();
            Cursor cr=dbManager.fetch();
            ip=cr.getString(0);
            port=cr.getString(1);
            username=cr.getString(2);
            password=cr.getString(3);
            Connection_type=cr.getString(6);

            Cursor cr1=dbManager.fetch10();
            if (cr1!=null)
            {
                AutoDate=cr1.getString(0);
                DateFrom=cr1.getString(1);
                AutoAssign=cr1.getString(2);
            }
            url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
            File myDirectory = new File(Environment.getExternalStoragePublicDirectory(""), "Agelegel Cafe");
            if(!myDirectory.exists())
                myDirectory.mkdir();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        String[] perms = {"android.permission.FINE_LOCATION", "android.permission.CAMERA"};

        context=getApplicationContext();
        AppCom=MainActivity.this;
        Main_WORK_DATE="";
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_repo_wai,R.id.nav_man_table,R.id.nav_pay_cash,R.id.nav_pay, R.id.nav_inc,R.id.nav_home,R.id.nav_sto_issu,R.id.nav_sto_grv,R.id.nav_sto_dis,R.id.nav_cha,R.id.nav_per, R.id.nav_gallery, R.id.nav_pur, R.id.nav_sal, R.id.nav_impo, R.id.nav_sales_ord, R.id.nav_sales_repo, R.id.nav_vouch, R.id.nav_setting, R.id.nav_aboutsub, R.id.nav_about, R.id.nav_items, R.id.nav_prof, R.id.nav_user, R.id.nav_order_view)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    if(NEW_OPEN==1)
                    {
                        NEW_OPEN=0;
                        userTx.setText("User:-");
                        prevl.setText("Privilege:-");
                        Emp_USERNAME="";
                        Emp_NAME="";Emp_PRIVILAGE="";Emp_ID="";Emp_MOBILE="";Connection_type="";
                        Main_WORK_DATE="";
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.activity_main_drawer2);
                        getSupportActionBar().setTitle("Home");
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,new HomeFragment()).commit();
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        };
        this.notificationManagerCompat = NotificationManagerCompat.from(this);
        ServerConnection serverConnection=new ServerConnection();
        serverConnection.execute();
        nav(NEW_OPEN,"User:-","");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void nav(int cho, String us, String username)
    {
        try
        {
            if(cho==1)
            {
                if(username.equalsIgnoreCase("Admin")||username.equals("sebtash"))
                {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_main_drawer);
                }
                else
                {
                    if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Waiter"))
                    {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.waiter_menu);
                    }
                    else if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Kitchen Sheff"))
                    {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.kitchen_menu);
                    }
                    else if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Manager"))
                    {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.activity_main_drawer);
                    }
                    else if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Cashier"))
                    {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.activity_main_drawer1);
                    }
                    else if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Store Man"))
                    {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.store_menu);
                    }
                    else
                    {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.activity_main_drawer2);
                    }
                }
                NavigationUI.setupActionBarWithNavController(AppCom, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);
                userTx=root.findViewById(R.id.heduser);
                userTx.setText("User:-"+MainActivity.Emp_NAME);
                prevl=root.findViewById(R.id.heduser1);
                prevl.setText("Privilege:-"+MainActivity.Emp_PRIVILAGE);
                logout=root.findViewById(R.id.heduser2);
                logout.setText("Logout");
                logout.setOnClickListener(clickListener);
            }
            else
            {
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.activity_main_drawer2);
                NavigationUI.setupActionBarWithNavController(AppCom, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public MainActivity()
    {

    }
    private int notificationId=0;
    private NotificationManagerCompat notificationManagerCompat;
    public void sendOnChannel1(String title,String message,int ext)  {
        try
        {
            //Toast.makeText(getApplicationContext(), "nott-1", Toast.LENGTH_LONG).show();
            ++notificationId;
            //String title = "New Food Order notif-"+notificationId;

            Intent notificationIntent = new Intent(this, MainActivity1.class);
            notificationIntent.putExtra("id", ext);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT| PendingIntent.FLAG_IMMUTABLE);
            Notification notification = new NotificationCompat.Builder(this, NotificationApp.CHANNEL_1_ID)
                    .setSmallIcon(R.mipmap.agelegel_hotel3)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setContentIntent(pendingIntent)
                    .build();
            notification.flags|=Notification.FLAG_AUTO_CANCEL;
            this.notificationManagerCompat.notify(notificationId, notification);
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public static Connection Conn()
    {
        Connection conn = null;
        try
        {
            dbManager = new DBManager(context);
            dbManager.open();
            Cursor cr=dbManager.fetch();
            ip=cr.getString(0);
            port=cr.getString(1);
            username=cr.getString(2);
            password=cr.getString(3);
            Connection_type=cr.getString(6);
            if(port.equalsIgnoreCase("no"))
                url = "jdbc:jtds:sqlserver://"+ip+"/"+database;
            else
                url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
        }
        catch (Exception ex)
        {
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        try
        {
            Class.forName(Classes);
            conn = DriverManager.getConnection(url, username,password);
            MainActivity.connection=conn;
            Statement st=conn.createStatement();
        }
        catch (ClassNotFoundException e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        catch (SQLException e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return conn;
    }
    private static ArrayList search3(String str)
    {
        ArrayList Big=new ArrayList();
        try
        {
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            Statement st3 = MainActivity.connection.createStatement();
            Statement st4 = MainActivity.connection.createStatement();
            if(Daily_Table_Transaction.SelectRR==0)
            {
                ResultSet rss=st.executeQuery("select empName,userName from userAccount where duety='Waiter'");
                while(rss.next())
                {
                    MainActivity.selectedWaiter.add(rss.getString("userName"));
                }
                Daily_Table_Transaction.SelectRR=1;
            }
            if(Collected_Table.SelectRR==0)
            {
                ResultSet rss=st.executeQuery("select empName,userName from userAccount where duety='Waiter'");
                while(rss.next())
                {
                    MainActivity.selectedWaiter1.add(rss.getString("userName"));
                }
                Collected_Table.SelectRR=1;
            }
            if(Uncollected_Table.SelectRR==0)
            {
                ResultSet rss=st.executeQuery("select empName,userName from userAccount where duety='Waiter'");
                while(rss.next())
                {
                    MainActivity.selectedWaiter2.add(rss.getString("userName"));
                }
                Uncollected_Table.SelectRR=1;
            }
            Big = new ArrayList();
            ResultSet rs=null;
            if(Uncollected_Table.CHOICE==1)
                rs=st.executeQuery("select * from tableOrder where workDate='"+MainActivity.Main_WORK_DATE+"' and waiter='"+MainActivity.Emp_USERNAME+"' and isClose='false'");
            else if(Uncollected_Table.CHOICE==2)
            {
                String sqlsq="select * from tableOrder where isClose='false'";
                if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Waiter"))
                    sqlsq="select * from tableOrder where isClose='false' and waiter='"+MainActivity.Emp_USERNAME+"'";
                rs=st.executeQuery(sqlsq);
            }
            while (rs.next())
            {
                String ordId=rs.getString("id");
                String tabId=rs.getString("tableId");
                String dateTra=rs.getString("workDate");
                String wait=rs.getString("waiter");
                String empN = "";
                ResultSet cc = st1.executeQuery("select empName from userAccount where userName='" + wait + "'");
                while (cc.next()) {
                    empN = cc.getString("empName");
                }
                int ckc=0;
                for (int i=0;i<selectedWaiter2.size();i++)
                {
                    if(wait.equalsIgnoreCase(selectedWaiter2.get(i).toString()))
                    {
                        ckc=1;
                    }
                }
                if(ckc==1)
                {
                    int noOford=rs.getInt("noOrder");
                    String tableN="";
                    ResultSet rs2=st2.executeQuery("select * from tableList where id='"+tabId+"'");
                    while (rs2.next())
                    {
                        tableN=rs2.getString("tableN");
                    }
                    double bal=0.0;
                    ResultSet rs1=st1.executeQuery("select SUM(balanace) AS bal from tableOrderDetail where orderId='"+ordId+"'");
                    while (rs1.next())
                    {
                        bal=rs1.getDouble("bal");
                    }
                    ArrayList s = new ArrayList();
                    s.add(ordId);
                    s.add(tabId);
                    s.add(bal);
                    s.add(noOford);
                    s.add(tableN);
                    s.add(empN);
                    s.add(dateTra);
                    Big.add(s);
                }
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return Big;
    }
    private static ArrayList search44(String str)
    {
        ArrayList Big=new ArrayList();
        try
        {
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            Big = new ArrayList();
            //Toast.makeText(root.getContext(),"ord=",Toast.LENGTH_LONG).show();
            if(Daily_Table_Transaction.SelectRR==0)
            {
                ResultSet rss=st.executeQuery("select empName,userName from userAccount where duety='Waiter'");
                while(rss.next())
                {
                    MainActivity.selectedWaiter.add(rss.getString("userName"));
                }
                Daily_Table_Transaction.SelectRR=1;
            }
            if(Collected_Table.SelectRR==0)
            {
                ResultSet rss=st.executeQuery("select empName,userName from userAccount where duety='Waiter'");
                while(rss.next())
                {
                    MainActivity.selectedWaiter1.add(rss.getString("userName"));
                }
                Collected_Table.SelectRR=1;
            }
            if(Uncollected_Table.SelectRR==0)
            {
                ResultSet rss=st.executeQuery("select empName,userName from userAccount where duety='Waiter'");
                while(rss.next())
                {
                    MainActivity.selectedWaiter2.add(rss.getString("userName"));
                }
                Uncollected_Table.SelectRR=1;
            }
            String sqlsq="select * from tableOrder";
            if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Waiter"))
                sqlsq="select * from tableOrder where waiter='"+MainActivity.Emp_USERNAME+"'";
            ResultSet rs=st.executeQuery(sqlsq);

            while (rs.next())
            {
                String ordId=rs.getString("id");
                String tabId=rs.getString("tableId");
                String dateTra=rs.getString("workDate");
                String wait=rs.getString("waiter");
                String empN = "";
                ResultSet cc = st1.executeQuery("select empName from userAccount where userName='" + wait + "'");
                while (cc.next()) {
                    empN = cc.getString("empName");
                }
                int ckc=0;
                for (int i=0;i<selectedWaiter.size();i++)
                {
                    if(wait.equalsIgnoreCase(selectedWaiter.get(i).toString()))
                    {
                        ckc=1;
                    }
                }
                if(ckc==1)
                {
                    int noOford = rs.getInt("noOrder");
                    String tableN = "";
                    ResultSet rs2 = st2.executeQuery("select * from tableList where id='" + tabId + "'");
                    while (rs2.next())
                    {
                        tableN = rs2.getString("tableN");
                    }
                    double bal = 0.0;
                    ResultSet rs1 = st1.executeQuery("select SUM(balanace) AS bal from tableOrderDetail where orderId='" + ordId + "'");
                    while (rs1.next())
                    {
                        bal = rs1.getDouble("bal");
                    }
                    ArrayList s = new ArrayList();
                    s.add(ordId);
                    s.add(tabId);
                    s.add(bal);
                    s.add(noOford);
                    s.add(tableN);
                    s.add(empN);
                    s.add(dateTra);
                    Big.add(s);
                }
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return Big;
    }
    private static ArrayList search4(String str)
    {
        ArrayList Big=new ArrayList();
        try
        {
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            if(Daily_Table_Transaction.SelectRR==0)
            {
                ResultSet rss=st.executeQuery("select empName,userName from userAccount where duety='Waiter'");
                while(rss.next())
                {
                    MainActivity.selectedWaiter.add(rss.getString("userName"));
                }
                Daily_Table_Transaction.SelectRR=1;
            }
            if(Collected_Table.SelectRR==0)
            {
                ResultSet rss=st.executeQuery("select empName,userName from userAccount where duety='Waiter'");
                while(rss.next())
                {
                    MainActivity.selectedWaiter1.add(rss.getString("userName"));
                }
                Collected_Table.SelectRR=1;
            }
            if(Uncollected_Table.SelectRR==0)
            {
                ResultSet rss=st.executeQuery("select empName,userName from userAccount where duety='Waiter'");
                while(rss.next())
                {
                    MainActivity.selectedWaiter2.add(rss.getString("userName"));
                }
                Uncollected_Table.SelectRR=1;
            }
            Big = new ArrayList();
            //Toast.makeText(root.getContext(),"ord=",Toast.LENGTH_LONG).show();
            ResultSet rs=null;
            if(Collected_Table.CHOICE==1)
                rs=st.executeQuery("select * from tableOrder where workDate='"+MainActivity.Main_WORK_DATE+"' and waiter='"+MainActivity.Emp_USERNAME+"' and isClose='true'");
            else
            {
                String sqlsq="select * from tableOrder where isClose='true'";
                if(MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Waiter"))
                    sqlsq="select * from tableOrder where isClose='true' and waiter='"+MainActivity.Emp_USERNAME+"'";
                rs=st.executeQuery(sqlsq);
            }
            while (rs.next())
            {
                String ordId=rs.getString("id");
                String tabId=rs.getString("tableId");
                String dateTra=rs.getString("workDate");
                String wait=rs.getString("waiter");
                String empN = "";
                ResultSet cc = st1.executeQuery("select empName from userAccount where userName='" + wait + "'");
                while (cc.next()) {
                    empN = cc.getString("empName");
                }
                int ckc=0;
                for (int i=0;i<selectedWaiter1.size();i++)
                {
                    if(wait.equalsIgnoreCase(selectedWaiter1.get(i).toString()))
                    {
                        ckc=1;
                    }
                }
                if(ckc==1)
                {
                    int noOford=rs.getInt("noOrder");
                    String tableN="";
                    ResultSet rs2=st2.executeQuery("select * from tableList where id='"+tabId+"'");
                    while (rs2.next())
                    {
                        tableN=rs2.getString("tableN");
                    }
                    double bal=0.0;
                    ResultSet rs1=st1.executeQuery("select SUM(balanace) AS bal from tableOrderDetail where orderId='"+ordId+"'");
                    while (rs1.next())
                    {
                        bal=rs1.getDouble("bal");
                    }
                    ArrayList s = new ArrayList();
                    s.add(ordId);
                    s.add(tabId);
                    s.add(bal);
                    s.add(noOford);
                    s.add(tableN);
                    s.add(empN);
                    s.add(dateTra);
                    Big.add(s);
                }
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return Big;
    }
    private static ArrayList search1(String str)
    {
        ArrayList Big=new ArrayList();
        try
        {
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            Big = new ArrayList();
            ResultSet cr=st.executeQuery("select itemId,itemName,balance,price,purPrice from itemList where itemName LIKE '%"+str+"%' order by itemName");
            while(cr.next())
            {
                String itid=cr.getString("itemId");
                String itNm=cr.getString("itemName");
                double bal=0.0,pri=0.0,Ppri=0.0;
                bal=cr.getDouble("balance");
                pri=cr.getDouble("price");
                Ppri=cr.getDouble("purPrice");
                ArrayList s = new ArrayList();
                s.add(itid);
                s.add(itNm);
                s.add(bal);
                s.add(pri);
                s.add(Ppri);
                Big.add(s);
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return Big;
    }
    private static ArrayList search2(String str)
    {
        ArrayList Big=new ArrayList();
        try
        {
            Statement st = MainActivity.connection.createStatement();
            Statement st1 = MainActivity.connection.createStatement();
            Statement st2 = MainActivity.connection.createStatement();
            Big = new ArrayList();
            String sql="select voucherId,voucherType,totalPrice,userId,date from voucher where userId='"+MainActivity.Emp_USERNAME+"' order by timeStamp desc";
            if(MainActivity.Emp_USERNAME.equalsIgnoreCase("Admin")||MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Manager"))
                sql="select voucherId,voucherType,totalPrice,userId,date from voucher order by timeStamp desc";
            ResultSet cr=st.executeQuery(sql);
            while(cr.next())
            {
                String itid=cr.getString("voucherId");
                String itNm=cr.getString("voucherType");
                String wa=cr.getString("userId");
                String empN="";
                ResultSet cc=st1.executeQuery("select empName from userAccount where userName='"+wa+"'");
                while (cc.next()){
                    empN=cc.getString("empName");
                }
                Timestamp d=cr.getTimestamp("date");
                String mn22="";
                int yr=d.getYear()+1900;
                int mn=d.getMonth()+1;
                int sc2=d.getDate();
                if(mn<10)
                    mn22="0"+mn;
                else
                    mn22=mn22+mn;
                String sc22="";
                if(sc2<10)
                    sc22="0"+sc2;
                else
                    sc22=sc22+sc2;
                String date2=yr+"/"+mn22+"/"+sc22;
                double bal=0.0,pri=0.0,Ppri=0.0;
                bal=cr.getDouble("totalPrice");
                ArrayList s = new ArrayList();
                s.add(itid);
                s.add(itNm);
                s.add(date2);
                s.add(bal);
                s.add(empN);
                s.add("Operator");
                Big.add(s);
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return Big;
    }
    NotificationManager NM;
    public static TextView TimeTx=null;
    public static TextView TimeTx1=null;
    public class ServerConnection extends AsyncTask<Void,String,Void> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                int count=0;
                int ckk=0;
                while (ckk==0)
                {
                    Statement st00 = MainActivity.connection.createStatement();
                    Statement st11 = MainActivity.connection.createStatement();
                    Statement st22 = MainActivity.connection.createStatement();
                    Statement st01 = MainActivity.connection.createStatement();
                    Statement st02 = MainActivity.connection.createStatement();
                    Statement st03 = MainActivity.connection.createStatement();
                    Statement st04 = MainActivity.connection.createStatement();
                    Statement st05 = MainActivity.connection.createStatement();
                    Statement st06 = MainActivity.connection.createStatement();
                    Statement st07 = MainActivity.connection.createStatement();
                    Statement st08 = MainActivity.connection.createStatement();
                    Statement st09 = MainActivity.connection.createStatement();
                    Statement st3 = MainActivity.connection.createStatement();
                    Statement st4=MainActivity.connection.createStatement();
                    Statement st5=MainActivity.connection.createStatement();
                    //Thread.sleep(200);
                    Cursor crr1=dbManager.fetch10();
                    if (crr1!=null)
                    {
                        AutoDate=crr1.getString(0);
                        DateFrom=crr1.getString(1);
                        AutoAssign=crr1.getString(2);
                    }
                    //Thread.sleep(200);
                    Date d = new Date();
                    int yr = d.getYear() + 1900;
                    int mn = d.getMonth() + 1;
                    int dt = d.getDate();
                    int ds=d.getDay();
                    String s16 = yr + "-" + mn + "-" + dt;
                    //Toast.makeText(root.getContext(),"Date="+s16,Toast.LENGTH_LONG).show();
                    int ckck=0;
                    if((MainActivity.Emp_USERNAME.equalsIgnoreCase("Admin")||MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Manager"))&&MainActivity.AutoDate.equalsIgnoreCase("true")&&MainActivity.DateFrom.equalsIgnoreCase("From Mobile"))
                    {
                        ResultSet cr1=st22.executeQuery("select date,dateName,localDate from workingDate where date!='"+s16+"'");
                        while (cr1.next())
                        {
                            ckck=1;
                        }
                    }
                    if(ckck==1)
                    {
                        SimpleDateFormat inform=new SimpleDateFormat("dd-MM-yyyy");
                        Date myDat=inform.parse(dt+"-"+mn+"-"+yr);
                        SimpleDateFormat simpleDateFo=new SimpleDateFormat("EEEE");
                        String dayName=simpleDateFo.format(myDat);
                        String t2Date=yr+"/"+ mn +"/"+dt;
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

                        String s1=s16;
                        String s2=dayName;
                        String s3=yr2+"/"+mn21+"/"+sc21;
                        st00.execute("delete from workingDate");
                        st11.execute("insert into workingDate(date,dateName,localDate) values('" + s1 + "','" + s2 + "','" + s3 + "')");
                        st00.execute("delete from workEnd where date='"+s1+"'");
                        String endD=s1+" 21:20:00.000";
                        st11.execute("insert into workEnd(date,stopTime,status) values('"+s1+"','"+endD+"','true')");
                        //Toast.makeText(root.getContext(), "Working Date Is Changed", Toast.LENGTH_LONG).show();
                    }
                    Main_WORK_DATE="";
                    ResultSet r1=st3.executeQuery("select date from workingDate");
                    int ckk1=0;
                    while(r1.next())
                    {
                        ckk1=1;
                        s16=r1.getString("date");
                    }
                    if(ckk1==0)
                    {
                        Main_WORK_DATE="No Working Date Found";
                    }
                    else
                    {
                        LocalDate cur1= LocalDate.parse(s16);
                        Main_WORK_DATE=s16;
                        String tim="";
                        Timestamp d1=null;
                        ResultSet r2=st4.executeQuery("select stopTime from workEnd where date='"+Main_WORK_DATE+"'");
                        while (r2.next())
                        {
                            d1=r2.getTimestamp("stopTime");;
                        }
                        Instant ins=d1.toInstant();
                        ZoneId zone=ZoneId.systemDefault();
                        ZonedDateTime zdt=ins.atZone(zone);
                        LocalTime lt1=zdt.toLocalTime();
                        LocalTime lt2=LocalTime.now();
                        int sec=lt2.getSecond();
                        int sec1=60-sec;
                        long nod= ChronoUnit.MINUTES.between(LocalTime.now(),lt1);
                        long v1=nod/60;
                        long v2=nod%60;
                        if(TimeTx!=null)
                        {
                            TimeTx.setText(v1 + ":" + v2 + ":" + sec1);
                        }
                        if(TimeTx1!=null)
                        {
                            TimeTx1.setText(v1 + ":" + v2 + ":" + sec1);
                        }
                        if(v1==0)
                        {
                            if(v2<0)
                            {
                                st5.execute("update workEnd set status='false' where date='"+Main_WORK_DATE+"'");
                                if(MainActivity.NEW_OPEN==1)
                                {
                                    if(!MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Manager"))
                                    {
                                        System.exit(0);
                                    }
                                }
                            }
                            if(v2<=50)
                            {
                                if(count==0)
                                {
                                    count=count+1;
                                    sendOnChannel1("Closing Time","Today's Time Is Close Soon. You Have Only "+v2+" Min",2);
                                }
                            }
                            if(v2==40&&(sec1<60&&sec1>50))
                            {
                                count=0;
                            }
                            if(v2==30&&(sec1<60&&sec1>50))
                            {
                                count=0;
                            }
                            if(v2==20&&(sec1<60&&sec1>50))
                            {
                                count=0;
                            }
                            if(v2==10&&(sec1<60&&sec1>50))
                            {
                                count=0;
                            }
                            if(v2==5)
                            {
                                count=0;
                            }
                        }
                    }
                    HomeFragment.B_ITEM=search1("");
                    HomeFragment.B_Transac=search2("");

                    HomeFragment.B_Collected = search4("");
                    HomeFragment.B_UnCollected = search3("");
                    Daily_Table_Transaction.B_Table_Transaction=search44("");
                    Thread.sleep(100);
                    if((MainActivity.Emp_USERNAME.equalsIgnoreCase("Admin")||MainActivity.Emp_PRIVILAGE.equalsIgnoreCase("Manager"))&&MainActivity.AutoAssign.equalsIgnoreCase("true"))
                    {
                        ArrayList TbGr=new ArrayList();
                        ResultSet cr3=st02.executeQuery("select * from tableGroup order by id");
                        while (cr3.next())
                        {
                            TbGr.add(cr3.getInt("id"));
                        }
                        ArrayList UserGr=new ArrayList();
                        ResultSet cr4=st02.executeQuery("select * from userAccount where duety = 'Waiter' order by count");
                        while (cr4.next())
                        {
                            UserGr.add(cr4.getString("userName"));
                        }
                        int ckck2=0;
                        ResultSet cr1=st01.executeQuery("select * from assignGroup");
                        while (cr1.next())
                        {
                            ckck2=1;
                        }
                        if(ckck2==0)
                        {
                            for(int i=0;i<UserGr.size();i++)
                            {
                                String userN=UserGr.get(i).toString();
                                int groId=Integer.parseInt(TbGr.get(i).toString());
                                String GrName="";
                                ResultSet cr5=st05.executeQuery("select * from tableGroup where id="+groId);
                                while (cr5.next())
                                {
                                    GrName=cr5.getString("TableGr");
                                }
                                st06.execute("delete from assignGroup where date='"+Main_WORK_DATE+"' and userName='"+userN+"' and grId="+groId);
                                st06.execute("insert into assignGroup(date, userName, grId, grName) " +
                                        "values('"+MainActivity.Main_WORK_DATE+"','"+userN+"',"+groId+",'"+GrName+"')");
                            }
                        }
                        else
                        {
                            int ckck1=0;
                            ResultSet cr7=st01.executeQuery("select * from assignGroup where date='"+MainActivity.Main_WORK_DATE+"'");
                            while (cr7.next())
                            {
                                ckck1=ckck1+1;
                            }
                            if(ckck1!=0||(ckck1!=TbGr.size()))
                            {
                                st06.execute("delete from assignGroup where date='"+Main_WORK_DATE+"'");
                            }
                            ArrayList UsGr=new ArrayList();
                            ResultSet cr2=st02.executeQuery("select TOP("+TbGr.size()+") * from assignGroup order by date desc,grId");
                            while (cr2.next())
                            {
                                ArrayList UsGr1=new ArrayList();
                                UsGr1.add(cr2.getString("userName"));
                                UsGr1.add(cr2.getInt("grId"));
                                UsGr.add(UsGr1);
                            }
                            if(UsGr.size()>0)
                            {
                                for(int i=0;i<UsGr.size();i++)
                                {
                                    ArrayList Usgr1= (ArrayList) UsGr.get(i);
                                    String userN=Usgr1.get(0).toString();
                                    int groId=Integer.parseInt(Usgr1.get(1).toString());
                                    groId=groId+1;
                                    int ckc=0;
                                    ResultSet cr5=st04.executeQuery("select * from tableGroup where id="+groId);
                                    while (cr5.next())
                                    {
                                        ckc=1;
                                    }
                                    if(ckc==0)
                                    {
                                        ResultSet cr6=st05.executeQuery("select TOP(1) * from tableGroup order by id");
                                        while (cr6.next())
                                        {
                                            groId=cr6.getInt("id");
                                        }
                                    }
                                    String GrName="";
                                    cr5=st03.executeQuery("select * from tableGroup where id="+groId);
                                    while (cr5.next())
                                    {
                                        GrName=cr5.getString("TableGr");
                                    }
                                    st06.execute("delete from assignGroup where date='"+Main_WORK_DATE+"' and userName='"+userN+"' and grId="+groId);
                                    st06.execute("insert into assignGroup(date, userName, grId, grName) " +
                                            "values('" + MainActivity.Main_WORK_DATE + "','" + userN + "'," + groId + ",'" + GrName + "')");
                                }
                                ArrayList store=new ArrayList();
                                ArrayList store1=new ArrayList();
                                ResultSet rs=st07.executeQuery("select id, TableGr from tableGroup");
                                while(rs.next())
                                {
                                    String tbb=rs.getString("id");
                                    int ck=0;
                                    ResultSet r=st08.executeQuery("select * from assignGroup where grId='"+tbb+"' and date='"+s16+"'");
                                    while(r.next())
                                    {
                                        ck=1;
                                    }
                                    if(ck==0)
                                    {
                                        store.add(rs.getInt("id"));
                                    }
                                }
                                rs=st07.executeQuery("select empName,userName from userAccount where duety='Waiter' order by count");
                                while(rs.next())
                                {
                                    String tbb=rs.getString("userName");
                                    int ck=0;
                                    ResultSet r=st09.executeQuery("select * from assignGroup where userName='"+tbb+"' and date='"+s16+"'");
                                    while(r.next())
                                    {
                                        ck=1;
                                    }
                                    if(ck==0)
                                    {
                                        store1.add(rs.getString("userName"));
                                    }
                                }
                                for(int i=0;i<store1.size();i++)
                                {
                                    String userN=store1.get(i).toString();
                                    int groId=Integer.parseInt(store.get(i).toString());
                                    String GrName="";
                                    ResultSet cr5=st05.executeQuery("select * from tableGroup where id="+groId);
                                    while (cr5.next())
                                    {
                                        GrName=cr5.getString("TableGr");
                                    }
                                    st06.execute("delete from assignGroup where date='"+Main_WORK_DATE+"' and userName='"+userN+"' and grId="+groId);
                                    st06.execute("insert into assignGroup(date, userName, grId, grName) " +
                                            "values('"+MainActivity.Main_WORK_DATE+"','"+userN+"',"+groId+",'"+GrName+"')");
                                }
                            }
                            else
                            {
                                for(int i=0;i<UserGr.size();i++)
                                {
                                    String userN=UserGr.get(i).toString();
                                    int groId=Integer.parseInt(TbGr.get(i).toString());
                                    String GrName="";
                                    ResultSet cr5=st05.executeQuery("select * from tableGroup where id="+groId);
                                    while (cr5.next())
                                    {
                                        GrName=cr5.getString("TableGr");
                                    }
                                    st06.execute("delete from assignGroup where date='"+Main_WORK_DATE+"' and userName='"+userN+"' and grId="+groId);
                                    st06.execute("insert into assignGroup(date, userName, grId, grName) " +
                                            "values('"+MainActivity.Main_WORK_DATE+"','"+userN+"',"+groId+",'"+GrName+"')");
                                }
                            }
                        }
                    }
                    Connection con = MainActivity.Conn();
                    Statement st = con.createStatement();
                    //HomeFragment.Connec();
                    if(Connection_type.equalsIgnoreCase("LAN(PC)"))
                    {
                        Statement st1 = con.createStatement();
                        Statement st2 = con.createStatement();
                        ResultSet rs=st.executeQuery("select * from voucher1 where cashier='"+MainActivity.Emp_USERNAME+"' and status='true' and order_status='Ordered' order by timeStamp desc");
                        while (rs.next())
                        {
                            String vouchId=rs.getString("voucherId");
                            int itCount=rs.getInt("itemCount");
                            String waiter=rs.getString("waiter");
                            String empN="";
                            ResultSet cc=st1.executeQuery("select empName from userAccount where userName='"+waiter+"'");
                            while (cc.next()){
                                empN=cc.getString("empName");
                            }
                            waiter=empN;
                            int ck=0;
                            String msg="You Have "+itCount+" Food Order.";
                            String msg1="Waiter is:-"+waiter;
                            Thread.sleep(2000);
                            sendOnChannel1(msg,msg1,1);
                            st2.execute("update voucher1 set order_status='Ready For Kitchen' where cashier='"+MainActivity.Emp_USERNAME+"' and voucherId='"+vouchId+"'");
                            //dbManager.update4(vouchId,"Ready For Kitchen");
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                //Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
                ServerConnection serverConnection=new ServerConnection();
                serverConnection.execute();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(String... values) {
            //sendOnChannel1();
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private static EditText txv=null;
    public static int QrCho=0;
    public static void scanQr(EditText tx)
    {
        txv=tx;
        try {

            IntentIntegrator integrator = new IntentIntegrator(AppCom);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(true);
            integrator.setOrientationLocked(false);
            integrator.initiateScan();

            //Intent intent = integrator.createScanIntent();
            //AppCom.startActivityForResult(intent, 0);
        }
        catch (Exception ex)
        {
            Toast.makeText(AppCom.getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null)
            {
                if(result.getContents() == null)
                {
                    Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Log.e("Scan", "Scanned");

                    //com.example.agelegelreader.ui.meterReading.meterFragment.BillRef1=result.getContents();
                    txv.setText(result.getContents());
                    if(QrCho==1)
                    {
                        Sales_InvoiceFragment.itemCode(result.getContents());
                    }
                    Toast.makeText(getApplicationContext(),"Scanned: " + result.getContents(),Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),"no result",Toast.LENGTH_LONG).show();
                super.onActivityResult(requestCode, resultCode, data);
            }
            //com.example.agelegelreader.ui.meterReading.meterFragment.searchCustData();
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}