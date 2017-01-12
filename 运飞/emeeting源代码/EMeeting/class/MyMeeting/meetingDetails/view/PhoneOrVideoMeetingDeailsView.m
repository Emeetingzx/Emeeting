//
//  PhoneOrVideoMeetingDeailsView.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/4/15.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "PhoneOrVideoMeetingDeailsView.h"
#import "Tools.h"
#import "MeetingDetailsTableViewCell.h"
#import "UIColor+LJFColor.h"
#import "EMMSecurity.h"
#import "NSDate+LJFDate.h"
@implementation PhoneOrVideoMeetingDeailsView{
    NSArray *columnNameArr1;//第一个分组数据
    NSArray *columnNameArr2;//第二个分组数据
    NSArray *columnNameArr3;//第三个分组数据
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.

*/
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
-(void)initialView{
    self.hidden=NO;
    _scrollView.contentSize=CGSizeMake(APPW,0);
    _scrollView.tag=10100;
    _scrollView.delegate=self;
    _scrollView.scrollEnabled=YES;
    _scrollView.pagingEnabled=YES;
    _scrollView.showsVerticalScrollIndicator = NO;
    _scrollView.showsHorizontalScrollIndicator = NO;
    _scrollView.bounces=NO;
    _meetingTableView=[[UITableView alloc]initWithFrame:CGRectMake(0,0, APPW,APPH-116)];
    _meetingTableView.dataSource=self;
    _meetingTableView.delegate=self;
    //_meetingTableView.allowsSelection = NO;
    //_meetingTableView.backgroundColor=[UIColor redColor];
    _meetingTableView.bounces=NO;
    _meetingTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [_meetingTableView registerNib:[UINib nibWithNibName:@"MeetingDetailsTableViewCell" bundle:nil] forCellReuseIdentifier:@"MeetingDetailsTableViewCell"];
    [_scrollView addSubview:_meetingTableView];
    

    _meetingDominateView=[[[NSBundle mainBundle]loadNibNamed:@"MeetingDominateView" owner:nil options:nil]firstObject];
    _meetingDominateView.requestMeetingProlongBlock=_requestMeetingProlongBlock;
    _meetingDominateView.requestMeetingOperationBlock=_requestMeetingOperationBlock;
    _meetingDominateView.requestMeetingProlongInfoBlock=_requestMeetingProlongInfoBlock;
     _meetingDominateView.skipMeetingDominateBlock=_skipMeetingDominateBlock;
    //_meetingDominateView.requestMeetingJoinInfoBlock=_requestMeetingJoinInfoBlock;
    _meetingDominateView.frame=CGRectMake(APPW,0, APPW,APPH-68);
    [_scrollView addSubview:_meetingDominateView];
    
    
    _cancelBtn=[[UIButton alloc]initWithFrame:CGRectMake(0,APPH-116, APPW, 48)];
    [_cancelBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    _cancelBtn.hidden=YES;
    [_cancelBtn addTarget:self action:@selector(unsubscribeAction) forControlEvents:UIControlEventTouchUpInside];
    _cancelBtn.backgroundColor=[UIColor colorWithHexString:@"ff9600"];
    [_scrollView addSubview:_cancelBtn];
   
    
}

-(void)unsubscribeAction{
    NSLog(@"2112");
    self.unsubscribeBlock();
}
#pragma mark 初始化数据

-(void)initialData:(MeetingInfo *)meetingInfo{
    columnNameArr1=[NSArray arrayWithObjects:@"会议主题",@"会议类型",@"参会领导",@"会议级别",@"起止时间", nil];
    
    if ([@"视频会议桥" isEqualToString:meetingInfo.meetingType]) {
        columnNameArr2=[NSArray arrayWithObjects:@"接入号码", nil];
    }else if ([@"电话会议桥" isEqualToString:meetingInfo.meetingType]){
        columnNameArr2=[NSArray arrayWithObjects:@"接入号码",@"会议编号",@"会议密码" ,nil];
    }
    
    columnNameArr3=[NSArray arrayWithObjects:@"组织者", @"签到表",nil];
    [_meetingTableView registerNib:[UINib nibWithNibName:@"MeetingDetailsTableViewCell" bundle:nil] forCellReuseIdentifier:@"MeetingDetailsTableViewCell"];
    _meetingDeails=[[NSMutableArray alloc]init];
}


#pragma mark 处理显示数据
-(void)loadMeetingData:(MeetingInfo *)meetInfo{
    [self initialView];
    [self initialData:meetInfo];
    NSArray *meetingDeailsArr1=[[NSArray alloc]initWithObjects:meetInfo.meetingName,meetInfo.meetingType,meetInfo.participateMeetingLeaderLevel,meetInfo.meetingLevel,meetInfo.showMeetingTime, nil];
    NSArray *meetingDeailsArr2;
    if ([@"0" isEqualToString:meetInfo.operatingState]) {
        [self hiddenCancelBtn];

        NSDate *beginDate= [NSDate dateWithString:meetInfo.beginDate format:[NSDate ymdHmsFormat]];
        //NSLog(@"%@",beginDate);
        //NSLog(@"%@",[NSDate getServerDate]);
        NSDate *endDate= [NSDate dateWithString:meetInfo.endDate format:[NSDate ymdHmsFormat]];
        
       // NSLog(@"%@==%@===%@",endDate,[NSDate getServerDate],[[NSDate getServerDate] laterDate:endDate]);
        
        if([beginDate isEqualToDate:[[NSDate getServerDate] earlierDate:beginDate]] && [endDate isEqualToDate:[[NSDate getServerDate] laterDate:endDate]]){
            if ([@"N" isEqualToString:meetInfo.isSingnIn]) {
                _cancelBtn.hidden=NO;
                _meetingTableView.frame=CGRectMake(0,0, APPW,APPH-116);
                [_cancelBtn setTitle:@"签到" forState:UIControlStateNormal];
            }
        }
    }else{
        if ([@"1" isEqualToString:meetInfo.operatingState]) {
            [_cancelBtn setTitle:@"退订" forState:UIControlStateNormal];
        }else{
            [_cancelBtn setTitle:@"结束" forState:UIControlStateNormal];
            _cancelBtn.hidden=NO;
        }
        
            _cancelBtn.hidden=NO;
    }
    
    
   [self isShowMeetingDominateView:meetInfo];
    if ([@"视频会议桥" isEqualToString:meetInfo.meetingType]) {
        
        if (![@"" isEqualToString:meetInfo.meetingPassword] && meetInfo.meetingPassword!=nil) {
            
            meetingDeailsArr2=[[NSArray alloc]initWithObjects:[NSString stringWithFormat:@"%@*%@",meetInfo.meetingNumber,meetInfo.meetingPassword],nil];
        }else{
            meetingDeailsArr2=[[NSArray alloc]initWithObjects:meetInfo.meetingNumber,nil];
        }
        
    }else{
        meetingDeailsArr2=[[NSArray alloc]initWithObjects:meetInfo.accessPhone,meetInfo.meetingNumber,meetInfo.meetingPassword, nil];
    }
    NSArray *meetingDeailsArr3;
    if([meetInfo.organizePeopleNo isEqualToString:[EMMSecurity share].userId]){
        meetingDeailsArr3=[[NSArray alloc]initWithObjects:meetInfo.organizePeopleChineseName,@"", nil];
    }else{
        meetingDeailsArr3=[[NSArray alloc]initWithObjects:meetInfo.organizePeopleChineseName, nil];
    }
   
    [_meetingDeails addObject:meetingDeailsArr1];
    [_meetingDeails addObject:meetingDeailsArr2];
    [_meetingDeails addObject:meetingDeailsArr3];
    [_meetingTableView reloadData];
    
}


-(void)hiddenCancelBtn{
    _cancelBtn.hidden=YES;
    _meetingTableView.frame=CGRectMake(0,0, APPW,APPH-68);
    //_scrollViewTop.constant=0;
    //_cancelBtn.frame=CGRectMake(0, APPH-116, APPW, 48);
}
-(void)isShowMeetingDominateView:(MeetingInfo *)meetInfo{
    
    if ([@"Y" isEqualToString:meetInfo.isDominate]) {
        
        _scrollView.contentSize=CGSizeMake(APPW*2,0);
        self.requestMeetingJoinInfoBlock();
        _scrollViewTop.constant=48;
        _meetingTableView.frame=CGRectMake(0, 0, APPW, APPH-116-48);
         _cancelBtn.frame=CGRectMake(0, APPH-116-48, APPW, 48);
        _meetingDetailsBtn.hidden=NO;
        _meetingDominateBtn.hidden=NO;
        if ([@"Y" isEqualToString:meetInfo.isProlong]) {
            _meetingDominateView.dominateBtn.hidden=NO;
        }else{
            
            [self hiddenMeetingDominateBtn];
        }
    }else{
        //_scrollViewTop.constant=0;
        //_meetingTableView.frame=CGRectMake(0, 0, APPW, APPH-116);
        //_cancelBtn.frame=CGRectMake(0, APPH-116, APPW, 48);
    }
}


-(void)hiddenMeetingDominateBtn{
    _meetingDominateView.dominateBtn.hidden=YES;
    _meetingDominateView.tableViewBottom.constant=0;
}
#pragma mark scrollView代理
-(void)scrollViewDidScroll:(UIScrollView *)scrollView{
    if (_scrollView.tag==10100) {
        if (_scrollView.contentOffset.x==0)
        {
            [self selectedMeetingDetail];
            
        }else if(_scrollView.contentOffset.x==APPW)
        {
            [self selectedDominate];
        }else{
            _meetingTableView.scrollEnabled = NO;
            _meetingDominateView.tableview.scrollEnabled = NO;
        }
    }
    
}

- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    if (_scrollView.tag==10100) {
        _meetingTableView.scrollEnabled = YES;
        _meetingDominateView.tableview.scrollEnabled = YES;
    }
}

#pragma mark 切换到会议详情
-(void)selectedMeetingDetail{
    
    [UIView animateWithDuration:0.5f animations:^{
        _underlinewidth.constant=0;
    }];
    [_meetingDetailsBtn setTitleColor:[UIColor colorWithHexString:@"01aeff"] forState:UIControlStateNormal];
    _meetingDetailsBtn.backgroundColor=[UIColor clearColor];
    
    [_meetingDominateBtn setTitleColor:[UIColor colorWithHexString:@"999999"] forState:UIControlStateNormal];
    _meetingDominateBtn.backgroundColor=[UIColor colorWithHexString:@"f4f4f4"];
}
#pragma mark 切换到会议室详情
-(void)selectedDominate{
    [UIView animateWithDuration:0.5f animations:^{
        _underlinewidth.constant=APPW/2;
    }];
    
    [_meetingDominateBtn setTitleColor:[UIColor colorWithHexString:@"01aeff"] forState:UIControlStateNormal];
    _meetingDominateBtn.backgroundColor=[UIColor clearColor];
    
    [_meetingDetailsBtn setTitleColor:[UIColor colorWithHexString:@"999999"] forState:UIControlStateNormal];
    _meetingDetailsBtn.backgroundColor=[UIColor colorWithHexString:@"f4f4f4"];

}

#pragma mark tableView代理
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 3;
}
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    
    if (section==2) {
        return 0;
    }else{
        return 10;
    }
    
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (section==0) {
        NSArray *arr1=_meetingDeails[section];
        return arr1.count;
    }else if(section==1)
    {
        NSArray *arr2=_meetingDeails[section];
        return arr2.count;
    }else{
        NSArray *arr3=_meetingDeails[section];
        return arr3.count;
    }
}


#pragma mark 展示信息
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    MeetingDetailsTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"MeetingDetailsTableViewCell"];
    
    tableView.rowHeight=48;
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.rightArrow.hidden=YES;
    cell.cuttingLine.hidden=YES;
    if (indexPath.section==0) {
        cell.contentName.text=columnNameArr1[indexPath.row];
        NSLog(@"columnNameArr1=%ld",(long)indexPath.row);
        if (_meetingDeails.count>0) {
            cell.contents.text=_meetingDeails[0][indexPath.row];
        }
    }else if(indexPath.section==1){
        cell.contentName.text=columnNameArr2[indexPath.row];
         NSLog(@"columnNameArr2=%ld",(long)indexPath.row);
        if (_meetingDeails.count>0) {
            cell.contents.text=_meetingDeails[1][indexPath.row];
        }
    }else{
        cell.contentName.text=columnNameArr3[indexPath.row];
         NSLog(@"columnNameArr3=%ld",(long)indexPath.row);
        if (_meetingDeails.count>0) {
            cell.contents.text=_meetingDeails[2][indexPath.row];
        }
        if (indexPath.row==columnNameArr3.count-1) {
            cell.cuttingLine.hidden=NO;
            cell.rightArrow.hidden=NO;
            cell.selectionStyle = UITableViewCellSelectionStyleDefault;
        }
    }
    return cell;
}

#pragma mark cell的点击事件
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.section==2) {
        if(indexPath.row==columnNameArr3.count-1) {
            UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
            cell.selected = NO;
            self.gotoScanCodeListBlock();
        }
    }
}

#pragma mark 会议详情和会议室详情切换
- (IBAction)btnAction:(id)sender {
    
    if (_meetingDetailsBtn==sender) {
        //会议详情
        [UIView animateWithDuration:0.5f animations:^{
            _scrollView.contentOffset=CGPointMake(0, 0);
        }];
        
    }else if (_meetingDominateBtn==sender){
        //会议室详情
        [UIView animateWithDuration:0.5f animations:^{
            _scrollView.contentOffset=CGPointMake(APPW, 0);
        }];
        
    }
    
}
@end
