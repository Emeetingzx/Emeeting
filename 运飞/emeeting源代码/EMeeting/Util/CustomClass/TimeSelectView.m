//
//  TimeSelectView.m
//  tht
//
//  Created by efutureinfo on 16/2/15.
//  Copyright © 2016年 ljf. All rights reserved.
//

#import "TimeSelectView.h"
#import "Tools.h"
#import "CustomAlertView.h"

@interface TimeSelectView ()

@property (nonatomic, strong) NSMutableArray<NSString *> *ymdDataArr;

@property (nonatomic, strong) NSMutableArray<NSString *> *beginHourDataArr;

@property (nonatomic, strong) NSMutableArray<NSString *> *beginMinuteDataArr;

@property (nonatomic, strong) NSMutableArray<NSString *> *endHourDataArr;

@property (nonatomic, strong) NSMutableArray<NSString *> *endMinuteDataArr;

@property (nonatomic, assign) BOOL isLast;

@end

@implementation TimeSelectView

-(NSMutableArray<NSString *> *)ymdDataArr
{
    if (_ymdDataArr == nil)
    {
        _ymdDataArr = [[NSMutableArray alloc] init];
        
        NSDate *date = [NSDate getServerDate];
        
        //NSInteger showNum = [NSDate getCanOffsetIndexWithToday:date];
        
        NSString *maxDateSte = [[NSUserDefaults standardUserDefaults] objectForKey:@"maxDate"];
        
        NSInteger showNum = [NSDate calcDaysFromBegin:date end:[NSDate dateWithString:maxDateSte format:[NSDate ymdFormat]]]+1;
        
        for (int i = 0; i < showNum; i++)
        {
            date = i== 0?[date offsetDays:0]:[date offsetDays:1];
            
            NSString *dataStr = [NSString stringWithFormat:@"%@ %@",[NSDate stringWithDate:date format:[date ymdFormat]],[NSDate getWeekdayWithDate:date]];
            
            [_ymdDataArr addObject:dataStr];
        }
    }
    return _ymdDataArr;
}

-(NSMutableArray<NSString *> *)beginHourDataArr
{
    if (_beginHourDataArr == nil)
    {
        _beginHourDataArr = [[NSMutableArray alloc] init];
        
        for (int i = 8; i<22; i++)
        {
            [_beginHourDataArr addObject:i>=10?[NSString stringWithFormat:@"%d",i]:[NSString stringWithFormat:@"0%d",i]];
        }
    }
    
    return _beginHourDataArr;
}

-(NSMutableArray<NSString *> *)beginMinuteDataArr
{
    if (_beginMinuteDataArr == nil)
    {
        _beginMinuteDataArr = [[NSMutableArray alloc] init];
        
        [_beginMinuteDataArr addObject:@"00"];
        
        [_beginMinuteDataArr addObject:@"30"];
    }
    
    return _beginMinuteDataArr;
}


-(NSMutableArray<NSString *> *)endHourDataArr
{
    if (_endHourDataArr == nil)
    {
        _endHourDataArr = [[NSMutableArray alloc] init];
        
        for (int i = 8; i<22; i++)
        {
            [_endHourDataArr addObject:i>=10?[NSString stringWithFormat:@"%d",i]:[NSString stringWithFormat:@"0%d",i]];
        }
    }
    
    return _endHourDataArr;
}

-(NSMutableArray<NSString *> *)endMinuteDataArr
{
    if (_endMinuteDataArr == nil)
    {
        _endMinuteDataArr = [[NSMutableArray alloc] init];
        
        [_endMinuteDataArr addObject:@"00"];
        
        [_endMinuteDataArr addObject:@"30"];
    }
    
    return _endMinuteDataArr;
}

- (void)setNotLimitIsHidden:(BOOL)notLimitIsHidden
{
    _notLimitIsHidden = notLimitIsHidden;
    
    self.notLimitButton.hidden = notLimitIsHidden;
    
    if (_notLimitIsHidden)
    {
        _beginMinuteDataArr = [[NSMutableArray alloc] init];
        _endMinuteDataArr = [[NSMutableArray alloc] init];
        for (int i = 0; i<60; i+=5)
        {
            NSString *timeStr = i>=10?[NSString stringWithFormat:@"%d",i]:[NSString stringWithFormat:@"0%d",i];
            [_beginMinuteDataArr addObject:timeStr];
            [_endMinuteDataArr addObject:timeStr];
        }
        _beginHourDataArr = [[NSMutableArray alloc] init];
        _endHourDataArr = [[NSMutableArray alloc] init];
        for (int j = 0; j<24; j++)
        {
            NSString *timeStr1 = j>=10?[NSString stringWithFormat:@"%d",j]:[NSString stringWithFormat:@"0%d",j];
            [_beginHourDataArr addObject:timeStr1];
            [_endHourDataArr addObject:timeStr1];
        }
        [self.startTimePicker reloadAllComponents];
        [self.endTimePicker reloadAllComponents];


    }
}

- (void)setSelectedTime:(NSString *)selectedTime
{
    _selectedTime = selectedTime;
    
    NSArray *array = [[NSDate getImterValTimeWithSelecttime:selectedTime] componentsSeparatedByCharactersInSet:[NSCharacterSet characterSetWithCharactersInString:@":-"]];
    
    [self.dayPicker selectRow:[NSDate calcDaysFromBegin:[NSDate getServerDate] end:[NSDate getymdDateWithSelectedTime:selectedTime]] inComponent:0 animated:NO];
    
    [self.startTimePicker selectRow:[self enqualIndexWithDefaultString:array[0] andStringArr:self.beginHourDataArr] inComponent:0 animated:NO];
    [self.startTimePicker selectRow:[self enqualIndexWithDefaultString:array[1] andStringArr:self.beginMinuteDataArr] inComponent:2 animated:NO];
    
    [self.endTimePicker selectRow:[self enqualIndexWithDefaultString:array[2] andStringArr:self.endHourDataArr] inComponent:0 animated:NO];
    [self.endTimePicker selectRow:[self enqualIndexWithDefaultString:array[3] andStringArr:self.endMinuteDataArr] inComponent:2 animated:NO];
    
    self.ymdString = [self.ymdDataArr[[NSDate calcDaysFromBegin:[NSDate getServerDate] end:[NSDate getymdDateWithSelectedTime:selectedTime]]] componentsSeparatedByString:@" "].firstObject;
    
    self.beginHourString = [array[0] integerValue] >=10?[NSString stringWithFormat:@"%d",(int)[array[0] integerValue]]:[NSString stringWithFormat:@"0%d",(int)[array[0] integerValue]];
    self.beginMinuteString = [array[1] integerValue] >=10?[NSString stringWithFormat:@"%d",(int)[array[1] integerValue]]:[NSString stringWithFormat:@"0%d",(int)[array[1] integerValue]];
    
    self.endHourString = [array[2] integerValue] >=10?[NSString stringWithFormat:@"%d",(int)[array[2] integerValue]]:[NSString stringWithFormat:@"0%d",(int)[array[2] integerValue]];;
    self.endMinuteString = [array[3] integerValue] >=10?[NSString stringWithFormat:@"%d",(int)[array[3] integerValue]]:[NSString stringWithFormat:@"0%d",(int)[array[3] integerValue]];;
    
    if (!_notLimitIsHidden && [self.endHourString isEqualToString:@"21"])
    {
        [self.endMinuteDataArr removeAllObjects];
        [self.endMinuteDataArr addObject:@"00"];
        [self.endTimePicker reloadComponent:2];
        self.endMinuteString = @"00";
    }

}


- (IBAction)notLimitAction:(UIButton *)sender
{
    if ([self.delegate respondsToSelector:@selector(TimeSelectViewDidClickNotlimitButton:)])
    {
        [self.delegate TimeSelectViewDidClickNotlimitButton:self];
    }
}

- (IBAction)cancel:(UIButton *)sender
{
    if ([self.delegate respondsToSelector:@selector(TimeSelectViewDidCancel:)])
    {
        [self.delegate TimeSelectViewDidCancel:self];
    }
}


- (IBAction)confirm:(UIButton *)sender
{
    if (![self checkSelectedTime])
    {
        NSLog(@"开始时间不能早于当前时间");
        
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:[UIScreen mainScreen].bounds Message:@"开始时间不能早于当前时间" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
        [self.superview addSubview:alertView];
        
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
        return;
    }
    
    if (![self checkSelectedStratAndEndTime])
    {
        NSLog(@"开始时间不能晚于结束时间");
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:[UIScreen mainScreen].bounds Message:@"开始时间不能晚于结束时间" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
        [self.superview addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
        return;
    }
    
    self.selectedTimeString = [NSString stringWithFormat:@"%@ %@:%@-%@:%@",self.ymdString,self.beginHourString,self.beginMinuteString,self.endHourString,self.endMinuteString];
    
    if ([self.delegate respondsToSelector:@selector(TimeSelectView:didSelectTime:)])
    {
        [self.delegate TimeSelectView:self didSelectTime:self.selectedTimeString];
    }
}

- (BOOL)checkSelectedTime
{
    if ([[NSDate getServerDate] isSameDay:[NSDate dateWithString:self.ymdString format:[NSDate ymdFormat]]])
    {
        if ([[NSDate getServerDate] hour] < [self.beginHourString integerValue])
        {
            return YES;
            
        }else if ([[NSDate getServerDate] hour] == [self.beginHourString intValue])
        {
            if ([[NSDate getServerDate] minute] < [self.beginMinuteString integerValue])
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

- (BOOL)checkSelectedStratAndEndTime
{
    if ([self.endHourString integerValue] < [self.beginHourString integerValue])
    {
        return NO;
        
    }else if([self.endHourString integerValue] == [self.beginHourString integerValue])
    {
        if ([self.endMinuteString integerValue] <= [self.beginMinuteString integerValue])
        {
            return NO;
        }else
        {
            return YES;
        }
    }else
    {
        return YES;
    }
}

-(NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{

    if (pickerView.tag == DayPickerTag)
    {
        return self.ymdDataArr.count;
    }else if(pickerView.tag == StartTimePickerTag)
    {
        if (component == 0)
        {
            return self.beginHourDataArr.count;
        }else if(component == 1)
        {
            return 1;
        }else
        {
            return self.beginMinuteDataArr.count;
        }
    }else
    {
        if (component == 0)
        {
            return self.endHourDataArr.count;
        }else if(component == 1)
        {
            return 1;
        }else
        {
            return self.endMinuteDataArr.count;
        }
    }
}

-(NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    if (pickerView.tag == DayPickerTag)
    {
        return 1;
    }else
    {
        return 3;
    }
}


-(CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component
{
    if (pickerView.tag == DayPickerTag)
    {
        return 120;
    }else
    {
        if (component == 0)
        {
            return 30;
        }else if(component == 1)
        {
            return 3;
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
    
    if (pickerView.tag == DayPickerTag)
    {
        label = [[UILabel alloc] initWithFrame:CGRectMake(0.0f, 0.0f, 120.0f, 30.0f)];
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
            
            if(component == 1)
            {
                label.text = @":";
            }else
            {
                if (pickerView.tag == StartTimePickerTag)
                {
                    if (component == 0)
                    {
                        label.text = self.beginHourDataArr[row];
                    }else
                    {
                        label.text = self.beginMinuteDataArr[row];
                    }
                    
                }else
                {
                    if (component == 0)
                    {
                        label.text = self.endHourDataArr[row];
                    }else
                    {
                        label.text = self.endMinuteDataArr[row];
                    }
                }
            }

        }
    }

    [label setFont:[UIFont boldSystemFontOfSize:14]];
    label.backgroundColor = [UIColor clearColor];
    label.textAlignment = NSTextAlignmentCenter;
    label.textColor = [UIColor colorWithRed:51/255.0 green:51/255.0 blue:51/255.0 alpha:1.0];
    return label;
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    if (pickerView.tag == DayPickerTag)
    {
        self.ymdString = [[self.ymdDataArr[row] componentsSeparatedByString:@" "] firstObject];
        
    }else if (pickerView.tag == StartTimePickerTag)
    {
        if (component == 0)
        {
           self.beginHourString = self.beginHourDataArr[row];
            
            if ([self.beginHourString integerValue] > [self.endHourString integerValue])
            {
                [self.endTimePicker selectRow:[self enqualIndexWithDefaultString:self.beginHourString andStringArr:self.endHourDataArr] inComponent:0 animated:YES];
                self.endHourString = self.beginHourString;
            }
            
            if (!_notLimitIsHidden)
            {
                if ([self.beginHourString integerValue] == 21)
                {
                    [self.beginMinuteDataArr removeAllObjects];
                    [self.beginMinuteDataArr addObject:@"00"];
                    [self.startTimePicker reloadComponent:2];
                    
                    [self.endMinuteDataArr removeAllObjects];
                    [self.endMinuteDataArr addObject:@"00"];
                    [self.endTimePicker reloadComponent:2];
                    
                    self.endMinuteString = @"00";
                    self.beginMinuteString = @"00";
                }else
                {
                    [self.beginMinuteDataArr removeAllObjects];
                    [self.beginMinuteDataArr addObject:@"00"];
                    [self.beginMinuteDataArr addObject:@"30"];
                    [self.startTimePicker reloadComponent:2];
                }

            }
        }else if (component == 2)
        {
           self.beginMinuteString = self.beginMinuteDataArr[row];
            
        }
    }else if (pickerView.tag == EndTimePickerTag)
    {
        if (component == 0)
        {
            self.endHourString = self.endHourDataArr[row];
            
            
            if ([self.endHourString integerValue] < [self.beginHourString integerValue])
            {
                [self.startTimePicker selectRow:[self enqualIndexWithDefaultString:self.endHourString andStringArr:self.beginHourDataArr] inComponent:0 animated:YES];
                self.beginHourString = self.endHourString;
            }
            
            if (!_notLimitIsHidden)
            {
                if ([self.endHourString integerValue] == 21)
                {
                    [self.endMinuteDataArr removeAllObjects];
                    [self.endMinuteDataArr addObject:@"00"];
                    [self.endTimePicker reloadComponent:2];
                    
                    self.endMinuteString = @"00";
                }else
                {
                    [self.beginMinuteDataArr removeAllObjects];
                    [self.beginMinuteDataArr addObject:@"00"];
                    [self.beginMinuteDataArr addObject:@"30"];
                    [self.startTimePicker reloadComponent:2];
                    
                    [self.endMinuteDataArr removeAllObjects];
                    [self.endMinuteDataArr addObject:@"00"];
                    [self.endMinuteDataArr addObject:@"30"];
                    [self.endTimePicker reloadComponent:2];
                }

            }
            
        }else if (component == 2)
        {
            self.endMinuteString = self.endMinuteDataArr[row];
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
