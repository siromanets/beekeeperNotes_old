package com.example.key.beekeepernote.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({Status.SUCCESS, Status.LOADING, Status.ERROR})
public @interface Status {
    int SUCCESS = 0;
    int LOADING = 1;
    int ERROR = 2;
}
