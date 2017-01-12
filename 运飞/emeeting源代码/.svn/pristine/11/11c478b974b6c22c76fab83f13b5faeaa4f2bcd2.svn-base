//
//  ShakeResultViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/3/4.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ShakeResultViewController.h"
#import "ShakeMeetingView.h"
#import "MeetingScheduleView.h"
#import "NSDate+LJFDate.h"
#import "MeetingSViewController.h"
#import "GetNearParkMeetingRoomInfoHandle.h"
#import <AVFoundation/AVFoundation.h>
#import "ProgressView.h"
#import "MeetingRoomInfo.h"
#import "Tools.h"
#import "MarkedWords.h"
#import "CustomAlertView.h"

@interface ShakeResultViewController ()<ShakeMeetingViewDelegate,MeetingScheduleViewDelegate>

@property (nonatomic, strong)ShakeMeetingView *shakeMeetingView;

@property (nonatomic, strong) AVAudioPlayer *player;

@property (nonatomic, strong) GetNearParkMeetingRoomInfoHandle *nearParkRoomHandle;

@property (nonatomic, assign) BOOL isShake;

@end

@implementation ShakeResultViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.

    _shakeMeetingView = [[ShakeMeetingView alloc] initWithFrame:CGRectMake(0, 20, APPW , APPH -20) MeetingRoomInfo:_dataArr];
    _shakeMeetingView.delegate = self;
    [self.view addSubview:_shakeMeetingView];
    
    UIButton *button  = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(0, 20, 48, 48);
    [button addTarget:self action:@selector(back:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
}

- (void)back:(UIButton *)button
{
    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark - ShakeMeetingView代理
- (void)shakeMeetingView:(ShakeMeetingView *)shakeMeetingView didClickMeetingRoomInfo:(MeetingRoomInfo *)meetingRoomInfo
{
    self.isShake = YES;
    
    MeetingScheduleView *meetingView = [[MeetingScheduleView alloc] initWithFrame:CGRectMake(0, 20, APPW , APPH -20) andDictionary:meetingRoomInfo selectTime:[NSDate getDefaultSelectedStringWithNowDate:[NSDate getServerDate] DefualtTimeLong:self.timeLong] andIsNotLimitTime:NO];
    meetingView.delegate = self;
    [self.view addSubview:meetingView];
}

- (void)meetingScheduleViewDidCancel:(MeetingScheduleView *)secheduleView
{
    self.isShake = NO;
}

- (void)meetingScheduleView:(MeetingScheduleView *)secheduleView lockMeetingSuccess:(NSString *)meetingId
{
    self.isShake = NO;
    
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MeetingSViewController" bundle:nil];
    MeetingSViewController *meetingS = [storyboard instantiateViewControllerWithIdentifier:@"MeetingSViewController"];
    meetingS.meetingId = meetingId;
    meetingS.isFromShake = YES;
    meetingS.isFromFirst = self.isFromFirst;
    [self.navigationController pushViewController:meetingS animated:YES];
}

#pragma mark - 查询定位或选择园区可预订会议室信息请求
- (void)getNearParkMeetingRoomInfoRequestWithLatitudeAndLongitude:(NSString *)latitudeAndLongitude addressId:(NSString *)addressId meetingTime:(NSString *)meetingTime page:(PageRequestObject *)page
{
    [ProgressView showCustomProgressViewAddTo:self.view];
    
    __weak typeof(self) wself = self;
    
    if (_nearParkRoomHandle == nil)
    {
        _nearParkRoomHandle = [[GetNearParkMeetingRoomInfoHandle alloc] init];
    }
    
    [_nearParkRoomHandle sendJSONRequestWithLatitudeAndLongitude:latitudeAndLongitude AddressId:addressId MeetingTime:meetingTime Page:page Success:^(ResponseObject *response)
     {
         [ProgressView hiddenCustomProgressViewAddTo:wself.view];
         
         if (response.s)
         {
             if ([(NSArray *)response.d count]>0)
             {
                 wself.page.pno++;
                 wself.page.e=response.p.e;
                 wself.page.t=[response.p.t intValue];
                 wself.shakeMeetingView.meetingRoomInfoArr = [wself loadMeetingInfoDataArrWithArr:(NSArray *)response.d];
                 [wself playVudioWithName:@"shake_have" andNumber:0];
             }else
             {
                 CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"没有获取到更多会议室信息" ImageName:nil andImageSize:CGSizeMake(36, 36) AlertWidth:300];
                 [wself.view addSubview:alertView];
                 [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:2.0];
             }
         }else
         {
             
         }
         wself.isShake = NO;
     } failure:^(NSError *error)
     {
         [ProgressView hiddenCustomProgressViewAddTo:wself.view];
         wself.isShake = NO;
     }];
}

- (NSMutableArray <MeetingRoomInfo *> *)loadMeetingInfoDataArrWithArr:(NSArray *)dataArr
{
    NSMutableArray *arrM = [[NSMutableArray alloc] init];
    
    for (NSDictionary *dict in dataArr)
    {
        MeetingRoomInfo *info = [[MeetingRoomInfo alloc] initWithDictionary:dict];
        info.rowHeigth = [self loadRowHeigthWithRoomName:info.meetingRoomName];
        
        [arrM addObject:info];
    }
    
    return arrM;
}

- (CGFloat)loadRowHeigthWithRoomName:(NSString *)roomName
{
    CGFloat width = 0;
    
    if (IPHONE6)
    {
        width = APPW - 68 - 70 - 20 - 10;
    }else if(IPHONE6P)
    {
        width = APPW - 68 - 80 - 20 - 10;
    }else
    {
        width = APPW - 68 - 60 - 20 - 10;
    }
    
    CGFloat heigth = [Tools heightOfString:roomName font:[UIFont systemFontOfSize:16] width:width];
    
    if (heigth > 30)
    {
        return 95 + heigth -20;
    }else
    {
        return 95;
    }
}

#pragma mark -允许摇动
- (BOOL)canBecomeFirstResponder
{
    return YES;
}

#pragma mark -检测到摇动
- (void)motionBegan:(UIEventSubtype)motion withEvent:(UIEvent *)event
{
    if (self.isShake)
    {
        return;
    }
    
    self.isShake = YES;
    
    [self playVudioWithName:@"shake_sound" andNumber:1];
    
    [self performSelector:@selector(stopShakeAndSendRequest) withObject:nil afterDelay:1.0];
    
}

- (void)stopShakeAndSendRequest
{
    [_player stop];
    _player = nil;
    
    [self getNearParkMeetingRoomInfoRequestWithLatitudeAndLongitude:self.latitudeAndLongitude addressId:self.meetingAddressId meetingTime:self.timeLong page:self.page];
}

#pragma mark -播放声音
- (void)createPlayerWihtFileName:(NSString *)fileName andNumber:(NSInteger)number
{
    NSString *path = [[NSBundle mainBundle]pathForResource:fileName ofType:@"mp3"];
    
    NSURL *url = [NSURL fileURLWithPath:path];
    
    _player = [[AVAudioPlayer alloc]initWithContentsOfURL:url error:nil];
    
    [_player prepareToPlay];
    
    _player.volume = 0.8;
    _player.numberOfLoops = number;
    
}

- (void)playVudioWithName:(NSString *)name andNumber:(NSInteger)number
{
    [self createPlayerWihtFileName:name andNumber:number];
    [_player play];
}


@end
