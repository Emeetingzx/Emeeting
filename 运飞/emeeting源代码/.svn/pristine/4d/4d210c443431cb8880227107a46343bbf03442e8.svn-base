//
//  GetValidBookDateHandle.m
//  EMeeting
//
//  Created by efutureinfo on 16/4/6.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetValidBookDateHandle.h"

@implementation GetValidBookDateHandle

+ (NSString *)commandName
{
    return @"GetValidBookDate";
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
