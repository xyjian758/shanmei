package com.shanmeicloud.android.mvp.home;

import com.shanmeicloud.android.base.BaseModel;
import com.shanmeicloud.android.base.BasePresenter;
import com.shanmeicloud.android.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by DELL on 2017/4/6.
 */

public class MainContract {
        interface Model extends BaseModel {
//        Observable< List<NewsChannelTable> > lodeMineNewsChannels();
        Observable<List<String>> testModel();
    }

    interface View extends BaseView {
//        void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine);
        void testView();
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void testPresenter();
    }
}
