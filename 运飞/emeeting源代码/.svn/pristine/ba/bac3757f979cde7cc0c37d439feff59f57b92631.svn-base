//
//  MeetingDominateView.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/4/25.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingDominateView.h"
#import "MeetingDominateTableViewCell.h"
#import "UIColor+LJFColor.h"
#import "ExtendView.h"
#import "Tools.h"
#import "GetMeetingProlong.h"
#import "MeetingJoinInfo.h"
#define MeetingJoinInfoKey @"meetingJoinInfoKey"
@implementation MeetingDominateView{
   
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.

*/
- (void)drawRect:(CGRect)rect {
    _tableview.dataSource=self;
    _tableview.delegate=self;
    _tableview.separatorStyle =UITableViewCellSeparatorStyleNone;
    _tableview.tableFooterView=[[UIView alloc]init];
    _tableview.bounces=NO;
    _textFieldView.delegate=self;
    _textFieldView.layer.borderColor=[[UIColor colorWithHexString:@"c6c6c6"] CGColor];
    _textFieldView.layer.borderWidth=1;
    [_tableview registerNib:[UINib nibWithNibName:@"MeetingDominateTableViewCell" bundle:nil] forCellReuseIdentifier:@"MeetingDominateTableViewCell"];
    [self dominateBtnAction];
    // Drawing code
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return _meetingJoinInfoList.count;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    MeetingJoinInfo *meetingJoinInfo=_meetingJoinInfoList[indexPath.row];
    return meetingJoinInfo.cellHeight;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
     MeetingDominateTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"MeetingDominateTableViewCell"];
    //tableView.rowHeight=80;
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    MeetingJoinInfo *meetingJoinInfo=_meetingJoinInfoList[indexPath.row];
    cell.meetingJoinInfo=meetingJoinInfo;
    
    [cell.callBtn addTarget:self action:@selector(callBtnAction:) forControlEvents:UIControlEventTouchUpInside];
    objc_setAssociatedObject(cell.callBtn, MeetingJoinInfoKey, meetingJoinInfo, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
    //if( [@"2"isEqualToString:meetingJoinInfo.state] || [@"3"isEqualToString:meetingJoinInfo.state]){
        [cell.muteBtn addTarget:self action:@selector(muteBtnAction:) forControlEvents:UIControlEventTouchUpInside];
        objc_setAssociatedObject(cell.muteBtn, MeetingJoinInfoKey, meetingJoinInfo, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
    //}
    
    return cell;
}

-(void)callBtnAction:(UIButton *)sender{
    
    MeetingJoinInfo *meetingJoinInfo=objc_getAssociatedObject(sender, MeetingJoinInfoKey);
    
    if([@"0"isEqualToString:meetingJoinInfo.state]){
        for (MeetingJoinInfo *meInfo in _meetingJoinInfoList) {
            if (meetingJoinInfo==meInfo) {
                meetingJoinInfo.state=@"1";
                [self.tableview reloadData];
            }
        }
        self.requestMeetingOperationBlock(@"1",meetingJoinInfo);
        
    }else{
//        for (MeetingJoinInfo *meInfo in _meetingJoinInfoList) {
//            if (meetingJoinInfo==meInfo) {
//                meetingJoinInfo.state=@"0";
//                [self.tableview reloadData];
//            }
 //       }
        
        self.requestMeetingOperationBlock(@"0",meetingJoinInfo);
        
    }
}

-(void)muteBtnAction:(UIButton *)sender{
    MeetingJoinInfo *meetingJoinInfo=objc_getAssociatedObject(sender, MeetingJoinInfoKey);
    if([@"2"isEqualToString:meetingJoinInfo.state]){
//        for (MeetingJoinInfo *meInfo in _meetingJoinInfoList) {
//            if (meetingJoinInfo==meInfo) {
//                meetingJoinInfo.state=@"2";
//                [self.tableview reloadData];
//            }
//        }

        self.requestMeetingOperationBlock(@"2",meetingJoinInfo);
    }else if([@"3"isEqualToString:meetingJoinInfo.state]){
//        for (MeetingJoinInfo *meInfo in _meetingJoinInfoList) {
//            if (meetingJoinInfo==meInfo) {
//                meetingJoinInfo.state=@"3";
//                [self.tableview reloadData];
//            }
//        }
        self.requestMeetingOperationBlock(@"3",meetingJoinInfo);
    }else if([@"1"isEqualToString:meetingJoinInfo.state]){
//        for (MeetingJoinInfo *meInfo in _meetingJoinInfoList) {
//            if (meetingJoinInfo==meInfo) {
//                meetingJoinInfo.state=@"0";
//                [self.tableview reloadData];
//            }
//        }
        
        self.requestMeetingOperationBlock(@"0",meetingJoinInfo);
    }
}
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    return YES;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSLog(@"点击了删除");
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        MeetingJoinInfo *meetingJoinInfo=_meetingJoinInfoList[indexPath.row];
    self.requestMeetingOperationBlock(@"4",meetingJoinInfo);
    }
}
-(UITableViewCellEditingStyle)tableView:(UITableView *)tableView editingStyleForRowAtIndexPath:(NSIndexPath *)indexPath
{
   // NSLog(@"手指撮动了%li",indexPath.row);
    return UITableViewCellEditingStyleDelete;
}

-(NSString *)tableView:(UITableView *)tableView titleForDeleteConfirmationButtonForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return  @"删除";
}
- (void)dominateBtnAction{
    if (!_extendView) {
        _extendView=[[[NSBundle mainBundle]loadNibNamed:@"ExtendView" owner:nil options:nil]firstObject];
        _extendView.frame=CGRectMake(0,0, APPW,APPH-116);
        _extendView.backgroundColor=RGBA(170, 170, 170, 0.45);
        _extendView.hidden=YES;
        [self addSubview:_extendView];
    }
    
}

- (IBAction)meetingDominateBtnAction:(id)sender{
    self.requestMeetingProlongInfoBlock();

}
-(void)dominate:(NSArray *)meetingProLongList{
     __weak typeof(self) wself = self;
    _extendView.hidden=NO;
    _extendView.cancelBtnBlock=^{
        [wself hideenExtendView];
    };
    _extendView.extendBtnBlock=^{
        wself.requestMeetingProlongBlock();
    };
    if(meetingProLongList.count>0){
        if(meetingProLongList.count>3){
            _extendView.tableViewHeight.constant=41*3+47;
        }else{
            _extendView.tableViewHeight.constant=41*meetingProLongList.count+47;
        }
         _extendView.meetingProLongList=meetingProLongList;
        _extendView.extendBtnBlock=^{
            wself.requestMeetingProlongBlock();
        };
        [_extendView.tableView reloadData];
    }else{
        _extendView.tableView.hidden=YES;
    }
    
}
- (IBAction)skipMeetingDominate:(id)sender {
    self.skipMeetingDominateBlock();
}

-(void)hideenExtendView{
    _extendView.hidden=YES;
}

@end
