package org.o7planning.agelegecafe.ui.sales_invoice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Sales_InvoiceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Sales_InvoiceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}