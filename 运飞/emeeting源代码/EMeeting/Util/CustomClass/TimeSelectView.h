//
//  TimeSelectView.h
//  tht
//
//  Created by efutureinfo on 16/2/15.
//  Copyright © 2016年 ljf. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NSDate+LJFDate.h"

#define DayPickerTag 3000
#define StartTimePickerTag 3001
#define EndTimePickerTag 3002

@class TimeSelectView;

@protocol TimeSelectViewDelegate <NSObject>

- (void)TimeSelectViewDidCancel:(TimeSelectView *)timeSelectView;

- (void)TimeSelectView:(TimeSelectView *)timeSelectView didSelectTime:(NSString *)selectedTimeString;

@optional
- (void)TimeSelectViewDidClickNotlimitButton:(TimeSelectView *)timeSelectView;

@end

@interface TimeSelectView : UIView<UIPickerViewDataSource,UIPickerViewDelegate>

/*
 * 年月日时间选择器
 */
@property (weak, nonatomic) IBOutlet UIPickerView *dayPicker;

/*
 * 开始时间选择器
 */
@property (weak, nonatomic) IBOutlet UIPickerView *startTimePicker;

/*
 * 结束时间选择器
 */
@property (weak, nonatomic) IBOutlet UIPickerView *endTimePicker;

/*
 * 时段不限按钮
 */
@property (weak, nonatomic) IBOutlet UIButton *notLimitButton;

@property (copy, nonatomic) NSString *selectedTime;

@property (assign, nonatomic) BOOL notLimitIsHidden;

@property (weak, nonatomic) id<TimeSelectViewDelegate> delegate;

@property (nonatomic, copy) NSString *ymdString;
@property (nonatomic, copy) NSString *beginHourString;
@property (nonatomic, copy) NSString *beginMinuteString;
@property (nonatomic, copy) NSString *endHourString;
@property (nonatomic, copy) NSString *endMinuteString;
@property (nonatomic, copy) NSString *selectedTimeString;

@end
