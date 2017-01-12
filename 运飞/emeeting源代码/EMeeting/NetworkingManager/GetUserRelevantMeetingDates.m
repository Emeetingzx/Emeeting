//
//  GetUserRelevantMeetingDates.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/3/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetUserRelevantMeetingDates.h"

@implementation GetUserRelevantMeetingDates
+ (NSString *)commandName
{
    return @"GetUserRelevantMeetingDates";
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
