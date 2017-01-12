//
//  GetRecentBuildingAddressInfoHandle.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
#import "EMMNetworking.h"

@interface GetRecentBuildingAddressInfoHandle : BaseNetworkingHandle

- (void)sendJSONRequestWithLatitudeAndLongitude:(NSString *)latitudeAndLongitude Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;

@end
