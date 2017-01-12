//
//  CancelMeetingRoom.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "CancelMeetingRoom.h"

@implementation CancelMeetingRoom
+ (NSString *)commandName
{
    return @"CancelMeetingRoom";
}

+ (NSString *)path
{
    return PublicPath;
}

- (void)sendJSONRequestWithEmeetingId:(NSString *) emeetingId  RoomId:(NSString *) roomId Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    FunctionObject *emeetingIdObj=[[FunctionObject alloc]init];
    emeetingIdObj.k=@"EmeetingId";
    emeetingIdObj.v=emeetingId;
    self.requestObj.f=@[emeetingIdObj];
    if (roomId) {
        FunctionObject *roomIdObj=[[FunctionObject alloc]init];
        roomIdObj.k=@"RoomId";
        roomIdObj.v=roomId;
        self.requestObj.f=@[emeetingIdObj,roomIdObj];
    }
   
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}


@end
