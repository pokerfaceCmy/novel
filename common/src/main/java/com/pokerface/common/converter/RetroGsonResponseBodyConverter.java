package com.pokerface.common.converter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pokerface.common.bean.IApiResult;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/14 17:45
 * @Desc: TODO
 * @GitHub： https://github.com/pokerfaceCmy
 */

class RetroGsonResponseBodyConverter<T, ApiResultType extends IApiResult> implements
        Converter<ResponseBody, T> {
    private final Gson gson;
    private Type type;
    private Class<ApiResultType> apiClass;

    RetroGsonResponseBodyConverter(Gson gson, Type type, Class<ApiResultType> apiClass) {
        this.gson = gson;
        this.type = type;
        this.apiClass = apiClass;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String response = value.string();
            ApiResultType apiResult = gson.fromJson(response, apiClass);
            if (!apiResult.isSuccess()) {
//                throw new ServerException(apiResult.getHttpCode(), apiResult.getHttpMsg());
            } else if (type.equals(apiClass)) {
                //noinspection unchecked
                return (T) apiResult;
            } else if (apiResult.getHttpData() == null) {
//                throw new SuccessWithNullDataException(apiResult.getResultMsg(), apiResult
//                        .getResultCode());
            }
            //如果未设置data字段，则认为返回的整个结果就是数据段
            if (TextUtils.isEmpty(apiResult.getDataField())) {
                return gson.fromJson(response, type);
            }
            //parse data field 解析data数据
//            Logger.json(
//                    ((JsonObject) new JsonParser().parse(response)).get(apiResult.getDataField()).toString()
//            );
            return gson.fromJson(((JsonObject) new JsonParser().parse(response)).get(apiResult.getDataField()), type);
        } finally {
            value.close();
        }
    }
}