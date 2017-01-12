//
//  AppDelegate.m
//  RedPacket
//
//  Created by itp on 15/10/21.
//  Copyright © 2015年 itp. All rights reserved.
//

#import "AppDelegate.h"
#import "EMMSecurity/EMMSecurity.h"
#import "LogInViewController.h"
#import "EMMAppUpdate.h"
#import "NetworkingManager.h"
#import "MBProgressHUD.h"
#import "GetLastUpdatetimeHandle.h"
#import "SysMeetingRoomAddressHandle.h"
#import "SysMeetingRoomInfoHandle.h"
#import "FMDBManager.h"
#import "MeetingInfoManager.h"
#import "MarkedWords.h"
#import "EMMMsgCenter.h"
#import "NotifyInfo.h"
#import <BaiduMapAPI_Base/BMKBaseComponent.h>
#import "MyMeetingViewController.h"
#import "AddValueViewController.h"
#import "LeftMuneViewController.h"
#import "CustomStatueBar.h"
static BOOL fistLaunching = NO;
static BOOL theOnes = NO;

@interface AppDelegate ()<EMMSecurityVerifyDelegate,EMMMsgCenterDelegate>
{
    BMKMapManager* _mapManager;
    NSDictionary *notifDic;
    CustomStatueBar *customStatueBar;
}
@property (nonatomic, strong) MMDrawerController *drawerController;

@property (nonatomic, strong) GetLastUpdatetimeHandle *lastUpdateHandle;

@property (nonatomic, strong) SysMeetingRoomAddressHandle *roomAddressHandle;

@property (nonatomic, strong) SysMeetingRoomInfoHandle *roomInfoHandle;

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.isFirstLaunching = YES;
    theOnes = YES;
    self.isDoneUpdate = NO;
    
    _mapManager = [[BMKMapManager alloc]init];
    BOOL ret = [_mapManager start:@"AqAisBM9S2Arex2xjOO1TGpwZiSlpe2h"  generalDelegate:nil];
    if (!ret)
    {
        NSLog(@"manager start failed!");
    }
    NSLog(@"UILocalNotificationDefaultSoundName=%@",UILocalNotificationDefaultSoundName);
    
    // Override point for customization after application launch.

//    [[EMMSecurity share] logOut];

    //[EMMNetworkSetting share].networking = EMMNetworking_Intranet;
    [[EMMSecurity share] applicationLaunchingWithAppId:EMMAppID];
    [EMMSecurity share].delegate = self;
    [[EMMAppUpdate share] checkAppVersion:EMMAppID withSSOToken:[EMMSecurity share].token];
    UIUserNotificationType types = UIUserNotificationTypeBadge |
    UIUserNotificationTypeSound | UIUserNotificationTypeAlert;
    
    UIUserNotificationSettings *mySettings = [UIUserNotificationSettings settingsForTypes:types categories:nil];
    
    [[UIApplication sharedApplication] registerUserNotificationSettings:mySettings];
    
    [[UIApplication sharedApplication] registerForRemoteNotifications];
    
        NSLog(@"EMeeting launching");
    fistLaunching = YES;
    
    
    UILocalNotification * localNotify = [launchOptions objectForKey:UIApplicationLaunchOptionsLocalNotificationKey];
    if(localNotify){
        [self dealNotifyInfo:localNotify.userInfo];
    }
    
    NSDictionary * userInfo = [launchOptions objectForKey:UIApplicationLaunchOptionsRemoteNotificationKey];
    if(userInfo){
       
        [self dealNotifyInfo:[self handleOctData:userInfo]];
    }
    
    //[[UIApplication sharedApplication] cancelAllLocalNotifications];
    return YES;
}

-(void)jj{
    UIStoryboard *board=[UIStoryboard storyboardWithName:@"Main" bundle:nil];
    NSLog(@"%@",[board instantiateViewControllerWithIdentifier:@"MMDrawerController"]);
    self.drawerController=[board instantiateViewControllerWithIdentifier:@"MMDrawerController"];
//    self.drawerController = (MMDrawerController *)self.window.rootViewController;
    UIStoryboard *centerStoryboard = [UIStoryboard storyboardWithName:@"MeetingSchedule" bundle:nil];
    [self.drawerController setCenterViewController:centerStoryboard.instantiateInitialViewController];
    UIStoryboard *leftStoryboard = [UIStoryboard storyboardWithName:@"LeftMune" bundle:nil];
    [self.drawerController setLeftDrawerViewController:leftStoryboard.instantiateInitialViewController];
    
    [self.drawerController setShouldStretchDrawer:NO];
    [self.drawerController setShowsShadow:YES];
    [self.drawerController setMaximumLeftDrawerWidth:260.0];
    [self.drawerController setCloseDrawerGestureModeMask:MMCloseDrawerGestureModeAll];
    self.window.rootViewController=self.drawerController;

}

- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    theOnes = YES;
    [[EMMSecurity share] applicationDidEnterBackground];
    [[EMMMsgCenter share] applicationDidEnterBackground];
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    //[self firstLaunchingloadinghud];
    //theOnes = YES;
    if (theOnes)
    {
        NSLog(@"applicationDidBecomeActive");
        theOnes = NO;
        [[EMMSecurity share] applicationDidBecomeActive];
        [[EMMMsgCenter share] applicationDidBecomeActive];
    }
}
- (BOOL)application:(UIApplication *)application handleOpenURL:(NSURL *)url
{
    NSLog(@"HandleOpenUrl: %@",url);
    return [[EMMSecurity share] applicationHandleOpenURL:url];
}

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
    NSLog(@"token:%@",deviceToken);
//    NSString *string=@"<3171a307 23d8f6f3 e6342a36 afc35174 66a24cc7 708e4cbe adf14cee 4111edd2>";
//    NSData *token=[string dataUsingEncoding:NSUTF8StringEncoding];
    [[EMMMsgCenter share] didRegisterPushToken:deviceToken];
}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
{
    NSLog(@"receiveRemoteNotifcation:%@",userInfo);
    if (application.applicationState == UIApplicationStateActive) {
        
    }else{
        
        [self dealNotifyInfo:[self handleOctData:userInfo]];
    }

}
- (void)application:(UIApplication *)application didReceiveLocalNotification:(UILocalNotification *)notif {
    //application.applicationIconBadgeNumber = notif.applicationIconBadgeNumber-1;
    //[self dealNotifyInfo:notif.userInfo];
    if (application.applicationState == UIApplicationStateActive) {
        NSLog(@"12116");
    }else{
      [self dealNotifyInfo:notif.userInfo];
    }
}

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error{
    
    NSLog(@"Regist fail%@",error);
}
- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    NSLog(@"over....");
}

#pragma mark SecurityVerifyDelegate

/**
 *  安全认证失败
 *
 *  @param errorCode 错误码
 *
 *  TokenNotFound = 0   没有读取到token 如果是MOA需要跳转到登录界面，如果是其他程序自动跳转到MOA去登录
 *  TokenInvalidate     读取到token 校验后token失效
 *  EncryptionError     加密串校验失败
 *  PasswordError       用户名密码校验失败
 *
 *  @param errorMsg  错误提示语
 */
- (void)securityVerifyFailureWithCode:(NSInteger)errorCode errorMsg:(NSString *)errorMsg
{
    NSLog(@"errorMsg == %@",errorMsg);
    NSLog(@"securityVerifyFailure,errorCode =%ld,errorMsg = %@",(long)errorCode,errorMsg);
//    [loadinghud hide:YES];
//    fistLaunching = NO;
    if (errorCode == PasswordError)
    {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"密码错误" delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil];
        [alert show];
        [alert dismissWithClickedButtonIndex:0 animated:YES];
    }
    else if ([errorMsg length] > 0)
    {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:errorMsg delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil];
        [alert show];
        [alert dismissWithClickedButtonIndex:0 animated:YES];
    }
}

/**
 *  安全验证成功
 *
 *  @param verifyType (TokenVerify = 0,EncryptionVerify,PasswordVerify,SSOVerify)
 */
- (void)securityVerifySuccess:(NSInteger)verifyType
{
    if ( verifyType == TokenVerify || verifyType == EncryptionVerify || verifyType == PasswordVerify) {
        
        //注意需要做内外网判断
        [EMMNetworkSetting share].networking = EMMNetworking_Internet;
        
        [[EMMMsgCenter share] setUserData:[EMMSecurity share].userId withSSOToken:[EMMSecurity share].token withDeviceId:[EMMSecurity share].deviceId withAppId:[EMMSecurity share].appId];
        [EMMMsgCenter share].delegate = self;
        [[EMMMsgCenter share] reconnect];
        NSLog(@"%d",[[EMMMsgCenter share]getWebSocketStatus]);
    }

    
    NSLog(@"securityVerifySuccess verifyType = %ld",(long)verifyType);

    if ( verifyType == TokenVerify || verifyType == EncryptionVerify)
    {
        self.tokenType = @"02";
    }
    else if (verifyType == PasswordVerify)
    {
        self.tokenType = @"01";
        
        [self.window.rootViewController dismissViewControllerAnimated:YES completion:nil];
    }
    
    if (self.isFirstLaunching)
    {
        [self sendGetLastUpdatTimeRequest];
    }else
    {
        [[NSNotificationCenter defaultCenter] postNotificationName:@"loginSuccessToGetPlace" object:nil];
    }

}

//- (void)goToLoginPage
//{
//    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
//    LogInViewController *loginViewController = (LogInViewController *)[storyboard instantiateViewControllerWithIdentifier:@"LogInViewController"];
//   [self.window.rootViewController presentViewController:loginViewController animated:YES completion:nil];
//}

#pragma mark-获取信息更新时间
- (void)sendGetLastUpdatTimeRequest
{
     NSString *roomAddressUpDate = [[NSUserDefaults standardUserDefaults] objectForKey:@"SysMeetingRoomAddress"]?[[NSUserDefaults standardUserDefaults] objectForKey:@"SysMeetingRoomAddress"]:@"";
    
    NSString *roomInfoUpDate = [[NSUserDefaults standardUserDefaults] objectForKey:@"SysMeetingRoomInfo"]?[[NSUserDefaults standardUserDefaults] objectForKey:@"SysMeetingRoomInfo"]:@"";
    
    __weak typeof(self) wself = self;
    
    if (_lastUpdateHandle == nil)
    {
        _lastUpdateHandle = [[GetLastUpdatetimeHandle alloc] init];
    }
    
    [_lastUpdateHandle sendJSONRequestWithSuccess:^(ResponseObject *response)
     {
         if (response.s)
         {
            [[NSNotificationCenter defaultCenter] postNotificationName:@"loginSuccessToGetPlace" object:nil];
             
             NSDictionary *dict = (NSDictionary *)[(NSArray *)response.d firstObject];
             
             if (![roomAddressUpDate isEqualToString:dict[@"LDT"]])
             {
                 [wself sendGetMeetingRoomAddressRequestWithLastUpDateTime:roomAddressUpDate.length == 0?roomAddressUpDate:dict[@"LDT"] andSaveTime:dict[@"LDT"]];
             }else
             {
                 wself.isDoneUpdate = YES;
                 [[NSNotificationCenter defaultCenter] postNotificationName:@"DoneUpdate" object:nil];
             }
             
             NSDictionary *dict1 = (NSDictionary *)[(NSArray *)response.d lastObject];
             if (![roomInfoUpDate isEqualToString:dict1[@"LDT"]])
             {
                 [wself sendGetMeetingRoomInfoRequestWithLastUpDateTime:roomInfoUpDate.length==0?roomInfoUpDate:dict1[@"LDT"] andSaveTime:dict1[@"LDT"]];
             }
         }else
         {
             if (roomAddressUpDate.length > 0)
             {
                 wself.isDoneUpdate = YES;
                 [[NSNotificationCenter defaultCenter] postNotificationName:@"DoneUpdate" object:nil];
                 [[NSNotificationCenter defaultCenter] postNotificationName:@"loginSuccessToGetPlace" object:nil];
             }else
             {
                 [wself sendGetLastUpdatTimeRequest];
             }
         }
     } failure:^(NSError *error)
     {
         if (roomAddressUpDate.length > 0)
         {
             wself.isDoneUpdate = YES;
             [[NSNotificationCenter defaultCenter] postNotificationName:@"DoneUpdate" object:nil];
             [[NSNotificationCenter defaultCenter] postNotificationName:@"loginSuccessToGetPlace" object:nil];
         }else
         {
             [wself sendGetLastUpdatTimeRequest];
         }
     }];
}

#pragma mark -获取会议室地址信息
- (void)sendGetMeetingRoomAddressRequestWithLastUpDateTime:(NSString *)updateTime andSaveTime:(NSString *)saveTime
{
    __weak typeof(self) wself = self;
    
    if (_roomAddressHandle == nil)
    {
        _roomAddressHandle = [[SysMeetingRoomAddressHandle alloc] init];
    }
    
    [_roomAddressHandle sendJSONRequestWithLastUpDateTime:updateTime  Success:^(ResponseObject *response)
     {
         if (response.s)
         {
             
             [[FMDBManager shareInstance] addSysMeetingRoomAddressInfoWithDataArr:(NSArray *)response.d susscess:^
             {
                 NSLog(@"成功...");
                 wself.isDoneUpdate = YES;
                 [[NSUserDefaults standardUserDefaults] setObject:saveTime forKey:@"SysMeetingRoomAddress"];
                 [[NSUserDefaults standardUserDefaults] synchronize];
                 
                 [[NSNotificationCenter defaultCenter] postNotificationName:@"DoneUpdate" object:nil];
             } faiure:^
             {
                 NSLog(@"失败...");
                 [[NSNotificationCenter defaultCenter] postNotificationName:@"UpdateError" object:nil];
             }];
         }
     } failure:^(NSError *error)
     {
         [MarkedWords showMarkedWordsWithMessage:@"网络错误,无法更新会议地址信息" addToView:wself.window];
         [[NSNotificationCenter defaultCenter] postNotificationName:@"UpdateError" object:nil];
     }];
}

#pragma mark -获取会议室基本信息

static int roomIndex = 0;

- (void)sendGetMeetingRoomInfoRequestWithLastUpDateTime:(NSString *)updateTime andSaveTime:(NSString *)saveTime
{
    __weak typeof(self) wself = self;
    
    if (_roomInfoHandle == nil)
    {
        _roomInfoHandle = [[SysMeetingRoomInfoHandle alloc] init];
    }
    
    [_roomInfoHandle sendJSONRequestWithLastUpDateTime:updateTime  Success:^(ResponseObject *response)
     {
         if (response.s)
         {
             [[MeetingInfoManager shareInstance] addSysMeetingRoomInfoWithDataArr:(NSArray *)response.d susscess:^
             {
                 NSLog(@"=============成功===============");
                 [[NSUserDefaults standardUserDefaults] setObject:saveTime forKey:@"SysMeetingRoomInfo"];
                 [[NSUserDefaults standardUserDefaults] synchronize];
             } faiure:^
             {
                 
             }];
         }else
         {
             [wself loadRoomInfoFaiuleWithLastUpDateTime:updateTime andSaveTime:saveTime];
         }
     } failure:^(NSError *error)
     {
         
         [wself loadRoomInfoFaiuleWithLastUpDateTime:updateTime andSaveTime:saveTime];
     }];
}

- (void)loadRoomInfoFaiuleWithLastUpDateTime:(NSString *)updateTime andSaveTime:(NSString *)saveTime
{
    if (roomIndex < 5)
    {
        [self sendGetMeetingRoomInfoRequestWithLastUpDateTime:updateTime andSaveTime:saveTime];
        
        roomIndex++;
    }else
    {
        [MarkedWords showMarkedWordsWithMessage:@"更新会议室信息失败" addToView:self.window];
    }
}

#pragma mark EMMMsgCenterDelegate

- (void)receviedPushMsg:(NSString *)pushMsg
{
    
    
    NSLog(@"Received the pushMsg: %@",pushMsg);
    dispatch_async(dispatch_get_main_queue(), ^{
        AudioServicesPlaySystemSound(1007);
            customStatueBar=[[CustomStatueBar alloc]initWithFrame:CGRectMake(0, -40, APPW, 40)];
        
        NSData *jsonData = [pushMsg dataUsingEncoding:NSUTF8StringEncoding];
        NSError *err;
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingMutableContainers error:&err];
        NSDictionary *dataDic=[self handleData:dic];
        //NSDictionary *nctDataDic=[self handleOctData:dic];
         [customStatueBar showStatusMessage:dic[@"Data"][@"NCT"][@"Alert"]];
        // 1.创建本地通知
        UILocalNotification *localNote = [[UILocalNotification alloc] init];
        
        // 2.设置本地通知的内容
        // 2.1.设置通知发出的时间
        localNote.fireDate = [NSDate dateWithTimeIntervalSinceNow:0];
        // 2.2.设置通知的内容
       
        localNote.alertBody = dic[@"Data"][@"NCT"][@"Alert"];
        // 2.3.设置滑块的文字（锁屏状态下：滑动来“解锁”）
        localNote.alertAction = NSLocalizedString(dic[@"Data"][@"NCT"][@"Alert"], nil);
        // 2.4.决定alertAction是否生效
        localNote.hasAction = YES;
        // 设置时区（此为默认时区）
        localNote.timeZone = [NSTimeZone defaultTimeZone];
        // 设置重复间隔（默认0，不重复推送）
          localNote.repeatInterval = 0;
        // 2.5.设置点击通知的启动图片
        //localNote.alertLaunchImage = @"123Abc";
        // 2.6.设置alertTitle
        //localNote.alertTitle = @"你有一条新通知";
        // 2.7.设置有通知时的音效
        localNote.soundName = UILocalNotificationDefaultSoundName;
        // 2.8.设置应用程序图标右上角的数字
        //localNote.applicationIconBadgeNumber = 0;
        
        // 2.9.设置额外信息
        localNote.userInfo =dataDic;
        
        [[UIApplication sharedApplication] scheduleLocalNotification:localNote];
        //[UIApplication sharedApplication].applicationIconBadgeNumber++;
        
    });
    
}

-(NSDictionary *)handleData:(NSDictionary *)dic{
    NSDictionary *dataDic;
    if(dic){
        NSError *err;
        NSString *dataString=dic[@"Data"][@"MCT"];
        NSData *jsonString = [dataString dataUsingEncoding:NSUTF8StringEncoding];
        dataDic = [NSJSONSerialization JSONObjectWithData:jsonString options:NSJSONReadingMutableContainers error:&err];
    }
    return dataDic;
}

-(NSDictionary *)handleOctData:(NSDictionary *)dic{
    NSDictionary *dataDic;
    if(dic){
        NSError *err;
        NSString *dataString=dic[@"OCT"];
        NSData *jsonString = [dataString dataUsingEncoding:NSUTF8StringEncoding];
        dataDic = [NSJSONSerialization JSONObjectWithData:jsonString options:NSJSONReadingMutableContainers error:&err];
 
    }
    return dataDic;
}
- (void)didFailWithError:(NSError *)error
{
    NSLog(@"EMMMsgCenter didFail error:%@",error);
    dispatch_async(dispatch_get_main_queue(), ^{
//        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"在线消息" message:@"在线消息发送错误" delegate:nil cancelButtonTitle:@"ok" otherButtonTitles:nil];
//        [alert show];
        
    });
    
}

- (void)didCloseWithCode:(NSString *)reason
{
    NSLog(@"EMMMsgCenter didClose by %@",reason);
    dispatch_async(dispatch_get_main_queue(), ^{
//        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"在线消息" message:@"关闭在线消息" delegate:nil cancelButtonTitle:@"ok" otherButtonTitles:nil];
//        [alert show];
    });
    
}
-(void)dealNotifyInfo:(NSDictionary *)notify{
    if (_isDoneUpdate) {
        [self.drawerController closeDrawerAnimated:NO completion:nil];
        [[NSNotificationCenter defaultCenter] postNotificationName:@"loginSuccessToGetPlace" object:nil];
        
//        if([UIApplication sharedApplication].applicationIconBadgeNumber!=0){
//            [UIApplication sharedApplication].applicationIconBadgeNumber--;
//        }
       
        NotifyInfo *notifyInfo=[[NotifyInfo alloc]initWithDictionary:notify];
        
        if ([@"0" isEqualToString:notifyInfo.notifyType]) {
            if ([@"0" isEqualToString:notifyInfo.meetingNotifyType] || [@"2" isEqualToString:notifyInfo.meetingNotifyType]) {
                [[NSNotificationCenter defaultCenter] postNotificationName:SetChangeColorNoti object:@(3)];
                UIStoryboard *centerStoryboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
                MyMeetingViewController *myMeetingViewController = [centerStoryboard instantiateViewControllerWithIdentifier:@"MyMeetingViewController"];
                myMeetingViewController.meeingId=notifyInfo.meetingID;
                myMeetingViewController.meeingType=notifyInfo.meetingType;
                UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:myMeetingViewController];
                navigationController.navigationBar.hidden=YES;
                [self.drawerController setCenterViewController:navigationController];
            }else if([@"1" isEqualToString:notifyInfo.meetingNotifyType]){
                [[NSNotificationCenter defaultCenter] postNotificationName:SetCenterControllerNoti object:@(0)];
            }
        }else if([@"1" isEqualToString:notifyInfo.notifyType]){
            [[NSNotificationCenter defaultCenter] postNotificationName:SetChangeColorNoti object:@(4)];
                UIStoryboard *centerStoryboard = [UIStoryboard storyboardWithName:@"AddValue" bundle:nil];
                AddValueViewController *addValueViewController = [centerStoryboard instantiateViewControllerWithIdentifier:@"AddValueViewController"];
                addValueViewController.valueErverNotifyType=[notifyInfo valueErverNotifyType];
                UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:addValueViewController];
                navigationController.navigationBar.hidden=YES;
                [self.drawerController setCenterViewController:navigationController];

        }
    }else{
        [self dealNotifyInfo:notify];
    }
}
@end
