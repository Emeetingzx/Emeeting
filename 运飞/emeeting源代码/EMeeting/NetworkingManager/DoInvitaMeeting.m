//
//  DoInvitaMeeting.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/19.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "DoInvitaMeeting.h"

@implementation DoInvitaMeeting
+ (NSString *)commandName
{
    return @"DoInvitaMeeting";
}

+ (NSString *)path
{
    return MeetingDominate;
}
- (void)sendJSONRequestWithMeetingId:(NSString *) meetingId Number:(NSString *) number Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *meetingIdObj=[[FunctionObject alloc]init];
    meetingIdObj.k=@"ID";
    meetingIdObj.v=meetingId;
    
    FunctionObject *numberObj=[[FunctionObject alloc]init];
    numberObj.k=@"Number";
    numberObj.v=number;
    
    self.requestObj.f=@[meetingIdObj,numberObj];
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}

@end
