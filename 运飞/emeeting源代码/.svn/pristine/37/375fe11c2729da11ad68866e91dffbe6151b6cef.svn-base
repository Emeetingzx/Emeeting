//
//  MeetingProlong.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/9.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetMeetingProlong.h"

@implementation GetMeetingProlong
+ (NSString *)commandName
{
    return @"MeetingProlong";
}

+ (NSString *)path
{
    return MeetingDominate;
}

- (void)sendJSONRequestWithMeetingId:(NSString *) Id Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *meetingIdObj=[[FunctionObject alloc]init];
    meetingIdObj.k=@"ID";
    meetingIdObj.v=Id;
    
    self.requestObj.f=@[meetingIdObj];
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}     


@end
