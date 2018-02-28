package com.bx.jz.jy.jybx;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class ConstantPool {

    public static final int FridgeId = 1;
    public static final int UserID = 12345;


    private static final String BASE_URL = "http://112.124.102.114:8090/imgTest/";
//    public static final String BASE_URL_RMS = "http://apitest.jouyoung.com:8389/rms/v1/app/register?action=register";//注册
    public static final String BASE_URL_RMS = "http://apitest.jouyoung.com:8389/rms/v1/app/login?action=login";//注册

//    public static final String BASE_URL = "http://192.168.199.188:8080/imgTest/";

    private static final String TEST_URL = "http://apitest.joyoung.com:8389";

    public static final String USER_SHARE = "login_share";
    public static final String CITYCODE = "101210101";
    public static final String SUCCESS = "1";

    public static String GOODSLIST = BASE_URL + "in/ingredients!list";//菜品列表
    public static String GOODSRECOMMEND = BASE_URL + "in/img!getRecipeImgs";//菜品列表
    public static String WEATHER = BASE_URL + "in/ingredients!getWeatherBeanByCityCode";//天气
    public static String DELETEFOODS = BASE_URL + "in/ingredients!delete";//删除菜品列表item
    public static String NEWPHOTO = BASE_URL + "in/img!newlist";//获取最近4张照片
    public static String ALBUM = BASE_URL + "in/img!list";//获取图片集合
    public static String SIMILAR = BASE_URL + "in/ingredients!similar";//模糊查询食材名字
    public static String saveOrUpdate = BASE_URL + "in/ingredients!saveOrUpdate";//食材新增/修改接口
    public static String FridgeInfo = BASE_URL + "in/refrigerator!getinfo";//冰箱信息
    public static String nutritionById = BASE_URL + "web/recipe!nutritionById";//获取营养功效
    public static String herbs = BASE_URL + "web/recipe!herbs";//获取饮食宜忌


    public static String GETNEWCODE = TEST_URL + "/rms/v1/common/vcode?action=getNewCode";//获取登录验证码
    public static String MOBILELOGIN = TEST_URL + "/rms/v1/app/login?action=mobileLogin";//获取登录验证码


    public static String phonenumber = "15823427797";
    public static String passwd = "123456";
    public static String devTypeId = "18432";
    public static String devId = "379e548b5ade4afabf29343d2067c348";
    public static String cmd = "CC00000000000D00000000B000060001000000000000";

    public static final byte Zero = 0x00;
    public static final byte Default = 10;
    public static final byte Data0_beginning_commend = 0x55;
    public static final byte Data1_beginning_commend = (byte) 0xAA;
    public static final byte Data2_Modify_Temperature = 0x01;
    public static final byte Data2_Modify_Mode = 0x02;
    public static final byte Data2_Setting_time = 0x03;
    public static final byte Data2_Running_State = 0x04;
    public static final byte Data2_System_Timing = 0x05;
    public static final byte Data2_Remote_Maintenance = 0x06;
    public static final byte Intelligent_Model = 0x01;// 智能模式
    public static final byte LengCang_Shutdown_Model = 0x02;// 冷藏关闭
    public static final byte Holiday_Mode = 0x04;// 假日
    public static final byte BianWen_Shutdown_Model = 0x08;// 关闭变温
    public static final byte Quick_Freezing_Mode = 0x10;// 速冻
    public static final byte Quick_Cooling_Mode = 0x20;// 速冷
    public static final byte Child_Lock_Mode = 0x40;
    public static final byte LECO_Mode = (byte) 0x80;

}
