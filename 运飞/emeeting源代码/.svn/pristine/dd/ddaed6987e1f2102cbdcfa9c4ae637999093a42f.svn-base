//
//  EMMMsgCenter.h
//  EMMMsgCenter
//
//  Created by lirui on 15/6/30.
//  Copyright (c) 2015年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
enum
{
    WS_CONNECTING = 0,
    WS_OPEN,
    WS_CLOSING,
    WS_CLOSED,
    WS_REGISTERED,
    UnKnowWSStatus = 0xff,
};

@protocol EMMMsgCenterDelegate;

@interface EMMMsgCenter : NSObject

@property (nonatomic,weak)id<EMMMsgCenterDelegate> delegate;


/**
 *  获取单例对象
 *
 *  @return 单例对象
 */
+ (EMMMsgCenter *)share;

- (void)setUserData:(NSString *)userId withSSOToken:(NSString *)stk withDeviceId:(NSString *)deviceId withAppId:(NSString *)appId;

- (void)reconnect;

- (int)getWebSocketStatus;

/**
 *   切换用户后需要调用close来注销
 */
- (void)close;


- (void)applicationDidEnterBackground;

- (void)applicationDidBecomeActive;

- (void)didRegisterPushToken:(NSData *)deviceToken;


@end

@protocol EMMMsgCenterDelegate <NSObject>

@required

/**
 *  接收到服务器的推送消息
 *
 *  @param pushMsg
 */
- (void)receviedPushMsg:(NSString *)pushMsg;

@optional


- (void)didFailWithError:(NSError *)error;

- (void)didCloseWithCode:(NSString *)reason;


@end


