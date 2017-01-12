//
//  ExtendView.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/5.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ExtendView.h"
#import "Tools.h"
#import "UIColor+LJFColor.h"
#import "ExtendViewTableViewCell.h"
#import "MeetingProlong.h"
#import "MeetingRoomInfo.h"
#import "MeetingInfoManager.h"
@implementation ExtendView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
*/
- (void)drawRect:(CGRect)rect {
    // Drawing code
    _tableView.dataSource=self;
    _tableView.delegate=self;
    _tableView.bounces=NO;
    _tableView.showsVerticalScrollIndicator=NO;
    _tableViewHeight.constant=41*3+47;
    [_tableView registerNib:[UINib nibWithNibName:@"ExtendViewTableViewCell" bundle:nil] forCellReuseIdentifier:@"ExtendViewTableViewCell"];
}
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return _meetingProLongList.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    ExtendViewTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"ExtendViewTableViewCell"];
    MeetingProlong *meetingProlong=_meetingProLongList[indexPath.row];
    cell.meetingRoomLable.text=[self returnMeetingRoom:meetingProlong.meetingRoomID];
    if([@"N" isEqualToString: meetingProlong.isProlong]){
       cell.timeLable.text=@"不可延长";
    }else{
        cell.timeLable.text=[NSString stringWithFormat:@"%@锁定",meetingProlong.prolongTime];
    }
    
    return cell;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 41;
}
-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 47;
}
-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    UILabel *label=[[UILabel alloc]initWithFrame:CGRectMake(0, 0, APPW, 47)];
    label.text=@"您延长的会议与该会议室下一场会议发生冲突";
    label.textColor=[UIColor colorWithHexString:@"999999"];
    label.font=[UIFont systemFontOfSize:16.0];
    label.textAlignment=NSTextAlignmentCenter;
    label.backgroundColor=[UIColor whiteColor];
    return label;
}
- (IBAction)extendBtnAction:(id)sender {
    self.extendBtnBlock();
}
- (IBAction)cancelBtnAction:(id)sender {
    self.cancelBtnBlock();
}

-(NSString *)returnMeetingRoom:(NSString *)rId{
    
    if (![@""isEqualToString:rId] && rId!=nil ) {
        
        MeetingRoomInfo *meetingRoomInfo= [[[MeetingInfoManager shareInstance] selectSysMeetingRoomInfoWithRoomId:rId]firstObject];
        return meetingRoomInfo.meetingRoomName;
    }
    return @"";
}
@end
