//
//  MarkedWords.m
//  RedPacket
//
//  Created by efutureinfo on 15/12/8.
//  Copyright © 2015年 itp. All rights reserved.
//

#import "MarkedWords.h"

@implementation MarkedWords

+ (void)showMarkedWordsWithMessage:(NSString *)message addToView:(UIView *)view
{
    if (view)
    {
        [self createLabelWithView:view labelText:message andIsBlock:NO andblock:nil];
    }
}

+ (void)showMarkedWordsWithMessage:(NSString *)message addToView:(UIView *)view finished:(finishBlock)block
{
    if (view)
    {
        [self createLabelWithView:view labelText:message andIsBlock:YES andblock:block];
    }
}

+ (void)createLabelWithView:(UIView *)view labelText:(NSString *)text andIsBlock:(BOOL)isBlock andblock:(finishBlock)block;
{
    UILabel *label = [[UILabel alloc] init];
    label.textColor = [UIColor whiteColor];
    label.backgroundColor = [UIColor blackColor];
    label.layer.cornerRadius = 20;
    label.font = [UIFont systemFontOfSize:17];
    label.textAlignment = NSTextAlignmentCenter;
    [label.layer setMasksToBounds:YES];
    label.alpha = 0;
    label.text = text;
    label.tag = 50;
    label.numberOfLines = 0;
    
    CGFloat width = [self widthOfString:label.text font:[UIFont systemFontOfSize:17] height:40];
    
    if (width+50 > view.frame.size.width)
    {
        CGFloat height = [self heightOfString:label.text font:[UIFont systemFontOfSize:17] width:view.frame.size.width - 50];
        label.frame = CGRectMake(10, view.frame.size.height - (height + 80), view.frame.size.width - 20, height + 20);
    }else
    {
       label.frame = CGRectMake((view.frame.size.width - width-30)/2.0, view.frame.size.height - 100, width +30, 40);
    }
    
    [view addSubview:label];
    
    __weak typeof(self) wself = self;
    
    [UIView animateWithDuration:0.5 animations:^
     {
         label.alpha = 1.0;
     } completion:^(BOOL finished)
     {
         if (isBlock)
         {
             NSDictionary *dict = @{@"lable":label,@"block":block};
             [wself performSelector:@selector(removeTishiLabelWithDict:) withObject:dict afterDelay:0.5];
         }else
         {
             [wself performSelector:@selector(removeTishiLabel:) withObject:label afterDelay:1.0];
         }
     }];
}

+ (void)removeTishiLabel:(UILabel *)label
{
    [UIView animateWithDuration:0.5 animations:^
     {
         label.alpha = 0;
     } completion:^(BOOL finished)
     {
         [label removeFromSuperview];
     }];
}

+ (void)removeTishiLabelWithDict:(NSDictionary *)dict
{
    UILabel *label = dict[@"lable"];
    finishBlock block = dict[@"block"];
    
    [UIView animateWithDuration:0.5 animations:^
     {
         label.alpha = 0;
     } completion:^(BOOL finished)
     {
         block();
         [label removeFromSuperview];
     }];
}

+(CGFloat)widthOfString:(NSString *)string font:(UIFont *)font height:(CGFloat)height
{
    NSDictionary * dict=[NSDictionary dictionaryWithObject: font forKey:NSFontAttributeName];
    CGRect rect=[string boundingRectWithSize:CGSizeMake(CGFLOAT_MAX, height) options:NSStringDrawingTruncatesLastVisibleLine|NSStringDrawingUsesFontLeading|NSStringDrawingUsesLineFragmentOrigin attributes:dict context:nil];
    return rect.size.width;
}

+(CGFloat)heightOfString:(NSString *)string font:(UIFont *)font width:(CGFloat)width
{
    CGRect bounds;
    NSDictionary * parameterDict=[NSDictionary dictionaryWithObject:font forKey:NSFontAttributeName];
    bounds=[string boundingRectWithSize:CGSizeMake(width, CGFLOAT_MAX) options:NSStringDrawingTruncatesLastVisibleLine|NSStringDrawingUsesFontLeading|NSStringDrawingUsesLineFragmentOrigin attributes:parameterDict context:nil];
    return bounds.size.height;
}



@end
