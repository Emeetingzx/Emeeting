//
//  LockBookingMeetingRoomHandle.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "LockBookingMeetingRoomHandle.h"

@implementation LockBookingMeetingRoomHandle

+ (NSString *)commandName
{
    return @"LockBookingMeetingRoom";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithFObjectModel:(FObjectModel *)fObjectModel   Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *roomIdObj=[[FunctionObject alloc]init];
    roomIdObj.k=@"RoomId";
    roomIdObj.v=fObjectModel.meetingRoomId;
    
    FunctionObject *beginDateObj=[[FunctionObject alloc]init];
    beginDateObj.k=@"BeginDate";
    beginDateObj.v=fObjectModel.beginDate;
    
    FunctionObject *endDateObj=[[FunctionObject alloc]init];
    endDateObj.k=@"EndDate";
    endDateObj.v=fObjectModel.endDate;
    
    self.requestObj.f=@[roomIdObj,beginDateObj,endDateObj];
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}


@end
