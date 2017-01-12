//
//  ServeTimeSelectView.h
//  tht
//
//  Created by efutureinfo on 16/2/15.
//  Copyright © 2016年 ljf. All rights reserved.
//

#import <UIKit/UIKit.h>

#define ServeTimeMDayPickerTag 3004
#define ServeTimeHSecondPickerTag 3005

@class ServeTimeSelectView;

@protocol ServeTimeSelectViewDelegate <NSObject>

- (void)serveTimeSelectView:(ServeTimeSelectView *)timeSelectView didSelectTime:(NSString *)selectedTimeString;
- (void)serveTimeSelectViewDidCancel:(ServeTimeSelectView *)serveTimeSelect;

@end

@interface ServeTimeSelectView : UIView<UIPickerViewDataSource,UIPickerViewDelegate>

@property (weak, nonatomic) IBOutlet UIPickerView *serveTimeMDayPicker;

@property (weak, nonatomic) IBOutlet UIPickerView *serveTimeHSecondPicker;

@property (nonatomic, copy) NSString *ymdString;
@property (nonatomic, copy) NSString *hourString;
@property (nonatomic, copy) NSString *minuteString;
@property (nonatomic, strong) NSString *selectedTime;
@property (weak, nonatomic) id<ServeTimeSelectViewDelegate> delegate;
@end
