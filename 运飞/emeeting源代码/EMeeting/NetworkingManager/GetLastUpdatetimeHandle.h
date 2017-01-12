//
//  GetLastUpdatetimeHandle.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/22.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
#import "EMMNetworking.h"

@interface GetLastUpdatetimeHandle : BaseNetworkingHandle

- (void)sendJSONRequestWithSuccess:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;

@end
