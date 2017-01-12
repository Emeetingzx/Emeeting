//
//  LocationTableView.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "LocationTableView.h"
#import "AreaTableViewCell.h"

#import "FMDBManager.h"
#import "UIColor+LJFColor.h"

@interface LocationTableView ()<UITableViewDelegate,UITableViewDataSource>

@property (nonatomic, strong) UITableView *tableView;

@property (nonatomic, strong) NSMutableArray<MeetingRoomModel *> *dataArr;

@end

@implementation LocationTableView

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
    self.backgroundColor = [UIColor whiteColor];
    
    self.tableView = [[UITableView alloc] initWithFrame:self.bounds style:UITableViewStylePlain];
    self.tableView.dataSource = self;
    self.tableView.delegate = self;
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.showsVerticalScrollIndicator = NO;
    
    [self addSubview:self.tableView];
    
    [self.tableView registerNib:[UINib nibWithNibName:@"AreaTableViewCell" bundle:nil] forCellReuseIdentifier:@"AreaTableViewCell"];
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
    AreaTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"AreaTableViewCell"];
    cell.backgroundColor = [UIColor whiteColor];
    
    cell.areaLabel.text = [self.dataArr[indexPath.row] addessChinese];
    
    if ([self.dataArr[indexPath.row] isSelected])
    {
        cell.areaLabel.textColor = [UIColor colorWithHexString:@"#00aeff"];
    }else
    {
        cell.areaLabel.textColor = [UIColor colorWithHexString:@"#333333"];
    }

    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    for (MeetingRoomModel *model in self.dataArr)
    {
        model.isSelected = NO;
    }
    
    [self.dataArr[indexPath.row] setIsSelected:YES];
    
    [self.tableView reloadData];
    
    if ([self.delegate respondsToSelector:@selector(locationTableView:didSelectMeetingRoomModel:)])
    {
        [self.delegate locationTableView:self didSelectMeetingRoomModel:self.dataArr[indexPath.row]];
    }
}

- (void)setPid:(NSString *)pid
{
    _pid = pid;
    
    _dataArr = [[NSMutableArray alloc] init];
    
    NSArray *arr = [[FMDBManager shareInstance] selectSysMeetingRoomAddressInfoLevelId:@"3" andPID:pid];
    
    NSArray *arr1 = [self loadMyDataWirtArr:arr];
    
    for (MeetingRoomModel *model in arr1)
    {
        model.isSelected = NO;
        [_dataArr addObject:model];
    }
    
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


- (void)setDefaultId:(NSString *)defaultId
{
    _defaultId = defaultId;
    
    if (defaultId == nil)
    {
        return;
    }
    
    for (MeetingRoomModel *model in self.dataArr)
    {
        if ([model.iD isEqualToString:defaultId])
        {
            model.isSelected = YES;
        }else
        {
            model.isSelected = NO;
        }
    }
    
    [self.tableView reloadData];
}



@end
