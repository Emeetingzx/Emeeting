//
//  ScreeningCondition.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/17.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ScreeningCondition.h"

@implementation ScreeningCondition

- (NSDictionary *)networkingDictionary
{
    return @{
             @"MRAIDS":self.meetingRoomAddressIds?:NULLObject,
             @"PJS":self.ProjectorState?:NULLObject,
             @"TVS":self.TelevisionState?:NULLObject,
             @"PS":self.PhoneState?:NULLObject,
             @"PN":self.ParticipantsNumber?:NULLObject,
             };
}

@end
