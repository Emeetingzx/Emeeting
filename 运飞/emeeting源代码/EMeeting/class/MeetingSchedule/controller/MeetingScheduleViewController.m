//
//  MeetingScheduleViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/1/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingScheduleViewController.h"
#import "UIViewController+MMDrawerController.h"
#import "MeetingScheduleTableViewCell.h"
#import "SelectConditionController.h"
#import "SelectPlaceViewController.h"
#import "ShakeViewController.h"
#import "MeetingScheduleView.h"
#import "TimeSelectView.h"
#import "Tools.h"
#import "NSDate+LJFDate.h"
#import "UIColor+LJFColor.h"
#import "EMMSecurity.h"
#import "GetLastUpdatetimeHandle.h"
#import "MeetingSViewController.h"
#import <CoreLocation/CoreLocation.h>
#import "NetworkingManager.h"
#import "GetMeetingRoomBookingInfoHandle.h"
#import "CustomAlertView.h"
#import "GetRecentBuildingAddressInfoHandle.h"
#import "MeetingRoomAddress.h"
#import "AppDelegate.h"
#import "ProgressView.h"
#import "MJRefresh.h"
#import "MarkedWords.h"
#import "TouchRefreshView.h"
#import "GetServerDateHandle.h"
#import "DefaultDataModel.h"
#import "GetValidBookDateHandle.h"
#import <BaiduMapAPI_Location/BMKLocationComponent.h>

@interface MeetingScheduleViewController ()<UITableViewDelegate,UITableViewDataSource,TimeSelectViewDelegate,MeetingScheduleTableViewCellDelegate,MeetingScheduleViewDelegate,CLLocationManagerDelegate,selectConditionDelegate,SelectPlaceViewControllerDelegate,CustomAlertViewDelegate,TouchRefreshViewDelegate,BMKLocationServiceDelegate>

@property (weak, nonatomic) IBOutlet UITableView *tableView;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *placeLabelWidth; //地点label的宽度约束

@property (nonatomic, strong) CLLocationManager *locationManager;

@property (nonatomic, strong) GetMeetingRoomBookingInfoHandle *ConditionHandle;

@property (nonatomic, strong) GetRecentBuildingAddressInfoHandle *recentBuildHandle;

@property (nonatomic, strong) GetValidBookDateHandle *validBookDateHandle;

@property (nonatomic, strong) UIView *bgView;

@property (nonatomic, strong) CustomAlertView *bgAlertView;

@property (nonatomic, strong) NSMutableArray <MeetingRoomInfo *> *dataArr;

@property (nonatomic, assign) int selectedIndex;

@property (nonatomic, assign) BOOL isDeleteDataArr;

@property (nonatomic, strong) PageRequestObject *page;

@property (nonatomic, copy) NSString *longitudeAndLatitude;

@property (nonatomic, strong) MeetingRoomModel *selectedInfo;

@property (nonatomic, strong) ScreeningCondition *screeningCondition;

@property (nonatomic, strong) TouchRefreshView *touchRefreshView;

@property (nonatomic, strong) BMKLocationService *locService;

@end

@implementation MeetingScheduleViewController

@synthesize serverDate = _serverDate;

-(PageRequestObject *)page
{
    if (_page == nil)
    {
        _page = [[PageRequestObject alloc] init];
        _page.psize = 20;
        _page.pno = 1;
    }
    return _page;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    
    self.maxDate = [NSDate offsetDays:[NSDate getCanOffsetIndexWithToday:[NSDate getServerDate]]-1 fromDate:[NSDate getServerDate]];
    
    self.selectedTime = [NSDate getDefaultSelectedStringWithNowDate:[NSDate getServerDate]];
    
    [self.tableView registerNib:[UINib nibWithNibName:@"MeetingScheduleTableViewCell" bundle:nil] forCellReuseIdentifier:@"MeetingScheduleTableViewCell"];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(loginSuccessToGetPlace) name:@"loginSuccessToGetPlace" object:nil];
    
    [self setupRefresh];
    
    [self loadTouchRefreshView];
    
    if ([(AppDelegate*)[[UIApplication sharedApplication]delegate] isFirstLaunching])
    {
        [self loadBgView];
    }else
    {
        [self loadDefaultData];
        
        [self getServerDateRequest];
        
        [self getValidBookDateRequest];
        
        [self SendConditionHandleRequestWithType:WithTime];
    }
}

- (void)loadBgView
{
    self.bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 68, APPW, APPH-68)];
    self.bgView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:self.bgView];
    
    UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake((APPW-200)/2.0, (APPH-200)/2.0-68, 200, 200)];
    imageView.image = [UIImage imageNamed:@"cri_background"];
    [self.bgView addSubview:imageView];
    
    self.bgAlertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"正在获取您的位置和会议室信息，请稍后..." ImageName:@"cri_location" andImageSize:CGSizeMake(26, 35) AlertWidth:APPW>320?320:300];
//    [self.view addSubview:self.bgAlertView];
}

#pragma mark－初始化默认数据
- (void)loadDefaultData
{
    DefaultDataModel *defaultDate = [DefaultDataModel shareInstance];
    self.selectedInfo = [[MeetingRoomModel alloc] init];
    self.screeningCondition = [[ScreeningCondition alloc] init];
    
    self.selectedInfo.iD = defaultDate.pid;
    self.screeningCondition.ParticipantsNumber = defaultDate.ParticipantsNumber?:@"0";
    self.screeningCondition.PhoneState = defaultDate.PhoneState?:@"0";
    self.screeningCondition.ProjectorState = defaultDate.ProjectorState?:@"0";
    self.screeningCondition.TelevisionState = defaultDate.TelevisionState?:@"0";
    self.screeningCondition.meetingRoomAddressIds = defaultDate.meetingRoomAddressIds?:defaultDate.pid;
    self.selectedIndex = defaultDate.selectedIndex;
    [self setPlaceLabelText:defaultDate.pName];
    
    self.dataArr = [NSMutableArray arrayWithArray:[DefaultDataModel shareInstance].dataArr];
    self.isDeleteDataArr = YES;
    [self.tableView reloadData];
}

#pragma mark -登陆成功开始定位
- (void)loginSuccessToGetPlace
{
    [self getServerDateRequest];
    
    [self getValidBookDateRequest];
    
    _locService = [[BMKLocationService alloc]init];
    _locService.delegate = self;
    [_locService startUserLocationService];
    
}

#pragma mark -定位成功回调
- (void)didUpdateBMKUserLocation:(BMKUserLocation *)userLocation
{
    NSLog(@"didUpdateUserLocation lat %f,long %f",userLocation.location.coordinate.latitude,userLocation.location.coordinate.longitude);
    
    self.longitudeAndLatitude = [NSString stringWithFormat:@"%f,%f",userLocation.location.coordinate.longitude,userLocation.location.coordinate.latitude];
    
    [[NSUserDefaults standardUserDefaults] setObject:self.longitudeAndLatitude forKey:@"LongitudeAndLatitude"];
    
    [[NSUserDefaults standardUserDefaults] synchronize];

    [_locService stopUserLocationService];
    
    [self getPlaceSuccessInitData];
}

#pragma mark -定位成功初始化首页数据
- (void)getPlaceSuccessInitData
{
    if ([(AppDelegate*)[[UIApplication sharedApplication]delegate] isFirstLaunching])
    {
        [(AppDelegate*)[[UIApplication sharedApplication]delegate] setIsFirstLaunching:NO];
        [self sendRecentBuildHandleRequestWithLatitudeAndLongitude:self.longitudeAndLatitude];
    }else
    {
        self.page = nil;
        self.isDeleteDataArr = YES;
        
        [self SendConditionHandleRequestWithType:WithTime];
    }
}

#pragma mark -定位失败回调
- (void)didFailToLocateUserWithError:(NSError *)error
{
    if ([(AppDelegate*)[[UIApplication sharedApplication]delegate] isFirstLaunching])
    {
        [(AppDelegate*)[[UIApplication sharedApplication]delegate] setIsFirstLaunching:NO];
        
        [self getRecentBuild];
    }
}

#pragma mark -重写选择的时间字符串
- (void)setSelectedTime:(NSString *)selectedTime
{
    _selectedTime = selectedTime;
    
    self.mdayLabel.text = [NSDate monthAndDayChineseStringWithSelectTime:selectedTime];
    
    self.hsecondLabel.text = [NSDate getImterValTimeWithSelecttime:selectedTime];
    
    if ([[NSDate getDateWithSelectTime:selectedTime] isSameDay:[NSDate getServerDate]])
    {
        self.lastDayLabel.textColor = [UIColor colorWithHexString:@"#CCCCCC"];
        self.lastDayButton.enabled = NO;
        self.lastDayImage.image = [UIImage imageNamed:@"ico_left_disenabled"];
    }else
    {
        self.lastDayLabel.textColor = [UIColor colorWithHexString:@"#333333"];
        self.lastDayButton.enabled = YES;
        self.lastDayImage.image = [UIImage imageNamed:@"ico_left_enabled"];
    }
    
    if ([[NSDate getDateWithSelectTime:selectedTime] isSameDay:self.maxDate])
    {
        self.nextDayLabel.textColor = [UIColor colorWithHexString:@"#CCCCCC"];
        self.nextDayButton.enabled = NO;
        self.nextDayImage.image = [UIImage imageNamed:@"ico_right_disenabled"];
    }else
    {
        self.nextDayLabel.textColor = [UIColor colorWithHexString:@"#333333"];
        self.nextDayButton.enabled = YES;
        self.nextDayImage.image = [UIImage imageNamed:@"ico_right_enabled"];
    }
}

#pragma mark - 设置定位地点的内容
- (void)setPlaceLabelText:(NSString *)text
{
    CGFloat width = [Tools widthOfString:text font:[UIFont systemFontOfSize:16] height:20];
    
    if (width > APPW/2.0- 5 -45)
    {
        self.placeLabelWidth.constant = APPW/2.0- 5 -45;
    }else
    {
        self.placeLabelWidth.constant = width+5;
    }
    
    self.placeLabel.text = text;
}

#pragma mark - tableView代理方法
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    MeetingScheduleTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"MeetingScheduleTableViewCell"];
    cell.scheduleButton.tag = ScheduleButtonBeginTag + indexPath.row;
    cell.delegate = self;
    
    if (self.isNotLimitTime)
    {
        cell.presentView.hidden = NO;
        cell.meetingImageView.hidden = YES;
    }else
    {
        cell.presentView.hidden = YES;
        cell.meetingImageView.hidden = NO;
    }
    
    cell.meetingRoomInfo = self.dataArr[indexPath.row];
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return [self.dataArr[indexPath.row] rowHeigth];
}

#pragma mark - 弹出预定界面
- (void)MeetingScheduleTableViewCell:(MeetingScheduleTableViewCell *)cell didClickRow:(NSInteger)row
{
    if ([NSDate canscheduleMeetingWithNowDate:[NSDate getServerDate]])
    {
        MeetingScheduleView *meetingView = [[MeetingScheduleView alloc] initWithFrame:CGRectMake(0, 20, APPW, APPH-20) andDictionary:self.dataArr[row] selectTime:_selectedTime andIsNotLimitTime:self.isNotLimitTime];
        meetingView.delegate = self;
        [self.view addSubview:meetingView];
    }else
    {
    CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:[UIScreen mainScreen].bounds Message:@"很抱歉，0:00-8:15之间为非预订时间,不提供会议预订服务!" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        [self.view addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:2.0];
    }
}

#pragma mark - 弹出预定会议控制器
- (void)meetingScheduleView:(MeetingScheduleView *)secheduleView lockMeetingSuccess:(NSString *)meetingId
{
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MeetingSViewController" bundle:nil];
    MeetingSViewController *meetingS = [storyboard instantiateViewControllerWithIdentifier:@"MeetingSViewController"];
    meetingS.meetingId = meetingId;
    [self.navigationController pushViewController:meetingS animated:YES];
}

#pragma mark -打开左侧菜单栏
- (IBAction)openLeftMune:(UIButton *)sender
{
    [self.mm_drawerController toggleDrawerSide:MMDrawerSideLeft animated:YES completion:nil];
}

#pragma mark -摇一摇
- (IBAction)shake:(id)sender
{
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Shake" bundle:nil];
    ShakeViewController *shake = [storyboard instantiateViewControllerWithIdentifier:@"ShakeViewController"];
    shake.isFromFirst = YES;
    [self.navigationController pushViewController:shake animated:YES];
}

#pragma mark -点击前一天
- (IBAction)lastDay:(id)sender
{
    self.selectedTime = [NSDate getOffsetDaySelectedStringWithNowSelectedTime:_selectedTime offset:-1];
    
    self.isDeleteDataArr = YES;
    self.page = nil;
    if (self.isNotLimitTime)
    {
        [self SendConditionHandleRequestWithType:NotLimitTime];
    }else
    {
        [self SendConditionHandleRequestWithType:WithTime];
    }

}

#pragma mark -点击后一天
- (IBAction)nextDay:(id)sender
{
    self.selectedTime = [NSDate getOffsetDaySelectedStringWithNowSelectedTime:_selectedTime offset:1];
    
    self.isDeleteDataArr = YES;
    self.page = nil;
    if (self.isNotLimitTime)
    {
        [self SendConditionHandleRequestWithType:NotLimitTime];
    }else
    {
        [self SendConditionHandleRequestWithType:WithTime];
    }

}

#pragma mark -选择时间
- (IBAction)selectTime:(id)sender
{
    UIView *view = [[UIView alloc] initWithFrame:self.view.bounds];
    view.backgroundColor = [UIColor colorWithWhite:0 alpha:0.5];
    [self.view addSubview:view];
    
    TimeSelectView *timeSelect = [[NSBundle mainBundle] loadNibNamed:@"TimeSelectView" owner:self options:0][0];
    
    timeSelect.frame = CGRectMake(0, 68, APPW, 200);
    
    timeSelect.delegate = self;
    
    timeSelect.selectedTime = _selectedTime;
    
    timeSelect.notLimitIsHidden = NO;
    
    [view addSubview:timeSelect];
    
    CGRect rect = timeSelect.frame;
    rect.origin.y = -rect.size.height;
    timeSelect.frame = rect;
    rect.origin.y = 68;
    [UIView animateWithDuration:0.2 animations:^
     {
         timeSelect.frame = rect;
     }];

}

#pragma mark -TimeSelectView代理方法，点击时段不限按钮
- (void)TimeSelectViewDidClickNotlimitButton:(TimeSelectView *)timeSelectView
{
    self.mdayLabelLeftConstraint.constant = 60;
    self.hsecondLabel.hidden = YES;
    
    self.isNotLimitTime = YES;
    [self.tableView reloadData];
    [timeSelectView.superview removeFromSuperview];
    
    self.isDeleteDataArr = YES;
    self.page = nil;
    [self SendConditionHandleRequestWithType:NotLimitTime];
}

#pragma mark -TimeSelectView代理方法，点击时段取消按钮
- (void)TimeSelectViewDidCancel:(TimeSelectView *)timeSelectView
{
    [timeSelectView.superview removeFromSuperview];
}

#pragma mark -TimeSelectView代理方法，点击时段确定按钮
- (void)TimeSelectView:(TimeSelectView *)timeSelectView didSelectTime:(NSString *)selectedTimeString
{
    self.selectedTime = selectedTimeString;
    
    self.mdayLabelLeftConstraint.constant = 0;
    self.hsecondLabel.hidden = NO;
    
    [timeSelectView.superview removeFromSuperview];
    
    self.isNotLimitTime = NO;
    
    self.isDeleteDataArr = YES;
    self.page = nil;
    [self SendConditionHandleRequestWithType:WithTime];

}

#pragma mark -选择区域
- (IBAction)selectPlace:(id)sender
{
    UIStoryboard *storyBoard = [UIStoryboard storyboardWithName:@"SelectPlace" bundle:nil];
    
    SelectPlaceViewController *SelectPlace = [storyBoard instantiateViewControllerWithIdentifier:@"SelectPlaceViewController"];
    SelectPlace.delegate = self;
    SelectPlace.DefaultSelect = self.selectedInfo;
    
    [self presentViewController:SelectPlace animated:YES completion:nil];
}

- (void)selectPlaceViewController:(SelectPlaceViewController *)selectPlace didSelectInfo:(MeetingRoomModel *)selectInfo
{
    NSLog(@"meetingaddressId == %@",selectInfo.iD);
    self.selectedInfo = selectInfo;
    
    [self setPlaceLabelText:self.selectedInfo.addessChinese];
    
    self.isDeleteDataArr = YES;
    self.page = nil;
    _screeningCondition = nil;
    _selectedIndex = 0;
    
    if (self.isNotLimitTime)
    {
        [self SendConditionHandleRequestWithType:NotLimitTime];
    }else
    {
        [self SendConditionHandleRequestWithType:WithTime];
    }
    
    [DefaultDataModel shareInstance].pid = selectInfo.iD;
    [DefaultDataModel shareInstance].pName = selectInfo.addessChinese;
    [DefaultDataModel shareInstance].ProjectorState = @"0";
    [DefaultDataModel shareInstance].PhoneState = @"0";
    [DefaultDataModel shareInstance].ParticipantsNumber = @"0";
    [DefaultDataModel shareInstance].TelevisionState = @"0";
    [DefaultDataModel shareInstance].meetingRoomAddressIds = selectInfo.iD;
    [DefaultDataModel shareInstance].selectedIndex = 0;
}

#pragma mark -筛选
- (IBAction)selectConditon:(id)sender
{
    UIStoryboard *storyBoard = [UIStoryboard storyboardWithName:@"SelectCondition" bundle:nil];
    
    SelectConditionController *selectCondition = [storyBoard instantiateViewControllerWithIdentifier:@"SelectConditionController"];
    selectCondition.delegate = self;
    selectCondition.screenCodition = self.screeningCondition;
    selectCondition.selectedIndex = self.selectedIndex;
    selectCondition.pid = self.selectedInfo.iD;
    
    [self presentViewController:selectCondition animated:YES completion:nil];
}

#pragma mark -筛选界面代理回调
- (void)selectCondition:(SelectConditionController *)selectCondition ScreeningCondition:(ScreeningCondition *)screeningCondition defaultSelected:(int)selectedIndex
{
    self.screeningCondition = screeningCondition;
    self.selectedIndex = selectedIndex;
    
    self.isDeleteDataArr = YES;
    self.page = nil;
    
    if (self.isNotLimitTime)
    {
        [self SendConditionHandleRequestWithType:NotLimitTime];
    }else
    {
       [self SendConditionHandleRequestWithType:WithTime];
    }
    
    [DefaultDataModel shareInstance].ProjectorState = self.screeningCondition.ProjectorState;
    [DefaultDataModel shareInstance].PhoneState = self.screeningCondition.PhoneState;
    [DefaultDataModel shareInstance].ParticipantsNumber = self.screeningCondition.ParticipantsNumber;
    [DefaultDataModel shareInstance].TelevisionState = self.screeningCondition.TelevisionState;
    [DefaultDataModel shareInstance].meetingRoomAddressIds = self.screeningCondition.meetingRoomAddressIds;
    [DefaultDataModel shareInstance].selectedIndex = selectedIndex;
}

#pragma mark -初始化筛选条件
-(ScreeningCondition *)screeningCondition
{
    if (_screeningCondition == nil)
    {
        _screeningCondition = [[ScreeningCondition alloc] init];
        
        _screeningCondition.meetingRoomAddressIds = self.selectedInfo.iD;
        _screeningCondition.ProjectorState = @"0";
        _screeningCondition.TelevisionState = @"0";
        _screeningCondition.PhoneState = @"0";
        _screeningCondition.ParticipantsNumber = @"0";
    }
    
    return _screeningCondition;
}


#pragma mark - 数据请求

#pragma mark - 发送条件筛选请求
- (void)SendConditionHandleRequestWithType:(RequestType)type
{
    [ProgressView showCustomProgressViewAddTo:self.view];
    
     __weak typeof(self) wself = self;
    
    FObjectModel *fObject = [[FObjectModel alloc] init];
    
    fObject.queryDate = [NSString stringWithFormat:@"%@ 08:00:00",[NSDate getymdStrWithSelectedTime:_selectedTime]];
    fObject.beginDate = [NSDate getBeginDateWithSelectedTime:_selectedTime];
    fObject.endDate = [NSDate getEndDateWithSelectedTime:_selectedTime];
    if (_ConditionHandle == nil)
    {
        _ConditionHandle = [[GetMeetingRoomBookingInfoHandle alloc] init];
    }
    if([NSDate canscheduleMeetingWithNowDate:[NSDate getServerDate]]){
        
        [_ConditionHandle sendJSONRequestWithFObjectModel:fObject ScreeningCondition:self.screeningCondition page:self.page  andRequestType:type Success:^(ResponseObject *response)
        {
            [wself.tableView headerEndRefreshing];
            [wself.tableView footerEndRefreshing];
            [ProgressView hiddenCustomProgressViewAddTo:wself.view];
            if (response.s)
            {
                wself.bgView.hidden = YES;
                wself.bgAlertView.hidden = YES;
                wself.touchRefreshView.hidden = YES;
                
                NSArray *dataArray = (NSArray *)response.d;
                
                if (dataArray.count > 0)
                {
                    wself.page.pno++;
                    wself.page.e=response.p.e;
                    wself.page.t=[response.p.t intValue];
                    [wself loadDataArrWithArray:dataArray];
                }else
                {
                    [self checkTime];
                    if (wself.page.pno > 1)
                    {
                        [MarkedWords showMarkedWordsWithMessage:@"没有加载到更多数据" addToView:wself.view];
                    }else
                    {
                        [wself.dataArr removeAllObjects];
                        [wself.tableView reloadData];
                    }
                }
                [DefaultDataModel shareInstance].dataArr = [NSMutableArray arrayWithArray:wself.dataArr];
             }
            
            if (wself.dataArr.count == 0)
            {
                wself.touchRefreshView.hidden = NO;
                wself.touchRefreshView.title = @"很抱歉，没有符合条件的会议室!";
            }

        } failure:^(NSError *error)
        {
            [ProgressView hiddenCustomProgressViewAddTo:wself.view];
            if (wself.dataArr.count == 0)
            {
                wself.touchRefreshView.hidden = NO;
                wself.touchRefreshView.title = @"网络发生异常";
            }else
            {
                [MarkedWords showMarkedWordsWithMessage:@"系统错误" addToView:wself.view];
            }
            [wself.tableView headerEndRefreshing];
            [wself.tableView footerEndRefreshing];
        }];
    }else{
        [ProgressView hiddenCustomProgressViewAddTo:self.view];
        [self loadDataArrWithArray:nil];
        self.touchRefreshView.hidden = NO;
        self.touchRefreshView.title = @"0:00-8:15不提供会议预订服务!";
    }
}

- (void)checkTime
{
    if (![NSDate checkAreadySelectTime:_selectedTime])
    {
        AppDelegate *delegate = [UIApplication sharedApplication].delegate;
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:delegate.window.bounds Message:@"该会议时间已过期，请重新选择!" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
        [delegate.window addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
        return;
    }
}

#pragma mark - 初始化数据源
- (NSMutableArray<MeetingRoomInfo *> *)dataArr
{
    if (_dataArr == nil)
    {
        _dataArr = [[NSMutableArray alloc] init];
    }
    
    return _dataArr;
}

- (void)loadDataArrWithArray:(NSArray *)array
{
    if (self.isDeleteDataArr)
    {
        [self.dataArr removeAllObjects];
    }
    
    for (NSDictionary *dict in array)
    {
        MeetingRoomInfo *roomInfo = [[MeetingRoomInfo alloc] initWithDictionary:dict];
        [self.dataArr addObject:roomInfo];
    }
    
    [self.tableView reloadData];
}

#pragma mark - 加载点击重新加载视图
- (void)loadTouchRefreshView
{
    self.touchRefreshView = [[TouchRefreshView alloc] initWithFrame:CGRectMake(0, 116, APPW, APPH-116-48)];
    [self.view addSubview:self.touchRefreshView];
    self.touchRefreshView.delegate = self;
    self.touchRefreshView.hidden = YES;
}

- (void)touchRefreshViewDidTouch:(TouchRefreshView *)touchView
{
    if (self.isNotLimitTime)
    {
        [self SendConditionHandleRequestWithType:NotLimitTime];
    }else
    {
        [self SendConditionHandleRequestWithType:WithTime];
    }

}

#pragma mark -获取离用户最近的地址
- (void)sendRecentBuildHandleRequestWithLatitudeAndLongitude:(NSString *)latitudeAndLongitude
{
    __weak typeof(self) wself =self;
    
    if (_recentBuildHandle == nil)
    {
        _recentBuildHandle = [[GetRecentBuildingAddressInfoHandle alloc] init];
    }
    
    [_recentBuildHandle sendJSONRequestWithLatitudeAndLongitude:latitudeAndLongitude Success:^(ResponseObject *response)
    {
        wself.bgView.hidden = YES;
        wself.bgAlertView.hidden = YES;
        
        if (response.s)
        {
            wself.selectedInfo = [[MeetingRoomModel alloc] initWithDictionary:(NSDictionary *)response.d];
            [wself setPlaceLabelText:wself.selectedInfo.addessChinese];
            [DefaultDataModel shareInstance].pid = wself.selectedInfo.iD;
            [DefaultDataModel shareInstance].pName = wself.selectedInfo.addessChinese;
            [wself SendConditionHandleRequestWithType:WithTime];
            
        }else
        {
            CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:wself.view.bounds delegate:wself Message:@"抱歉，未获取到有附近会议室地址" firstButtonTitle:@"选择会议区域" secondButtonTitle:nil ImageName:@"cri_location" andImageSize:CGSizeMake(25, 36) AlertWidth:APPW>320?320:300];
            [wself.view addSubview:alertView];
        }
    } failure:^(NSError *error)
    {
        wself.bgView.hidden = YES;
        wself.bgAlertView.hidden = YES;
        
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:wself.view.bounds delegate:wself Message:@"抱歉，网络错误，请手动选择会议区域" firstButtonTitle:@"选择会议区域" secondButtonTitle:nil ImageName:@"cri_location" andImageSize:CGSizeMake(25, 36) AlertWidth:APPW>320?320:300];
        [wself.view addSubview:alertView];
    }];
}

#pragma mark -CustomAlertView代理方法
- (void)CustomAlertView:(CustomAlertView *)customAlert andButtonClickIndex:(NSInteger)index
{
    if (index == 0)
    {
        self.bgView.hidden = YES;
        [self selectPlace:nil];
    }
}

#pragma mark 添加上下拉刷新控件
- (void)setupRefresh
{
    // 1.下拉刷新(进入刷新状态就会调用self的headerRereshing)
    [_tableView addHeaderWithTarget:self action:@selector(headerRereshing)];
    // 自动刷新(一进入程序就下拉刷新)
    //[tableView1 headerBeginRefreshing];
    
    // 2.上拉加载更多(进入刷新状态就会调用self的footerRereshing)
    [_tableView addFooterWithTarget:self action:@selector(footerRereshing)];
    
    // 设置文字(也可以不设置,默认的文字在MJRefreshConst中修改)
    _tableView.headerPullToRefreshText = @"下拉可以刷新了";
    _tableView.headerReleaseToRefreshText = @"松开马上刷新了";
    _tableView.headerRefreshingText = @"刷新中...";
    
    _tableView.footerPullToRefreshText = @"上拉可以加载更多数据了";
    _tableView.footerReleaseToRefreshText = @"松开马上加载更多数据了";
    _tableView.footerRefreshingText = @"加载中...";
}

#pragma mark 开始进入刷新状态
- (void)headerRereshing
{
    // 1.刷新数据
    
    self.isDeleteDataArr = YES;
    self.page = nil;
    if (self.isNotLimitTime)
    {
        [self SendConditionHandleRequestWithType:NotLimitTime];
    }else
    {
        [self SendConditionHandleRequestWithType:WithTime];
    }

}

- (void)footerRereshing
{
    // 1.加载下一页数据
    self.isDeleteDataArr = NO;
    if (self.isNotLimitTime)
    {
        [self SendConditionHandleRequestWithType:NotLimitTime];
    }else
    {
        [self SendConditionHandleRequestWithType:WithTime];
    }
}

#pragma mark -获取服务器时间
- (void)getServerDateRequest
{
    __weak typeof(self) wself = self;
    
    [[[GetServerDateHandle alloc] init] sendJSONRequestWithSuccess:^(ResponseObject *response)
    {
        if (response.s)
        {
            wself.serverDate = [NSDate dateWithString:(NSString *)response.d format:[NSDate ymdHmsFormat]];
            
            [NSDate getTimestampWithSystemDate:[NSDate date] ServerDate:wself.serverDate];
            
            wself.selectedTime = [NSDate getDefaultSelectedStringWithNowDate:_serverDate];
        }
         
    } failure:^(NSError *error)
    {
    }];
}

#pragma mark -获取可选时间范围请求
- (void)getValidBookDateRequest
{
    __weak typeof(self) wself = self;
    
    if (_validBookDateHandle == nil)
    {
        _validBookDateHandle = [[GetValidBookDateHandle alloc] init];
    }
    
    [_validBookDateHandle sendJSONRequestWithSuccess:^(ResponseObject *response)
    {
        if (response.s)
        {
            NSDictionary *dict = response.d;
            
            if (dict[@"maxDate"])
            {
                wself.maxDate = [NSDate dateWithString:dict[@"maxDate"] format:[NSDate ymdFormat]];
            }
        }
    } failure:^(NSError *error)
     {
        
     }];
}

- (void)setMaxDate:(NSDate *)maxDate
{
    _maxDate = maxDate;
    
    [[NSUserDefaults standardUserDefaults] setObject:[NSDate stringWithDate:maxDate format:[NSDate ymdFormat]] forKey:@"maxDate"];
    [[NSUserDefaults standardUserDefaults] synchronize];
}

#pragma mark- 获取用户附近地点信息
- (void)getRecentBuild
{
    NSString *longitudeAndLatitude = [[NSUserDefaults standardUserDefaults] objectForKey:@"LongitudeAndLatitude"];
    
    if (longitudeAndLatitude)
    {
        [self sendRecentBuildHandleRequestWithLatitudeAndLongitude:longitudeAndLatitude];
        
    }else
    {
        self.bgAlertView.hidden = YES;
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds delegate:self Message:@"抱歉，获取不到您的位置信息，请选择会议区域!" firstButtonTitle:@"选择会议区域" secondButtonTitle:nil ImageName:@"cri_location" andImageSize:CGSizeMake(25, 36) AlertWidth:APPW>320?320:300];
        [self.view addSubview:alertView];
    }
}

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

@end
