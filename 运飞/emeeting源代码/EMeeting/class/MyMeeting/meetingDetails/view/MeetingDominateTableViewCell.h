//
//  MeetingDominateTableViewCell.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/4/26.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeetingJoinInfo.h"
@interface MeetingDominateTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *boardroomAdress;
@property (weak, nonatomic) IBOutlet UIImageView *stateImageView;
@property (weak, nonatomic) IBOutlet UILabel *stateLableView;
@property (weak, nonatomic) IBOutlet UIImageView *muteStateImageView;
@property (weak, nonatomic) IBOutlet UIView *lineView;
@property (weak, nonatomic) IBOutlet UIView *line1View;
@property (weak, nonatomic) IBOutlet UIButton *muteBtn;

@property (weak, nonatomic) IBOutlet UILabel *callLable;
@property (weak, nonatomic) IBOutlet UILabel *muteLable;
@property (weak, nonatomic) IBOutlet UILabel *endTime;
@property (weak, nonatomic) IBOutlet UIImageView *endImageView;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *endTimeLableLeft;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *endLineViewLeft;
@property (weak, nonatomic) IBOutlet UIView *endLineView;

@property (weak, nonatomic) IBOutlet UIButton *callBtn;
@property (strong, nonatomic) MeetingJoinInfo *meetingJoinInfo;

-(CGFloat)cellHeightServiceAddress:(NSString *)serviceAddress;
@end
