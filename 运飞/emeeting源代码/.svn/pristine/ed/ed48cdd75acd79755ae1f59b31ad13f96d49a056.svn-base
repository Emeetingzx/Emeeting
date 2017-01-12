//
//  EMMNetworkStatus.h
//  EMMNetworking
//
//  Created by clement on 14-6-11.
//  Copyright (c) 2014年 zte. All rights reserved.
//

#import <Foundation/Foundation.h>

extern NSString *const kEMMReachabilityChangedNotification;

@class EMMNetworkStatus;

typedef enum
{
	// Apple NetworkStatus Compatible Names.
	EMMNotReachable     = 0,
    EMMReachableViaWWAN = 1,
	EMMReachableViaWiFi = 2
} EMMNetworkStatusFlag;

@interface EMMNetworkStatus : NSObject
@property (nonatomic, assign) BOOL reachableOnWWAN;


+(EMMNetworkStatus*)networkStatuWithHostname:(NSString*)hostname;

-(BOOL)isReachable;

-(BOOL)isReachableViaWWAN;

-(BOOL)isReachableViaWiFi;

-(EMMNetworkStatusFlag)currentReachabilityStatus;

- (void (^)(EMMNetworkStatus *reachability))reachableBlock;

- (void (^)(EMMNetworkStatus *reachability))unreachableBlock;

- (void)setReachableBlock:(void (^)(EMMNetworkStatus *reachability))reachableBlock;

- (void)setUnreachableBlock:(void (^)(EMMNetworkStatus *reachability))unreachableBlock;

-(BOOL)startNotifier;

-(void)stopNotifier;

@end
