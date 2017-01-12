//
//  MeetingScheduleView.m
//  会议预定
//
//  Created by efutureinfo on 16/2/4.
//  Copyright © 2016年 efutureinfo. All rights reserved.
//

#import "MeetingScheduleView.h"
#import "Tools.h"
#import "NetworkingManager.h"
#import "GetMeetingRoomBookingInfoHandle.h"
#import "NSDate+LJFDate.h"
#import "LockBookingMeetingRoomHandle.h"
#import "MarkedWords.h"
#import "ProgressView.h"
#import "CustomAlertView.h"

@interface MeetingScheduleView ()

@property (nonatomic, strong) NSString *selectedTime;
@property (nonatomic, strong) MeetingRoomInfo *roomInfo;

@property (nonatomic, strong) GetMeetingRoomBookingInfoHandle *getRoomInfoHandle;

@property (nonatomic, strong) LockBookingMeetingRoomHandle *lockMeetingHandle;

@property (nonatomic, assign) BOOL isNotlimitTime;

@property (nonatomic, strong) UITableView *tableView;

@end

@implementation MeetingScheduleView

-(instancetype)initWithFrame:(CGRect)frame andDictionary:(MeetingRoomInfo *)roominfo selectTime:(NSString *)selectedTime andIsNotLimitTime:(BOOL)isNotLimitTime
{
    self = [super initWithFrame:frame];
    if (self)
    {
        _isNotlimitTime = isNotLimitTime;
        _selectedTime = selectedTime;
        _roomInfo = roominfo;
        [self loadDetailView];
        [self SendConditionHandleRequestWithType:MeetIngRoomInfo];
    }
    return self;
}

- (void)loadDetailView
{
    self.backgroundColor = [UIColor colorWithWhite:0 alpha:0.5];
    
    _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, APPW, APPH - 20>DetailViewHeight?DetailViewHeight:APPH - 20) style:UITableViewStylePlain];
    
    _tableView.delegate = self;
    _tableView.dataSource = self;
    
    _tableView.backgroundColor = RGBA(248, 248, 248, 1.0);
    
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    [self addSubview:_tableView];
    
    [_tableView registerNib:[UINib nibWithNibName:@"MeetingSCell" bundle:nil] forCellReuseIdentifier:@"MeetingSCell"];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 1;
}

//返回Cell
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    MeetingSCell *cell = [tableView dequeueReusableCellWithIdentifier:@"MeetingSCell"];
    
    cell.backgroundColor = RGBA(248, 248, 248, 1.0);
    
    cell.delegate = self;
    cell.isNotLimitTime = _isNotlimitTime;
    cell.meetingRoomInfo = _roomInfo;
    cell.selectedTime = _selectedTime;
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return DetailViewHeight;
}

#pragma mark - 取消
- (void)MeetingSCellDidClickCancel:(MeetingSCell *)detailView
{
    if ([self.delegate respondsToSelector:@selector(meetingScheduleViewDidCancel:)])
    {
        [self.delegate meetingScheduleViewDidCancel:self];
    }
    [self removeFromSuperview];
}

#pragma mark - 锁定会议室
- (void)MeetingSCellDidClickConfirm:(MeetingSCell *)detailView andSelectedTime:(NSString *)selectTime
{
    self.selectedTime = selectTime;
    
    [self sendLockMeetingRoomRequest];
}

#pragma mark - 锁定会议室成功
- (void)LockMeetingRoomSuccessWithMeetingId:(NSString *)meetingId
{
    if ([self.delegate respondsToSelector:@selector(meetingScheduleView:lockMeetingSuccess:)])
    {
        [self.delegate meetingScheduleView:self lockMeetingSuccess:meetingId];
    }
    [self removeFromSuperview];

}

#pragma mark - 获取会议室信息请求
- (void)SendConditionHandleRequestWithType:(RequestType)type
{
    __weak typeof(self) wself = self;
    
    FObjectModel *fObject = [[FObjectModel alloc] init];
    fObject.meetingRoomId = self.roomInfo.meetingRoomId;
    fObject.queryDate = [NSDate getBeginDateWithSelectedTime:_selectedTime];
    
    if (_getRoomInfoHandle == nil)
    {
        _getRoomInfoHandle = [[GetMeetingRoomBookingInfoHandle alloc] init];
    }
    
    [_getRoomInfoHandle sendJSONRequestWithFObjectModel:fObject ScreeningCondition:nil page:nil  andRequestType:type Success:^(ResponseObject *response)
     {
         if (response.s)
         {
             MeetingRoomInfo *info = [[MeetingRoomInfo alloc] initWithDictionary:(NSDictionary *)response.d];
             wself.roomInfo.haveBookingMeetingInfos = info.haveBookingMeetingInfos;
             [wself.tableView reloadData];
         }else
         {
             if (response.m)
             {
                 [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself];
             }else
             {
                 [MarkedWords showMarkedWordsWithMessage:@"系统错误,请稍后重试" addToView:wself];
             }

         }
     } failure:^(NSError *error)
     {
         [MarkedWords showMarkedWordsWithMessage:@"网络不可用，请检查网络" addToView:wself];
     }];
}

#pragma mark - 发送锁定会议室请求
- (void)sendLockMeetingRoomRequest
{
    FObjectModel *fObject = [[FObjectModel alloc] init];
    fObject.meetingRoomId = self.roomInfo.meetingRoomId;
    fObject.beginDate = [NSDate getBeginDateWithSelectedTime:_selectedTime];
    fObject.endDate = [NSDate getEndDateWithSelectedTime:_selectedTime];
    
    [ProgressView showCustomProgressViewAddTo:self];

    __weak typeof(self) wself = self;
    
    if (_lockMeetingHandle == nil)
    {
        _lockMeetingHandle = [[LockBookingMeetingRoomHandle alloc] init];
    }
    
    [_lockMeetingHandle sendJSONRequestWithFObjectModel:fObject Success:^(ResponseObject *response)
    {
        [ProgressView hiddenCustomProgressViewAddTo:wself];
        if (response.s)
        {
            [wself LockMeetingRoomSuccessWithMeetingId:response.d[@"EID"]];
        }else
        {
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself];
        }
    } failure:^(NSError *error)
    {
        [ProgressView hiddenCustomProgressViewAddTo:wself];
        [MarkedWords showMarkedWordsWithMessage:@"网络不可用，请检查网络" addToView:wself];
    }];
}





@end
