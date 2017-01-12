//
//  PhoneOrVideoMeetingDeailsViewController.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/3/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "PhoneOrVideoMeetingDeailsViewController.h"
#import "MeetingDetailsTableViewCell.h"
#import "UIColor+LJFColor.h"
#import "GetMeetingInfo.h"
#import "CancelMeetingRoom.h"
#import "EndMeetingRoom.h"
#import "CustomAlertView.h"
#import "Tools.h"
#import "UIColor+LJFColor.h"
#import "ProgressView.h"
#import "MarkedWords.h"
#import "GetMeetingJoinInfo.h"
#import "MeetingJoinInfo.h"
#import "ScanCodePersonnelViewController.h"
#import "GetMeetingProlong.h"
#import "GetMeetingProlongInfo.h"
#import "NSDate+LJFDate.h"
#import "MeetingProlong.h"
#import "MeetingDominateViewController.h"
#import "MeetingOperation.h"
#import "AttendanceOperation.h"
@interface PhoneOrVideoMeetingDeailsViewController (){
    GetMeetingInfo *getMeetingInfo;//请求会议信息接口对象
    CancelMeetingRoom *cancelMeetingRoom;//退订会议接口对象
    EndMeetingRoom    *endMeetingRoom;//结束会议接口对象
    GetMeetingJoinInfo *getMeetingJoinInfo;//获取会议的参会会议室或者人员接口对象
    GetMeetingProlong *meetingProlong;
    GetMeetingProlongInfo *getMeetingProlongInfo;
    MeetingOperation *meetingOperation;
    AttendanceOperation *attendanceOperation;
    NSTimer * timer;
    BOOL isStartTimer;
}

@end

@implementation PhoneOrVideoMeetingDeailsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    isStartTimer=NO;

    _pOrVideoDeailsView=[[[NSBundle mainBundle]loadNibNamed:@"PhoneOrVideoMeetingDeailsView" owner:nil options:nil]firstObject];
    _pOrVideoDeailsView.frame=CGRectMake(0, 68, APPW, APPH-68);
    __weak PhoneOrVideoMeetingDeailsViewController *controller = self;
    _pOrVideoDeailsView.unsubscribeBlock=^{
        [controller unsubscribeAction:nil];
    };
    _pOrVideoDeailsView.gotoScanCodeListBlock=^{
        [controller  gotoScanCodeList];
    };
    _pOrVideoDeailsView.requestMeetingJoinInfoBlock=^{
        [controller cycleRequestMeetingJoinInfo];
    };
    _pOrVideoDeailsView.requestMeetingProlongBlock=^{
        [controller requestMeetingProlong];
    };
    
    _pOrVideoDeailsView.requestMeetingProlongInfoBlock=^{
        [controller requestMeetingProlongInfo];
    };
    _pOrVideoDeailsView.skipMeetingDominateBlock=^{
        [controller  gotoMeetingDominate];
    };
    
    _pOrVideoDeailsView.requestMeetingOperationBlock=^(NSString *type,MeetingJoinInfo *meetingJoinInfo){
        [controller requestMeetingOperation:type MeetingJoinInfo:meetingJoinInfo];
        
    };
//pOrVideoDeailsView.hidden=YES;
    [self.view addSubview:_pOrVideoDeailsView];
    //_tableView=pOrVideoDeailsView.meetingTableView;
   
    [self initialData];
        // Do any additional setup after loading the view.
}
#pragma mark 初始化数据
-(void)initialData{
    
    _meetingDeails=[[NSMutableArray alloc]init];
    getMeetingInfo=[[GetMeetingInfo alloc]init];
    //getMeetingJoinInfo=[[GetMeetingJoinInfo alloc]init];
    [self requestMeetingInfo];
}
#pragma mark 跳转签到表
-(void)gotoScanCodeList{
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"ScanCode" bundle:nil];
    ScanCodePersonnelViewController  *scanCodePersonnelViewController = [storyboard instantiateViewControllerWithIdentifier:@"ScanCodePersonnelViewController"];
    scanCodePersonnelViewController.meetingInfo=_meetInfo;
    [self.navigationController pushViewController:scanCodePersonnelViewController animated:YES];
}
#pragma mark 跳转会议控制
-(void)gotoMeetingDominate{
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
    MeetingDominateViewController  *meetingDominateViewController = [storyboard instantiateViewControllerWithIdentifier:@"MeetingDominateViewController"];
    meetingDominateViewController.meetingId=_meetInfo.meetingID;
    meetingDominateViewController.meetingJoinInfoList=_meetingJoinInfoList;
    __weak typeof(self) wself = self;
    meetingDominateViewController.refreshMeetingJoinInfoBlock=^{
        [wself requestMeetingJoinInfo];
    };
    [self.navigationController pushViewController:meetingDominateViewController animated:YES];
}
#pragma mark 处理显示数据
-(void)loadMeetingData{
    [_pOrVideoDeailsView loadMeetingData:_meetInfo];
    
    NSLog(@"%@",[NSDate getServerDate]);
}

#pragma mark 请求会议信息
-(void)requestMeetingInfo{
    [ProgressView showCustomProgressViewAddTo:self.view];
     __weak typeof(self) wself = self;
    [getMeetingInfo sendJSONRequestWithEmeetingId:wself.meetingId Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            
            if ([response.d isKindOfClass:[NSDictionary class]]) {
                wself.meetInfo=[[MeetingInfo alloc]initWithMeetingDictionary:response.d];
                [wself loadMeetingData];
            }
        }else{
             [ProgressView showHUDAddedTo:wself.view ProgressText:response.m];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
         [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}

#pragma mark - 退订请求
-(void)requestCancelMeetingRoom{
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!cancelMeetingRoom) {
        cancelMeetingRoom=[[CancelMeetingRoom alloc]init];
    }
    __weak typeof(self) wself = self;
    [cancelMeetingRoom sendJSONRequestWithEmeetingId:wself.meetingId RoomId:wself.meetInfo.bookingsMeetingRoomIds Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            NSLog(@"退订成功！");
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"退订成功！"];
            wself.block();
            [wself performSelector:@selector(back:) withObject:nil afterDelay:1.5];
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
    
}
#pragma mark - 结束请求
-(void)requestEndMeetingRoom{
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!endMeetingRoom) {
        endMeetingRoom=[[EndMeetingRoom alloc]init];
    }
    __weak typeof(self) wself = self;
    [endMeetingRoom sendJSONRequestWithEmeetingId:wself.meetingId RoomId:wself.meetInfo.bookingsMeetingRoomIds Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            NSLog(@"结束成功！");
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"结束成功！"];
            wself.block();
            [wself performSelector:@selector(back:) withObject:nil afterDelay:1.5];
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
    
}

#pragma mark - 会议操作接口
-(void)requestMeetingOperation:(NSString *)operationType  MeetingJoinInfo:(MeetingJoinInfo *)meetingJoinInfo{
    if (!meetingOperation) {
        meetingOperation=[[MeetingOperation alloc]init];
    }
    __weak typeof(self) wself = self;
    [meetingOperation sendJSONRequestWithMeetingId:wself.meetingId TermId:meetingJoinInfo.meetingRoomID Number:meetingJoinInfo.terminalNumber OperationType:operationType Type:meetingJoinInfo.terminalType Success:^(ResponseObject *response) {

        if (response.s) {
            [wself requestMeetingJoinInfo];
        }else{
            [wself requestMeetingJoinInfo];
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    } failure:^(NSError *error) {
        [wself requestMeetingJoinInfo];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
    
}

#pragma mark -获取会议的参会会议室或者人员
-(void)cycleRequestMeetingJoinInfo{
    if (!getMeetingJoinInfo) {
        getMeetingJoinInfo=[[GetMeetingJoinInfo alloc]init];
         [ProgressView showCustomProgressViewAddTo:self.view];
    }
    __weak typeof(self) wself = self;
    [getMeetingJoinInfo sendJSONRequestWithMeetingId:wself.meetingId  Success:^(ResponseObject *response) {
        [wself performSelector:@selector(cycleRequestMeetingJoinInfo) withObject:nil afterDelay:8.0f];
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSMutableArray *meetingJoinArr=[[NSMutableArray alloc]init];
                for (NSDictionary *dic in response.d) {
                     MeetingJoinInfo *meetingJoinInfo=[[MeetingJoinInfo alloc]initWithDictionary:dic];
                    [meetingJoinArr addObject:meetingJoinInfo];
                }
                wself.pOrVideoDeailsView.meetingDominateView.meetingJoinInfoList=meetingJoinArr;
                wself.meetingJoinInfoList=meetingJoinArr;
                [wself.pOrVideoDeailsView.meetingDominateView.tableview reloadData];
            }
        }else{
            //[ProgressView showHUDAddedTo:wself.view ProgressText:response.m];
        }
    } failure:^(NSError *error) {
        [wself performSelector:@selector(cycleRequestMeetingJoinInfo) withObject:nil afterDelay:8.0f];
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];

}

#pragma mark -获取会议的参会会议室或者人员
-(void)requestMeetingJoinInfo{
    if (!getMeetingJoinInfo) {
        getMeetingJoinInfo=[[GetMeetingJoinInfo alloc]init];
        [ProgressView showCustomProgressViewAddTo:self.view];
    }
    __weak typeof(self) wself = self;
    [getMeetingJoinInfo sendJSONRequestWithMeetingId: wself.meetingId  Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSMutableArray *meetingJoinArr=[[NSMutableArray alloc]init];
                for (NSDictionary *dic in response.d) {
                    MeetingJoinInfo *meetingJoinInfo=[[MeetingJoinInfo alloc]initWithDictionary:dic];
                    [meetingJoinArr addObject:meetingJoinInfo];
                }
                wself.pOrVideoDeailsView.meetingDominateView.meetingJoinInfoList=meetingJoinArr;
                wself.meetingJoinInfoList=meetingJoinArr;
                [wself.pOrVideoDeailsView.meetingDominateView.tableview reloadData];
            }
        }else{
            [ProgressView showHUDAddedTo:wself.view ProgressText:response.m];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
}

#pragma mark 返回上一页
- (IBAction)back:(id)sender {
    [self.navigationController popViewControllerAnimated:YES];

}
#pragma mark 退订
- (void)unsubscribeAction:(id)sender {
    
    if ([@"1" isEqualToString:_meetInfo.operatingState]) {
        
        CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:[NSString stringWithFormat:@"确定要退订“%@”吗?",_meetInfo.meetingName] firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
        
        [customAlertView setLableAttributedColor:[UIColor colorWithHexString:@"01AEFF"] range:NSMakeRange(5, _meetInfo.meetingName.length+2)];
        [self.view addSubview:customAlertView];
    }else if([@"2" isEqualToString:_meetInfo.operatingState]){
        CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:[NSString stringWithFormat:@"确定要结束“%@”吗?",_meetInfo.meetingName] firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
        [customAlertView setLableAttributedColor:[UIColor colorWithHexString:@"01AEFF"] range:NSMakeRange(5, _meetInfo.meetingName.length+2)];
        customAlertView.tag=10000000;
        [self.view addSubview:customAlertView];
    }else{
        CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:[NSString stringWithFormat:@"确定要签到“%@”吗?",_meetInfo.meetingName] firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
        [customAlertView setLableAttributedColor:[UIColor colorWithHexString:@"01AEFF"] range:NSMakeRange(5, _meetInfo.meetingName.length+2)];
        customAlertView.tag=11000000;
        [self.view addSubview:customAlertView];
    }
}
#pragma mark - 获取会议时间延长信息接口
-(void)requestMeetingProlongInfo{
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!getMeetingProlongInfo) {
        getMeetingProlongInfo=[[GetMeetingProlongInfo alloc]init];
    }
    __weak typeof(self) wself = self;
    [getMeetingProlongInfo sendJSONRequestWithMeetingId:wself.meetingId Success:^(ResponseObject *response){
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        
        if (response.s) {
            if([response.d isKindOfClass:[NSArray class]]){
                
                //if (response.d>0) {
                    NSMutableArray *arr=[[NSMutableArray alloc]init];
                    for (NSDictionary *dic in response.d) {
                        MeetingProlong *meetingProlong1=[[MeetingProlong alloc]initWithDictionary:dic EndTime:wself.meetInfo.endDate];
                        if ([@"Y" isEqualToString: meetingProlong1.isProlong]) {
                            if ([dic[@"PLT"] intValue]<30) {
                                [arr addObject:meetingProlong1];
                            }
                        }else{
                            [arr addObject:meetingProlong1];
                        }
                    }
                //if (arr.count>0) {
                    [wself.pOrVideoDeailsView.meetingDominateView dominate:arr];
                //}else{
                   // [self requestMeetingProlong];
                //}
                
            }
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    }failure:^(NSError *error){
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}


#pragma mark - 延长会议时间请求
-(void)requestMeetingProlong{
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!meetingProlong) {
        meetingProlong=[[GetMeetingProlong alloc]init];
    }
    __weak typeof(self) wself = self;
    [meetingProlong sendJSONRequestWithMeetingId:wself.meetingId Success:^(ResponseObject *response){
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            NSLog(@"延长成功！");
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"延长成功！"];
            _pOrVideoDeailsView.meetingDominateView.extendView.hidden=YES;
            _pOrVideoDeailsView.meetingDominateView.dominateBtn.hidden=YES;
            _pOrVideoDeailsView.meetingDominateView.tableViewBottom=0;
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    }failure:^(NSError *error){
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}

#pragma mark - 签到请求
-(void)requestAttendanceOperation{
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!attendanceOperation) {
        attendanceOperation=[[AttendanceOperation alloc]init];
    }
    __weak typeof(self) wself = self;
    [attendanceOperation sendJSONRequestWithMeetingId:wself.meetingId CodeInfo:@"" Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"签到成功！"];
            [wself.pOrVideoDeailsView hiddenCancelBtn];
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}


#pragma mark —CustomAlertView代理方法
- (void)CustomAlertView:(CustomAlertView *)customAlert andButtonClickIndex:(NSInteger)index{
    if (index==1) {
        if (customAlert.tag==10000000) {
            [self requestEndMeetingRoom];
        }else if (customAlert.tag==11000000) {
            [self requestAttendanceOperation];
        }else{
            [self requestCancelMeetingRoom];
        }
    }
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
