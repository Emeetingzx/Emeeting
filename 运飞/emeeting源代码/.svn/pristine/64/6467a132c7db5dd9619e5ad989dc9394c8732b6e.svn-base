//
//  PhoneOrVideoMeetingViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/1/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "PhoneOrVideoMeetingViewController.h"
#import "UIViewController+MMDrawerController.h"
#import "Tools.h"
#import "AlertTableView.h"
#import "TimeSelectView.h"
#import "NSDate+LJFDate.h"
#import "ReservePhoneOrVideoMeetingRoomHandle.h"
#import "ProgressView.h"
#import "CustomAlertView.h"
#import "MarkedWords.h"

@interface PhoneOrVideoMeetingViewController ()<UITextFieldDelegate,AlertTableViewDelegate,TimeSelectViewDelegate,CustomAlertViewDelegate>

@property (nonatomic, strong) UITextField *selectText;

@property (nonatomic, strong) NSDate *today;

@property (nonatomic, strong) MeetingInfo *meetingInfo;

@property (nonatomic, strong) ReservePhoneOrVideoMeetingRoomHandle *meetingScheduleHandle;

@property (nonatomic, copy) NSString *meetingType;
@property (nonatomic, copy) NSString *meetingLeader;
@property (nonatomic, copy) NSString *meetingLevel;
@property (nonatomic, copy) NSString *selectedTime;


@end

@implementation PhoneOrVideoMeetingViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.meetingType = @"2";
    self.meetingLeader = @"4";
    self.meetingLevel = @"3";
    
    [self setKeyBoardNoti];
    
    [self.nameText addTarget:self action:@selector(textFieldDidChange:) forControlEvents:UIControlEventEditingChanged];
    
    self.selectedTime = [NSDate getDefaultSelectedStringWithNowDate:[NSDate getServerDate]];
}

-(void)setSelectedTime:(NSString *)selectedTime
{
    _selectedTime = selectedTime;
    
    self.timeLabel.text = selectedTime;
}

#pragma mark - 点击屏幕退出编辑
-(void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [self.view endEditing:YES];
}

#pragma mark - 监听键盘，添加键盘观察者
- (void)setKeyBoardNoti
{
    //监听键盘抬起事件
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(KeyboardWillShow:) name:UIKeyboardWillShowNotification object:nil];
    
    //监听键盘掉下的事件
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(KeyboardWillHide:) name:UIKeyboardWillHideNotification object:nil];
    
}

#pragma mark - 监听键盘，键盘出现
- (void)KeyboardWillShow:(NSNotification *)noti
{
    NSDictionary *dic = noti.userInfo;
    NSValue *keyboardValue = [dic objectForKey:@"UIKeyboardFrameEndUserInfoKey"];
    CGRect keyboardRect = keyboardValue.CGRectValue;
    
    NSNumber *duration = [noti.userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey];
    
    CGFloat offset = 68 + self.selectText.frame.origin.y + 40 - keyboardRect.origin.y;
    
    __weak typeof(self) wself = self;
    if (offset >0)
    {
        self.bgViewTop.constant = -offset;
        [UIView animateWithDuration:[duration floatValue] animations:^{
            [wself.view layoutIfNeeded];
        }];
    }
}

#pragma mark - 监听键盘，键盘消失
- (void)KeyboardWillHide:(NSNotification *)noti
{
    NSNumber *duration = [noti.userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey];
    
    self.bgViewTop.constant = 0;
    __weak typeof(self) wself = self;
    [UIView animateWithDuration:[duration floatValue] animations:^{
        [wself.view layoutIfNeeded];
    }];}

#pragma mark - 开始编辑
- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    self.selectText = textField;
}

#pragma mark - 结束编辑
- (void)textFieldDidEndEditing:(UITextField *)textField
{
    NSLog(@"text == %@",textField.text);
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    
    if(textField.tag == NameTextTag)
    {
        [self.passWordText becomeFirstResponder];
    }
//    else
//    {
//        [self scheduleMeeting:nil];
//    }
    
    return YES;
}

#pragma mark - 输入框输入限制条件
- (void)textFieldDidChange:(UITextField *)textField
{
    if (textField == self.nameText)
    {
        if (textField.text.length > 50)
        {
            textField.text = [textField.text substringToIndex:50];
        }
    }
}

- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    NSString *toBeString = [textField.text stringByReplacingCharactersInRange:range withString:string];
    
    if (textField.tag == PasswordTextTag)
    {
        if (toBeString.length >6)
        {
            return NO;
        }
        
        if ([@"0123456789" rangeOfString:string].location != NSNotFound || [string isEqualToString:@""])
        {
            return YES;
        }
        else
        {
            return NO;
        }
    }
    else
    {
        return YES;
    }
}

#pragma mark - 打开左侧菜单栏
- (IBAction)openLeftMenu:(id)sender
{
    [self.view endEditing:YES];
    [self.mm_drawerController toggleDrawerSide:MMDrawerSideLeft animated:YES completion:nil];
}

#pragma mark - 点击cell按钮
- (IBAction)ButtonCellDidClick:(UIButton *)sender
{
    [self.view endEditing:YES];
    
    switch (sender.tag)
    {
        case TypeButtonTag:
        {
            AlertTableView *type = [[AlertTableView alloc] initWithFrame:self.view.bounds andSelectedButtonString:self.typeLabel.text andOtherButtons:@[@"电话会议桥",@"视频会议桥",@"取消"] andDelegate:self];
            type.tag = TypeButtonTag;
            [self.view addSubview:type];
        }
            
            break;
            
        case TimeButtonTag:
            [self loadTimeSelectView];
            
            break;
        
        case LeaderButtonTag:
        {
            AlertTableView *leader = [[AlertTableView alloc] initWithFrame:self.view.bounds andSelectedButtonString:self.leaderLabel.text andOtherButtons:@[@"二层领导",@"三层领导",@"四层领导",@"其他",@"取消"] andDelegate:self];
            leader.tag = LeaderButtonTag;
            [self.view addSubview:leader];
        }
            
            break;
            
        default:
            break;
    }
}

#pragma mark - 加载时间选择视图TimeSelectView
- (void)loadTimeSelectView
{
    UIView *view = [[UIView alloc] initWithFrame:self.view.bounds];
    view.backgroundColor = [UIColor colorWithWhite:0 alpha:0.5];
    [self.view addSubview:view];
    
    TimeSelectView *timeSelect = [[NSBundle mainBundle] loadNibNamed:@"TimeSelectView" owner:self options:0][0];
    
    timeSelect.frame = CGRectMake(0, APPH - 200, APPW, 200);
    
    timeSelect.notLimitIsHidden = YES;
    
    timeSelect.delegate = self;
    
    timeSelect.selectedTime = _selectedTime;
    
    [view addSubview:timeSelect];
    
    CGRect rect = timeSelect.frame;
    rect.origin.y = APPH;
    timeSelect.frame = rect;
    rect.origin.y = APPH-rect.size.height;
    [UIView animateWithDuration:0.2 animations:^
     {
         timeSelect.frame = rect;
     }];
}

#pragma mark - 时间选择视图TimeSelectView代理方法
- (void)TimeSelectViewDidCancel:(TimeSelectView *)timeSelectView
{
    CGRect rect = timeSelectView.frame;
    rect.origin.y = APPH;
    [UIView animateWithDuration:0.2 animations:^
     {
         timeSelectView.frame = rect;
     } completion:^(BOOL finished)
     {
         [timeSelectView.superview removeFromSuperview];
     }];
}

- (void)TimeSelectView:(TimeSelectView *)timeSelectView didSelectTime:(NSString *)selectedTimeString
{
    self.selectedTime = selectedTimeString;
    
    CGRect rect = timeSelectView.frame;
    rect.origin.y = APPH;
    [UIView animateWithDuration:0.2 animations:^
     {
         timeSelectView.frame = rect;
     } completion:^(BOOL finished)
     {
         [timeSelectView.superview removeFromSuperview];
     }];
}

#pragma mark - AlertTableView代理方法
- (void)AlertTableView:(AlertTableView *)alertTable andDidClickButtonIndex:(NSInteger)index andClickButtonIndexText:(NSString *)text
{
    if (index != CancelIndex)
    {
        if (alertTable.tag == TypeButtonTag)
        {
            self.typeLabel.text = text;
            
            if (index == 0)
            {
                self.meetingType = @"2";
                self.passWordText.placeholder = @"仅支持6位数字";
            }else
            {
                self.meetingType = @"6";
                self.passWordText.placeholder = @"非必填项,仅支持6位数字";
            }
            
        }else if (alertTable.tag == LeaderButtonTag)
        {
            self.leaderLabel.text = text;
            self.meetingLeader = [NSString stringWithFormat:@"%ld",index +1];
            
            switch (index)
            {
                case 0:
                {
                   self.rankLabel.text = @"A级";
                   self.meetingLevel = [NSString localizedStringWithFormat:@"%d",1];
                }
                break;
                    
                case 1:
                {
                   self.rankLabel.text = @"B级";
                   self.meetingLevel = [NSString localizedStringWithFormat:@"%d",2];
                }
                break;
                    
                default:
                {
                    self.rankLabel.text = @"C级";
                    self.meetingLevel = [NSString localizedStringWithFormat:@"%d",3];
                }
                    break;
            }
        }
    }
}

#pragma mark -预订会议
- (IBAction)scheduleMeeting:(UIButton *)sender
{
    if (![self checkMeetingInfo])
    {
        return;
    }
    
    [self sendScheduleMeetingRequest];
}

#pragma mark -会议信息检查
- (BOOL)checkMeetingInfo
{
    self.meetingInfo = [[MeetingInfo alloc] init];
    self.meetingInfo.meetingName = self.nameText.text;
    self.meetingInfo.meetingType = self.meetingType;
    self.meetingInfo.beginDate = [NSDate getBeginDateWithSelectedTime:_selectedTime];
    self.meetingInfo.endDate = [NSDate getEndDateWithSelectedTime:_selectedTime];
    self.meetingInfo.meetingLevel = self.meetingLevel;
    self.meetingInfo.participateMeetingLeaderLevel = self.meetingLeader;
    self.meetingInfo.meetingPassword = self.passWordText.text;
    
    if (self.meetingInfo.meetingName.length == 0)
    {
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"请输入会议名称" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
        [self.view addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
        return NO;
    }else if (![self isPureNumandCharacters:self.meetingInfo.meetingPassword]||self.meetingInfo.meetingPassword.length == 0)
    {
        if ([self.meetingInfo.meetingType isEqualToString:@"6"])
        {
            return YES;
        }else
        {
            if (self.meetingInfo.meetingPassword.length>0)
            {
                CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"会议密码输入错误,只支持数字" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
                [self.view addSubview:alertView];
                [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
                return NO;
            }else
            {
                CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"请输入会议密码" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
                [self.view addSubview:alertView];
                [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
                return NO;
            }
        }
    }else
    {
        return YES;
    }
}

- (BOOL)isPureNumandCharacters:(NSString *)string
{
    string = [string stringByTrimmingCharactersInSet:[NSCharacterSet decimalDigitCharacterSet]];
    if(string.length > 0)
    {
        return NO;
    }
    return YES;
}

#pragma mark -发送预订会议请求
- (void)sendScheduleMeetingRequest
{
    [ProgressView showHUDAddedToProgress:self.view ProgressText:@"正在预订中，请稍后..."];
    
    __weak typeof(self) wself = self;
    
    if (_meetingScheduleHandle == nil)
    {
        _meetingScheduleHandle = [[ReservePhoneOrVideoMeetingRoomHandle alloc] init];
    }
    
   [_meetingScheduleHandle sendJSONRequestWithMeetingInfo:self.meetingInfo Success:^(ResponseObject *response)
    {
        if (response.s)
        {
            CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:wself.view.bounds delegate:wself Message:[NSString stringWithFormat:@"%@已成功预订!",wself.typeLabel.text] firstButtonTitle:@"继续预订" secondButtonTitle:@"查看我的会议" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
            [wself.view addSubview:alertView];
        }else
        {
            if (response.m)
            {
                [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
            }else
            {
                [MarkedWords showMarkedWordsWithMessage:@"预订发生未知错误!" addToView:wself.view];
            }
            
        }
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
       
   } failure:^(NSError *error)
    {
        [MarkedWords showMarkedWordsWithMessage:@"网络出现问题请重新预订" addToView:wself.view];
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
    }];
}

- (void)CustomAlertView:(CustomAlertView *)customAlert andButtonClickIndex:(NSInteger)index
{
    if (index == 0)
    {
        self.meetingInfo = nil;
        
        self.nameText.text = nil;
        self.passWordText.text = nil;
        self.passWordText.placeholder = @"仅支持6位数字";
        
        self.typeLabel.text = @"电话会议桥";
        self.meetingType = @"2";
        
        self.meetingLeader = @"4";
        self.leaderLabel.text = @"其他";
        
        self.meetingLevel = @"3";
        self.rankLabel.text = @"C级";
        
        self.selectedTime = [NSDate getDefaultSelectedStringWithNowDate:[NSDate getServerDate]];
        
    }else if (index == 1)
    {
       [[NSNotificationCenter defaultCenter] postNotificationName:SetCenterControllerNoti object:@(3)];
    }
}

@end
