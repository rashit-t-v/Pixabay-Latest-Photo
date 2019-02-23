package com.rashit.tiugaev.image.mvp.presenter;

import com.rashit.tiugaev.image.Hit;
import com.rashit.tiugaev.image.mvp.callback.PhotoCallBack;
import com.rashit.tiugaev.image.mvp.model.PhotoModel;

import java.util.List;

public class PhotoPresenter implements PhotoCallBack.returnPresenter{

    private PhotoCallBack.returnView returnView;
    private PhotoModel photoModel;

    public PhotoPresenter (PhotoCallBack.returnView returnV, PhotoModel model){
        returnView = returnV;
        photoModel = model;
        photoModel.setCallBack(this);
    }

    public void getData (String order, String orintation, int count_per_page){
        photoModel.getDataModel(order,orintation,count_per_page);
    }


    @Override
    public void onSuccses(List<Hit> dataBases) {
        returnView.showData(dataBases);
    }
}
