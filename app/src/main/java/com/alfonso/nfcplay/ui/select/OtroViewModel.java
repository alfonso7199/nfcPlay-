package com.alfonso.nfcplay.ui.select;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OtroViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public OtroViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pulse el botón para activar o desactivar el NFC");

    }

    public LiveData<String> getText() {
        return mText;
    }
}