//
//  SelectPlaceViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "SelectPlaceViewController.h"
#import "AreaTableViewCell.h"
#import "SubAreaTableViewCell.h"
#import "SubAreaTableView.h"
#import "LocationTableView.h"
#import "FMDBManager.h"
#import "UIColor+LJFColor.h"
#import "Tools.h"
#import "AppDelegate.h"
#import "CustomAlertView.h"

@interface SelectPlaceViewController ()<UITableViewDataSource,UITableViewDelegate,SubAreaTableViewDelegate,CustomAlertViewDelegate>

@property (weak, nonatomic) IBOutlet UITableView *tableView;

@property (weak, nonatomic) IBOutlet UIView *bgView;

@property (strong, nonatomic) NSMutableArray<MeetingRoomModel *> *dataArr;

@property (strong, nonatomic) SubAreaTableView *subAreaTable;

@property (strong, nonatomic) LocationTableView *locationTableView;

@property (nonatomic, copy) NSString *firstId;

@property (nonatomic, copy) NSString *secondId;

@property (nonatomic, assign) int defauletIndex;

@property (nonatomic, strong) CustomAlertView *bgAlertView;

@end

@implementation SelectPlaceViewController

#pragma mark -加载数据源
-(NSMutableArray *)dataArr
{
    if (_dataArr == nil)
    {
        _dataArr = [[NSMutableArray alloc] init];
        
        NSArray *arr = [[FMDBManager shareInstance] selectSysMeetingRoomAddressInfoLevelId:@"1" andPID:@"0"];
        
        NSArray *arr1 = [self loadMyDataWirtArr:arr];

        [self getFirstidAndSecondIdWithDataArr:arr1];
        
        for (int i = 0; i < arr1.count; i++)
        {
            MeetingRoomModel *model = arr1[i];
                
            if ([model.iD isEqualToString:self.firstId])
            {
                model.isSelected = YES;
                self.defauletIndex = i;
            }else
            {
                model.isSelected = NO;
            }
            [_dataArr addObject:model];
        }
        
    }
    return _dataArr;
}

#pragma mark -对数据源排序
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

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    if ([(AppDelegate*)[[UIApplication sharedApplication]delegate] isDoneUpdate])
    {
        self.bgView.hidden = YES;
        self.tableView.dataSource = self;
        self.tableView.delegate = self;
        [self.tableView registerNib:[UINib nibWithNibName:@"AreaTableViewCell" bundle:nil] forCellReuseIdentifier:@"AreaTableViewCell"];
        [self loadSubAreaTableView];
        
    }else
    {
        self.bgView.hidden = NO;
        self.bgAlertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"正在更新会议地址信息，请稍后..." ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
        self.bgAlertView.delegate = self;
        self.bgAlertView.tapIsHidden = YES;
        [self.view addSubview:self.bgAlertView];
        
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateSuceess) name:@"DoneUpdate" object:nil];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateError) name:@"UpdateError" object:nil];
    }
}

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)updateSuceess
{
    self.bgView.hidden = YES;
    self.bgAlertView.hidden = YES;
    
    self.tableView.dataSource = self;
    self.tableView.delegate = self;
    [self.tableView registerNib:[UINib nibWithNibName:@"AreaTableViewCell" bundle:nil] forCellReuseIdentifier:@"AreaTableViewCell"];
    [self.tableView reloadData];
    [self loadSubAreaTableView];
}

- (void)updateError
{
    NSString *roomAddressUpDate = [[NSUserDefaults standardUserDefaults] objectForKey:@"SysMeetingRoomAddress"];
    
    self.bgView.hidden = YES;
    self.bgAlertView.hidden = YES;
    if (roomAddressUpDate.length > 0)
    {
        self.tableView.dataSource = self;
        self.tableView.delegate = self;
        [self.tableView registerNib:[UINib nibWithNibName:@"AreaTableViewCell" bundle:nil] forCellReuseIdentifier:@"AreaTableViewCell"];
        [self loadSubAreaTableView];
    }else
    {
        self.bgView.hidden = NO;
        CustomAlertView *AlertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"更新数据失败" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
        [AlertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:2.0];
    }
}

- (void)CustomAlertViewTouchHidden:(CustomAlertView *)customAlert
{
    [customAlert removeFromSuperview];
    [self back:nil];
}

#pragma mark -加载二级tableView
- (void)loadSubAreaTableView
{
    self.subAreaTable = [[SubAreaTableView alloc] initWithFrame:CGRectMake(80, 68, [UIScreen mainScreen].bounds.size.width > 320?130:120, self.view.bounds.size.height - 68)];
    self.subAreaTable.defaultModel = self.DefaultSelect;
    self.subAreaTable.supView = self.view;
    self.subAreaTable.pid = [self.dataArr[self.defauletIndex] iD];
    self.subAreaTable.delegate = self;
    self.subAreaTable.secondId = self.secondId;
    [self.view addSubview:self.subAreaTable];
}

#pragma mark -tableView方法
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataArr.count;
}

//返回Cell
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    AreaTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"AreaTableViewCell"];
    
    cell.areaLabel.text = [self.dataArr[indexPath.row] addessChinese];
    
    if ([self.dataArr[indexPath.row] isSelected])
    {
        cell.areaLabel.textColor = [UIColor colorWithHexString:@"#00aeff"];
        cell.backgroundColor = [UIColor colorWithHexString:@"#f4f4f4"];
    }else
    {
        cell.areaLabel.textColor = [UIColor colorWithHexString:@"#333333"];
        cell.backgroundColor = [UIColor colorWithHexString:@"#dddddd"];
    }
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    self.subAreaTable.pid = [self.dataArr[indexPath.row] iD];
    
    for (MeetingRoomModel *model in self.dataArr)
    {
        model.isSelected = NO;
    }
    
    [self.dataArr[indexPath.row] setIsSelected:YES];
    
    [self.tableView reloadData];
}

- (IBAction)back:(id)sender
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)SubAreaTableView:(SubAreaTableView *)areaView didSelectedInfo:(MeetingRoomModel *)selectedInfo
{
    if ([self.delegate respondsToSelector:@selector(selectPlaceViewController:didSelectInfo:)])
    {
        [self.delegate selectPlaceViewController:self didSelectInfo:selectedInfo];
    }
    
    [self back:nil];
}

- (void)getFirstidAndSecondIdWithDataArr:(NSArray *)dataArr
{
    if (self.DefaultSelect)
    {
        NSArray *arr = [[FMDBManager shareInstance] selectSysMeetingRoomAddressInfoWithRoomId:self.DefaultSelect.iD];
        NSString *SecondId = [(MeetingRoomModel *)arr.firstObject pID];
        self.secondId = SecondId;
        
        NSArray *arr1 = [[FMDBManager shareInstance] selectSysMeetingRoomAddressInfoWithRoomId:SecondId];
        
        NSString *firstId = [(MeetingRoomModel *)arr1.firstObject pID];
        self.firstId = firstId;

    }else
    {
        MeetingRoomModel *model = dataArr.firstObject;
        self.firstId = model.iD;
    }
}

@end
