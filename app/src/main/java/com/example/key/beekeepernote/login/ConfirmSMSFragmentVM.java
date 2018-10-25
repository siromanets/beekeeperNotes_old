package com.example.key.beekeepernote.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

public class ConfirmSMSFragmentVM extends AndroidViewModel {

    private static final String TAG = "ConfirmSMSFragmentVM";

    private static final int API_ERROR_VERIFICATION = 400;


    public ConfirmSMSFragmentVM(@NonNull Application application) {
        super(application);
    }
}
