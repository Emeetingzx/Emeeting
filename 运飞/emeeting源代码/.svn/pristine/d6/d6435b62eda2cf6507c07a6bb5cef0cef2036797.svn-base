//
//  MeetingScheduleView.h
//  会议预定
//
//  Created by efutureinfo on 16/2/4.
//  Copyright © 2016年 efutureinfo. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeetingSCell.h"
#import "MeetingRoomInfo.h"
#import "MeetingInfo.h"

#define DetailViewHeight 556

@class MeetingScheduleView;
@protocol MeetingScheduleViewDelegate <NSObject>

@optional
- (void)meetingScheduleView:(MeetingScheduleView *)secheduleView lockMeetingSuccess:(NSString *)meetingId;

- (void)meetingScheduleViewDidCancel:(MeetingScheduleView *)secheduleView;

@end

@interface MeetingScheduleView : UIView<UITableViewDelegate,UITableViewDataSource,MeetingSCellDelegate>

-(instancetype)initWithFrame:(CGRect)frame andDictionary:(MeetingRoomInfo *)roominfo selectTime:(NSString *)selectedTime andIsNotLimitTime:(BOOL)isNotLimitTime;

@property (nonatomic, weak) id<MeetingScheduleViewDelegate> delegate;

@end
