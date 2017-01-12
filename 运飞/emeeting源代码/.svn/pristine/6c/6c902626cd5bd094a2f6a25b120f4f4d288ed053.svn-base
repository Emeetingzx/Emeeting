//
//  EndMeetingRoom.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/3/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "EndMeetingRoom.h"

@implementation EndMeetingRoom
+ (NSString *)commandName
{
    return @"EndMeetingRoom";
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
