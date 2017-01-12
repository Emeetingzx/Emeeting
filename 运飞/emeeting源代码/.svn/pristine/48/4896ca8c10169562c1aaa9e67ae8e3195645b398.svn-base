//
//  MeetingDominateViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/4.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingDominateViewController.h"
#import "MeetingDominateViewTableViewCell.h"
#import "InvitaMeeting.h"
#import "ProgressView.h"
#import "RegularTool.h"
#import "MarkedWords.h"
#import "Tools.h"
#import "DoInvitaMeeting.h"
#import "UIColor+LJFColor.h"
@interface MeetingDominateViewController (){
    InvitaMeeting *invitaMeeting;
    DoInvitaMeeting *doInvitaMeeting;
}

@end

@implementation MeetingDominateViewController


-(NSArray *)array
{
    if (_array==nil) {
        _array=[[NSMutableArray alloc]initWithObjects:@[@"邀请其他会议室一起视频",@"输入会议室终端号,即可邀请参会",@"InvitationMeeting.png"],
            @[@"邀请公司内线加入",@"输入公司内线号码,即可邀请同事参会",@"phoneFriends.png"],
            @[@"邀请公司外线加入",@"输入外线固话,手机号或邀请手机联系人参会",@"callMeeting.png"], nil];
        
    }
    return _array;
}
- (void)viewDidLoad {
    [super viewDidLoad];
    _tableView.dataSource=self;
    _tableView.delegate=self;
    _tableView.bounces=NO;
    _tableView.tableFooterView=[UIView new];
    _numberTextField.layer.borderColor=[[UIColor colorWithHexString:@"c6c6c6"] CGColor];
    _numberTextField.layer.borderWidth=1;
   [_tableView registerNib:[UINib nibWithNibName:@"MeetingDominateViewTableViewCell" bundle:nil] forCellReuseIdentifier:@"MeetingDominateViewTableViewCell"];
    
    // Do any additional setup after loading the view.
    _numberTextField.delegate=self;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.array.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    MeetingDominateViewTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"MeetingDominateViewTableViewCell"];
    cell.imageview.image=[UIImage imageNamed:self.array[indexPath.row][2]];
    cell.titleLable.text=self.array[indexPath.row][0];
    cell.assistantTileLable.text=self.array[indexPath.row][1];
    return cell;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 70;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
    InvitationViewController  *invitationViewController = [storyboard instantiateViewControllerWithIdentifier:@"InvitationViewController"];
    invitationViewController.rowNum=indexPath.row;
    invitationViewController.meetingId=_meetingId;
    invitationViewController.meetingJoinInfoList=_meetingJoinInfoList;
    invitationViewController.refreshMeetingJoinInfoBlock=_refreshMeetingJoinInfoBlock;
    [self.navigationController pushViewController:invitationViewController animated:YES];
}

- (IBAction)joinMeetingplaceAction:(id)sender {
    [_numberTextField resignFirstResponder];
    if (_numberTextField.text.length>0) {
        [self requestJoinMeetingplace];
    }else{
         [MarkedWords showMarkedWordsWithMessage:@"内容不能为空" addToView:self.view];
    }
    
    
}

#pragma mark - 会议邀请接口
-(void)requestJoinMeetingplace{
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!invitaMeeting) {
        invitaMeeting=[[InvitaMeeting alloc]init];
    }
    __weak typeof(self) wself = self;
    
    NSString *type=@"0";
    if([RegularTool validateMobile:_numberTextField.text]){
            type=@"2";
    }else if([RegularTool validateGKNum:_numberTextField.text]){
        type=@"0";
    }else{
        type=@"1";
    }
    
    [invitaMeeting sendJSONRequestWithMeetingId:_meetingId Type:type  Number:_numberTextField.text Name:@""  Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"邀请成功!"];
            [wself backMeetingDetail];
        }else{
            if (response.d==NULLObject &&response.d!=nil &&  [@""isEqualToString:response.d]) {
                CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:response.d firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
                customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
                customAlertView.tag=10000000;
                [self.view addSubview:customAlertView];
            }else{
                [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
            }

        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
    
}

#pragma mark - 会议邀请接口
-(void)requestDoInvitaMeeting{
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!doInvitaMeeting) {
        doInvitaMeeting=[[DoInvitaMeeting alloc]init];
    }
    __weak typeof(self) wself = self;
    [doInvitaMeeting sendJSONRequestWithMeetingId:_meetingId  Number:_numberTextField.text  Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"邀请成功!"];
            [wself backMeetingDetail];
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
            
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
    
}


#pragma mark —CustomAlertView代理方法
- (void)CustomAlertView:(CustomAlertView *)customAlert andButtonClickIndex:(NSInteger)index{
    if (index==1) {
        if (customAlert.tag==10000000) {
            [self requestDoInvitaMeeting];
        }
    }
}
-(void)backMeetingDetail{
    self.refreshMeetingJoinInfoBlock();
    [self.navigationController popViewControllerAnimated:YES];
}


- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
    if (![_numberTextField isExclusiveTouch]) {
        [_numberTextField resignFirstResponder];
    }
}
- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    [_numberTextField resignFirstResponder];
    return YES;
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
