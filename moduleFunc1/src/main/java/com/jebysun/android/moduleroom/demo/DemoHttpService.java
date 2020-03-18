package com.jebysun.android.moduleroom.demo;

import com.jebysun.android.appcommon.contants.APIContants;
import com.jebysun.android.appcommon.http.Result;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DemoHttpService {

    /**
     * 返回OKhttp原始ResponseBody
     * @return
     */
    @GET(value = APIContants.HMTL_PAGE)
    Observable<ResponseBody> getHTMLPage();

    /**
     * 获取礼物列表
     * @param paramHolder 占位参数，本接口业务上不需要参数，但框架代码需要参数。
     * @return
     */
    @FormUrlEncoded
    @POST(value = APIContants.JSON_DATA)
    Observable<Result<String>> giftList(@Field("paramholder") String paramHolder);

}
