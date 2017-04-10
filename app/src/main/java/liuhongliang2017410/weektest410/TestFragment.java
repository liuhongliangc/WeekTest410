package liuhongliang2017410.weektest410;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.xllistviewlhl10.XListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * date:2017/4/10.
 * author:刘宏亮.
 * function:
 */

public class TestFragment extends Fragment implements XListView.IXListViewListener{
    private static final String KEY_CONTENT = "TestFragment:Content";
    private XListView mXListView;

    public static TestFragment newInstance(String content) {
            TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",content);
        fragment.setArguments(bundle);

            return fragment;
        }

        private String mContent = "";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
                mContent = savedInstanceState.getString(KEY_CONTENT);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment,container,false);
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString(KEY_CONTENT, mContent);
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mXListView = (XListView) getView().findViewById(R.id.frag_xlistview);
        mXListView.setPullLoadEnable(true);
        mXListView.setPullRefreshEnable(true);
        mXListView.setXListViewListener(this);
        String url = getArguments().getString("url");
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                Gson gson=new Gson();
                GsonBean bean = gson.fromJson(result, GsonBean.class);
                mXListView.setAdapter(new CommonAdapter<GsonBean.ResultBean.DataBean>(getActivity(),bean.getResult().getData()) {
                    @Override
                    public void convert(final ViewHolder holder, GsonBean.ResultBean.DataBean item) {
                        holder.setText(R.id.textview,item.getTitle());
                        x.image().loadDrawable(item.getThumbnail_pic_s(),new ImageOptions.Builder().build(), new Callback.CommonCallback<Drawable>() {
                            @Override
                            public void onSuccess(Drawable result) {
                                holder.setImage(R.id.imageView).setImageDrawable(result);
                            }
                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                            }
                            @Override
                            public void onCancelled(CancelledException cex) {
                            }
                            @Override
                            public void onFinished() {
                            }
                        });
                    }
                });

            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    public void onLoad(){
        mXListView.stopLoadMore();
        mXListView.stopRefresh();
    }
    @Override
    public void onRefresh() {
        onLoad();
    }

    @Override
    public void onLoadMore() {
        onLoad();
    }
}
