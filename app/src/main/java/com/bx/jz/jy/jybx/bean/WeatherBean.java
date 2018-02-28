package com.bx.jz.jy.jybx.bean;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class WeatherBean {

    private String msg;
    private WeatherBeanBean weatherBean;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WeatherBeanBean getWeatherBean() {
        return weatherBean;
    }

    public void setWeatherBean(WeatherBeanBean weatherBean) {
        this.weatherBean = weatherBean;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class WeatherBeanBean {

        private WeatherinfoBean weatherinfo;

        public WeatherinfoBean getWeatherinfo() {
            return weatherinfo;
        }

        public void setWeatherinfo(WeatherinfoBean weatherinfo) {
            this.weatherinfo = weatherinfo;
        }

        public static class WeatherinfoBean {

            private String city;
            private String cityid;
            private String img1;
            private String img2;
            private String ptime;
            private String temp1;
            private String temp2;
            private String weather;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCityid() {
                return cityid;
            }

            public void setCityid(String cityid) {
                this.cityid = cityid;
            }

            public String getImg1() {
                return img1;
            }

            public void setImg1(String img1) {
                this.img1 = img1;
            }

            public String getImg2() {
                return img2;
            }

            public void setImg2(String img2) {
                this.img2 = img2;
            }

            public String getPtime() {
                return ptime;
            }

            public void setPtime(String ptime) {
                this.ptime = ptime;
            }

            public String getTemp1() {
                return temp1;
            }

            public void setTemp1(String temp1) {
                this.temp1 = temp1;
            }

            public String getTemp2() {
                return temp2;
            }

            public void setTemp2(String temp2) {
                this.temp2 = temp2;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }
        }
    }
}
