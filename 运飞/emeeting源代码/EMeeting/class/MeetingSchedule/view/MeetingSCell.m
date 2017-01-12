//
//  MeetingSCell.m
//  会议预定
//
//  Created by efutureinfo on 16/2/23.
//  Copyright © 2016年 efutureinfo. All rights reserved.
//

#import "MeetingSCell.h"
#import "NSDate+LJFDate.h"
#import "CustomAlertView.h"
#import "AppDelegate.h"


@interface MeetingSCell ()

@property (nonatomic, strong) NSMutableArray<UIButton *> *selectedButtonArr;

@property (nonatomic, strong) NSMutableArray<UIButton *> *notAllowSelectedButtonArr;

@property (nonatomic, assign) BOOL isSelectContinue;

@property (nonatomic, assign) BOOL isHaveSelectTime;

@property (nonatomic, assign) BOOL isShow;

@end

@implementation MeetingSCell

- (void)awakeFromNib
{
    if ([UIScreen mainScreen].bounds.size.width == 320)
    {
        self.imageLeft1.constant = 5;
        self.imageLeft2.constant = 0;
        self.imageLeft3.constant = 5;
        self.imageLeft4.constant = 0;
    }else if ([UIScreen mainScreen].bounds.size.height > 667)
    {
        self.imageLeft1.constant = 40;
        self.imageLeft3.constant = 40;
    }

}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

- (void)setMeetingRoomInfo:(MeetingRoomInfo *)meetingRoomInfo
{
    _meetingRoomInfo = meetingRoomInfo;
    
    self.meetingNameLabel.text = _meetingRoomInfo.meetingRoomName;
    self.numberLabel.text = [NSString stringWithFormat:@"规模：%@",_meetingRoomInfo.meetingRoomScale];
    
    switch ([_meetingRoomInfo.projectorState intValue])
    {
        case 0:
            self.projectorLabel.text = @"投影：没有";
            break;
            
        case 1:
            self.projectorLabel.text = @"投影：不支持双流";
            break;
            
        case 2:
            self.projectorLabel.text = @"投影：支持双流接收";
            break;
            
        case 3:
            self.projectorLabel.text = @"投影：支持双流接收发送";
            break;
            
        default:
            self.projectorLabel.text = @"投影：没有";
            break;
    }
    
    switch ([_meetingRoomInfo.televisionState intValue])
    {
        case 0:
            self.televisionLabel.text = @"电视：没有";
            break;
            
        case 1:
            self.televisionLabel.text = @"电视：有";
            break;
            
        default:
            self.televisionLabel.text = @"电视：没有";
            break;
    }
    
    switch ([_meetingRoomInfo.phoneState intValue])
    {
        case 0:
            self.phoneLabel.text = @"电话：没有";
            break;
            
        case 1:
            self.phoneLabel.text = @"电话：POLYCOM";
            break;
            
        case 2:
            self.phoneLabel.text = @"电话：USB-Phone";
            break;
            
        default:
            self.phoneLabel.text = @"电话：没有";
            break;
    }
    
    _notAllowSelectedButtonArr = [[NSMutableArray alloc] init];
    for (MeetingInfo *info in _meetingRoomInfo.haveBookingMeetingInfos)
    {
        [self setNotAllowToSelectTimeButtonWithBegindate:info.beginDate andEnddate:info.endDate];
    }
}

- (void)setSelectedTime:(NSString *)selectedTime
{
    _selectedTime = selectedTime;
    
    _selectedButtonArr = [[NSMutableArray alloc] init];
    
    self.mdayLabel.text = [NSDate monthAndDayChineseStringWithSelectTime:selectedTime];
    
    self.msecondLabel.text = [NSDate getImterValTimeWithSelecttime:selectedTime];
    
    if (_isNotLimitTime)
    {
        _isNotLimitTime = NO;
    }else
    {
        [self setAreadySelectTimeButton];
    }
    
    [self setDefaultNotAllowToSelectTimeButtonWithNowDate:[NSDate getServerDate]];
    
    [self setViewAttributeWithSelectedButtonArr];
}

- (void)setAreadySelectTimeButton
{
    if (![NSDate checkAreadySelectTime:_selectedTime])
    {
        if (self.isShow)
        {
            return;
        }else
        {
            self.isShow = YES;
        }
        
        AppDelegate *delegate = [UIApplication sharedApplication].delegate;
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:delegate.window.bounds Message:@"该会议时间已过期，请重新选择!" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
        [delegate.window addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.5];
        return;
    }
    
    NSInteger begindex = [NSDate getBeginIndexWithBegindateStr:[NSDate getBeginDateWithSelectedTime:_selectedTime]];
    
    NSInteger endIndex = [NSDate getEndIndexWithEndDateStr:[NSDate getEndDateWithSelectedTime:_selectedTime]]>26?26:[NSDate getEndIndexWithEndDateStr:[NSDate getEndDateWithSelectedTime:_selectedTime]];
    
    for (NSInteger i = SelectTimeButtonBeginTag + begindex; i<SelectTimeButtonBeginTag + endIndex; i++)
    {
        UIButton *button = (UIButton *)[self.timeSelectBgView viewWithTag:i];
        
        if (![self.notAllowSelectedButtonArr containsObject:button])
        {
            button.selected = YES;
            if (button) {
                [self.selectedButtonArr addObject:button];
            }
            
        }
    }
}

- (IBAction)cancel:(id)sender
{
    if ([self.delegate respondsToSelector:@selector(MeetingSCellDidClickCancel:)])
    {
        [self.delegate MeetingSCellDidClickCancel:self];
    }
}


- (IBAction)schedule:(id)sender
{
    AppDelegate *delegate = [UIApplication sharedApplication].delegate;
    
    if (!self.isHaveSelectTime)
    {
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:delegate.window.bounds Message:@"请选择预定会议时间" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
        [delegate.window addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
        return;
    }
    
    if (!self.isSelectContinue)
    {
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:delegate.window.bounds Message:@"时间选择不连续，请重新选择" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
        [delegate.window addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
        return;
    }
    
    if ([self.delegate respondsToSelector:@selector(MeetingSCellDidClickConfirm:andSelectedTime:)])
    {
        [self.delegate MeetingSCellDidClickConfirm:self andSelectedTime:_selectedTime];
    }
}

- (IBAction)selectTimeClick:(UIButton *)sender
{
    sender.selected = !sender.selected;
    
    if (sender.selected)
    {
        [self.selectedButtonArr addObject:sender];
    }else
    {
        [self.selectedButtonArr removeObject:sender];
    }
    
    [self setViewAttributeWithSelectedButtonArr];
    
}


- (void)setNotAllowToSelectTimeButtonWithBegindate:(NSString *)begindate andEnddate:(NSString *)enddate
{
    NSInteger begindex = [NSDate getBeginIndexWithBegindateStr:begindate];
    
    NSInteger endIndex = [NSDate getEndIndexWithEndDateStr:enddate];
    
    for (NSInteger i = SelectTimeButtonBeginTag + begindex; i<SelectTimeButtonBeginTag + endIndex; i++)
    {
        UIButton *button = (UIButton *)[self.timeSelectBgView viewWithTag:i];
        
        button.enabled = NO;
        
        [self.notAllowSelectedButtonArr addObject:button];
    }
}

#pragma mark -设置早于当前时间不可点击
- (void)setDefaultNotAllowToSelectTimeButtonWithNowDate:(NSDate *)nowdate
{
    if ([nowdate isSameDay:[NSDate getDateWithSelectTime:_selectedTime]])
    {
        NSInteger endIndex = ([[nowdate offsetHours:-1] hour]-8 + 1)*2;
        
        if ([nowdate minute]<30)
        {
            endIndex = endIndex + 1;
        }else
        {
            endIndex = endIndex + 2;
        }
        
        for (int i = SelectTimeButtonBeginTag; i<SelectTimeButtonBeginTag + endIndex; i++)
        {
            UIButton *button = (UIButton *)[self.timeSelectBgView viewWithTag:i];
            
            button.enabled = NO;
        }
    }
}

- (void)setViewAttributeWithSelectedButtonArr
{
    if (self.selectedButtonArr.count == 0)
    {
        self.msecondLabel.hidden = YES;
        self.isHaveSelectTime = NO;
    }else
    {
        self.isHaveSelectTime = YES;
        self.msecondLabel.hidden = ![self checkToIsSelectButtonContinue];
        self.isSelectContinue = [self checkToIsSelectButtonContinue];
    }
}

- (BOOL)checkToIsSelectButtonContinue
{
    NSMutableArray *p = [[NSMutableArray alloc] init];
    for (UIButton *button in self.selectedButtonArr)
    {
        NSString *buttonTag = [NSString stringWithFormat:@"%ld",button.tag - SelectTimeButtonBeginTag];
        [p addObject:buttonTag];
    }
    
    [p sortUsingComparator:^NSComparisonResult(id obj1, id obj2)
    {
        NSString *a = (NSString *)obj1;
        NSString *b = (NSString *)obj2;
        
        int aNum = [a intValue];
        int bNum = [b intValue];
        
        if (aNum > bNum) {
            return NSOrderedDescending;
        }
        else if (aNum < bNum){
            return NSOrderedAscending;
        }
        else {
            return NSOrderedSame;
        }
    }];
    
    for (int i = 0; i<p.count-1; i++)
    {
        if ([p[i] integerValue]+1 != [p[i+1] integerValue])
        {
            return NO;
        }
    }
    
    [self setSelectedTimeWithisSelectContinueWithDataArr:p];
    return YES;
}

- (void)setSelectedTimeWithisSelectContinueWithDataArr:(NSMutableArray *)dataArr
{
    NSInteger beginIndex = [dataArr.firstObject integerValue];
    NSInteger endIndex = [dataArr.lastObject integerValue];
        
    _selectedTime = [NSDate getSelectTimeWithBeginIndex:beginIndex andEndIndex:endIndex+1 andSelectTime:_selectedTime];
    
    self.mdayLabel.text = [NSDate monthAndDayChineseStringWithSelectTime:_selectedTime];
    
    self.msecondLabel.text = [NSDate getImterValTimeWithSelecttime:_selectedTime];
}



@end
