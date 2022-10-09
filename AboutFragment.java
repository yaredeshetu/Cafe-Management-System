package org.o7planning.agelegecafe.ui.about;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.o7planning.agelegecafe.DBManager;
import org.o7planning.agelegecafe.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class AboutFragment extends Fragment {

    private AboutViewModel slideshowViewModel;
    private static View root=null;
    private static DBManager dbManager;
    private static Button b1=null,b2=null,b3=null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(AboutViewModel.class);

        root = inflater.inflate(R.layout.about, container, false);
        try
        {
            dbManager = new DBManager(root.getContext());
            dbManager.open();
        }
        catch (Exception ex)
        {
            Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        b1=root.findViewById(R.id.abtb1);
        b2=root.findViewById(R.id.abtb2);
        b3=root.findViewById(R.id.abtb3);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    exportDB(root.getContext());
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    importDB(root.getContext());
                }
                catch (Exception ex)
                {
                    Toast.makeText(root.getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }
    public static void importDB(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {
                File backupDB = context.getDatabasePath(DBManager.dbHelper.getDatabaseName());//(DBHandler.getDBName());
                String backupDBPath = String.format("%s.bak", DBManager.dbHelper.getDatabaseName());
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Toast.makeText(root.getContext(),"Import Successful",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportDB(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String backupDBPath = String.format("%s.bak", DBManager.dbHelper.getDatabaseName());
                File currentDB = context.getDatabasePath(DBManager.dbHelper.getDatabaseName());
                File backupDB = new File(sd, backupDBPath);
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Toast.makeText(root.getContext(),"sd Backup Successful",Toast.LENGTH_LONG).show();
            }
            else if(data.canWrite())
            {
                String backupDBPath = String.format("%s.bak", DBManager.dbHelper.getDatabaseName());
                File currentDB = context.getDatabasePath(DBManager.dbHelper.getDatabaseName());
                File backupDB = new File(data, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Toast.makeText(root.getContext(),"data Backup Successful",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(root.getContext(),"error="+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}