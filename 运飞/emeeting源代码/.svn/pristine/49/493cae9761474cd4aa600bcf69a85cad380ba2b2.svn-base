//
//  ScanCodePersonnelViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/10.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ScanCodePersonnelViewController.h"
#import "ScanCodePersonnelTableViewCell.h"
#import "GetMeetingAttendanceInfo.h"
#import "ProgressView.h"
#import "MeetingAttendanceInfo.h"
#import "Tools.h"
#import "UIColor+LJFColor.h"
#import "UIViewController+MMDrawerController.h"
#import "UserImageCacheManager.h"
#import "UIImageView+EMMFileCache.h"
#import "NSDate+LJFDate.h"
#import "NSDate+Extension.h"
@interface ScanCodePersonnelViewController (){
    GetMeetingAttendanceInfo *getMeetingAttendanceInfo;
}

@end

@implementation ScanCodePersonnelViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self initialView];
    //[self initialData];
    
}
-(void)initialView{
    
    NSDate *beginDate= [NSDate dateWithString:_meetingInfo.beginDate format:[NSDate ymdHmsFormat]];
    if([beginDate isEqualToDate:[beginDate earlierDate:[NSDate getServerDate]]]){
        _notStartView.hidden=YES;
        _organizerImageView.layer.cornerRadius=25;
        _organizerImageView.layer.masksToBounds=YES;
        _scanCodeTableView.dataSource=self;
        _scanCodeTableView.delegate=self;
        _scanCodeTableView.tableFooterView=[[UIView alloc]init];
        [_scanCodeTableView registerNib:[UINib nibWithNibName:@"ScanCodePersonnelTableViewCell" bundle:nil] forCellReuseIdentifier:@"ScanCodePersonnelTableViewCell"];
        getMeetingAttendanceInfo=[[GetMeetingAttendanceInfo alloc]init];
        [UserImageCacheManager loadHeaderImageWithUID:_meetingInfo.organizePeopleNo placeholder:@"default_icon_small" imageView:_organizerImageView];
        _organizerName.text=_meetingInfo.organizePeopleChineseName;
        [self requestMeetingAttendanceInfo];
    }
}

-(void)requestMeetingAttendanceInfo{
    [ProgressView showCustomProgressViewAddTo:self.view];
     __weak typeof(self) wself = self;
    [getMeetingAttendanceInfo sendJSONRequestWithMeetingId:wself.meetingInfo.meetingID Success:^(ResponseObject *response) {
         [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSMutableArray *alreadySignArr=[[NSMutableArray alloc]init];
                NSMutableArray *noSignArr=[[NSMutableArray alloc]init];
                for (NSDictionary *dic in response.d) {
                    MeetingAttendanceInfo *meetingAttendanceInfo=[[MeetingAttendanceInfo alloc]initWithDictionary:dic];
                    if ([@"0" isEqualToString:meetingAttendanceInfo.state]) {
                        [noSignArr addObject:meetingAttendanceInfo];
                    }else if ([@"1" isEqualToString:meetingAttendanceInfo.state]){
                        [alreadySignArr addObject:meetingAttendanceInfo];
                    }
                    
                }
                
                wself.noSignArr=noSignArr;
                wself.alreadySignArr=alreadySignArr;
                [wself.scanCodeTableView reloadData];
            }
            
        }else{
            [ProgressView showHUDAddedTo:wself.view ProgressText:response.m];
        }
        
    } failure:^(NSError *error) {
         [ProgressView hiddenCustomProgressViewAddTo:wself.view];
    }];
    
    
}
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (section==0) {
        return self.alreadySignArr.count;
    }
    return self.noSignArr.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    ScanCodePersonnelTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"ScanCodePersonnelTableViewCell"];
    MeetingAttendanceInfo *meetingAttendanceInfo;
    if (indexPath.section==0) {
        meetingAttendanceInfo=_alreadySignArr[indexPath.row];
    }else{
        meetingAttendanceInfo=_noSignArr[indexPath.row];
    }
    //cell.headImage.image=[UIImage imageNamed:meetingAttendanceInfo];
    cell.name.text=meetingAttendanceInfo.meetingAttendanceName;
    cell.meetingAttendanceNumber.text=meetingAttendanceInfo.meetingAttendanceNumber;
    cell.scanCodeTime.text=meetingAttendanceInfo.attendanceTime;
    [self sendImageRequest:meetingAttendanceInfo.meetingAttendanceNumber andCell:cell andDefaultImageName:@""];
    return cell;
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    UIView *view=[[UIView alloc]initWithFrame:CGRectMake(0, 0, APPW, 20)];
    view.backgroundColor=[UIColor whiteColor];
    UILabel *lable=[[UILabel alloc]initWithFrame:CGRectMake(20, 0, APPW-20, 19)];
    lable.textColor=[UIColor colorWithHexString:@"939393"];
   
    if (section==0) {
        lable.text=[NSString stringWithFormat:@"已签到%lu/%u个",(unsigned long)_alreadySignArr.count,_noSignArr.count+_alreadySignArr.count];
    }else{
        lable.text=[NSString stringWithFormat:@"未签到%lu/%u个",(unsigned long)_noSignArr.count,_noSignArr.count+_alreadySignArr.count];
    }
    
    lable.font=[UIFont systemFontOfSize:12.0];
    [view addSubview:lable];
    UIView *lineView=[[UIView alloc]initWithFrame:CGRectMake(0, 19, APPW, 1)];
    lineView.backgroundColor=[UIColor colorWithHexString:@"f4f4f4"];
    [view addSubview:lineView];
    return view;
}
#pragma mark -请求图片
- (void)sendImageRequest:(NSString *)mId andCell:(ScanCodePersonnelTableViewCell*)cell andDefaultImageName:(NSString *)imageName
{
    [UserImageCacheManager loadHeaderImageWithUID:mId placeholder:@"default_icon_small" imageView:cell.headImage];
    
}
-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 20;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 70;
}
#pragma mark 返回
- (IBAction)back:(id)sender {
    
    [self.navigationController popViewControllerAnimated:YES];
    
}
- (void)didReceiveMemoryWarning {
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

@end
