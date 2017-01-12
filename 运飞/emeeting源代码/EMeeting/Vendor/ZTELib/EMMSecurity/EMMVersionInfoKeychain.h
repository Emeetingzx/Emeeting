//
//  EMMVersionInfo.h
//  EMMSecurity
//
//  Created by clement on 14-5-13.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import "EMMShareKeychain.h"


@interface EMMVersionInfo : NSObject<NSCoding>
@property(nonatomic,strong)NSString      *appbundleIdentifier;
@property(nonatomic,strong)NSString      *appId;
@property(nonatomic,strong)NSString      *appIdentifier;
@property(nonatomic,strong)NSString      *appScheme;
@property(nonatomic,strong)NSString      *appVersion;

@end

@interface EMMVersionInfoKeychain : EMMShareKeychain
@property(nonatomic,strong)EMMVersionInfo      *versionInfo;

+ (EMMVersionInfoKeychain *)versinInfoWithAppbundleIdentifier:(NSString *)appbundleIdentifier;

@end


@interface EMMVersionInfoManager : NSObject
/**
 *  从keychain读取系统所有版本
 *
 *  @return 返回VersionInfo 对象数组
 */
+ (NSArray *)readVersionInfosFromKeyChain;

@end