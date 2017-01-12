//
//  ReserveAddValueService.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
#import "EMMNetworking.h"
#import "serviceReserveData.h"
@interface ReserveAddValueService : BaseNetworkingHandle
- (void)sendJSONRequestWithServiceReserveData:(ServiceReserveData *)serviceReserveData Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;
@end
