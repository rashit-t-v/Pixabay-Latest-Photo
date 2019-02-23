package com.rashit.tiugaev.image.mvp.callback;

import com.rashit.tiugaev.image.Hit;

import java.util.List;

public interface PhotoCallBack {

    interface returnView{
        void  showData (List<Hit> posts);
    }
    interface returnPresenter{
        void onSuccses(List<Hit> dataBases);
    }
}
