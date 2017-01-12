//
//  EMMBaseUrlManager.h
//  EMMNetworking
//
//  Created by clement on 14-6-16.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import <Foundation/Foundation.h>

enum
{
    MsgHttpHeader = 0,//http
    MsgHttpsHeader,//https
    MsgWSHeader,//ws
    MsgWSSHeader,//wss
    UnKnowMsgHeader = 0xff,
};

extern NSString * const EMMSSOConfigKey; //EMMSSO

extern NSString * const EMMMSGConfigKey; //EMMMSG

@interface EMMBaseUrlManager : NSObject

- (id)initWithServer:(NSString *)server;

- (NSString *)intranetServerHost;

- (NSString *)internetServerHost;

- (NSString *)intranetServerPort;

- (NSString *)internetServerPort;

- (NSString *)intranetServerBaseUrl;

- (NSString *)internetServerBaseUrl;

- (NSString *)headerType;

+ (NSString *)rootAppUrlSchema;

+ (NSString *)rootAppUrlIdentifier;

+ (BOOL)supportGlobalSvrConfig;

- (NSString *)currentNetworkingbaseUrl;

- (NSString *)path:(NSString *)path;

@end
