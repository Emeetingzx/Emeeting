//
//  MeetingJoinInfo.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/9.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseNetworkingObject.h"
@interface MeetingJoinInfo : BaseNetworkingObject
@property (nonatomic, copy) NSString *meetingRoomID;
@property (nonatomic, copy) NSString *state;
@property (nonatomic, copy) NSString *terminalName;
@property (nonatomic, copy) NSString *terminalNumber;
@property (nonatomic, copy) NSString *endTime;
@property (nonatomic, copy) NSString *terminalType;
@property (nonatomic, copy) NSString * showTerminalName;
@property (nonatomic, assign) float cellHeight;
-(instancetype)initWithDictionary:(NSDictionary *)dic;
@end
