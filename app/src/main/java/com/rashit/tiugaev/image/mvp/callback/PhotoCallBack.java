package com.rashit.tiugaev.image.mvp.callback;

import com.rashit.tiugaev.image.pojo.Hit;

import java.util.List;

public interface PhotoCallBack {

    interface returnView{
        void  showData (List<Hit> posts);
        void countPage();
    }
    interface returnPresenter{
        void onSuccses(List<Hit> dataBases);

    }
}
