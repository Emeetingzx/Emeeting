//
//  CancelMeetingRoom.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
#import "EMMNetworking.h"
@interface CancelMeetingRoom : BaseNetworkingHandle
- (void)sendJSONRequestWithEmeetingId:(NSString *) emeetingId  RoomId:(NSString *) roomId Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;

@end
