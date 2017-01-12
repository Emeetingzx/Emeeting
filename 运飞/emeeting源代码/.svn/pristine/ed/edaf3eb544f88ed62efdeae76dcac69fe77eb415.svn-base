//
//  ShakeScreenOutView.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/16.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UIColor+LJFColor.h"
#import "Tools.h"

#define SelectMeetingTimeBeginTag 4000
#define SelectMeetingTimeEndTag 4002
#define SelectPlaceButtonBeginTag 4100

@class ShakeScreenOutView;

@protocol ShakeScreenOutViewDelegate <NSObject>

@optional
- (void) shakeScreenOutView:(ShakeScreenOutView *)screenOutView didSelectedPlaceIds:(NSString *)placeIds selectedTime:(NSString *)time;

@end

@interface ShakeScreenOutView : UIView

- (instancetype)initWithFrame:(CGRect)frame meetingLocation:(NSString *)location subPlaceDataArr:(NSArray *)placeArr;

@property (nonatomic, weak) id<ShakeScreenOutViewDelegate> delegate;

@property (nonatomic, copy) NSString *selectedPlaceIds;

@property (nonatomic, copy) NSString *selectedTime;

@end
