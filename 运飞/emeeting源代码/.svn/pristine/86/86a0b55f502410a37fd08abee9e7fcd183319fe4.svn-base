//
//  GetNearParkMeetingRoomInfoHandle.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "GetNearParkMeetingRoomInfoHandle.h"

@implementation GetNearParkMeetingRoomInfoHandle

+ (NSString *)commandName
{
    return @"GetNearParkMeetingRoomInfo";
}

+ (NSString *)path
{
    return SharkOffPath;
}

- (void)sendJSONRequestWithLatitudeAndLongitude:(NSString *)latitudeAndLongitude AddressId:(NSString *)addressId MeetingTime:(NSString *)meetingTime Page:(PageRequestObject *) page Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *latitudeAndLongitudeObj=[[FunctionObject alloc]init];
    latitudeAndLongitudeObj.k=@"LatitudeAndLongitude";
    latitudeAndLongitudeObj.v=latitudeAndLongitude;
    
    FunctionObject *addressIdObj=[[FunctionObject alloc]init];
    addressIdObj.k=@"AddressId";
    addressIdObj.v=addressId;
    
    FunctionObject *meetingTimeObj=[[FunctionObject alloc]init];
    meetingTimeObj.k=@"MeetingTime";
    meetingTimeObj.v=meetingTime;
    
    
    self.requestObj.f=@[latitudeAndLongitudeObj,addressIdObj,meetingTimeObj];
    
    self.requestObj.p = page;
    
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
         
     }failure:failure];
}


@end
