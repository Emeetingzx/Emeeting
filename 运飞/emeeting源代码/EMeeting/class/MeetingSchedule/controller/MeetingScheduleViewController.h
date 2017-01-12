//
//  MeetingScheduleViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/1/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MeetingScheduleViewController : UIViewController

/*
 * 前一天按钮
 */
@property (weak, nonatomic) IBOutlet UIButton *lastDayButton;

/*
 * 前一天箭头
 */
@property (weak, nonatomic) IBOutlet UIImageView *lastDayImage;

/*
 * 前一天label
 */
@property (weak, nonatomic) IBOutlet UILabel *lastDayLabel;

/*
 * 后一天按钮
 */
@property (weak, nonatomic) IBOutlet UIButton *nextDayButton;

/*
 * 后一天箭头
 */
@property (weak, nonatomic) IBOutlet UIImageView *nextDayImage;

/*
 * 后一天label
 */
@property (weak, nonatomic) IBOutlet UILabel *nextDayLabel;

/*
 * 选择的月份和日期
 */
@property (weak, nonatomic) IBOutlet UILabel *mdayLabel;

/*
 * 选择的月份和日期label距离父视图左边的距离约束
 */
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *mdayLabelLeftConstraint;

/*
 * 选择的时间段label
 */
@property (weak, nonatomic) IBOutlet UILabel *hsecondLabel;

/*
 * 选择或定位的地点
 */
@property (weak, nonatomic) IBOutlet UILabel *placeLabel;

/*
 * 是否时间不限制标志，YES表示不限制
 */
@property (assign, nonatomic) BOOL isNotLimitTime;

@property (nonatomic, copy) NSString *selectedTime;

@property (nonatomic, strong) NSDate *serverDate;

@property (nonatomic, strong) NSDate *maxDate;

@end
