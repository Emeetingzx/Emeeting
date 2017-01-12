//
//  ScreeningCondition.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/17.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseNetworkingObject.h"

@interface ScreeningCondition : BaseNetworkingObject

@property (nonatomic, copy) NSString *meetingRoomAddressIds;

@property (nonatomic, copy) NSString *ProjectorState;

@property (nonatomic, copy) NSString *TelevisionState;

@property (nonatomic, copy) NSString *PhoneState;

@property (nonatomic, copy) NSString *ParticipantsNumber;

@end
