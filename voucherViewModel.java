package org.o7planning.agelegecafe.ui.voucher;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class voucherViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public voucherViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}