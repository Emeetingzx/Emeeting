//
//  SelectPlaceViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/1.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "SelectConditionController.h"
#import "Tools.h"
#import "StroeyView.h"
#import "CustomAlertView.h"
#import "AppDelegate.h"

@interface SelectConditionController ()<StroeyViewDelegate,CustomAlertViewDelegate>

@property (nonatomic, strong)StroeyView *stroeyView;

@property (weak, nonatomic) IBOutlet UIView *bgView;

@property (nonatomic, strong) CustomAlertView *bgAlertView;

@end

@implementation SelectConditionController

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    if ([(AppDelegate*)[[UIApplication sharedApplication]delegate] isDoneUpdate])
    {
        self.bgView.hidden = YES;

        [self setButtonLineWidth];
        [self loadStroeyView];
        [self loadDefaultSelected];
        [self setScreenCodition:_screenCodition];

    }else
    {
        self.bgView.hidden = NO;
        self.bgAlertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"正在更新会议地址信息，请稍后..." ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
        self.bgAlertView.delegate = self;
        self.bgAlertView.tapIsHidden = YES;
        [self.view addSubview:self.bgAlertView];
        
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateSuceess) name:@"DoneUpdate" object:nil];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateError) name:@"UpdateError" object:nil];
    }

}

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)updateSuceess
{
    self.bgView.hidden = YES;
    self.bgAlertView.hidden = YES;
    
    [self setButtonLineWidth];
    [self loadStroeyView];
    [self loadDefaultSelected];
    [self setScreenCodition:_screenCodition];
}

- (void)updateError
{
    NSString *roomAddressUpDate = [[NSUserDefaults standardUserDefaults] objectForKey:@"SysMeetingRoomAddress"];
    
    self.bgView.hidden = YES;
    self.bgAlertView.hidden = YES;
    if (roomAddressUpDate.length > 0)
    {
        [self setButtonLineWidth];
        [self loadStroeyView];
        [self loadDefaultSelected];
        [self setScreenCodition:_screenCodition];
    }else
    {
        self.bgView.hidden = NO;
        CustomAlertView *AlertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"更新数据失败" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        [AlertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:2.0];
    }
}

- (void)CustomAlertViewTouchHidden:(CustomAlertView *)customAlert
{
    [customAlert removeFromSuperview];
    [self back:nil];
}


- (void)loadDefaultSelected
{
    if (self.selectedIndex == 1)
    {
        [self selectStorey:nil];
    }else if (self.selectedIndex == 2)
    {
        [self selectNumber:nil];
    }else
    {
        [self selectDevice:nil];
    }
    
}

#pragma mark - 重写screenCodition

- (void)setScreenCodition:(ScreeningCondition *)screenCodition
{
    _screenCodition = screenCodition;
    
    [self setButtonBackgroundColorFromTag:ProjectButtonBeginTag ToTag:ProjectButtonEndTag andsupView:self.deviceView andSelectTag:ProjectButtonBeginTag + [screenCodition.ProjectorState integerValue]];
    [self setButtonBackgroundColorFromTag:VideoButtonBeginTag ToTag:VideoButtonEndTag andsupView:self.deviceView andSelectTag:VideoButtonBeginTag + [screenCodition.TelevisionState integerValue]];
    [self setButtonBackgroundColorFromTag:PhoneButtonBeginTag ToTag:PhoneButtonEndTag andsupView:self.deviceView andSelectTag:PhoneButtonBeginTag + [screenCodition.PhoneState integerValue]];
    
    int i = 0;
    
    switch ([_screenCodition.ParticipantsNumber intValue])
    {
        case 0:
            i = 0;
            break;
        
        case 10:
            i = 1;
            break;
            
        case 20:
            i = 2;
            break;
            
        case 100:
            i = 3;
            break;
            
        case 200:
            i = 4;
            break;
            
        default:
            break;
    }
    
    [self setButtonBackgroundColorFromTag:SelectNumberButtonBeginTag ToTag:SelectNumberButtonEndTag andsupView:self.numberView andSelectTag:SelectNumberButtonBeginTag + i];
}

#pragma mark - 创建楼层选择视图
- (void)loadStroeyView
{
    self.stroeyView = [[StroeyView alloc] initWithFrame:CGRectMake(80, 68, APPW - 80, APPH - 68 - 48) andPid:self.pid andSelectAddressIds:_screenCodition.meetingRoomAddressIds];
    self.stroeyView.delegate = self;
    self.stroeyView.meetingRoomAddressIds = _screenCodition.meetingRoomAddressIds;
    [self.view addSubview:self.stroeyView];
    self.stroeyView.hidden = YES;
}

-(void)stroeyView:(StroeyView *)stroeyView didSelectedStroeyArr:(NSString *)meetingRoomAddressIds
{
    NSLog(@"meetingRoomAddressIds == %@",meetingRoomAddressIds);
    
    _screenCodition.meetingRoomAddressIds = meetingRoomAddressIds;
}

#pragma mark -点击设备
- (IBAction)selectDevice:(id)sender
{
    self.selectedIndex = 0;
    
    [self setLeftAllButtonStyle];
    self.deviceButton.backgroundColor = [UIColor whiteColor];
    [self.deviceButton setTitleColor:RGBA(0, 175, 255, 1.0) forState:UIControlStateNormal];
    self.numberView.hidden = YES;
    self.stroeyView.hidden = YES;
    self.deviceView.hidden = NO;
}

#pragma mark -点击楼层
- (IBAction)selectStorey:(id)sender
{
    self.selectedIndex = 1;
    
    [self setLeftAllButtonStyle];
    self.storeyButton.backgroundColor = [UIColor whiteColor];
    [self.storeyButton setTitleColor:RGBA(0, 175, 255, 1.0) forState:UIControlStateNormal];
    self.deviceView.hidden = YES;
    self.numberView.hidden = YES;
    self.stroeyView.hidden = NO;
}

#pragma mark -点击人数
- (IBAction)selectNumber:(id)sender
{
    self.selectedIndex = 2;
    
    [self setLeftAllButtonStyle];
    self.numberButton.backgroundColor = [UIColor whiteColor];
    [self.numberButton setTitleColor:RGBA(0, 175, 255, 1.0) forState:UIControlStateNormal];
    
    self.deviceView.hidden = YES;
    self.stroeyView.hidden = YES;
    self.numberView.hidden = NO;
    self.numberTop.constant = 0;
}

#pragma mark -投影仪选择
- (IBAction)projectorSelect:(UIButton *)sender
{
    _screenCodition.ProjectorState = [NSString stringWithFormat:@"%li",sender.tag-ProjectButtonBeginTag];
    
    [self setButtonBackgroundColorFromTag:ProjectButtonBeginTag ToTag:ProjectButtonEndTag andsupView:self.deviceView andSelectTag:sender.tag];
}

#pragma mark -电视选择
- (IBAction)videoSelect:(UIButton *)sender
{
    _screenCodition.TelevisionState = [NSString stringWithFormat:@"%li",sender.tag-VideoButtonBeginTag];
    
     [self setButtonBackgroundColorFromTag:VideoButtonBeginTag ToTag:VideoButtonEndTag andsupView:self.deviceView andSelectTag:sender.tag];
}

#pragma mark -电话选择
- (IBAction)phoneSelect:(UIButton *)sender
{
     _screenCodition.PhoneState = [NSString stringWithFormat:@"%li",sender.tag-PhoneButtonBeginTag];

     [self setButtonBackgroundColorFromTag:PhoneButtonBeginTag ToTag:PhoneButtonEndTag andsupView:self.deviceView andSelectTag:sender.tag];
}

#pragma mark -人数选择
- (IBAction)numberSelect:(UIButton *)sender
{
    switch (sender.tag-SelectNumberButtonBeginTag)
    {
        case 0:
            _screenCodition.ParticipantsNumber = @"0";
            break;
            
        case 1:
            _screenCodition.ParticipantsNumber = @"10";
            break;
            
        case 2:
            _screenCodition.ParticipantsNumber = @"20";
            break;
            
        case 3:
            _screenCodition.ParticipantsNumber = @"100";
            break;
            
        case 4:
            _screenCodition.ParticipantsNumber = @"200";
            break;
            
        default:
            break;
    }
    
     [self setButtonBackgroundColorFromTag:SelectNumberButtonBeginTag ToTag:SelectNumberButtonEndTag andsupView:self.numberView andSelectTag:sender.tag];
}

#pragma mark -返回
- (IBAction)back:(id)sender
{
    //[self.navigationController popViewControllerAnimated:YES];
    [self dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark -点击确认
- (IBAction)confirmClick:(id)sender
{
    
    NSLog(@"ProjectorState ==%@ TelevisionState==%@ PhoneState==%@ meetingRoomAddressIds==%@ ParticipantsNumber ==%@",_screenCodition.ProjectorState,_screenCodition.TelevisionState,_screenCodition.PhoneState,_screenCodition.meetingRoomAddressIds,_screenCodition.ParticipantsNumber);
    
    if ([self.delegate respondsToSelector:@selector(selectCondition:ScreeningCondition:defaultSelected:)])
    {
        [self.delegate selectCondition:self ScreeningCondition:_screenCodition defaultSelected:self.selectedIndex];
    }
    
    [self dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark -清除筛选
- (IBAction)cleanSelected:(id)sender
{
    _screenCodition.ProjectorState = @"0";
    _screenCodition.TelevisionState = @"0";
    _screenCodition.PhoneState = @"0";
    _screenCodition.meetingRoomAddressIds = @"不限";
    _screenCodition.ParticipantsNumber = @"0";
    
    [self setButtonBackgroundColorFromTag:ProjectButtonBeginTag ToTag:ProjectButtonEndTag andsupView:self.deviceView andSelectTag:ProjectButtonBeginTag];
    [self setButtonBackgroundColorFromTag:VideoButtonBeginTag ToTag:VideoButtonEndTag andsupView:self.deviceView andSelectTag:VideoButtonBeginTag];
    [self setButtonBackgroundColorFromTag:PhoneButtonBeginTag ToTag:PhoneButtonEndTag andsupView:self.deviceView andSelectTag:PhoneButtonBeginTag];
    [self setButtonBackgroundColorFromTag:SelectNumberButtonBeginTag ToTag:SelectNumberButtonEndTag andsupView:self.numberView andSelectTag:SelectNumberButtonBeginTag];
    
    [self.stroeyView setButtonBackgroundColorFromTag:SelectStroeyButtonBeginTag ToTag:1130-1 andSelectTag:SelectStroeyButtonBeginTag];
    [self.stroeyView addFirstNotLimitButton];
}

#pragma mark - 设置左侧按钮样式
- (void)setLeftAllButtonStyle
{
    self.deviceButton.backgroundColor = [UIColor clearColor];
    self.storeyButton.backgroundColor = [UIColor clearColor];
    self.numberButton.backgroundColor = [UIColor clearColor];
    
    [self.deviceButton setTitleColor:RGBA(51, 51, 51, 1.0) forState:UIControlStateNormal];
    [self.storeyButton setTitleColor:RGBA(51, 51, 51, 1.0) forState:UIControlStateNormal];
    [self.numberButton setTitleColor:RGBA(51, 51, 51, 1.0) forState:UIControlStateNormal];
}

#pragma mark - 设置选择设备按钮线条
- (void)setButtonLineWidth
{
    for (int i = 0; i<3; i++)
    {
        [self setLineWithY:50+80*i];
    }
    
    CGFloat btnW = 70;
    CGFloat btnH = 30;
    CGFloat btnY = 20;
    CGFloat btnX = (APPW - 80 - 3*btnW)/2.0;
    
    for (int i = 0 ; i <= 2; i++)
    {
        if (i < 2)
        {
            UIView *line = [[UIView alloc] initWithFrame:CGRectMake(btnX, btnY+i*btnH, btnW * 3, 1)];
            line.backgroundColor = RGBA(204, 204, 204, 1.0);
            [self.numberView addSubview:line];
        }else
        {
            UIView *line = [[UIView alloc] initWithFrame:CGRectMake(btnX, btnY+i*btnH, btnW * 2+1, 1)];
            line.backgroundColor = RGBA(204, 204, 204, 1.0);
            [self.numberView addSubview:line];
        }
    }

    for (int i = 0; i < 2; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            if (i==1 && j >= 3)
            {
                break;
            }
            
            UIView *line = [[UIView alloc] initWithFrame:CGRectMake(btnX+j*btnW, (btnY+1)+i*btnH, 1, btnH -1)];
            line.backgroundColor = RGBA(204, 204, 204, 1.0);
            
            if (j == 3)
            {
                line.frame = CGRectMake(btnX+j*btnW-1, (btnY+1)+i*btnH, 1, btnH -1);
            }
            
            [self.numberView addSubview:line];
        }
    }

}

- (void)setLineWithY:(CGFloat)y
{
    CGFloat btnW = 70;
    CGFloat btnH = 30;
    CGFloat btnY = y;
    CGFloat btnX = (APPW - 80 - 3*btnW)/2.0;
    
    for (int i = 0; i<2; i++)
    {
        UIView *line = [[UIView alloc] initWithFrame:CGRectMake(btnX, btnY+i*btnH, btnW * 3, 1)];
        line.backgroundColor = RGBA(204, 204, 204, 1.0);
        [self.deviceView addSubview:line];
    }
    for (int i = 0 ; i < 4; i++)
    {
        UIView *line1 = [[UIView alloc] initWithFrame:CGRectMake(btnX+i*btnW, btnY+1, 1, btnH-1)];
        line1.backgroundColor = RGBA(204, 204, 204, 1.0);
        if (i == 3)
        {
            line1.frame = CGRectMake(btnX+3*btnW-1, btnY+1, 1, btnH-1);
        }
        [self.deviceView addSubview:line1];
    }
}


#pragma mark - 设置选择设备按钮背景
- (void)setButtonBackgroundColorFromTag:(NSInteger)fromtag ToTag:(NSInteger)totag andsupView:(UIView *)supView andSelectTag:(NSInteger)selecttag
{
    for (NSInteger i = fromtag; i<=totag; i++)
    {
        UIButton *button = [supView viewWithTag:i];
        button.backgroundColor = [UIColor clearColor];
        [button setTitleColor:RGBA(51, 51, 51, 1.0) forState:UIControlStateNormal];
        
        if (i == selecttag)
        {
            button.backgroundColor = RGBA(255, 150, 0, 1.0);
            [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        }
    }
}


@end
