//
//  LeftMuneViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/1/29.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "LeftMuneViewController.h"
#import "LeftMenuCell.h"
#import "MenuModel.h"
#import "PhoneOrVideoMeetingViewController.h"
#import "MyMeetingViewController.h"
#import "SetViewController.h"
#import "ShakeViewController.h"
#import "MeetingScheduleViewController.h"
#import "UIViewController+MMDrawerController.h"
#import "MMDrawerController.h"
#import "AddValueViewController.h"
#import "Tools.h"
#import "NetworkingManager.h"
#import "EMMSecurity.h"
#import "UIImageView+EMMFileCache.h"
#define MeetingSchedule          0
#define Shake                    1
#define PhoneOrVideoMeeting      2
#define MyMeeting                3
#define AddValue                 4
#define ScanCode                 5
#define Set                      7


@interface LeftMuneViewController ()<UITableViewDataSource,UITableViewDelegate>


@property (weak, nonatomic) IBOutlet NSLayoutConstraint *tableViewTop;

@property (weak, nonatomic) IBOutlet UITableView *tableView;

@property (weak, nonatomic) IBOutlet UIImageView *iconImag;

@property (weak, nonatomic) IBOutlet UILabel *nameLabel;

@property (weak, nonatomic) IBOutlet UILabel *userIdLabel;

@property (strong, nonatomic) NSMutableArray<MenuModel *> *dataArr;
@property (strong, nonatomic) MenuModel *selectMenu;

@end

@implementation LeftMuneViewController

- (NSMutableArray *)dataArr
{
 
    if (_dataArr == nil)
    {
        MenuModel *secdule = [[MenuModel alloc] initWithImageName:@"conference_booking_btn" andTextName:@"会议预订" andColor:[UIColor colorWithRed:255/255.0 green:150/255.0 blue:0/255.0 alpha:1.0]];
        self.selectMenu = secdule;
        
        MenuModel *shake = [[MenuModel alloc] initWithImageName:@"shake_btn" andTextName:@"摇一摇" andColor:[UIColor clearColor]];
        
        MenuModel *phone = [[MenuModel alloc] initWithImageName:@"conference_bridge_btn" andTextName:@"电话/视频会议桥" andColor:[UIColor clearColor]];
        
        MenuModel *myMeeting = [[MenuModel alloc] initWithImageName:@"my_meeting_btn" andTextName:@"我的会议" andColor:[UIColor clearColor]];
        
        MenuModel *otherService = [[MenuModel alloc] initWithImageName:@"icon_homemenu_service" andTextName:@"增值服务" andColor:[UIColor clearColor]];
        
         MenuModel *scanCode = [[MenuModel alloc] initWithImageName:@"scanCodeIcon" andTextName:@"扫一扫签到" andColor:[UIColor clearColor]];
         MenuModel *nullModel = [[MenuModel alloc] initWithImageName:nil andTextName:@"" andColor:[UIColor clearColor]];
        MenuModel *set = [[MenuModel alloc] initWithImageName:@"intercalate_btn" andTextName:@"设置" andColor:[UIColor clearColor]];
        
        _dataArr = [[NSMutableArray alloc] initWithObjects:secdule,shake,phone,myMeeting,otherService,scanCode,nullModel,set, nil];
    }
    
    return _dataArr;
    
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.iconImag.layer.cornerRadius=28;
    [self.tableView registerNib:[UINib nibWithNibName:@"LeftMenuCell" bundle:nil] forCellReuseIdentifier:@"LeftMenuCell"];
    
    if (IPHONE4)
    {
        self.tableViewTop.constant = 10;
    }
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(setMyCenterController:) name:SetCenterControllerNoti object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(loginSuccess) name:@"loginSuccessToGetPlace" object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(setChangeColorNoti:) name: SetChangeColorNoti object:nil];
   
}

- (void)loginSuccess
{
    //NSLog(@"userInfo == %@",[[EMMSecurity share] userInfo]);
    self.nameLabel.text = [[EMMSecurity share] userInfo][@"CNM"];
    self.userIdLabel.text = [[EMMSecurity share] userId];
    self.iconImag.layer.masksToBounds = YES;
    [self loadIconImage];
}

- (void)loadIconImage
{
    
    NSString *path = [NSString stringWithFormat:@"http://mdm.zte.com.cn:80/redpacketnew/moa/services.dssm?U=%@",[[EMMSecurity share] userId]];
    NSURL *url = [NSURL URLWithString:path];
    
    [self.iconImag emm_setImageWithURL:url placeholderImage:[UIImage imageNamed:@"default_icon_small"]];
    
//    NSString *path = [NSString stringWithFormat:@"http://moaproxy.zte.com.cn/tech/rest/auth/userinfo_image?userId=%@",@"00138624"];
//    
//    NSURL *url = [NSURL URLWithString:path];
//    
//    NSURLRequest *request = [NSURLRequest requestWithURL:url];
//    
//    [NSURLConnection sendAsynchronousRequest:request queue:[NSOperationQueue mainQueue] completionHandler:^(NSURLResponse * _Nullable response, NSData * _Nullable data, NSError * _Nullable connectionError)
//    {
//        NSString *str = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
//        
//        NSURL *url1 = [NSURL URLWithString:str];
//        
//        [self.iconImag emm_setImageWithURL:url1 placeholderImage:[UIImage imageNamed:@"default_icon_small"]];
//    }];
}

-(void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)didReceiveMemoryWarning
{
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
    LeftMenuCell *cell = [tableView dequeueReusableCellWithIdentifier:@"LeftMenuCell"];

    if (self.dataArr[indexPath.row].imageName)
    {
        cell.imageV.image = [UIImage imageNamed:self.dataArr[indexPath.row].imageName];
    }else
    {
        cell.imageV.image = nil;
    }
    
    cell.textName.text = self.dataArr[indexPath.row].textName;
    cell.contentView.backgroundColor = self.dataArr[indexPath.row].color;
    
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row ==6)
    {
        return;
    }
    
//    if ([self.dataArr[indexPath.row].textName isEqualToString:self.selectMenu.textName])
//    {
//        [self.mm_drawerController closeDrawerAnimated:YES completion:nil];
//        
//        return;
//    }
    
    [self changeColor:indexPath.row];
    NSLog(@"row=%ld",(long)indexPath.row);
    
    [self setCenterControllerWithIndex:indexPath.row];
}

-(void)changeColor:(NSInteger) rowNum{
    self.selectMenu.color = [UIColor clearColor];
    self.dataArr[rowNum].color = [UIColor colorWithRed:255/255.0 green:150/255.0 blue:0/255.0 alpha:1.0];
    self.selectMenu = self.dataArr[rowNum];
    [self.tableView reloadData];
}

- (void)setCenterControllerWithIndex:(NSInteger)index
{
    UIViewController *controller = nil;
    switch (index)
    {
        case MeetingSchedule:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MeetingSchedule" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
        case Shake:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Shake" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
        case PhoneOrVideoMeeting:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"PhoneOrVideoMeeting" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
        case MyMeeting:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
            
        case AddValue:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"AddValue" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
        case ScanCode:{
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"ScanCode" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
             break;
        case Set:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Set" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
        default:
            break;
    }
    
    __weak typeof(self) wself = self;
    if (controller)
    {
        [self.mm_drawerController closeDrawerAnimated:YES completion:^(BOOL finished)
        {
            [wself.mm_drawerController setCenterViewController:controller];
        }];
    }
}

-(void)setChangeColorNoti:(NSNotification *)info{
    NSNumber *num = info.object;
    
    int index = [num intValue];
    
    self.selectMenu.color = [UIColor clearColor];
    self.dataArr[index].color = [UIColor colorWithRed:255/255.0 green:150/255.0 blue:0/255.0 alpha:1.0];
    self.selectMenu = self.dataArr[index];
    [self.tableView reloadData];

}

- (void)setMyCenterController:(NSNotification *)info
{
    NSNumber *num = info.object;
    
    int index = [num intValue];
    
    self.selectMenu.color = [UIColor clearColor];
    self.dataArr[index].color = [UIColor colorWithRed:255/255.0 green:150/255.0 blue:0/255.0 alpha:1.0];
    self.selectMenu = self.dataArr[index];
    [self.tableView reloadData];
    
    UIViewController *controller = nil;
    switch (index)
    {
        case MeetingSchedule:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MeetingSchedule" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
        case Shake:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Shake" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
        case PhoneOrVideoMeeting:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"PhoneOrVideoMeeting" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
        case MyMeeting:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
            
        case AddValue:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"AddValue" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
        case ScanCode:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"AddValue" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
        case Set:
        {
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Set" bundle:nil];
            controller = [storyboard instantiateInitialViewController];
        }
            break;
        default:
            break;
    }
    
    __weak typeof(self) wself = self;
    if (controller)
    {
        [self.mm_drawerController closeDrawerAnimated:NO completion:^(BOOL finished)
         {
             [wself.mm_drawerController setCenterViewController:controller];
         }];
    }
}


@end
