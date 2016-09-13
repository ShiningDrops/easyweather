package com.weather.byhieg.easyweather.Tools;

import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.Db.City;
import com.weather.byhieg.easyweather.Db.CityWeather;
import com.weather.byhieg.easyweather.Db.CityWeatherDao;
import com.weather.byhieg.easyweather.Db.LoveCity;
import com.weather.byhieg.easyweather.Db.LoveCityDao;
import com.weather.byhieg.easyweather.Db.Province;
import com.weather.byhieg.easyweather.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class HandleDaoData {

    /**
     * 根据城市，得到城市天气表中的天气数据
     * @param city city
     * @return WeatherBean对象的天气数据
     * @throws Exception
     */

    public static WeatherBean getWeatherBean(String city) throws Exception{
        List<CityWeather> cityWeather = MyApplication.
                                  getDaoSession().
                                  getCityWeatherDao().
                                  queryBuilder().
                                  where(CityWeatherDao.Properties.CityName.like(city)).limit(1).list();

        if (cityWeather != null && cityWeather.size() != 0) {
            byte[] javaObjBytes = cityWeather.get(0).getWeatherBean();
            ByteArrayInputStream bai = new ByteArrayInputStream(javaObjBytes);
            return (WeatherBean) new ObjectInputStream(bai).readObject();
        }else{
            return null;
        }
    }

    /**
     *
     * @return 要展示的城市的名字
     */

    public static String getShowCity() {
        LoveCity city = MyApplication.
                        getDaoSession().
                        getLoveCityDao().
                        queryBuilder().
                        where(LoveCityDao.Properties.Order.eq(1)).list().get(0);

        if (city != null) {
            return city.getCitynName();
        }else{
            return null;
        }
    }

    /******************************************************************************************
     *  对CityWeatherDao进行的操作
     *******************************************************************************************/

    /**
     * 插入城市天气
     * @param cityWeather 要插入的城市天气
     */
    public static void insertCityWeather(CityWeather cityWeather){
        MyApplication.getDaoSession().getCityWeatherDao().insert(cityWeather);
    }

    /**
     * 查询城市天气表中　该城市的天气是否存在
     * @param city 城市名字
     * @return true 存在
     */

    public static boolean isExistInCityWeather(String city) {
        List<CityWeather> cityWeathers = MyApplication.
                                         getDaoSession().
                                         getCityWeatherDao().
                                         queryBuilder().
                                         where(CityWeatherDao.Properties.CityName.eq(city)).
                                         limit(1).list();

        return cityWeathers != null && cityWeathers.size() != 0;
    }


    /**
     * 根据天气数据 更新表中内容
     * @param cityWeather 天气数据
     */
    public static void updateCityWeather(CityWeather cityWeather) {
        String name = cityWeather.getCityName();
        CityWeather temp = getCityWeather(name);
        temp.setWeatherBean(cityWeather.getWeatherBean());
        temp.setUpdateTime(cityWeather.getUpdateTime());
        MyApplication.getDaoSession().getCityWeatherDao().update(temp);
    }


    public static CityWeather getCityWeather(String cityName){
        List<CityWeather> cityWeathers = MyApplication.
                                  getDaoSession().
                                  getCityWeatherDao().
                                  queryBuilder().
                                  where(CityWeatherDao.Properties.CityName.eq(cityName)).list();

        return cityWeathers.get(cityWeathers.size() - 1);

    }

    /******************************************************************************************
     * 对CityDao进行的操作
     *******************************************************************************************/

    /**
     * 添加城市进入城市表中
     * @param city 城市数据
     */
    public static void insertCity(City city) {
        MyApplication.getDaoSession().getCityDao().insert(city);
    }

    public static boolean isExistInCity(){
        City tempCity = MyApplication.
                getDaoSession().
                getCityDao().
                loadByRowId(1);

        return tempCity != null;
    }


    /******************************************************************************************
     * 对ProvinceDao进行的操作
     *******************************************************************************************/

    /**
     * 添加省份进入省份表中
     * @param province 省份数据
     */
    public static void insertProvince(Province province) {
        MyApplication.getDaoSession().getProvinceDao().insert(province);
    }


    public static boolean isExistInProvince(){
        Province tempProvince = MyApplication.
                getDaoSession().
                getProvinceDao().
                loadByRowId(1);

        return tempProvince != null;
    }


    /******************************************************************************************
     *  对LoveCityDao进行的操作
     *******************************************************************************************/
    /**
     * 重载方法 根据order的值得到喜欢的城市
     * @param order 喜欢城市的顺序
     * @return LoveCity 喜欢的一个城市
     */
    public static LoveCity getLoveCity(Integer order){
        return  MyApplication.
                getDaoSession().
                getLoveCityDao().
                queryBuilder().
                where(LoveCityDao.Properties.Order.eq(order)).list().get(0);
    }

    /**
     * 无参数，得到所有喜欢的城市
     * @return List<LoveCity> 喜欢城市的集合
     */
    public static List<LoveCity> getLoveCity(){

        return MyApplication.
                getDaoSession().
                getLoveCityDao().
                queryBuilder().
                orderAsc(LoveCityDao.Properties.Order).
                list();

    }

    /**
     * 插入喜欢的城市
     * @param loveCity 喜欢的城市
     */
    public static void insertLoveCity(LoveCity loveCity) {
        MyApplication.getDaoSession().getLoveCityDao().insert(loveCity);
    }
}
