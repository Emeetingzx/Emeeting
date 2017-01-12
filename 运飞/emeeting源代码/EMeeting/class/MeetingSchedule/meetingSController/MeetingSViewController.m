//
//  MeetingSViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/23.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingSViewController.h"
#import "AlertTableView.h"
#import "SubmitBookingMeetingRoomHandle.h"
#import "MarkedWords.h"
#import "ProgressView.h"
#import "CustomAlertView.h"

@interface MeetingSViewController ()<AlertTableViewDelegate,UITextFieldDelegate>

@property (nonatomic, strong) SubmitBookingMeetingRoomHandle *submitMeetingHandle;

@property (nonatomic, strong) FObjectModel *fObject;

@end

@implementation MeetingSViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self.nameTextFiled addTarget:self action:@selector(textFieldDidChange:) forControlEvents:UIControlEventEditingChanged];
    
    self.leaderLevel = @"4";
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - 选择领导
- (IBAction)selectLeader:(UIButton *)sender
{
    [self.view endEditing:YES];
    
    AlertTableView *leader = [[AlertTableView alloc] initWithFrame:self.view.bounds andSelectedButtonString:self.leaderLabel.text andOtherButtons:@[@"二层领导",@"三层领导",@"四层领导",@"其他",@"取消"] andDelegate:self];
    [self.view addSubview:leader];
}

#pragma mark - AlertTableView代理方法
- (void)AlertTableView:(AlertTableView *)alertTable andDidClickButtonIndex:(NSInteger)index andClickButtonIndexText:(NSString *)text
{
    if (index != CancelIndex)
    {
        self.leaderLabel.text = text;
        
        self.leaderLevel = [NSString stringWithFormat:@"%ld",index + 1];
        
        switch (index)
        {
            case 0: self.rankLabel.text = @"A级"; break;
                
            case 1: self.rankLabel.text = @"B级"; break;
                
            default: self.rankLabel.text = @"C级";
                break;
        }
    }
}

#pragma mark - 预定会议
- (IBAction)scheduleMeeting:(id)sender
{
    if (![self checkfObject])
    {
        return;
    }
    
    [self sendSubmitBookingMeetingRoomRequestWithFObjectModel:self.fObject];
}

#pragma mark - 核对提交的会议信息
- (BOOL)checkfObject
{
    self.fObject = [[FObjectModel alloc] init];
    self.fObject.meetingName = self.meetingName;
    self.fObject.emeetingId = self.meetingId;
    self.fObject.attendLeaderLevel = self.leaderLevel;
    
    if (self.fObject.meetingName.length >0)
    {
        return YES;
    }else
    {
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:self.view.bounds Message:@"请输入会议名称" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
        [self.view addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
        return NO;
    }
}


- (IBAction)back:(id)sender
{

    [self.navigationController popViewControllerAnimated:YES];

}

#pragma mark - 点击屏幕退出编辑
-(void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [self.view endEditing:YES];
}

#pragma mark - 结束编辑
- (void)textFieldDidEndEditing:(UITextField *)textField
{
    self.meetingName = textField.text;
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    
    return YES;
}

#pragma mark - 输入框输入限制条件
- (void)textFieldDidChange:(UITextField *)textField
{
    if (textField == self.nameTextFiled)
    {
        if (textField.text.length > 50)
        {
            textField.text = [textField.text substringToIndex:50];
        }
    }
}

#pragma mark - 发送提交预定会议请求
- (void)sendSubmitBookingMeetingRoomRequestWithFObjectModel:(FObjectModel *)fObject
{
    [ProgressView showHUDAddedToProgress:self.view ProgressText:@"正在预订中，请稍后..."];
    
    __weak typeof(self) wself = self;
    
    if (_submitMeetingHandle == nil)
    {
        _submitMeetingHandle = [[SubmitBookingMeetingRoomHandle alloc] init];
    }
    
    [_submitMeetingHandle sendJSONRequestWithFObjectModel:fObject Success:^(ResponseObject *response)
    {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s)
        {
            CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:wself.view.bounds delegate:wself Message:@"已成功预订!" firstButtonTitle:@"继续预订" secondButtonTitle:@"查看我的会议" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
            [wself.view addSubview:alertView];
        }else
        {
            if (response.m)
            {
                [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
            }else
            {
                [MarkedWords showMarkedWordsWithMessage:@"预订发生未知错误,请稍后重试" addToView:wself.view];
            }
 
        }
    } failure:^(NSError *error)
    {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [MarkedWords showMarkedWordsWithMessage:@"网络不可用，请检查网络设置" addToView:wself.view];
    }];
}

- (void)CustomAlertView:(CustomAlertView *)customAlert andButtonClickIndex:(NSInteger)index
{
    if (index == 0)
    {
        if (self.isFromShake)
        {
            if (self.isFromFirst)
            {
                [self.navigationController popToViewController:self.navigationController.viewControllers[1] animated:YES];
            }else
            {
                [self.navigationController popToRootViewControllerAnimated:YES];
            }
        }else
        {
            [self.navigationController popViewControllerAnimated:YES];
        }
        
    }else if (index == 1)
    {
        [[NSNotificationCenter defaultCenter] postNotificationName:SetCenterControllerNoti object:@(3)];
    }
}

@end
