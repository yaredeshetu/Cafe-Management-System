package org.o7planning.agelegecafe.ui.item_registration;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemRegViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ItemRegViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}