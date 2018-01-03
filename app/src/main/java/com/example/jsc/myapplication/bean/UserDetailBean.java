package com.example.jsc.myapplication.bean;

import java.io.Serializable;

/**
 * 个人信息和高级设置需要的详细信息
 * Created by jsc on 2017/4/5.
 */

public class UserDetailBean implements Cloneable, Serializable {
    private static final long serialVersionUID = 7190969606810279340L;
    /**
     * answerPrice : 20.98
     * introduction : 北大牛人 就不考虑考虑考虑考虑了她一下就要好好珍惜身边人吧！测试电文！测试一下自己的一些酒喝的是一个人了！测试电文！测试电文！测试电文！测试一下你的心也在这里举行的记者招待会上宣布的人是谁在一起了！测试电文！测试一下自己的心去爱别人
     * isAnswer : 0
     * microblogUrl : http://m.weibo.cn/绝对经典剧
     * mobile : 18211870590
     * name : 董一夫测试下
     * picUrl : https://testpic-1252376469.costj.myqcloud.com/headImage/iOS_1474630624898337_UserIcon_1491377976086
     * qqChatUrl :
     * uid : 1474630624898337
     * wechatPublicNoUrl : https://testpic-1252376469.costj.myqcloud.com/weixinQrcode/iOS_1474630624898337_UserIcon_1491377943341
     */

    public String answerPrice;//一对一问答价格
    public String introduction;//个人简介
    public String isAnswer;//是否回答
    public String isAcceptNote;
    public String flag;//1:更改高级设置;0:个人信息
    public String isAcceptPl;
    public String isAcceptPlNotice;
    public String microblogUrl;//微博主页地址
    public String screenName;//微博名称
    public String mobile;//手机号
    public String name;//用户名
    public String picUrl;//头像
    public String qqChatUrl;
    public String uid;
    public String wechatPublicNoUrl;//微信二维码地址
    public String is_inner;//是否是内测
    public String is_employee;//是否是公司员工
    public String appversion;//版本号
    public boolean is_can_create_child;  //是否允许开启账号管理权限
    public boolean isBelongInstitution;  //是否属于机构
    public int countFollow;//粉丝数
    public boolean isChum;//是否成为密友
    public int isAcceptConsultOrder;//是否接受咨询下单


    public int isAcceptConsult;//是否开启电话咨询服务

    @Override
    public UserDetailBean clone() {
        UserDetailBean stu = null;
        try {
            stu = (UserDetailBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }
}
