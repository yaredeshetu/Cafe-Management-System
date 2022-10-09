package org.o7planning.agelegecafe.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.o7planning.agelegecafe.R;

public class userFragment extends Fragment {

    private org.o7planning.agelegecafe.ui.user.userViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(org.o7planning.agelegecafe.ui.user.userViewModel.class);
        View root = inflater.inflate(R.layout.user, container, false);
        return root;
    }
}