//
//  GetServerDateHandle.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/23.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetServerDateHandle.h"

@implementation GetServerDateHandle

+ (NSString *)commandName
{
    return @"GetServerData";
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
