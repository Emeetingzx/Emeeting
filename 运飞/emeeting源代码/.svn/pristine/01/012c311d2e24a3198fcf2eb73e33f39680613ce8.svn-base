//
//  TouchRefreshView.m
//  EMeeting
//
//  Created by efutureinfo on 16/3/3.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "TouchRefreshView.h"
#import "UIColor+LJFColor.h"

@interface TouchRefreshView ()

@property (nonatomic, strong) UILabel *label;

@property (nonatomic, strong) UILabel *label1;

@end

@implementation TouchRefreshView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self)
    {
        [self loadLabel];
    }
    return self;
}

- (void)loadLabel
{
   _label = [[UILabel alloc] initWithFrame:CGRectMake((self.frame.size.width -300)/2.0,self.frame.size.height/2.0-20-5, 300, 20)];
    _label.text = @"很抱歉，没有符合条件的会议室!";
    _label.textAlignment = NSTextAlignmentCenter;
    [self addSubview:_label];
    
    _label1 = [[UILabel alloc] initWithFrame:CGRectMake((self.frame.size.width -300)/2.0,self.frame.size.height/2.0, 300, 20)];
    _label1.text = @"点击刷新";
    _label1.textAlignment = NSTextAlignmentCenter;
    _label1.textColor = [UIColor colorWithHexString:@"#FF9600"];
    [self addSubview:_label1];
}

- (void)setTitle:(NSString *)title
{
    _title = title;
    
    _label.text = title;
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    NSLog(@"点击屏幕...");
    
    if ([self.delegate respondsToSelector:@selector(touchRefreshViewDidTouch:)])
    {
        [self.delegate touchRefreshViewDidTouch:self];
    }
}

@end
