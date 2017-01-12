//
//  MeetingPlaceTableViewCell.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/19.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingPlaceTableViewCell.h"
#import "PlaceTableViewCell.h"
#import "EndMeetingRoom.h"
#import "CancelMeetingRoom.h"
#import "UIColor+LJFColor.h"
#import "Tools.h"
#import "ProgressView.h"
#import "MarkedWords.h"
#import "MeetingRoomInfo.h"
#import "MeetingInfoManager.h"
@implementation MeetingPlaceTableViewCell{
    EndMeetingRoom *endMeetingRoom;
    CancelMeetingRoom *cancelMeetingRoom;
    NSString *roomId;
}

- (void)awakeFromNib {
    // Initialization code
    _tableView.delegate=self;
    _tableView.dataSource=self;
    [_tableView registerNib:[UINib nibWithNibName:@"PlaceTableViewCell" bundle:nil] forCellReuseIdentifier:@"PlaceTableViewCell"];
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    
    return _bookingsMeetingRoomArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    PlaceTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"PlaceTableViewCell"];
    cell.rescindBtn.tag=1010+[indexPath row];
    [cell.rescindBtn addTarget:self action:@selector(unsubscribeAction:) forControlEvents:UIControlEventTouchUpInside];
    cell.placeRight.constant=65;
    if ([@"0" isEqualToString:_meetingInfo.operatingState]) {
        cell.rescindBtn.hidden=YES;
        cell.placeRight.constant=20;
    }else if ([@"1" isEqualToString:_meetingInfo.operatingState]) {
        cell.rescindBtn.hidden=NO;
        [cell.rescindBtn setTitle:@"退订" forState:UIControlStateNormal];
    }else{
        cell.rescindBtn.hidden=NO;
        [cell.rescindBtn setTitle:@"结束" forState:UIControlStateNormal];
    }
    cell.placeLable.text=  [self returnMeetingRoom: _bookingsMeetingRoomArr[indexPath.row]];
    tableView.rowHeight=48;
    return cell;
}

#pragma mark - 退订请求
-(void)requestCancelMeetingRoom{
     NSLog(@"退订！");
    if (!cancelMeetingRoom) {
        cancelMeetingRoom=[[CancelMeetingRoom alloc]init];
    }
    __weak typeof(self) wself = self;
    [cancelMeetingRoom sendJSONRequestWithEmeetingId:wself.meetingInfo.meetingID RoomId:roomId Success:^(ResponseObject *response) {
        if (response.s) {
            NSLog(@"退订成功！");
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"退订成功！"];
            wself.refreshBlock();
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    } failure:^(NSError *error) {
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
    
}

#pragma mark - 结束请求
-(void)requestEndMeetingRoom{
    
    if (!endMeetingRoom) {
        endMeetingRoom=[[EndMeetingRoom alloc]init];
    }
    __weak typeof(self) wself = self;
    [endMeetingRoom sendJSONRequestWithEmeetingId:wself.meetingInfo.meetingID RoomId:roomId Success:^(ResponseObject *response) {
        if (response.s) {
            NSLog(@"结束成功！");
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"结束成功！"];
            wself.refreshBlock();
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    } failure:^(NSError *error) {
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
    
}



#pragma mark 退订
- (void)unsubscribeAction:(UIButton *)sender {
    roomId=_bookingsMeetingRoomArr[sender.tag-1010];
    if ([@"1" isEqualToString:_meetingInfo.operatingState]) {
        CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:_view.bounds delegate:self Message:@"确定要退订吗?" firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
        [_view addSubview:customAlertView];
    }else{
        CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:_view.bounds delegate:self Message:@"确定要结束吗?" firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
        customAlertView.tag=10000000;
        [_view addSubview:customAlertView];
    }
}

#pragma mark —CustomAlertView代理方法
- (void)CustomAlertView:(CustomAlertView *)customAlert andButtonClickIndex:(NSInteger)index{
    if (index==1) {
        if (customAlert.tag==10000000) {
            [self requestEndMeetingRoom];
        }else{
            [self requestCancelMeetingRoom];
        }
    }
}

-(NSString *)returnMeetingRoom:(NSString *)rId{
    
    if (![@""isEqualToString:rId] && rId!=nil ) {
        
        MeetingRoomInfo *meetingRoomInfo= [[[MeetingInfoManager shareInstance] selectSysMeetingRoomInfoWithRoomId:rId]firstObject];
        return meetingRoomInfo.meetingRoomName;
    }
    return @"";
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
