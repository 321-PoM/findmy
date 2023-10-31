package com.example.findmy.network;

import androidx.lifecycle.ViewModel;

public class FindMyServiceViewModel extends ViewModel {
    private FindMyService findMyService;

    public void initFindMyService(){
        findMyService = new FindMyService();
    }

    public FindMyService getFindMyService() {
        return findMyService;
    }
}
