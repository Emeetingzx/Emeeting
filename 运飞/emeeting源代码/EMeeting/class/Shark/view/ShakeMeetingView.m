//
//  ShakeMeetingView.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/24.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ShakeMeetingView.h"
#import "Tools.h"
#import "UIColor+LJFColor.h"


@interface ShakeMeetingView ()<MeetingScheduleTableViewCellDelegate>

@property (nonatomic, strong) UILabel *titleLabel;

@property (nonatomic, strong) UITableView *tableView;

@property (nonatomic, assign) CGFloat tableViewHeigth;

@property (nonatomic, strong) UIView *bgView;

@end

@implementation ShakeMeetingView

- (instancetype)initWithFrame:(CGRect)frame MeetingRoomInfo:(NSArray *)dataArr
{
    self = [super initWithFrame:frame];
    if (self)
    {
        [self loadMeetingInfoDataArrWithArr:dataArr];
        
        [self loadSubView];
    }
    return self;
}

- (void)loadMeetingInfoDataArrWithArr:(NSArray *)dataArr
{
    _meetingRoomInfoArr = [[NSMutableArray alloc] init];
    
    for (NSDictionary *dict in dataArr)
    {
        MeetingRoomInfo *info = [[MeetingRoomInfo alloc] initWithDictionary:dict];
        info.rowHeigth = [self loadRowHeigthWithRoomName:info.meetingRoomName];
        _tableViewHeigth += info.rowHeigth;
        [_meetingRoomInfoArr addObject:info];
    }
    
    if ( _tableViewHeigth > 113.2*3)
    {
        _tableViewHeigth = 113.2*3;
    }
}

- (CGFloat)loadRowHeigthWithRoomName:(NSString *)roomName
{
    CGFloat width = 0;
    
    if (IPHONE6)
    {
        width = APPW - 68 - 70 - 20 -10;
    }else if(IPHONE6P)
    {
        width = APPW - 68 - 80 - 20 - 10;
    }else
    {
        width = APPW - 68 - 60 - 20 - 10;
    }
    
    CGFloat heigth = [Tools heightOfString:roomName font:[UIFont systemFontOfSize:16] width:width];
    
    if (heigth > 30)
    {
        return 95 + heigth -20;
    }else
    {
        return 95;
    }
}

- (void)loadSubView
{
    self.backgroundColor = [UIColor colorWithWhite:0 alpha:0.5];
    
    self.bgView = [[UIView alloc] initWithFrame:CGRectMake(10, (self.frame.size.height- (38+ _tableViewHeigth))/2.0+24, APPW - 20, 38+ _tableViewHeigth)];
    self.bgView.backgroundColor = [UIColor whiteColor];
    [self addSubview:self.bgView];
    
    _titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(10, 14, APPW - 100, 20)];
    _titleLabel.text = [NSString stringWithFormat:@"恭喜您！摇出%li个会议室",(unsigned long)self.meetingRoomInfoArr.count];
    _titleLabel.textColor = [UIColor colorWithHexString:@"#333333"];
    _titleLabel.font = [UIFont systemFontOfSize:16];
    [self.bgView addSubview:_titleLabel];
    
    _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 38, self.bgView.frame.size.width, self.bgView.frame.size.height - 38) style:UITableViewStylePlain];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.bgView addSubview:_tableView];
    
    [_tableView registerNib:[UINib nibWithNibName:@"MeetingScheduleTableViewCell" bundle:nil] forCellReuseIdentifier:@"MeetingScheduleTableViewCell"];
    
    CGRect rect = self.bgView.frame;
    rect.origin.y = -rect.size.height;
    self.bgView.frame = rect;
    rect.origin.y = (self.frame.size.height - (38+ _tableViewHeigth))/2.0+24;
    __weak typeof(self) wself = self;
    [UIView animateWithDuration:0.5 animations:^
     {
         wself.bgView.frame = rect;
     }];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.meetingRoomInfoArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    MeetingScheduleTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"MeetingScheduleTableViewCell"];
    
    cell.presentView.hidden = YES;
    cell.meetingImageView.hidden = NO;
    
    cell.meetingRoomInfo = self.meetingRoomInfoArr[indexPath.row];
    
    cell.delegate = self;
    
    cell.scheduleButton.tag = ScheduleButtonBeginTag + indexPath.row;
    
    return cell;
}

- (void)MeetingScheduleTableViewCell:(MeetingScheduleTableViewCell *)cell didClickRow:(NSInteger)row
{
    if ([self.delegate respondsToSelector:@selector(shakeMeetingView:didClickMeetingRoomInfo:)])
    {
        [self.delegate shakeMeetingView:self didClickMeetingRoomInfo:self.meetingRoomInfoArr[row]];
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return [self.meetingRoomInfoArr[indexPath.row] rowHeigth];
}

- (void)setMeetingRoomInfoArr:(NSMutableArray<MeetingRoomInfo *> *)meetingRoomInfoArr
{
    _meetingRoomInfoArr = meetingRoomInfoArr;
    
    _titleLabel.text = [NSString stringWithFormat:@"恭喜您！摇出%li个会议室",(unsigned long)_meetingRoomInfoArr.count];
    
    _tableViewHeigth = 0;
    for (MeetingRoomInfo *model in meetingRoomInfoArr)
    {
        _tableViewHeigth += model.rowHeigth;
    }
    
    if ( _tableViewHeigth > 113.2*3)
    {
        _tableViewHeigth = 113.2*3;
    }
    
    self.bgView.frame = CGRectMake(10, (self.frame.size.height- (38+ _tableViewHeigth))/2.0+24, APPW - 20, 38+ _tableViewHeigth);
    self.tableView.frame = CGRectMake(0, 38, self.bgView.frame.size.width, self.bgView.frame.size.height - 38);
    
    [self.tableView reloadData];
}


@end
