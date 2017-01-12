//
//  ShakeViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/1/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ShakeViewController.h"
#import "UIViewController+MMDrawerController.h"
#import "CustomAlertView.h"
#import "Tools.h"
#import "ShakeScreenOutView.h"
#import "GetNearParkAddressInfoHandle.h"
#import "GetNearParkMeetingRoomInfoHandle.h"
#import "ShakeMeetingView.h"
#import <AVFoundation/AVFoundation.h>
#import "GetRecentBuildingAddressInfoHandle.h"
#import "MeetingRoomAddress.h"
#import "ProgressView.h"
#import "ShakeResultViewController.h"
#import "NSDate+LJFDate.h"

@interface ShakeViewController ()<ShakeScreenOutViewDelegate,CustomAlertViewDelegate>


@property (weak, nonatomic) IBOutlet UILabel *tiShiTitle;

@property (weak, nonatomic) IBOutlet UIImageView *shakeImageView;

@property (nonatomic,copy) NSString *latitudeAndLongitude;

@property (nonatomic,copy) NSString *addressId;

@property (nonatomic,copy) NSString *meetingTime;

@property (nonatomic, strong) PageRequestObject *page;

@property (nonatomic, assign) BOOL isShake;

@property (nonatomic, strong) UIImageView *notSeekBgImage;

@property (nonatomic, strong) AVAudioPlayer *player;

@property (nonatomic, strong) GetNearParkAddressInfoHandle *nearParkHadle;

@property (nonatomic, strong) GetNearParkMeetingRoomInfoHandle *nearParkRoomHandle;

@property (nonatomic, strong) NSMutableArray <MeetingRoomAddress *> *roomAddressInfoArr;

@end

@implementation ShakeViewController

-(PageRequestObject *)page
{
    if (_page == nil)
    {
        _page = [[PageRequestObject alloc] init];
        _page.psize = 3;
        _page.pno = 1;
    }
    return _page;
}

-(NSMutableArray<MeetingRoomAddress *> *)roomAddressInfoArr
{
    if (_roomAddressInfoArr == nil)
    {
        _roomAddressInfoArr = [[NSMutableArray alloc] init];
    }
    return _roomAddressInfoArr;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.isShake = YES;
    
    self.view.backgroundColor = [UIColor whiteColor];
    
    if (self.isFromFirst)
    {
        [self.headImage setImage:[UIImage imageNamed:@"back"] forState:UIControlStateNormal];
    }
    
    self.latitudeAndLongitude = [[NSUserDefaults standardUserDefaults] objectForKey:@"LongitudeAndLatitude"];
    self.meetingTime = @"1";
    
    [self getNearParkAddressInfoRequestWithLatitudeAndLongitude:self.latitudeAndLongitude];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    self.page = nil;
    if (self.latitudeAndLongitude==nil || self.latitudeAndLongitude.length == 0)
    {
       self.isShake = YES;
    }else
    {
        self.isShake = NO;
    }

}

#pragma mark - 打开左侧菜单栏
- (IBAction)openLeftMenu:(id)sender
{
    if (self.isFromFirst)
    {
        [self.navigationController popViewControllerAnimated:YES];
    }else
    {
        [self.mm_drawerController toggleDrawerSide:MMDrawerSideLeft animated:YES completion:nil];
    }
}

#pragma mark - 加载条件选择视图ShakeScreenOutView
- (IBAction)selectedPlaceAndTime:(UIButton *)sender
{
    ShakeScreenOutView *screenOut = [[ShakeScreenOutView alloc] initWithFrame:self.view.bounds meetingLocation:nil subPlaceDataArr:self.roomAddressInfoArr];
    screenOut.delegate = self;
    screenOut.selectedPlaceIds = self.addressId;
    screenOut.selectedTime = self.meetingTime;
    [self.view addSubview:screenOut];
}

#pragma mark - ShakeScreenOutView代理，获得选择的条件
- (void)shakeScreenOutView:(ShakeScreenOutView *)screenOutView didSelectedPlaceIds:(NSString *)placeIds selectedTime:(NSString *)time
{
    self.page = nil;
    
    self.addressId = placeIds;
    self.meetingTime = time;
    
    self.tiShiTitle.text = [NSString stringWithFormat:@"摇一摇，秒杀当下%@小时会议室",self.meetingTime];
}


#pragma mark - 查询附近园区地址信息请求
- (void)getNearParkAddressInfoRequestWithLatitudeAndLongitude:(NSString *)latitudeAndLongitude
{
    __weak typeof(self) wself = self;
    
    if (_nearParkHadle == nil)
    {
        _nearParkHadle = [[GetNearParkAddressInfoHandle alloc] init];
    }
    
    [ProgressView showCustomProgressViewAddTo:self.view];
    [_nearParkHadle sendJSONRequestWithLatitudeAndLongitude:latitudeAndLongitude Success:^(ResponseObject *response)
     {
         [ProgressView hiddenCustomProgressViewAddTo:wself.view];
         wself.isShake = NO;
         if (response.s)
         {
             NSArray *arr = (NSArray *)response.d;
             
             for (NSDictionary *dict in arr)
             {
                 MeetingRoomAddress *roomAddressInfo = [[MeetingRoomAddress alloc] initWithDictionary:dict];
                 [wself.roomAddressInfoArr addObject:roomAddressInfo];
             }
              wself.addressId = wself.roomAddressInfoArr.firstObject.iD;
         }
     } failure:^(NSError *error)
     {
          wself.isShake = NO;
         [ProgressView hiddenCustomProgressViewAddTo:wself.view];
     }];
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
                
                [wself loadSeekMeetingInfoViewWithData:(NSArray *)response.d];
            }else
            {
                [wself loadNotSeekMeetingInfo];
            }
        }else
        {
            [wself loadNotSeekMeetingInfo];
        }
    } failure:^(NSError *error)
    {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        wself.isShake = NO;
    }];
}

#pragma mark - 没有找到相关会议室处理
- (void)loadNotSeekMeetingInfo
{
    [self playVudioWithName:@"shake_no_have" andNumber:0];
    
    CustomAlertView *alertViewError = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"未能找到会议室，请输入条件后重新尝试!" ImageName:nil andImageSize:CGSizeMake(36, 36) AlertWidth:APPW>320?320:305];
    alertViewError.delegate = self;
    alertViewError.tapIsHidden = YES;
    [self.view addSubview:alertViewError];
    
    self.notSeekBgImage = [[UIImageView alloc] initWithFrame:[self loadImageViewFrame]];
    self.notSeekBgImage.image = [UIImage imageNamed:@"shake_background1"];
    [self.view addSubview:self.notSeekBgImage];
}

- (CGRect)loadImageViewFrame
{
    if (IPHONE4)
    {
        return CGRectMake(60, 25, 250, 185);
    }else if (IPHONE5)
    {
        return CGRectMake(0, 20, 315, 235);
    }else if (IPHONE6)
    {
        return CGRectMake(15, 20, 360, 270);
    }else
    {
        return CGRectMake(13, 18, 400, 300);
    }
}

#pragma mark - CustomAlertView代理方法，点击屏幕隐藏
- (void)CustomAlertViewTouchHidden:(CustomAlertView *)customAlert
{
    [self.notSeekBgImage removeFromSuperview];
    self.notSeekBgImage = nil;
    
    self.isShake = NO;
}

#pragma mark - 找到相关会议室处理
- (void)loadSeekMeetingInfoViewWithData:(NSArray *)dataArr
{
    [self playVudioWithName:@"shake_have" andNumber:0];
    
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"ShakeResultViewController" bundle:nil];
    ShakeResultViewController *shakeResult = [storyboard instantiateViewControllerWithIdentifier:@"ShakeResultViewController"];
    shakeResult.dataArr = dataArr;
    shakeResult.latitudeAndLongitude = self.latitudeAndLongitude;
    shakeResult.meetingAddressId = self.addressId;
    shakeResult.timeLong = self.meetingTime;
    shakeResult.page = self.page;
    shakeResult.isFromFirst = self.isFromFirst;
    [self.navigationController pushViewController:shakeResult animated:NO];
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
    
    if (![self checkIsCanShake])
    {
        return;
    }
    
    [self playVudioWithName:@"shake_sound" andNumber:2];
    [self shakeImageViewBeginAnimation];
    
    [self performSelector:@selector(stopShakeAndSendRequest) withObject:nil afterDelay:2.0];
}

#pragma mark -判断是否可以摇一摇
- (BOOL)checkIsCanShake
{
    if (self.latitudeAndLongitude==nil || self.latitudeAndLongitude.length == 0)
    {
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"无法获取您的位置信息，请打开您的定位服务!" ImageName:nil andImageSize:CGSizeMake(36, 36) AlertWidth:APPW>320?320:305];
        [self.view addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:3.0];
        [self performSelector:@selector(setIsShakeToNo) withObject:nil afterDelay:3.0];
        return NO;
    }
    
    if (self.roomAddressInfoArr.count == 0)
    {
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"附近没有会议室地址" ImageName:nil andImageSize:CGSizeMake(36, 36) AlertWidth:APPW>320?320:305];
        [self.view addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:3.0];
        [self performSelector:@selector(setIsShakeToNo) withObject:nil afterDelay:3.0];
        return NO;

    }
    
    if (![NSDate canShakeWithNowDate:[NSDate getServerDate]])
    {
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:[UIScreen mainScreen].bounds Message:@"很抱歉，20:00至次日8:15之间无法使用摇一摇!" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        [self.view addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:3.0];
        [self performSelector:@selector(setIsShakeToNo) withObject:nil afterDelay:3.0];
        return NO;
    }
    
    if ([[NSDate getServerDate] hour]==18 && [_meetingTime integerValue] >2)
    {
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:[UIScreen mainScreen].bounds Message:@"请输入条件后重新尝试，最多可预定2小时的会议!" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        [self.view addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:3.0];
        [self performSelector:@selector(setIsShakeToNo) withObject:nil afterDelay:3.0];
        return NO;
    }
    
    if ([[NSDate getServerDate] hour]==19 && [_meetingTime integerValue] >1)
    {
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:[UIScreen mainScreen].bounds Message:@"请输入条件后重新尝试，最多可预定1小时的会议!" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        [self.view addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:3.0];
        [self performSelector:@selector(setIsShakeToNo) withObject:nil afterDelay:3.0];
        return NO;
    }
    
    return YES;
}

- (void)setIsShakeToNo
{
    self.isShake = NO;
}

- (void)stopShakeAndSendRequest
{
    [_player stop];
    _player = nil;
    
    [self.shakeImageView.layer removeAnimationForKey:@"shakeImageView"];
    
    [self getNearParkMeetingRoomInfoRequestWithLatitudeAndLongitude:self.latitudeAndLongitude addressId:self.addressId meetingTime:self.meetingTime page:self.page];
}

- (void)shakeImageViewBeginAnimation
{
    CAKeyframeAnimation *animation = [CAKeyframeAnimation animationWithKeyPath:@"transform.rotation.z"];
    CGFloat angle = M_PI_4/2;
    animation.values = @[@(-angle),@(angle),@(-angle)];
    animation.repeatCount = MAXFLOAT;
    animation.duration  = 0.4;
    [self.shakeImageView.layer addAnimation:animation forKey:@"shakeImageView"];
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
