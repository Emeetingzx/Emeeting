//
//  GetMeetingJoinInfo.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/9.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
#import "EMMNetworking.h"
@interface GetMeetingJoinInfo : BaseNetworkingHandle
- (void)sendJSONRequestWithMeetingId:(NSString *) Id Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;
@end
