//
//  GetMeetingInfo.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/3/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetMeetingInfo.h"

@implementation GetMeetingInfo
+ (NSString *)commandName
{
    return @"GetMeetingInfo";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithEmeetingId:(NSString *)meetingId Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *emeetingIdObj=[[FunctionObject alloc]init];
    emeetingIdObj.k=@"MeetingId";
    emeetingIdObj.v=meetingId;
    
    self.requestObj.f=@[emeetingIdObj];
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}


@end
