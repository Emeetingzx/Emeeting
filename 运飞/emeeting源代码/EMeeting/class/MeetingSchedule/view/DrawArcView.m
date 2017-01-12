//
//  DrawView.m
//  画图(下载实时状态)
//
//  Created by qianfeng on 15/9/5.
//  Copyright (c) 2015年 ljf. All rights reserved.
//

#import "DrawArcView.h"

@implementation DrawArcView


// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    CGContextRef context = UIGraphicsGetCurrentContext();
    
    CGContextAddArc(context, rect.size.width/2, rect.size.height/2, (rect.size.width - 5)/2, -M_PI_2, 2*M_PI - M_PI_2, 0);
    CGContextSetRGBStrokeColor(context, 204/255.0, 204/255.0, 204/255.0, 1.0);
    CGContextSetLineWidth(context, 5);
    CGContextStrokePath(context);
    
    CGContextAddArc(context, rect.size.width/2, rect.size.height/2, (rect.size.width - 5)/2, -M_PI_2, self.percentValue*2*M_PI - M_PI_2, 0);
    CGContextSetRGBStrokeColor(context, 1.0, (9*16+6)/255.0, 0, 1.0);
    CGContextSetLineWidth(context, 5);
    CGContextStrokePath(context);
}

-(void)setPercentValue:(CGFloat)percentValue
{
    _percentValue = percentValue;
    
    [self setNeedsDisplay];
}

@end
