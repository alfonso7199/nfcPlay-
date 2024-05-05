package com.alfonso.nfcplay.ui.select;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OtroViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public OtroViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Acerca una tarjeta NFC para insertarle información");

    }

    public LiveData<String> getText() {
        return mText;
    }
}