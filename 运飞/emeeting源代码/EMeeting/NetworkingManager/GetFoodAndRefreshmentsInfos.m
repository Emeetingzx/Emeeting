//
//  GetFoodAndRefreshmentsInfos.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/23.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetFoodAndRefreshmentsInfos.h"

@implementation GetFoodAndRefreshmentsInfos
+ (NSString *)commandName
{
    return @"GetFoodAndRefreshmentsInfos";
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
