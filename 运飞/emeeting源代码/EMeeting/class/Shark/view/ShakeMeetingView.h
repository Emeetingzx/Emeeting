//
//  ShakeMeetingView.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeetingRoomInfo.h"
#import "MeetingScheduleTableViewCell.h"

@class ShakeMeetingView;

@protocol ShakeMeetingViewDelegate <NSObject>

@optional
- (void)shakeMeetingViewDidcancel:(ShakeMeetingView *)shakeMeetingView;
- (void)shakeMeetingView:(ShakeMeetingView *)shakeMeetingView didClickMeetingRoomInfo:(MeetingRoomInfo *)meetingRoomInfo;

@end

@interface ShakeMeetingView : UIView<UITableViewDataSource,UITableViewDelegate>

- (instancetype)initWithFrame:(CGRect)frame MeetingRoomInfo:(NSArray *)dataArr;

@property (nonatomic, weak) id<ShakeMeetingViewDelegate> delegate;

@property (nonatomic, strong) NSMutableArray<MeetingRoomInfo *>* meetingRoomInfoArr;

@end
