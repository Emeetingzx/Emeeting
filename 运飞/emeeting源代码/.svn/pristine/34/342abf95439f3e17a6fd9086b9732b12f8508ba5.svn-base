//
//  MeetingScheduleTableViewCell.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DrawArcView.h"
#import "MeetingRoomInfo.h"

#define ScheduleButtonBeginTag 6000

@class MeetingScheduleTableViewCell;

@protocol MeetingScheduleTableViewCellDelegate <NSObject>

@optional
- (void)MeetingScheduleTableViewCell:(MeetingScheduleTableViewCell *)cell didClickRow:(NSInteger)row;

@end

@interface MeetingScheduleTableViewCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *meetingAdressLabel;

@property (weak, nonatomic) IBOutlet UILabel *numberLabel;

@property (weak, nonatomic) IBOutlet UIImageView *projectorImageView;

@property (weak, nonatomic) IBOutlet UIImageView *televisionImageView;

@property (weak, nonatomic) IBOutlet UIImageView *phoneImageView;

@property (weak, nonatomic) IBOutlet DrawArcView *presentView;

@property (weak, nonatomic) IBOutlet UIView *bottonBgview;

@property (weak, nonatomic) IBOutlet UIButton *scheduleButton;

@property (weak, nonatomic) IBOutlet UIImageView *meetingImageView;

@property (weak, nonatomic) IBOutlet UILabel *rationLabel;


@property (weak, nonatomic) IBOutlet NSLayoutConstraint *buttonWidthConstraint;

@property (nonatomic, weak) id<MeetingScheduleTableViewCellDelegate> delegate;

@property (nonatomic, strong) MeetingRoomInfo *meetingRoomInfo;

@end
