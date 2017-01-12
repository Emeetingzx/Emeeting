//
//  InvitationViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/4.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "InvitationViewController.h"
#import "InvitaMeeting.h"
#import "ProgressView.h"
#import "ContactsListViewController.h"
#import "MeetingDetailsViewController.h"
#import "PhoneOrVideoMeetingDeailsViewController.h"
#import "MarkedWords.h"
#import "UIColor+LJFColor.h"
#import "Tools.h"
#import "DoInvitaMeeting.h"
#import "RegularTool.h"
@interface InvitationViewController (){
    InvitaMeeting *invitaMeeting;
    DoInvitaMeeting *doInvitaMeeting;
}

@end

@implementation InvitationViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    _numberTextField.layer.borderColor=[[UIColor colorWithHexString:@"c6c6c6"] CGColor];
    _numberTextField.layer.borderWidth=1;
    if (_rowNum==0) {
        _promptLable.text=@"提示：输入视频终端号码";
        _promptFormatLable.text=@"";
        _addAddressBookBtn.hidden=YES;
    }else if (_rowNum==2){
        _promptLable.text=@"提示：\n输入外线固话或手机号码(仅支持国内号码)";
        _promptFormatLable.text=@"";
    }else{
        _promptLable.text=@"提示：\n国内内线：城市代码+5位号码,如075583456";
        _promptFormatLable.text=@"国际内线：国家代码+城市代码+5位号码\n如00925181234";
        _addAddressBookBtn.hidden=YES;
    }
    _numberTextField.delegate=self;
}

#pragma mark - 会议邀请接口
-(void)requestJoinMeetingplace{
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!invitaMeeting) {
        invitaMeeting=[[InvitaMeeting alloc]init];
    }

    __weak typeof(self) wself = self;
    NSString *type=[NSString stringWithFormat:@"%ld",(long)_rowNum];
    [invitaMeeting sendJSONRequestWithMeetingId:_meetingId Type:type  Number:_numberTextField.text Name:@""  Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"邀请成功!"];
            [wself backMeetingDetail];
        }else{
            if (response.d!=NULLObject && response.d!=nil &&  ![@""isEqualToString:response.d]) {
                
                if([response.d rangeOfString:@"自动断开"].location !=NSNotFound){
                    CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:response.d firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
                    customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
                    customAlertView.tag=10000000;
                    [self.view addSubview:customAlertView];
                }else{
                    [MarkedWords showMarkedWordsWithMessage:response.d addToView:wself.view];
                }
                
            }else{
                [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
            }
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
    
}
#pragma mark - 会议邀请确认接口
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
            [MarkedWords showMarkedWordsWithMessage:@"邀请失败!" addToView:wself.view];
            
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
    for (UIViewController *controller in self.navigationController.viewControllers) {
        if ([controller isKindOfClass:[MeetingDetailsViewController class]] || [controller isKindOfClass:[PhoneOrVideoMeetingDeailsViewController class]]) {
            self.refreshMeetingJoinInfoBlock();
            [self.navigationController popToViewController:controller animated:YES];
        }
    }
}
#pragma mark 返回
- (IBAction)back:(id)sender {
    [_numberTextField resignFirstResponder];
    [self.navigationController popViewControllerAnimated:YES];
    
    
}
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
    if (![_numberTextField isExclusiveTouch]) {
        [_numberTextField resignFirstResponder];
    }
}

//#pragma mark - 输入框输入限制条件
//- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
//{
//    if(_rowNum==1){
//        if (_numberTextField.text.length==0) {
//            if (![string isEqualToString:@"0"]) {
//               
//                return NO;
//            }
//        }
//    }
//    return YES;
//}
- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    [_numberTextField resignFirstResponder];
    return YES;
}
- (IBAction)joinMeetingplaceAction:(id)sender {
     [_numberTextField resignFirstResponder];
    if (_numberTextField.text.length>0) {
        if(_rowNum==1){
            if ([[_numberTextField.text substringToIndex:1] isEqualToString:@"0"]) {
                 [self requestJoinMeetingplace];
            }else{
                [MarkedWords showMarkedWordsWithMessage:@"号码输入错误,请从新输入" addToView:self.view];
            }
        }else if(_rowNum==0){
            if([RegularTool validateGKNum:_numberTextField.text]){
                [self requestJoinMeetingplace];
            }else{
                [MarkedWords showMarkedWordsWithMessage:@"GK号码错误,无法找到对应的会议室,请重新输入!" addToView:self.view];
            }
            
        }else{
            if ([[_numberTextField.text substringToIndex:1] isEqualToString:@"0"]) {
                if(_numberTextField.text.length==11 || _numberTextField.text.length==12){
                    [self requestJoinMeetingplace];
                }else{
                    [MarkedWords showMarkedWordsWithMessage:@"电话号码错误,请重新输入!" addToView:self.view];
                }
            }else{
                if(_numberTextField.text.length==11 && [[_numberTextField.text substringToIndex:1] isEqualToString:@"1"]){
                    [self requestJoinMeetingplace];
                }else{
                    if(_numberTextField.text.length==8){
                        [self requestJoinMeetingplace];
                    }else{
                        [MarkedWords showMarkedWordsWithMessage:@"手机号码错误,请重新输入!" addToView:self.view];
                    }
                    
                }

            }
            
        }
    }else{
        [MarkedWords showMarkedWordsWithMessage:@"内容不能为空" addToView:self.view];
    }
    
}
- (IBAction)addAddressBookAction:(id)sender {
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
    ContactsListViewController  *contactsListViewController = [storyboard instantiateViewControllerWithIdentifier:@"ContactsListViewController"];
    contactsListViewController.meetingId=_meetingId;
    contactsListViewController.refreshMeetingJoinInfoBlock=_refreshMeetingJoinInfoBlock;
    contactsListViewController.meetingJoinInfoList=_meetingJoinInfoList;
    [self.navigationController pushViewController:contactsListViewController animated:YES];

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
