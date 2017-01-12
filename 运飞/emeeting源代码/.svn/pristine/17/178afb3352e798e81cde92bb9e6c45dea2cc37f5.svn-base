//
//  EMMSecurity.h
//  EMMSecurity
//
//  Created by clement on 14-4-30.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "EMMNetworking/EMMNetworking.h"
enum
{
    TokenVerify = 0,
    EncryptionVerify,
    PasswordVerify,
    SSOVerify,
    UnKnowVerify = 0xff,
};

enum
{
    TokenNotFound = 0,
    TokenInvalidate,
    InvalidAppId,
    DeviceHaveRegistered,
    EncryptionError,
    PasswordError,
    DeviceRegisterError,
    TimeOutError,
    UnKnowError = 0xff,
};

enum
{
    TokenNull = 0,
    TokenEnable,
    TokenDisable,
    UnKnowStatus = 0xff,
};

typedef enum {
    Token_Method = 0,
    Encryption_Method,
    ReloadLogin_Method,
    Password_Method,
    Unknow_Mehtod = 0xff
}SSOVerifyMethod;

@protocol EMMSecurityVerifyDelegate;

@interface EMMSecurity: NSObject

@property (nonatomic,weak)id<EMMSecurityVerifyDelegate> delegate;

@property (nonatomic,strong,readonly)NSString   *appId;
@property (nonatomic,strong,readonly)NSString   *token;
@property (nonatomic,strong,readonly)NSString   *userId;
@property (nonatomic,strong,readonly)NSString   *deviceId;
@property (nonatomic,strong,readonly)NSString    *encryption;
@property (nonatomic,strong,readonly)NSString    *timestamp;
@property (nonatomic,strong,readonly)NSDictionary *userInfo;
@property (nonatomic,assign)BOOL    userInfoFlag;//默认获取用户信息
@property (nonatomic,assign,readonly)SSOVerifyMethod    ssoVerifyMethod;
@property (nonatomic,strong)NSString    *currentMoaCode;//设置当前moacode
@property (nonatomic,strong,readonly)EMMHTTPManager          *httpManager;
/**
 *  获取单例对象
 *
 *  @return 单例对象
 */
+ (EMMSecurity *)share;

/**
 *  程序第一次启动时设置EMM平台给每个app分配的appId
 *
 *  @param appID EMM平台提供的appId
 */
- (void)applicationLaunchingWithAppId:(NSString *)appID;

/**
 *  app退出后台
 */
- (void)applicationDidEnterBackground;

/**
 *  app激活到前台 触发自动登录操作
 */
- (void)applicationDidBecomeActive;

/**
 *  app处理回调url
 *
 *  @param url 回调url
 *
 *  @return 是否处理url回调
 */
- (BOOL)applicationHandleOpenURL:(NSURL *)url;

/**
 *  使用账号密码登录
 *
 *  @param userID   用户id
 *  @param password 密码
 */
- (void)loginWithUserId:(NSString *)userID password:(NSString *)password;

/**
 *  当token失效时，主动调用自动登录
 */
- (void)reloadLogin;

/**
 *  注销登录
 */
- (void)logOut;

/*
 * 检查token状态 0：Token为空  1：Token有效  2：Token无效
 */
- (int)CheckTokenStatus;

/**
 *  跳转到rootApp(moa)
 */
- (BOOL)jumpIntoRootApp;

@end

@protocol EMMSecurityVerifyDelegate <NSObject>

@required

/**
 *  安全认证成功
 *
 *  @param verifyType (TokenVerify = 0,EncryptionVerify,PasswordVerify,SSOVerify)
 */
- (void)securityVerifySuccess:(NSInteger)verifyType;

@optional

/**
 *  安全认证失败
 *
 *  @param errorCode 错误码
 *
 *  TokenNotFound       (TokenNotFound = 0)没有读取到token 如果是MOA需要跳转到登录界面，如果是其他程序自动跳转到MOA去登录
 *  TokenInvalidate     读取到token 校验后token失效
 *  InvalidAppId        无效的appId
 *  DeviceHaveRegistered 设备已经被注册
 *  DeviceRegisterError  设备注册失败
 *  EncryptionError     加密串校验失败
 *  PasswordError       用户名密码校验失败
 *  UnKnowError         未知错误
 *
 *  @param errorMsg  错误提示语
 */
- (void)securityVerifyFailureWithCode:(NSInteger)errorCode errorMsg:(NSString *)errorMsg;

/**
 *  启动登录界面  单点app需要实现这个方法显示登录界面
 */
- (void)goToLoginPage;


@end