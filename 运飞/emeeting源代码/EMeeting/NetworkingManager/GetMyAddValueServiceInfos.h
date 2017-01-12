//
//  GetMyAddValueServiceInfos.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/25.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
#import "EMMNetworking.h"
@interface GetMyAddValueServiceInfos : BaseNetworkingHandle

- (void)sendJSONRequestWithPage:(PageRequestObject *) page Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;
@end
