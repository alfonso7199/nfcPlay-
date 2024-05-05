package com.alfonso.nfcplay.ui.write;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WriteViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WriteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aquí podrás escribir en tarjetas NFC");
    }

    public LiveData<String> getText() {
        return mText;
    }
}