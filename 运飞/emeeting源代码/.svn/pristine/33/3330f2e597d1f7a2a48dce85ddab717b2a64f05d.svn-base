//
//  GetMeetingRoomBookingInfoHandle.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/25.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "BaseNetworkingHandle.h"
#import "NetworkingManager.h"
#import "EMMNetworking.h"
#import "FObjectModel.h"
#import "ScreeningCondition.h"

typedef enum
{
    MeetIngRoomInfo = 1,
    NotLimitTime,
    WithTime
    
}RequestType;

@interface GetMeetingRoomBookingInfoHandle : BaseNetworkingHandle

- (void)sendJSONRequestWithFObjectModel:(FObjectModel *)fObjectModel  ScreeningCondition:(ScreeningCondition *)condition page:(PageRequestObject *)page andRequestType:(RequestType)type Success:(void (^)(ResponseObject * response))success failure:(void (^)(NSError * error))failure;

@end
