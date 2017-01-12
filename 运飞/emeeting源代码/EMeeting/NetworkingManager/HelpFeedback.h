//
//  HelpFeedback.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/3/7.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
#import "EMMNetworking.h"
@interface HelpFeedback : BaseNetworkingHandle
- (void)sendJSONRequestWithFeedbackInfo:(NSString *) feedbackInfo Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;
@end
