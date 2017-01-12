//
//  EMMAppUpdate.h
//  EMMAppUpdate
//
//  Created by clement on 14-6-16.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef enum
{
    AppVersionStatu_AlreadyNew,  //已经是最新版本
    AppVersionStatu_HaveNew,     //有新版本
    AppVersionStatu_ForceUpdate, //强制更新版本
    AppVersionStatu_UnKnow = 0xff//未知版本
} AppVersionStatu;

@protocol EMMAppUpdateDelegate <NSObject>

@optional

- (void)finishedCheckAppVersion:(BOOL)success update:(BOOL)update;

- (void)finishedCheckAppVersion:(BOOL)success appversionStatu:(AppVersionStatu)statu;

@end

@interface EMMAppUpdate : NSObject
@property (nonatomic,weak)id delegate;

+ (EMMAppUpdate *)share;

/* modified by lirui in 2015-05-19 */
- (void)checkAppVersion:(NSString *)appId withSSOToken:(NSString *)token;

- (void)updateVersion;

@end
