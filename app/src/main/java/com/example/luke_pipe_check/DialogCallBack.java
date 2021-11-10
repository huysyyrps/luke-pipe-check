package com.example.luke_pipe_check;

import android.app.Dialog;

/**
 * Created by dell on 2017/4/25.
 */

public interface DialogCallBack {
    void confirm(String name, Dialog dialog);

    void cancel();
}