package org.o7planning.agelegecafe.ui.opptra;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OppTraViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OppTraViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is senoono fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}