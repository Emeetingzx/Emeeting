//
//  MeetingSCell.h
//  会议预定
//
//  Created by efutureinfo on 16/2/23.
//  Copyright © 2016年 efutureinfo. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeetingRoomInfo.h"
#import "MeetingInfo.h"

#define SelectTimeButtonBeginTag 1800

#define SelectTimeButtonEndTag 1825

@class MeetingSCell;

@protocol MeetingSCellDelegate <NSObject>

@optional
-(void)MeetingSCellDidClickCancel:(MeetingSCell *)detailView;

-(void)MeetingSCellDidClickConfirm:(MeetingSCell *)detailView andSelectedTime:(NSString *)selectTime;

@end

@interface MeetingSCell : UITableViewCell

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *imageLeft1;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *imageLeft2;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *imageLeft3;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *imageLeft4;

@property (weak, nonatomic) IBOutlet UILabel *numberLabel;

@property (weak, nonatomic) IBOutlet UILabel *projectorLabel;

@property (weak, nonatomic) IBOutlet UILabel *televisionLabel;

@property (weak, nonatomic) IBOutlet UILabel *phoneLabel;

@property (weak, nonatomic) IBOutlet UILabel *meetingNameLabel;

@property (weak, nonatomic) IBOutlet UIView *timeSelectBgView;

@property (weak, nonatomic) IBOutlet UILabel *mdayLabel;

@property (weak, nonatomic) IBOutlet UILabel *msecondLabel;

@property (weak, nonatomic) id<MeetingSCellDelegate> delegate;

@property (nonatomic, strong) MeetingRoomInfo *meetingRoomInfo;

@property (nonatomic, copy) NSString* selectedTime;

@property (nonatomic, assign) BOOL isNotLimitTime;

@end
