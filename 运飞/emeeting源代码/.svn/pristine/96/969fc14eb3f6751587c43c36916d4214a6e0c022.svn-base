//
//  EMMDownloadConfig.h
//  EMMNetworking
//
//  Created by clement on 14-7-2.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import <Foundation/Foundation.h>

#define DownloadConfigKey        @"checkversion"

@class EMMHTTPRequestOperation;
@class EMMHTTPManager;

@interface EMMDownloadConfig : NSObject
@property (nonatomic,strong,readonly)NSString    *PPIn;//应用包内网下载路径前缀
@property (nonatomic,strong,readonly)NSString    *PPOu;//应用包外网下载路径前缀
@property (nonatomic,strong,readonly)NSString    *IPIn;//应用图标内网下载路径前缀
@property (nonatomic,strong,readonly)NSString    *IPOu;//应用图标外网下载路径前缀
@property (nonatomic,assign,readonly)BOOL       success;//是否成功获取下载配置

+ (EMMDownloadConfig *)share;

- (EMMHTTPRequestOperation *)getServiceConfigOperation:(EMMHTTPManager *)httpManager;

@end
