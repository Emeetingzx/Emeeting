//
//  AllMeetingListViewController.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/17.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "AllMeetingListViewController.h"
#import "MeetingDetailsViewController.h"
#import "MyMeetingTableViewCell.h"
#import "MJRefresh.h"
#import "CancelMeetingRoom.h"
#import "EndMeetingRoom.h"
#import "PhoneOrVideoMeetingDeailsViewController.h"
#import "Tools.h"
#import "UIColor+LJFColor.h"
#import "ProgressView.h"
#import "MarkedWords.h"
#import "MeetingRoomInfo.h"
#import "FMDBManager.h"
#import "MeetingInfoManager.h"
@interface AllMeetingListViewController ()

@end

@implementation AllMeetingListViewController{
    GetUserRelevantMeetingInfo *getUserRelevantMeetingInfo;//获取与我相关的会议信息
    CancelMeetingRoom *cancelMeetingRoom;
    EndMeetingRoom    *endMeetingRoom;
    MeetingInfo *meet;//操作的会议对象
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    _tableView.delegate=self;
    _tableView.dataSource=self;
    _tableView.tableFooterView=[[UIView alloc]init];
    [_tableView registerNib:[UINib nibWithNibName:@"MyMeetingTableViewCell" bundle:nil] forCellReuseIdentifier:@"MyMeetingTableViewCell"];
    [ProgressView showCustomProgressViewAddTo:_tableView];
    [self loadRefreshView:_tableView];
    [self initialData];
}

#pragma mark -初始化数据
-(void)initialData{
    _dateTitle.text=[NSString stringWithFormat:@"%lu年%lu月%lu日",(unsigned long)_dateModel.year,(unsigned long)_dateModel.month,(unsigned long)_dateModel.day];
    self.myAllAddValueInfoArr=[[NSMutableArray<MeetingInfo *> alloc]init];
    _p=[[PageRequestObject alloc]init];
    _isDown=NO;
    [self requestMyAllAddValueInfo];
    
}

#pragma mark tableView代理
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return _myAllAddValueInfoArr.count;
}



- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
     tableView.rowHeight=116;
    MyMeetingTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"MyMeetingTableViewCell"];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.unsubscribeBtn.tag=1000000+indexPath.row;
     MeetingInfo *meetingInfo=_myAllAddValueInfoArr[indexPath.row];
    cell.meetingName.text=meetingInfo.meetingName;
    cell.organizePeopleChineseName.text=meetingInfo.organizePeopleChineseName;
    cell.meetingTime.text=meetingInfo.showMeetingTime;
    if ([@"常规会议" isEqualToString:meetingInfo.meetingType]) {
        cell.meetingPlace.text= [self returnMeetingRoom:meetingInfo.bookingsMeetingRoomIds];
    }else{
        cell.meetingPlace.text=meetingInfo.meetingType;
    }
    if ([@"1" isEqualToString:meetingInfo.operatingState]) {
        [cell.unsubscribeBtn setTitle:@"退订" forState:UIControlStateNormal];
        cell.unsubscribeBtn.hidden=NO;
    }else if([@"2" isEqualToString:meetingInfo.operatingState]){
        [cell.unsubscribeBtn setTitle:@"结束" forState:UIControlStateNormal];
        cell.unsubscribeBtn.hidden=NO;
    }else{
        cell.unsubscribeBtn.hidden=YES;
    }
    [cell.unsubscribeBtn addTarget:self action:@selector(unsubscribeAction:) forControlEvents:UIControlEventTouchUpInside];
    cell.meetingImage.image=[UIImage imageNamed:[self returnMeetingImage:meetingInfo.meetingType]];
    tableView.rowHeight=[cell cellHeightServiceAddress:meetingInfo.bookingsMeetingRoomIds MeetingName:meetingInfo.meetingName];
    return cell;
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    if([@"电话会议桥"isEqualToString:_myAllAddValueInfoArr[indexPath.row].meetingType] || [@"视频会议桥"isEqualToString:_myAllAddValueInfoArr[indexPath.row].meetingType]){
        UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
        PhoneOrVideoMeetingDeailsViewController  *phoneOrVideoMeetingDeailsViewController = [storyboard instantiateViewControllerWithIdentifier:@"PhoneOrVideoMeetingDeailsViewController"];
        
        //phoneOrVideoMeetingDeailsViewController.emeetingId=_myAllAddValueInfoArr[indexPath.row].meetingID;
        phoneOrVideoMeetingDeailsViewController.meetingId=_myAllAddValueInfoArr[indexPath.row].meetingID;
        __weak typeof(self) wself = self;
        phoneOrVideoMeetingDeailsViewController.block=^{
            [wself headerRereshing];
        };
        [self.navigationController pushViewController:phoneOrVideoMeetingDeailsViewController animated:YES];
    }else{
        UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
        MeetingDetailsViewController  *meetingDetailsViewController = [storyboard instantiateViewControllerWithIdentifier:@"MeetingDetailsViewController"];
        meetingDetailsViewController.emeetingId=_myAllAddValueInfoArr[indexPath.row].meetingID;
        //meetingDetailsViewController.meetingInfo=_myAllAddValueInfoArr[indexPath.row];
        __weak typeof(self) wself = self;
        meetingDetailsViewController.block=^{
            [wself headerRereshing];
        };
        
        [self.navigationController pushViewController:meetingDetailsViewController animated:YES];
    }
    
    
    NSLog(@"会议详情！");
}

#pragma mark  转换对应的数据
-(NSString *)returnMeetingImage:(NSString *) meetingTypeString{
    NSString *meetingImage=@"";
    if ([@"常规会议" isEqualToString:meetingTypeString] ) {
        meetingImage=@"mymeeting_meeting.png";
    }else if ([@"电话会议桥" isEqualToString:meetingTypeString]){
        meetingImage=@"mymeeting_conference_call.png";
    }else if ([@"视频会议桥" isEqualToString:meetingTypeString]){
        meetingImage=@"mymeeting_video_conferencing.png";
    }else{
        meetingImage=@"mymeeting_meeting.png";
    }
    return meetingImage;
}


#pragma mark 退订按钮事件
-(void)unsubscribeAction:(UIButton *)sender
{
     meet=_myAllAddValueInfoArr[sender.tag-1000000];
    NSString *message=[NSString stringWithFormat:@"确定退订“%@”吗?",meet.meetingName];
    if ([@"退订" isEqualToString:sender.titleLabel.text]) {
        CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:message firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
        [customAlertView setLableAttributedColor:[UIColor colorWithHexString:@"01AEFF"] range:NSMakeRange(5, meet.meetingName.length+2)];
        [self.view addSubview:customAlertView];
    }else{
        message=[NSString stringWithFormat:@"确定结束“%@”吗?",meet.meetingName];
        CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:message firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
        [customAlertView setLableAttributedColor:[UIColor colorWithHexString:@"01AEFF"] range:NSMakeRange(5, meet.meetingName.length+2)];
        customAlertView.tag=10000000;
        [self.view addSubview:customAlertView];
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

#pragma mark - 请求我参加的数据
-(void)requestMyAllAddValueInfo{
    
    if (!getUserRelevantMeetingInfo) {
        getUserRelevantMeetingInfo=[[GetUserRelevantMeetingInfo alloc]init];
    }
    __weak typeof(self) wself = self;
    
   NSString * dateString=[NSString stringWithFormat:@"%lu-%lu-%lu 08:00:00",(unsigned long)wself.dateModel.year,(unsigned long)wself.dateModel.month,(unsigned long)wself.dateModel.day];
    [getUserRelevantMeetingInfo sendJSONRequestWithMeetingBelonging:@"0" PageRequestObject:wself.p  MeetingDate:dateString Success:^(ResponseObject *response) {
        [wself.tableView headerEndRefreshing];
        [wself.tableView footerEndRefreshing];
        [ProgressView hiddenCustomProgressViewAddTo:wself.tableView];
        if (response.s) {
            if (wself.isDown)
            {
                [wself.myAllAddValueInfoArr removeAllObjects];
                wself.isDown=NO;
            }
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSArray *arr=response.d;
                if (arr.count!=0) {
                    wself.p.pno++;
                    wself.p.e=response.p.e;
                    wself.p.t=[response.p.t intValue];
                    for (NSDictionary *dic in response.d) {
                        MeetingInfo *meetingInfo=[[MeetingInfo alloc]initWithDictionary:dic];
                        [wself.myAllAddValueInfoArr addObject:meetingInfo];
                    }
                    
                }else{
                    if (wself.myAllAddValueInfoArr.count!=0) {
                        [MarkedWords showMarkedWordsWithMessage:@"没有更多数据" addToView:wself.view];
                    }
                }
            }
            if (wself.myAllAddValueInfoArr.count==0) {
                wself.touchRefreshView.hidden=NO;
                [wself.tableView removeHeader];
                [wself.tableView removeFooter];
            }else{
                wself.touchRefreshView.hidden=YES;
                [wself setupRefresh];
            }
            [wself.tableView reloadData];
        }else{
             [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    } failure:^(NSError *error) {
        [wself.tableView headerEndRefreshing];
        [wself.tableView footerEndRefreshing];
        [ProgressView hiddenCustomProgressViewAddTo:wself.tableView];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}

#pragma mark 加载刷新视图
-(void)loadRefreshView:(UIView *) view{
    _touchRefreshView=[[TouchRefreshView alloc]initWithFrame:CGRectMake(0, (APPH-40)/2-68, APPW,40)];
    _touchRefreshView.title=@"很抱歉，没有符合条件的会议!";
    _touchRefreshView.hidden=YES;
    _touchRefreshView.delegate=self;
    _touchRefreshView.tag=11000;
    [view addSubview:_touchRefreshView];
}
#pragma mark 实现TouchRefreshView的代理
- (void)touchRefreshViewDidTouch:(TouchRefreshView *)touchView{
    [ProgressView showCustomProgressViewAddTo:_tableView];
    [self headerRereshing];
    
}

#pragma mark  -集成刷新控件
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
    
    _isDown=YES;
    _p=[[PageRequestObject alloc]init];
    [self requestMyAllAddValueInfo];
}

#pragma mark -加载下一页数据
- (void)footerRereshing
{
    // 1.加载下一页数据
    [self requestMyAllAddValueInfo];
    
}

#pragma mark - 退订请求
-(void)requestCancelMeetingRoom{
    
    if (!cancelMeetingRoom) {
        cancelMeetingRoom=[[CancelMeetingRoom alloc]init];
    }
    __weak typeof(self) wself = self;
    [cancelMeetingRoom sendJSONRequestWithEmeetingId:meet.meetingID RoomId:nil Success:^(ResponseObject *response) {
        if (response.s) {
            NSLog(@"退订成功！");
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"退订成功！"];
            [wself headerRereshing];
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
    [endMeetingRoom sendJSONRequestWithEmeetingId:meet.meetingID RoomId:nil Success:^(ResponseObject *response) {
        if (response.s) {
            NSLog(@"结束成功！");
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"结束成功！"];
            [wself headerRereshing];
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    } failure:^(NSError *error) {
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
    
}


-(NSString *)returnMeetingRoom:(NSString *)roomIds{
    
    if (![@""isEqualToString:roomIds] && roomIds!=nil ) {
        NSArray   *bookingsMeetingRoomArr = [roomIds componentsSeparatedByString:@","];
        MeetingRoomInfo *meetingRoomInfo= [[[MeetingInfoManager shareInstance] selectSysMeetingRoomInfoWithRoomId:bookingsMeetingRoomArr[0]] firstObject];
        
        if (bookingsMeetingRoomArr.count>1) {
            NSString *meetingPlaceString=[NSString stringWithFormat:@"%@等%lu个",meetingRoomInfo.meetingRoomName,(unsigned long)bookingsMeetingRoomArr.count];
            return meetingPlaceString;
            
        }else{
            return meetingRoomInfo.meetingRoomName;
        }
    }
    return @"";
}

- (IBAction)back:(id)sender {
    self.block();
    [self.navigationController popViewControllerAnimated:YES];
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
