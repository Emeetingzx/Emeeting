//
//  MeetingRoomInfo.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "BaseNetworkingObject.h"
#import "MeetingInfo.h"

@interface MeetingRoomInfo : BaseNetworkingObject

@property (nonatomic, copy) NSString *meetingRoomId;
@property (nonatomic, copy) NSString *meetingRoomName;
@property (nonatomic, copy) NSString *meetingRoomAddressIds;
@property (nonatomic, copy) NSString *meetingRoomScale;
@property (nonatomic, copy) NSString *projectorState;
@property (nonatomic, copy) NSString *televisionState;
@property (nonatomic, copy) NSString *phoneState;
@property (nonatomic, copy) NSString *meetingRoomOccupancyRation;
@property (nonatomic, strong) NSMutableArray<MeetingInfo *> *haveBookingMeetingInfos;
@property (nonatomic, assign) float rowHeigth;

-(NSString *)returnProjectorString:(NSString *)projectorState;
-(NSString *)returnPhoneString:(NSString *)phoneState;
-(NSString *)returnTelevisionString:(NSString *)televisionState;
@end
