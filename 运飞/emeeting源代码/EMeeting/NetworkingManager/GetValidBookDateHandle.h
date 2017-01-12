//
//  GetValidBookDateHandle.h
//  EMeeting
//
//  Created by efutureinfo on 16/4/6.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
#import "EMMNetworking.h"

@interface GetValidBookDateHandle : BaseNetworkingHandle

- (void)sendJSONRequestWithSuccess:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;

@end
