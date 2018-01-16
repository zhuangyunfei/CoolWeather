package com.example.coolweather.util;

import android.text.TextUtils;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称
 * 创建人
 * 创建时间
 * 说明
 */

public class Utility {
    /**
     *User: langyazyf
     *Data: 2018/1/15
     *Time:13:29
     *Description:解析和处理服务器返回的省级数据
     */
    public static boolean  handleProvinceResponse(String response) {
        //通过TextUtils类（判断简单文本的类）的isEmpty方法来判断服务器返回的数据是否为空
        if (!TextUtils.isEmpty(response)) {
            try {
                //将服务器返回的数据response填充到JSONArray数组中
                JSONArray allProvince = new JSONArray(response);
                //依次获取JSONArray数组中的数据
                for (int i=0;i<allProvince.length();i++) {
                    //通过索引找到具体的Json数据对象
                    JSONObject provinceObject = allProvince.getJSONObject(i);
                    Province province = new Province();
                    //将JSONArray数组中的数据“name”找出来，填充到ProvinceName中
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    //将服务器解析后的数据保存到数据库
                    province.save();
                }
                return true;
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;

    }
    /**
     *User: langyazyf
     *Data: 2018/1/15
     *Time:14:48
     *Description:解析和处理服务器返回的市级数据
     */
    public static boolean handleCityResponse(String response,int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray();
                for (int i = 0; i<allCities.length();i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    //解析数据后进行拼接City数据表
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    //保存City数据表到数据库
                    city.save();
                }
                return true;
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     *User: langyazyf
     *Data: 2018/1/15
     *Time:15:04
     *Description:解析和处理服务器返回的县级数据
     */
    public static boolean handleCountyResponse(String response,int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray();
                for (int i=0;i<allCounties.length();i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("id"));
                    county.setCityId(cityId);
                }
                return true;
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
