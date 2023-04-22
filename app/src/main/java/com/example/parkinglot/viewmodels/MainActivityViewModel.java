package com.example.parkinglot.viewmodels;

import androidx.lifecycle.ViewModel;
import com.example.parkinglot.models.TdxModel;

public class MainActivityViewModel extends ViewModel {
    private final TdxModel tdxModel = new TdxModel();

    public void doCheck() {
        tdxModel.checkFirstStartupApp();
    }
}
