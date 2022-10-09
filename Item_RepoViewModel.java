package org.o7planning.agelegecafe.ui.item_report;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Item_RepoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Item_RepoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}