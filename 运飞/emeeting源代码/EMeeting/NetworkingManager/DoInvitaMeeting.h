//
//  DoInvitaMeeting.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/19.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
@interface DoInvitaMeeting : BaseNetworkingHandle
- (void)sendJSONRequestWithMeetingId:(NSString *) meetingId Number:(NSString *) number Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;
@end
