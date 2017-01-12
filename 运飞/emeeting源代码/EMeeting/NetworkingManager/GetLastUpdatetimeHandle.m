//
//  GetLastUpdatetimeHandle.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/22.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetLastUpdatetimeHandle.h"


@implementation GetLastUpdatetimeHandle

+ (NSString *)commandName
{
    return @"GetLastUpdatetime";
}

+ (NSString *)path
{
    return BaseDataPath;
}

- (void)sendJSONRequestWithSuccess:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
    {

        success(response);
        
    }failure:failure];
}


@end
