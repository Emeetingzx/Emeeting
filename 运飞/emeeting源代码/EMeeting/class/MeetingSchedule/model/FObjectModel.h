//
//  FObjectModel.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/25.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface FObjectModel : NSObject

@property (nonatomic, copy) NSString *meetingRoomId;
@property (nonatomic, copy) NSString *queryDate;
@property (nonatomic, copy) NSString *beginDate;
@property (nonatomic, copy) NSString *endDate;

@property (nonatomic, copy) NSString *emeetingId;
@property (nonatomic, copy) NSString *attendLeaderLevel;
@property (nonatomic, copy) NSString *memberNos;
@property (nonatomic, copy) NSString *meetingName;

@end
