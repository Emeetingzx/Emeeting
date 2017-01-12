//
//  SubAreaTableView.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "SubAreaTableView.h"
#import "SubAreaTableViewCell.h"
#import "Tools.h"
#import "FMDBManager.h"
#import "LocationTableView.h"
#import "UIColor+LJFColor.h"

@interface SubAreaTableView ()<UITableViewDataSource,UITableViewDelegate,LocationTableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;

@property (nonatomic, strong) NSMutableArray<MeetingRoomModel *> *dataArr;

@property (nonatomic, strong) LocationTableView *locationTableView;

@end

@implementation SubAreaTableView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self)
    {
        [self loadTableView];
    }
    return self;
}

- (void)loadTableView
{
    self.tableView = [[UITableView alloc] initWithFrame:self.bounds style:UITableViewStylePlain];
    self.tableView.dataSource = self;
    self.tableView.delegate = self;
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.backgroundColor = [UIColor colorWithHexString:@"#f4f4f4"];
    self.tableView.showsVerticalScrollIndicator = NO;
    
    [self addSubview:self.tableView];
    
    [self.tableView registerNib:[UINib nibWithNibName:@"SubAreaTableViewCell" bundle:nil] forCellReuseIdentifier:@"SubAreaTableViewCell"];
}

-(void)setSupView:(UIView *)supView
{
    _supView = supView;
    
    self.locationTableView = [[LocationTableView alloc] initWithFrame:CGRectMake(210, 68, [UIScreen mainScreen].bounds.size.width > 320?130:120, _supView.bounds.size.height - 68)];
    self.locationTableView.delegate = self;
    [_supView addSubview:self.locationTableView];

}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataArr.count;
}

//返回段数
- (NSInteger)tableView:(UITableView *)tableView sectionForSectionIndexTitle:(NSString *)title atIndex:(NSInteger)index
{
    return 1;
}

//返回Cell
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    SubAreaTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"SubAreaTableViewCell"];
    cell.backgroundColor = [UIColor colorWithHexString:@"#f4f4f4"];
    
    cell.subAreaLabel.text = [self.dataArr[indexPath.row] addessChinese];
    
    if ([self.dataArr[indexPath.row] isSelected])
    {
        cell.subAreaLabel.textColor = [UIColor colorWithHexString:@"#00aeff"];
    }else
    {
        cell.subAreaLabel.textColor = [UIColor colorWithHexString:@"#333333"];
    }
    cell.selectImageView.hidden = ![self.dataArr[indexPath.row] isSelected];

    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    self.locationTableView.pid = [self.dataArr[indexPath.row] iD];
    
    for (MeetingRoomModel *model in self.dataArr)
    {
        model.isSelected = NO;
    }
    
    [self.dataArr[indexPath.row] setIsSelected:YES];
    
    [self.tableView reloadData];

}

- (void)setPid:(NSString *)pid
{
    _pid = pid;
    
    _dataArr = [[NSMutableArray alloc] init];
    
    NSArray *arr = [[FMDBManager shareInstance] selectSysMeetingRoomAddressInfoLevelId:@"2" andPID:pid];
    
    NSArray *arr1 = [self loadMyDataWirtArr:arr];
    
    for (MeetingRoomModel *model in arr1)
    {
        model.isSelected = NO;
        [_dataArr addObject:model];
    }
    
    _dataArr.firstObject.isSelected = YES;

    self.locationTableView.pid = _dataArr.firstObject.iD;
    
    [self.tableView reloadData];
}

- (NSArray *)loadMyDataWirtArr:(NSArray *)arr
{
    NSMutableArray *p = [NSMutableArray arrayWithArray:arr];
    
    [p sortUsingComparator:^NSComparisonResult(id obj1, id obj2)
     {
         NSString *a = [(MeetingRoomModel *)obj1 orderID];
         NSString *b = [(MeetingRoomModel *)obj2 orderID];
         
         int aNum = [a intValue];
         int bNum = [b intValue];
         
         if (aNum > bNum) {
             return NSOrderedDescending;
         }
         else if (aNum < bNum){
             return NSOrderedAscending;
         }
         else {
             return NSOrderedSame;
         }
     }];
    
    return p;
}


-(void)setSecondId:(NSString *)secondId
{
    _secondId = secondId;
    
    if (self.defaultModel == nil)
    {
        return;
    }
    
    for (MeetingRoomModel *model in self.dataArr)
    {
        if ([_secondId isEqualToString:model.iD])
        {
            model.isSelected = YES;
            self.locationTableView.pid = model.iD;
            self.locationTableView.defaultId = self.defaultModel.iD;
        }else
        {
            model.isSelected = NO;
        }
    }
    [self.tableView reloadData];
}


- (void)locationTableView:(LocationTableView *)locationView didSelectMeetingRoomModel:(MeetingRoomModel *)selectLocationModel
{
    if ([self.delegate respondsToSelector:@selector(SubAreaTableView:didSelectedInfo:)])
    {
        [self.delegate SubAreaTableView:self didSelectedInfo:selectLocationModel];
    }
}

@end
