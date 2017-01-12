//
//  ReservePhoneOrVideoMeetingRoomHandle.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ReservePhoneOrVideoMeetingRoomHandle.h"

@implementation ReservePhoneOrVideoMeetingRoomHandle

+ (NSString *)commandName
{
    return @"ReservePhoneOrVideoMeetingRoom";
}

+ (NSString *)path
{
    return MeetingBridgePath;
}

- (void)sendJSONRequestWithMeetingInfo:(MeetingInfo *)meetingInfo Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure
{
    self.requestObj.d = [meetingInfo networkingDictionary];
    [super sendJSONRequestWithSuccess:^(ResponseObject * response)
     {
         success(response);
     }failure:failure];
}

@end
