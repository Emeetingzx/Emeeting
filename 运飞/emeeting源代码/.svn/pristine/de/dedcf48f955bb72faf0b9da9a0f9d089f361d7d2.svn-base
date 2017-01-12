//
//  GetAddValueServiceRegionInfos.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetAddValueServiceRegionInfos.h"

@implementation GetAddValueServiceRegionInfos
+ (NSString *)commandName
{
    return @"GetAddValueServiceRegionInfos";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithSuccess:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         if ([response.d isKindOfClass:[NSArray class]])
         {
             success(response);
         }
         else
         {
             failure(nil);
         }
     }failure:failure];
}


@end
