//
//  GetUserRelevantMeetingInfo.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetUserRelevantMeetingInfo.h"

@implementation GetUserRelevantMeetingInfo
+ (NSString *)commandName
{
    return @"GetUserRelevantMeetingInfo";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithMeetingBelonging:(NSString *)meetingBelonging  PageRequestObject:(PageRequestObject *) pageRequestObject  MeetingDate:(NSString *)meetingDate Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;
{
    FunctionObject *meetingBelongingObj=[[FunctionObject alloc]init];
    meetingBelongingObj.k=@"MeetingBelonging";
    meetingBelongingObj.v=meetingBelonging;
    self.requestObj.f=@[meetingBelongingObj];
    if (meetingDate) {
        FunctionObject *meetingDateObj=[[FunctionObject alloc]init];
        meetingDateObj.k=@"MeetingDate";
        meetingDateObj.v=meetingDate;
        self.requestObj.f=@[meetingBelongingObj,meetingDateObj];
    }
    
    
    
    self.requestObj.p=pageRequestObject;
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}


@end
