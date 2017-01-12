//
//  ContactsListViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/4.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ContactsListViewController.h"
#import "TKAddressBook.h"
#import "ContactsListTableViewCell.h"
#import "UIImage+Extension.h"
#import "InvitaMeeting.h"
#import "ProgressView.h"
#import "UIColor+LJFColor.h"
#define InvitationBtnKey @"invitationBtnKey"
#import "MeetingJoinInfo.h"
#import "MarkedWords.h"
#import "PinYinString.h"
#import "DoInvitaMeeting.h"
#import "RegularTool.h"
@interface ContactsListViewController (){
    ABAddressBookRef _book;
    InvitaMeeting *invitaMeeting;
    DoInvitaMeeting *doInvitaMeeting;
}

@end

@implementation ContactsListViewController



- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    //新建一个通讯录类
    _tableView.dataSource=self;
    _tableView.delegate=self;
    _tableView.tableFooterView=[[UIView alloc]init];
    [_tableView registerNib:[UINib nibWithNibName:@"ContactsListTableViewCell" bundle:nil] forCellReuseIdentifier:@"ContactsListTableViewCell"];
    _addressBookTemp=[[NSMutableArray alloc]init];
    //创建通讯录对象
    _book = ABAddressBookCreateWithOptions(NULL, NULL);
    //1.请求权限
    ABAddressBookRequestAccessWithCompletion(_book, ^(bool granted, CFErrorRef error) {
        // 用户允许应用访问通讯录数据
        if (granted) {
            NSLog(@"用户允许应用访问通讯录数据");
        }else{
            NSLog(@"用户不允许应用访问通讯录数据");
//            [SVProgressHUD showInfoWithStatus:@"没有权限访问通讯录"];
        }
    });
    //读取通讯录
    [self readContacts];
    [self sortAddressBook:_addressBookTemp];
}

-(void)sortAddressBook:(NSArray *)arr{
    _addressBookTemp=[PinYinString SortArray:arr];
    [_tableView reloadData];
}
///读取通讯录信息
- (void)readContacts
{
    // 1.读取所有的联系人
    CFArrayRef allPeople = ABAddressBookCopyArrayOfAllPeople(_book);
    // 2.遍历联系人
    long count = CFArrayGetCount(allPeople);
    for (long i = 0; i < count; ++i) {
        // 一个联系人的数据 就是一条记录
        ABRecordRef person = CFArrayGetValueAtIndex(allPeople, i);
        // 获取姓
        CFStringRef lastName = ABRecordCopyValue(person, kABPersonLastNameProperty);
        // 获取名
        CFStringRef firstName = ABRecordCopyValue(person, kABPersonFirstNameProperty);
        NSString *name = @"";
        if (firstName && lastName) {
            name = [NSString stringWithFormat:@"%@%@",lastName,firstName];
        }else if(lastName && !firstName){
            name = (__bridge NSString *)(lastName);
        }else if(!lastName && firstName){
            name = (__bridge NSString *)(firstName);
        }else
        {
            name = @"";
        }
        //获取头像
        NSData *imageData = (__bridge NSData*)ABPersonCopyImageData(person);
        UIImage *image = [UIImage imageWithData:imageData];
        // 获取电话号码(多个)
        ABMultiValueRef phones = ABRecordCopyValue(person, kABPersonPhoneProperty);
        // 获取电话号码
        long phonesCount = ABMultiValueGetCount(phones);
        for (long j = 0 ; j < phonesCount; ++j) {
            // 电话类型
            CFStringRef phoneType = ABMultiValueCopyLabelAtIndex(phones, j);
            // 电话号码
            CFStringRef phoneNum = ABMultiValueCopyValueAtIndex(phones, j);
            NSString *telphoneNum = (__bridge NSString *)phoneNum;
            //过滤电话号码中间的特殊字符
            NSString *telphoneNum01 = [self stringByTrimmingCharactersInSet:telphoneNum];
            
            //if (telphoneNum01.length == 11) {
                //创建一个通讯录联系人对象（提前定义好的一个联系人类）
                  TKAddressBook *addressBook = [[TKAddressBook alloc] init];
                //设置电话号码
                addressBook.tel = telphoneNum;

                //联系人名字
                addressBook.name = name;
                //头像
                addressBook.image = image;
               addressBook.isInvitation=NO;
            
                for (MeetingJoinInfo *meetingJoinInfo in _meetingJoinInfoList) {
                    if ([telphoneNum01 isEqualToString:meetingJoinInfo.terminalName] || [telphoneNum01 isEqualToString:[meetingJoinInfo.terminalNumber substringFromIndex:2]]) {
                        addressBook.isInvitation=YES;
                        
                    }
                }
                
                //将对象加入电话联系人数组中
                [_addressBookTemp addObject:addressBook];
            //}
            CFRelease(phoneType);
            CFRelease(phoneNum);
        }
        CFRelease(phones);
    }
    CFRelease(allPeople);
}

///过滤字符串中间的特殊符号
- (NSString *)stringByTrimmingCharactersInSet:(NSString *)str
{
    
    NSString *tempStr = @"";
    for (int i = 0; i < str.length; i++) {
        NSString  *strc = [str substringWithRange:NSMakeRange(i, 1)];
        
        if ([RegularTool validateNumber:strc]) {
            tempStr = [tempStr stringByAppendingString:strc];
        }
    }
    return tempStr;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    
    return _addressBookTemp.count;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    ContactsListTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"ContactsListTableViewCell"];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    TKAddressBook *addressBook=_addressBookTemp[indexPath.row];
    if (addressBook.image) {
        cell.headImage.image=addressBook.image;
    }else{
        cell.headImage.image=[UIImage imageNamed:@"default_icon_small"];
    }
    
    cell.name.text=addressBook.name;
    cell.phoneNum.text=addressBook.tel;
    
    if (addressBook.isInvitation) {
        cell.invitationBtn.backgroundColor=[UIColor colorWithHexString:@"a5a5a5"];
    }else{
        cell.invitationBtn.backgroundColor=[UIColor colorWithHexString:@"FF9600"];
        [cell.invitationBtn addTarget:self action:@selector(requestJoinMeetingplace:) forControlEvents:UIControlEventTouchUpInside];
        objc_setAssociatedObject(cell.invitationBtn, InvitationBtnKey, indexPath, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
    }
    return cell;
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 61;
}


#pragma mark - 会议邀请接口
-(void)requestJoinMeetingplace:(UIButton *)sender{
    NSIndexPath *indexPath=objc_getAssociatedObject(sender, InvitationBtnKey);
    _selectAddressBook=_addressBookTemp[indexPath.row];
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!invitaMeeting) {
        invitaMeeting=[[InvitaMeeting alloc]init];
    }
    __weak typeof(self) wself = self;
    
   NSString *number=[wself stringByTrimmingCharactersInSet:wself.selectAddressBook.tel];
    
    if([[number substringToIndex:2] isEqualToString:@"86"]){
            number=[number substringFromIndex:2];
    }else{
        if([[number substringToIndex:3] isEqualToString:@"086"]){
            number=[number substringFromIndex:3];
        }
    }
   
    [invitaMeeting sendJSONRequestWithMeetingId:_meetingId Type:@"2"  Number:number Name:wself.selectAddressBook.name  Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"邀请成功!"];
            //[sender setTitleColor:[UIColor colorWithHexString:@"A5a5a5"] forState:UIControlStateNormal];
            sender.backgroundColor=[UIColor colorWithHexString:@"a5a5a5"];
            wself.selectAddressBook.isInvitation=YES;
            wself.addressBookTemp[indexPath.row]=wself.selectAddressBook;
            //[wself backMeetingDetail];
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

-(void)backMeetingDetail{
    for (UIViewController *controller in self.navigationController.viewControllers) {
        if ([controller isKindOfClass:[MeetingDetailsViewController class]] || [controller isKindOfClass:[PhoneOrVideoMeetingDeailsViewController class]]) {
            self.refreshMeetingJoinInfoBlock();
            [self.navigationController popToViewController:controller animated:YES];
        }
    }
}
#pragma mark - 会议邀请确认接口
-(void)requestDoInvitaMeeting{
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!doInvitaMeeting) {
        doInvitaMeeting=[[DoInvitaMeeting alloc]init];
    }
    __weak typeof(self) wself = self;
     NSString *number=[wself stringByTrimmingCharactersInSet:wself.selectAddressBook.tel];
    [doInvitaMeeting sendJSONRequestWithMeetingId:_meetingId  Number:number  Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"邀请成功!"];
            //[wself backMeetingDetail];
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.d addToView:wself.view];
            
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

#pragma mark 返回
- (IBAction)back:(id)sender {
   // [self.navigationController popViewControllerAnimated:YES];
    [self backMeetingDetail];
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
