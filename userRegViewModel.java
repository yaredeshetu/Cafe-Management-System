package org.o7planning.agelegecafe.ui.user_registeration;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class userRegViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public userRegViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}