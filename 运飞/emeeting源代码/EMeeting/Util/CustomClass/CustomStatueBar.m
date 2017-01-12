//
//  CustomStatueBar.m
//  CustomStatueBar
//
//  Created by efutureinfo on 16/5/6.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "CustomStatueBar.h"
#import "Tools.h"
@implementation CustomStatueBar

- (id)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if (self) {
        self.windowLevel = UIWindowLevelNormal;
        self.windowLevel = UIWindowLevelStatusBar + 1.0f;
		self.frame = frame;
        self.backgroundColor = [UIColor blackColor];
        
        defaultLabel=[[UILabel alloc]initWithFrame:CGRectMake(0, 0, APPW, 40)];
        defaultLabel.backgroundColor = [UIColor clearColor];
        defaultLabel.textColor = [UIColor whiteColor];
        defaultLabel.backgroundColor=[UIColor blackColor];
        defaultLabel.font = [UIFont systemFontOfSize:14.0f];
        defaultLabel.textAlignment = NSTextAlignmentCenter;
        defaultLabel.numberOfLines=2;
        [self addSubview:defaultLabel];
    }
    return self;
}
- (void)showStatusMessage:(NSString *)message{
    defaultLabel.text = message;
    //self.window
    self.hidden = NO;
    self.alpha = 1.0f;
    CGSize totalSize = self.frame.size;
    self.frame = (CGRect){0,-40,APPW,totalSize.height};
    [UIView animateWithDuration:0.5 animations:^{
        self.transform=CGAffineTransformMakeTranslation(0, totalSize.height);
    } completion:^(BOOL finished){
        
        [UIView animateWithDuration:0.5 delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
            // 恢复到原来的位置
            [self hide];
        } completion:^(BOOL finished) {
        }];
    }];
}
- (void)hide{
    self.alpha = 1.0f;
    
    [UIView animateWithDuration:0.5 delay:2 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        self.transform=CGAffineTransformIdentity;
    } completion:^(BOOL finished){
        defaultLabel.text = @"";
        self.hidden = YES;
    }];

}
- (void)changeMessge:(NSString *)message{
//    [defaultLabel setText:message animated:YES];
}

@end
