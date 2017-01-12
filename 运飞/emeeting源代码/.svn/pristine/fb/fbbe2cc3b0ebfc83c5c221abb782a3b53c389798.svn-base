//
//  MeetingDetailsViewController.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/3.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingDetailsViewController.h"
#import "EMMSecurity.h"
//没选择中的按钮字体颜色
#define defaultBtnColor  [UIColor colorWithRed:153/255.0 green:153/255.0 blue:153/255.0 alpha:1]

//没选择中的按钮背景颜色
#define defaultBgColor  [UIColor colorWithRed:244/255.0 green:244/255.0 blue:244/255.0 alpha:1]

//按钮选中字体颜色
#define selectedColor  [UIColor colorWithRed:1/255.0 green:174/255.0 blue:255/255.0 alpha:1]

@interface MeetingDetailsViewController ()

@end

@implementation MeetingDetailsViewController{
    UITableView *boardroomDetailTableView;
    UITableView *meetingDetailTableView;
    NSArray *columnNameArr1;//会议列名数组
    NSArray *columnNameArr2;//会议室列名数组
    //MeetingPlaceTableViewCell *meetingPlace;
    GetMeetingInfo *getMeetingInfo;//获取会议信息接口对象
    NSArray *meetingDeailsArr;//会议信息
    NSArray *bookingsMeetingRoomArr;//会议室地址
    CancelMeetingRoom *cancelMeetingRoom;//退订接口对象
    EndMeetingRoom    *endMeetingRoom;//结束接口对象
    NSArray  *meetingRoomDetailArr;//会议室信息
    NSInteger sectionNum;
    GetMeetingJoinInfo *getMeetingJoinInfo;//获取会议的参会会议室或者人员接口对象
    GetMeetingProlongInfo *getMeetingProlongInfo;
    GetMeetingProlong *getMeetingProlong;
    MeetingOperation *meetingOperation;
    AttendanceOperation *attendanceOperation;
    float menuNum;
    NSMutableArray *roomDetailState;//0展开,1闭合
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self initialView];
    [self initialData];
}
#pragma mark 初始化数据
-(void)initialData{
    //meetingPlace=[[NSBundle mainBundle] loadNibNamed:@"MeetingPlaceTableViewCell" owner:self options:0][0];
    menuNum=2.0;
    columnNameArr1=[NSArray arrayWithObjects:@"会议主题",@"会议日期",@"会议时长",@"组织者",@"参会领导",@"会议级别",@"会议地点",@"签到表", nil];
    columnNameArr2=[NSArray arrayWithObjects:@"会议室规模",@"电视",@"电话",@"投影仪", nil];
    getMeetingInfo=[[GetMeetingInfo alloc]init];
    
    getMeetingJoinInfo=[[GetMeetingJoinInfo alloc]init];
    roomDetailState=[[NSMutableArray alloc]init];
    [self requestMeetingInfo];

}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark tableView代理

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    if (boardroomDetailTableView==tableView) {
        if (bookingsMeetingRoomArr.count>1) {
            
            return bookingsMeetingRoomArr.count;
        }
    }
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (boardroomDetailTableView==tableView) {
        if (bookingsMeetingRoomArr.count>1) {
            if([roomDetailState[section] isEqualToString:@"0"]){
                return columnNameArr2.count;
            }else{
                return 0;
            }
//            if (sectionNum==section) {
//                return columnNameArr2.count;
//            }else{
//                return 0;
//            }
        }else{
            return columnNameArr2.count;
        }
        
    }
    return meetingDeailsArr.count;
}
-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    if (boardroomDetailTableView==tableView) {
        if (bookingsMeetingRoomArr.count>1) {
            return 41;
        }
    }
    return 0;
}
- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    
    UIView *view=[[UIView alloc]initWithFrame:CGRectMake(0, 0, APPW, 30)];
    view.backgroundColor=[UIColor colorWithHexString:@"f9f9f9"];
    UILabel *titleLabel=[[UILabel alloc]initWithFrame:CGRectMake(20, 10, APPW-60, 20)];
    titleLabel.font=[UIFont systemFontOfSize:14.0];
    [titleLabel setTextColor:[UIColor colorWithHexString:@"01AEFF"]];
    titleLabel.text=[self returnMeetingRoom:bookingsMeetingRoomArr[section]].meetingRoomName;
    if (section!=0) {
        UIView *view1=[[UIView alloc]initWithFrame:CGRectMake(0, 0, APPW, 1)];
        view1.backgroundColor=[UIColor colorWithHexString:@"dddddd"];
        [view addSubview:view1];
    }
    [view addSubview:titleLabel];
    UIImageView *imageView=[[UIImageView alloc]initWithFrame:CGRectMake(APPW-40, 10, 24, 18)];
    NSString *imageString;
    if([roomDetailState[section] isEqualToString:@"0"]){
        imageString=@"ico_meetingroomdetail_up";
    }else{
        imageString=@"ico_meetingroomdetail_down";
    }
    
//    if (sectionNum==section) {
//        imageString=@"ico_meetingroomdetail_up";
//    }else{
//         imageString=@"ico_meetingroomdetail_down";
//    }
    imageView.image=[UIImage imageNamed:imageString];
    //imageView.backgroundColor=[UIColor colorWithHexString:@"01AEFF"];
    [view addSubview:imageView];
    UIButton *btn=[[UIButton alloc]initWithFrame:CGRectMake(0, 0, APPW, 30)];
    btn.tag=100000000+section;
    [btn addTarget:self action:@selector(singBtnAction:) forControlEvents:UIControlEventTouchUpInside];
    
    [view addSubview:btn];
    return view;
}
-(void)singBtnAction:(UIButton *)sender
{
    sectionNum=sender.tag-100000000;
    
    if([roomDetailState[sectionNum] isEqualToString:@"0"]){
        roomDetailState[sectionNum] =@"1";
    }else{
        roomDetailState[sectionNum] =@"0";
    }
    [boardroomDetailTableView reloadData];
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
   
    if (meetingDetailTableView==tableView) {
        MeetingDetailsTableViewCell *meetingDetailsTableViewCell;
        if (indexPath.row==6) {
            if (bookingsMeetingRoomArr.count>1) {
                MeetingPlaceTableViewCell *cell=[[NSBundle mainBundle] loadNibNamed:@"MeetingPlaceTableViewCell" owner:self options:0][0];
                meetingDetailsTableViewCell.cuttingLine.hidden=YES;
                meetingDetailsTableViewCell.rightArrow.hidden=YES;
                cell.bookingsMeetingRoomArr=bookingsMeetingRoomArr;
                cell.view=self.view;
                cell.meetingInfo=_meetingInfo;
                __weak typeof(self) wself = self;
                cell.refreshBlock=^(){
                    //刷新数据
                    [wself  requestMeetingInfo];
                };
                tableView.rowHeight=48*cell.bookingsMeetingRoomArr.count;
                return cell;

            }else{
                meetingDetailsTableViewCell=[tableView dequeueReusableCellWithIdentifier:@"MeetingDetailsTableViewCell"];
                meetingDetailsTableViewCell.contentName.text=columnNameArr1[indexPath.row];
                meetingDetailsTableViewCell.cuttingLine.hidden=YES;
                meetingDetailsTableViewCell.rightArrow.hidden=YES;
                meetingDetailsTableViewCell.contents.text=[self returnMeetingRoom:[bookingsMeetingRoomArr firstObject]].meetingRoomName;
                tableView.rowHeight=48;
            }
            
        }else{
            meetingDetailsTableViewCell=[tableView dequeueReusableCellWithIdentifier:@"MeetingDetailsTableViewCell"];
            meetingDetailsTableViewCell.contentName.text=columnNameArr1[indexPath.row];
            if (meetingDeailsArr>0) {
                meetingDetailsTableViewCell.contents.text=meetingDeailsArr[indexPath.row];
            }
            if (indexPath.row== columnNameArr1.count-1) {
                meetingDetailsTableViewCell.cuttingLine.hidden=NO;
                meetingDetailsTableViewCell.rightArrow.hidden=NO;
            }else{
                meetingDetailsTableViewCell.cuttingLine.hidden=YES;
                meetingDetailsTableViewCell.rightArrow.hidden=YES;
            }
            tableView.rowHeight=52;
            
        }
        if(indexPath.row==columnNameArr1.count-1){
            meetingDetailsTableViewCell.selectionStyle=UITableViewCellSelectionStyleDefault;
        }else{
            meetingDetailsTableViewCell.selectionStyle=UITableViewCellSelectionStyleNone;
        }
        return meetingDetailsTableViewCell;
    }else{
      MeetingDetailsTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"MeetingDetailsTableViewCell"];
        cell.contentName.text=columnNameArr2[indexPath.row];
        
        cell.contents.text=meetingRoomDetailArr[indexPath.section][indexPath.row];
        tableView.rowHeight=48;
        return cell;
    }
   
    
}

#pragma mark cell的点击事件
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if ( meetingDetailTableView==tableView){
        if(indexPath.row==columnNameArr1.count-1) {
            UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
            
            cell.selected = NO;
            [self  gotoScanCodeList];
        }
    }
}
#pragma mark 跳转签到表
-(void)gotoScanCodeList{
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"ScanCode" bundle:nil];
    ScanCodePersonnelViewController  *scanCodePersonnelViewController = [storyboard instantiateViewControllerWithIdentifier:@"ScanCodePersonnelViewController"];
    scanCodePersonnelViewController.meetingInfo=_meetingInfo;
    [self.navigationController pushViewController:scanCodePersonnelViewController animated:YES];
}

#pragma mark scrollView代理
-(void)scrollViewDidScroll:(UIScrollView *)scrollView{
    if (_scrollView.tag==10100) {
       
        if (_scrollView.contentOffset.x==0)
        {
            [self selectedMeetingDetail];
            
        }else if(_scrollView.contentOffset.x==APPW)
        {
            [self selectedBoardroomDetail];
        }else if(_scrollView.contentOffset.x==APPW*2)
        {
            [self selectedMeetingDominate];
        }else{
            boardroomDetailTableView.scrollEnabled = NO;
            meetingDetailTableView.scrollEnabled = NO;
            _meetingDominateView.tableview.scrollEnabled = NO;
        }
    }

}
- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    if (_scrollView.tag==10100) {
        boardroomDetailTableView.scrollEnabled = YES;
        meetingDetailTableView.scrollEnabled = YES;
        _meetingDominateView.tableview.scrollEnabled = YES;
    }
}
#pragma mark 切换到会议详情
-(void)selectedMeetingDetail{
    
    [UIView animateWithDuration:0.5f animations:^{
        _underlinewidth.constant=0;
    }];
    [_boardroomDetailBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _boardroomDetailBtn.backgroundColor=defaultBgColor;
    [_meetingDominateBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _meetingDominateBtn.backgroundColor=defaultBgColor;
    [_meetingDetailBtn setTitleColor:selectedColor forState:UIControlStateNormal];
    _meetingDetailBtn.backgroundColor=[UIColor whiteColor];
    
}
#pragma mark 切换到会议室详情
-(void)selectedBoardroomDetail{
    [UIView animateWithDuration:0.5f animations:^{
         _underlinewidth.constant=APPW/menuNum;
    }];
   
    [_meetingDetailBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _meetingDetailBtn.backgroundColor=defaultBgColor;
    
    [_boardroomDetailBtn setTitleColor:selectedColor forState:UIControlStateNormal];
    _boardroomDetailBtn.backgroundColor=[UIColor whiteColor];
    [_meetingDominateBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _meetingDominateBtn.backgroundColor=defaultBgColor;
}


#pragma mark 切换到会议控制
-(void)selectedMeetingDominate{
    [UIView animateWithDuration:0.5f animations:^{
        _underlinewidth.constant=APPW*2/menuNum;
    }];
    
    [_meetingDetailBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _meetingDetailBtn.backgroundColor=defaultBgColor;
    
    [_boardroomDetailBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _boardroomDetailBtn.backgroundColor=defaultBgColor;
    [_meetingDominateBtn setTitleColor:selectedColor forState:UIControlStateNormal];
    _meetingDominateBtn.backgroundColor=[UIColor whiteColor];
}

#pragma mark 初始化视图
-(void)initialView{
    
    _scrollView.contentSize=CGSizeMake(APPW*2,0);
    _meetingDetailBtnWidth.constant=APPW/2.0;
    _scrollView.frame=CGRectMake(116, 0, APPW, APPH-116);
    _scrollView.tag=10100;
    _scrollView.delegate=self;
    _scrollView.scrollEnabled=YES;
    _scrollView.pagingEnabled=YES;
    _scrollView.bounces=NO; 
    _scrollView.showsVerticalScrollIndicator = NO;
    _scrollView.showsHorizontalScrollIndicator = NO;
    boardroomDetailTableView=[[UITableView alloc]initWithFrame:CGRectMake(APPW,0, APPW,APPH-116)];
    boardroomDetailTableView.dataSource=self;
    boardroomDetailTableView.delegate=self;
    boardroomDetailTableView.allowsSelection = NO;
    boardroomDetailTableView.separatorStyle =UITableViewCellSeparatorStyleNone;
    boardroomDetailTableView.bounces=NO;
    [boardroomDetailTableView registerNib:[UINib nibWithNibName:@"MeetingDetailsTableViewCell" bundle:nil] forCellReuseIdentifier:@"MeetingDetailsTableViewCell"];
    [_scrollView addSubview:boardroomDetailTableView];
    
    meetingDetailTableView=[[UITableView alloc]initWithFrame:CGRectMake(0,0, APPW,APPH-116)];
    meetingDetailTableView.dataSource=self;
    meetingDetailTableView.delegate=self;
    //meetingDetailTableView.bounces=NO;
    //meetingDetailTableView.allowsSelection = NO;
    meetingDetailTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [meetingDetailTableView registerNib:[UINib nibWithNibName:@"MeetingDetailsTableViewCell" bundle:nil] forCellReuseIdentifier:@"MeetingDetailsTableViewCell"];
    [_scrollView addSubview:meetingDetailTableView];
    
    _meetingDominateView=[[[NSBundle mainBundle]loadNibNamed:@"MeetingDominateView" owner:nil options:nil]firstObject];
    _meetingDominateView.frame=CGRectMake(APPW*2,0, APPW,APPH-116);
    __weak typeof(self) wself = self;
    _meetingDominateView.requestMeetingProlongBlock=^{
        [wself requestMeetingProlongInfo];
    };
    
    _meetingDominateView.requestMeetingProlongBlock=^{
        [wself requestMeetingProlong];
    };
    
    _meetingDominateView.requestMeetingProlongInfoBlock=^{
        [wself requestMeetingProlongInfo];
    };
    _meetingDominateView.skipMeetingDominateBlock=^{
        [wself  gotoMeetingDominate];
    };
    
    _meetingDominateView.requestMeetingOperationBlock=^(NSString *type,MeetingJoinInfo *meetingJoinInfo){
        [wself requestMeetingOperation:type MeetingJoinInfo:meetingJoinInfo];
        
    };


    [self.scrollView addSubview:_meetingDominateView];
    
    
    _endBtn=[[UIButton alloc]initWithFrame:CGRectMake(0, _scrollView.frame.size.height-48, APPW, 48)];
    [_endBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    _endBtn.hidden=YES;
    [_endBtn addTarget:self action:@selector(unsubscribeAction:) forControlEvents:UIControlEventTouchUpInside];
    _endBtn.backgroundColor=[UIColor colorWithHexString:@"ff9600"];
    [_scrollView addSubview:_endBtn];
}

#pragma mark 跳转会议控制
-(void)gotoMeetingDominate{
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
    MeetingDominateViewController  *meetingDominateViewController = [storyboard instantiateViewControllerWithIdentifier:@"MeetingDominateViewController"];
    meetingDominateViewController.meetingId=_meetingInfo.meetingID;
    meetingDominateViewController.meetingJoinInfoList=_meetingJoinInfoList;
    __weak typeof(self) wself = self;
    meetingDominateViewController.refreshMeetingJoinInfoBlock=^{
        [wself requestMeetingJoinInfo];
    };
    [self.navigationController pushViewController:meetingDominateViewController animated:YES];
}

#pragma mark 会议详情和会议室详情切换
- (IBAction)btnAction:(id)sender {
    
    if (_meetingDetailBtn==sender) {
        //会议详情
        [UIView animateWithDuration:0.5f animations:^{
            _scrollView.contentOffset=CGPointMake(0, 0);
        }];

    }else if (_boardroomDetailBtn==sender){
        //会议室详情
        [UIView animateWithDuration:0.5f animations:^{
            _scrollView.contentOffset=CGPointMake(APPW, 0);
        }];

    }else if (_meetingDominateBtn==sender){
        //会议室详情
        [UIView animateWithDuration:0.5f animations:^{
            _scrollView.contentOffset=CGPointMake(APPW*2, 0);
        }];
        
    }
    
}

-(void)hiddenCancelBtn{
    _endBtn.hidden=YES;
    meetingDetailTableView.frame=CGRectMake(0,0, APPW,APPH-116);
    //_scrollViewTop.constant=0;
    //_cancelBtn.frame=CGRectMake(0, APPH-116, APPW, 48);
}
#pragma mark 请求会议信息
-(void)requestMeetingInfo{
    __weak typeof(self) wself = self;
    [ProgressView showCustomProgressViewAddTo:self.view];
    [getMeetingInfo sendJSONRequestWithEmeetingId:wself.emeetingId Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            if ([response.d isKindOfClass:[NSDictionary class]]) {
                wself.meetingInfo=[[MeetingInfo alloc]initWithMeetingDictionary:response.d];
                [wself loadMeetingData];
            }
        }else{
            [ProgressView showHUDAddedTo:wself.view ProgressText:response.m];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:self.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}

#pragma mark 处理要显示的数据
-(void)loadMeetingData{
    if ([@"0" isEqualToString:_meetingInfo.operatingState]) {
        _endBtn.hidden=YES;
        meetingDetailTableView.frame=CGRectMake(0,0, APPW,APPH-116);
        NSDate *beginDate= [NSDate dateWithString:_meetingInfo.beginDate format:[NSDate ymdHmsFormat]];
        
        NSDate *endDate= [NSDate dateWithString:_meetingInfo.endDate format:[NSDate ymdHmsFormat]];
        //NSLog(@"%@==%@===%@",beginDate,[NSDate getServerDate],[[NSDate getServerDate] earlierDate:endDate]);
        
        if([beginDate isEqualToDate:[[NSDate getServerDate] earlierDate:beginDate]] && [endDate isEqualToDate:[[NSDate getServerDate] laterDate:endDate]]){
            if ([@"N" isEqualToString:_meetingInfo.isSingnIn]) {
                _endBtn.hidden=NO;
                meetingDetailTableView.frame=CGRectMake(0,0, APPW,APPH-116-48);
                [_endBtn setTitle:@"签到" forState:UIControlStateNormal];
            }
        }

    }else{
        _endBtn.hidden=NO;
        if ([@"1" isEqualToString:_meetingInfo.operatingState]) {
            [_endBtn setTitle:@"退  订" forState:UIControlStateNormal];
        }else{
            [_endBtn setTitle:@"结  束" forState:UIControlStateNormal];
            if ([@"Y" isEqualToString:_meetingInfo.isProlong]) {
                
            }
        }
        meetingDetailTableView.frame=CGRectMake(0,0, APPW,APPH-116-48);
//        if (bookingsMeetingRoomArr.count>1) {
//            //_scrollBottom.constant=0;
//            meetingDetailTableView.frame=CGRectMake(0,0, APPW,APPH-116);
//        }
        
    }
    [self isShowMeetingDominateView:_meetingInfo];
    if([_meetingInfo.organizePeopleNo isEqualToString:[EMMSecurity share].userId]){
         meetingDeailsArr=[[NSArray alloc]initWithObjects:_meetingInfo.meetingName,_meetingInfo.showMeetingTime,[_meetingInfo.meetingTime stringByAppendingString:@"小时"],_meetingInfo.organizePeopleChineseName ,_meetingInfo.participateMeetingLeaderLevel,_meetingInfo.meetingLevel,@"",@"", nil];
    }else{
         meetingDeailsArr=[[NSArray alloc]initWithObjects:_meetingInfo.meetingName,_meetingInfo.showMeetingTime,[_meetingInfo.meetingTime stringByAppendingString:@"小时"],_meetingInfo.organizePeopleChineseName ,_meetingInfo.participateMeetingLeaderLevel,_meetingInfo.meetingLevel,@"", nil];
    }
   
    bookingsMeetingRoomArr = [_meetingInfo.bookingsMeetingRoomIds componentsSeparatedByString:@","];
    NSMutableArray *roomArr=[[NSMutableArray alloc]init];
    for (NSString *roomArrId in bookingsMeetingRoomArr) {
        MeetingRoomInfo *mRoom=[self returnMeetingRoom:roomArrId];
        NSArray *meetingRoom=[[NSArray alloc]initWithObjects:
                              [NSString stringWithFormat:@"%@人",mRoom.meetingRoomScale]?:@"",
                              [mRoom returnTelevisionString:mRoom.televisionState]?:@"",
                              [mRoom returnPhoneString:mRoom.phoneState]?:@"",
                              [mRoom returnProjectorString:mRoom.projectorState]?:@"",nil];
        [roomArr addObject:meetingRoom];
        
        [roomDetailState addObject:@"1"];
    }
    meetingRoomDetailArr=roomArr;
    [boardroomDetailTableView reloadData];
    [meetingDetailTableView reloadData];
    [_meetingDominateView.tableview reloadData];
}


-(void)isShowMeetingDominateView:(MeetingInfo *)meetInfo{
    
    if ([@"Y" isEqualToString:meetInfo.isDominate]) {
        menuNum=3.0;
        [self cycleRequestMeetingJoinInfo];
        _scrollView.contentSize=CGSizeMake(APPW*menuNum,0);
        _meetingDetailBtnWidth.constant=APPW/menuNum;
        if ( [@"Y" isEqualToString:meetInfo.isProlong]) {
            _meetingDominateView.dominateBtn.hidden=NO;
        }else{
            [self hiddenMeetingDominateBtn];
        }
    }else{
        
    }
}

-(void)hiddenMeetingDominateBtn{
    _meetingDominateView.dominateBtn.hidden=YES;
    _meetingDominateView.tableViewBottom.constant=0;
}
#pragma mark - 退订请求
-(void)requestCancelMeetingRoom{
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!cancelMeetingRoom) {
        cancelMeetingRoom=[[CancelMeetingRoom alloc]init];
    }
    __weak typeof(self) wself = self;
    [cancelMeetingRoom sendJSONRequestWithEmeetingId:wself.emeetingId RoomId:nil Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"退订成功！"];
            
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
    [endMeetingRoom sendJSONRequestWithEmeetingId:wself.emeetingId RoomId:nil Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:self.view];
        if (response.s) {
           [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"结束成功！"];
            [wself performSelector:@selector(back:) withObject:nil afterDelay:1.5];
            
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:self.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
    
}

#pragma mark 退订
- (void)unsubscribeAction:(id)sender {
    
    if ([@"1" isEqualToString:_meetingInfo.operatingState]) {
        
        CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:[NSString stringWithFormat:@"确定退订“%@”吗?",_meetingInfo.meetingName] firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
        
        [customAlertView setLableAttributedColor:[UIColor colorWithHexString:@"01AEFF"] range:NSMakeRange(4, _meetingInfo.meetingName.length+2)];
        [self.view addSubview:customAlertView];
    }else if([@"2" isEqualToString:_meetingInfo.operatingState]){
        CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:[NSString stringWithFormat:@"确定要结束“%@”吗?",_meetingInfo.meetingName] firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
        [customAlertView setLableAttributedColor:[UIColor colorWithHexString:@"01AEFF"] range:NSMakeRange(5, _meetingInfo.meetingName.length+2)];
        customAlertView.tag=10000000;
        [self.view addSubview:customAlertView];
    }else{
        CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:[NSString stringWithFormat:@"确定要签到“%@”吗?",_meetingInfo.meetingName] firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
        [customAlertView setLableAttributedColor:[UIColor colorWithHexString:@"01AEFF"] range:NSMakeRange(5, _meetingInfo.meetingName.length+2)];
        customAlertView.tag=11000000;
        [self.view addSubview:customAlertView];
    }
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

#pragma mark —查询会议室详情
-(MeetingRoomInfo *)returnMeetingRoom:(NSString *)rId{
   
    if (![@""isEqualToString:rId] && rId!=nil ) {
        MeetingRoomInfo *meetingRoomInfo= [[[MeetingInfoManager shareInstance] selectSysMeetingRoomInfoWithRoomId:rId] firstObject];
         return meetingRoomInfo;
    }
    return nil;
}
#pragma mark - 获取会议时间延长信息接口
-(void)requestMeetingProlongInfo{
    
    if (!getMeetingProlongInfo) {
        getMeetingProlongInfo=[[GetMeetingProlongInfo alloc]init];
        [ProgressView showCustomProgressViewAddTo:self.view];
    }
    __weak typeof(self) wself = self;
    [getMeetingProlongInfo sendJSONRequestWithMeetingId:_meetingInfo.meetingID Success:^(ResponseObject *response){
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            if([response.d isKindOfClass:[NSArray class]]){
                
                //if (response.d>0) {
                    NSMutableArray *arr=[[NSMutableArray alloc]init];
                
                    for (NSDictionary *dic in response.d) {
                        
                        MeetingProlong *meetingProlong=[[MeetingProlong alloc]initWithDictionary:dic EndTime:wself.meetingInfo.endDate];
                        
                        if ([@"Y" isEqualToString: meetingProlong.isProlong]) {
                            if ([dic[@"PLT"] intValue]<30) {
                                [arr addObject:meetingProlong];
                            }
                        }else{
                            [arr addObject:meetingProlong];
                        }

                        
                    }
                //if (arr.count>0) {
                    [wself.meetingDominateView dominate:arr];
               // }else{
                //    [self requestMeetingProlong];
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
    if (!getMeetingProlong) {
        getMeetingProlong=[[GetMeetingProlong alloc]init];
    }
    __weak typeof(self) wself = self;
    [getMeetingProlong sendJSONRequestWithMeetingId:wself.meetingInfo.meetingID Success:^(ResponseObject *response){
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            NSLog(@"延长成功！");
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"延长成功！"];
            [wself hiddenMeetingDominateBtn];
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    }failure:^(NSError *error){
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
    [meetingOperation sendJSONRequestWithMeetingId:wself.meetingInfo.meetingID TermId:meetingJoinInfo.meetingRoomID Number:meetingJoinInfo.terminalNumber OperationType:operationType Type:meetingJoinInfo.terminalType Success:^(ResponseObject *response) {
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
    [getMeetingJoinInfo sendJSONRequestWithMeetingId: _emeetingId  Success:^(ResponseObject *response) {
        [wself performSelector:@selector(cycleRequestMeetingJoinInfo) withObject:nil afterDelay:10.0f];
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSMutableArray *meetingJoinArr=[[NSMutableArray alloc]init];
                for (NSDictionary *dic in response.d) {
                    MeetingJoinInfo *meetingJoinInfo=[[MeetingJoinInfo alloc]initWithDictionary:dic];
                    [meetingJoinArr addObject:meetingJoinInfo];
                }
                wself.meetingDominateView.meetingJoinInfoList=meetingJoinArr;
                wself.meetingJoinInfoList=meetingJoinArr;
                [wself.meetingDominateView.tableview reloadData];
            }
        }else{
            //[ProgressView showHUDAddedTo:wself.view ProgressText:response.m];
        }
    } failure:^(NSError *error) {
        [wself performSelector:@selector(cycleRequestMeetingJoinInfo) withObject:nil afterDelay:10.0f];
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
}

#pragma mark -获取会议的参会会议室或者人员
-(void)requestMeetingJoinInfo{
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!getMeetingJoinInfo) {
        getMeetingJoinInfo=[[GetMeetingJoinInfo alloc]init];
    }
    __weak typeof(self) wself = self;
    [getMeetingJoinInfo sendJSONRequestWithMeetingId: _emeetingId  Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSMutableArray *meetingJoinArr=[[NSMutableArray alloc]init];
                for (NSDictionary *dic in response.d) {
                    MeetingJoinInfo *meetingJoinInfo=[[MeetingJoinInfo alloc]initWithDictionary:dic];
                    [meetingJoinArr addObject:meetingJoinInfo];
                }
                wself.meetingDominateView.meetingJoinInfoList=meetingJoinArr;
                wself.meetingJoinInfoList=meetingJoinArr;
                [wself.meetingDominateView.tableview reloadData];
            }
        }else{
            [ProgressView showHUDAddedTo:wself.view ProgressText:response.m];
        }
    } failure:^(NSError *error) {
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
    [attendanceOperation sendJSONRequestWithMeetingId:_meetingInfo.meetingID CodeInfo:@"" Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"签到成功！"];
            [wself hiddenCancelBtn];
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}
#pragma mark 返回
- (IBAction)back:(id)sender {
    self.block();
    [self.navigationController popViewControllerAnimated:YES];
    
}
@end
