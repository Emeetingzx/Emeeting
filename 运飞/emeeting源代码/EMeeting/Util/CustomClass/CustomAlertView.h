//
//  CustomAlertView.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

@class CustomAlertView;

@protocol CustomAlertViewDelegate <NSObject>

@optional
- (void)CustomAlertView:(CustomAlertView *)customAlert andButtonClickIndex:(NSInteger)index;

- (void)CustomAlertViewTouchHidden:(CustomAlertView *)customAlert;

@end

@interface CustomAlertView : UIView

@property (nonatomic, weak) id<CustomAlertViewDelegate> delegate;

@property (nonatomic, strong) UILabel *lable;

@property (nonatomic, strong) UIButton *firstButton;

@property (nonatomic, strong) UIButton *secondButton;

@property (nonatomic, strong) UIView *line;

@property (nonatomic, assign) BOOL tapIsHidden;

- (instancetype)initWithFrame:(CGRect)frame delegate:(id)delegate Message:(NSString *)message firstButtonTitle:(NSString *)firstButtonTitle secondButtonTitle:(NSString *)secondButtonTitle ImageName:(NSString *)imageName andImageSize:(CGSize)imagesize AlertWidth:(CGFloat)alertwidth;

-(instancetype)initWithFrame:(CGRect)frame Message:(NSString *)message ImageName:(NSString *)imageName andImageSize:(CGSize)imagesize AlertWidth:(CGFloat)alertwidth;

- (void)setLableAttributedColor:(UIColor *)color range:(NSRange)range;

@end
