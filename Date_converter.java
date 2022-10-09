package org.o7planning.agelegecafe;

import java.util.ArrayList;
import java.util.Date;

public class Date_converter {
    public static ArrayList ToEth(String Edate)
    {
        String str="";
        Date d=new Date(Edate);
        int year=d.getYear()+1900;
        int month=d.getMonth()+1;
        int date=d.getDate();
        int rem=year%4;//remender
        int Ethyear=0;
        int Ethmonth=0;
        int Ethdate=0;
        if(month>9)
        {
            Ethyear=year-7;
            if(rem==3)
            {
                if(month==10)
                {
                    if(date>11)
                    {
                        Ethmonth=month-8;
                        Ethdate=date-11;
                    }
                    else
                    {
                        Ethmonth=month-9;
                        int b=date-11;
                        Ethdate=30+b;
                    }
                }
                else if(month==11 ||month==12)
                {
                    if(date>10)
                    {
                        Ethmonth=month-8;
                        Ethdate=date-10;
                    }
                    else
                    {
                        Ethmonth=month-9;
                        int b=date-10;
                        Ethdate=30+b;
                    }
                }
            }
            else
            {
                if(month==10)
                {
                    if(date>10)
                    {
                        Ethmonth=month-8;
                        Ethdate=date-10;
                    }
                    else
                    {
                        Ethmonth=month-9;
                        int b=date-10;
                        Ethdate=30+b;
                    }
                }
                else if(month==11 ||month==12)
                {
                    if(date>9)
                    {
                        Ethmonth=month-8;
                        Ethdate=date-9;
                    }
                    else
                    {
                        Ethmonth=month-9;
                        int b=date-9;
                        Ethdate=30+b;
                    }
                }
            }
        }
        else if(month==9)
        {
            if(rem==3)
            {
                if(date>11)
                {
                    Ethyear=year-7;
                    Ethmonth=month-8;
                    Ethdate=date-11;
                }
                else if((date<=11)&&(date>=6))
                {
                    Ethyear=year-8;
                    Ethmonth=13;
                    int b=date-11;
                    Ethdate=6+b;
                }
                else
                {
                    Ethyear=year-8;
                    Ethmonth=12;
                    int b=date-11;
                    Ethdate=36+b;
                }
            }
            else
            {
                if(date>10)
                {
                    Ethyear=year-7;
                    Ethmonth=month-8;
                    Ethdate=date-10;
                }
                else if((date<=10)&&(date>=6))
                {
                    Ethyear=year-8;
                    Ethmonth=13;
                    int b=date-10;
                    Ethdate=5+b;
                }
                else
                {
                    Ethyear=year-8;
                    Ethmonth=12;
                    int b=date-10;
                    Ethdate=35+b;
                }
            }
        }
        else if(month<9)
        {
            Ethyear=year-8;
            if(month==8)
            {
                if(date>6)
                {
                    Ethmonth=12;
                    Ethdate=date-6;
                }
                else
                {
                    Ethmonth=11;
                    int b=date-6;
                    Ethdate=30+b;
                }
            }
            else if(month==7)
            {
                if(date>7)
                {
                    Ethmonth=11;
                    Ethdate=date-7;
                }
                else
                {
                    Ethmonth=10;
                    int b=date-7;
                    Ethdate=30+b;
                }
            }
            else if(month==6)
            {
                if(date>7)
                {
                    Ethmonth=10;
                    Ethdate=date-7;
                }
                else
                {
                    Ethmonth=9;
                    int b=date-7;
                    Ethdate=30+b;
                }
            }
            else if(month==5)
            {
                if(date>8)
                {
                    Ethmonth=9;
                    Ethdate=date-8;
                }
                else
                {
                    Ethmonth=8;
                    int b=date-8;
                    Ethdate=30+b;
                }
            }
            else if(month==4)
            {
                if(date>8)
                {
                    Ethmonth=8;
                    Ethdate=date-8;
                }
                else
                {
                    Ethmonth=7;
                    int b=date-8;
                    Ethdate=30+b;
                }
            }

            else if(month==3)
            {
                if(date>9)
                {
                    Ethmonth=7;
                    Ethdate=date-9;
                }
                else
                {
                    Ethmonth=6;
                    int b=date-9;
                    Ethdate=30+b;
                }
            }
            else if(month==2)
            {
                if(rem==3)
                {
                    if(date>8)
                    {
                        Ethmonth=6;
                        Ethdate=date-8;
                    }
                    else
                    {
                        Ethmonth=5;
                        int b=date-8;
                        Ethdate=30+b;
                    }
                }
                else
                {
                    if(date>7)
                    {
                        Ethmonth=6;
                        Ethdate=date-8;
                    }
                    else
                    {
                        Ethmonth=5;
                        int b=date-7;
                        Ethdate=30+b;
                    }
                }
            }
            else if(month==1)
            {
                if(rem==3)
                {
                    if(date>9)
                    {
                        Ethmonth=5;
                        Ethdate=date-9;
                    }
                    else
                    {
                        Ethmonth=4;
                        int b=date-9;
                        Ethdate=30+b;
                    }
                }
                else
                {
                    if(date>8)
                    {
                        Ethmonth=5;
                        Ethdate=date-8;
                    }
                    else
                    {
                        Ethmonth=4;
                        int b=date-8;
                        Ethdate=30+b;
                    }
                }
            }
        }
        ArrayList arr=new ArrayList();
        arr.add(Ethyear);
        arr.add(Ethmonth);
        arr.add(Ethdate);
        return arr;
    }
    public static ArrayList ToGre(ArrayList Gdate)
    {
        String eyear=Gdate.get(0).toString();
        int year=Integer.parseInt(eyear);

        String emonth=Gdate.get(1).toString();
        int month=Integer.parseInt(emonth);

        String edate=Gdate.get(2).toString();
        int date=Integer.parseInt(edate);

        int Greyear=0;
        int Gremonth=0;
        int Gredate=0;
        int rem=year%4;//remender


        if(month<4)
        {
            Greyear=year+7;
            if(month==1)
            {
                if(rem==0)
                {
                    if(date<20)
                    {
                        Gremonth=month+8;
                        Gredate=date+11;
                    }
                    else
                    {
                        Gremonth=month+9;
                        int ddd=date+11;
                        Gredate=ddd-30;
                    }
                }
                else
                {
                    if(date<21)
                    {
                        Gremonth=month+8;
                        Gredate=date+10;
                    }
                    else
                    {
                        Gremonth=month+9;
                        int ddd=date+10;
                        Gredate=ddd-30;
                    }
                }
            }
            else if(month==2)
            {
                if(rem==0)
                {
                    if(date<21)
                    {
                        Gremonth=month+8;
                        Gredate=date+11;
                    }
                    else
                    {
                        Gremonth=month+9;
                        int ddd=date+10;
                        Gredate=ddd-30;
                    }
                }
                else
                {
                    if(date<22)
                    {
                        Gremonth=month+8;
                        Gredate=date+10;
                    }
                    else
                    {
                        Gremonth=month+9;
                        int ddd=date+9;
                        Gredate=ddd-30;
                    }
                }
            }
            else if(month==3)
            {
                if(rem==0)
                {
                    if(date<21)
                    {
                        Gremonth=month+8;
                        Gredate=date+10;
                    }
                    else
                    {
                        Gremonth=month+9;
                        int ddd=date+10;
                        Gredate=ddd-30;
                    }
                }
                else
                {
                    if(date<22)
                    {
                        Gremonth=month+8;
                        Gredate=date+9;
                    }
                    else
                    {
                        Gremonth=month+9;
                        int ddd=date+9;
                        Gredate=ddd-30;
                    }
                }
            }
        }
        else if(month==4)
        {
            if(rem==0)
            {
                if(date<22)
                {
                    Greyear=year+7;
                    Gremonth=month+8;
                    Gredate=date+10;
                }
                else
                {
                    Greyear=year+8;
                    Gremonth=1;
                    int ddd=date+9;
                    Gredate=ddd-30;
                }
            }
            else
            {
                if(date<23)
                {
                    Greyear=year+7;
                    Gremonth=month+8;
                    Gredate=date+9;
                }
                else
                {
                    Greyear=year+8;
                    Gremonth=1;
                    int ddd=date+8;
                    Gredate=ddd-30;
                }
            }
        }
        else if(month>4)
        {
            Greyear=year+8;
            if(month==5)
            {
                if(rem==0)
                {
                    if(date<23)
                    {
                        Gremonth=1;
                        Gredate=date+9;
                    }
                    else
                    {
                        Gremonth=2;
                        int ddd=date+8;
                        Gredate=ddd-30;
                    }
                }
                else
                {
                    if(date<24)
                    {
                        Gremonth=1;
                        Gredate=date+8;
                    }
                    else
                    {
                        Gremonth=2;
                        int ddd=date+7;
                        Gredate=ddd-30;
                    }
                }
            }
            else if(month==6)
            {
                if(rem==0)
                {
                    if(date<22)
                    {
                        Gremonth=2;
                        Gredate=date+8;
                    }
                    else
                    {
                        Gremonth=3;
                        int ddd=date+9;
                        Gredate=ddd-30;
                    }
                }
                else
                {
                    if(date<23)
                    {
                        Gremonth=2;
                        Gredate=date+7;
                    }
                    else
                    {
                        Gremonth=3;
                        int ddd=date+8;
                        Gredate=ddd-30;
                    }
                }
            }
            else if(month==7)
            {
                if(date<23)
                {
                    Gremonth=3;
                    Gredate=date+9;
                }
                else
                {
                    Gremonth=4;
                    int ddd=date+8;
                    Gredate=ddd-30;
                }
            }
            else if(month==8)
            {
                if(date<23)
                {
                    Gremonth=4;
                    Gredate=date+8;
                }
                else
                {
                    Gremonth=5;
                    int ddd=date+8;
                    Gredate=ddd-30;
                }
            }
            else if(month==9)
            {
                if(date<24)
                {
                    Gremonth=5;
                    Gredate=date+8;
                }
                else
                {
                    Gremonth=6;
                    int ddd=date+7;
                    Gredate=ddd-30;
                }
            }
            else if(month==10)
            {
                if(date<24)
                {
                    Gremonth=6;
                    Gredate=date+7;
                }
                else
                {
                    Gremonth=7;
                    int ddd=date+7;
                    Gredate=ddd-30;
                }
            }
            else if(month==11)
            {
                if(date<25)
                {
                    Gremonth=7;
                    Gredate=date+7;
                }
                else
                {
                    Gremonth=8;
                    int ddd=date+6;
                    Gredate=ddd-30;
                }
            }
            else if(month==12)
            {
                if(date<26)
                {
                    Gremonth=8;
                    Gredate=date+6;
                }
                else
                {
                    Gremonth=9;
                    int ddd=date+5;
                    Gredate=ddd-30;
                }
            }
            else if(month==13)
            {
                Gremonth=9;
                Gredate=date+5;
            }
        }

        ArrayList arr=new ArrayList();
        arr.add(Greyear);
        arr.add(Gremonth);
        arr.add(Gredate);

        return arr;
    }
}
