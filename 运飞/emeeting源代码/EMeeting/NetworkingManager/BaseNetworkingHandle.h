//
//  BaseNetworkingHandle.h
//  RedPacket
//
//  Created by itp on 15/11/11.
//  Copyright © 2015年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>

@class RequestObject;
@class ResponseObject;

@interface BaseNetworkingHandle : NSObject
@property (nonatomic,strong,readonly)RequestObject *requestObj;

- (void)sendJSONRequestWithSuccess:(void(^)(ResponseObject *response))success failure:(void(^)(NSError *error))failure;

- (void)sendSoapRequestWithSuccess:(void(^)(ResponseObject *response))success failure:(void(^)(NSError *error))failure;

+ (NSString *)path;

+ (NSString *)commandName;

@end
