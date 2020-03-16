package com.jebysun.android.appcommon.http;

import android.util.Log;

import com.jebysun.android.appcommon.MyApp;
import com.jebysun.android.appcommon.util.AESBase64EncodeUtil;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxRetrofitClient {

    private static final int DEFAULT_TIME_OUT = 5;
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private Retrofit mRetrofit;
    private static final RxRetrofitClient INSTANCE = new RxRetrofitClient();

    private RxRetrofitClient() {
        init();
    }


    public static RxRetrofitClient getInstance() {
        return INSTANCE;
    }

    public <T> T createHTTPService(Class<T> serviceClass) {
        return mRetrofit.create(serviceClass);
    }

    private void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS); // 连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS); // 写操作超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS); // 读操作超时时间
        builder.retryOnConnectionFailure(true); // 错误重连
        // 添加公共参数拦截器
//        builder.addInterceptor(buildCommonParamsInterceptor());
        // 添加网络日志拦截器
        builder.addInterceptor(buildLogInterceptor());
        builder.addInterceptor(new ChuckInterceptor(MyApp.getAppContext()));

        httpsSurport(builder);

        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                // TODO 使用BuildConfig字段配置
                .baseUrl("https://test.qiuqiuyuliao.com/")
                .build();
    }

    /**
     * 解决在Android5.0版本以下https无法访问
     */
    private void httpsSurport(OkHttpClient.Builder httpClient) {
        ConnectionSpec spec1 = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .allEnabledCipherSuites()
                .build();
        // 兼容http接口
        ConnectionSpec spec2 = new ConnectionSpec.Builder(ConnectionSpec.CLEARTEXT).build();
        httpClient.connectionSpecs(Arrays.asList(spec1, spec2));
    }

    /**
     * 添加公共参数拦截器
     * @return
     */
    private CommonParamsInterceptor buildCommonParamsInterceptor() {
        // 添加公共参数拦截器
        CommonParamsInterceptor interceptor = new CommonParamsInterceptor();
        return interceptor;
    }

    private HttpLoggingInterceptor buildLogInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("【HTTP】", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }



    class CommonParamsInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String requestMethod = request.method();
            if (requestMethod.equals("GET")) {
//                HttpUrl httpUrl = request.url();
//                HttpUrl newHttpUrl = httpUrl.newBuilder()
//                        .addEncodedQueryParameter("newKey", "newValue")
//                        .addEncodedQueryParameter("newKey2", "newvalue2")
//                        .build();
//                request = request.newBuilder().url(newHttpUrl).build();
            }
            else if (requestMethod.equals("POST")) {
                RequestBody body = request.body();
                // 参数就要针对body做操作，这里针对From表单做操作
                if (body instanceof FormBody) {
                    FormBody formBody = (FormBody) body;
                    Map<String, String> paramsMap = new HashMap<>(formBody.size()+5);
                    // 追加新的参数
                    // 客户端版本 TODO
                    paramsMap.put("version", "1");
                    // 客户端唯一识别号 TODO
                    paramsMap.put("mobile_devices", "ID0001");
                    // 设备型号 TODO
                    paramsMap.put("i_mei", "Androidp_30");
                    // 时间戳(秒)
                    paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
                    // 用户token TODO
                    paramsMap.put("token", "D81C2908242FD1C88EFE5FEEE9ACD15C");

                    // 将以前的参数添加
                    for (int i = 0; i < formBody.size(); i++) {
                        paramsMap.put(formBody.encodedName(i), formBody.encodedValue(i));
                    }

                    List<Map.Entry<String, String>> entryList = new ArrayList<>(paramsMap.entrySet());
                    // 参数字母表升序排序
                    Collections.sort(entryList, new Comparator<Map.Entry<String, String>>() {
                        @Override
                        public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                            return o1.getKey().compareTo(o2.getKey());
                        }
                    });

                    // 构造新的请求表单
                    FormBody.Builder formBuilder = new FormBody.Builder();
                    StringBuilder encodeSrcStr = new StringBuilder();
                    for (Map.Entry<String, String> entry : entryList) {
                        formBuilder.add(entry.getKey(), entry.getValue());

                        encodeSrcStr.append(entry.getKey());
                        encodeSrcStr.append("=");
                        encodeSrcStr.append(entry.getValue());
                        encodeSrcStr.append("&");
                    }

                    String encodeStr = encodeSrcStr.substring(0, encodeSrcStr.length()-1);
                    // 安全签名
                    formBuilder.add("sign", AESBase64EncodeUtil.encode(encodeStr));

                    // 构造新的请求体
                    request = request.newBuilder().post(formBuilder.build()).build();
                }
            }

            return chain.proceed(request);
        }



    }


}
