//
//  ServeTimeSelectView.m
//  tht
//
//  Created by efutureinfo on 16/2/15.
//  Copyright © 2016年 ljf. All rights reserved.
//

#import "ServeTimeSelectView.h"
#import "NSDate+LJFDate.h"
#import "CustomAlertView.h"
@interface ServeTimeSelectView ()

@property (nonatomic, strong) NSMutableArray<NSString *> *ymdDataArr;

@property (nonatomic, strong) NSMutableArray<NSString *> *hourDataArr;

@property (nonatomic, strong) NSMutableArray<NSString *> *minuteDataArr;

@end

@implementation ServeTimeSelectView

-(NSMutableArray<NSString *> *)ymdDataArr
{
    if (_ymdDataArr == nil)
    {
        _ymdDataArr = [[NSMutableArray alloc] init];
        
        NSDate *date = [NSDate getServerDate];
        
        for (int i = 0; i<15; i++)
        {
            date = i== 0?[date offsetDays:0]:[date offsetDays:1];
            
            NSString *dataStr = [NSString stringWithFormat:@"%@ %@",[NSDate stringWithDate:date format:[date ymdFormat]],[NSDate getWeekdayWithDate:date]];
            
            [_ymdDataArr addObject:dataStr];
        }
    }
    return _ymdDataArr;
}

-(NSMutableArray<NSString *> *)hourDataArr
{
    if (_hourDataArr == nil)
    {
        _hourDataArr = [[NSMutableArray alloc] init];
        
        for (int i = 8; i<22; i++)
        {
            [_hourDataArr addObject:i>=10?[NSString stringWithFormat:@"%d",i]:[NSString stringWithFormat:@"0%d",i]];
        }
    }
    
    return _hourDataArr;
}

-(NSMutableArray<NSString *> *)minuteDataArr
{
    if (_minuteDataArr == nil)
    {
        _minuteDataArr = [[NSMutableArray alloc] init];
        for (int i = 0; i<60;)
        {
            if (i>=10) {
                [_minuteDataArr addObject:[NSString stringWithFormat:@"%d",i]];
                i=i+5;
            }else{
                [_minuteDataArr addObject:[NSString stringWithFormat:@"0%d",i]];
                i=i+5;
            }
            
        }
    }
    
    return _minuteDataArr;
}

- (void)setSelectedTime:(NSString *)selectedTime
{
    
    NSArray  *ymdArr=[selectedTime componentsSeparatedByString:@" "];
    
    NSArray *timeArr=[ymdArr[1] componentsSeparatedByString:@":"];
    self.hourString=timeArr[0];
    self.minuteString=@"00";
    
    self.ymdString=ymdArr[0];

    [self.serveTimeMDayPicker selectRow:[NSDate calcDaysFromBegin:[NSDate getServerDate] end:[NSDate dateWithString:ymdArr[0] format:[NSDate ymdFormat]]]  inComponent:0 animated:NO];
    
    [self.serveTimeHSecondPicker selectRow:[self enqualIndexWithDefaultString:timeArr[0] andStringArr:self.hourDataArr] inComponent:0 animated:NO];
    
    [self.serveTimeHSecondPicker selectRow:[self enqualIndexWithDefaultString:@"00" andStringArr:self.minuteDataArr] inComponent:2 animated:NO];
    
}

- (IBAction)cancel:(UIButton *)sender
{
    if ([self.delegate respondsToSelector:@selector(serveTimeSelectViewDidCancel:)])
    {
        [self.delegate serveTimeSelectViewDidCancel:self];
    }

}

- (IBAction)confirm:(UIButton *)sender
{
    NSLog(@"55555%@",_ymdString);
    if (![self checkSelectedTime])
    {
        NSLog(@"不能早于当前时间");
        
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:[UIScreen mainScreen].bounds Message:@"不能早于当前时间" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
        alertView.backgroundColor = [UIColor clearColor];
        [self.superview addSubview:alertView];
        
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
        return;
    }
    NSString *selectedTimeString = [NSString stringWithFormat:@"%@ %@:%@",self.ymdString,self.hourString,self.minuteString];
    
    if ([self.delegate respondsToSelector:@selector(serveTimeSelectView:didSelectTime:)])
    {
        [self.delegate serveTimeSelectView:self didSelectTime:selectedTimeString];
    }


}

- (BOOL)checkSelectedTime
{
    if ([[NSDate getServerDate] isSameDay:[NSDate dateWithString:self.ymdString format:[NSDate ymdFormat]]])
    {
        if ([[NSDate getServerDate] hour] < [self.hourString integerValue])
        {
            return YES;
            
        }else if ([[NSDate getServerDate] hour] == [self.hourString intValue])
        {
            if ([[NSDate getServerDate] minute] < [self.minuteString integerValue])
            {
                return YES;
            }else
            {
                return NO;
            }
        }else
        {
            return NO;
        }
    }
    else
    {
        return YES;
    }
}


-(NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    
    if (pickerView.tag == ServeTimeMDayPickerTag)
    {
        return self.ymdDataArr.count;
    }else
    {
        if (component == 0)
        {
            return self.hourDataArr.count;
        }else if(component == 1)
        {
            return 1;
        }else
        {
            return self.minuteDataArr.count;
        }
    }
}

-(NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    if (pickerView.tag == ServeTimeMDayPickerTag)
    {
        return 1;
    }else
    {
        return 3;
    }
}


-(CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component
{
    if (pickerView.tag == ServeTimeMDayPickerTag)
    {
        return 138;
    }else
    {
        if (component == 0)
        {
            return 30;
        }else if(component == 1)
        {
            return 5;
        }else
        {
            return 30;
        }
        
    }
}

- (CGFloat)pickerView:(UIPickerView *)pickerView rowHeightForComponent:(NSInteger)component
{
    return 30;
}

- (UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row
          forComponent:(NSInteger)component reusingView:(UIView *)view
{
    UILabel *label = nil;
    
    if (pickerView.tag == ServeTimeMDayPickerTag)
    {
        label = [[UILabel alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 138.0f, 30.0f)];
        label.text = _ymdDataArr[row];
    }else
    {
        label = [[UILabel alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 10.0f, 30.0f)];
        
        if(component == 1)
        {
            label.text = @":";
        }else
        {
            label = [[UILabel alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 30.0f, 30.0f)];
            
            if (component == 0)
            {
                label.text = self.hourDataArr[row];
            }else
            {
                label.text = self.minuteDataArr[row];
            }
        }
    }
    
    [label setFont:[UIFont boldSystemFontOfSize:16]];
    label.backgroundColor = [UIColor clearColor];
    label.textAlignment = NSTextAlignmentCenter;
    label.textColor = [UIColor colorWithRed:51/255.0 green:51/255.0 blue:51/255.0 alpha:1.0];
    return label;
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    if (pickerView.tag == ServeTimeMDayPickerTag)
    {
         self.ymdString = [[self.ymdDataArr[row] componentsSeparatedByString:@" "] firstObject];
    }else
    {
        if (component == 0)
        {
            self.hourString = self.hourDataArr[row];
            
        }else if (component == 2)
        {
            self.minuteString = self.minuteDataArr[row];
        }
    }
}
- (NSInteger)enqualIndexWithDefaultString:(NSString *)defaultString andStringArr:(NSMutableArray<NSString *> *)stringArr
{
    for (int i = 0; i < stringArr.count; i++)
    {
        if ([defaultString integerValue] == [stringArr[i] integerValue])
        {
            NSLog(@"i == %d",i);
            return i;
        }
    }
    
    return 0;
}


@end
