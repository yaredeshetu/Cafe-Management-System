package org.o7planning.agelegecafe.ui.order_view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class order_viewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public order_viewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}