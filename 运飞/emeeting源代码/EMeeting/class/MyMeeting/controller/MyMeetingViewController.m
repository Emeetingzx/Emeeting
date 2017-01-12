//
//  MyMeetingViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/1/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MyMeetingViewController.h"
#import "UIViewController+MMDrawerController.h"
#import "MyMeetingTableViewCell.h"
#import "MeetingDetailsViewController.h"
#import "AllMeetingViewController.h"
#import "UIColor+LJFColor.h"
#import "Tools.h"
#import "CancelMeetingRoom.h"
#import "EndMeetingRoom.h"
#import "PhoneOrVideoMeetingDeailsViewController.h"
#import "ProgressView.h"
#import "FMDBManager.h"
#import "MeetingRoomInfo.h"
#import "MeetingInfoManager.h"
#import "MarkedWords.h"
#import "RegularTool.h"
//没选择中的按钮字体颜色
#define defaultBtnColor  [UIColor colorWithRed:153/255.0 green:153/255.0 blue:153/255.0 alpha:1]

//没选择中的按钮背景颜色
#define defaultBgColor  [UIColor colorWithRed:244/255.0 green:244/255.0 blue:244/255.0 alpha:1]

//按钮选择字体颜色
#define selectedColor  [UIColor colorWithRed:1/255.0 green:174/255.0 blue:255/255.0 alpha:1]

#define selfViewWidth  self.view.frame.size.width
@interface MyMeetingViewController ()

@end

@implementation MyMeetingViewController{
    GetUserRelevantMeetingInfo *getUserRelevantMeetingInfo;//请求我的会议接口对象
    CancelMeetingRoom *cancelMeetingRoom;//退订接口对象
    EndMeetingRoom    *endMeetingRoom;//结束接口对象
    MeetingInfo *meet;//当前操作对象
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the
    [self initialView];
    [self initialData];
}

#pragma mark 初始化视图
-(void)initialView{
    
    _scrollView.contentSize=CGSizeMake(selfViewWidth*3,0);
    _scrollView.tag=11000;
    _scrollView.delegate=self;
    _scrollView.scrollEnabled=YES;
    _scrollView.pagingEnabled=YES;
    _scrollView.showsVerticalScrollIndicator = NO;
    _scrollView.showsHorizontalScrollIndicator = NO;
    for (int i=0; i<3; i++) {
        UITableView  *tabView=[[UITableView alloc]initWithFrame:CGRectMake(i*selfViewWidth,0, selfViewWidth,self.view.frame.size.height-_scrollView.frame.origin.y)];
        tabView.tag=10000+i;
        tabView.dataSource=self;
        tabView.delegate=self;
        tabView.tableFooterView=[[UIView alloc]init];
        tabView.separatorStyle = UITableViewCellSeparatorStyleNone;
        [self loadRefreshView:tabView];
        [ProgressView showCustomProgressViewAddTo:tabView];
        [tabView registerNib:[UINib nibWithNibName:@"MyMeetingTableViewCell" bundle:nil] forCellReuseIdentifier:@"MyMeetingTableViewCell"];
        [_scrollView addSubview:tabView];
    }
}

#pragma mark 初始化数据
-(void)initialData{
    _currentTableView=(UITableView *)[_scrollView viewWithTag:10000];
    
    _myReserveArr=[[NSMutableArray<MeetingInfo *> alloc]init];
    _myReservePage=[[PageRequestObject alloc]init];
    _myReserveisDown=NO;
    
    _myOrganizationalArr=[[NSMutableArray<MeetingInfo *> alloc]init];
    _myOrganizationalPage=[[PageRequestObject alloc]init];
    _myOrganizationalisDown=NO;
    
    _myJoinArr=[[NSMutableArray<MeetingInfo *> alloc]init];
    _myJoinPage=[[PageRequestObject alloc]init];
    _myJoinisDown=NO;
    
    meet=[[MeetingInfo alloc]init];
    
    if(_meeingType){
        if ([@"0" isEqualToString:_meeingType]) {
            [self skipMeetingDetailsViewController:_meeingId];
        }else{
            [self skipPhoneOrVideoMeetingDeailsViewController:_meeingId];
        }
    }
    [self requestmyReserveData];
    [self requestMyOrganizationalData];
    [self requestMyJoinData];
}

#pragma mark  只要滚动了就会触发
- (void)scrollViewDidScroll:(UIScrollView *)scrollView;
{
    if (scrollView.tag==11000) {
        if (_scrollView.contentOffset.x==0)
        {
            _currentTableView=(UITableView *)[_scrollView viewWithTag:10000];
            [self myReserve];
        }else if(_scrollView.contentOffset.x==self.view.frame.size.width)
        {
            
            _currentTableView=(UITableView *) [_scrollView viewWithTag:10001];
            [self myOrganizational];
        }else if(_scrollView.contentOffset.x==self.view.frame.size.width*2)
        {
            _currentTableView=(UITableView *)[_scrollView viewWithTag:10002];
            [self myJoinb];
        }
    }
    
}

#pragma mark 跳转到我预定的会议时按钮状态
-(void)myReserve{
   
    [_myReservebtn setTitleColor:selectedColor forState:UIControlStateNormal];
    _myReservebtn.backgroundColor=[UIColor clearColor];
    
    [_myOrganizationalbtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _myOrganizationalbtn.backgroundColor=defaultBgColor;
    
    [_myJoinbtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _myJoinbtn.backgroundColor=defaultBgColor;
    [UIView animateWithDuration:0.5f animations:^{
         _singViewLead.constant=0;
    }];
}

#pragma mark 跳转到我组织的会议时按钮状态
-(void)myOrganizational{
    
   
    [_myReservebtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _myReservebtn.backgroundColor=defaultBgColor;
    
    [_myOrganizationalbtn setTitleColor:selectedColor forState:UIControlStateNormal];
    _myOrganizationalbtn.backgroundColor=[UIColor clearColor];
    
    [_myJoinbtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _myJoinbtn.backgroundColor=defaultBgColor;
    [UIView animateWithDuration:0.5f animations:^{
        _singViewLead.constant=selfViewWidth/3;
    }];

}

#pragma mark 跳转到我参加的会议时按钮状态
-(void)myJoinb{
    
    [_myReservebtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _myReservebtn.backgroundColor=defaultBgColor;
    
    [_myOrganizationalbtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _myOrganizationalbtn.backgroundColor=defaultBgColor;
    
    [_myJoinbtn setTitleColor:selectedColor forState:UIControlStateNormal];
    _myJoinbtn.backgroundColor=[UIColor clearColor];
    [UIView animateWithDuration:0.5f animations:^{
        _singViewLead.constant=2*selfViewWidth/3;
    }];
    
}


#pragma mark 加载刷新视图
-(void)loadRefreshView:(UIView *) view{
    _touchRefreshView=[[TouchRefreshView alloc]initWithFrame:CGRectMake(0, (APPH-40)/2-116, APPW,40)];
    _touchRefreshView.title=@"很抱歉，没有符合条件的会议!";
    _touchRefreshView.hidden=YES;
    _touchRefreshView.tag=11000;
    _touchRefreshView.delegate=self;
    [view addSubview:_touchRefreshView];
}
#pragma mark 实现TouchRefreshView的代理
- (void)touchRefreshViewDidTouch:(TouchRefreshView *)touchView{
    [ProgressView showCustomProgressViewAddTo:_currentTableView];
    [self headerRereshing];
    
}


#pragma mark 刷新所有数据
-(void)refreshData{
    
    _myReserveisDown=YES;
    _myReservePage=[[PageRequestObject alloc]init];
    [self requestmyReserveData];

    _myOrganizationalisDown=YES;
    _myOrganizationalPage=[[PageRequestObject alloc]init];
    [self requestMyOrganizationalData];

    _myJoinisDown=YES;
    _myJoinPage=[[PageRequestObject alloc]init];
    [self requestMyJoinData];
}

#pragma mark tableView代理
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (tableView.tag==10000) {
        return self.myReserveArr.count;
    }else if(tableView.tag==10001){
        return self.myOrganizationalArr.count;
    }else{
        return self.myJoinArr.count;
    }
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    MyMeetingTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"MyMeetingTableViewCell"];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    MeetingInfo *meetingInfo=nil;
    if (tableView.tag==10000) {
        meetingInfo=self.myReserveArr[indexPath.row];
        [self unsubscribeBtnStyleMeetingInfo:meetingInfo TableViewCell:cell];
    }else if(tableView.tag==10001){
        meetingInfo=self.myOrganizationalArr[indexPath.row];
        [self unsubscribeBtnStyleMeetingInfo:meetingInfo TableViewCell:cell];
    }else{
        cell.unsubscribeBtn.hidden=YES;
        meetingInfo=self.myJoinArr[indexPath.row];
    }
    cell.meetingName.text=meetingInfo.meetingName;
    if ([@"常规会议" isEqualToString:meetingInfo.meetingType]) {
        cell.meetingPlace.text=[self returnMeetingRoom:meetingInfo.bookingsMeetingRoomIds];
    }else{
        cell.meetingPlace.text=meetingInfo.meetingType;
    }
    cell.unsubscribeBtn.tag=1000000+indexPath.row;
    
    cell.organizePeopleChineseName.text=meetingInfo.organizePeopleChineseName;
    cell.meetingTime.text=meetingInfo.showMeetingTime;
    cell.meetingImage.image=[UIImage imageNamed:[self returnMeetingImage:meetingInfo.meetingType]];
    tableView.rowHeight=[cell cellHeightServiceAddress:cell.meetingPlace.text MeetingName:meetingInfo.meetingName];
    
    return cell;
}


#pragma mark cell的点击事件
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    if (tableView.tag==10000) {
        [self toMeetingDeails:self.myReserveArr[indexPath.row] EndBtnStyle:@"0"];
    }else if(tableView.tag==10001){
        [self toMeetingDeails:self.myOrganizationalArr[indexPath.row]EndBtnStyle:@"0"];
    }else{
        [self toMeetingDeails:self.myJoinArr[indexPath.row] EndBtnStyle:@"1"];
    }
    
}

#pragma mark 会议操作
-(void)unsubscribeBtnStyleMeetingInfo:(MeetingInfo *)meetingInfo TableViewCell:(MyMeetingTableViewCell *) cell{
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

#pragma mark 跳转详情页
-(void)toMeetingDeails:(MeetingInfo *) meeting EndBtnStyle:(NSString *)endBtnStyle{
    
    if([@"电话会议桥"isEqualToString:meeting.meetingType] || [@"视频会议桥"isEqualToString:meeting.meetingType]){
        [self skipPhoneOrVideoMeetingDeailsViewController:meeting.meetingID];
    
    }else{
        [self skipMeetingDetailsViewController:meeting.meetingID];
    }
}

#pragma mark 跳转视频和电话会议详情
-(void)skipPhoneOrVideoMeetingDeailsViewController:(NSString *)meeingId{
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
    PhoneOrVideoMeetingDeailsViewController  *phoneOrVideoMeetingDeailsViewController = [storyboard instantiateViewControllerWithIdentifier:@"PhoneOrVideoMeetingDeailsViewController"];
    
    phoneOrVideoMeetingDeailsViewController.meetingId=meeingId;
    __weak typeof(self) wself = self;
    phoneOrVideoMeetingDeailsViewController.block=^{
        [wself refreshData];
    };
    
    if (_meeingType) {
        _meeingType=nil;
        [self.navigationController pushViewController:phoneOrVideoMeetingDeailsViewController animated:NO];
    }else{
        [self.navigationController pushViewController:phoneOrVideoMeetingDeailsViewController animated:NO];
    }
    
}

#pragma mark 跳转普通会议详情
-(void)skipMeetingDetailsViewController:(NSString *)meeingId{
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
    MeetingDetailsViewController  *meetingDetailsViewController = [storyboard instantiateViewControllerWithIdentifier:@"MeetingDetailsViewController"];
    __weak typeof(self) wself = self;
    meetingDetailsViewController.block=^{
        [wself refreshData];
    };
    
    meetingDetailsViewController.emeetingId=meeingId;
    if (_meeingType) {
        _meeingType=nil;
        [self.navigationController pushViewController:meetingDetailsViewController animated:NO];
    }else{
        [self.navigationController pushViewController:meetingDetailsViewController animated:YES];
    }
    
}
#pragma mark 退订 结束按钮绑定事件
-(void)unsubscribeAction:(UIButton *)sender
{
    NSLog(@"=====%ld",(long)sender.tag);
    if (self.currentTableView.tag==10000) {
         meet=_myReserveArr[sender.tag-1000000];
    }else if(self.currentTableView.tag==10001){
         meet=_myOrganizationalArr[sender.tag-1000000];
    }
     NSString *message=[NSString stringWithFormat:@"确定退订“%@”吗?",meet.meetingName];
    if ([@"退订" isEqualToString:sender.titleLabel.text]) {
        CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:message firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
        [customAlertView setLableAttributedColor:[UIColor colorWithHexString:@"01AEFF"] range:NSMakeRange(4, meet.meetingName.length+2)];
        [self.view addSubview:customAlertView];
    }else{
        message=[NSString stringWithFormat:@"确定结束“%@”吗?",meet.meetingName];
        
        CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:message firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
        [customAlertView setLableAttributedColor:[UIColor colorWithHexString:@"01AEFF"] range:NSMakeRange(4, meet.meetingName.length+2)];
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
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
#pragma mark 我预定的 和我组织的 我参加的 所有会议按钮绑定事件
- (IBAction)btnAction:(id)sender
{
    if (sender==_myReservebtn)
    {
        [UIView animateWithDuration:0.5f animations:^{
            _scrollView.contentOffset=CGPointMake(0, 0);
        }];
       
    }else if (sender==_myOrganizationalbtn)
    {
        [UIView animateWithDuration:0.5f animations:^{
           _scrollView.contentOffset=CGPointMake(selfViewWidth, 0);
        }];
    }else if(sender==_allMeetingBtn)
    {
         UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
        AllMeetingViewController  *allMeetingViewController = [storyboard instantiateViewControllerWithIdentifier:@"AllMeetingViewController"];
        __weak typeof(self) wself = self;
        allMeetingViewController.block=^{
            [wself refreshData];
        };
        [self.navigationController pushViewController:allMeetingViewController animated:YES];
    
    }else
    {
        [UIView animateWithDuration:0.5f animations:^{
            _scrollView.contentOffset=CGPointMake(selfViewWidth*2, 0);
        }];
    }
    
}

#pragma mark - 请求我的预定数据
-(void)requestmyReserveData{
    
    if (!getUserRelevantMeetingInfo) {
        getUserRelevantMeetingInfo=[[GetUserRelevantMeetingInfo alloc]init];
    }
    __weak typeof(self) wself = self;
    UITableView *tableView=[wself.scrollView viewWithTag:10000];
    [getUserRelevantMeetingInfo sendJSONRequestWithMeetingBelonging:@"1" PageRequestObject:wself.myReservePage  MeetingDate:nil Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:tableView];
        [tableView headerEndRefreshing];
        [tableView footerEndRefreshing];
        if (response.s) {
            if (wself.myReserveisDown)
            {
                [wself.myReserveArr removeAllObjects];
                wself.myReserveisDown=NO;
                
            }
            
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSArray *arr=response.d;
                
                if (arr.count!=0) {
                    wself.myReservePage.pno++;
                    wself.myReservePage.e=response.p.e;
                    wself.myReservePage.t=[response.p.t intValue];
                    for (NSDictionary *dic in response.d) {
                        MeetingInfo *meetingInfo=[[MeetingInfo alloc]initWithDictionary:dic];
                        [wself.myReserveArr addObject:meetingInfo];
                    }
                    
                }else{
                    if (wself.myReserveArr.count!=0) {
                        [MarkedWords showMarkedWordsWithMessage:@"没有更多数据" addToView:wself.view];
                    }
                }
            }
            if (wself.myReserveArr.count==0) {
                [tableView viewWithTag:11000].hidden=NO;
                [tableView removeHeader];
                [tableView removeFooter];
            }else{
                [tableView viewWithTag:11000].hidden=YES;
                [wself setupRefresh:tableView];
            }
            [tableView reloadData];
        }else{
             [ProgressView showHUDAddedTo:tableView ProgressText:@"数据加载失败,请稍候重试！"];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:tableView];
        [tableView headerEndRefreshing];
        [tableView footerEndRefreshing];
         [ProgressView showHUDAddedTo:tableView ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}

#pragma mark - 请求我组织的数据
-(void)requestMyOrganizationalData{
    
    if (!getUserRelevantMeetingInfo) {
        getUserRelevantMeetingInfo=[[GetUserRelevantMeetingInfo alloc]init];
    }
    __weak typeof(self) wself = self;
    UITableView *tableView=[wself.scrollView viewWithTag:10001];
    [getUserRelevantMeetingInfo sendJSONRequestWithMeetingBelonging:@"2" PageRequestObject:wself.myOrganizationalPage  MeetingDate:nil Success:^(ResponseObject *response) {
        
        [ProgressView hiddenCustomProgressViewAddTo:tableView];
        [tableView headerEndRefreshing];
        [tableView footerEndRefreshing];
        if (response.s) {
            if (wself.myOrganizationalisDown)
            {
                [wself.myOrganizationalArr removeAllObjects];
                wself.myOrganizationalisDown=NO;
                
            }
            
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSArray *arr=response.d;
                if (arr.count!=0) {
                    wself.myOrganizationalPage.pno++;
                    wself.myOrganizationalPage.e=response.p.e;
                    wself.myOrganizationalPage.t=[response.p.t intValue];
                    for (NSDictionary *dic in response.d) {
                        MeetingInfo *meetingInfo=[[MeetingInfo alloc]initWithDictionary:dic];
                        [wself.myOrganizationalArr addObject:meetingInfo];
                    }
                    
                }else{
                    if (wself.myOrganizationalArr.count!=0) {
                        [MarkedWords showMarkedWordsWithMessage:@"没有更多数据" addToView:wself.view];
                    }
                }
                if (wself.myOrganizationalArr.count==0) {
                    [tableView viewWithTag:11000].hidden=NO;
                    [tableView removeHeader];
                    [tableView removeFooter];
                }else{
                    [tableView viewWithTag:11000].hidden=YES;
                    [wself setupRefresh:tableView];
                }
               [tableView reloadData];
            }
        }else{
            [ProgressView showHUDAddedTo:tableView ProgressText:@"数据加载失败,请稍候重试！"];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:tableView];
        [tableView headerEndRefreshing];
        [tableView footerEndRefreshing];
        [ProgressView showHUDAddedTo:tableView ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}

#pragma mark - 请求我参加的数据
-(void)requestMyJoinData{
    
    if (!getUserRelevantMeetingInfo) {
        getUserRelevantMeetingInfo=[[GetUserRelevantMeetingInfo alloc]init];
    }
    __weak typeof(self) wself = self;
     UITableView *tableView=[wself.scrollView viewWithTag:10002];
    [getUserRelevantMeetingInfo sendJSONRequestWithMeetingBelonging:@"3" PageRequestObject:wself.myJoinPage  MeetingDate:nil Success:^(ResponseObject *response) {
       
        [ProgressView hiddenCustomProgressViewAddTo:tableView];
        [tableView headerEndRefreshing];
        [tableView footerEndRefreshing];
        if (response.s) {
            if (wself.myJoinisDown)
            {
                [wself.myJoinArr removeAllObjects];
                wself.myJoinisDown=NO;
                
            }
            
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSArray *arr=response.d;
                if (arr.count!=0) {
                    wself.myJoinPage.pno++;
                    wself.myJoinPage.e=response.p.e;
                    wself.myJoinPage.t=[response.p.t intValue];
                    for (NSDictionary *dic in response.d) {
                        MeetingInfo *meetingInfo=[[MeetingInfo alloc]initWithDictionary:dic];
                        [wself.myJoinArr addObject:meetingInfo];
                    }
                    
                }else{
                    if (wself.myJoinArr.count!=0) {
                        [MarkedWords showMarkedWordsWithMessage:@"没有更多数据" addToView:wself.view];
                    }
                }
                if (wself.myJoinArr.count==0) {
                    [tableView viewWithTag:11000].hidden=NO;
                    [tableView removeHeader];
                    [tableView removeFooter];
                }else{
                    [tableView viewWithTag:11000].hidden=YES;
                    [wself setupRefresh:tableView];
                }
               [tableView reloadData];
            }
        }else{
             [ProgressView showHUDAddedTo:tableView ProgressText:@"数据加载失败,请稍候重试！"];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:tableView];
        [tableView headerEndRefreshing];
        [tableView footerEndRefreshing];
         [ProgressView showHUDAddedTo:tableView ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}

#pragma mark - 退订请求
-(void)requestCancelMeetingRoom{
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!cancelMeetingRoom) {
        cancelMeetingRoom=[[CancelMeetingRoom alloc]init];
    }
    __weak typeof(self) wself = self;
    [cancelMeetingRoom sendJSONRequestWithEmeetingId:meet.meetingID RoomId:nil Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
             [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"退订成功!"];
            [wself refreshData];
        }else{
            [ProgressView showHUDAddedTo:wself.view ProgressText:response.m];
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
    [endMeetingRoom sendJSONRequestWithEmeetingId:meet.meetingID RoomId:nil Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"结束成功!"];
            [wself refreshData];
        }else{
            [ProgressView showHUDAddedTo:wself.view ProgressText:response.m];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}

#pragma mark 集成刷新控件
- (void)setupRefresh:(UITableView *)tableView
{
    // 1.下拉刷新(进入刷新状态就会调用self的headerRereshing)
    [tableView addHeaderWithTarget:self action:@selector(headerRereshing)];
    //    // 自动刷新(一进入程序就下拉刷新)
    //[tableView headerBeginRefreshing];
    
    // 2.上拉加载更多(进入刷新状态就会调用self的footerRereshing)
    [tableView addFooterWithTarget:self action:@selector(footerRereshing)];
    
    // 设置文字(也可以不设置,默认的文字在MJRefreshConst中修改)
    tableView.headerPullToRefreshText = @"下拉可以刷新了";
    tableView.headerReleaseToRefreshText = @"松开马上刷新了";
    tableView.headerRefreshingText = @"刷新中...";
    
    tableView.footerPullToRefreshText = @"上拉可以加载更多数据了";
    tableView.footerReleaseToRefreshText = @"松开马上加载更多数据了";
    tableView.footerRefreshingText = @"加载中...";
}

#pragma mark 开始进入刷新状态
- (void)headerRereshing
{
    // 1.刷新数据
    if (self.currentTableView.tag==10000) {
        _myReserveisDown=YES;
        _myReservePage=[[PageRequestObject alloc]init];
        [self requestmyReserveData];

    }else if(self.currentTableView.tag==10001){
        _myOrganizationalisDown=YES;
        _myOrganizationalPage=[[PageRequestObject alloc]init];
        [self requestMyOrganizationalData];
    }else{
        _myJoinisDown=YES;
        _myJoinPage=[[PageRequestObject alloc]init];
        [self requestMyJoinData];
    }
}

#pragma mark -加载下一页数据
- (void)footerRereshing
{
    if (self.currentTableView.tag==10000) {
        [self requestmyReserveData];
    }else if(self.currentTableView.tag==10001){
        [self requestMyOrganizationalData];
    }else{
        [self requestMyJoinData];
    }
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

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/
#pragma mark 打开左侧菜单
- (IBAction)openLeftMenu:(id)sender
{
    [self.mm_drawerController toggleDrawerSide:MMDrawerSideLeft animated:YES completion:nil];
}
@end
