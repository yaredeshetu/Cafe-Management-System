package org.o7planning.agelegecafe.ui.aboutSebtash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class aboutSubtashViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public aboutSubtashViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}