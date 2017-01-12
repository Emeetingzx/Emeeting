//
//  DefaultDataModel.h
//  EMeeting
//
//  Created by efutureinfo on 16/3/11.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MeetingRoomInfo.h"

@interface DefaultDataModel : NSObject

@property (nonatomic, copy) NSString *pid;

@property (nonatomic, copy) NSString *pName;

@property (nonatomic, copy) NSString *meetingRoomAddressIds;

@property (nonatomic, copy) NSString *ProjectorState;

@property (nonatomic, copy) NSString *TelevisionState;

@property (nonatomic, copy) NSString *PhoneState;

@property (nonatomic, copy) NSString *ParticipantsNumber;

@property (nonatomic, assign) int selectedIndex;

@property (nonatomic, strong) NSMutableArray <MeetingRoomInfo *>* dataArr;

+ (instancetype)shareInstance;

@end
