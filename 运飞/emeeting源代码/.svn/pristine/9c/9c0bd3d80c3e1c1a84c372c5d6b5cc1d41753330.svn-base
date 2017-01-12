//
//  SubmitBookingMeetingRoomHandle.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "SubmitBookingMeetingRoomHandle.h"

@implementation SubmitBookingMeetingRoomHandle

+ (NSString *)commandName
{
    return @"SubmitBookingMeetingRoom";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithFObjectModel:(FObjectModel *)fObjectModel   Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *meetingIdObj=[[FunctionObject alloc]init];
    meetingIdObj.k=@"EmeetingId";
    meetingIdObj.v=fObjectModel.emeetingId;
    
    FunctionObject *levelObj=[[FunctionObject alloc]init];
    levelObj.k=@"AttendLeaderLevel";
    levelObj.v=fObjectModel.attendLeaderLevel;
    
    FunctionObject *memberNosObj=[[FunctionObject alloc]init];
    memberNosObj.k=@"MemberNos";
    memberNosObj.v=fObjectModel.memberNos;
    
    FunctionObject *nameObj=[[FunctionObject alloc]init];
    nameObj.k=@"MeetingName";
    nameObj.v=fObjectModel.meetingName;
    
    self.requestObj.f=@[meetingIdObj,levelObj,memberNosObj,nameObj];
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}


@end
