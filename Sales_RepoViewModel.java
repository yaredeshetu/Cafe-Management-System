package org.o7planning.agelegecafe.ui.sales_report;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Sales_RepoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Sales_RepoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}