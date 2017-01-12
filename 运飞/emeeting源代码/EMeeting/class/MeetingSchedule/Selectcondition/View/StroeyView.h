//
//  StroeyView.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

#define SelectStroeyButtonBeginTag 1100

@class StroeyView;

@protocol StroeyViewDelegate <NSObject>

@optional
- (void)stroeyView:(StroeyView *)stroeyView didSelectedStroeyArr:(NSString *)meetingRoomAddressIds;

@end

@interface StroeyView : UIView

@property (nonatomic, weak) id<StroeyViewDelegate> delegate;

- (instancetype)initWithFrame:(CGRect)frame andPid:(NSString *)pid andSelectAddressIds:(NSString *)addressIds;

- (void)setButtonBackgroundColorFromTag:(NSInteger)fromtag ToTag:(NSInteger)totag andSelectTag:(NSInteger)selecttag;

- (void)addFirstNotLimitButton;

@property (nonatomic, copy) NSString *meetingRoomAddressIds;

@end
