//
//  GetNearParkMeetingRoomInfoHandle.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
#import "EMMNetworking.h"

@interface GetNearParkMeetingRoomInfoHandle : BaseNetworkingHandle

- (void)sendJSONRequestWithLatitudeAndLongitude:(NSString *)latitudeAndLongitude AddressId:(NSString *)addressId MeetingTime:(NSString *)meetingTime Page:(PageRequestObject *) page Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;

@end
