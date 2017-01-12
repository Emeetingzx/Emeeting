//
//  SetViewController.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "SetViewController.h"
#import "UIViewController+MMDrawerController.h"
#import "FeedbackViewController.h"
#import "EconewbieViewController.h"
#import "AboutViewController.h"
#import "CustomAlertView.h"
#import "Tools.h"
#import "UIColor+LJFColor.h"
#import "EMMAppUpdate.h"
#import "EMMSecurity.h"
#import "AppDelegate.h"
#import "EMMMsgCenter.h"
@interface SetViewController ()

@end

@implementation SetViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor= [UIColor whiteColor];
    self.tableView.tableFooterView=[[UIView alloc]init];
    
    // Uncomment the following line to preserve selection between presentations.
     //self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    UIView *stateView=[[UIView alloc]initWithFrame:CGRectMake(0, -20,APPW , 20)];
    stateView.backgroundColor=[UIColor colorWithHexString:@"292929"];
    [self.view addSubview:stateView];
    [[EMMAppUpdate share] checkAppVersion:EMMAppID withSSOToken:[EMMSecurity share].token];
    [EMMAppUpdate share].delegate=self;
    NSDictionary *infoDict = [[NSBundle mainBundle] infoDictionary];
    NSString *appVersion = [infoDict objectForKey:@"CFBundleShortVersionString"];
    _versionNum.text=appVersion;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {

    return 8;
}
-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath{
    cell.selectionStyle = UITableViewCellSelectionStyleBlue;
    if (indexPath.row==0) {
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Set" bundle:nil];
     [tableView deselectRowAtIndexPath:indexPath animated:NO];
    switch (indexPath.row) {
            
        case 2:
        {
            NSLog(@"跳转意见反馈");
          FeedbackViewController  *feedbackViewController = [storyboard instantiateViewControllerWithIdentifier:@"FeedbackViewController"];
             __weak typeof(self) wself = self;
            feedbackViewController.block=^{
                CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:[UIScreen mainScreen].bounds Message:@"反馈成功" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:150];
                [wself.view addSubview:alertView];
                [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
            };
            [self.navigationController pushViewController:feedbackViewController animated:YES];
            break;
        }
        case 3:
        {
            EconewbieViewController  *econewbieViewController = [storyboard instantiateViewControllerWithIdentifier:@"EconewbieViewController"];
            [self.navigationController pushViewController:econewbieViewController animated:YES];
             NSLog(@"跳转使用指南");
            break;
        }
        case 4:
        {
            AboutViewController  *aboutViewController = [storyboard instantiateViewControllerWithIdentifier:@"AboutViewController"];
            [self.navigationController pushViewController:aboutViewController animated:YES];
             NSLog(@"跳转关于");
            break;
        }
        case 5:
             NSLog(@"检测新版本");
            [[EMMAppUpdate share] checkAppVersion:EMMAppID withSSOToken:[EMMSecurity share].token];
            [EMMAppUpdate share].delegate=self;

            break;
        default:
           
            break;
    }
}

#pragma mark -EMMAppUpdate代理方法
- (void)finishedCheckAppVersion:(BOOL)success update:(BOOL)update
{
    if (!update)
    {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"当前版本已经是最新版本" delegate:nil cancelButtonTitle:nil otherButtonTitles:@"确定", nil];
        [alert show];
    }
    [EMMAppUpdate share].delegate = nil;
}


- (void)finishedCheckAppVersion:(BOOL)success appversionStatu:(AppVersionStatu)statu{
    
    if (statu==AppVersionStatu_HaveNew) {
         _versionImageView.hidden=NO;
        
    }else{
        _versionImageView.hidden=YES;
    }
    if (statu==AppVersionStatu_AlreadyNew){
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"当前版本已经是最新版本" delegate:nil cancelButtonTitle:nil otherButtonTitles:@"确定", nil];
        [alert show];
    }
    
}
- (IBAction)logoutAction:(id)sender {
    UIAlertView *alertView=[[UIAlertView alloc]initWithTitle:nil message:@"是否退出当前账号？" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
    alertView.delegate=self;
    [alertView show];

}
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex{
    if (buttonIndex==1) {
        [[EMMSecurity share] logOut];
        [[EMMMsgCenter share] close];
        [self exitApplication];
    }
}
- (void)exitApplication {
    
    AppDelegate *app = [UIApplication sharedApplication].delegate;
    UIWindow *window = app.window;
    
    [UIView animateWithDuration:1.0f animations:^{
        window.alpha = 0;
        window.frame = CGRectMake(APPW/2.0, APPH/2.0, 0, 0);
    } completion:^(BOOL finished) {
        exit(0);
    }];
    
}
- (IBAction)openLeftMenu:(id)sender
{
    [self.mm_drawerController toggleDrawerSide:MMDrawerSideLeft animated:YES completion:nil];
}


@end
