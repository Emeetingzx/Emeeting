//
//  GetUserIfAddValueServiceAdmin.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/3/7.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetUserIfAddValueServiceAdmin.h"

@implementation GetUserIfAddValueServiceAdmin
+ (NSString *)commandName
{
    return @"GetUserIfAddValueServiceAdmin";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithSuccess:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}


@end
