//
//  CustomAlertView.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "CustomAlertView.h"
#import "Tools.h"

@interface CustomAlertView ()

@end

@implementation CustomAlertView

- (instancetype)initWithFrame:(CGRect)frame delegate:(id)delegate Message:(NSString *)message firstButtonTitle:(NSString *)firstButtonTitle secondButtonTitle:(NSString *)secondButtonTitle ImageName:(NSString *)imageName andImageSize:(CGSize)imagesize AlertWidth:(CGFloat)alertwidth
{
    if (self = [self initWithFrame:frame])
    {
        self.delegate = delegate;
        [self loadAlertViewWithMessage:message firstButtonTitle:firstButtonTitle secondButtonTitle:secondButtonTitle ImageName:imageName andImageSize:imagesize AlertWidth:alertwidth];
    }
    
    return self;
}

-(instancetype)initWithFrame:(CGRect)frame Message:(NSString *)message ImageName:(NSString *)imageName andImageSize:(CGSize)imagesize AlertWidth:(CGFloat)alertwidth
{
    if (self = [self initWithFrame:frame])
    {
        [self loadAlertViewWithMessage:message ImageName:imageName andImageSize:imagesize AlertWidth:alertwidth];
    }
    
    return self;
}

- (void)loadAlertViewWithMessage:(NSString *)message ImageName:(NSString *)imageName andImageSize:(CGSize)imagesize AlertWidth:(CGFloat)alertwidth
{
    self.backgroundColor = [UIColor colorWithWhite:0 alpha:0.5];
    
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake((APPW - alertwidth)/2.0, (APPH -60)/2.0, alertwidth, 60)];
    view.backgroundColor = [UIColor whiteColor];
    [self addSubview:view];

    [self loadTitleAndImageWithSupView:view Message:message ImageName:imageName andImageSize:imagesize AlertWidth:alertwidth];
}

- (void)loadAlertViewWithMessage:(NSString *)message firstButtonTitle:(NSString *)firstButtonTitle secondButtonTitle:(NSString *)secondButtonTitle ImageName:(NSString *)imageName andImageSize:(CGSize)imagesize AlertWidth:(CGFloat)alertwidth
{
    self.backgroundColor = [UIColor colorWithWhite:0 alpha:0.5];
    
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake((APPW - alertwidth)/2.0, (APPH -100)/2.0, alertwidth, 100)];
    view.backgroundColor = [UIColor whiteColor];
    [self addSubview:view];
    
    [self loadTitleAndImageWithSupView:view Message:message ImageName:imageName andImageSize:imagesize AlertWidth:alertwidth];
    
    UIView *bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 60, alertwidth, 40)];
    bgView.backgroundColor = RGBA(255, 150, 0, 1.0);
    [view addSubview:bgView];
    
    self.firstButton = [UIButton buttonWithType:UIButtonTypeCustom];
    self.firstButton.frame = bgView.bounds;
    [self.firstButton setTitle:firstButtonTitle forState:UIControlStateNormal];
    [self.firstButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.firstButton addTarget:self action:@selector(firstButtonClick) forControlEvents:UIControlEventTouchUpInside];
    [bgView addSubview:self.firstButton];
    
    if (firstButtonTitle && secondButtonTitle)
    {
         self.firstButton.frame = CGRectMake(0, 0, (alertwidth-1)/2.0, 40);
        
        self.line = [[UIView alloc] initWithFrame:CGRectMake((alertwidth-1)/2.0, 0, 1, 40)];
        self.line.backgroundColor = [UIColor whiteColor];
        [bgView addSubview:self.line];
        
        self.secondButton = [UIButton buttonWithType:UIButtonTypeCustom];
        self.secondButton.frame = CGRectMake((alertwidth-1)/2.0 + 1,0, (alertwidth-1)/2.0, 40);
        [self.secondButton setTitle:secondButtonTitle forState:UIControlStateNormal];
        [self.secondButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        [self.secondButton addTarget:self action:@selector(secondButtonClick) forControlEvents:UIControlEventTouchUpInside];
        [bgView addSubview:self.secondButton];

    }
}

- (void)loadTitleAndImageWithSupView:(UIView *)view Message:(NSString *)message ImageName:(NSString *)imageName andImageSize:(CGSize)imagesize AlertWidth:(CGFloat)alertwidth
{
    if (imageName)
    {
        UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(10, (100 - imagesize.height - 40)/2.0, imagesize.width, imagesize.height)];
        imageView.image = [UIImage imageNamed:imageName];
        [view addSubview:imageView];
        
        self.lable = [[UILabel alloc] initWithFrame:CGRectMake(10 + imagesize.width + 5, 5, alertwidth - (10 + imagesize.width + 5 + 10), 50)];
        self.lable.text = message;
        self.lable.numberOfLines = 0;
        self.lable.font = [UIFont systemFontOfSize:16];
        [view addSubview:self.lable];
    }else
    {
        self.lable = [[UILabel alloc] initWithFrame:CGRectMake(5, 0, alertwidth - 10, 60)];
        self.lable.font = [UIFont systemFontOfSize:16];
        self.lable.text = message;
        self.lable.numberOfLines = 0;
        //self.lable.textAlignment = NSTextAlignmentCenter;
        [view addSubview:self.lable];
        
        NSLog(@"height == %f",[Tools heightOfString:message font:[UIFont systemFontOfSize:16] width:alertwidth-10]);
        
        if ([Tools heightOfString:message font:[UIFont systemFontOfSize:16] width:alertwidth-10] > 30)
        {
            self.lable.textAlignment = NSTextAlignmentLeft;
        }else
        {
            self.lable.textAlignment = NSTextAlignmentCenter;
        }
    }

}

- (void)firstButtonClick
{
    if ([self.delegate respondsToSelector:@selector(CustomAlertView:andButtonClickIndex:)])
    {
        [self.delegate CustomAlertView:self andButtonClickIndex:0];
    }
    [self removeFromSuperview];
}

- (void)secondButtonClick
{
    if ([self.delegate respondsToSelector:@selector(CustomAlertView:andButtonClickIndex:)])
    {
        [self.delegate CustomAlertView:self andButtonClickIndex:1];
    }
    [self removeFromSuperview];
}

- (void)setLableAttributedColor:(UIColor *)color range:(NSRange)range
{

    NSMutableAttributedString *noteStr = [[NSMutableAttributedString alloc] initWithString:self.lable.text];
    
    [noteStr addAttribute:NSForegroundColorAttributeName value:color range:range];
    
    [self.lable setAttributedText:noteStr] ;
    
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    if (self.tapIsHidden)
    {
        if ([self.delegate respondsToSelector:@selector(CustomAlertViewTouchHidden:)])
        {
            [self.delegate CustomAlertViewTouchHidden:self];
        }
        
        [self removeFromSuperview];
    }
}

@end
