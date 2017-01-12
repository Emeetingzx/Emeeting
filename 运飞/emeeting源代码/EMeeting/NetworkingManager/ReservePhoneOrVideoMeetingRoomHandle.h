//
//  ReservePhoneOrVideoMeetingRoomHandle.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
#import "EMMNetworking.h"
#import "MeetingInfo.h"


@interface ReservePhoneOrVideoMeetingRoomHandle : BaseNetworkingHandle

- (void)sendJSONRequestWithMeetingInfo:(MeetingInfo *)meetingInfo Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;

@end
