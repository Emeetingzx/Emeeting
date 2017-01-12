//
//  GetFoodAndRefreshmentsInfos.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/23.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
#import "EMMNetworking.h"
@interface GetFoodAndRefreshmentsInfos : BaseNetworkingHandle

- (void)sendJSONRequestWithSuccess:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;
@end
