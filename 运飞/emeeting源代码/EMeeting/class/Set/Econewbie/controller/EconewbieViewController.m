//
//  EconewbieViewController.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "EconewbieViewController.h"
#import "EconewbieTableViewCell.h"
#import "EconewbieDetailsViewController.h"
@interface EconewbieViewController (){
    NSArray *titleArr;
    NSArray *textArr;
}

@end

@implementation EconewbieViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    _tableView.dataSource=self;
    _tableView.delegate=self;
    [_tableView registerNib:[UINib nibWithNibName:@"EconewbieTableViewCell" bundle:nil] forCellReuseIdentifier:@"EconewbieTableViewCell"];
    titleArr=[NSArray arrayWithObjects:@"会议预定有时间限制吗？",@"如何使用摇一摇功能？",@"如何预定电话和视频会议？",@"如何预定增值业务？", nil];
    
    textArr=[NSArray arrayWithObjects:
                  @"每日0:00至8:20为非预订时间，不提供会议预订服务。",
                  @"摇一摇可以预订接下来整点或半点开始的一小时会议，会议地点为手机当前定位地点。\n\n您也可以点击右上角的筛选图标，选择大楼和时长预订。",
                  @"可通过左侧菜单进入电话/视频会议桥预订界面，预订电话会议桥或视频会议桥。预订成功后可在我的会议—我预订的 界面查看会议详情。\n\n电话会议桥可通过手机、座机或PC电话接入，会议开始后，拨打接入号码，按语音提示输入会议编号和密码即可接入电话会议。\n\n视频会议桥通过会议终端呼入，只需输入接入号码，即可自主接入视频会议【如在公用会议室呼入，记得另行预订会议室】。",
                  @"可通过左侧菜单进入增值服务界面，预订增值服务，预订成功后，会有服务人员联系您确认订单。",nil];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return titleArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
     EconewbieTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"EconewbieTableViewCell"];
    cell.titleLable.text=titleArr[indexPath.row];
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
     UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Set" bundle:nil];
    EconewbieDetailsViewController  *econewbieDetailsViewController = [storyboard instantiateViewControllerWithIdentifier:@"EconewbieDetailsViewController"];
    econewbieDetailsViewController.textString=textArr[indexPath.row];
    econewbieDetailsViewController.titleString=titleArr[indexPath.row];
    [self.navigationController pushViewController:econewbieDetailsViewController animated:YES];
    
}
#pragma mark 返回
- (IBAction)back:(id)sender {
    
    [self.navigationController popViewControllerAnimated:YES];
    
}
/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
