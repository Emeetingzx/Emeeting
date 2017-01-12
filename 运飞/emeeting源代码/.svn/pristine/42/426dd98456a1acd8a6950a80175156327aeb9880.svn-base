//
//  GetMeetingRoomBookingInfoHandle.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/25.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetMeetingRoomBookingInfoHandle.h"


@implementation GetMeetingRoomBookingInfoHandle

+ (NSString *)commandName
{
    return @"GetMeetingRoomBookingInfo";
}

+ (NSString *)path
{
    return MeetingScheduledPath;
}

- (void)sendJSONRequestWithFObjectModel:(FObjectModel *)fObjectModel  ScreeningCondition:(ScreeningCondition *)condition page:(PageRequestObject *)page andRequestType:(RequestType)type Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *roomIdObj=[[FunctionObject alloc]init];
    roomIdObj.k=@"MeetingRoomId";
    roomIdObj.v=fObjectModel.meetingRoomId;
    
    FunctionObject *queryDateObj=[[FunctionObject alloc]init];
    queryDateObj.k=@"QueryDate";
    queryDateObj.v=fObjectModel.queryDate;
    
    FunctionObject *beginDateObj=[[FunctionObject alloc]init];
    beginDateObj.k=@"BeginDate";
    beginDateObj.v=fObjectModel.beginDate;
    
    FunctionObject *endDateObj=[[FunctionObject alloc]init];
    endDateObj.k=@"EndDate";
    endDateObj.v=fObjectModel.endDate;
    
    if (type == MeetIngRoomInfo)
    {
        self.requestObj.f=@[roomIdObj,queryDateObj];
    }else if (type == NotLimitTime)
    {
        self.requestObj.f=@[queryDateObj];
        
        self.requestObj.d = [condition networkingDictionary];
    }else if (type == WithTime)
    {
        self.requestObj.f=@[beginDateObj,endDateObj];
        
        self.requestObj.d = [condition networkingDictionary];
    }
    
    if (page)
    {
        self.requestObj.p = page;
    }
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}


@end
